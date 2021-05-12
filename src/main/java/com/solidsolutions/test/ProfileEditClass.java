/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solidsolutions.test;


import com.mycompany.perscriptumtest.MainFraimClass;
import java.io.File;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
/**
 *
 * @author user
 */
public class ProfileEditClass {
    
    public HelperClass helperClass = new HelperClass();
    public CredentialsClass credentialsClass;
    private String pathToLogFileFolder;
    public File fileToWriteLogsOfTesting;
    public File fileToWriteErrorLogOfTesting;
    public WebDriver webDriver = null;
    public String dateTimeOfSession;
    private String osName;
    private int numberOfAdresses = 3;
    private Thread threadForJs;
    
    public ProfileEditClass(String pathToFileFolderIn, String osNameIn) {
        this.pathToLogFileFolder = pathToFileFolderIn;
        this.osName = osNameIn;
    }
    
    public void editProfile() throws InterruptedException
    {            
        credentialsClass = new CredentialsClass();
        dateTimeOfSession = helperClass.getDateInStringForWindowsLinux();  
        
        String fileName = this.pathToLogFileFolder + "EditProfileLogFile_" + dateTimeOfSession + ".txt";
        String fileNameERRORS = this.pathToLogFileFolder + "EditProfileLogFile_ERRORS_" + dateTimeOfSession + ".txt";        
        
        try {
            fileToWriteLogsOfTesting = new File(fileName);
            fileToWriteErrorLogOfTesting = new File(fileNameERRORS);
            System.out.println("Path to logfile:" + fileName);
        } catch (Exception exx) {
            System.out.println(exx.getMessage());
            System.out.println("Error file creation, testing log will be only in terminal");
        }
        
        helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Edit profile testing starts at: " + dateTimeOfSession +" OS: " + osName);
        
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
            Thread.sleep(2500);  
            //login to site END
            
            webDriver.get("https://perscriptum-dev.herokuapp.com/profile"); 
            Thread.sleep(2500);
            
            try {
                //first name
                helperClass.editDataInTextInputWithLabel(webDriver, null, "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.row > div:nth-child(2) > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div > div > div > div > div > div:nth-child(1) > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(500);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill first name", ex.getMessage());
            }
            
            try {
                //insertion
                helperClass.editDataInTextInputWithLabel(webDriver, null, "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.row > div:nth-child(2) > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div > div > div > div > div > div:nth-child(2) > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(500);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill insertion", ex.getMessage());
            }
            
            try {
                //last name
                helperClass.editDataInTextInputWithLabel(webDriver, null, "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.row > div:nth-child(2) > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div > div > div > div > div > div:nth-child(3) > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(500);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill last name", ex.getMessage());
            }
            
            try {
                //initials
                helperClass.editDataInTextInputWithLabel(webDriver, null, "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.row > div:nth-child(2) > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div > div > div > div > div > div:nth-child(4) > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(500);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill initials", ex.getMessage());
            }
            
            try {
                //languages
                helperClass.editDataInTextInputWithLabel(webDriver, null, "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.row > div:nth-child(2) > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div > div > div > div > div > div:nth-child(5) > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(500);
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: test switching languages"); 
                webDriver.findElement(By.className("v-item--active")).click();
                Thread.sleep(1500);
                webDriver.findElement(By.className("v-item--active")).click();
                Thread.sleep(1500);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to switch languages", ex.getMessage());
            }
            
            try {
                //click on TAB contact_information
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: click on tab Contact information"); 
                webDriver.findElement(By.cssSelector("#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.row > div:nth-child(2) > div > div.v-item-group.theme--light.v-slide-group.v-tabs-bar.primary--text > div.v-slide-group__wrapper > div > div:nth-child(3)")).click();
                Thread.sleep(500);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to click on second tab", ex.getMessage());
            }
            
            try {
                //phone number
                helperClass.editDataInTextInputWithLabel(webDriver, "123456789", "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.row > div:nth-child(2) > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div.v-window-item.v-window-item--active > div > div > div > div > div:nth-child(1) > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(500);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill phone number", ex.getMessage());
            }
            
            try {
                //mobile number
                helperClass.editDataInTextInputWithLabel(webDriver, "987654321", "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.row > div:nth-child(2) > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div.v-window-item.v-window-item--active > div > div > div > div > div:nth-child(2) > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(500);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill mobile number", ex.getMessage());
            }
            
            try {
                //fax number
                helperClass.editDataInTextInputWithLabel(webDriver, "1234", "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.row > div:nth-child(2) > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div.v-window-item.v-window-item--active > div > div > div > div > div:nth-child(3) > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(500);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill fax number", ex.getMessage());
            }
            
            try {
                //email
                WebElement inputForEmail = webDriver.findElement(By.cssSelector("#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.row > div:nth-child(2) > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div.v-window-item.v-window-item--active > div > div > div > div > div.row > div > form > div > div.v-input__control > div.v-input__slot > div > input"));                
                String placeholderInEmailInput = inputForEmail.getAttribute("placeholder");
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: fill data ---test2@pernexus.org--- in element " + placeholderInEmailInput); 
                inputForEmail.sendKeys("test2@pernexus.org");
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: after filling found data ---" + inputForEmail.getAttribute("value") + "---\r\n"); 
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: try to click on SAVE EMAIL BUTTON"); 
                Thread.sleep(500);
                try {
                    WebElement buttonToSaveEmail = webDriver.findElement(By.cssSelector("#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.row > div:nth-child(2) > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div.v-window-item.v-window-item--active > div > div > div > div > div.row > div > form > div > div.v-input__append-outer > div > button"));
                    buttonToSaveEmail.click();
                } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to click on SAVE EMAIL BUTTON", ex.getMessage());
            }
                Thread.sleep(500);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill input for email", ex.getMessage());
            }
            
            //address - click and dropdown            
            try {                
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "\r\nWork: Try to fill input Address");
                helperClass.selectOneElementFromDropdownAddressInHelper(webDriver);
                Thread.sleep(1500);  
                webDriver.findElement(By.cssSelector("#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.row > div:nth-child(2) > div > div.v-item-group.theme--light.v-slide-group.v-tabs-bar.primary--text > div.v-slide-group__wrapper > div > div:nth-child(3)")).click();                                         
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill adresses", ex.getMessage());
            }   
            
