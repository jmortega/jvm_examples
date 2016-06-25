@Grab('com.gmongo:gmongo:1.0')
import com.gmongo.GMongo

def mongo = new GMongo()

// Create db
def db = mongo.getDB('groovy-book')
// DROP Collection
db.todos.drop()
println db.getLastError()
// Insert docs
db.todos.insert([name: 'buy milk'])
db.todos.insert(name: 'pay rent')
println db.todos.findOne(name:'pay rent')

db.todos.find([name:~/^pay/]).each {

    println it
}

db.todos << [name: 'read document']

//println db.todos.findOne()

// Update doc (change value)
db.todos.update(name: 'pay rent', [$set: [name: 'pay December rent']])
assert db.todos.count(name:'pay December rent') == 1
// Update doc (add attribute)
db.todos.update(name: 'pay December rent', [$set:[priority:1]])
// Find by name
def todo = db.todos.findOne(name:'buy milk')

println todo.getClass().getName()
// Update doc (add attribute), alternate way
todo.priority = 2
db.todos.save todo
println todo

db.todos.insert([name:"call John", priority:2])
assert db.todos.count(priority: 2) == 2

db.todos.remove(priority:2,name:"call John")
assert db.todos.count(priority: 2) == 1

class Todo {

    def name
    def priority
    def context
    
}

def td = new Todo(name:'open account', priority:1, context:'finance')
db.todos << td.properties.findAll { !['class', 'metaClass'].contains(it.key) } 
db.todos.find([name:~/^open/]).each {

    println it
}

println td.properties.findAll { !['class', 'metaClass'].contains(it.key) } 
