package source.classes.users;

import java.util.Scanner;

import source.App;
import source.classes.Inventory;
import source.classes.Product;
import source.exceptions.ObjectDoesntExistException;

public class Manager extends Agent{

    public Manager(String name, String surname, String address, String password) {
        super(name, surname, address, password);
    }

    public Manager(String id, String name, String surname, String address, String password) throws ObjectDoesntExistException {
        super(id, name, surname, address, password);
    }

    public static boolean AddProduct(){
        App.ClearConsole();
        Scanner cin = new Scanner(System.in);
        String[] prodinfo = new String[5];
        for (int i = 0; i < prodinfo.length; i++) {
            System.out.println(i == 0 ?"Name : "
                              :i == 1 ?"Category : "
                              :i == 2 ?"Description : "
                              :i == 3 ?"Specs : "
                              :i == 4 ?"Price : "
                              :null);
            prodinfo[i] = cin.nextLine();
        }
        Product prod = new Product(prodinfo[0], prodinfo[1], prodinfo[2], prodinfo[3], Float.parseFloat(prodinfo[4]));
        if(Inventory.getProdPath(prod.getRef()) != null){
            System.out.println("Product Already Exists !");
            System.out.println("Would you like to retry :\n 1- Yes    0- No");
            int in = cin.nextInt();cin.nextLine();
            cin.close();
            if(in == 1) return AddProduct();
            return false;
        }
        System.out.println("Please enter the quantity of the Product : ");
        int in = cin.nextInt();cin.nextLine();
        cin.close();
        return Inventory.AddProduct(prod, in);
    }

    public static boolean ChangePrice(){
        App.ClearConsole();
        Scanner cin = new Scanner(System.in);
        System.out.println("Enter the reference of the product : ");
        String ref = cin.nextLine();
        String cat = Inventory.getProdPath(ref);
        if(cat == null){
            System.out.println("Product doesn't exist !\n would you like to retry ? \n 1- Yes   0- No");
            int in = cin.nextInt();cin.nextLine();
            cin.close();
            if(in == 1) return ChangePrice();
        }
        System.out.println("Enter the new Price");
        float price = cin.nextFloat();cin.nextLine();
        while(price <= 0){
            System.out.println("Cannot enter a negative price !\n try again :");
            price = cin.nextFloat();cin.nextLine();
        }
        cin.close();
        return Inventory.ChangePrice(ref, price, cat);
    }
}