package org.groovy.cookbook

class MessageSender extends DatagramWorker {

  def send(String message) {
    socket = new DatagramSocket()
    send(message, InetAddress.getByName("localhost"), 12345)
    receive()
  }
  
}
