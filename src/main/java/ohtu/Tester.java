package ohtu;

import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Tester {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        driver.get("http://localhost:4567");
        
        WebElement wrongPassword = driver.findElement(By.linkText("login"));
        wrongPassword.click();
        
        sleep(2);
        
        wrongPassword = driver.findElement(By.name("username"));
        wrongPassword.sendKeys("pekka");
        wrongPassword = driver.findElement(By.name("password"));
        wrongPassword.sendKeys("drived");
        wrongPassword = driver.findElement(By.name("login"));
        
        sleep(2);
        
        wrongPassword.submit();
        
        sleep(3);
        
        WebElement wrongUsername = driver.findElement(By.name("username"));
        wrongUsername.sendKeys("not");
        wrongUsername = driver.findElement(By.name("password"));
        wrongUsername.sendKeys("akkep");
        wrongUsername = driver.findElement(By.name("login"));
        
        sleep(2);
        
        wrongUsername.submit();
        
        sleep(3);
        
        wrongUsername = driver.findElement(By.linkText("back to home"));
        wrongUsername.click();
        
        sleep(3);
        
        WebElement newUser = driver.findElement(By.linkText("register new user"));
        newUser.click();
        
        sleep(2);
        
        Random r = new Random();
    
        newUser = driver.findElement(By.name("username"));
        newUser.sendKeys("arto"+r.nextInt(100000));
        newUser = driver.findElement(By.name("password"));
        newUser.sendKeys("toimii");
        newUser = driver.findElement(By.name("passwordConfirmation"));
        newUser.sendKeys("toimii");
        newUser = driver.findElement(By.name("signup"));
        
        sleep(2);
        
        newUser.submit();
        
        sleep(3);
        
        WebElement logOut = driver.findElement(By.linkText("continue to application mainpage"));
        logOut.click();
        
        sleep(2);
        
        logOut = driver.findElement(By.linkText("logout"));
        logOut.click();
        
        sleep(3);
        
        WebElement loginSuccesfull = driver.findElement(By.linkText("login"));
        loginSuccesfull.click();

        sleep(2);

        loginSuccesfull = driver.findElement(By.name("username"));
        loginSuccesfull.sendKeys("pekka");
        loginSuccesfull = driver.findElement(By.name("password"));
        loginSuccesfull.sendKeys("akkep");
        loginSuccesfull = driver.findElement(By.name("login"));
        
        sleep(2);
        
        loginSuccesfull.submit();

        sleep(3);
        
        driver.quit();
    }
    
    private static void sleep(int n){
        try{
            Thread.sleep(n*1000);
        } catch(Exception e){}
    }
}
