package org.groovy.cookbook.dbunit

import static org.junit.Assert.*
import groovy.sql.Sql
import groovy.xml.StreamingMarkupBuilder
import org.dbunit.JdbcDatabaseTester
import org.dbunit.database.*
import org.dbunit.dataset.xml.*
import org.junit.*

class DbUnitTest  {

  def tester
  final static DB_DRIVER = 'org.hsqldb.jdbcDriver'

  final static DB_CONFIG = ['jdbc:hsqldb:db/sample', 'sa', '']

  @BeforeClass
	static void schema(){
		Sql.newInstance(*DB_CONFIG, DB_DRIVER).execute('''
			drop table if exists post;
			drop table if exists author;
			create table author(id integer primary key,
                name varchar(500)
			);

			create table post(
				id integer primary key,
		                title varchar(500),
                		text longvarchar,
                		author integer not null,
                		foreign key (author) references author(id)
			);
		''')
	}

	@Before
	void setUp(){
		tester = new JdbcDatabaseTester(DB_DRIVER, *DB_CONFIG)
	}


  @Test
  void testSelectWithId() {
    tester.dataSet = dataSet {
      author id:1, name:'Admin'
      post id:1,
           author:1, title:'Testing db with dbunit is easy',
			     text:'How to use DBUnit to rock your tests'

    }
    tester.onSetup()

    def row = firstRow('select id,title,text from post where id =?', [1])

    assertEquals(1,row.id)
		assertEquals('Testing db with dbunit is easy',row.title)
	}

	@Test
	void testSelectWithAuthorName(){
    tester.dataSet = dataSet {
      author id:1, name:'testName'
      post id:1,
      author:1, title:'Testing a sql join',
			text:'some text...'
    }
    tester.onSetup()

    def row = firstRow('select p.id,p.title,p.text from post p left join author a on a.id = p.author where a.name =?', 
      ['testName'])
    assertEquals(1,row.id)
		assertEquals('some text...',row.text)
	}



	def dataSet(dataClosure) {
		new FlatXmlDataSet(new StringReader(new StreamingMarkupBuilder().bind{dataset dataClosure}.toString()))
	}

  def firstRow(SQL, List args) {
    Sql.newInstance(*DB_CONFIG, DB_DRIVER).firstRow(SQL, args)
  }


}
