package org.groovy.cookbook

abstract class DatagramWorker {

  byte[] buffer = new byte[256]
  DatagramSocket socket = null
  String lastMessage = null

  def DatagramPacket receive() {
    def packet = new DatagramPacket(buffer, buffer.length)
    socket.receive(packet)
    lastMessage = new String(packet.data, 0, packet.length)
    println "RECEIVED: $lastMessage"
    return packet
  }

  def send(String message, InetAddress address, int port) {
    def output = new DatagramPacket(message.bytes, message.length(), address, port)
    socket.send(output)
  }

  def send(String message, DatagramPacket inputPacket) {
    send(message, inputPacket.address, inputPacket.port)
  }

  def send(InetAddress address, int port) {
    send("OK", address, port)
  }

  def send(DatagramPacket inputPacket) {
    send("OK", inputPacket.address, inputPacket.port)
  }
}
