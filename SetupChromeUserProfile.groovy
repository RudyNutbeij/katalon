import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext


import com.kms.katalon.core.webui.driver.DriverFactory

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

import org.apache.commons.io.FileUtils

class ChromeProfielTestSuite {
	/**
	 * Executes before every test suite starts.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@BeforeTestSuite
	def createTmpChromeUserProfile(TestSuiteContext testSuiteContext) {
		// creates new user profiles for each test suite
		// useful for parallel testing
		def chromeDriverPath =  DriverFactory.getChromeDriverPath();
		System.setProperty("webdriver.chrome.driver", chromeDriverPath.toString())
		
		def testSuiteId = testSuiteContext.getTestSuiteId();
		def tmpPath = new File(System.getProperty("java.io.tmpdir")); 
		def tmpChromeProfilePath = new File(tmpPath, testSuiteId);

		File tmpChromeUserProfile = new File(tmpChromeProfilePath, 'User Data');
		tmpChromeUserProfile.mkdirs()
		FileUtils.cleanDirectory(tmpChromeUserProfile);
		
		def chromePath = DriverFactory.getChromeDriverPath();
		String chromeProfilePath = tmpChromeUserProfile.getCanonicalPath();
		
		ChromeOptions chromeProfile = new ChromeOptions();
		chromeProfile.addArguments("--user-data-dir=${chromeProfilePath}");
		chromeProfile.addArguments("--incognito");
		chromeProfile.addArguments("--no-first-run");

		
		WebDriver driver = new ChromeDriver(chromeProfile);
		DriverFactory.changeWebDriver(driver)
	}

	@BeforeTestCase
	def openBrowserVoorTestCase(TestCaseContext testCaseContext) {
		try {
			WebUI.maximizeWindow()
		} catch(BrowserNotOpenedException) {
			WebUI.openBrowser('')
			WebUI.maximizeWindow()
		}

	}
}
