import static DBUtil.*
import groovy.sql.Sql

def server = startServer()
createSchema()

def cookbooks = [
  [ id: 1,
    title: '30-minute-meals',
    author: 'Jamie Oliver',
    year: 2010],
  [ id: 2,
    title: 'Ministry of food',
    author: 'Jamie Oliver',
    year: 2005],
  [ id: 3,
    title: 'Vegan food',
    author: 'Mr. Spock',
    year: 2105]
]

def sql = Sql.newInstance(dbSettings)
cookbooks.each { cookbook ->
  sql.execute(
    'INSERT INTO COOKBOOK ' +
    'VALUES(:id, :title, :author, :year)',
    cookbook
  )
}

assert 3 == sql.rows('SELECT * FROM COOKBOOK').size()

sql.execute(
  'UPDATE COOKBOOK ' +
  'SET title = :title ' +
  'WHERE ID = :id',
  [title: '15-minutes meals', id: 1]
)

assert '15-minutes meals' == sql.rows('SELECT * FROM COOKBOOK WHERE ID = 1')[0].title

sql.execute(
  'DELETE from COOKBOOK WHERE ID = :id',
  [id: 3]
)

assert 2 == sql.rows('SELECT * FROM COOKBOOK').size()

sql.close()
server.stop()