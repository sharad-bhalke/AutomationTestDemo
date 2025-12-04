package AutomationTestDemo.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebUtility {
	private static WebUtility webUtil = null;
	private WebDriver driver;
	private Properties prop;
	private String env;
	private String url;
	private Workbook workbook;

	private WebUtility() {
		loadConfig();
		setupBrowser();
	}

	public static WebUtility getInstance() {
		if (webUtil == null) {
			webUtil = new WebUtility();
		}
		return webUtil;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
			webUtil = null;
		}
	}

	/* ============================= CONFIG ============================= */
	private void loadConfig() {
		try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
			prop = new Properties();
			prop.load(input);
			String sysEnv = System.getProperty("env");
			if (sysEnv != null && !sysEnv.isBlank()) {
				env = sysEnv.trim().toLowerCase();
				logInfo("✅ Environment set from System property/TestNG: " + env);
			} else {
				env = prop.getProperty("env", "qa").trim().toLowerCase();
				logInfo("✅ Environment set from config.properties (default): " + env);
			}
			url = prop.getProperty(env + ".url");
			if (url == null || url.isBlank()) {
				throw new RuntimeException("❌ URL not found for environment: " + env);
			} else {
				logInfo("🌍 Loaded URL for " + env + ": " + url);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("❌ Could not load config.properties file.");
		}
	}

	/* ============================= BROWSER ============================= */
	public void launchBrowser() {
		try {
			driver.get(url);
			acceptAlert();
			logInfo("🌐 Browser launched and navigated to: " + url);
		} catch (Exception e) {
			logError("❌ ERROR: Failed to launch browser for URL: " + url + " | Reason: " + e.getMessage());
			Assert.fail("❌ Browser launch failed: " + e.getMessage());
		}
	}

	public String getConfig(String key) {
		return prop.getProperty(env + "." + key, "").trim();
	}

	private void setupBrowser() {
		String browser = prop.getProperty("browser", "chrome").trim().toLowerCase();
		switch (browser) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		default:
			Assert.fail("❌ Unsupported browser in config.properties: " + browser);
		}
		driver.manage().window().maximize();
		setImplicitWait(Integer.parseInt(prop.getProperty("timeout", "10")));
	}

	/*
	 * ============================= ELEMENT INTERACTIONS =========
	 */
	private void highlightElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='2px solid red'", element);
	}

	public void clickElement(WebElement element) {
		try {
			highlightElement(element);
			element.click();
			logInfo("🖱️ Clicked element: " + element);
		} catch (Exception e1) {
			logInfo("⚠️ Normal click failed, trying JS click...");
			try {
				clickWithJS(element);
			} catch (Exception e2) {
				logInfo("⚠️ JS click failed, trying Actions...");
				try {
					clickWithActions(element);
				} catch (Exception e3) {
					logError("❌ All click methods failed: " + e3.getMessage());
					Assert.fail("❌ Failed to click element: " + e3.getMessage());
				}
			}
		}
	}

	public void clickWithJS(WebElement element) {
		highlightElement(element);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		logInfo("✅ JS click performed.");
	}

	// --------------------clear with JS------------
	public void clearElement(WebElement element) {
		try {
			highlightElement(element);
			element.clear();
			logInfo("🖱️ Clear element: " + element);
		} catch (Exception e1) {
			logInfo("⚠️ Normal clear failed, trying JS clear...");
			try {
				clearWithJS(element);
			} catch (Exception e2) {
				logInfo("⚠️ JS clear failed, trying Actions...");
//				try {
//					clearWithActions(element);
//				} catch (Exception e3) {
//					logError("❌ All click methods failed: " + e3.getMessage());
//					Assert.fail("❌ Failed to click element: " + e3.getMessage());
//				}
			}
		}
	}

	public void clearWithJS(WebElement element) {
		highlightElement(element);
		((JavascriptExecutor) driver).executeScript("arguments[0].clear", element);
		logInfo(" JS Clear");
	}

	// ------------------------Element Display----------------------
	public boolean isElementDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void waitForElementVisible(WebElement element, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void clickWithActions(WebElement element) {
		highlightElement(element);
		new Actions(driver).moveToElement(element).click().perform();
		logInfo("✅ Actions click performed.");
	}

	public void sendKeys(WebElement element, String text) {
		try {
			highlightElement(element);
			element.clear();
			element.sendKeys(text);
			logInfo("⌨️ Sent keys: " + text);
		} catch (Exception e) {
			logError("❌ Failed to send keys: " + e.getMessage());
			Assert.fail("SendKeys Failed: " + e.getMessage());
		}
	}

	public String getText(WebElement element) {
		try {
			highlightElement(element);
			String txt = element.getText();
			logInfo("📋 Fetched text: " + txt);
			return txt;
		} catch (Exception e) {
			logError("❌ Failed to get text: " + e.getMessage());
			Assert.fail("Get Text Failed: " + e.getMessage());
			return "";
		}
	}

	public void navigateTo(String url) {
		try {
			driver.get(url);
			logInfo("🔗 Navigated to: " + url);
		} catch (Exception e) {
			logError("❌ Navigation failed: " + e.getMessage());
		}
	}

	/* ============================= ALERT HANDLING ============================= */
	public void acceptAlert() {
		if (waitForAlert(3)) {
			driver.switchTo().alert().accept();
			logInfo("✅ Alert accepted successfully.");
		} else {
			logInfo("ℹ️ No alert present to accept.");
		}
	}

	public void dismissAlert() {
		if (waitForAlert(3)) {
			driver.switchTo().alert().dismiss();
			logInfo("✅ Alert dismissed successfully.");
		} else {
			logInfo("ℹ️ No alert present to dismiss.");
		}
	}

	public String getAlertText() {
		if (waitForAlert(3)) {
			String alertText = driver.switchTo().alert().getText();
			logInfo("📋 Alert text fetched: " + alertText);
			return alertText;
		} else {
			logInfo("ℹ️ No alert present to fetch text.");
			return "";
		}
	}

	public void sendTextToAlert(String text) {
		if (waitForAlert(3)) {
			driver.switchTo().alert().sendKeys(text);
			logInfo("📨 Text sent to alert: " + text);
		} else {
			logInfo("ℹ️ No alert present to send text.");
		}
	}

	/* ============================= IFRAMES ============================= */
	public void switchToIframeByElement(WebElement iframe) {
		try {
			driver.switchTo().frame(iframe);
			logInfo("✅ Switched to iframe by WebElement.");
		} catch (Exception e) {
			logError("❌ Failed to switch to iframe by WebElement: " + e.getMessage());
		}
	}

	public void switchToIframeByIndex(int index) {
		try {
			driver.switchTo().frame(index);
			logInfo("✅ Switched to iframe by index: " + index);
		} catch (Exception e) {
			logError("❌ Failed to switch to iframe by index: " + e.getMessage());
		}
	}

	public void switchToIframeByNameOrId(String nameOrId) {
		try {
			driver.switchTo().frame(nameOrId);
			logInfo("✅ Switched to iframe by name/ID: " + nameOrId);
		} catch (Exception e) {
			logError("❌ Failed to switch to iframe by name/ID: " + e.getMessage());
		}
	}

	public void switchToDefaultContent() {
		try {
			driver.switchTo().defaultContent();
			logInfo("✅ Switched back to default content.");
		} catch (Exception e) {
			logError("❌ Failed to switch to default content: " + e.getMessage());
		}
	}

	/* ============================= WAITS ============================= */
	public void setImplicitWait(int seconds) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
		logInfo("⏳ Implicit wait set to " + seconds + " sec.");
	}

	public boolean waitForAlert(int timeoutSec) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public WebElement waitForElementVisible(By locator, int timeoutSec) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
			WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			logInfo("✅ Element visible: " + locator);
			return ele;
		} catch (Exception e) {
			logError("❌ Wait failed for visibility: " + e.getMessage());
			return null;
		}
	}

	public WebElement fluentWait(By locator, int timeoutSec, int pollingSec) {
		try {
			Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeoutSec))
					.pollingEvery(Duration.ofSeconds(pollingSec)).ignoring(NoSuchElementException.class);
			WebElement ele = wait.until(d -> d.findElement(locator));
			logInfo("✅ Fluent wait successful: " + locator);
			return ele;
		} catch (Exception e) {
			logError("❌ Fluent wait failed: " + e.getMessage());
			return null;
		}
	}

	public void sleep(int sec) {
		try {
			Thread.sleep(sec * 1000);
			logInfo("⏱️ Sleep for " + sec + " seconds");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/* ============================= EXCEL HANDLING ============================= */
	private void loadExcel(String excelPath) {
		try {
			File file = new File(excelPath);
			if (!file.exists()) {
				throw new FileNotFoundException("Excel file not found at: " + file.getAbsolutePath());
			}
			FileInputStream fis = new FileInputStream(file);
			workbook = new XSSFWorkbook(fis);
			logInfo("✅ Excel file loaded successfully: " + file.getAbsolutePath());
			fis.close();
		} catch (Exception e) {
			logError("❌ Failed to load Excel file: " + e.getMessage());
			throw new RuntimeException("Excel load failed: " + e.getMessage());
		}
	}

	public Object[][] getSheetData(String sheetName, String path) {
		try {
			if (workbook == null) {
				loadExcel(path);
			}
			Sheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				throw new RuntimeException("Sheet not found: " + sheetName);
			}
			int rowCount = sheet.getPhysicalNumberOfRows();
			if (rowCount < 2) {
				logError("❌ No data rows found in sheet: " + sheetName);
				return new Object[0][0];
			}
			Row headerRow = sheet.getRow(0);
			if (headerRow == null) {
				logError("❌ Header row is empty in sheet: " + sheetName);
				return new Object[0][0];
			}
			// int colCount = headerRow.getPhysicalNumberOfCells();
			int colCount = headerRow.getLastCellNum();
			Object[][] data = new Object[rowCount - 1][colCount];
			DataFormatter formatter = new DataFormatter();
			for (int i = 1; i < rowCount; i++) {
				Row row = sheet.getRow(i);
				if (row == null)
					continue;
				for (int j = 0; j < colCount; j++) {
					Cell cell = row.getCell(j);
					data[i - 1][j] = (cell == null) ? "" : formatter.formatCellValue(cell);
				}
			}
			logInfo("✅ Excel data read successfully from sheet: " + sheetName);
			return data;
		} catch (Exception e) {
			logError("❌ Failed to read Excel sheet data: " + e.getMessage());
			return new Object[0][0];
		}
	}

	/*
	 * ============================= GET SINGLE CELL =============================
	 */
	public String getCellValue(String sheetName, int rowIndex, int colIndex, String path) {
		try {
			if (workbook == null) {
				loadExcel(path);
			}
			Sheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				throw new RuntimeException("Sheet not found: " + sheetName);
			}
			Row row = sheet.getRow(rowIndex);
			if (row == null)
				return "";
			Cell cell = row.getCell(colIndex);
			if (cell == null)
				return "";
			DataFormatter formatter = new DataFormatter();
			return formatter.formatCellValue(cell);
		} catch (Exception e) {
			System.out.println("❌ Failed to read cell: " + e.getMessage());
			return "";
		}
	}

	/* ============================= LOGGER ============================= */
	public void logInfo(String message) {
		try {
			if (TestListener.getLogger() != null) {
				TestListener.getLogger().info(message);
			}
		} catch (Exception e) {
			System.out.println("Logger error: " + e.getMessage());
		}
		System.out.println("[LOG] " + message);
	}

	public void logError(String message) {
		try {
			if (TestListener.getLogger() != null) {
				TestListener.getLogger().fail(message);
			}
		} catch (Exception e) {
			System.out.println("Logger error: " + e.getMessage());
		}
		System.err.println("[ERROR] " + message);
	}

	// ======================TrimmedSheetData============================
	public Object[][] getTrimmedSheetData(String sheetName, String filePath) {
		try (FileInputStream fis = new FileInputStream(filePath); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

			XSSFSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				logError("❌ Sheet not found: " + sheetName);
				return new Object[0][0];
			}

			int rows = sheet.getPhysicalNumberOfRows();
			int cols = sheet.getRow(0).getLastCellNum();

			List<Object[]> dataList = new ArrayList<>();

			for (int i = 1; i < rows; i++) { // skip header row
				XSSFRow row = sheet.getRow(i);
				if (row == null)
					continue;

				Object[] rowData = new Object[cols];
				boolean emptyRow = true;

				for (int j = 0; j < cols; j++) {
					XSSFCell cell = row.getCell(j);
					String value = (cell == null) ? "" : cell.toString().trim(); // 🔥 auto-trim
					rowData[j] = value;

					if (!value.isBlank())
						emptyRow = false;
				}

				if (!emptyRow)
					dataList.add(rowData);
			}

			return dataList.toArray(new Object[0][]);

		} catch (Exception e) {
			logError("❌ Error reading trimmed Excel data: " + e.getMessage());
			return new Object[0][0];
		}
	}

	// ====================================Scroll Down=========================
	public void scrollAndClick(WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", element);
			clickElement(element);
			System.out.println("Scrolled to and clicked element: " + element);
		} catch (Exception e) {
			System.out.println("Failed to scroll and click element: " + element + " - " + e.getMessage());
			throw e;
		}
	}

//	// =============================Handle Tooltip================
//	public String getHTML5ValidationMessage(WebElement element) {
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		return (String) js.executeScript("return arguments[0].validationMessage;", element);
//	}

	// =============Check Box Selected=================================
	public boolean isSelected(WebElement element) {
		try {
			return element.isSelected();
		} catch (Exception e) {
			logInfo("⚠️ Unable to check selected state: " + e.getMessage());
			return false;
		}
	}

	public void selectIfNotSelected(WebElement element) {
		if (!isSelected(element)) {
			clickElement(element);
			logInfo("Selected: " + element);
		} else {
			logInfo("Already selected: " + element);
		}
	}

	public void deselectIfSelected(WebElement element) {
		if (isSelected(element)) {
			clickElement(element);
			logInfo("De-selected: " + element);
		} else {
			logInfo("Already de-selected: " + element);
		}
	}
	public void selectByVisibleText(WebElement element, String visibleText) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOf(element));
	    wait.until(ExpectedConditions.elementToBeClickable(element));

	    Select select = new Select(element);
	    select.selectByVisibleText(visibleText);

	    logInfo("Selected dropdown value: " + visibleText);
	}	
}
