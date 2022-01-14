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
            if (!FileExists(path)) return true;
            FileWriter fw = new FileWriter(path);
            fw.close();
            return true;
        } catch (IOException e) {
            return false;
        }
        
    }

    public static boolean Remove(String path, String data) {
        try {
            if (!FileExists(path)) return true;
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
                Add(path, fs.nextLine());
            }
            fs.close();
            tempFile.delete();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean DeleteFile(String path) {
        File file = new File(path);
        return file.delete();
    }

    public static boolean CreateDIR(String path) {
        File dir = new File(path);
        return dir.mkdirs();
    }

    public static String GetDataByRef(String path, String ref) {
        try {
            Scanner fs = new Scanner(new File(path));
            while (fs.hasNextLine()) {
                String line = fs.nextLine();
                if (line.split(" ")[0].equals(ref)) return line;
            }
            fs.close();
            return null;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean ModifyData(String path, String oldData, String newData) {
        try {
            String tempPath = path.replace(".txt", "_temp.txt");
            Scanner fs = new Scanner(new File(path));
            while (fs.hasNextLine()) {
                String line = fs.nextLine();
                if (line.equals(oldData)) Add(tempPath, newData);
                else Add(tempPath, line);
            }
            fs.close();
            File tempFile = new File(tempPath);
            fs = new Scanner(tempFile);
            ClearFile(path);
            while(fs.hasNextLine()) {
                Add(path, fs.nextLine());
            }
            fs.close();
            tempFile.delete();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static String GenerateUID(int index) {
        try {
            Scanner fs = new Scanner(new File("./data/currentUID.txt"));
            String[] line = fs.nextLine().split(" ");
            fs.close();
            line[index] = String.valueOf(Integer.parseInt(line[index]) + 1);
            FileWriter fw = new FileWriter("./data/currentUID.txt");
            fw.write(line[0]+" "+line[1]+" "+line[2]);
            fw.close();
            return line[index];
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String GetContent(String path) {
        try {
            Scanner fs = new Scanner(new File(path));
            String result = "";
            while (fs.hasNextLine()) {
                result += fs.nextLine()+"\n";
            }
            return result;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
        
    }

    public static String ReturnLine(String path, String key){
        String[] temp = GetContent(path).split("\n");
        for (String string : temp)
            if(string.contains(key) == true) return string;
        return null;
    }

    public static boolean DeleteDataByRef(String path, String ref) {
        try {
            String tempPath = path.replace(".txt", "_temp.txt");
            Scanner fs = new Scanner(new File(path));
            while (fs.hasNextLine()) {
                String line = fs.nextLine();
                if (!line.split(" ")[0].equals(ref)) Add(tempPath, line);
            }
            fs.close();
            File tempFile = new File(tempPath);
            fs = new Scanner(tempFile);
            ClearFile(path);
            while(fs.hasNextLine()) {
                Add(path, fs.nextLine());
            }
            fs.close();
            tempFile.delete();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
