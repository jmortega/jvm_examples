package org.groovy.cookbook

class MessageReceiver extends DatagramWorker {
  
  def start() {
    socket = new DatagramSocket(12345)
    Thread.startDaemon {      
      while(true) { 
        send(receive())
      }
    }
  }

}
