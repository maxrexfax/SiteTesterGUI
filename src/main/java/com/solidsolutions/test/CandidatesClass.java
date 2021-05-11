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
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CandidatesClass {
    public int phoneNumber = 1234567890;
    public int numberOfCandidate = 0;
    public HelperClass helperClass = new HelperClass();
    public String dateTimeOfSession;
    public File fileToWriteLogsOfTesting;
    public File fileToWriteErrorLogOfTesting;
    public WebDriver webDriver = null;
    public String mainUrl = "https://perscriptum-dev.herokuapp.com/";
    public String[] candidateTypes = {"", "candidates", "assessors", "employees", "contacts"};
    public CredentialsClass credentialsClass;
    private String pathToLogFileFolder;
    private String osName;
    private String firstName = "";
    private String lastName = "";
    String fullName = null;
    
    public CandidatesClass(String pathToFileFolderIn, String osNameIn) {
        this.pathToLogFileFolder = pathToFileFolderIn;
        this.osName = osNameIn;
    }
    
    
    public CandidatesClass(String pathToFileFolderIn, String osNameIn, int typeOfUser) {
        this.numberOfCandidate = typeOfUser;
        this.pathToLogFileFolder = pathToFileFolderIn;
        this.osName = osNameIn;
    }
    
    public void createCandidate() throws InterruptedException{        
        firstName = "FN_" + UUID.randomUUID().toString() + "_" + helperClass.getRandomDigit(99,999);
        firstName = firstName.substring(0, 12);
        lastName = "LN_" + UUID.randomUUID().toString() + "_" + helperClass.getRandomDigit(99,999);
        lastName = lastName.substring(0, 12);
        fullName = lastName + ", " + firstName;
        credentialsClass = new CredentialsClass();
        dateTimeOfSession = helperClass.getDateInStringForWindowsLinux();    
        String fileName = "";
        String fileNameERRORS = "";
        
        fileName = this.pathToLogFileFolder + "testCandidatesCreationLogFile_" + dateTimeOfSession + ".txt";
        fileNameERRORS = this.pathToLogFileFolder + "testCandidatesCreationLogFile_ERRORS_" + dateTimeOfSession + ".txt";        
        
        try {
            fileToWriteLogsOfTesting = new File(fileName);
            fileToWriteErrorLogOfTesting = new File(fileNameERRORS);
            System.out.println("Path to logfile:" + fileName);
        } catch (Exception exx) {
            System.out.println(exx.getMessage());
            System.out.println("Error file creation, test log will be only in terminal");
        }
        
        helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Candidate creation testing starts at: " + dateTimeOfSession +" OS: " + osName);
        
        try {
            if(MainFraimClass.CURRENT_BROWSER == MainFraimClass.CHANGE_CHROME_BROWSER) {
                webDriver = new ChromeDriver();
            } else {
                webDriver = new FirefoxDriver();
            }
            //login to site START
            JavascriptExecutor js = (JavascriptExecutor)webDriver;
            webDriver.manage().window().maximize();
            helperClass.writeStringToFile(fileToWriteLogsOfTesting, "Work: go to url https://perscriptum-dev.herokuapp.com");
            webDriver.get(mainUrl);
            Thread.sleep(1500); 
            helperClass.safeFindElement(webDriver, "#materialpro > div > div > div.d-flex.align-center.col-lg-5.col-xl-6.col-12 > div > div > div.v-item-group.theme--light.v-btn-toggle > button:nth-child(2)", "cssSelector").click();
            Thread.sleep(2500);
            WebElement login = webDriver.findElement(By.id("input-11"));
            WebElement passwd = webDriver.findElement(By.id("input-14"));
            WebElement btnLogin = webDriver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div/div[2]/div/form/button"));
            login.sendKeys(credentialsClass.emailToLogin);
            passwd.sendKeys(credentialsClass.passwordToLogin);
            Thread.sleep(500);            
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: trying to login with email \"test2@pernexus.org\" and password \"***\" ");
            btnLogin.click();
            Thread.sleep(1500);  
            //login to site END

            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: go to url https://perscriptum-dev.herokuapp.com/candidates");
            webDriver.get("https://perscriptum-dev.herokuapp.com/candidates");
            Thread.sleep(3000);
            webDriver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/main/div/div/div/div[2]/div/div/div/div/header/div/button[2]")).click();//create new candidate
            Thread.sleep(500);
            //Click on 4 types of candidates START
            webDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div/div/div[2]/div/div/div/div/div[1]/div/div[2]/label")).click();
            Thread.sleep(500);
            webDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div/div/div[2]/div/div/div/div/div[1]/div/div[3]/label")).click();
            Thread.sleep(500);
            webDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div/div/div[2]/div/div/div/div/div[1]/div/div[4]/label")).click();
            Thread.sleep(500);
            webDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div/div/div[2]/div/div/div/div/div[1]/div/div[1]/label")).click();
            //Click on 4 types of candidates END
            Thread.sleep(1500);
            if (numberOfCandidate == 0) {//if is not set then random
                numberOfCandidate = helperClass.getRandomDigit(1, 4);
            } else if (numberOfCandidate < 1 || numberOfCandidate > 4) {
                numberOfCandidate = 1;
            }
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: start to create type of user: " + candidateTypes[numberOfCandidate]);
            //  
            webDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div/div/div[2]/div/div/div/div/div[1]/div/div["+ numberOfCandidate +"]/label")).click();
            Thread.sleep(300);
            try {
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Try to click on Add person contact info");
                webDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div/div/div[3]/div")).click();//menu Add person contact info
                Thread.sleep(300);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to click on Add person contact info", ex.getMessage());
            }
            
            //first name
            try {                
                helperClass.editDataInTextInputWithLabel(webDriver, firstName, "#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(4) > div > div > div:nth-child(2) > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(300); 
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill first name", ex.getMessage());
            }            
            
            //second name
            String secondName = new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
            try {                
                helperClass.editDataInTextInputWithLabel(webDriver, secondName, "#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(4) > div > div > div:nth-child(3) > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(300); 
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill second name", ex.getMessage());
            }
           
            //last name
            try {                
                helperClass.editDataInTextInputWithLabel(webDriver, lastName, "#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(4) > div > div > div:nth-child(4) > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(300); 
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill last name", ex.getMessage());
            }
            
            //initials
            String dataToFillInInput = "II_" + helperClass.getRandomDigit(99,999);
            try {                
                helperClass.editDataInTextInputWithLabel(webDriver, dataToFillInInput, "#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(4) > div > div > div:nth-child(5) > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(300); 
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill initials", ex.getMessage());
            }  
            
            //date of birth
            dataToFillInInput = helperClass.getFormattedDateForTest();
            try {                
                helperClass.editDataInTextInputWithLabel(webDriver, dataToFillInInput, "#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(4) > div > div > div.v-input.theme--light.v-text-field.v-text-field--is-booted.v-text-field--placeholder > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(300); 
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill date of birth", ex.getMessage());
            } 
                        
            //city
            dataToFillInInput = "City_" + helperClass.getRandomDigit(99,999);
            try {                
                helperClass.editDataInTextInputWithLabel(webDriver, dataToFillInInput, "#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(4) > div > div > div:nth-child(7) > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(500); 
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill city", ex.getMessage());
            } 
            
            //phone
            dataToFillInInput = "1234" + helperClass.getRandomDigit(9999,99999);
            try {                
                helperClass.editDataInTextInputWithLabel(webDriver, dataToFillInInput, "#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(4) > div > div > div:nth-child(8) > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(500); 
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill phone", ex.getMessage());
            } 
            
            try {
                //address 
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: try to fill Address");   
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
            
            Thread.sleep(300);   
            //Additional field in address
            dataToFillInInput = "" + helperClass.getRandomDigit(999,9999);
            try {           
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: try to fill Additional field in address");     
                helperClass.editDataInTextInputWithLabel(webDriver, dataToFillInInput, "#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(4) > div > div > form > div.row > div.col-sm-4.col-md-2.col-12 > div > div > div.v-input__slot > div > ", fileToWriteLogsOfTesting, null);
                Thread.sleep(500); 
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill Additional field in address", ex.getMessage());
            }   
                    
            //email
            dataToFillInInput = "email_" + helperClass.getRandomDigit(99,999) + "@mail.com";  
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: fill EMAIL " + dataToFillInInput);
            try {
                //email
                WebElement inputForEmail = helperClass.safeFindElement(webDriver, "#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(4) > div > div > div.row > div > form > div.v-input.v-input--has-state.theme--light.v-text-field.v-text-field--is-booted.v-text-field--placeholder.error--text > div.v-input__control > div.v-input__slot > div > input", "cssSelector");
                String placeholderInEmailInput = inputForEmail.getAttribute("placeholder");
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: (EMAIL) fill data " + helperClass.leftDemarkator + dataToFillInInput + helperClass.rightDemarkator + " in element " + placeholderInEmailInput); 
                inputForEmail.sendKeys(dataToFillInInput);
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: after filling found data " + helperClass.leftDemarkator + inputForEmail.getAttribute("value") + helperClass.rightDemarkator + "\r\n"); 
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: try to click on SAVE EMAIL BUTTON"); 
                Thread.sleep(500);
                try {
                    WebElement buttonToSaveEmail = webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(4) > div > div > div.row > div > form > div.v-input.theme--light.v-text-field.v-text-field--is-booted.v-text-field--placeholder > div.v-input__append-outer > div > button"));
                    buttonToSaveEmail.click();
                    helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: click on SAVE EMAIL BUTTON"); 
                } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to click on SAVE EMAIL BUTTON", ex.getMessage());
            }
                Thread.sleep(500);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill input for email", ex.getMessage());
            }
            
                     
            
            try {
                WebElement thirdPunktOfMenu = helperClass.safeFindElement(webDriver, "#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(5)", "cssSelector");
                WebElement thirdPunktOfMenySpan = helperClass.safeFindElement(webDriver, "#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(5) > div > span", "cssSelector");
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: click on " + thirdPunktOfMenySpan.getText());
                helperClass.safeClickOnElement(thirdPunktOfMenu);
                Thread.sleep(500);
                setInfoInThirdStage(numberOfCandidate, webDriver);
                Thread.sleep(500);               
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill data in third part of menu", ex.getMessage());
            }
            
            try {
                Thread.sleep(500);
                WebElement fourthPunktOfMenu = helperClass.safeFindElement(webDriver, "#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(7)", "cssSelector");
                WebElement fourthPunktOfMenuDivText = helperClass.safeFindElement(webDriver, "#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(7) > div", "cssSelector");
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: click on " + fourthPunktOfMenuDivText.getText());
                helperClass.safeClickOnElement(fourthPunktOfMenu);
                Thread.sleep(500);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to click on fourth part of menu", ex.getMessage());
            }
                        
            try {
                WebElement inputWithCompanies = webDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div/div/div[8]/div/div/div"));//dropdown with companies
                inputWithCompanies.click();
                Thread.sleep(500);
                WebElement tagsList = webDriver.findElement(By.xpath("//*[contains(@class,'v-menu__content--fixed menuable__content__active')]"));
                Thread.sleep(500);
                helperClass.selectOneElementFromDropdownInHelper(webDriver, fileToWriteLogsOfTesting);        
                Thread.sleep(1000);
                webDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div/div/div[7]/div")).click();//menu attach_to_companies
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill Companies", ex.getMessage());
            }
            
            Thread.sleep(1000); 
            
            try {
                webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > header > div > button.v-btn.v-btn--text.theme--dark.v-size--default")).click();
                Thread.sleep(1500);
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: pressed button save, waiting for system message");
                Thread.sleep(1000);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to click save button", ex.getMessage());
            }
            
             //find div container with message result of saving START
            String systemMessage = helperClass.getSystemMessage(webDriver);
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: system message: " + systemMessage); 
            //find div container with message result of saving END
            
            Thread.sleep(2500);
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "\r\nWork: Redirencting to " + mainUrl + candidateTypes[numberOfCandidate]);
            goToCorrespondingUrl(numberOfCandidate);
            try {
                WebElement searchInput = helperClass.safeFindElement(webDriver, "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div > div > header > div > div.v-toolbar__title > div > div > div > div.v-text-field__slot > input", "cssSelector");
                Thread.sleep(500);
                searchInput.sendKeys(Keys.CONTROL + "a");
                Thread.sleep(500);
                searchInput.sendKeys(Keys.DELETE);
                Thread.sleep(500);
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: filling user credentials:   " + helperClass.leftDemarkator + fullName + helperClass.rightDemarkator + " into the search field...");
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Trying to find on page user with credentials: " + helperClass.leftDemarkator + fullName + helperClass.rightDemarkator + " to check is data saved id DB..."); 
                searchInput.sendKeys(fullName);
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to fill date in Search input after saving user", ex.getMessage());
            }
            
            boolean isUserDataFound = false;
            
            try {
                Thread.sleep(1500);
                WebElement containerOnThePage = helperClass.safeFindElement(webDriver, "v-data-table__wrapper", "className");
                List<WebElement> resultTrsOnThePage = containerOnThePage.findElements(By.tagName("tr"));       
                if (resultTrsOnThePage.size() > 0) {
                    for (int i = 1; i < resultTrsOnThePage.size(); i++) {
                        List<WebElement> listOfTableDatas = resultTrsOnThePage.get(i).findElements(By.tagName("td"));
                        helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Check saving fullName=" + fullName); 
                        if (listOfTableDatas.size() > 1) {   
                            for(WebElement EL : listOfTableDatas) {
                                if (EL.getText().contains(fullName)) {
                                    isUserDataFound = true;
                                }
                            }     
                        }
                    }
                }
            } catch (Exception ex) {
                helperClass.writeErrorsToFiles(fileToWriteLogsOfTesting, fileToWriteErrorLogOfTesting, "ERROR: Unable to perform search on the web page", ex.getMessage());
            }
            
            if (isUserDataFound){
                    helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: User saved successfully!"); 
                } else {
                    helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: User saving FAILED!"); 
                }
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: END");
            Thread.sleep(5000);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            helperClass.printToFileAndConsoleInformation(fileToWriteErrorLogOfTesting, "ERROR: Error in main try block of CandidatesClass"); 
        } finally {
            webDriver.close();
            webDriver.quit();
        }
    }

    private void setInfoInThirdStage(int numberOfCandidate, WebDriver webDriver) throws InterruptedException {
        switch (numberOfCandidate) {
            case 1:
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: function setInfoInThirdStage: case 1..."); 
                Thread.sleep(500);
                webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(6) > div > div > div:nth-child(1) > div > div.v-input__slot > div > input")).sendKeys("TestRefData_" + helperClass.getRandomDigit(99,999));
                webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(6) > div > div > div.v-input.v-input--is-label-active.v-input--is-dirty.theme--light.v-text-field.v-text-field--is-booted.v-select")).click();
                helperClass.selectOneElementFromDropdownInHelper(webDriver, fileToWriteLogsOfTesting);
                webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(6) > div > div > div.v-input.theme--light.v-input--selection-controls.v-input--switch > div > div.v-input__slot > label")).click();
                Thread.sleep(500);
                webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(6) > div > div > div.v-input.theme--light.v-input--selection-controls.v-input--switch > div > div.v-input__slot > label")).click();
                break;
            case 2:
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: function setInfoInThirdStage: case 2..."); 
                Thread.sleep(500);
                webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(6) > div > div > div")).click();
                helperClass.selectOneElementFromDropdownInHelper(webDriver, fileToWriteLogsOfTesting);
                webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div.v-stepper__step.v-stepper__step--active.v-stepper__step--editable")).click();
                Thread.sleep(500);
                break;
            case 3:
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: function setInfoInThirdStage: case 3..."); 
                Thread.sleep(500);
                webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(6) > div > div > div > div > div.v-input__slot > div > input")).sendKeys("TestData_" + helperClass.getRandomDigit(99,999));
                Thread.sleep(500);
                break;
            case 4:
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: function setInfoInThirdStage: case 4..."); 
                Thread.sleep(500);
                webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div:nth-child(6) > div > div > div > div.v-input.mt-5.v-input--dense.theme--light.v-text-field.v-text-field--single-line.v-text-field--is-booted.v-text-field--enclosed.v-text-field--outlined.v-select.v-select--chips.v-select--chips--small.v-select--is-multi.v-autocomplete")).click();
                helperClass.selectOneElementFromDropdownInHelper(webDriver, fileToWriteLogsOfTesting);
                webDriver.findElement(By.cssSelector("#materialpro > div.v-dialog__content.v-dialog__content--active > div > div > div > div.v-stepper__step.v-stepper__step--active.v-stepper__step--editable")).click();
                Thread.sleep(500);
                break;
        }
    }


    
    private String checkInputDataFunction(WebElement inputToCheck, String dataSentToInput) 
    {
        if (isInputHasString(inputToCheck, dataSentToInput)) {
            return "Input filled successfully";
        }
        return "Input text is different";
    }
    
    private boolean isInputHasString(WebElement inputToCheck, String dataSentToInput)
    {        
        return inputToCheck.getAttribute("value").contains(dataSentToInput);
    }

    private void goToCorrespondingUrl(int numberOfCandidate) {
        String postfixUrl = "";
        switch (numberOfCandidate) {
            case 1: 
                postfixUrl = "candidates";
            break;
            case 2: 
                postfixUrl = "assessors";
            break;
            case 3: 
                postfixUrl = "employees";
            break;
            case 4: 
                postfixUrl = "contacts";
            break;            
        }
        webDriver.get(mainUrl + postfixUrl);
    }
}
