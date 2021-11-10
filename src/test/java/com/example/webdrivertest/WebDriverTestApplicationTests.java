package com.example.webdrivertest;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@EnableAsync
class WebDriverTestApplicationTests {

   @Test
   void contextLoads() throws IOException, InterruptedException {
      System.setProperty("webdriver.chrome.driver", "/opt/webdriver/chromedriver");

      var options = new ChromeOptions();
      options.addArguments("--headless");
      options.addArguments("--no-sandbox");
      options.addArguments("--disable-dev-shm-usage");

      DesiredCapabilities capabilities = DesiredCapabilities.chrome();
      capabilities.setCapability(ChromeOptions.CAPABILITY, options);
      var driver = new RemoteWebDriver(new URL("http://localhost:9999/wd/hub"),
              capabilities);

      for(int i = 0; i < 100; i++) {
         /*driver.get("https://www.google.com/");
         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
         File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

         FileUtils.copyFile(file, new File("/opt/webdriver/google-" + String.valueOf(i) + ".png"));

         System.out.println("completed... index:" + String.valueOf(i));*/
         fetch(driver, i);
      }

      driver.quit();
   }

   public void fetch(RemoteWebDriver driver, int i) throws IOException {
//      driver.get("https://www.google.com/");
//      driver.get("file:///g:/temp/google.html");
      driver.get("http://localhost:18080/test");
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

      FileUtils.copyFile(file, new File("/opt/webdriver/google-" + String.valueOf(i) + ".png"));

      System.out.println("completed... index:" + String.valueOf(i));
   }

}
