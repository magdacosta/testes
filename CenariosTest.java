package br.treinamento;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CenariosTest {
	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		driver.get("http://www.olx.com.br/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void cenario1() throws Exception {
		driver.findElement(By.linkText("Cadastre-se")).click();
		driver.findElement(By.id("username")).sendKeys("magdacostta");
		driver.findElement(By.id("email")).sendKeys("magda.costta@gmail.com");
		driver.findElement(By.id("password")).sendKeys("123456");
		driver.findElement(By.id("identity_type_1")).click();
		driver.findElement(By.id("agree_terms")).click();
		driver.findElement(By.id("btn-submit")).click();
		driver.close();
	}

	@Test
	public void cenario2() throws Exception {
		driver.findElement(By.xpath("//a[contains(text(),'CE')]")).click();
		driver.findElement(By.id("s-searchCell")).sendKeys("iphone 5s");
		driver.findElement(By.cssSelector("button.button2")).click();

		driver.findElement(By.id("s-searchCell")).clear();
		driver.findElement(By.id("s-searchCell")).sendKeys("galaxy s4");
		driver.findElement(By.cssSelector("button.button2")).click();

		driver.close();
	}

	@Test
	public void cenario3() throws Exception {
		driver.findElement(
				By.xpath("//div[@id='supercontainer']/div/span/a[3]/span"))
				.click();
		driver.findElement(By.id("email")).sendKeys("magda.costta@gmail.com");
		driver.findElement(By.id("submit2")).click();

		driver.findElement(By.id("username"))
				.sendKeys("magda.costta@gmail.com");
		driver.findElement(By.id("password")).sendKeys("123456");
		driver.findElement(By.id("submit")).click();

		driver.close();
	}

}