            try {
                WebElement inputMap = webDriver.findElement(By.id("map"));
                System.out.println(inputMap);
                String address = inputMap.getAttribute("value");
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Data in the input Address:" + address + "\r\n");
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to find address data", ex.getMessage());
            }  
            //input Address END
            
            Thread.sleep(1500);
            
            try {
                //country of birth  - click and dropdown
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "\r\nWork: attempt to change country of birth"); 
                helperClass.workWithDropdownElementCitiesNation(webDriver, "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.row > div:nth-child(2) > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div.v-window-item.v-window-item--active > div > div > div > div > div:nth-child(6)", "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.row > div:nth-child(2) > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div.v-window-item.v-window-item--active > div > div > div > div > div:nth-child(6) > div > div.v-input__slot > div.v-select__slot > ", fileToWriteLogsOfTesting);
                Thread.sleep(500);  
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to change country of birth", ex.getMessage());
            }  
            
            try {
                //place of birth
                helperClass.editDataInTextInputWithLabel(webDriver, "PP", "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.row > div:nth-child(2) > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div.v-window-item.v-window-item--active > div > div > div > div > div:nth-child(7) > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(500); 
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to change place of birth", ex.getMessage());
            }             
            
            try {
                //nationality  - click and dropdown
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "\r\nWork: attempt to change nationality"); 
                helperClass.workWithDropdownElementCitiesNation(webDriver, "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.row > div:nth-child(2) > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div.v-window-item.v-window-item--active > div > div > div > div > div:nth-child(8)", "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.row > div:nth-child(2) > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div.v-window-item.v-window-item--active > div > div > div > div > div:nth-child(8) > div > div.v-input__slot > div.v-select__slot > ", fileToWriteLogsOfTesting);
                Thread.sleep(500);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to change nationality", ex.getMessage());
            }
            
            try {
                //date of birth  - 11-11-1999
                helperClass.editDataInTextInputWithLabel(webDriver, helperClass.getFormattedDateForTest(), "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.row > div:nth-child(2) > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div.v-window-item.v-window-item--active > div > div > div > div > div.v-input.theme--light.v-text-field.v-text-field--is-booted.v-text-field--placeholder > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(500);  
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill date of birth", ex.getMessage());
            }        
            
            try {
                //gender  - click and dropdown
                webDriver.findElement(By.cssSelector("#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.row > div:nth-child(2) > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div.v-window-item.v-window-item--active > div > div > div > div > div:nth-child(10)")).click();
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "\r\nWork: attempt to change gender"); 
                Thread.sleep(500); 
                helperClass.selectOneElementFromDropdownInHelper(webDriver, fileToWriteLogsOfTesting);
                Thread.sleep(500); 
                String valueOfGender = webDriver.findElement(By.cssSelector("#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.row > div:nth-child(2) > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div.v-window-item.v-window-item--active > div > div > div > div > div.v-input.v-input--is-label-active.v-input--is-dirty.v-input--is-focused.theme--light.v-text-field.v-text-field--is-booted.v-select.primary--text > div > div.v-input__slot > div.v-select__slot > div.v-select__selections > div")).getText();
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: gender property:  " + helperClass.leftDemarkator + valueOfGender + helperClass.rightDemarkator + "\r\n"); 
                } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to change gender", ex.getMessage());
            }       
            
            Thread.sleep(500);
            try {
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: try to click save button"); 
                webDriver.findElement(By.cssSelector("#inspire > div > main > div > div > div > div:nth-child(2) > div > div > header > div > button")).click();
                Thread.sleep(1500);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to click save button", ex.getMessage());
            }  
            
            try {
                //find div container with message result of saving START
                String systemMessage = helperClass.getSystemMessage(webDriver);
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "\r\nWork: system message: " + systemMessage); 
                //find div container with message result of saving END
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to find message from site", ex.getMessage());
            }              
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: END"); 
            Thread.sleep(15000);            
            } catch (Exception ex) {
            System.out.println(ex.getMessage());
            helperClass.printToFileAndConsoleInformation(fileToWriteErrorLogOfTesting, "ERROR: Error in main try block of ProfileEditClass"); 
        } finally {
            webDriver.close();
            webDriver.quit();
        }
    }
    
}
