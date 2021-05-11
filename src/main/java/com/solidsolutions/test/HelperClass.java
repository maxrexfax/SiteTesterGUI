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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author user
 */
public class HelperClass {
    
    public final String leftDemarkator = "--->";
    public final String rightDemarkator = "<---";
    
    public String getRandChar() 
    {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int randomNumberOfChar1 = (int)(Math.random() * alphabet.length());
        int randomNumberOfChar2 = (int)(Math.random() * alphabet.length());
        int randomNumberOfChar3 = (int)(Math.random() * alphabet.length());
        int randomNumberOfChar4 = (int)(Math.random() * alphabet.length());
        int randomNumberOfChar5 = (int)(Math.random() * alphabet.length());
        int extraRandom = (randomNumberOfChar1 + randomNumberOfChar2 + randomNumberOfChar3 + randomNumberOfChar4 + randomNumberOfChar5) / 5;
        if (extraRandom < 0) {
            extraRandom = (int)(Math.random() * alphabet.length());
        }
        if (extraRandom >= alphabet.length()) {
            extraRandom = (int)(Math.random() * alphabet.length());
        }
        return String.valueOf(alphabet.charAt(extraRandom));
    }
    
    public String getRandomLengthString(int lengthOfString) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        
        for (int i = 0; i < lengthOfString; i++) {
            stringBuilder.append(getRandChar());
        }
        
