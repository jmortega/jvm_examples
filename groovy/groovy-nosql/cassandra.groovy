@Grab('org.hectorclient:hector-core:1.1-2')
@GrabExclude('org.apache.httpcomponents:httpcore')
import me.prettyprint.hector.api.Cluster
import me.prettyprint.hector.api.factory.HFactory
import me.prettyprint.hector.api.Keyspace
import me.prettyprint.cassandra.serializers.*
import me.prettyprint.hector.api.mutation.Mutator
import me.prettyprint.hector.api.query.MultigetSliceQuery
import me.prettyprint.hector.api.query.QueryResult
import me.prettyprint.hector.api.beans.ColumnSlice
import me.prettyprint.hector.api.query.SliceQuery

def stringSerializer = StringSerializer.get()
  
Cluster cluster = HFactory.getOrCreateCluster('Test Cluster', 'localhost:9160')
cluster.describeKeyspaces().each {
  println it
}

// Create a keyspace object from the existing keyspace we created using CLI
Keyspace keyspace = HFactory.createKeyspace('hr', cluster)

// Create a mutator object for this keyspace using utf-8 encoding
Mutator<Integer> mutator = HFactory.createMutator(keyspace, IntegerSerializer.get())

// Use the mutator object to insert a column and value pair to an existing key
// mutator.insert(931233, 'employee', HFactory.createStringColumn('name', 'Shithead'))
// mutator.insert('0002', 'employee', HFactory.createStringColumn('lastName', 'Slick'))

SliceQuery<String, String, String> sliceQuery = HFactory.createSliceQuery(keyspace, IntegerSerializer.get(), stringSerializer, stringSerializer)
sliceQuery.setColumnFamily('employee').setKey(5003)
sliceQuery.setRange('', '', false, 100)
             
QueryResult<ColumnSlice<String, String>> result = sliceQuery.execute()
System.out.println("\nInserted data is as follows:\n" + result.get()) 

println IntegerSerializer.get().class.name

