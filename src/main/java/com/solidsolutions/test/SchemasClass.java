/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solidsolutions.test;

/**
 *
 * @author maxim
 */

import com.mycompany.perscriptumtest.MainFraimClass;
import java.io.File;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 *
 * @author user
 */
public class SchemasClass {
    
    public static String SchemaName = "TestSchemaName";
    public static String SchemaSectionName = "TestSectionName";
    public static String SchemaCode = "TestCode";
    public static String SchemaNumber = "TestSectionNumber";
    public static String SchemaValidity = "TestShemaValidity";
    public static String SchemaDescription = "TestShemaDescription";
    public static String SchemaExamName = "TestShemaExamName";
    public static String SchemaTextInIframe = "Test_Text_Text_Text";
    private String pathToLogFileFolder;
    public HelperClass helperClass = new HelperClass();
    public CredentialsClass credentialsClass;
    public File fileToWriteLogsOfTesting;
    public File fileToWriteErrorLogOfTesting;
    public WebDriver webDriver = null;
    public String dateTimeOfSession;
    private String osName;
    
    public SchemasClass(String pathToFileFolderIn, String osNameIn) {
        this.pathToLogFileFolder = pathToFileFolderIn;
        this.osName = osNameIn;
    }
    
    public void createSchema() throws InterruptedException
    {
        credentialsClass = new CredentialsClass();
        dateTimeOfSession = helperClass.getDateInStringForWindowsLinux();    
        String fileName = "";
        String fileNameERRORS = "";
        
        fileName = this.pathToLogFileFolder + "testShemasCreationLogFile_" + dateTimeOfSession + ".txt";
        fileNameERRORS = this.pathToLogFileFolder + "testShemasCreationLogFile_ERRORS_" + dateTimeOfSession + ".txt";        
        
        try {
            fileToWriteLogsOfTesting = new File(fileName);
            fileToWriteErrorLogOfTesting = new File(fileNameERRORS);
            System.out.println("Path to logfile:" + fileName);
        } catch (Exception exx) {
            System.out.println(exx.getMessage());
            System.out.println("Error file creation, test log will be only in terminal");
        }
        
        helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Shema creation testing starts at: " + dateTimeOfSession +" OS: " + osName);        
        
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
            //Logger.global.log(new LogRecord(Level.INFO, "Login complete"));

            webDriver.get("https://perscriptum-dev.herokuapp.com/schemes");
            Thread.sleep(1000);

            WebElement createBtn = webDriver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/main/div/div/div/div[2]/div/div/div/div/header/div/button[2]"));
            createBtn.click();
            Thread.sleep(400);
            WebElement inputForLocationName = webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div > div > div.v-input__slot > div > input"));
            Thread.sleep(400);
            inputForLocationName.sendKeys(SchemaName);
            webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > header > div > button.v-btn.v-btn--text.theme--dark.v-size--small")).click();
            Thread.sleep(2000);

            
            
            //Section name
            String dataToFillInInput = "" + helperClass.getRandomDigit(999,9999);
            try {          
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: try to fill Section Name");
                helperClass.editDataInTextInputWithLabel(webDriver, SchemaSectionName, "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.v-tabs.theme--light > div.v-window.v-item-group.theme--light.v-tabs-items > div > div > div > div > div > div > div > div:nth-child(1) > div:nth-child(2) > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(500); 
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill Section Name", ex.getMessage());
            } 
            
            //Code        
            try {          
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: try to fill Code");
                helperClass.editDataInTextInputWithLabel(webDriver, SchemaCode, "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.v-tabs.theme--light > div.v-window.v-item-group.theme--light.v-tabs-items > div > div > div > div > div > div > div > div:nth-child(1) > div:nth-child(3) > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(500); 
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill Code", ex.getMessage());
            } 
            
            //Number        
            try {          
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: try to fill Number");
                helperClass.editDataInTextInputWithLabel(webDriver, SchemaNumber, "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.v-tabs.theme--light > div.v-window.v-item-group.theme--light.v-tabs-items > div > div > div > div > div > div > div > div:nth-child(1) > div:nth-child(4) > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(500); 
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill Number", ex.getMessage());
            } 
            
            //Validity        
            try {          
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: try to fill Validity");
                helperClass.editDataInTextInputWithLabel(webDriver, SchemaValidity, "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.v-tabs.theme--light > div.v-window.v-item-group.theme--light.v-tabs-items > div > div > div > div > div > div > div > div:nth-child(1) > div.row > div.col.col-2 > div > div > div.v-input__slot > div.v-text-field__slot > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(500); 
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill Validity", ex.getMessage());
            } 
            
            //Description        
            try {          
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: try to fill Description");
                helperClass.editDataInTextInputWithLabel(webDriver, SchemaDescription, "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.v-tabs.theme--light > div.v-window.v-item-group.theme--light.v-tabs-items > div > div > div > div > div > div > div > div:nth-child(1) > div.v-input.v-textarea.theme--light.v-text-field.v-text-field--is-booted > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, "textarea");
                Thread.sleep(500); 
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill Description", ex.getMessage());
            } 

            //ExamName        
            try {          
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: try to fill ExamName");
                helperClass.editDataInTextInputWithLabel(webDriver, SchemaExamName, "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.v-tabs.theme--light > div.v-window.v-item-group.theme--light.v-tabs-items > div > div > div > div > div > div > div > div:nth-child(2) > div.v-input.theme--light.v-text-field.v-text-field--is-booted > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(500); 
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill ExamName", ex.getMessage());
            } 
            
            
            Thread.sleep(500);
            //select tags START
            try {                
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Try to select Tag");
                WebElement tagsContainer = webDriver.findElement(By.cssSelector("#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div.v-tabs.theme--light > div.v-window.v-item-group.theme--light.v-tabs-items > div > div > div > div > div > div > div > div:nth-child(2) > div:nth-child(3) > div.v-input.mt-5.v-input--dense.theme--light.v-text-field.v-text-field--single-line.v-text-field--is-booted.v-text-field--enclosed.v-text-field--outlined.v-select.v-select--chips.v-select--chips--small.v-select--is-multi.v-autocomplete"));
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
            //select tags END


            //click to show dropdown
            try {                
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Try to click on Dropdown shemeowner");
                webDriver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/main/div/div/div/div[2]/div/div/div[1]/div[2]/div/div/div/div/div/div/div/div[1]/div[5]")).click();      
                //Dropdown shemeowner
                //helperClass.selectOneElementFromDropdownInHelper(webDriver);  //EMPTY!
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to find Tags!", ex.getMessage());
            }
                  
            webDriver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/main/div/div/div/div[2]/div/div/div[1]/div[1]/div[2]/div/div[2]")).click();
                        
            Thread.sleep(1500);
            //Dropdown examenbureau
            try {                
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Try to click on Dropdown examenbureau");
                webDriver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/main/div/div/div/div[2]/div/div/div[1]/div[2]/div/div/div/div/div/div/div/div[1]/div[6]")).click();        
                Thread.sleep(400);
                helperClass.selectOneElementFromDropdownInHelper(webDriver, fileToWriteLogsOfTesting);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to click Dropdown examenbureau!", ex.getMessage());
            }
            webDriver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/main/div/div/div/div[2]/div/div/div[1]/div[1]/div[2]/div/div[2]")).click();
            Thread.sleep(500);
            
            //Dropdown Validity choise
            try {                
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Try to click on Validity choise");
                webDriver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/main/div/div/div/div[2]/div/div/div[1]/div[2]/div/div/div/div/div/div/div/div[1]/div[7]")).click();
                helperClass.selectOneElementFromDropdownInHelper(webDriver, fileToWriteLogsOfTesting);
                Thread.sleep(400);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to click Dropdown examenbureau!", ex.getMessage());
            }
            webDriver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/main/div/div/div/div[2]/div/div/div[1]/div[1]/div[2]/div/div[2]")).click();
            Thread.sleep(500);

            //end result
            try {                
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Try to click on end result");
                webDriver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/main/div/div/div/div[2]/div/div/div[1]/div[2]/div/div/div/div/div/div/div/div[1]/div[8]")).click();
                Thread.sleep(400);
                helperClass.selectOneElementFromDropdownInHelper(webDriver, fileToWriteLogsOfTesting);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to click Dropdown examenbureau!", ex.getMessage());
            }
            
            Thread.sleep(500);
            
            //Optional field - exist only if END_RESULT is 1
            WebElement alternativeSertificateChoise = null;
            try {
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Try to find optional field TEMPLATE");  
                Thread.sleep(500);
                alternativeSertificateChoise = helperClass.safeFindElement(webDriver, "/html/body/div[1]/div[1]/div/div/main/div/div/div/div[2]/div/div/div[1]/div[2]/div/div/div/div/div/div/div/div[1]/div[9]", "xpath");
                if (alternativeSertificateChoise != null) {
                    alternativeSertificateChoise.click();
                    helperClass.selectOneElementFromDropdownInHelper(webDriver, fileToWriteLogsOfTesting);  
                    helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Field TEMPLATE found!");  
                } else {
                    helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Field TEMPLATE NOT found!"); 
                }
            }
            catch(NoSuchElementException nex) {
                System.out.println(nex.getMessage());
            }
            catch(Exception ex) {
                System.out.println(ex.getMessage());
            }

            try {
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Try to fill iframe");  
                webDriver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/main/div/div/div/div[2]/div/div/div[1]/div[2]/div/div/div/div/div/div/div/div[2]/div[2]/div/div[1]/div[2]/div[1]/iframe")).sendKeys("Test text to iframe");
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill iframe", ex.getMessage());
            }
            
            Thread.sleep(1000);
            webDriver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/main/div/div/div/div[2]/div/div/div[1]/div[1]/div[2]/div/header/div/button[2]")).click();
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: END");            
            Thread.sleep(15000);
            } catch (Exception ex) {
            System.out.println(ex.getMessage());
            helperClass.printToFileAndConsoleInformation(fileToWriteErrorLogOfTesting, "ERROR: Error in main try block of SchemasClass"); 
        } finally {
            webDriver.close();
            webDriver.quit();
        }
    }
    
    
}