package org.captcha;

import java.io.File;
import java.io.IOException;

import org.baseclass.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Captcha extends BaseClass {

	public static void main(String[] args) throws IOException, TesseractException, InterruptedException {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.irctc.co.in/nget/train-search");
		driver.findElement(By.xpath("/html/body/app-root/app-home/div[1]/app-header/p-dialog[2]/div/div/div[2]/div/form/div[2]/button")).click();
		driver.findElement(By.xpath("//a[text()=' LOGIN ']")).click();
		
		Thread.sleep(5000);
		
		WebElement userName = driver.findElement(By.xpath("//input[@placeholder='User Name']"));
		toFillTextBox(userName, readExcel("Login Details", "Sheet1", 1, 0));
				
		WebElement password = driver.findElement(By.xpath("//input[@placeholder='Password']"));
		toFillTextBox(password, readExcel("Login Details", "Sheet1", 1, 1));
		
		String pass = readExcel("Login Details", "Sheet1", 1, 1);
		System.out.println(pass);
		
		File src = driver.findElement(By.xpath("(//img[@border='0'])[2]")).getScreenshotAs(OutputType.FILE);
		
		String path = "D:\\Captcha\\screenshots\\captcha.png";
		
		FileHandler.copy(src, new File(path));
		
		ITesseract img = new Tesseract();
		
		String str = img.doOCR(new File(path));
		
		System.out.println(str);
		
		String captcha = str.split("below")[1].replaceAll("[^a-zA-Z0-9]", "");
		
		driver.findElement(By.id("nlpAnswer")).sendKeys(captcha);
		
		driver.findElement(By.xpath("//button[text()='SIGN IN']")).click();
		
		WebElement from = driver.findElement(By.xpath("(//input[@aria-activedescendant='p-highlighted-option'])[1]"));
		toFillTextBox(from, readExcel("Login Details", "Book Ticket", 1, 0));
		
		WebElement to = driver.findElement(By.xpath("(//input[@aria-activedescendant='p-highlighted-option'])[2]"));
		toFillTextBox(to, readExcel("Login Details", "Book Ticket", 1, 1));
		
		WebElement date = driver.findElement(By.xpath("(//input[@type='text'])"));
		date.click();
		
		driver.findElement(By.xpath("//span[@class='ui-datepicker-next-icon pi pi-chevron-right ng-tns-c59-10']")).click();
		
		driver.findElement(By.xpath("//a[text()='31']")).click();
		
	}

}
