/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solidsolutions.test;


import com.google.common.collect.Ordering;
import com.mycompany.perscriptumtest.MainFraimClass;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 *
 * @author user
 */
public class SortCandidatesClass {
    
    public HelperClass helperClass = new HelperClass();
    final boolean DEFAULT = true;
    final boolean REVERSED = false;
    public CredentialsClass credentialsClass;
    private String pathToLogFileFolder;
    private String osName;
    public String dateTimeOfSession;
    public File fileToWriteLogsOfTesting;
    public File fileToWriteErrorLogOfTesting;
    public WebDriver webDriver = null;
            
    public SortCandidatesClass(String pathToFileFolderIn, String osNameIn) {
        this.pathToLogFileFolder = pathToFileFolderIn;
        this.osName = osNameIn;
    }
    
    public void sortCandidates()
    {
        credentialsClass = new CredentialsClass();        
        dateTimeOfSession = helperClass.getDateInStringForWindowsLinux();    
        String fileName = "";
        String fileNameERRORS = "";
        
        fileName = this.pathToLogFileFolder + "testCandidatesSortLogFile_" + dateTimeOfSession + ".txt";
        fileNameERRORS = this.pathToLogFileFolder + "testCandidatesSortLogFile_ERRORS_" + dateTimeOfSession + ".txt";        
        
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
            
            JavascriptExecutor js = (JavascriptExecutor)webDriver;
            webDriver.manage().window().maximize();
            webDriver.get("https://perscriptum-dev.herokuapp.com/"); 
            Thread.sleep(1500);
            WebElement login = helperClass.safeFindElement(webDriver, "input-11", "id");
            WebElement passwd = helperClass.safeFindElement(webDriver, "input-14", "id");
            WebElement btnLogin = helperClass.safeFindElement(webDriver, "/html/body/div[1]/div/div/div[2]/div/div/div[2]/div/form/button", "xpath");
            login.sendKeys(credentialsClass.emailToLogin);
            passwd.sendKeys(credentialsClass.passwordToLogin);
            Thread.sleep(500);
            helperClass.safeClickOnElement(btnLogin);
            Thread.sleep(2500);  
            webDriver.get("https://perscriptum-dev.herokuapp.com/candidates");
            Thread.sleep(2500);  
            WebElement tableContainer = helperClass.safeFindElement(webDriver, "v-data-table__wrapper", "className");
            Thread.sleep(500);
            List<WebElement> listOfTableRows = tableContainer.findElements(By.tagName("tr"));
            List<WebElement> listOfFlagsToSort = listOfTableRows.get(0).findElements(By.tagName("th"));
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Try to click on Select/Deselect ALL");
            listOfFlagsToSort.get(0).click();   
            Thread.sleep(1000);
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Try to click on Select/Deselect ALL");
            listOfFlagsToSort.get(0).click();   
            Thread.sleep(1000);
            for (int i = 1; i < (listOfFlagsToSort.size() - 1); i++) {           
                Thread.sleep(1500);
                listOfFlagsToSort.get(i).click();             
                Thread.sleep(1500);
                if (checkisTableSortedByCurrentColumn(webDriver, i, DEFAULT)) {
                    helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: click on element N " + i + " - Table sorting correct");
                } else {
                    helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: click on element N " + i + " - ERROR table sorting");
                }            
                Thread.sleep(1500);
                listOfFlagsToSort.get(i).click();             
                Thread.sleep(1500); 
                if (checkisTableSortedByCurrentColumn(webDriver, i, REVERSED)) {
                    helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: click on element N " + i + " - Table sorting correct");
                } else {
                    helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: click on element N " + i + " - ERROR table sorting");
                }       
            
                Thread.sleep(1500);
                listOfFlagsToSort.get(i).click(); 
                Thread.sleep(5000);
            }
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: End");
            Thread.sleep(5000);            
            } catch (Exception ex) {
            System.out.println(ex.getMessage());
            helperClass.printToFileAndConsoleInformation(fileToWriteErrorLogOfTesting, "ERROR: Error in main try block of SortCandidatesClass"); 
        } finally {
            webDriver.close();
            webDriver.quit();
        }
    }

    private boolean checkisTableSortedByCurrentColumn(WebDriver webDriver, int numberOfColumnTocheck , boolean flagOfSorting) {
        String nameOfSorting = null;
        if (flagOfSorting) {
            nameOfSorting = " in default order";
        } else {
            nameOfSorting = " in reverced order";
        }
        WebElement tableContainer = helperClass.safeFindElement(webDriver, "v-data-table__wrapper", "className");
        List<WebElement> listOfTableRows = tableContainer.findElements(By.tagName("tr")); 
        List<WebElement> listOfTableHeaders = listOfTableRows.get(0).findElements(By.tagName("th"));        
        helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Testing sorting of the column " + listOfTableHeaders.get(numberOfColumnTocheck).getText() + nameOfSorting);        
        String[] listToCheckArr = new String[listOfTableRows.size()-1];
        for (int i = 1; i < listOfTableRows.size(); i++) {
            List<WebElement> listOfTableDatas = listOfTableRows.get(i).findElements(By.tagName("td"));
            listToCheckArr[i-1] = listOfTableDatas.get(numberOfColumnTocheck).getText();
        }        

        //printArray(listToCheckArr);        
        
        String[] tmp = listToCheckArr.clone();
        if (flagOfSorting) {
            Arrays.sort(tmp);
        } else {
            Arrays.sort(tmp, Collections.reverseOrder());
        }        
        
        boolean isArrayEquals = Arrays.equals(listToCheckArr, tmp);                
        return isArrayEquals;
    }
    
    public void printArray(String[] arrayToPrint)
    {
        for (int i = 0; i < arrayToPrint.length; i++) {
            System.out.print(arrayToPrint[i] + " - ");
        }
        System.out.println();
    }
    
}
