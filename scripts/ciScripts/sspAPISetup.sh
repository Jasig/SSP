#!/bin/sh
#
# Licensed to Jasig under one or more contributor license
# agreements. See the NOTICE file distributed with this work
# for additional information regarding copyright ownership.
# Jasig licenses this file to you under the Apache License,
# Version 2.0 (the "License"); you may not use this file
# except in compliance with the License. You may obtain a
# copy of the License at:
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on
# an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied. See the License for the
# specific language governing permissions and limitations
# under the License.
#

#
# *** SSP Insert Static Reference Content into SSP Training Script ***
#
# This script inserts static reference content that is referenced by students and coaches
#  into a postgres SSP database. It does so by calling a psql command on database 'ssp' 
#  that loads a sql file and inserts it into the database. 
#
# Params: 1st OPTIONAL arg number=1 to tell script to output to file 
#	 	instead of the db. File is set to 
#           ../postgres/sspTrainingDataCompiled(TODAY'S DATE).sql
#
#
# Note: Requires Postgres 8.X or higher (SQL Script Dependency)
#

TOMCAT_SERVICE1="tomcat6"
TOMCAT_SERVICE2="tomcatSSP_API"
DB_NAME="sspAPI"
SSP_URL="http://ec2-23-20-222-7.compute-1.amazonaws.com/ssp-platform/media/skins/universality/ssp/images/portal_logo.png"
HTTP_STATUS=1
TIME=0
MAXTIMECYCLES=40
TIMEBETWEENCYCLES=5

if [ $1 -le 1 ]; then
    
    sudo /etc/init.d/$TOMCAT_SERVICE1 stop
    sleep 5s
 
    psql -U postgres -c "SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname='$DB_NAME';"
    psql -U postgres -c "DROP DATABASE \"$DB_NAME\";"
    psql -U postgres -c "CREATE DATABASE \"$DB_NAME\";"
    psql -U postgres -c "ALTER DATABASE \"$DB_NAME\" OWNER TO sspadmin;"
    psql -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE \"$DB_NAME\" to sspadmin;"
    psql -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE \"$DB_NAME\" to ssp;"

    exit 0

elif [ $1 -eq 2 ]; then

    sudo /etc/init.d/$TOMCAT_SERVICE2 start
    sleep 15s	    

    while [ "$HTTP_STATUS" != "200"  ] && [ $TIME -lt $MAXTIMECYCLES ]
    do 
        HTTP_STATUS=`wget --spider -S "$SSP_URL" 2>&1 | grep "HTTP/" | awk '{print $2}'`

       if [ "$HTTP_STATUS" != "200" ]; then
           sleep "$TIMEBETWEENCYCLES"s
           let TIME++
       else
           exit 0
       fi      
    done

    exit 1 
else
    sudo /etc/init.d/$TOMCAT_SERVICE2 stop
    sleep 15s
    sudo /etc/init.d/$TOMCAT_SERVICE1 start

    exit 0
fi

exit 1 

