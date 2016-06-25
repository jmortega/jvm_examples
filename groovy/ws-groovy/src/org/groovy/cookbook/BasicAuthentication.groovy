@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.6')
import groovyx.net.http.HTTPBuilder

def service = new HTTPBuilder('http://localhost:5000/')
service.auth.basic('groovy', 'cookbook')
service.get(path: '/secret').each { line -> println line }
