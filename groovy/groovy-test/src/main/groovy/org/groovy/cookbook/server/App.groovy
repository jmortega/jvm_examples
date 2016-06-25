package org.groovy.cookbook.server
import org.ratpackframework.bootstrap.RatpackServer;
import org.ratpackframework.groovy.RatpackScriptApp
/**
 * This is cool!
 */
class App {
	public static RatpackServer init(int port){
		return RatpackScriptApp.ratpack(new File("./src/ratpack/ratpack.groovy"));
	}
}