package org.groovy.cookbook.dsl.ssh

import static org.junit.Assert.*

import org.junit.Test

import com.aestasit.ssh.mocks.MockSshServer

public class SshDslTest {

  @Test
  public void testDsl() {
    MockSshServer.command('^.*$') { inp, out, err, callback, env ->
      // out << "I'm a command!"
      callback.onExit(0)
    }
    MockSshServer.startSshd(3223)
    def engine = new SshDslEngine()
    engine.remoteSession {

      url = 'root:secret123@localhost:3223'

      if (exec('rpm -qa | grep groovy').exitStatus != 0) {
        exec 'yum --assumeyes install groovy'
      }
      exec 'groovy -e "println \'Hello, Remote!\'"'
    }
  }
}
