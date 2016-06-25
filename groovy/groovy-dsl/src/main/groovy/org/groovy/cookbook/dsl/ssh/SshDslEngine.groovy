package org.groovy.cookbook.dsl.ssh

import com.jcraft.jsch.JSch


class SshDslEngine {

  private final JSch jsch
  private RemoteSession delegate

  SshDslEngine()  {
    JSch.setConfig("HashKnownHosts",  "yes")
    JSch.setConfig("StrictHostKeyChecking", "no")
    this.jsch = new JSch()
  }

  def remoteSession(Closure cl) {
    if (cl != null) {
      delegate = new RemoteSession(jsch)
      cl.delegate = delegate
      cl.resolveStrategy = Closure.DELEGATE_FIRST
      cl()
      if (delegate.session != null && delegate.session.connected) {
        try {
          delegate.session.disconnect()
        } catch (Exception e) {
        }
      }
    }
  }
}

