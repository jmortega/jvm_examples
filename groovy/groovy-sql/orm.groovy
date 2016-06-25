import static DBUtil.*
import groovy.sql.Sql
import groovy.sql.DataSet

class Person {

  Integer id
  String name
  String lastName
  Integer age
  Integer department

}

def server = startServer()
def sql = Sql.newInstance(dbSettings)

sql.execute('''CREATE TABLE EMPLOYEE (
          ID INTEGER PRIMARY KEY,
          NAME VARCHAR(100),
          LASTNAME VARCHAR(100),
          AGE INTEGER,
          DEPARTMENT INTEGER)''')

def person = new DataSet(sql, 'EMPLOYEE')
person.add(name: 'John',
           lastname: 'Willis',
           id: 1,
           age: 40,
           DEPARTMENT: 100)
person.add(name: 'Alfred',
           lastname: 'Morris',
           id: 2,
           age: 50,
           DEPARTMENT: 101)

def persons = new DataSet(sql, 'EMPLOYEE')
        persons.each {
          println "employee $it.id - $it.name $it.lastname"
        }

server.stop()

