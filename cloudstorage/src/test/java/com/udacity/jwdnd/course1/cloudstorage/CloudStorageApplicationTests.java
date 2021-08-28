package com.udacity.jwdnd.course1.cloudstorage;


import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
class CloudStorageApplicationTests {
	@Autowired
	EncryptionService encryptionService;
	@Autowired
	CredentialService credentialsService;
	@Autowired
	UserService userService;

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}
	@Test
	public void testUnauthorizedUserAccess(){
		driver.get("http://localhost:" + this.port +"/home");
		Assertions.assertEquals("Login",driver.getTitle());
		driver.get("http://localhost:" + this.port +"/uploadFile");
		Assertions.assertEquals("Login",driver.getTitle());
		driver.get("http://localhost:" + this.port +"/credential");
		Assertions.assertEquals("Login",driver.getTitle());
		driver.get("http://localhost:" + this.port +"/note");
		Assertions.assertEquals("Login",driver.getTitle());
	}

	//testing indivual then the test pass
	@Test
	public void testUserSignupAndLogin() throws InterruptedException {
		String username = "admina2";
		String password = "admin123";
		String firstname = "Arthus";
		String lastname = "nire";

		//get url plus signup
		driver.get("http://localhost:" + this.port+"/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname,lastname,username,password);
		Assertions.assertEquals("You successfully signed up! Please continue to the login page.",driver.findElement(By.id("success-msg")).getText());

		driver.get("http://localhost:" + this.port+"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);
		Assertions.assertEquals("Home",driver.getTitle());
		driver.findElement(By.id("logout")).submit();
		//wait 6 second
		Thread.sleep(6000);
		Assertions.assertNotEquals("Home",driver.getTitle());

		driver.get("http://localhost:" + this.port+"/home");
		Thread.sleep(4000);
		Assertions.assertNotEquals("Home",driver.getTitle());
		Thread.sleep(4000);
	}

	//testing indivual then the test pass
	@Test
	public void testLogin()throws InterruptedException{
		String username = "admina2";
		String password = "admin123";
		String firstname = "Arthus";
		String lastname = "nire";

		//signup
		driver.get("http://localhost:" + this.port+"/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname,lastname,username,password);
		Assertions.assertEquals("You successfully signed up! Please continue to the login page.",driver.findElement(By.id("success-msg")).getText());

		//login
		driver.get("http://localhost:" + this.port+"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username,password);
		Assertions.assertEquals("Home",driver.getTitle());
	}

	@Test
	public void testAddNote() {
		//signup
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signUpPage = new SignupPage(driver);
		signUpPage.signup("arthus","nire","admina2","admin123");

        //login
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("admina2", "admin123");

		//got to homepage
		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.createNote();
		driver.get("http://localhost:" + this.port + "/home");
		homePage.clickNoteTab();

	}

	//testing indivual then the test pass
	@Test
	public void testDeleteNote() {
		//signup
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signUpPage = new SignupPage(driver);
		signUpPage.signup("arthus","nire","admina2","admin123");

          //login
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("admina2", "admin123");

		//homePage
		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.createNote();

		driver.get("http://localhost:" + this.port + "/home");
		homePage.clickNoteTab();
		homePage.deleteNote();
		Assertions.assertThrows(NoSuchElementException.class, ()->{
			homePage.getNoteTitleText();
		});


	}

	@Test
	public void testEditNote() {

		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signUpPage = new SignupPage(driver);
		signUpPage.signup("Arthus","nire","admina2","admin123");


		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("admina2", "admin123");

		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.createNote();

		String noteTitleText= homePage.getNoteTitleText();
		System.out.println("the edited note text is"+noteTitleText);

	}




}
