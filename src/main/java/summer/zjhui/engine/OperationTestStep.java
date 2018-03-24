package summer.zjhui.engine;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import cucumber.api.java.en.*;
import summer.cryption.CryptionUtil;

public class OperationTestStep {
	static WebDriver driver;
	static Wait<WebDriver> wait;
	static String number;
	
	@Given("^get access to zj$")
	public void getAccessToZJ() {
		System.setProperty("webdriver.chrome.driver", "src/webdriver/chromedriver.exe");
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 500);
		driver.get("http://www.zj-talentapt.com/");
	}

	@And("^I login as username \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void Login(String userName,String password) throws Exception{
		driver.findElement(By.name("btnLoginPersonal")).click();
		driver.findElement(By.id("login_tel")).sendKeys(CryptionUtil.decryptByBase64(userName));
		driver.findElement(By.id("login_password")).sendKeys(CryptionUtil.decrypt(password));
		driver.findElement(By.id("login_btn")).click();
	}

	@And("^get waitness number$")
	public void getNumber() {

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("#loginBack > div > p.goto_hyzx_btn.mt10 > a")));
		driver.findElement(By.cssSelector("#loginBack > div > p.goto_hyzx_btn.mt10 > a")).click();
		driver.findElement(By.id("lhjl")).click();
		number = driver.findElement(By.id("ctl00_ctl00_ctl00_main_main_main_rptApplyRecord_ctl00_labPageRank"))
				.getText();
	}

	@Then("^send the number to email: \"([^\"]*)\"$")
	public void sendEmail(String email) throws Exception {
		final String from = CryptionUtil.decryptByBase64("eGlhb2NoYW4ueGlhQHFxLmNvbQ==");
		final String password = CryptionUtil.decrypt("wjdKdu208kSZZF3LfK+osA==");
		
		/*new ProcessBuilder("C:\\Program Files (x86)\\Microsoft Office\\Office14\\OUTLOOK.EXE", 
				"/c IPM.Note /m", to +"&subject=Waitness:" + number).start();*/
		/*Desktop.getDesktop().mail(new URI("mailto:xx18626@imcnam.ssmb.com"));*/
	    Properties props = new Properties();

	    props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.qq.com");
        props.put("mail.smtp.port", "587");


		Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
          });
		try {
			MimeMessage message = new MimeMessage(session);
			// Set From: 头部头字段
			message.setFrom(new InternetAddress(from));
			// Set To: 头部头字段
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(CryptionUtil.decryptByBase64(email)));
			message.setSubject("人才公寓排名:"+number);
			// 设置消息体
			message.setText(number);

			// 发送消息
			Transport.send(message);
			
			//close webdriver.
			driver.quit();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
