package source.classes.users;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

import source.App;
import source.classes.LoyaltyAccount;
import source.classes.Cart;
import source.classes.Inventory;
import source.classes.Product;
import source.classes.Purchase;
import source.classes.User;
import source.exceptions.ObjectDoesntExistException;
import source.services.Authentication;
import source.services.FileHandler;

public class Agent extends User{
    public static final String filePath = "./data/users/agents.txt";

    public Agent(String id, String name, String surname, String address, String password) throws ObjectDoesntExistException {
        super(id, name, surname, address, password);
        if (FileHandler.GetDataByRef(filePath, id) == null)
            throw new ObjectDoesntExistException();
    }

    public Agent(String name, String surname, String address, String password) {
        super(name, surname, address, password);
        FileHandler.CreateDIR("./data/users");
        FileHandler.Add(filePath, StringIt());
    }

    public String StringIt() {
		String returned = this.id+" "+this.name+" "+this.surname+" "+this.address+" "+this.password;
		return returned;
	}

    @Override
    public void ShowActions() {
        App.ClearConsole();
        Scanner cin = new Scanner(System.in);
        System.out.println("Choose an action:");
        System.out.println("1- Search for a product details");
        System.out.println("2- Show available products");
        System.out.println("3- Validate a cart");
        System.out.println("4- Initiate a new purchase");
        System.out.println("5- Make a refund");
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
                ValidateCart();
                break;
            case 4:
                InitPurchase();
                break;
            case 5:
                MakeRefund();
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
        System.out.println("The product you selected:");
        System.out.println(Inventory.GetProdDataByRef(product.getRef()));
    }

    private void InitPurchase() {
        App.ClearConsole();
        Scanner cin = new Scanner(System.in);
        boolean shouldAdd = true;
        int choice = 0;
        float currentPrice = 0;
        LinkedList<Purchase> purchases = new LinkedList<Purchase>();
        Client client = GetClient();
        if (client == null) {
            System.out.println("Authentication Error!");
            cin.close();
            return;
        }
        while (shouldAdd) {
            App.ClearConsole();
            System.out.println("The Price: "+currentPrice);
            System.out.print("Enter The product reference: ");
            Product chosen = Product.ObjectIt(Inventory.GetProdDataByRef(cin.nextLine()));
            if (chosen == null) System.out.println("Product doesn't exist!");
            int availableQte = Inventory.AvailableQuantity(chosen.getRef());
            if (availableQte == 0) System.out.println("Product isn't available right now");
            else {
                System.out.println(chosen.StringIt());
                System.out.println("1- Add   2- Skip");
                System.out.print("choose a number: ");
                choice = cin.nextInt();
                if (choice == 1) {
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
                    Purchase pur = (new Purchase(chosen.getRef(), qte, chosen.getPrice()));
                    purchases.add(pur);
                    currentPrice += pur.getPrice() * pur.getQte();
                    System.out.println("Product added successfully!");
                }
            }
            System.out.println("1- Add another one   2- Stop   0- Cancel");
            System.out.print("choose a number: ");
            choice = cin.nextInt();
            if (choice == 0 || choice == 2) shouldAdd = false;
        }
        if (choice == 0) {
            purchases.clear();
            cin.close();
            return;
        }
        Purchase[] purchases2 = (Purchase[])purchases.toArray();
        System.out.println("Here is your order:");
        System.out.println("The Price: "+currentPrice);
        for (int i = 0; i < purchases2.length; i++) {
            System.out.println((i+1)+"- "+Inventory.GetProdDataByRef(purchases2[i].getRefProd()).split(" ")[2]
                                +" "+purchases2[i].StringIt());
        }
        System.out.println("1- Confirm   2- Cancel");
        System.out.print("choose a number: ");
        choice = cin.nextInt();
        if (choice == 1) ExecutePurchases(purchases2, client);
        cin.close();
        purchases.clear();
        return;
    }

    private void ValidateCart() {
        Client client = GetClient();
        if (client == null) {
            System.out.println("Authentication Error!");
            return;
        }
        Cart cart = new Cart(client);
        Scanner cin = new Scanner(System.in);
        cart.ShowCart();
        System.out.print("Validate? (0: no, 1: yes): ");
        if (cin.nextInt() == 1) ExecutePurchases(cart.GetCartPurchases(), client);
        cin.close();
    }

    private void MakeRefund() {
        Client client = GetClient();
        if (client == null) {
            System.out.println("Authentication Error!");
            return;
        }
        App.ClearConsole();
        Scanner cin = new Scanner(System.in);
        System.out.print("Enter your purchase reference: ");
        String ref = cin.nextLine();
        String clientPurchaseStr = FileHandler.GetDataByRef(client.getDirectoryPath()+"/purchases.txt", ref);
        if (clientPurchaseStr == null) {
            System.out.println("Purchase doesn't exist");
            cin.close();
            return;
        }
        Purchase clientPurchase = Purchase.ObjectIt(clientPurchaseStr);
        System.out.println("Here is the price to return: " + clientPurchase.getPrice() * clientPurchase.getQte());
        FileHandler.DeleteDataByRef(client.getDirectoryPath()+"/purchases.txt", ref);
        String prodRef = clientPurchase.getRefProd();
        Inventory.ChangeProdQuantity(prodRef, Inventory.AvailableQuantity(prodRef) + clientPurchase.getQte());
        cin.close();
    }

    private void ExecutePurchases(Purchase[] purchases, Client client) {
        Scanner cin = new Scanner(System.in);
        LoyaltyAccount account = new LoyaltyAccount(client);
        HashSet<String> categories = new HashSet<String>();
        float price = 0;
        System.out.println("Do you want a discount? (0: no, 1: yes): ");
        boolean shouldDiscount = cin.nextInt() == 1;
        for (Purchase purchase : purchases) {
            Product prod = Inventory.GetProdByRef(purchase.getRefProd());
            Inventory.ChangeProdQuantity(prod.getRef(), Inventory.AvailableQuantity(prod.getRef()) - purchase.getQte());
            if (!shouldDiscount) account.RecordPurchase(purchase);
            client.RecordPurchase(purchase);
            categories.add(prod.getCategory());
            price += purchase.getPrice() * purchase.getQte();
        }
        if (shouldDiscount) System.out.println("The new price is: "+ (price - account.GetDiscount((String[])categories.toArray())));
        cin.close();
    }

    public Client GetClient() {
        Scanner cin = new Scanner(System.in);
        int choice;
        System.out.println("Please Sign In/Sign Up the client to begin:");
        System.out.println("1- Sign in          2- Sign Up");
        System.out.print("Choose a number: ");
        choice = cin.nextInt();
        cin.close();
        return choice == 1 ?(Client)Authentication.SignIn('c') :(Client)Authentication.SignUp('c');
    }
}