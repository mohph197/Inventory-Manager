import java.io.IOException;
import java.util.Scanner;

import classes.Client;

public class App {
    static Scanner cin = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Hello please specify what kind of user you are:");
        System.out.println("1- Agent          2- Client");
        System.out.print("Choisir un numero: ");
        int choice = cin.nextInt();
        try {
            Runtime.getRuntime().exec("cls");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (choice == 1) GotoAgent();
        else GotoClient();
        cin.close();
    }
    static void GotoClient() {
        System.out.println("Welcome!");
        System.out.println("1- Sign in          2- Sign Up");
        System.out.print("Choisir un numero: ");
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
}
