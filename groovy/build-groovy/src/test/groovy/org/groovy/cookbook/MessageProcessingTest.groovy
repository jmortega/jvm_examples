package org.groovy.cookbook

import org.junit.Test

class MessageProcessingTest {

  MessageReceiver reciever = new MessageReceiver()
  MessageSender sender = new MessageSender()
  
  @Test
  public void testMessages() throws Exception {
    reciever.start()     
    sender.send('HELLO')
    assert reciever.lastMessage == 'HELLO'
    assert sender.lastMessage == 'OK'
  }
  
}
