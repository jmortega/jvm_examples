import static org.ratpackframework.groovy.RatpackScript.ratpack
import groovy.json.JsonBuilder;
import groovy.json.JsonSlurper
import org.ratpackframework.groovy.templating.TemplateRenderer
import io.netty.handler.codec.http.HttpHeaders

ratpack {
	routing {
		/*Selenium test*/
		get("") {
			get(TemplateRenderer).render "hello.html"
		}

		get("form") {
			get(TemplateRenderer).render "form.html"

		}

		post("formpost") {
			Map<String,?> model = new HashMap<String,?>();
			model.put("username", request.form.username.first())
			get(TemplateRenderer).render(model,"post.html")
		}

		assets "public"
	}
}