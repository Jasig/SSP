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
//@GrabResolver( name='codehaus.snapshot', root='http://snapshots.repository.codehaus.org', m2compatible='true' )
//@Grab( 'org.codehaus.groovy.modules.http-builder:http-builder:0.6-SNAPSHOT' )
@Grapes([
@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.6-SNAPSHOT' )
])
import groovyx.net.http.RESTClient
import groovyx.net.http.HTTPBuilder
//import groovy.util.slurpersupport.GPathResult
import static groovyx.net.http.ContentType.URLENC
import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.POST

def client = new RESTClient ( 'http://localhost:8080' )

def site = new HTTPBuilder( 'http://localhost:8080' )
//site.auth.basic 'my-client-with-secret-2', 'secret-2'
site.auth.basic 'dmac', 'foo'
//site.auth.basic 'MC101', 'MC101'

//def body = [grant_type:'client_credentials']
def accessToken
//site.post(path: '/ssp/api/oauth/token',
site.post(path: '/ssp/api/1/oauth2/token',
            body: [grant_type:'client_credentials'],
            requestContentType: URLENC ) { resp, reader ->
//
////    println resp
//
        System.out << "Token response: " << reader
        println()
////        System.out << resp
////        System.out << reader['access_token']
//

    accessToken = reader['access_token']
//
////    println json
//
}

//println("Access Token: ${accessToken}")
site.auth.basic "",""
//accessToken = "8cab2bd9-d55b-4cb9-bf11-73545917bc57"
site.get(path: '/ssp/api/1/person',
        contentType: JSON,
        query: ["limit": 1],
        headers: ['Authorization': "Bearer ${accessToken}"]) { resp, reader ->

    System.out << "Person response: " << reader
    println()

}


//
//site.get(path: '/ssp/api/1/foo',
//        headers: ['Authorization': "Bearer ${accessToken}"]) { resp, reader ->
//
//    System.out << "Foo response: " << reader
//    println()
//
//}

//site.request ( POST, JSON ) { req ->
//    uri.path = '/ssp/api/1/reference/earlyAlertOutcome/'
//    headers.'Authorization' = "Bearer ${accessToken}"
//
//    body = '{"name":"foo 3","description":"bar 3"}'
//    response.success = { resp, json ->
//        System.out << "EA Outcome response: " << json
//        println()
//    }
//}

///ssp/api/1/reference/earlyAlertOutcome/
//POST
//        {"id":"","createdDate":null,"modifiedDate":null,"name":"foo","description":"bar","sortOrder":0,"createdBy":{"id":"","firstName":"","lastName":""},"modifiedBy":{"id":"","firstName":"","lastName":""},"objectStatus":"ACTIVE","active":true}


//
//
//twitter = new RESTClient( 'https://twitter.com/statuses/' )
//// twitter auth omitted
//
//try { // expect an exception from a 404 response:
//	twitter.head path : 'public_timeline'
//	assert false, 'Expected exception'
//}
//// The exception is used for flow control but has access to the response as well:
//catch( ex ) { assert ex.response.status == 404 }
//
//assert twitter.head( path : 'public_timeline.json' ).status == 200