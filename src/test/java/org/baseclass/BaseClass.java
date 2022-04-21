package org.baseclass;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	
	public static WebDriver driver;
	public static Actions a;
	public static Robot r;
	public static TakesScreenshot sc;
	public static JavascriptExecutor js;
	
	// 1. To Launch Chrome Browser
	public static void openChromeBrowser() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}
	
	// To Launch Firefox Browser
	public static void openFirefox() {

		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
	}
	
	// 2. To Maximize Browser Window
	public static void maxWindow() {

		driver.manage().window().maximize();
	}
	
	// 3. To Get Title
	public static void toFetchTitle() {

		String titleName = driver.getTitle();
		System.out.println(titleName);
	}
	
	// 4. To Get Current URL
	public static void toFetchUrl() {

		String url = driver.getCurrentUrl();
		System.out.println(url);
	}

	// 5. To Close the Browser
	public static void closeBrowser() {

		driver.quit();
	}
	
	// 6. To Fill Text box (SendKeys)
	public static void toFillTextBox(WebElement element, String data) {

		element.sendKeys(data);
	}

	// 7. To wait (Thread.Sleep)
	public static void holdProgram(int time) throws InterruptedException {

		Thread.sleep(time);
	}
	
	
	// 8. To Launch a URL
	public static void launchUrl(String url) {

		driver.get(url);
	}
	
	// 9. To Click a Button
	public static void toClickButton(WebElement ref) {

		ref.click();
	}

	// 10. To apply Implicit wait
	public static void applyImplicitWait(int time) {

		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	// 11. To Double Click
	public static void toDoubleClick(WebElement element) {

		a = new Actions(driver);
		a.doubleClick(element).perform();
	}

	// 12. To Right Click
	public static void toRightClick(WebElement ref) {

		a = new Actions(driver);
		a.contextClick(ref).perform();
	}
	
	// 13. To Drag and Drop
	public static void toDragAndDrop(WebElement dragElement, WebElement dropElement ) {
		a = new Actions(driver);
		a.dragAndDrop(dragElement, dropElement).perform();

	}
	
	// 14. To MouseOver
	public static void mouseOver(WebElement element) {
		a = new Actions(driver);
		a.moveToElement(element).perform();
	

	}
	
	
	// 15. To Copy 
	public static void copy() throws AWTException {

		r = new Robot();
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_C);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyRelease(KeyEvent.VK_C);

	}

	// 16. To Paste 
	public static void paste() throws AWTException {

		r = new Robot();
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyRelease(KeyEvent.VK_V);
	}
	// 17. To Take ScreenShot
	public static void takeScreenShot(String name) throws Exception  {
		File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    File des = new File("C:\\Users\\JYoga\\eclipse-workspace\\FrameWorkSample\\src\\test\\java\\org\\"+name+".png");
	    FileUtils.copyFile(src, des);
	} 
	// 18. JavaScript Executor
	public static void javaScriptExecutor(String data, WebElement element) {
		js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('value',(data)", element);
	}
	
	// 19. For Excel 
	public static String readExcel(String workBookName,String sheetName, int rowNo, int cellNo) throws IOException {
		
		// 1. File Location 
		File f = new File("D:\\Captcha\\Excel\\Login Details.xlsx");
		// 2. To Read
		FileInputStream fin = new FileInputStream(f);
		// 3. To get Workbooks
		Workbook b = new XSSFWorkbook(fin);
		// 4. To fetch Sheet 
		Sheet sh = b.getSheet(sheetName);
		// 5. To fetch a Row
		Row r = sh.getRow(rowNo);
		// 6. To fetch a Cell
		Cell c = r.getCell(cellNo);
		// To check Cell type
		int type = c.getCellType();
		String name;
		if (type == 1) {
			name = c.getStringCellValue();
		}
		else if (DateUtil.isCellDateFormatted(c)) {
		Date da = c.getDateCellValue();
		SimpleDateFormat sim = new SimpleDateFormat("dd/mm/yyyy");
		name = sim.format(da);
		}
		else {
			double d = c.getNumericCellValue();
			long l = (long)d;
			name = String.valueOf(l);
		}
		return name;
	}

	
 
	
}
