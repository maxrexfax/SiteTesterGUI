/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solidsolutions.test;

import com.mycompany.perscriptumtest.MainFraimClass;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.function.Predicate;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
 *
 * @author user
 */
public class ManyRequestsClass {
    public int loops;
    public int millisecondsToWait;
    public int millisecondsToWaitStepIncrease;
    public int numberOfPageToTest = 0;
    
    public HelperClass helperClass = new HelperClass();
    public CredentialsClass credentialsClass = new CredentialsClass();
    public String dateTimeOfSession;
    public File fileToWriteLogsOfTesting;
    public File fileToWriteErrorLogOfTesting;
    public WebDriver webDriver = null;
    public String mainUrl = "https://perscriptum-dev.herokuapp.com/";
    //public String mainUrl = "http://maxbarannyk.ru/laravel/public/index.php/login";
    public String urlToTest = "https://perscriptum-dev.herokuapp.com/";
    //public String urlToTest = "http://maxbarannyk.ru/laravel/public/index.php/users/list";
    //public String mainUrl = "http://maxbarannyk.ru/";
    public StringBuffer strBuffer = new StringBuffer("");
    public String inputLoginData = "input-11";
    public String inputLoginData1 = "email";
    public String inputPasswordData = "input-14";
    public String inputPasswordData1 = "password";
    public String buttonToLoginPath = "/html/body/div[1]/div/div/div[2]/div/div/div[2]/div/form/button";
    public String buttonToLoginPath1 = "/html/body/div[1]/main/div/div/div/div/div[2]/form/div[4]/div/button";
    private String pathToLogFileFolder;
    private String osName;
    private String[] arrayOfUrlsToTest = null;
    
    public ManyRequestsClass(String pathToFileFolderIn, String osNameIn, int loopsIn, int firstPauseIn, int increaseIn, int urlToCheckNum) {
        this.pathToLogFileFolder = pathToFileFolderIn;
        this.osName = osNameIn;
        this.loops = loopsIn;
        this.millisecondsToWait = firstPauseIn;
        this.millisecondsToWaitStepIncrease = increaseIn;
        this.numberOfPageToTest = urlToCheckNum;
    }
    
    public void startTest() throws IOException {
        fillAllData();
                
        helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Page to test set to " + arrayOfUrlsToTest[numberOfPageToTest]);
        loginAndTestInternalUrlFunction();
        
    }
    
    private void preLoader() {
        dateTimeOfSession = helperClass.getDateInStringForWindowsLinux();    
        String fileName = "";
        String fileNameERRORS = "";
        
        fileName = this.pathToLogFileFolder + "testManyRequestsSearchLogFile_" + dateTimeOfSession + ".txt";
        fileNameERRORS = this.pathToLogFileFolder + "testManyRequestsSearchLogFile_ERRORS_" + dateTimeOfSession + ".txt";
        System.out.println("Path to logfile:" + fileName);
        
        try {
            fileToWriteLogsOfTesting = new File(fileName);
            fileToWriteErrorLogOfTesting = new File(fileNameERRORS);
        } catch (Exception exx) {
            System.out.println(exx.getMessage());
            System.out.println("Error file creation, testing log will be only in terminal");
        }
        
        helperClass.writeStringToFile(fileToWriteLogsOfTesting, "Page load testing starts at: " + dateTimeOfSession +" OS: " + osName);
    }
    
    private void loginAndTestInternalUrlFunction() {
        
        preLoader();
        try {            
            if(MainFraimClass.CURRENT_BROWSER == MainFraimClass.CHANGE_CHROME_BROWSER) {
                webDriver = new ChromeDriver();
            } else {
                webDriver = new FirefoxDriver();
            }
            
            JavascriptExecutor js = (JavascriptExecutor)webDriver;
            webDriver.manage().window().maximize();
            helperClass.writeStringToFile(fileToWriteLogsOfTesting, "Work: go to url " + mainUrl);
            webDriver.get(mainUrl);
            Thread.sleep(1500); 
            helperClass.safeFindElement(webDriver, "#materialpro > div > div > div.d-flex.align-center.col-lg-5.col-xl-6.col-12 > div > div > div.v-item-group.theme--light.v-btn-toggle > button:nth-child(2)", "cssSelector").click();
            Thread.sleep(2500);
            WebElement login = webDriver.findElement(By.id(inputLoginData));
            WebElement passwd = webDriver.findElement(By.id(inputPasswordData));
            WebElement btnLogin = webDriver.findElement(By.xpath(buttonToLoginPath));
            login.sendKeys(credentialsClass.emailToLogin);
            passwd.sendKeys(credentialsClass.passwordToLogin);
            Thread.sleep(500);            
            helperClass.writeStringToFile(fileToWriteLogsOfTesting, "Work: trying to login");
            btnLogin.click();
            Thread.sleep(2500);
            //first get to url and get it length
            urlToTest = mainUrl + arrayOfUrlsToTest[numberOfPageToTest];
            webDriver.get(urlToTest);
            Thread.sleep(3500);
            //webDriver.getPageSource();
            final int normalLengthOfPage = webDriver.getPageSource().length();
            String message = "Work: On the url " + urlToTest + " found web page with total length " + normalLengthOfPage + " after delay 3500MS";
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, message);
            Thread.sleep(1500);              
            
            String message2 = "Work: URL TO TEST " + urlToTest;
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, message2);
            testLoopLoadFunction(normalLengthOfPage);            
            
