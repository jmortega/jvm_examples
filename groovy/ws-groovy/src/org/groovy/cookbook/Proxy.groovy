@Grapes([
  @Grab('org.eclipse.jetty:jetty-servlet:7.4.5.v20110725'),
  @Grab('org.eclipse.jetty:jetty-security:7.4.5.v20110725'),
  @Grab('org.eclipse.jetty:jetty-servlets:7.4.5.v20110725'),
  @GrabExclude('org.eclipse.jetty.orbit:javax.servlet'),
  @Grab('javax.servlet:servlet-api:2.5')
])
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.servlet.ServletHandler
import org.eclipse.jetty.servlets.ProxyServlet
import org.eclipse.jetty.client.HttpExchange
import org.eclipse.jetty.client.HttpEventListener
import org.eclipse.jetty.io.Buffer

import javax.servlet.ServletConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest


Server httpProxy = new Server(8087)

def servletHandler = new ServletHandler()
servletHandler.addServletWithMapping(new ServletHolder(new TunnelProxyServlet()), "/*")

httpProxy.setHandler(servletHandler)
httpProxy.start()
httpProxy.join()


class TunnelProxyServlet extends ProxyServlet {

  public void init(ServletConfig config) throws ServletException {
    super.init(config)
    System.out.println("init done !")
  }

  public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    System.out.println("got a request !")
    super.service(req, res)
    // println res
  }

  protected void customizeExchange(HttpExchange exchange, HttpServletRequest request) {
    // exchange.setEventListener(new DebugEventListener())
  }
}

class DebugEventListener implements HttpEventListener {

  public void onResponseContent(Buffer content) {
    // content.writeTo(System.out)
    // println()
  }

  void  onConnectionFailed(Throwable ex) {
  }

  void  onException(Throwable ex) {
  }

  void  onExpire() {
  }

  void  onRequestCommitted() {
  }

  void  onRequestComplete() {
  }

  void  onResponseComplete() {
  }

  void  onResponseHeader(Buffer name, Buffer value) {
  }

  void  onResponseHeaderComplete() {
  }

  void  onResponseStatus(Buffer version, int status, Buffer reason) {
  }

  void  onRetry() {
  }
}

