/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solidsolutions.test;

import com.mycompany.perscriptumtest.MainFraimClass;
import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 *
 * @author maxim
 */
public class LocationsClass {
    private String pathToLogFileFolder;
    public HelperClass helperClass = new HelperClass();
    public CredentialsClass credentialsClass;
    public File fileToWriteLogsOfTesting;
    public File fileToWriteErrorLogOfTesting;
    public WebDriver webDriver = null;
    public String dateTimeOfSession;
    private String osName;
    
    public LocationsClass(String pathToFileFolderIn, String osNameIn) {
        this.pathToLogFileFolder = pathToFileFolderIn;
        this.osName = osNameIn;
    }
    
    public void createLocation() throws InterruptedException
    {
        credentialsClass = new CredentialsClass();
        dateTimeOfSession = helperClass.getDateInStringForWindowsLinux();    
        String fileName = "";
        String fileNameERRORS = "";
        
        fileName = this.pathToLogFileFolder + "testLocationsCreationLogFile_" + dateTimeOfSession + ".txt";
        fileNameERRORS = this.pathToLogFileFolder + "testLocationsCreationLogFile_ERRORS_" + dateTimeOfSession + ".txt";
                
        try {
            fileToWriteLogsOfTesting = new File(fileName);
            fileToWriteErrorLogOfTesting = new File(fileNameERRORS);
            System.out.println("Path to logfile:" + fileName);
        } catch (Exception exx) {
            System.out.println(exx.getMessage());
            System.out.println("Error file creation, test log will be only in terminal");
        }
        
        helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Location creation testing starts at: " + dateTimeOfSession +" OS: " + osName);
        
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
            helperClass.safeFindElement(webDriver, "#materialpro > div > div > div.d-flex.align-center.col-lg-5.col-xl-6.col-12 > div > div > div.v-item-group.theme--light.v-btn-toggle > button:nth-child(2)", "cssSelector").click();
            Thread.sleep(2500);
            WebElement login = helperClass.safeFindElement(webDriver, "input-11", "id");
            WebElement passwd = helperClass.safeFindElement(webDriver, "input-14", "id");
            WebElement btnLogin = helperClass.safeFindElement(webDriver, "/html/body/div[1]/div/div/div[2]/div/div/div[2]/div/form/button", "xpath");
            login.sendKeys(credentialsClass.emailToLogin);
            passwd.sendKeys(credentialsClass.passwordToLogin);
            Thread.sleep(500);

            helperClass.safeClickOnElement(btnLogin);
            Thread.sleep(2500);  
            //Logger.global.log(new LogRecord(Level.INFO, "Login complete"));            
            
            webDriver.get("https://perscriptum-dev.herokuapp.com/locations");
            Thread.sleep(1000);
            try {
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Try to click \"Create new Location\" button");
                WebElement createBtn = webDriver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/main/div/div/div/div[2]/div/div/div/div/header/div/button[2]"));
                createBtn.click();
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable click \"Create new Location\" button", ex.getMessage());
            }
            Thread.sleep(400);
            
            try {
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Try to fill First name");
                helperClass.editDataInTextInputWithLabel(webDriver, "TestNameLocation", "#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable click \"Create new Location\" button", ex.getMessage());
            }
            
            Thread.sleep(400);
            try {
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Try to click \"Save new Location\" button");
                webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > header > div > button.v-btn.v-btn--text.theme--dark.v-size--small")).click();
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to select Tag", ex.getMessage());
            }
            //
            Thread.sleep(4000);            
            
            try {
                //address 
                WebElement mapElement = helperClass.safeFindElement(webDriver, "map", "id");
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: try to fill element  " + helperClass.leftDemarkator + mapElement.getAttribute("placeholder") + helperClass.rightDemarkator);            
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: filling random address using external service");
                mapElement.click();
                Thread.sleep(500);    
                helperClass.selectOneElementFromDropdownAddressInHelper(webDriver);
                Thread.sleep(300);
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: filled address(') is: " + helperClass.getAllAddressesOnPage(webDriver) + "\n");
                Thread.sleep(300);   
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill address", ex.getMessage());
            } 
            
            Thread.sleep(400);

            
            //Additional field in address
            String dataToFillInInput = "" + helperClass.getRandomDigit(999,9999);
            try {          
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: try to fill Additional field in address");
                helperClass.editDataInTextInputWithLabel(webDriver, dataToFillInInput, "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.v-tabs.theme--light > div.v-window.v-item-group.theme--light.v-tabs-items > div > div > div > div > div > div > form > div.row > div.col-sm-4.col-md-2.col-12 > div > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(500); 
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill Additional field in address", ex.getMessage());
            } 
            
            webDriver.findElement(By.xpath("/html/body/div[1]/div/div/div/main/div/div/div/div[2]/div/div/div[1]/div[1]/div[2]/div/div[2]")).click();
            try {
            //select tags START
            Thread.sleep(500);
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Try to select Tag");
            WebElement tagsContainer = webDriver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/main/div/div/div/div[2]/div/div/div[1]/div[2]/div/div/div/div/div/div/div[2]/div[1]"));
            tagsContainer.click();
            Thread.sleep(500);
            helperClass.selectOneElementFromDropdownInHelper(webDriver, fileToWriteLogsOfTesting);
            Thread.sleep(200);        
            //select tags END
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to select Tag", ex.getMessage());
            }
            
            String allTags = "";
            try {
                allTags = helperClass.getAllChosenTags(webDriver);
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Found Tags: " + helperClass.leftDemarkator + allTags + helperClass.rightDemarkator + "\r\n");
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to find Tags!", ex.getMessage());
            }

            //click on main text on top div
            try {
                webDriver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/main/div/div/div/div[2]/div/div/div[1]/div[1]/div[2]/div/div[2]")).click();
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable click of div", ex.getMessage());
            }
            
            //Description
            try {
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Try to fill Description");
                helperClass.editDataInTextInputWithLabel(webDriver, "Test Location Description", "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.v-tabs.theme--light > div.v-window.v-item-group.theme--light.v-tabs-items > div > div > div > div > div > div > div.v-input.v-textarea.theme--light.v-text-field.v-text-field--is-booted > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, "textarea");
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill address", ex.getMessage());
            } 
            //save button click
            webDriver.findElement(By.cssSelector("#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.v-tabs.theme--light > div.v-item-group.theme--light.v-slide-group.v-tabs-bar.primary--text > div.v-slide-group__wrapper > div > header > div > button.v-btn.v-btn--text.theme--light.v-size--small.primary--text")).click();
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: END");
            Thread.sleep(5000);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            helperClass.printToFileAndConsoleInformation(fileToWriteErrorLogOfTesting, "ERROR: Error in main try block of LocationsClass"); 
        } finally {
            webDriver.close();
            webDriver.quit();
        }
        
    }
}
