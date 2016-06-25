@Grab('commons-dbcp:commons-dbcp:1.4')
import static DBUtil.*
import groovy.sql.Sql

def server = startServer()
createSchema()
populate()

def params = [ Sql.INTEGER, 'sugar' ]

def sql = Sql.newInstance(dbSettings)
sql.call(
  '{ CALL INGREDIENT_USAGE(:rate, :pattern) }',
   params) { rate ->
   println rate
}

server.stop()
