import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;

public class LinkedInProfileScraper {
    public static final String EMAIL = "YOUR_EMAIL"; // enter your LinkedIn email
    public static final String PASSWORD = "YOUR_PASSWORD"; // enter your LinkedIn password
    public static final String PROFILE_URL = "PROFILE_URL"; // enter LinkedIn profile url here

    public static void main(String[] args) throws InterruptedException, IOException {

        // setup driver
        System.setProperty("webdriver.chrome.driver",
                "C:\\ChromeDriver\\chromedriver.exe"); // in case we have different chromedriver path, just change this path
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        // init variable
        ArrayList<String> name = new ArrayList<>(),
                location = new ArrayList<>(),
                experience = new ArrayList<>(),
                education = new ArrayList<>();
        List<WebElement> experienceSection, educationSection;

        // open LinkedIn login page
        driver.get("https://www.linkedin.com/login");
        Thread.sleep(2000);

        // login to LinkedIn
        driver.findElement(By.name("session_key")).sendKeys(EMAIL);
        driver.findElement(By.name("session_password")).sendKeys(PASSWORD);
        driver.findElement(By.xpath("//div[@class='login__form_action_container ']/button")).click();
        Thread.sleep(2000);

        // navigate to LinkedIn profile page
        driver.get(PROFILE_URL);

        // get name and location
        name.add(driver.findElement(By.xpath("//div[@class='flex-1 mr5']/ul[@class='pv-top-card--list inline-flex align-items-center']/li")).getText());
        location.add(driver.findElement(By.xpath("//div[@class='flex-1 mr5']/ul[@class='pv-top-card--list pv-top-card--list-bullet mt1']/li")).getText());

        // use this to iterate the element in experience and education section
        experienceSection = driver.findElements(By.xpath("//section[@id='experience-section']/ul/li"));
        educationSection = driver.findElements(By.xpath("//section[@id='education-section']/ul/li"));

        // get experience and education
        for (WebElement element : experienceSection) {
            experience.add(element.getText());
        }
        for (WebElement element : educationSection) {
            education.add(element.getText());
        }

        // build all value into 1 string array
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(System.getProperty("line.separator"))
                .append(location).append(System.getProperty("line.separator"))
                .append(experience).append(System.getProperty("line.separator"))
                .append(education).append(System.getProperty("line.separator"));
        String[] sbToArray = sb.toString().split("TABTAB");

        // the output.csv will be exported to the project directory
        CSVWriter writer = new CSVWriter(new FileWriter("outputLinkedIn.csv"));
        writer.writeNext(sbToArray);
        writer.close();
    }
}
