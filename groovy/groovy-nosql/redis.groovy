@Grab('redis.clients:jedis:2.1.0')
import redis.clients.jedis.*

jedis = new Jedis('localhost')

// GET AND SET
jedis.set('foo', 'bar')
String value = jedis.get('foo')
assert value == 'bar'

// INCR
jedis.set('counter', '1')
jedis.incr('counter')
jedis.incr('counter')
assert jedis.get('counter') == '3'

// EXPIRE
jedis.set('short lived', '10')
jedis.expire('short lived', 3)
Thread.sleep(3000)
assert jedis.exists('short lived') == false

// LISTS

// Lists are used to store an (ordered) collection of items. Stacks and queues can be very easily modeled with lists.

jedis.del ("myList")
jedis.rpush ("myList", "a", "b", "c") // append to start of the list
assert 3 == jedis.llen ("myList")
assert "a" == jedis.lrange("myList", 0,0)[0]

jedis.lpush("myList", "3","2", "1") // append to end of the list
assert 6 == jedis.llen ("myList")

// LIST AS QUEUE

jedis.del ("myQueue")
jedis.lpush("myQueue", "new york")
jedis.lpush("myQueue", "dallas")
jedis.lpush("myQueue", "tulsa")

assert "new york" == jedis.rpop("myQueue")
assert "dallas" == jedis.rpop("myQueue")
assert "tulsa" == jedis.rpop("myQueue")


// HASHES

jedis.hset("user:100", "username", "jack1")
jedis.hset("user:100", "password", "this_is_an_hash!")
jedis.hset("user:100", "last_login", "20121009")

assert "jack1" == jedis.hget("user:100", "username")
assert "this_is_an_hash!" == jedis.hget("user:100", "password")

class User {
  Long userid
  String username
  String password
}

User u = new User()
u.userid = 2001
u.username = "john"
u.password = "12345"

jedis.del ("user:$u.userid")

jedis.hmset("user:$u.userid", u.getProperties().findAll { it.key != "class" && it.key != "userid" }.collectEntries { k,v -> [k, v.toString()]})
assert "john" == jedis.hget("user:2001", "username")
println jedis.hgetAll("user:$u.userid")
jedis.hset("user:$u.userid", "last_login", "20120909")
println jedis.hgetAll("user:$u.userid")

// TRANSACTIONS

def atomic(Closure c) {
  def results = null     
  try {
    def tx = jedis.multi()
    c.delegate = tx      
    c.call()
    results = tx.exec()
  } catch(e) {
    tx.discard()
  }
  return results
}

jedis.del("foo")
jedis.del("bar")

def res =  atomic {
  incr ("foo")
  incr ("bar")
}

println jedis.get("foo")
assert "1" == jedis.get("bar")
assert res == [1,1]
// assert jedis.get("foo") =="2"
