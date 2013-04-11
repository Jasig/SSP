/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.dao

import net.sourceforge.jtds.jdbc.DateTime
import org.hibernate.SessionFactory
import org.jasig.ssp.model.Appointment
import org.jasig.ssp.model.Person
import org.jasig.ssp.service.SecurityService
import org.jasig.ssp.util.database.ReloadableBasicDataSourceWrapper
import org.jasig.ssp.util.service.stub.Stubs
import org.jasig.ssp.util.transaction.WithTransaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import java.util.concurrent.Callable

@ContextConfiguration("dao-testConfig.xml")
// don't want this to be transactional. need to be able to force a read-back
// from the db after writing values
class PersistentTimestampTest extends Specification {

    TimeZone origDefaultTimezone

    Object origJtdsCalendar

    UUID appointmentId

    @Autowired
    SecurityService securityService

    @Autowired
    AppointmentDao appointmentDao

    @Autowired
    SessionFactory sessionFactory

    @Autowired
    PersonDao personDao

    @Autowired
    WithTransaction withTransaction

    @Autowired
    ReloadableBasicDataSourceWrapper dataSource

    def setup() {
        origDefaultTimezone = TimeZone.default
        TimeZone.default = TimeZone.getTimeZone("America/New_York")
        // Postgres driver permanently caches the default timezone, but on
        // each connection instance. So there's no global cache we can
        // meddle with. Instead have to reinitialize the entire conn pool.
        dataSource.reload();
        // For JTDS the timezone cache is global to the thread and available
        // via a static ThreadLocal field
        origJtdsCalendar = DateTime.calendar.get()
        DateTime.calendar.set(new GregorianCalendar())
        securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID))
    }

    def cleanup() {
        txn { if ( appointmentId != null ) appointmentDao.delete(appointmentDao.load(appointmentId)) }
        securityService.setCurrent(new Person())
        TimeZone.default = origDefaultTimezone
        dataSource.reload(); // so db driver picks up default TZ change
        DateTime.calendar.set(origJtdsCalendar)
    }

    def "never stores ambiguous timestamps"() {
        // At a DST boundary when you reset time backwards you can have
        // an hour that occurs twice. During that hour, if you store two
        // distinct timestamps in a local timezone but without timezone
        // information, you can't necessarily know which preceded which.
        // This test demonstrates the problem by persisting two different
        // timestamps which both represent 1:30a, one DST, one standard time.
        // If the data layer is implemented incorrectly, we can't distinguish
        // between these times when we read the data back.

        given: "an assignment with potentially ambiguious start and end timestamps"
        def dateFormatWithTz = "yyyy-MM-dd HH:mm:ss z"
        // 2013-11-03 01:30:00 EDT
        def middleOfAmbiguousDaylightSavingsHour = Date.parse(dateFormatWithTz, "2013-11-03 01:30:00 EDT")
        // 2013-11-03 01:30:00 EST
        def middleOfAmbiguousStandardTimeHour = Date.parse(dateFormatWithTz, "2013-11-03 01:30:00 EST")
        txn {
            def savedAppointment = appointmentDao.save(new Appointment(
                    startTime: middleOfAmbiguousDaylightSavingsHour,
                    endTime: middleOfAmbiguousStandardTimeHour,
                    person: findPerson(Stubs.PersonFixture.KEN)))
            appointmentId = savedAppointment.id
        }

        when: "the apppointment is read back in another transaction"
        def loadedAppointment
        txn {
            loadedAppointment = appointmentDao.get(appointmentId)
        }


        then: "the appointment start and end times are correctly distinguished"
        loadedAppointment.startTime.time != loadedAppointment.endTime.time

    }

    def Person findPerson(personFixture) {
        personDao.get(personFixture.id)
    }

    def txn(clos) {
        withTransaction.withNewTransaction(clos)
    }

    def swallow(clos) {
        try {
            clos()
        } catch (e) {}
    }

}
