@Grapes([
  @Grab('org.eclipse.jetty:jetty-servlet:7.4.5.v20110725'),
  @Grab('org.eclipse.jetty:jetty-security:7.4.5.v20110725'),
  @GrabExclude('org.eclipse.jetty.orbit:javax.servlet'),
  @Grab('javax.servlet:servlet-api:2.5')
])
import org.eclipse.jetty.security.ConstraintMapping
import org.eclipse.jetty.security.ConstraintSecurityHandler
import org.eclipse.jetty.http.security.Constraint
import org.eclipse.jetty.http.security.Credential
import org.eclipse.jetty.security.HashLoginService
import org.eclipse.jetty.security.SecurityHandler
import org.eclipse.jetty.security.authentication.BasicAuthenticator
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class SecureWebService extends HttpServlet {

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.contentType = "text/plain"
    resp.writer.print("Hello from Groovy!\n")
  }

}

def SecurityHandler basicAuth(String username, String password, String realm) {

  HashLoginService l = new HashLoginService()
  l.putUser(username, Credential.getCredential(password), ["user"] as String[])
  l.setName(realm)

  Constraint constraint = new Constraint()
  constraint.setName(Constraint.__BASIC_AUTH)
  constraint.setRoles(["user"] as String[])
  constraint.setAuthenticate(true)

  ConstraintMapping cm = new ConstraintMapping()
  cm.setConstraint(constraint)
  cm.setPathSpec("/*")

  ConstraintSecurityHandler csh = new ConstraintSecurityHandler()
  csh.setAuthenticator(new BasicAuthenticator())
  csh.setRealmName("myrealm")
  csh.addConstraintMapping(cm)
  csh.setLoginService(l)

  return csh

}


Server server = new Server(5000)

ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS)
context.setSecurityHandler(basicAuth("groovy", "cookbook", "Private!"))
context.setContextPath("/")
server.setHandler(context)
context.addServlet(new ServletHolder(new SecureWebService()), "/*")
server.start()
server.join()

