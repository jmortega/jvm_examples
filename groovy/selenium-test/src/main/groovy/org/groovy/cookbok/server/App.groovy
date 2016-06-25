package org.groovy.cookbook.server
import org.ratpackframework.bootstrap.RatpackServer 
import org.ratpackframework.groovy.RatpackScriptApp

class App {
	static RatpackServer init(){
		RatpackScriptApp.ratpack(new File("./src/ratpack/ratpack.groovy"));
	}
	
}