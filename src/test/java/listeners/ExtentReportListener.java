package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import driver.DriverFactory;
import utils.StepLogger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportListener implements ITestListener, ISuiteListener {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    // -------------------------------------------------------------------------
    // Ciclo de vida da suite — cria e fecha o relatório uma única vez
    // -------------------------------------------------------------------------

    @Override
    public void onStart(ISuite suite) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String dir       = "extent-report";
        new File(dir).mkdirs();

        ExtentSparkReporter spark = new ExtentSparkReporter(dir + "/report_" + timestamp + ".html");
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("SauceDemo — Test Report");
        spark.config().setReportName("UI Automation · TestNG + Selenium");
        spark.config().setTimeStampFormat("dd/MM/yyyy HH:mm:ss");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Aplicação",  "SauceDemo");
        extent.setSystemInfo("URL",        "https://www.saucedemo.com");
        extent.setSystemInfo("Browser",    "Chrome");
        extent.setSystemInfo("Framework",  "TestNG 7.10 + Selenium 4.27");
    }

    @Override
    public void onFinish(ISuite suite) {
        if (extent != null) {
            extent.flush();
        }
    }

    // -------------------------------------------------------------------------
    // Ciclo de vida de cada teste
    // -------------------------------------------------------------------------

    @Override
    public void onTestStart(ITestResult result) {
        String description = result.getMethod().getDescription();
        String name        = (description != null && !description.isBlank())
                ? description
                : result.getName();
        String category    = result.getTestClass().getRealClass()
                .getSimpleName().replace("Test", "");

        ExtentTest test = extent.createTest(name).assignCategory(category);
        extentTest.set(test);
        StepLogger.setTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().pass("Teste passou.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());
        captureScreenshot();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String reason = result.getThrowable() != null
                ? result.getThrowable().getMessage()
                : "sem motivo informado";
        extentTest.get().skip("Teste ignorado: " + reason);
    }

    // -------------------------------------------------------------------------
    // Métodos obrigatórios da interface — não utilizados
    // -------------------------------------------------------------------------

    @Override public void onStart(ITestContext context)  {}
    @Override public void onFinish(ITestContext context) {}

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private void captureScreenshot() {
        try {
            WebDriver driver = DriverFactory.getDriver();
            if (driver instanceof TakesScreenshot ts) {
                String base64 = ts.getScreenshotAs(OutputType.BASE64);
                extentTest.get().addScreenCaptureFromBase64String(base64, "Screenshot da falha");
            }
        } catch (Exception e) {
            extentTest.get().warning("Não foi possível capturar screenshot: " + e.getMessage());
        }
    }
}
