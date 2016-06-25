@Grab('oauth.signpost:signpost-core:1.2.1.2')
import oauth.signpost.basic.DefaultOAuthConsumer
import oauth.signpost.basic.DefaultOAuthProvider
import oauth.signpost.OAuth

def consumer = new DefaultOAuthConsumer('wrTpGgKSuz5QKSo7C6OfmQ', 'LSRamiXCJIS8O9EL0EETO0GDIcZuOgy1doSqbKba9A')

def provider = new DefaultOAuthProvider(
    "http://twitter.com/oauth/request_token",
    "http://twitter.com/oauth/access_token",
    "http://twitter.com/oauth/authorize")

String authUrl = provider.retrieveRequestToken(consumer, OAuth.OUT_OF_BAND)

println "Open this URL in the browser: " + authUrl
print "Authorize application and enter pin code: "

def pinCode = null
System.in.withReader { pinCode = it.readLine(); println() }

provider.retrieveAccessToken(consumer, pinCode)

println "Access token: " + consumer.token
println "Token secret: " + consumer.tokenSecret
