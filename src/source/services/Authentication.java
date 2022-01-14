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
        Scanner cin = new Scanner(System.in);
        System.out.print("Enter your Address : ");
        String adr = cin.nextLine();
        String[] creds = FileHandler.ReturnLine(type == 'c' ?clientsPath
                                              : type == 'a' ?agentsPath
                                              : type == 'm' ?managersPath
                                              : null , adr ).split(" ");
        if(creds[3].contains(adr) == true){
            String pass;
            int ctr = 3;
            System.out.print("Enter your password : ");
            pass = cin.nextLine();
            while(!creds[4].contains(pass) || ctr != 0){
                System.out.println("Wrong password");
                ctr--;
            }
            if(ctr == 0){
                System.out.println("Too many tries");
                cin.close();
                return null;
            }
            System.out.println("Welcome !");
            cin.close();
            try {
                if(type == 'a') return new Agent(creds[0],creds[1],creds[2],creds[3], creds[4]);
                if(type == 'c') return new Client(creds[0],creds[1],creds[2],creds[3], creds[4]);
                if(type == 'm') return new Manager(creds[0],creds[1],creds[2],creds[3], creds[4]);
            } catch (ObjectDoesntExistException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }else{
            System.out.println("Invalid Address or User doesn't exist");
            System.out.println("Would you like to retry :\n 1- Yes    0- No");
            int in = cin.nextInt();
            cin.close();
            if(in == 1) return SignIn(type);
            return null;
        }
        cin.close();
        return null;
    }

    public static User SignUp(char type) {
        App.ClearConsole();
        Scanner cin = new Scanner(System.in);
        String[] userInfo = new String[4];
        for (int i = 0; i < userInfo.length; i++) {
            System.out.println(i == 0 ?"Name : "
                              :i == 1 ?"Surname : "
                              :i == 2 ?"Address : "
                              :i == 3 ?"Password : "
                              :null);
            userInfo[i] = cin.nextLine();
        }
        cin.close();
        if(type == 'a') return new Agent(userInfo[0],userInfo[1],userInfo[2],userInfo[3]);
        if(type == 'c') return new Client(userInfo[0],userInfo[1],userInfo[2],userInfo[3]);
        if(type == 'm') return new Manager(userInfo[0],userInfo[1],userInfo[2],userInfo[3]);
        return null;
    }
}
