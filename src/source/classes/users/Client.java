package source.classes.users;
import java.io.*;
import java.util.Scanner;

import source.classes.Cart;
import source.classes.Inventory;
import source.classes.Product;
import source.classes.Purchase;
import source.classes.User;
import source.exceptions.ObjectDoesntExistException;
import source.services.FileHandler;

public class Client extends User{
    private String directoryPath;
    private Cart cart;

    public Client(String id, String name, String surname, String address, String password) throws ObjectDoesntExistException{
        super(id, name, surname, address, password);
        this.directoryPath = "./data/users/clients/"+id;
        this.cart = new Cart(this);
        if (FileHandler.GetDataByRef("./data/users/clients.txt", id) == null)
            throw new ObjectDoesntExistException();
    }

    public Client(String name, String surname, String address, String password) {
        super(name, surname, address, password);
        this.directoryPath = "./data/users/clients/"+id;
        this.cart = new Cart(this);
        FileHandler.CreateDIR(directoryPath);
        FileHandler.Add("./data/users/clients.txt", StringIt());
    }

    public static Client ObjectIt(String data) throws ObjectDoesntExistException {
        if (data == null) return null;
        String[] dataArray = data.split("|");
        return new Client(dataArray[0], dataArray[1], dataArray[2], dataArray[3], dataArray[4]);
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    @Override
    protected void ShowActions() {
        try {
            Runtime.getRuntime().exec("cls");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Scanner cin = new Scanner(System.in);
        System.out.println("Choose an action:");
        System.out.println("1- Search for a product");
        System.out.println("2- Show available products");
        System.out.println("3- Check the cart");
        System.out.println("4- Check 'Loyalty' account");
        System.out.print("Choose a number: ");
        int choice = cin.nextInt();
        switch (choice) {
            case 1:
                UseSelection(SearchProduct());
                break;
            case 2:
                UseSelection(ShowSelectProducts(ChooseProductCategory()));
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

    @Override
    protected void UseSelection(Product product) {
        if (product == null) return;
        int availableQte = Inventory.AvailableQuantity(product.getRef());
        Scanner cin = new Scanner(System.in);
        System.out.println("Do you want to:");
        System.out.println("1- Add to Cart   0- Go Back");
        System.out.print("Choose a number: ");
        int choice = cin.nextInt();
        if (choice == 1) {
            if (availableQte == 0) {
                System.out.println("Product isn't available right now");
                cin.close();
                return;
            }
            System.out.println("How much do you want?: ");
            int qte = cin.nextInt();
            if (qte > availableQte) {
                System.out.println("This quantity is unavailable!");
                System.out.println("Would you like to order "+availableQte+" instead?\n(0: no, 1: yes): ");
                choice = cin.nextInt();
                if (choice == 0) {
                    cin.close();
                    return;
                }
                qte = availableQte;
            }
            Purchase pur = new Purchase(product.getRef(), qte, product.getPrice());
            cart.AddPurchase(pur);
            Inventory.ChangeProdQuantity(product.getRef(), availableQte - qte);
        }
        cin.close();
    }

    private void CheckAccount() {}
}