        return stringBuilder.toString();
    }
    
    public static void startThread(String commandJs, JavascriptExecutor js)
    {
        Thread thread = new Thread(){
            public void run(){
              System.out.println("Thread Running and sending to JS:" + commandJs);
              js.executeAsyncScript(commandJs);
            }
        };
        thread.start();
    }
    
    public static void writeStringToFileInThread1(File fileToWriteLogsOfTesting, String dataToWrite)//не использовать в потоке сообщений - записывает в хаотическом порядке!
    {
        Thread thread = new Thread(){
            public void run(){
              System.out.println("Thread is trying to write in file:" + dataToWrite);
              //File fileToWriteLogsOfTesting = new File("./logs/myLogFile" + fileNamePostfix);
              //writeStringToFile(fileToWriteLogsOfTesting.getAbsolutePath(), dataToWrite);
              try
                {
                    FileWriter myWriter = new FileWriter(fileToWriteLogsOfTesting.getAbsolutePath());
                    myWriter.append(dataToWrite);
                    //myWriter.write(dataToWrite);
                    myWriter.close();
                    System.out.println("Successfully edit the file with path " + fileToWriteLogsOfTesting.getAbsolutePath());
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
                }
        };
        thread.start();
    }
    
    
    public void writeStringToFile(File fileToWriteLogsOfTesting, String content) 
        {
            try(FileWriter fw = new FileWriter(fileToWriteLogsOfTesting.getAbsolutePath(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw))
            {
                out.println(content);
            } catch (IOException e) {
                System.out.println("IOException in writeStringToFile function");
                System.out.println("Failed to write data " + leftDemarkator + content + rightDemarkator);
                System.out.println(e.getMessage());
            } catch (Exception ex) {
                System.out.println("Exception in writeStringToFile function");
                System.out.println("Failed to write data " + leftDemarkator + content + rightDemarkator);
                System.out.println(ex.getMessage());
            }
        }
    
    public void selectOneElementFromDropdownInHelper(WebDriver webDriver, File logFileNormal) throws InterruptedException
    {     
        //WebElement listContainerElement = webDriver.findElement(By.xpath("//*[contains(@class,'v-menu__content--fixed menuable__content__active')]"));
        WebElement listContainerElement = webDriver.findElement(By.className("menuable__content__active"));
        Thread.sleep(500);
        List<WebElement> listElements = null;
        try {
            listElements = listContainerElement.findElements(By.className("v-list-item--link"));
        } catch (Exception ex) {
            System.out.println("Error while finding elements in dropdown");
            writeStringToFile(logFileNormal, "Error while finding elements in dropdown!");
        }
        //System.out.println("H_listElements.size=" + listElements.size());
        Thread.sleep(500);
        int randomNumberOfElement = (int)(Math.random() * listElements.size());        
        Thread.sleep(500);
        if (listElements != null) {
            if (listElements.size() > 0) {
                //System.out.println("H_BEFORE CLICK ON ELEMENT");
                listElements.get(randomNumberOfElement).click(); 
                //System.out.println("H_AFTER CLICK ON ELEMENT"); 
                Thread.sleep(500);            
            } else {
                System.out.println("H_Error, listElements.size() = " + listElements.size());
            }
        } else {
            System.out.println("H_Error, listElements is null");
            writeStringToFile(logFileNormal, "Dropdown is null");
        }
    }
    
    public String getDateInStringForWindowsLinux()
    {
        return new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss").format(new java.util.Date());
    }
    
    public void selectOneElementFromDropdownInHeperAlt(WebDriver webDriver, int index) throws InterruptedException
    {     
        int indexToClick;
        WebElement listContainerElement = webDriver.findElement(By.className("menuable__content__active"));
        Thread.sleep(500);
        List<WebElement> listElements = listContainerElement.findElements(By.className("v-list-item--link"));
        //System.out.println("H_listElements.size=" + listElements.size());
        Thread.sleep(500);
        if (index == 0) {
            indexToClick = listElements.size() - 1;
        } else if (index > listElements.size()) {
            indexToClick = (int)(Math.random() * listElements.size());
        } else {
            indexToClick = index;
        }

        Thread.sleep(500);
        if (listElements.size() > 0) {
            listElements.get(indexToClick).click();
            Thread.sleep(500);
            
        } else {
            System.out.println("H_alt_Error, listElements.size() = " + listElements.size());
        }   
    }
    
    public void selectOneElementFromDropdownAddressInHelper(WebDriver webDriver) throws InterruptedException
    {
        WebElement mapInput = webDriver.findElement(By.id("map"));
        mapInput.click();
        Thread.sleep(500);        
        mapInput.sendKeys(getRandChar());  
        Thread.sleep(500);                  
        List<WebElement> listContainerElement = webDriver.findElements(By.xpath("//*[contains(@class,'pac-container pac-logo')]"));
        Thread.sleep(500);        
        List<WebElement> listElements = listContainerElement.get(1).findElements(By.className("pac-item"));
        Thread.sleep(500);
        //System.out.println("Address listElements.size=" + listElements.size());
        Thread.sleep(500);
        int randomNumberOfElement = (int)(Math.random() * listElements.size());
        Thread.sleep(500);
        //System.out.println(listElements.get(randomNumberOfElement));
        Thread.sleep(500);
        if (listElements.size() > 0) {
            //listElements.get(randomNumberOfElement);   
            listElements.get(randomNumberOfElement).click();        
        } else {
            System.out.println("Error, listElements.size() = " + listElements.size());
        }
    }
    
    public WebElement safeFindElement(WebDriver webDriver, String identifier, String type)
    {
        WebElement foundedElement = null;
        try {
            switch (type){
                case "id":
                    foundedElement = webDriver.findElement(By.id(identifier));
                    break;
                case "xpath":
                    foundedElement = webDriver.findElement(By.xpath(identifier));
                    break;
                case "cssSelector":
                    foundedElement = webDriver.findElement(By.cssSelector(identifier));
                    break;
                case "className":
                    foundedElement = webDriver.findElement(By.className(identifier));
                    break;
                case "tagName":
                    foundedElement = webDriver.findElement(By.tagName(identifier));
                    break;
            }
        }
        catch(NoSuchElementException eex) {
            System.out.println(eex.getMessage());
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        return foundedElement;
    }
    
    public void safeClickOnElement(WebElement elementToClick)
    {
        if (elementToClick != null) {
            try {
                elementToClick.click();
            }
            catch(ElementNotInteractableException eex) {
                System.out.println("Error click!(1)");
                System.out.println(eex.getMessage());
            }
            catch(Exception ex) {
                System.out.println("Error click!(2)");
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("Error! Element is null!");
        }   
    }
    
    public void safeFillInput(WebElement elementToFill, String dataToFill)
    {
        //System.out.println(elementToFill.getTagName());
        if (elementToFill != null /*&& elementToFill.getTagName() == "input"*/) {
            try {
                elementToFill.sendKeys(dataToFill);
            }
            catch(ElementNotInteractableException eex) {
                System.out.println("Error sending data to input!(1)");
                System.out.println(eex.getMessage());
            }
            catch(Exception ex) {
                System.out.println("Error sending data to input!(2)");
                System.out.println(ex.getMessage());
            }
        }   else {
            System.out.println("Error! Element is null or not input!");
        } 
    }
    
    public int getRandomDigit(int min, int max)
    {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    
    public String getSystemMessage(WebDriver webDriver)
    {
        String result = "";
        String resultCleared = "DATA_NOT_FOUND";
        try {
                result = webDriver.findElement(By.xpath("//*[contains(@class,'toasted toasted-primary')]")).getText();
                resultCleared = result.replaceAll("[X\r\n]", "");
                System.out.println("Helper: 1st attempt, to found system message:  " + leftDemarkator + resultCleared + rightDemarkator);
            } catch(NoSuchElementException eex) {
                //System.out.println(eex.getMessage());
                System.out.println("Error: in 1st attempt in searching system message");
                Thread.sleep(1000);
                try {
                    result = webDriver.findElement(By.xpath("//*[contains(@class,'toasted toasted-primary')]")).getText();
                    System.out.println("Helper: 2nd attempt, to found system message:   " + leftDemarkator + resultCleared + rightDemarkator);
                    resultCleared = result.replaceAll("[X\r\n]", "");
                    } catch(NoSuchElementException eex1) {
                        System.out.println("Error: in 2nd attempt in searching system message");
                    }catch(Exception ex) {
                        System.out.println(ex.getMessage());
                        System.out.println("Error: some Exception while trying to find message in div container");
                    }
            } catch(Exception ex) {
                System.out.println(ex.getMessage());
                System.out.println("Error: some Exception while trying to find message in div container");
            } finally {
                return resultCleared;
            }        
    }
    
    public String[] getSystemMessageWithClasses(WebDriver webDriver) {
        WebElement divWithMessage = null;
        String[] arrayWithMessageAndClasses = null;
        try {
                divWithMessage = webDriver.findElement(By.xpath("//*[contains(@class,'toasted toasted-primary')]"));
                arrayWithMessageAndClasses = new String[2];
                arrayWithMessageAndClasses[0] = divWithMessage.getText().replaceAll("[X\r\n]", "");
                arrayWithMessageAndClasses[1] = divWithMessage.getAttribute("class");
                System.out.println("Helper: 1st attempt, to found system message:  " + leftDemarkator + arrayWithMessageAndClasses[0] + rightDemarkator);
            } catch(NoSuchElementException eex) {
                //System.out.println(eex.getMessage());
                System.out.println("Error: in 1st attempt in searching system message");
                Thread.sleep(1000);
                try {
                    divWithMessage = webDriver.findElement(By.xpath("//*[contains(@class,'toasted toasted-primary')]"));
                    arrayWithMessageAndClasses = new String[2];
                    arrayWithMessageAndClasses[0] = divWithMessage.getText().replaceAll("[X\r\n]", "");
                    arrayWithMessageAndClasses[1] = divWithMessage.getAttribute("class");
                    System.out.println("Helper: 2nd attempt, to found system message:  " + leftDemarkator + arrayWithMessageAndClasses[0] + rightDemarkator);
                    } catch(NoSuchElementException eex1) {
                        System.out.println("Error: in 2nd attempt in searching system message");
                    }catch(Exception ex) {
                        System.out.println(ex.getMessage());
                        System.out.println("Error: some Exception while trying to find message in div container");
                    }
            } catch(Exception ex) {
                System.out.println(ex.getMessage());
                System.out.println("Error: some Exception while trying to find message in div container");
            } finally {
                return arrayWithMessageAndClasses;
            }  
    }
    
    public String getAllAddressesOnPage(WebDriver webDriver) throws InterruptedException
    {
        Thread.sleep(500);
        StringBuffer strBuffer = new StringBuffer("\r");
        List <WebElement> spansWithAddresses = webDriver.findElements(By.className("value"));
        
        for (WebElement s : spansWithAddresses)
        {
            strBuffer.append(s.getText()).append("\r");
        }
        
        return strBuffer.toString();
    }
    
    public String getFormattedDateForTest() 
    {
        int randomDay = getRandomDigit(1,28);
        String rndDayStr = (randomDay < 10) ? "0" + randomDay : String.valueOf(randomDay);
        int randomMonth = getRandomDigit(1,12);
        String rndMonthStr = (randomMonth < 10) ? "0" + randomMonth : String.valueOf(randomMonth);
        return "" + rndDayStr + "-" + rndMonthStr + "-" + getRandomDigit(1960,2005);
    }
    
    public void editDataInTextInputWithLabel(WebDriver webDriver, String dataToFill, String cssPreSelector, File logFileNormal, String tagNameOfInputElement) throws InterruptedException {
        Thread.sleep(300);
        String tagNameOfInputElementToUse;
        if (tagNameOfInputElement != null) {
            tagNameOfInputElementToUse = tagNameOfInputElement;
        } else {
            tagNameOfInputElementToUse = "input";
        }
        //NOTE if dataToFill == null this function will try to copy and paste in the input
        WebElement inputToEditValueLabel = null;
        WebElement inputToEditValue = null;
        String textInLabel = "";
        
        try {
            inputToEditValueLabel = webDriver.findElement(By.cssSelector(cssPreSelector + "label"));
            textInLabel = inputToEditValueLabel.getText();    
            System.out.println("Text in label = " + textInLabel);
        } catch(Exception ex) {
            writeStringToFile(logFileNormal, "Error while finding element with cssSelector   " + cssPreSelector + "label");
            System.out.println("Error while finding element with cssSelector   " + cssPreSelector + "label");
        }
        
        try {
            inputToEditValue = webDriver.findElement(By.cssSelector(cssPreSelector + tagNameOfInputElementToUse));
        } catch(Exception ex) {
            writeStringToFile(logFileNormal, "Error while finding element with cssSelector   " + cssPreSelector + tagNameOfInputElementToUse);
            System.out.println("Error while finding element with cssSelector   " + cssPreSelector + tagNameOfInputElementToUse);
        }
        
        String inputValue;
        if (dataToFill != null) {
            inputValue = dataToFill;
        } else {
            inputValue = inputToEditValue.getAttribute("value");
        }
         
        writeStringToFile(logFileNormal, "Work: found in element " + textInLabel + "  data:   " + leftDemarkator + inputToEditValue.getAttribute("value") + rightDemarkator); 
        System.out.println("Work: found in element " + textInLabel + "  data:   " + leftDemarkator + inputToEditValue.getAttribute("value") + rightDemarkator);
        
        if (inputToEditValue.getAttribute("value").length() > 0) {
            writeStringToFile(logFileNormal, "Work: clear data in element"); 
            System.out.println("Work: clear data in element");
            inputToEditValue.sendKeys(Keys.CONTROL + "a");
            Thread.sleep(500);
            inputToEditValue.sendKeys(Keys.DELETE);
            Thread.sleep(500);
        }        
        
        writeStringToFile(logFileNormal, "Work: fill data   " + leftDemarkator + inputValue + rightDemarkator + " in element " + textInLabel); 
        System.out.println("Work: fill data " + leftDemarkator + inputValue + rightDemarkator + " in element " +  textInLabel);
        
        try {
            inputToEditValue.sendKeys(inputValue);
        } catch (Exception ex) {
            writeStringToFile(logFileNormal, "Error while sending data " + leftDemarkator + inputValue + rightDemarkator + " to input");
            System.out.println("Error while sending data   " + leftDemarkator + inputValue + rightDemarkator + " to input");
        }
        
        writeStringToFile(logFileNormal, "Work: after filling found data " + leftDemarkator + inputToEditValue.getAttribute("value") + rightDemarkator + "\r\n");
        System.out.println("Work: after filling found data   " + leftDemarkator + inputToEditValue.getAttribute("value") + rightDemarkator + "\r");
        Thread.sleep(300);
    }
    
    public void workWithDropdownElementCitiesNation(WebDriver webDriver, String elementToClickCss, String labelAndInput, File logFileNormal) throws InterruptedException {
        WebElement inputToEditValueLabel = webDriver.findElement(By.cssSelector(labelAndInput + "label"));
        String textInLabel = inputToEditValueLabel.getText();
        writeStringToFile(logFileNormal, "Work: attempt to change   " + leftDemarkator + textInLabel + rightDemarkator); 
        System.out.println("Work: attempt to change   " + leftDemarkator + textInLabel + rightDemarkator);
        //WebElement inputToEditValue = webDriver.findElement(By.cssSelector(labelAndInput + "input"));
        WebElement divToEditValue = webDriver.findElement(By.cssSelector(labelAndInput + "div"));
        webDriver.findElement(By.cssSelector(elementToClickCss)).click();
        Thread.sleep(500);
        selectOneElementFromDropdownInHelper(webDriver, logFileNormal);
        Thread.sleep(500);
        writeStringToFile(logFileNormal, "Work: after changing found data   " + leftDemarkator + divToEditValue.getText() + rightDemarkator + "\r\n"); 
        System.out.println("Work: after changing found data   " + leftDemarkator + divToEditValue.getText() + leftDemarkator);        
    }
    
    public void fillOneInput(WebDriver webDriver, String dataToFill, String cssInputSelector, File logFileNormal, String nameOfField) throws InterruptedException {
        Thread.sleep(300);
        writeStringToFile(logFileNormal, "Work: trying to find element " + nameOfField + " and fill with data:   " + leftDemarkator + dataToFill + rightDemarkator); 
        System.out.println("Work: trying to find element " + nameOfField + " and fill with data:   " + leftDemarkator + dataToFill + rightDemarkator);
        WebElement inputToEditValue = null;
        
        try {
            inputToEditValue = webDriver.findElement(By.cssSelector(cssInputSelector));
        } catch(Exception ex) {
            writeStringToFile(logFileNormal, "Error while finding element with cssSelector  " + cssInputSelector + "input");
            System.out.println("Error while finding element with cssSelector  " + cssInputSelector + "input");
        }
        
        try {
            inputToEditValue.sendKeys(dataToFill);
        } catch (Exception ex) {
            writeStringToFile(logFileNormal, "Error while sending data   " + leftDemarkator + dataToFill + rightDemarkator + " to input");
            System.out.println("Error while sending data   " + leftDemarkator + dataToFill + rightDemarkator + " to input");
        }
        
        Thread.sleep(300);
        String valueInInput = "EMPTY_DATA";
        try {
            valueInInput = inputToEditValue.getAttribute("value");
        } catch (Exception ex) {
            writeStringToFile(logFileNormal, "Error while getting value from input");
            System.out.println("Error while getting value from input");
        }
        Thread.sleep(300);
        writeStringToFile(logFileNormal, "Work: found in element " + nameOfField + " this data:    " + leftDemarkator + valueInInput + rightDemarkator + "\r\n"); 
        System.out.println("Work: found in element " + nameOfField + " this data: " + leftDemarkator + valueInInput + rightDemarkator + "\r\n");
        Thread.sleep(300);
    }
    
    public void writeErrorsToFiles(File logFile, File errorLogFile, String message, String exceptionMessage) {
        writeStringToFile(logFile, message); 
        writeStringToFile(errorLogFile, message); 
        writeStringToFile(errorLogFile, exceptionMessage); 
    }
    
    public String getAllChosenTags(WebDriver webDriver) {
        StringBuffer strBuf = new StringBuffer();
        List <WebElement> allTags = webDriver.findElements(By.className("v-chip__content"));
        for (int i = 0; i < allTags.size(); i++) {
            strBuf.append(allTags.get(i).getText());
            if (i != allTags.size()-1) {
                strBuf.append(", ");
            }
        }
        return strBuf.toString();
    }
    
    public void printToFileAndConsoleInformation(File logFile, String message) {
        if (message == null) message = "NULL REPLACED!";
        writeStringToFile(logFile, message);
        System.out.println(message);
    }
    
    public void clearTextInsideInput(WebElement inputToClear) throws InterruptedException {
        System.out.println("Work: clear data in element");
        inputToClear.sendKeys(Keys.CONTROL + "a");
        Thread.sleep(500);
        inputToClear.sendKeys(Keys.DELETE);
        Thread.sleep(500);
    }
    
//    public String setSystemPropertyAndGetPathToLogsFolder(String osName) {
//        String pathToLogFile = "";
//        if (osName.contains("Linux")) {
////            System.out.println("OS Linux detected. Trying to create a folder for log files in the same folder as the executable file");
////            System.out.println("Please make sure the appropriate chromedriver exists along the path /usr/bin/chromedriver");    
////            System.out.println("Please make sure the appropriate geckodriver exists along the path /usr/bin/geckodriver");  
////            System.out.println("Please make sure the appropriate chromedriver exists in the folder with jar file"); 
////            System.out.println("Visit URL https://chromedriver.chromium.org/downloads to download if you have no chromedriver!");             
//            pathToLogFile = "./logs/";                        
//            
//            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");      
//            System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
//            
//        } else if (osName.contains("Windows")) {
////            System.out.println("Please make sure the appropriate chromedriver exists along the path C:\\users\\public\\documents\\chromedriver.exe"); 
////            System.out.println("OS Windows detected, try to create log folder in C:\\users\\public\\documents\\logs");
//            pathToLogFile = "C:\\users\\public\\documents\\logs\\";            
//            
//            System.setProperty("webdriver.chrome.driver", "C:\\users\\public\\documents\\chromedriver.exe");
//            System.setProperty("webdriver.gecko.driver", "C:\\users\\public\\documents\\geckodriver.exe");
//            
//        } else {
//            pathToLogFile = "./";
//        }
//        return pathToLogFile;
//    }
}