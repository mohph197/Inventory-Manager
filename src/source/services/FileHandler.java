package source.services;
import java.io.*;
import java.util.Scanner;

public class FileHandler {


    public static boolean FileExists(String path){
        File file = new File(path);
        return file.exists();
    }

    public static boolean Add(String path, String data){
        try {
            FileWriter fw = new FileWriter(path, true);
            fw.write(data+"\n");
            fw.close();
            return true;
        }catch(IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean ClearFile(String path) {
        try {
            FileWriter fw = new FileWriter(path);
            fw.close();
            return true;
        } catch (IOException e) {
            return false;
        }
        
    }

    public static boolean Remove(String path, String data) {
        try {
            Scanner fs = new Scanner(new File(path));
            while(fs.hasNextLine()) {
                String line = fs.nextLine();
                if (!line.equals(data)) Add(path.replace(".txt", "_temp.txt"), line);
            }
            fs.close();
            File tempFile = new File(path.replace(".txt", "_temp.txt"));
            fs = new Scanner(tempFile);
            ClearFile(path);
            while (fs.hasNextLine()) {
                String line = fs.nextLine();
                Add(path, line);
            }
            fs.close();
            tempFile.delete();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }





}
