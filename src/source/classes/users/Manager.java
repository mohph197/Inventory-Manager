package source.classes.users;

import java.util.Scanner;

import source.App;
import source.classes.Inventory;
import source.classes.Product;
import source.classes.User;
import source.exceptions.ObjectDoesntExistException;
import source.services.FileHandler;

public class Manager extends User {
    public static final String filePath = "./data/users/managers.txt";

    public Manager(String name, String surname, String address, String password) {
        super(name, surname, address, password);
        FileHandler.CreateDIR("./data/users");
        FileHandler.Add(filePath, StringIt());
    }

    public Manager(String id, String name, String surname, String address, String password) throws ObjectDoesntExistException {
        super(id, name, surname, address, password);
        if (FileHandler.GetDataByRef(filePath, id) == null)
            throw new ObjectDoesntExistException();
    }

    private boolean AddProduct(){
        App.ClearConsole();
        Scanner cin =  App.cin;
        String[] prodinfo = new String[5];
        for (int i = 0; i < 5; i++) {
            System.out.print(i == 0 ?"Name : "
                              :i == 1 ?"Category : "
                              :i == 2 ?"Description : "
                              :i == 3 ?"Specs : "
                              :i == 4 ?"Price : "
                              :null);
            prodinfo[i] = cin.nextLine();
        }
        String category = prodinfo[1];
        if (!category.equals("mi") && !category.equals("em") && !category.equals("ks")) return false;
        Product prod = new Product(prodinfo[0], prodinfo[1], prodinfo[2], prodinfo[3], Float.parseFloat(prodinfo[4]));
        // if(Inventory.DoesExist(prod.getRef())){
        //     System.out.println("Product Already Exists !");
        //     System.out.println("Would you like to retry :\n 1- Yes    0- No");
        //     int in = cin.nextInt();cin.nextLine();
             
        //     if(in == 1) return AddProduct();
        //     return false;
        // }
        System.out.print("Quantity : ");
        int in = cin.nextInt();cin.nextLine();
        return Inventory.AddProduct(prod, in);
    }

    private boolean ChangePrice(){
        App.ClearConsole();
        Scanner cin =  App.cin;
        System.out.println("Enter the reference of the product : ");
        String ref = cin.nextLine();
        String cat = Inventory.GetProdByRef(ref).getCategory();
        if (!Inventory.DoesExist(ref)) {
            System.out.println("Product doesn't exist !\n would you like to retry ? \n 1- Yes   0- No");
            int in = cin.nextInt();cin.nextLine();
            if (in == 1) return ChangePrice();
            if (in == 0) return false;
        }
        System.out.println("Enter the new Price");
        float price = cin.nextFloat();cin.nextLine();
        while(price <= 0){
            System.out.println("Cannot enter a negative price !\n try again :");
            price = cin.nextFloat();cin.nextLine();
        }
         
        return Inventory.ChangePrice(ref, price, cat);
    }

    @Override
    public void ShowActions() {
        App.ClearConsole();
        Scanner cin =  App.cin;
        System.out.println("Choose an action:");
        System.out.println("1- Add a new product");
        System.out.println("2- Change the price for a product");
        System.out.println("0- Exit");
        System.out.print("Choose a number: ");
        int choice = cin.nextInt();cin.nextLine();
        switch (choice) {
            case 1:
                AddProduct();
                break;
            case 2:
                ChangePrice();
                break;
            case 0:
                App.ClearConsole();
                return;
            default:
                System.out.println("Wrong Number!");
                ShowActions();
                break;
        }
        ShowActions();
    }
    
    @Override
    protected void UseSelection(Product product) {}
}