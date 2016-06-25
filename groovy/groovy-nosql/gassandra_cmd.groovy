@Grab('org.hectorclient:hector-core:1.1-2')
@GrabExclude('org.apache.httpcomponents:httpcore')
import me.prettyprint.hector.api.Cluster
import me.prettyprint.hector.api.factory.HFactory
import me.prettyprint.hector.api.Keyspace
import me.prettyprint.cassandra.serializers.*
import me.prettyprint.hector.api.Serializer
import me.prettyprint.hector.api.mutation.Mutator
import me.prettyprint.hector.api.ddl.*
import me.prettyprint.hector.api.beans.ColumnSlice

class Gassandra {
    
    def cluster 
    def keyspace
    def colFamily
    Serializer serializer
    def stringSerializer = StringSerializer.get()
    private Gassandra (Keyspace keyspace) {
        this.keyspace = keyspace
    }
    
    public Gassandra() {}

    void connect(clustername, host, port) {
      cluster = HFactory.getOrCreateCluster(clustername, "$host:$port")
    }

    List<KeyspaceDefinition> getKeyspaces() {
        cluster.describeKeyspaces()
    }

    Gassandra withKeyspace(_keyspace) {
      keyspace = HFactory.createKeyspace(_keyspace, cluster)
      new Gassandra(keyspace)        
    }

    Gassandra withColumnFamily(columnFamily, Serializer c) {
      colFamily =  columnFamily
      serializer = c
      this
    }

    Gassandra insert(key, columnName, value) {
      Mutator<String> mutator = HFactory.createMutator(keyspace, serializer)
      mutator.insert(key, colFamily, HFactory.createStringColumn(columnName, value))
      this
    }

    Gassandra insert(key, Map args) {
      Mutator<String> mutator = HFactory.createMutator(keyspace, serializer)
      args.each {
        mutator.insert(key, colFamily, HFactory.createStringColumn(it.key, it.value))
      }
      this
    }

    ColumnSlice findByKey(key)  {
      def sliceQuery = HFactory.createSliceQuery(keyspace, serializer, stringSerializer, stringSerializer)
      sliceQuery.setColumnFamily(colFamily).setKey(key)
      sliceQuery.setRange('', '', false, 100)             
      sliceQuery.execute().get()
    }

}


def g = new Gassandra()
// g.withColumnFamily('hellO')
g.connect('test', 'localhost', '9160')
g.keyspaces.each() {
    println it
}
def employee = g.withKeyspace('hr').withColumnFamily('employee', IntegerSerializer.get())
employee.insert(5005, 'name', 'Zoe')
employee.insert(5005, 'lastName', 'Ross')
employee.insert(5005, 'age', '31')

['name':'Zoe', 'lastName':'Ross', 'age':'31']

println employee.findByKey(5005)

// g.withKeyspace('hr').withColumnFamily('employee').insert('0003', ['name':'Bob','lastName':'Red', 'age':'20'])

