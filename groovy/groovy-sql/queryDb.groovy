import static DBUtil.*
import groovy.sql.Sql

def server = startServer()
createSchema()
populate()

println ": simple query"
def sql = Sql.newInstance(dbSettings)
sql.eachRow('SELECT * FROM COOKBOOK') { cookbook ->
  printf "%-20s%s\n", cookbook.id, cookbook[1]
}

println ": using named arguments"
sql.eachRow('SELECT * FROM COOKBOOK ' +
            'WHERE id = :id ',
            [id:2]) { cookbook ->

  printf "%s|%s|%s\n", cookbook.id, cookbook.author, cookbook.title
}

println ": using offset"
sql.eachRow('SELECT * FROM COOKBOOK', 1, 5) { cookbook ->
  printf "%s|%s|%s\n", cookbook.id, cookbook.author, cookbook.title
}

println ": using ResultSet"
sql.query('SELECT * FROM COOKBOOK') { rs ->
  while (rs.next()) {
    println rs.getString('title')
  }
}

println ": using rows method"
List allCookbooks = sql.rows('SELECT * FROM COOKBOOK')
assert allCookbooks.size() == 2
println allCookbooks[0]?.title

println ": querying for BLOB/CLOB"
sql.eachRow('SELECT * FROM RECIPE') { recipe ->
  println recipe.description.characterStream.text
  def recipeImage = new File("recipe-${recipe.id}.jpg")
  recipeImage.delete()
  recipeImage << recipe.image.binaryStream

}

sql.close()
server.stop()