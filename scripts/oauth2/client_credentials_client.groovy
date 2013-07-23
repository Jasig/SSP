//@GrabResolver( name='codehaus.snapshot', root='http://snapshots.repository.codehaus.org', m2compatible='true' )
//@Grab( 'org.codehaus.groovy.modules.http-builder:http-builder:0.6-SNAPSHOT' )
@Grapes([
@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.6-SNAPSHOT' )
])
import groovyx.net.http.RESTClient
import groovyx.net.http.HTTPBuilder
//import groovy.util.slurpersupport.GPathResult
import static groovyx.net.http.ContentType.URLENC

def client = new RESTClient ( 'http://localhost:8080' )

def site = new HTTPBuilder( 'http://localhost:8080' )
site.auth.basic 'my-client-with-secret', 'secret'

//def body = [grant_type:'client_credentials']
site.post(path: '/ssp/api/1/oauth/token',
            body: [grant_type:'client_credentials'],
            requestContentType: URLENC ) { resp ->

    println resp.text()

}

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