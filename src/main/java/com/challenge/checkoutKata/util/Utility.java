package com.challenge.checkoutKata.util;

import com.challenge.checkoutKata.stock.StockItems;
import com.challenge.checkoutKata.wholesale.SpecialOffers;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.challenge.checkoutKata.util.Constants.*;
import static java.util.Objects.isNull;

public class Utility {
    private static final Logger LOGGER = Logger.getLogger( Utility.class.getName() );

    public void initialise(StockItems stockItems, SpecialOffers specialOffers) throws ExceptionHandling {
        List<String> items = readInputFromResource(ITEMS);
        List<String> specialPrices = readInputFromResource(SPECIALPRICES);

        stockItems.loadStockItems(items);
        specialOffers.loadSpecialOffers(specialPrices, stockItems.getProducts());
    }

    public List<String> readInputFromResource(String fileName) throws ExceptionHandling {
        List<String> inputList = new ArrayList<>();
        BufferedReader reader;
        try {
            InputStream fileNameStream = this.getClass().getResourceAsStream("/" + fileName); //convert fileLocation from resource input to stream
            reader = new BufferedReader(new InputStreamReader(fileNameStream));
            String inputLine;

            while((inputLine =  reader.readLine())  != null) {
                if(!inputLine.isEmpty())
                    inputList.add(inputLine);
            }
            reader.close();
            LOGGER.log(Level.FINE, FILEOPERAATION_SUCCESSFUL);
        } catch(Exception e) {
            LOGGER.log(Level.SEVERE, "Caught Exception: " + e.getMessage());
            throw new ExceptionHandling(INTERNAL_SERVER_ERROR);
        }

        return inputList;
    }

    public static void writeToFile(String fileName, String inputLine) throws ExceptionHandling {

        if(isNull(fileName) || isNull(inputLine) || !Files.exists(Paths.get(fileName))) {
            LOGGER.log(Level.SEVERE, "Caught Exception: " + FILE_DOES_NOT_EXISTS + " " + fileName );
            throw new ExceptionHandling(INTERNAL_SERVER_ERROR);
        }
        try {
            if(!inputLine.isEmpty()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
                writer.newLine();
                writer.append(inputLine);   //writes to the file specified
                writer.close();
                LOGGER.log(Level.FINE, FILEOPERAATION_SUCCESSFUL);
            }

        }catch(Exception e) {
            LOGGER.log(Level.SEVERE, "Caught Exception: " + e.getMessage());
            throw new ExceptionHandling(INTERNAL_SERVER_ERROR);
        }
    }

    public static void deleteALineFromFile(String fileName, String inputLine) throws ExceptionHandling {

        if(isNull(fileName) || isNull(inputLine)) {
            LOGGER.log(Level.SEVERE, "Caught Exception: " + FILE_DOES_NOT_EXISTS + " " + fileName );
            throw new ExceptionHandling(INTERNAL_SERVER_ERROR);
        }

        try {
            File oldFile = new File(fileName);
            //create temp File under current filename directory,
            String tempFileName = fileName.split("resources/")[0] + "resources/" + fileName.split("resources/")[1] + "_";
            File tempFile = new File(tempFileName);
            PrintWriter tempFilePw = new PrintWriter(tempFileName);
            Scanner scan = new Scanner (oldFile);
            while (scan.hasNext())
            {
                String buffer = scan.nextLine();
                if (!buffer.contains(inputLine)) {
                    if (!buffer.equals("")) {

                        tempFilePw.println(buffer); //copy all existing items aside from the one to be deleted
                    }
                }
            }
            tempFilePw.flush();
            tempFilePw.close();
            scan.close();
            oldFile.delete();

            //renamed tempFile as new file with all copied line from old file aside, from the deleted pricing rule
            tempFile.renameTo(new File(fileName));
            LOGGER.log(Level.FINE, FILEOPERAATION_SUCCESSFUL);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Caught Exception: " + e.getMessage());
            throw new ExceptionHandling(INTERNAL_SERVER_ERROR);
        }
    }
}
