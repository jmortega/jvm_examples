@GrabConfig(systemClassLoader=true)
@Grab('org.hsqldb:hsqldb:2.3.0')
import org.hsqldb.Server
import groovy.sql.Sql

class DBUtil {

  static dbSettings = [
    url:'jdbc:hsqldb:hsql://localhost/cookingdb',
    driver:'org.hsqldb.jdbcDriver',
    user:'sa',
    password:''
  ]

  static ddls = [
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
    ''',
    '''
       CREATE PROCEDURE INGREDIENT_USAGE(
          OUT INGREDIENTS_RATE INTEGER,
          IN INGREDIENT_NAME VARCHAR(100))
       READS SQL DATA
       BEGIN ATOMIC
         SELECT COUNT(*) 
         INTO INGREDIENTS_RATE 
         FROM INGREDIENT
         WHERE NAME LIKE '%' || INGREDIENT_NAME || '%'; 
       END
    '''
  ]

  static cookbooks = [
    [id: 1, title: "30-minute-meals", author: "Jamie Oliver", year: 2010],
    [id: 2, title: "Ministry of food", author: "Jamie Oliver", year: 2005],
  ]

  static chapters = [
    [id: 100, book_id: 1, title: "simple"],
    [id: 101, book_id: 1, title: "advanced"],
    [id: 200, book_id: 2, title: "simple"],
    [id: 201, book_id: 2, title: "advanced"],
  ]

  static recipes = [
    [id: 1000, chapter_id: 100, title: "steak indian-style",
      description: "TO START Get all your ingredients and equipment ready. Put a griddle pan on a high heat...",
      image: Sql.BLOB(new File("images/steak.jpg").bytes)],
    [id: 1001, chapter_id: 100, title: "mustard chicken",
      description: "TO START Get all your ingredients and equipment ready. Put a medium saucepan and a large ovenproof frying pan on a low heat...",
      image: Sql.BLOB(new File("images/chicken.jpg").bytes)],
  ]

  static ingredients = [
    [id: 10000, recipe_id: 1000, name: 'sugar', amount: 1, units: 'kg'],
    [id: 10001, recipe_id: 1000, name: 'sugar powder', amount: 1, units: 'kg'],
  ]

  static startServer() {
    Server server = new Server()
    server.setLogWriter(new PrintWriter(new File('db.log')))
    server.with {
      setDatabaseName(0, 'cookingdb')
      setDatabasePath(0, 'mem:cookingdb')
      start()
    }
    return server
  }

  static createSchema() {
    Sql sql = Sql.newInstance(dbSettings)
    ddls.each { ddl ->
      sql.execute(ddl)
    }
    sql.close()
    println 'Schema created successfully'
  }

  static populate() {

    Sql sql = Sql.newInstance(dbSettings)

    cookbooks.each { cookbook ->
      sql.execute('INSERT INTO COOKBOOK VALUES(:id, :title, :author, :year)', cookbook)
    }

    chapters.each { chapter ->
      sql.execute('INSERT INTO CHAPTER VALUES(:id, :book_id, :title)', chapter)
    }

    recipes.each { recipe ->
      sql.execute('INSERT INTO RECIPE VALUES(:id, :chapter_id, :title, :description, :image)', recipe)
    }

    ingredients.each { ingredient ->
      sql.execute('INSERT INTO INGREDIENT VALUES(:id, :recipe_id, :name, :amount, :units)', ingredient)
    }

    sql.close()
    println "Database populated: ok"
    
  }
  
}
