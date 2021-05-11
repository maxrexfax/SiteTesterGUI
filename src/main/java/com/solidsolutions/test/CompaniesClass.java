/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solidsolutions.test;

/**
 *
 * @author user
 */

import com.mycompany.perscriptumtest.MainFraimClass;
import java.io.File;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class CompaniesClass {
    public HelperClass helperClass = new HelperClass();
    public CredentialsClass credentialsClass;
    public WebDriver webDriver = null;
    public File fileToWriteLogsOfTesting;
    public File fileToWriteErrorLogOfTesting;
    public String dateTimeOfSession;
    private String pathToLogFileFolder;
    private String osName;
    public StringBuffer strBuffer = new StringBuffer("");
    private final int numberOfAdresses = 3;
    
    public CompaniesClass(String pathToFileFolderIn, String osNameIn) {
        this.pathToLogFileFolder = pathToFileFolderIn;
        this.osName = osNameIn;
    }
    
    public void createCompany() throws InterruptedException{
        
        credentialsClass = new CredentialsClass();
        dateTimeOfSession = helperClass.getDateInStringForWindowsLinux();  
        
        String fileName = this.pathToLogFileFolder + "testCreateCompanyLogFile_" + dateTimeOfSession + ".txt";
        String fileNameERRORS = this.pathToLogFileFolder + "testCreateCompanyLogFile_ERRORS_" + dateTimeOfSession + ".txt";
        
        try {
            fileToWriteLogsOfTesting = new File(fileName);
            fileToWriteErrorLogOfTesting = new File(fileNameERRORS);
            System.out.println("Path to logfile:" + fileName);
        } catch (Exception exx) {
            System.out.println(exx.getMessage());
            System.out.println("Error file creation, testing log will be only in terminal");
        }
        
        helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Create Company testing starts at: " + dateTimeOfSession +" OS: " + osName);
        
        try {
            
            if(MainFraimClass.CURRENT_BROWSER == MainFraimClass.CHANGE_CHROME_BROWSER) {
                webDriver = new ChromeDriver();
            } else {
                webDriver = new FirefoxDriver();
            }
            
            JavascriptExecutor js = (JavascriptExecutor)webDriver;
            webDriver.manage().window().maximize();
            webDriver.get("https://perscriptum-dev.herokuapp.com/"); 
            Thread.sleep(1500);
            WebElement login = webDriver.findElement(By.id("input-11"));
            WebElement passwd = webDriver.findElement(By.id("input-14"));
            WebElement btnLogin = webDriver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div/div[2]/div/form/button"));
            login.sendKeys(credentialsClass.emailToLogin);
            passwd.sendKeys(credentialsClass.passwordToLogin);
            Thread.sleep(500);
            btnLogin.click();
            Thread.sleep(1500);              

            webDriver.get("https://perscriptum-dev.herokuapp.com/companies");
            Thread.sleep(2000);

            webDriver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/main/div/div/div/div[2]/div/div/div/div/header/div/button[2]")).click();//create company
            Thread.sleep(500);
            
            //input name of company START
            String textLabel1 = webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div > div > form:nth-child(1) > div.v-input.mt-5.theme--light.v-text-field.v-text-field--is-booted > div > div.v-input__slot > div > label")).getText();
            String nameToFillInInput = "TestCompanyName_" + helperClass.getRandomDigit(99, 999);
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: try to send data ---" + nameToFillInInput + "--- to input with label text: " + textLabel1); 
            WebElement inputForName = webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div > div > form:nth-child(1) > div.v-input.mt-5.theme--light.v-text-field.v-text-field--is-booted > div > div.v-input__slot > div > input"));
            inputForName.sendKeys(nameToFillInInput);
            Thread.sleep(500);
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Data in the input:" + inputForName.getAttribute("value"));
            //input name of company END
            
            Thread.sleep(500);
            
            //input Reference START
            String textLabel2 = webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div > div > form:nth-child(1) > div:nth-child(2) > div > div.v-input__slot > div > label")).getText();
            String refToFillInInput = "Reference_" + helperClass.getRandomDigit(99, 999);
            WebElement inputForReference = webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div > div > form:nth-child(1) > div:nth-child(2) > div > div.v-input__slot > div > input"));
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: try to send data ---" + refToFillInInput + "--- to input with label text: " + textLabel2); 
            inputForReference.sendKeys(refToFillInInput);
            Thread.sleep(500);
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Data in the input:" + inputForReference.getAttribute("value"));
            //input Reference END  
            
            Thread.sleep(500);
            
            //input Reference START
            String textLabel3 = webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div > div > form:nth-child(1) > div:nth-child(3) > div > div.v-input__slot > div > label")).getText();
            String randomPhomeNumber = "" + helperClass.getRandomDigit(99999999, 999999999);
            WebElement inputForNumber = webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div > div > form:nth-child(1) > div:nth-child(3) > div > div.v-input__slot > div > input"));
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: try to send data ---" + randomPhomeNumber + "--- to input with label text: " + textLabel3); 
            inputForNumber.sendKeys(randomPhomeNumber);
            Thread.sleep(500);
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Data in the input:" + inputForNumber.getAttribute("value"));
            //input Reference START
            
            Thread.sleep(500);
            
            //input FAX START
            String textLabel4 = webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div > div > form:nth-child(1) > div:nth-child(4) > div > div.v-input__slot > div > label")).getText();
            WebElement inputForFax = webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div > div > form:nth-child(1) > div:nth-child(4) > div > div.v-input__slot > div > input"));
            String randomFaxNumber = "" + helperClass.getRandomDigit(99, 9999);
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: try to send data ---" + randomFaxNumber + "--- to input with label text: " + textLabel4); 
            inputForFax.sendKeys(randomFaxNumber);
            Thread.sleep(500);
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Data in the input:" + inputForFax.getAttribute("value"));
            //input FAX END
            
            Thread.sleep(500);
            
            
            //input Address START
            for (int i = 1; i <= numberOfAdresses; i++) {
                helperClass.selectOneElementFromDropdownAddressInHelper(webDriver);
                Thread.sleep(1500);  
                webDriver.findElement(By.className("value")).click();
            }            
            
            String allAddresses = helperClass.getAllAddressesOnPage(webDriver);
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Data in the input Address:" + allAddresses);
            //input Address END
            
            Thread.sleep(1500);
            
            //input Additional (appears after filling address) START//
            for (int i = 1; i <= numberOfAdresses; i++) {
                fillTmpAddressInput("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div > div > form:nth-child(2) > div:nth-child(" + i + ") > div.col-sm-4.col-md-2.col-12 > div > div > div.v-input__slot > div > ");                
            }            
            //input     Additional (appears after filling address) END            
            
            Thread.sleep(1000);
            
            webDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div/header/div/button[2]")).click();
            
            Thread.sleep(1000);
            
            //find div container with message result of saving START
            String systemMessage = helperClass.getSystemMessage(webDriver);
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: system message: " + systemMessage); 
            //find div container with message result of saving END
            
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: END"); 
            Thread.sleep(5000);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            helperClass.printToFileAndConsoleInformation(fileToWriteErrorLogOfTesting, "ERROR: Error in main try block of CompaniesClass"); 
        } finally {
            webDriver.close();
            webDriver.quit();
        }
    }

    private void fillTmpAddressInput(String cssSelector) throws InterruptedException {
        Thread.sleep(500);
        String textLabelAdd = webDriver.findElement(By.cssSelector(cssSelector + "label")).getText();
        WebElement inputForAdd = webDriver.findElement(By.cssSelector(cssSelector + "input"));
        String randomData = "Val_" + helperClass.getRandomDigit(99, 999);
        helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: try to send data ---" + randomData + "--- to input with label text: " + textLabelAdd); 
        inputForAdd.sendKeys(randomData);
        Thread.sleep(500);
        helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Data in the input:" + inputForAdd.getAttribute("value"));
    }
}
