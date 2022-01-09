import java.util.Scanner;

public class App {
    static void GotoClient() {}
    static void GotoAgent() {}
    public static void main(String[] args) throws Exception {
        Scanner cin = new Scanner(System.in);

        System.out.println("Hello please specify what kind of user you are:");
        System.out.println("1- Agent          2- Client");
        System.out.print("Choisir un numero: ");
        int choice = cin.nextInt();
        if (choice == 1) GotoAgent();
        else GotoClient();
        cin.close();
    }
}
