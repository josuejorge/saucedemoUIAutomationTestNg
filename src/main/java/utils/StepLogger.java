package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class StepLogger {

    private static final ThreadLocal<ExtentTest> currentTest = new ThreadLocal<>();

    public static void setTest(ExtentTest test) {
        currentTest.set(test);
    }

    public static void step(WebDriver driver, String description) {
        ExtentTest test = currentTest.get();
        if (test == null) return;
        try {
            String base64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.info(description,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
        } catch (Exception e) {
            test.info(description + " (screenshot indisponível)");
        }
    }
}
