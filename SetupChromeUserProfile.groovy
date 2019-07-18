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

// om de tijdelijke chrome profielen te verwijderen
import org.apache.commons.io.FileUtils


class ChromeProfielTestSuite {
	/**
	 * Executes before every test suite starts.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@BeforeTestSuite
	def createTmpChromeUserProfile(TestSuiteContext testSuiteContext) {
		// maakt tijdelijkse user profiles aan voor Chrome per test suite
		// cleaned achter gebleven user profiles evt voor initialisatie
		def chromeDriverPath =  DriverFactory.getChromeDriverPath();
		System.setProperty("webdriver.chrome.driver", chromeDriverPath.toString())
		
		def tmpPath = new File(System.getProperty("java.io.tmpdir")); 
		def tmpChromeProfilePath = new File(tmpPath, testSuiteId);

		File tmpChromeUserProfile = new File(tmpChromeProfilePath, 'User Data');
		String chromeProfilePath = tmpChromeUserProfile.getCanonicalPath();

		tmpChromeUserProfile.mkdirs()
		FileUtils.cleanDirectory(tmpChromeUserProfile);
		
		ChromeOptions chromeProfile = new ChromeOptions();
		chromeProfile.addArguments("--user-data-dir=${chromeProfilePath}");
		chromeProfile.addArguments("--incognito");
		chromeProfile.addArguments("--no-first-run");
		chromeProfile.addArguments("--disable-infobars");
		
		WebDriver driver = new ChromeDriver(chromeProfile);
		
		DriverFactory.changeWebDriver(driver)
	}

	@BeforeTestCase
	def openBrowserVoorTestCase(TestCaseContext testCaseContext) {
		try {
			WebUI.maximizeWindow()
		} catch(BrowserNotOpenedException) {
			WebUI.openBrowser('www.topicus.nl')
			WebUI.maximizeWindow()
		}

	}
}
