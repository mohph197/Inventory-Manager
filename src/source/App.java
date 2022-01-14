package source;
import java.io.IOException;
import java.util.Scanner;

import source.classes.users.Client;

public class App {
    static Scanner cin = new Scanner(System.in);
    public static void main(String[] args) {
        ClearConsole();
        System.out.println("Hello please specify what kind of user you are:");
        System.out.println("1- Agent          2- Client");
        System.out.print("Choose a number: ");
        int choice = cin.nextInt();
        ClearConsole();
        if (choice == 1) GotoAgent();
        else GotoClient();
        cin.close();
    }
    static void GotoClient() {
        System.out.println("Welcome!");
        System.out.println("1- Sign in          2- Sign Up");
        System.out.print("Choose a number: ");
        int choice = cin.nextInt();
        Client client = choice == 1 ?SignIn('c') :SignUp('c');
        if (client == null) return;
        client.ShowActions();
    }
    static void GotoAgent() {}

    static Client SignIn(Character type) {
        return null;
    }
    static Client SignUp(Character type) {
        return null;
    }

    public static void ClearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            System.out.println("Error Clearing The console");
        }
    }
}
