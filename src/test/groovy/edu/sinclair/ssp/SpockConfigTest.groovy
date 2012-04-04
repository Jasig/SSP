package edu.sinclair.ssp

import spock.lang.Specification

/**
 * Test whether Spock test environment is correctly setup
 *
 */
class SpockConfigTest extends Specification {
	def "length of Spock's and his friends' names"() {
		expect:
		name.size() == length

		where:
		name     | length
		"Spock"  | 5
		"Kirk"   | 4
		"Scotty" | 6
	}
}
