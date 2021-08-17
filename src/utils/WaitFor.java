package utils;

import java.util.Timer;
import java.util.TimerTask;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WaitFor {
	private boolean isLocked = true;
	private long tickIntervalInMiliseconds = 500;
	private long timeoutInMiliseconds = 20000;

	public WaitFor(float timeoutInSeconds) {
		super();
		this.timeoutInMiliseconds = (long) (timeoutInSeconds * 1000);
	}

	public WaitFor(float timeoutInSeconds, float intervalInSeconds) {
		super();
		this.timeoutInMiliseconds = (long) (timeoutInSeconds * 1000);
		this.tickIntervalInMiliseconds = (long) (intervalInSeconds * 1000);
	}

	private void updateLockedState(boolean isLocked) {
		this.isLocked = isLocked;
	}

	private void throwError(String details) throws Exception {
		if (timeoutInMiliseconds == 0) {
			throw new Exception("\n===WaitFor: Timeout error\n" + details);
		}
	}

	private void timeoutDeduction() {
		timeoutInMiliseconds -= tickIntervalInMiliseconds;
	}

	public void waitForVisibilityById(WebDriver driver, String id) throws Exception {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String message = "\n=== Checking for visibility by id: " + id;
				if ((driver.findElements(By.id(id)).size() > 0) || timeoutInMiliseconds == 0) {
					timer.cancel();
					updateLockedState(false);
				} else {
					timeoutDeduction();
				}
			}
		}, tickIntervalInMiliseconds, tickIntervalInMiliseconds);
		while (isLocked) {
			String loop = "\n---looping in locked for visibility by id: " + id;
			Thread.sleep(tickIntervalInMiliseconds);
			// loop until unlock
			throwError("waitForVisibilityById: " + id);
		}
	}

	public void waitForVisibilityByXpath(WebDriver driver, String xpath) throws Exception {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String message = "\n=== Checking for visibility by xpath: " + xpath;
				if ((driver.findElements(By.xpath(xpath)).size() > 0) || timeoutInMiliseconds == 0) {
					timer.cancel();
					updateLockedState(false);
				} else {
					timeoutDeduction();
				}
			}
		}, tickIntervalInMiliseconds, tickIntervalInMiliseconds);
		while (isLocked) {
			String loopMessage = "\n---looping in locked for visibility by xpath: " + xpath;
			Thread.sleep(tickIntervalInMiliseconds);
			// loop until unlock
			throwError("waitForVisibilityByXpath: " + xpath);
		}
	}

}
