package source.classes;
import java.io.*;
import java.util.Scanner;

import source.services.FileHandler;
import source.services.Purchase;

public class Client {
    private String id;
    private String name;
    private String surname;
    private String address;
    private String password;
    private String directoryPath;
    private Cart cart;

    public Client(String id, String name, String surname, String address, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.password = password;
        this.directoryPath = "./data/clients/"+id;
        this.cart = new Cart(this);
        FileHandler.Add("./data/clients/clients.txt", StringIt());
        FileHandler.CreateDIR(directoryPath);
    }

    public String StringIt(){
		String returned = this.id+"|"+this.name+"|"+this.surname+"|"+this.address+"|"+this.password;
		return returned;
	}

    public static Client ObjectIt(String data) {
        String[] dataArray = data.split("|");
        return new Client(dataArray[0], dataArray[1], dataArray[2], dataArray[3], dataArray[4]);
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void ShowActions() {
        Scanner cin = new Scanner(System.in);
        System.out.println("Choose an action:");
        System.out.println("1- Search for a product");
        System.out.println("2- Show all available products");
        System.out.println("3- Check the cart");
        System.out.println("4- Check 'Loyalty' account");
        System.out.print("Choose a number: ");
        try {
            Runtime.getRuntime().exec("cls");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        int choice = cin.nextInt();
        switch (choice) {
            case 1:
                SearchProduct();
                break;
            case 2:
                ShowProducts(Inventory.GetProducts());
                break;
            case 3:
                cart.ShowCart();
                break;
            case 4:
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
            System.out.println(e.getMessage());
        }
        System.out.print("Enter search key: ");
        ShowProducts(Inventory.SearchProduct(
            choice == 1 ?"name"
           :choice == 2 ?"ref"
           :choice == 3 ?"desc"
           :choice == 4 ?"spec"
           :null, cin.nextLine())
        );
        cin.close();
    }

    private void ShowProducts(Product[] products) {
        Scanner cin = new Scanner(System.in);
        int choice;
        for (int i = 0; i < products.length; i++) {
            System.out.println((i+1) + "- " + products[i].StringIt());
        }
        System.out.println("0- Go Back");
        System.out.print("Choose a number: ");
        choice = cin.nextInt();
        if (choice == 0) {
            cin.close();
            return;
        }
        if (choice >= products.length) {
            try {
                Runtime.getRuntime().exec("cls");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Wrong Number!");
            cin.close();
            ShowProducts(products);
        }
        Product product = products[choice - 1];
        int availableQte = Inventory.GetQuantity(product.getRef());
        System.out.println("The Product you selected is:");
        System.out.println(product.StringIt());
        if (availableQte == 0) {
            System.out.println("This product isn't available right now!");
            return;
        }
        System.out.println("Do you want to:");
        System.out.println("1- Add to Cart   0- Go Back");
        System.out.print("Choose a number: ");
        choice = cin.nextInt();
        if (choice == 1) {
            System.out.println("How much do you want?: ");
            int qte = cin.nextInt();
            if (qte > availableQte) {
                System.out.println("This quantity is unavailable!");
                System.out.println("Would you like to order "+availableQte+" instead?\n(0: no, 1: yes): ");
                choice = cin.nextInt();
                if (choice == 0) return;
                qte = availableQte;
            }
            Purchase pur = new Purchase(product.getRef(), qte, product.getPrice());
            cart.AddPurchase(pur);
            Inventory.ChangeQuantity(product.getRef(), availableQte - qte);
        }
        cin.close();
    }

    private void CheckAccount() {}
}
