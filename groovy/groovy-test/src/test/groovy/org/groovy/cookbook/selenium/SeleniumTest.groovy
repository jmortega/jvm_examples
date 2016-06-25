package org.groovy.cookbook.selenium

import static org.junit.Assert.*

import org.groovy.cookbook.server.*
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.support.ui.WebDriverWait

import com.google.common.base.Function

class SeleniumTest {

	def static server;
	static HtmlUnitDriver driver;
	@BeforeClass
	public static void setUp(){
		server = App.init(8080)
		server.startAndWait()
		driver =  new HtmlUnitDriver(true);
	}

	@AfterClass
	public static void tearDown(){
		if(server.isRunning()){
			server.stop();
		}
		//driver.quit();
	}


	@Test
	public void testWelcomePage(){
		driver.get("http://localhost:5050");
		def title = driver.getTitle()
		assertEquals("welcome",title)
	}

	@Test
	public void testFormPost(){
		driver.get("http://localhost:5050/form");
		def title = driver.getTitle()
		assertEquals("test form",title)

		WebElement input = driver.findElement(By.name("username"))
		input.sendKeys("test")
		input.submit();

		WebDriverWait wait = new WebDriverWait(driver, 4);
		WebElement span = wait.until(new Function<WebDriver, WebElement>() {
					public WebElement apply(WebDriver driver) {
						return driver.findElement(By.className("hello"));
					}
				});
		title = driver.getTitle();
		assertEquals("oh hello,test",title)




	}


}