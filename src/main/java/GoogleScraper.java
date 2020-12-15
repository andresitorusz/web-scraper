import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class GoogleScraper {
    public static void main(String[] args) throws InterruptedException {

        String strXpath;
        List<WebElement> searchPageResult;
        List<String> allLinkText = new ArrayList<>();


        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        Thread.sleep(2000);

        driver.findElement(By.name("q")).sendKeys("Cristiano Ronaldo");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);

        for (int i = 1; i <= 5; i++) {
            if (i > 1) {
                strXpath = "//a[@class='fl' and text()='" + i + "']";
                driver.findElement(By.xpath(strXpath)).click();
                Thread.sleep(2000);
            }

            searchPageResult = driver.findElements(By.xpath("//div[@class='rc']/div/a/h3"));

            for (WebElement link : searchPageResult) {
                allLinkText.add(link.getText());
            }
        }
        for (String eachLinkText: allLinkText) {
            System.out.println(eachLinkText);
        }
    }
}
