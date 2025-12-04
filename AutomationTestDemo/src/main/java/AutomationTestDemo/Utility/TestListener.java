package AutomationTestDemo.Utility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class TestListener implements ITestListener {

	private static ExtentReports extent = ExtentManager.getInstance();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
		test.set(extentTest);
		getLogger().info("🚀 Test started: " + result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		getLogger().pass("✅ Test Passed: " + result.getMethod().getMethodName());
		System.out.println("[TEST PASS] " + result.getMethod().getMethodName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		getLogger().fail("❌ Test Failed: " + result.getMethod().getMethodName());
		getLogger().fail(result.getThrowable());
		System.err.println("[TEST FAIL] " + result.getMethod().getMethodName() + " | Reason: " + result.getThrowable());

		try {
			WebDriver driver = WebUtility.getInstance().getDriver();
			String screenshotPath = captureScreenshot(driver, result.getMethod().getMethodName());
			if (!screenshotPath.isEmpty()) {
				getLogger().addScreenCaptureFromPath(screenshotPath);
			}
		} catch (Exception e) {
			System.err.println("⚠️ Failed to capture screenshot: " + e.getMessage());
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		getLogger().skip("⏭️ Test Skipped: " + result.getMethod().getMethodName());
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
		System.out.println("📄 Extent Report generated successfully.");
	}

	private String captureScreenshot(WebDriver driver, String testName) {
		String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		String screenshotDir = System.getProperty("user.dir") + "/Reports/Failed Snap";
		String screenshotPath = screenshotDir + "/" + testName + "_" + timestamp + ".png";

		try {
			if (driver == null)
				return "";
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File dest = new File(screenshotPath);
			dest.getParentFile().mkdirs();
			// Files.copy(src.toPath(), dest.toPath());
			return screenshotPath;
		} catch (Exception e) {
			System.err.println("❌ Screenshot capture failed: " + e.getMessage());
			return "";
		}
	}

	public static ExtentTest getLogger() {
		return test.get();
	}
}
