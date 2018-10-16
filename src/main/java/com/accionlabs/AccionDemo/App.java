package com.accionlabs.AccionDemo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

/**
 * Hello world!
 *
 */

/**
 * @author AL1984
 *
 */
/**
 * @author AL1984
 *
 */
public class App {
	public static void main(String[] args) throws MalformedURLException, InterruptedException {

		// C:\Users\al1984\AppData\Local\Android\Sdk
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("automationName", "Appium");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("deviceName", "emulator-5554");
		capabilities.setCapability("app", "D:\\app-ama-release.apk");
		capabilities.setCapability("packageName", "com.thinkrite.assistant");
		capabilities.setCapability("appActivity", "com.thinkrite.assistant.wizard.WizardFirstPageActivity");
		AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(
				new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		loginToApp(driver, "traqatest5@gmail.com", "welcometr1", "9546532543");
		updateBusinessCard(driver);
		addDeleteWebinar(driver);

	}

	public static void createAnAccount(AndroidDriver<AndroidElement> driver, String email, String pwd, String phone) {
		driver.findElementById("com.thinkrite.assistant:id/doNotHaveAnAccountLink").click();
		if (driver.findElementsByXPath("//android.widget.TextView[@text='Create an Account']").size() == 1)
			System.out.println("Create An Account Appeared");
		driver.findElementById("com.thinkrite.assistant:id/emailEditText").click();
		driver.findElementById("com.thinkrite.assistant:id/emailEditText").setValue(email);
		driver.findElementById("com.thinkrite.assistant:id/passwordEditText").setValue(pwd);
		driver.findElementById("com.thinkrite.assistant:id/e164PhNumEditText").setValue(phone);
		driver.findElementById("com.thinkrite.assistant:id/buttonCreateAccount").click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.thinkrite.assistant:id/emailHasBeenSentToText")));
		System.out.println(
				driver.findElementById("com.thinkrite.assistant:id/emailHasBeenSentToText").getAttribute("text"));
		System.out.println(driver.findElementById("com.thinkrite.assistant:id/emailText").getAttribute("text"));

	}

	/**
	 * @param driver
	 *            Background: Given User is on login page
	 * 
	 *            Scenario User is able to update Business Card 
	 *            When User navigates to Business Card option
	 *            Then it should be able to update salutation
	 *            
	 * 
	 */
	public static void updateBusinessCard(AndroidDriver<AndroidElement> driver) {
		// click on `Daily View` menu -> Settings
		openSidePanelOption(driver, "Settings");

		// click on `Settings` -> `My Account`
		openSettingsSubOption(driver, "My Account");

		// click on `My Account` sub-menu items
		openSettingsSubOptions_Option(driver, "My Account", "Business Card");

		// Update the salutation text
		String retSaluation = updateSalutation(driver);
		boolean retResult = verifySalutation(driver, retSaluation);
		System.out.println("Verification Result " + retResult);

		GoToHomeScreen(driver);
	}

	/**
	 * @param driver
	 *            Background: Given User is on login page
	 * 
	 *            Scenario User is able to add/update/delete Webinars 
	 *            When User navigates to Webinars 
	 *            Then it should be able to delete Webinars 
	 *            And it should be able to add new Webinars 
	 *            And it should be able to update existing Webinars
	 * 
	 */
	public static void addDeleteWebinar(AndroidDriver<AndroidElement> driver) {
		openSidePanelOption(driver, "Settings");
		openSettingsSubOption(driver, "Joining Options");
		openSettingsSubOptions_Option(driver, "Joining Options", "Webinars");

		// Delete all Webinars
		deleteAllWebinars(driver);

		// Create a Webinars
		createWebinars(driver, "Angularjs Demo", "http://angularjs.com");

		// Update a Webinars
		updateWebinars(driver, "Angularjs Demo", "Reactjs Demo", "http://reactjs.com");

		// Verifiy Webinars
		boolean retResult = verifyWebinars(driver, "Reactjs Demo", "http://reactjs.com");

		System.out.println("Validation of webinar " + retResult);

		GoToHomeScreen(driver);

	}

	public static void openSidePanelOption(AndroidDriver<AndroidElement> driver, String option) {
		// click on Daily View menu -> Settings
		driver.findElementByXPath("//android.widget.ImageButton[contains(@content-desc,'Navigate up')]").click();
		List<AndroidElement> menuItems = driver.findElementsById("com.thinkrite.assistant:id/design_menu_item_text");
		for (AndroidElement element : menuItems) {
			if (element.getAttribute("text").contains(option)) {
				element.click();
				break;
			}
		}
	}

	public static void openSettingsSubOption(AndroidDriver<AndroidElement> driver, String option) {
		if (driver.findElementsByXPath("//android.widget.TextView[contains(@text,'Settings')]").size() == 1) {
			List<AndroidElement> subMenuItems = driver.findElementsById("android:id/title");
			for (AndroidElement element : subMenuItems) {
				if (element.getAttribute("text").contains(option)) {
					element.click();
					break;
				}
			}
		}
	}

	public static void openSettingsSubOptions_Option(AndroidDriver<AndroidElement> driver, String option,
			String subOptions) {
		if (driver.findElementsByXPath("//android.widget.TextView[contains(@text,'" + option + "')]").size() == 1) {
			List<AndroidElement> myAccountMenuItems = driver.findElementsById("android:id/title");
			for (AndroidElement element : myAccountMenuItems) {
				if (element.getAttribute("text").contains(subOptions)) {
					element.click();
					break;
				}
			}
		}
	}

	public static String updateSalutation(AndroidDriver<AndroidElement> driver) {
		driver.findElementById("com.thinkrite.assistant:id/salutationText").click();
		List<String> saluation = new ArrayList<>();
		driver.findElementsById("android:id/text1").forEach(ele -> {
			saluation.add(ele.getAttribute("text"));
		});
		Random ran = new Random();
		int x = ran.nextInt(5);
		List<AndroidElement> saluationItems = driver.findElementsById("android:id/text1");
		for (AndroidElement ele : saluationItems) {
			if (ele.getAttribute("text").contains(saluation.get(x))) {
				ele.click();
				driver.findElementById("android:id/button1").click();
				driver.findElementById("com.thinkrite.assistant:id/save").click();
				break;
			}
		}
		return saluation.get(x);
	}

	public static boolean verifySalutation(AndroidDriver<AndroidElement> driver, String expected) {
		if (driver.findElementsByXPath("//android.widget.TextView[contains(@text,'My Account')]").size() == 1) {
			List<AndroidElement> myAccountMenuItems = driver.findElementsById("android:id/title");
			for (AndroidElement element : myAccountMenuItems) {
				if (element.getAttribute("text").contains("Business Card")) {
					element.click();
					break;
				}
			}
		}
		if (driver.findElementById("com.thinkrite.assistant:id/salutationText").getAttribute("text").equals(expected))
			return true;

		return false;
	}

	public static void deleteAllWebinars(AndroidDriver<AndroidElement> driver) {
		// Delete all webinars
		driver.findElementsById("com.thinkrite.assistant:id/cardView").forEach(item -> {
			item.findElementById("com.thinkrite.assistant:id/cardIcon").click();
			driver.findElementById("android:id/button1").click();
		});
	}

	public static void createWebinars(AndroidDriver<AndroidElement> driver, String name, String url) {
		driver.findElementById("com.thinkrite.assistant:id/floatingActionButton").click();
		driver.findElementById("com.thinkrite.assistant:id/joiningOptionLabelEditText").setValue(name);
		driver.findElementById("com.thinkrite.assistant:id/joiningOptionValueEditText").setValue(url);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.attributeContains(By.id("com.thinkrite.assistant:id/save"), "enabled", "true"));
		driver.findElementById("com.thinkrite.assistant:id/save").click();
	}

