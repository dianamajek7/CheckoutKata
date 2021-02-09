package util;

import Stock.StockUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SkuUtil {
    private static final Logger LOGGER = Logger.getLogger( SkuUtil.class.getName() );

    public List<String> readInputFromResource(String fileName) {
        List<String> inputList = new ArrayList<>();
        BufferedReader reader;
        try {

            InputStream fileNameStream = this.getClass().getResourceAsStream("/" + fileName); //convert fileLocation from resource input to stream
            reader = new BufferedReader(new InputStreamReader(fileNameStream));
            String inputLine;

            while((inputLine =  reader.readLine())  != null) {
                inputList.add(inputLine);
            }
            reader.close();


        } catch(FileNotFoundException e) {
            System.err.println("Caught FileNotFoundException: " + e.getMessage());
            throw new RuntimeException(e);
        } catch(IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }

        return inputList;
    }

    public void writeToFile(String fileName, String inputLine) {
        try {
            Files.write(Paths.get(fileName), inputLine.getBytes());
            System.out.println("Message written to file successfully!");
        }catch(FileNotFoundException e) {
            System.err.println("Caught FileNotFoundException: " + e.getMessage());
            throw new RuntimeException(e);
        } catch(IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }

    }
}
