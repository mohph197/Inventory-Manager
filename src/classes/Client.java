package classes;

import java.io.IOException;
import java.util.Scanner;

// import services.Search;

public abstract class Client {
    String name;
    String surname;
    String address;

    public Client(String name, String surname, String address) {
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    public void ShowActions() {
        Scanner cin = new Scanner(System.in);
        System.out.println("Choose an action:");
        System.out.println("1- Search for a product");
        System.out.println("2- Show all available products");
        System.out.println("3- Check the cart");
        System.out.println("4- Request a discount");
        System.out.println("5- Check 'Loyalty' account");
        System.out.print("Choose a number: ");
        try {
            Runtime.getRuntime().exec("cls");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int choice = cin.nextInt();
        switch (choice) {
            case 1:
                SearchProduct();
                break;
            case 2:
                ShowProducts();
                break;
            case 3:
                CheckCart();
                break;
            case 4:
                RequestDiscount();
                break;
            case 5:
                CheckAccount();
                break;
            default:
                System.out.println("Wrong Number!");
                ShowActions();
                break;
        }
        cin.close();
    }


    private void SearchProduct() {
        Scanner cin = new Scanner(System.in);
        System.out.println("Do you want to search by:");
        System.out.println("1- by name   2- by reference   3- by description   4- by specs");
        System.out.print("Choose a number: ");
        int choice = cin.nextInt();
        try {
            Runtime.getRuntime().exec("cls");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        switch (choice) {
            case 1:
                System.out.print("Enter search key: ");
                String key = cin.nextLine();
                // Search.ByName(key);
                break;
            case 2:
                System.out.print("Enter reference: ");
                String ref = cin.nextLine();
                // Search.ByRef(ref);
                break;
            case 3:
                System.out.print("Enter description: ");
                String desc = cin.nextLine();
                // Search.ByDescription(desc);
                break;
            case 4:
                System.out.print("Enter description: ");
                String spec = cin.nextLine();
                // Search.BySpecs(spec);
                break;
            default:
                break;
        }
        cin.close();
    }
    private void ShowProducts() {
        Product[] products = Inventory.GetProducts();
        for (int i = 0; i < products.length; i++) {
            System.out.println((i+1) + "- " + products[i].getName() + ' ' + products[i].getPrice());
        }
    }
    private void CheckCart() {}
    private void RequestDiscount() {}
    private void CheckAccount() {}
}