	public static void updateWebinars(AndroidDriver<AndroidElement> driver, String name, String newName,
			String newUrl) {
		driver.findElementsById("com.thinkrite.assistant:id/cardView").forEach(item -> {
			if (item.findElementById("com.thinkrite.assistant:id/cardText").getAttribute("text").contentEquals(name)) {
				item.click();
				driver.findElementById("com.thinkrite.assistant:id/joiningOptionLabelEditText").clear();
				driver.findElementById("com.thinkrite.assistant:id/joiningOptionLabelEditText").setValue(newName);
				driver.findElementById("com.thinkrite.assistant:id/joiningOptionValueEditText").clear();
				driver.findElementById("com.thinkrite.assistant:id/joiningOptionValueEditText").setValue(newUrl);
				driver.findElementById("com.thinkrite.assistant:id/save").click();
			}
		});

	}

	// Verify the Webinars title
	public static boolean verifyWebinars(AndroidDriver<AndroidElement> driver, String name, String url) {
		List<AndroidElement> cardViews = driver.findElementsById("com.thinkrite.assistant:id/cardView");
		for (AndroidElement ele : cardViews) {
			if (ele.findElementById("com.thinkrite.assistant:id/cardText").getAttribute("text").equals(name)
					&& ele.findElementById("com.thinkrite.assistant:id/cardSubText").getAttribute("text").equals(url))
				return true;
		}
		return false;
	}

