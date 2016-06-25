@Grab('commons-dbcp:commons-dbcp:1.4')
import static DBUtil.*
import groovy.sql.Sql
import org.apache.commons.dbcp.BasicDataSource

def server = startServer()

// Connect with simple parameters
Sql sql = Sql.newInstance('jdbc:hsqldb:hsql://localhost/cookingdb', 'sa', '', 'org.hsqldb.jdbcDriver')

println 'connected with connection data: ok'

Sql sql2 = new Sql(sql.createConnection())

println 'connected with reused connection: ok'

def ds = new BasicDataSource()
ds.with {
  driverClassName = 'org.hsqldb.jdbcDriver'
  password = ''
  username = 'sa'
  url = 'jdbc:hsqldb:hsql://localhost/cookingdb'

}

Sql sql3 = new Sql(ds)

println 'connected with datasource: ok'

server.stop()