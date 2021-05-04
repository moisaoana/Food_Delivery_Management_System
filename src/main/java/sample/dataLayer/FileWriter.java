package sample.dataLayer;

import sample.businessLayer.User;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileWriter {
    public static void writeToFile(User user, String filename){
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(filename,true);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(file);
            out.writeObject(user);
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
