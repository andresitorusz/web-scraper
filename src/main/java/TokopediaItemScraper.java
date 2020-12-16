import com.opencsv.CSVWriter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TokopediaItemScraper {

    public static void main(String[] args) throws InterruptedException, IOException {

        //setup driver
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--disable-notifications"); // disable notification pop-up
        System.setProperty("webdriver.chrome.driver",
                "C:\\ChromeDriver\\chromedriver.exe"); // in case we have different chromedriver path, just change this path
        WebDriver driver = new ChromeDriver(option);
        driver.manage().window().maximize();

        // init variable
        ArrayList<String> phoneItemContainer = new ArrayList<>();
        List<WebElement> itemElement;

        driver.get("https://www.tokopedia.com/");

        // search and navigate to search result page "handphone"
        driver.findElement(By.xpath("//div[@class='css-rl8xd2 e1v0ehno0']/input")).sendKeys("handphone");
        driver.findElement(By.xpath("//button[@class='css-1ymn4im e1v0ehno1']")).click();
        Thread.sleep(2000);

        // to sort the search results by user rating
        driver.findElement(By.xpath("//label[@class='css-ymlcjc e15j6tp63']")).click();
        driver.findElement(By.xpath("//li[@class='css-sxiy95 e15j6tp68']/button/span[text()='Ulasan']")).click();
        Thread.sleep(2000);

        // to perform scroll down and back to top of webpage
        // to get all items
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
        Thread.sleep(2000);
        js.executeScript("window.scrollBy(0,-document.body.scrollHeight)");
        Thread.sleep(2000);

        itemElement = driver.findElements(By.xpath("//div[@class='pcv3__container css-1bd8ct']"));

        for (WebElement element : itemElement) {
            // index 5 to 64 is the items sort by user rating, if we want more items, we should go to the next page
            if (itemElement.indexOf(element) > 4 && itemElement.indexOf(element) < 65 && phoneItemContainer.size() < 60) {
                phoneItemContainer.add(element.getText());
                System.out.println(element.getText());
            }
        }

//        // go to second page to get more items
//        driver.findElement(By.xpath("//button[@class='css-1gpfbae-unf-pagination-item e19tp72t3']")).click();
//        Thread.sleep(6000);
//        for (WebElement secondPageElement : itemElement) {
//            if (itemElement.indexOf(secondPageElement) < 40 && phoneItemContainer.size() >= 60) {
//                phoneItemContainer.add(secondPageElement.getText());
//                System.out.println(secondPageElement.getText());
//            }
//        }

        Thread.sleep(3000);
        System.out.println(phoneItemContainer.size());
    }
}
