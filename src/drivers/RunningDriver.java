package drivers;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import utils.Utils;

public class RunningDriver {
	private Utils utils = new Utils();

	private String pathToChromeDriver() throws IOException {
		String executableDriverFileName = "chromedriver";

		if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
			executableDriverFileName = "chromedriver.exe";
		}
		String pathToChromeDriverExecutableFile = utils.copyFileToTempDir(executableDriverFileName);
		System.out.println("pathToChromeDriverExecutableFile: " + pathToChromeDriverExecutableFile);

		return pathToChromeDriverExecutableFile;
	}

	public WebDriver firefoxDriver() {
//        DesiredCapabilities capabilites = new DesiredCapabilities();
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	public WebDriver chromeDriver() throws IOException {
		System.setProperty("webdriver.chrome.driver", pathToChromeDriver());
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-browser-side-navigation");
		options.addArguments("--disable-gpu");
		WebDriver driver = new ChromeDriver(options);
		return driver;
	}

	public WebDriver chromeDriver(String pathToProfileDir) throws IOException {
		System.setProperty("webdriver.chrome.driver", pathToChromeDriver());
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--user-data-dir=" + pathToProfileDir);
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-browser-side-navigation");
		options.addArguments("--disable-gpu");
		WebDriver driver = new ChromeDriver(options);
		return driver;
	}
}
