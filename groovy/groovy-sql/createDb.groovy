import static DBUtil.*
import groovy.sql.Sql

def ddls = [

    '''
       CREATE TABLE COOKBOOK(
          ID INTEGER PRIMARY KEY,
          TITLE VARCHAR(100),
          AUTHOR VARCHAR(100),
          YEAR INTEGER)
    ''',

    '''
       CREATE TABLE CHAPTER(
          ID INTEGER PRIMARY KEY,
          BOOK_ID INTEGER,
          TITLE VARCHAR(100))
    ''',

    '''
       CREATE TABLE RECIPE(
          ID INTEGER PRIMARY KEY,
          CHAPTER_ID INTEGER,
          TITLE VARCHAR(100),
          DESCRIPTION CLOB,
          IMAGE BLOB)
    ''',

    '''
       CREATE TABLE INGREDIENT(
          ID INTEGER PRIMARY KEY,
          RECIPE_ID INTEGER,
          NAME VARCHAR(100),
          AMOUNT DOUBLE,
          UNITS VARCHAR(20))
    '''
]

def server = startServer()

Sql sql = Sql.newInstance(dbSettings)
ddls.each { ddl ->
  sql.execute(ddl)
}
println 'Schema created successfully'

server.stop()