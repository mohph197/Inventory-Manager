package source;
import java.io.IOException;
import java.util.Scanner;

import source.classes.Cart;
import source.classes.User;
import source.classes.users.Agent;
import source.classes.users.Client;
import source.classes.users.Manager;
import source.services.Authentication;

public class App {
    public static Scanner cin = new Scanner(System.in);
    public static void main(String[] args) {
        ClearConsole();
        while (true) {
            Cart.VerifyDuePurchasese();
            System.err.println("\nPLEASE DON'T USE SPACES WHEN ENTERING DATA\nUSE (_) (UNDERSCORE) INSTEAD\n");
            System.out.println("Hello, please specify which kind of user you are:");
            System.out.println("1- Agent   2- Client   3- Manager   0- Exit");
            System.out.print("Choose a number: ");
            int choice = cin.nextInt();cin.nextLine();
            ClearConsole();
            if (choice == 0) break;
            if (choice == 1 || choice == 2 || choice == 3) GotoUser(choice);
            else {
                ClearConsole();
                System.out.println("Wrong Number!");
            }
        }
        cin.close();
    }
    static void GotoUser(int typeInt) {
        char type =  typeInt == 1 ?'a'
                    :typeInt == 2 ?'c'
                    :typeInt == 3 ?'m'
                    :' ';
        System.out.println("Welcome!");
        System.out.println("1- Sign in   2- Sign Up");
        System.out.print("Choose a number: ");
        int choice = cin.nextInt();cin.nextLine();
        User user = choice == 1 ?Authentication.SignIn(type) :Authentication.SignUp(type);
        if (user == null) return;
        if (typeInt == 1) ((Agent)user).ShowActions();
        else if (typeInt == 2) ((Client)user).ShowActions();
        else if (typeInt == 3) ((Manager)user).ShowActions();
    }

    public static void ClearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            System.out.println("Error Clearing The console");
        }
    }
}
