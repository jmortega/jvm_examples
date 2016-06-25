@Grapes([
  @Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.6'),
  @Grab('oauth.signpost:signpost-commonshttp4:1.2.1.2'),
  @Grab('oauth.signpost:signpost-core:1.2.1.2')])
import groovyx.net.http.RESTClient

def CONSUMER_KEY = 'wrTpGgKSuz5QKSo7C6OfmQ'
def CONSUMER_SECRET = 'LSRamiXCJIS8O9EL0EETO0GDIcZuOgy1doSqbKba9A'
def ACCESS_TOKEN = '252950142-AZotPooKPeXg72Qry3Z75xGGoZhWK18ccijhVViq'
def ACCESS_SECRET = 'UGAzXg9K5aUrus03jipqZhn5tOACKnbhqH9wuoSwA'

def twitter = new RESTClient( 'https://api.twitter.com/1.1/statuses/' )

twitter.auth.oauth CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, ACCESS_SECRET

println twitter.get( path : 'mentions_timeline.json' ).data
