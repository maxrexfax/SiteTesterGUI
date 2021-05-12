/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solidsolutions.test;



import com.mycompany.perscriptumtest.MainFraimClass;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 *
 * @author user
 */
public class SearchCandidateClass {
    
    private int numberOfLoops;
    public int counter;
    private int indexOfIdsColumn = 0;
    private int indexOfNamesColumn = 0;
    public HelperClass helperClass = new HelperClass();
    public WebDriver webDriver = null;
    public String[] iDs;
    public String[] Names;
    public String[] Statuses;
    public String[] References;
    public String[] DatesOfBirth;
    public boolean isTestGoOn;
    public String dateTimeOfSession;
    public File fileToWriteLogsOfTesting;
    public File fileToWriteErrorLogOfTesting;
    public CredentialsClass credentialsClass;
    private String pathToLogFileFolder;
    private String osName;
    InputStreamReader isr;
    BufferedReader br;
            
    public SearchCandidateClass(String pathToFileFolderIn, String osNameIn, int loops) {
        this.pathToLogFileFolder = pathToFileFolderIn;
        this.osName = osNameIn;
        this.numberOfLoops = loops;
    }
    
    public void searchCandidate()
    {        
        credentialsClass = new CredentialsClass();
        dateTimeOfSession = helperClass.getDateInStringForWindowsLinux();    
        String fileName = "";
        String fileNameERRORS = "";
        counter = 1;
        
        fileName = this.pathToLogFileFolder + "testCandidatesSearchLogFile_" + dateTimeOfSession + ".txt";
        fileNameERRORS = this.pathToLogFileFolder + "testCandidatesSearchLogFile_ERRORS_" + dateTimeOfSession + ".txt";
        
        try {
            fileToWriteLogsOfTesting = new File(fileName);
            fileToWriteErrorLogOfTesting = new File(fileNameERRORS);
            System.out.println("Path to logfile:" + fileName);
        } catch (Exception exx) {
            System.out.println(exx.getMessage());
            System.out.println("Error file creation, test log will be only in terminal");
        }
        
        helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Candidate search testing starts at: " + dateTimeOfSession +" OS: " + osName);
                
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
            //login to site END
           
            webDriver.get("https://perscriptum-dev.herokuapp.com/candidates");
            Thread.sleep(1500);
            helperClass.safeFindElement(webDriver, "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div > div > div > div.v-data-footer > div.v-data-footer__select > div > div > div", "cssSelector").click();
            helperClass.selectOneElementFromDropdownInHeperAlt(webDriver, 0);//переключение количества юзеров на странице на последнее значение
            Thread.sleep(2500);
            WebElement tableContainer = helperClass.safeFindElement(webDriver, "v-data-table__wrapper", "className");
            Thread.sleep(500);
            List<WebElement> listOfTableRows = tableContainer.findElements(By.tagName("tr"));
            //find number of name and ID columns
            List<WebElement> listOfTableHeads = listOfTableRows.get(0).findElements(By.tagName("th"));
            for (int i = 0; i < listOfTableHeads.size(); i++) {
                if (listOfTableHeads.get(i).getText().contains("ID")) {
                    indexOfIdsColumn = i;
                    System.out.println("Number of ID column=" + indexOfIdsColumn);
                }
                
                if (listOfTableHeads.get(i).getText().contains("Name") || listOfTableHeads.get(i).getText().contains("Naam")) {
                    indexOfNamesColumn = i;
                    System.out.println("Number of name column=" + indexOfNamesColumn);
                }
            }
            iDs = new String[listOfTableRows.size()];
            Names = new String[listOfTableRows.size()];
            
            for (int i = 1; i < listOfTableRows.size(); i++) {
                List<WebElement> listOfTableDatas = listOfTableRows.get(i).findElements(By.tagName("td"));
                iDs[i] = listOfTableDatas.get(indexOfIdsColumn).getText();
                Names[i] = listOfTableDatas.get(indexOfNamesColumn).getText();
            }
            
            isTestGoOn = true;
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Results of testing will be stored in file " + fileName);
            checkDataOnThePage();
            
            helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: END");   
            Thread.sleep(5000);            
            } catch (Exception ex) {
            System.out.println(ex.getMessage());
            helperClass.printToFileAndConsoleInformation(fileToWriteErrorLogOfTesting, "ERROR: Error in main try block of SearchCandidatesClass"); 
        } finally {
            webDriver.close();
            webDriver.quit();
        }
    }
    public void printArray(String[] arrayToPrint)
    {        
        for (int i = 0; i < arrayToPrint.length; i++) {            
            System.out.print(arrayToPrint[i] + " - ");
        }        
        System.out.println();
    }
    
    
    
    public void startThread()
    {
        Thread thread = new Thread(){
            public void run(){            
            }
        };
        thread.start();
    }
    
    public String getCurrentTime()
    {
        return new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
    }

    private void checkDataOnThePage() {
        helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Work: Search testing starting at: " + dateTimeOfSession);
        WebElement searchInput = helperClass.safeFindElement(webDriver, "#inspire > div > main > div > div > div > div:nth-child(2) > div > div > div > div > header > div > div.v-toolbar__title > div > div > div > div.v-text-field__slot > input", "cssSelector");
        WebElement containerOnThePage;
        List <WebElement> resultTrsOnThePage;
        do {      

            if (counter > numberOfLoops) {
                isTestGoOn = false;
            }
            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            if(isTestGoOn){
                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Test N:" + counter + " from:" + numberOfLoops);
                int type = helperClass.getRandomDigit(1,2);
                int index = helperClass.getRandomDigit(1, (iDs.length - 1));
                String dataToSend = "";
                if (type == 1) {
                    dataToSend = iDs[index];
                } else if (type == 2) {
                    dataToSend = Names[index];
                } else if (type == 3) {
                    dataToSend = Statuses[index];
                }  

                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "Running test search for data ---" + dataToSend + "---  time:" + getCurrentTime());
                searchInput.sendKeys(Keys.CONTROL + "a");
                try{
                    Thread.sleep(500);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }                    
                searchInput.sendKeys(Keys.DELETE);
                //searchInput.clear();
                try{
                    Thread.sleep(500);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
                helperClass.safeFillInput(searchInput, dataToSend);
                try{
                    Thread.sleep(2000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
                containerOnThePage = helperClass.safeFindElement(webDriver, "v-data-table__wrapper", "className");
                resultTrsOnThePage = containerOnThePage.findElements(By.tagName("tr")); 
                List<WebElement> listOfTableHeads = resultTrsOnThePage.get(0).findElements(By.tagName("th"));

                helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "For search request ---" + dataToSend + "--- site returns:" + (resultTrsOnThePage.size() - 1) + " results");
                //System.out.println("Results for ---" + dataToSend + "--- found:" + (resultTrsOnThePage.size() - 1));
                for (int i = 1; i < resultTrsOnThePage.size(); i++) {
                    List<WebElement> listOfTableDatas = resultTrsOnThePage.get(i).findElements(By.tagName("td"));
                    if (listOfTableDatas.get(type).getText().contains(dataToSend)) {
                        helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "CHECKING: String ---" + dataToSend + "---  in column ----" + listOfTableHeads.get(type).getText() + "--- was found on page. Success!\r");
                        //System.out.println("String ---" + dataToSend + "--- used for search was found on page. Success!");
                    } else {
                        helperClass.printToFileAndConsoleInformation(fileToWriteLogsOfTesting, "CHECKING: String ---" + dataToSend + "--- in column ---" + listOfTableHeads.get(type).getText() + "--- not found\r");
                        //System.out.println("Data ---" + dataToSend + "--- not found");
                    }
                }
            }
            counter++;
        } while (isTestGoOn); 
    }    
}
