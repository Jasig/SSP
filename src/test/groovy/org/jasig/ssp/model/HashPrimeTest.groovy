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
package org.jasig.ssp.model;

import static org.junit.Assert.*

import java.lang.reflect.Modifier

import org.jasig.ssp.util.ClassDiscovery
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

public class HashPrimeTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(HashPrimeTest.class);

	@Test
	void testForUniqueHashPrimes(){
		boolean success = true;

		Map<Integer, List<Class>> primeGroups = [:]
		int largest = 1;

		List<Class> classes = ClassDiscovery.getClasses("org.jasig.ssp.model")

		primeGroups = classes.groupBy { Class clazz ->
			if(Auditable.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())){
				LOGGER.debug(clazz.toString() + " - Auditable")
				Auditable auditable = clazz.newInstance()
				if (largest < auditable.hashPrime()){
					// update largest prime for warning purposes so the next available can be found easily
					largest = auditable.hashPrime();
				}
				return auditable.hashPrime()
			}else{
				return 0
				LOGGER.debug(clazz.toString())
			}
		}

		String dups = "";
		primeGroups.each{ key, value ->
			if(value.size()>1 && key!=0){
				success = false
				if (dups.length() > 0) {
					dups += ",";
				}
				dups += key.toString() + " { " + value.toString() + "}"
				LOGGER.error("More than one class generating $key as its hashprime, see: " + value.toString())
			}
		}

		assertTrue("More than one class using the same hashPrime: " + dups + ". Highest prime so far: " + largest, success);
	}

	@Test
	void testForValidHashPrimes(){
		boolean success = true;

		Map<Integer, List<Class>> primeGroups = [:]

		List<Class> classes = ClassDiscovery.getClasses("org.jasig.ssp.model")
		classes.each{ Class clazz ->
			if(Auditable.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())){
				Auditable auditable = clazz.newInstance()
				if(!isPrime(auditable.hashPrime())){
					LOGGER.error("Class has a non prime number as its hashprime, see: " + clazz.toString())
					success = false;
				}
			}
		}

		assertTrue("Invalid Primes Detected", success);
	}

	public boolean isPrime(int value) {

		//exclude negative, 0, and 1, 2
		if(value < 3) return false

		//if even drop right away
		if((value % 2) == 0) return false

		int count = 0
		int i = 3
		//for each number up to the number, is it divisible? (%==0)
		//exit if number is more than half of its number square root
		while(i <= (Math.sqrt(value))){
			if((value % i) == 0) count += 1

			//if we've gotten a hit, don't bother continuing
			if(count > 0) return false

			//next value - no point in trying the next number, an even
			i+=2
		}

		return true
	}


}