package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static util.Constants.FILEOPERAATION_SUCCESSFUL;

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
                if(!inputLine.isEmpty())
                    inputList.add(inputLine);
            }
            reader.close();
            LOGGER.log(Level.FINE, FILEOPERAATION_SUCCESSFUL);
        } catch(Exception e) {
            LOGGER.log(Level.SEVERE, "Caught Exception: " + e.getMessage());
        }

        return inputList;
    }

    public static void writeToFile(String fileName, String inputLine) {
        try {
            if(!inputLine.isEmpty()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
                writer.write(inputLine);
                writer.newLine();
               // writer.append(inputLine);
                writer.close();
                LOGGER.log(Level.FINE, FILEOPERAATION_SUCCESSFUL);
            }

        }catch(Exception e) {
            LOGGER.log(Level.SEVERE, "Caught Exception: " + e.getMessage());
        }

    }

    public static void removeLastLineFromFile(String fileName) {
        try {
            File file = new File( fileName);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            byte b;
            long length = randomAccessFile.length();
            if (length != 0) {
                do {    //find the linefeed character keep decrementing until found, set new length to be -1 of the file length
                    length -= 1;
                    randomAccessFile.seek(length);
                    b = randomAccessFile.readByte();
                } while (b != 10 && length > 0);
                randomAccessFile.setLength(length); //set new length
                randomAccessFile.close();
                LOGGER.log(Level.FINE, FILEOPERAATION_SUCCESSFUL);
            }
        }catch(Exception e) {
            LOGGER.log(Level.SEVERE, "Caught Exception: " + e.getMessage());
        }
    }

    public static void deleteALineFromFile (String fileName, String inputLine)
    {
        try {
            File oldFile = new File(fileName);

            //create temp File under current directory
            String tempFileName = fileName.split("resources/")[0] + "resources/" + fileName.split("resources/")[1] + "_";
            File tempFile = new File(tempFileName);
            PrintWriter tempFilePw = new PrintWriter(tempFileName);

            Scanner scan = new Scanner (oldFile);
            while (scan.hasNext())
            {
                String buffer = scan.nextLine();
                if (!buffer.contains(inputLine)) {
                    if (!buffer.equals("")) {
                        tempFilePw.println(buffer);
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
        }

    }
}
