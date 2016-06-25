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
import org.openqa.selenium.support.ui.ExpectedConditions

import com.google.common.base.Function

class SeleniumTest {

	static server
	static HOST = 'http://localhost:5050'
	static HtmlUnitDriver driver

	@BeforeClass
	public static void setUp() {
		server = App.init()
		server.startAndWait()
		driver =  new HtmlUnitDriver(true)
	}

	@AfterClass
	public static void tearDown() {
		if(server.isRunning()){
			server.stop()
		}
	}


	@Test
	void testWelcomePage() {
		driver.get(HOST)
		def title = driver.getTitle()
		assertEquals('welcome',title)
	}

	@Test
	void testFormPost() {
		driver.get("${HOST}/form")
		def title = driver.getTitle()
		assertEquals('test form',title)

		WebElement input = driver.findElement(By.name('username'))
		input.sendKeys('test')
		input.submit()

		def wait = new WebDriverWait(driver, 4)
		wait.until ExpectedConditions.presenceOfElementLocated (By.className('hello'))


		title = driver.getTitle();
		assertEquals('oh hello,test',title)


	}

}