package com.udacity.jwdnd.course1.cloudstorage;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class HomePage {
    @FindBy(id = "logout")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "add-note-btn")
    private WebElement addNoteBtn;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-title-text")
    // @FindBy(id = "note-title-text")
    private WebElement noteTitleText;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "note-savechanges-btn")
    private WebElement submitBtn;

    @FindBy(css = "#userTable")
    private WebElement notesTable;

    @FindBy(id = "edit-btn-note")
    private WebElement editNoteBtn;

    @FindBy(id = "delete-btn-note")
    private WebElement deleteNoteBtn;

    private final WebDriver driver;

    //defining field for Credentials to use it when needed
    @FindBy(id = "nav-credentials-tab")

    private WebElement credentialTab;

    @FindBy(id = "add-credential-btn")
    private WebElement addCredentailBtn;

    @FindBy(id = "credential-url")
    private WebElement credentialurl;

    @FindBy(xpath = "//*[@id='credential-url']")
    private WebElement credentailurl;

    @FindBy(id = "credential-username")
    private WebElement credentialusername;

    @FindBy(id = "credential-password")
    private WebElement credentialpassword;

    @FindBy(id = "credential-save-btn")
    private WebElement credentialsubmitBtn;

    @FindBy(id = "edit-credential-btn")
    private WebElement editcredentialBtn;

    @FindBy(id = "delete-credential-btn")
    private WebElement deletecredentailBtn;

    // constructor:
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickNoteTab() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.noteTab);
    }
    public void createNote(){

        waitForVisibility(noteTab);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", noteTab);
        WebDriverWait wait = new WebDriverWait(driver, 200);

        waitForVisibility(addNoteBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",addNoteBtn );

        WebDriverWait wait_modal = new WebDriverWait(driver, 20);

        //Switch to active element here in our case its model dialogue box.
        driver.switchTo().activeElement();

        wait_modal.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("note-title"))));
        wait_modal.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("note-description"))));

        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "test" + "';", driver.findElement(By.id("note-title")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "testdescription" + "';", driver.findElement(By.id("note-description")));

        //waitForVisibility(saveNoteButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);
    }



    public void editNote(){
        WebDriverWait wait = new WebDriverWait(driver, 200);
        waitForVisibility(editNoteBtn);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editNoteBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "edittest" + "';", driver.findElement(By.id("note-title")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "edittestdescription" + "';", driver.findElement(By.id("note-description")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);
        System.out.println("note edited");

    }
    public void deleteNote(){

        System.out.println("the delete note method");
        WebDriverWait wait = new WebDriverWait(driver, 200);

        waitForVisibility(deleteNoteBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteNoteBtn);

    }
    // verify that new note title is created:
    public String getNoteTitleText() {
        return noteTitleText.getText();

    }
    // verify that new note description is created:
    public String getNoteDescriptionText() {
        return noteDescription.getText();
    }

    private void waitForVisibility(WebElement element) throws Error {
        new WebDriverWait(driver, 40)
                .until(ExpectedConditions.visibilityOf(element));
    }

    //methods for credentials

    public void clickCredentialTab() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.credentialTab);
    }

    public void createCredential(){

        waitForVisibility(credentialTab);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", credentialTab);
        WebDriverWait wait = new WebDriverWait(driver, 200);


        waitForVisibility(addCredentailBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",addCredentailBtn );

        WebDriverWait wait_modal = new WebDriverWait(driver, 10);

        //Switch to active element here in our case its model dialogue box.
        driver.switchTo().activeElement();

        wait_modal.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("credential-url"))));
        wait_modal.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("credential-username"))));
        wait_modal.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("credential-password"))));

        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "example.gmail" + "';", driver.findElement(By.id("credential-url")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "user1" + "';", driver.findElement(By.id("credential-username")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "user123" + "';", driver.findElement(By.id("credential-password")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", credentialsubmitBtn);
    }


    public List getCredentialtableDetails() {
        WebDriverWait wait_modal = new WebDriverWait(driver, 10);

        int rowcount= driver.findElements(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr")).size();
        int columncount = driver.findElements(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr[2]/*")).size();
        List<String> details = new ArrayList();

        for (int i=2;i<=rowcount;i++) {
            for(int j=2;j<=columncount;j++) {
                String credentialvalue = wait_modal.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr["+i+"]/td["+j+"]")))).getText();
                details.add(credentialvalue);
            }
        }

        return details;
    }

    public String getCredentialUserName() {
        return credentialusername.getText();
    }
}
