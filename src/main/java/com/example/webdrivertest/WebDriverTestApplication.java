package com.example.webdrivertest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@RestController
public class WebDriverTestApplication {

   public static void main(String[] args) {
      SpringApplication.run(WebDriverTestApplication.class, args);
   }


   @GetMapping("/webdriver")
   public String webDriver() throws IOException {
      System.setProperty("webdriver.chrome.driver", "/opt/webdriver/chromedriver");

      var options = new ChromeOptions();
      options.addArguments("--headless");
      options.addArguments("--no-sandbox");
      options.addArguments("--disable-dev-shm-usage");

      DesiredCapabilities capabilities = DesiredCapabilities.chrome();
      capabilities.setCapability(ChromeOptions.CAPABILITY, options);
      var driver = new RemoteWebDriver(new URL("http://localhost:9999/wd/hub"),
              capabilities);

      driver.get("https://google.com");
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

      FileUtils.copyFile(file, new File("/opt/webdriver/" +
              RandomStringUtils.randomAlphabetic(10) + ".png"));
      driver.quit();

      return "OK";
   }

   @GetMapping("/test")
   public String test() {
      return "testOK";
   }
}
