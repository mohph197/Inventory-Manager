package source;
import java.io.IOException;
import java.util.Scanner;

import source.classes.users.Client;
import source.services.Authentication;

public class App {
    static Scanner cin = new Scanner(System.in);
    public static void main(String[] args) {
        ClearConsole();
        while (true) {
            System.out.println("Hello, please specify which kind of user you are:");
            System.out.println("1- Agent   2- Client   3- Agent   0- Exit");
            System.out.print("Choose a number: ");
            int choice = cin.nextInt();cin.nextLine();
            ClearConsole();
            if (choice == 0) break;
            if (choice == 1) GotoAgent();
            else if (choice == 2) GotoClient();
            else if (choice == 3) GotoManager();
            else {
                ClearConsole();
                System.out.println("Wrong Number!");
            }
        }
        cin.close();
    }
    static void GotoClient() {
        System.out.println("Welcome!");
        System.out.println("1- Sign in   2- Sign Up");
        System.out.print("Choose a number: ");
        int choice = cin.nextInt();cin.nextLine();
        Client client = choice == 1 ?(Client)Authentication.SignIn('c') :(Client)Authentication.SignUp('c');
        if (client == null) return;
        client.ShowActions();
    }
    static void GotoAgent() {}

    static void GotoManager() {
        
    }

    // static Client SignIn(Character type) {
    //     return null;
    // }
    // static Client SignUp(Character type) {
    //     return null;
    // }

    public static void ClearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            System.out.println("Error Clearing The console");
        }
    }
}
