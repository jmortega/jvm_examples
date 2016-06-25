package org.cookbook.groovy.spock

import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByLinkText;
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import spock.lang.Shared;

import static java.util.concurrent.TimeUnit.SECONDS
import spock.lang.Specification

class HomeSpecification extends Specification {

	static final def HOME = 'http://localhost:9966/petclinic'

	@Shared
	def driver = new HtmlUnitDriver(true)

	def setup() {
		driver.manage().timeouts().implicitlyWait 10, SECONDS
	}

	def "user enters home page"(){
		when:
			driver.get(HOME)
		then:
			driver.title == "PetClinic :: a Spring Framework demonstration"
	}

	def "user clicks on menus"(){
		when:
			driver.get(HOME)
			WebElement vets = driver.findElement(By.linkText("Veterinarians"))
			vets.click()

		then:
			driver.currentUrl == "http://localhost:9966/petclinic/vets.html"
	}


}