            helperClass.writeStringToFile(fileToWriteLogsOfTesting, "Work: Accumulated message: \r" + strBuffer.toString());
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: END");     
            Thread.sleep(5000);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            helperClass.printToFileAndConsoleInformation(fileToWriteErrorLogOfTesting, "ERROR: Error in main try block of ManyRequestsClass"); 
                } finally {
                    webDriver.close();
                    webDriver.quit();
                }
    }
    
    

    private void testLoopLoadFunction(int normalLengthOfPage) throws InterruptedException {
        String message = "";
        if (millisecondsToWaitStepIncrease > 0) {
            message = "Work: STARTING ASCENDING TEST WITH NUMBER OF LOOPS: " + loops + " FIRST PAUSE: " + millisecondsToWait + "MS AND INCREASING STEP: " + millisecondsToWaitStepIncrease + "MS";                        
        } else {
            message = "Work: STARTING TEST WITH NUMBER OF LOOPS:" + loops + "  AND PAUSES:" + millisecondsToWait + "MS";
        }
        helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, message); 
        String timeOfStart, timeAfterSleep;
        for (int i = 0; i < loops; i++ ){
                timeOfStart = new SimpleDateFormat("HH.mm.ss.SSS").format(new java.util.Date());
                webDriver.get(urlToTest);
                System.out.println("N " + (i+1) + " Pause=" + millisecondsToWait + "MS  ");
                strBuffer.append("N " + (i+1) + " Pause=" + millisecondsToWait + "MS  ");
                Thread.sleep(millisecondsToWait);
                millisecondsToWait += millisecondsToWaitStepIncrease;                
                timeAfterSleep = new SimpleDateFormat("HH.mm.ss.SSS").format(new java.util.Date());
                int currentLengthOfPage = webDriver.getPageSource().length();
                String tmpInfo = "Start time=" + timeOfStart + " read time=" + timeAfterSleep + "  Sample length was=" + normalLengthOfPage 
                        + "  get length now=" + currentLengthOfPage +  "  difference abs=" + (normalLengthOfPage-currentLengthOfPage) + " or "+ ((normalLengthOfPage-currentLengthOfPage)*100/normalLengthOfPage) + "%";
                System.out.println(tmpInfo);
                strBuffer.append(tmpInfo);
                strBuffer.append("\r");
            }
    }

   

    private void fillAllData() {
        arrayOfUrlsToTest = new String[17];
        arrayOfUrlsToTest[0] = "exams";
        arrayOfUrlsToTest[1] = "candidates";
        arrayOfUrlsToTest[2] = "certificates";
        arrayOfUrlsToTest[3] = "dossiers";
        arrayOfUrlsToTest[4] = "assessors";
        arrayOfUrlsToTest[5] = "companies";
        arrayOfUrlsToTest[6] = "contacts";
        arrayOfUrlsToTest[7] = "schemes";
        arrayOfUrlsToTest[8] = "templates";
        arrayOfUrlsToTest[9] = "locations";
        arrayOfUrlsToTest[10] = "translations";
        arrayOfUrlsToTest[11] = "fields";
        arrayOfUrlsToTest[12] = "employees";
        arrayOfUrlsToTest[13] = "roles";
        arrayOfUrlsToTest[14] = "workflows";
        arrayOfUrlsToTest[15] = "settings";
        arrayOfUrlsToTest[16] = "profile";
    }
}
