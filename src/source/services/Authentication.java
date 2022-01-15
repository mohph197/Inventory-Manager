package source.services;
import java.util.Scanner;
import source.App;
import source.classes.User;
import source.classes.users.*;
import source.exceptions.ObjectDoesntExistException;

public abstract class Authentication {
    public static final String clientsPath = "./data/users/clients.txt";
    public static final String agentsPath = "./data/users/agents.txt";
    public static final String managersPath = "./data/users/managers.txt";

    public static User SignIn(char type) {
        App.ClearConsole();
        Scanner cin =  App.cin;
        System.out.print("Enter your Address : ");
        String adr = cin.nextLine();
        String usersStr = FileHandler.GetContent(type == 'c' ?clientsPath
                                                :type == 'a' ?agentsPath
                                                :type == 'm' ?managersPath
                                                :null);
        if (usersStr == null) {
            System.out.println("User doesn't Exist!");
            return null;
        }
        String[] users = usersStr.split("\n");
        for (String user : users) {
            String[] creds = user.split(" ");
            if (creds[3].equals(adr)) return VerifyUser(creds, type);
        }
        System.out.println("User doesn't Exist!");
        return null;
    }

    private static User VerifyUser(String[] creds, char type) {
        Scanner cin = App.cin;
        String pass;
        int ctr = 3;
        System.out.print("Enter your password : ");
        pass = cin.nextLine();
        System.out.println(creds[4]+" "+pass);
        while(!creds[4].equals(pass)){
            if (ctr != 0) {    
                System.out.println("Wrong password");
                System.out.print("Enter the password again: ");
                pass = cin.nextLine();
                ctr--;
            } else break;
        }
        if(ctr == 0) {
            System.out.println("Too many tries");
            return null;
        }
        System.out.println("Welcome!");
        try {
            if(type == 'a') return new Agent(creds[0],creds[1],creds[2],creds[3], creds[4]);
            if(type == 'c') return new Client(creds[0],creds[1],creds[2],creds[3], creds[4]);
            if(type == 'm') return new Manager(creds[0],creds[1],creds[2],creds[3], creds[4]);
        } catch (ObjectDoesntExistException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    public static User SignUp(char type) {
        App.ClearConsole();
        Scanner cin =  App.cin;
        String[] userInfo = new String[4];
        for (int i = 0; i < userInfo.length; i++) {
            System.out.println(i == 0 ?"Name : "
                              :i == 1 ?"Surname : "
                              :i == 2 ?"Address : "
                              :i == 3 ?"Password : "
                              :null);
            userInfo[i] = cin.nextLine();
        }
         
        if(type == 'a') return new Agent(userInfo[0],userInfo[1],userInfo[2],userInfo[3]);
        if(type == 'c') return new Client(userInfo[0],userInfo[1],userInfo[2],userInfo[3]);
        if(type == 'm') return new Manager(userInfo[0],userInfo[1],userInfo[2],userInfo[3]);
        return null;
    }
}