	// Go back to Home screen of the application (Either Daily View or
	// Notifications page)
	public static void GoToHomeScreen(AndroidDriver<AndroidElement> driver) {
		while (true) {
			String title;
			try {
				title = driver.findElementByClassName("android.widget.TextView").getAttribute("text");
			} catch (Exception e) {
				break;
			}
			if (title.equals("Daily View") || title.equals("Notificaitons")) {
				break;
			} else {
				driver.navigate().back();
			}

		}
	}

	public static void loginToApp(AndroidDriver<AndroidElement> driver, String email, String pwd, String number)
			throws InterruptedException {

		driver.findElementById("com.thinkrite.assistant:id/emailEditText").clear();
		driver.findElementById("com.thinkrite.assistant:id/emailEditText").setValue(email);
		driver.findElementById("com.thinkrite.assistant:id/passwordEditText").click();
		driver.findElementById("com.thinkrite.assistant:id/passwordEditText").clear();
		driver.findElementById("com.thinkrite.assistant:id/passwordEditText").setValue(pwd);
		driver.findElementById("com.thinkrite.assistant:id/e164PhNumEditText").clear();
		driver.findElementById("com.thinkrite.assistant:id/e164PhNumEditText").setValue(number);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.attributeContains(By.id("com.thinkrite.assistant:id/buttonSignIn"), "enabled",
				"true"));
		driver.findElementById("com.thinkrite.assistant:id/buttonSignIn").click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='Calendars']")));

		if (driver.findElementsById("com.thinkrite.assistant:id/checkBoxThisDevice").size() >= 1) {
			driver.findElementById("com.thinkrite.assistant:id/checkBoxThisDevice").click();
			acceptCalendarAlert(driver);
			driver.findElementById("com.thinkrite.assistant:id/done").click();
			driver.findElementById("com.thinkrite.assistant:id/buttonOk").click();
			acceptCalendarAlert(driver);
		}
		List<AndroidElement> radiobtns = driver.findElementsByClassName("android.widget.RadioButton");
		int size = radiobtns.size();
		radiobtns.get(size - 1).click();
		driver.findElementById("com.thinkrite.assistant:id/textViewClose").click();

	}

	public static void acceptCalendarAlert(AndroidDriver<AndroidElement> driver) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.android.packageinstaller:id/permission_allow_button")));
		driver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
	}

}