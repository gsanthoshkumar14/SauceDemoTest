package testPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SauceDemoTest {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("https://www.saucedemo.com/");
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		int productCount = driver.findElements(By.xpath("//div[@class='inventory_item_price']")).size();
		ArrayList<Double> priceTagList = new ArrayList<Double>();
		for(int i=0;i<productCount;i++) {
			priceTagList.add(Double.parseDouble(driver.findElements(By.xpath("//div[@class='inventory_item_price']")).get(i).getText().replace("$", "")));
		}
		Collections.sort(priceTagList, Collections.reverseOrder());
		double highestPrice = priceTagList.get(0);
		String highPrice = String.valueOf(highestPrice);
		String xpath = "//div[@class='pricebar']//div[contains(text()[2],'"+highPrice+"')]/ following-sibling::button";
		driver.findElement(By.xpath(xpath)).click();
		driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click();
		String selectedItemPrice = driver.findElement(By.xpath("//div[@class='inventory_item_price']")).getText();
		System.out.println(selectedItemPrice);
		driver.quit();
	}

}
