package org.groovy.cookbook.stm

import groovyx.gpars.actor.*
import java.util.Random
import groovyx.gpars.stm.GParsStm
import org.multiverse.api.references.IntRef
import static org.multiverse.api.StmUtils.newIntRef

class StmValueIncreaser {

  private int value = 0
  private final IntRef stmValue = newIntRef(0)

  // Message
  final class Increase {
  }


  final class ValueAccessActor extends DynamicDispatchActor {
    void onMessage(Increase message) {
      value++
      
      GParsStm.atomic {
        stmValue.increment();
      }

    }

  }

  def actors = [:]
  Random random = new Random()
  int max = 20

  Map start() {

    (1..20).each() {
      actors.put(it,new ValueAccessActor().start())
    }
    // spawn actors and increase counter
    (1..100).each() {
      actors.get(rnd(1,20)) << new Increase()
      //actors.get(1) << new Increase() // 
       
    }
    actors.values().collect {it.stop()}*.join()
    int stmProtected = 0
    GParsStm.atomic {
        stmProtected  = stmValue.get()
    }
    
    return ["withStm":stmProtected, "noStm":value]

  }

  def rnd = { from, to ->
    random.nextInt(to-from+1)+from
  }
}
