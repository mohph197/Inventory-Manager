package source.classes;
import java.util.Scanner;

import source.App;
import source.interfaces.Storable;
import source.services.FileHandler;

public abstract class User implements Storable{
    protected String id;
    protected String name;
    protected String surname;
    protected String address;
    protected String password;
    
    public User(String name, String surname, String address, String password) {
        this.id = FileHandler.GenerateUID(0);
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.password = password;
    }

    public User(String id, String name, String surname, String address, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.password = password;
    }

    public String getId() {
        return id;
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

    public abstract void ShowActions();
    protected abstract void UseSelection(Product product);

    public String StringIt() {
		String returned = this.id+"|"+this.name+"|"+this.surname+"|"+this.address+"|"+this.password;
		return returned;
	}

    protected Product SearchProduct() {
        App.ClearConsole();
        Scanner cin = new Scanner(System.in);
        System.out.println("Do you want to search by:");
        System.out.println("1- by name   2- by reference   3- by description   4- by specs");
        System.out.print("Choose a number: ");
        int choice = cin.nextInt();
        App.ClearConsole();
        System.out.print("Enter search key: ");
        String searchKey = cin.nextLine();
        cin.close();
        return ShowSelectProducts(Inventory.SearchProducts(
            choice == 1 ?"name"
           :choice == 2 ?"ref"
           :choice == 3 ?"desc"
           :choice == 4 ?"spec"
           :null, searchKey)
        );
    }

    protected Product ShowSelectProducts(Product[] products) {
        App.ClearConsole();
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
            return null;
        }
        App.ClearConsole();
        if (choice >= products.length) {
            System.out.println("Wrong Number!");
            cin.close();
            ShowSelectProducts(products);
        }
        Product product = products[choice - 1];
        System.out.println("The Product you selected is:");
        System.out.println(product.StringIt());
        cin.close();
        return product;
    }

    protected Product[] ChooseProductCategory() {
        App.ClearConsole();
        Scanner cin = new Scanner(System.in);
        System.out.println("Choose a category:");
        System.out.println("1- Informatique et Mobiles   2- Electromenager   3- Kits Solaires   4- All");
        System.out.print("Choose a number: ");
        int choice = cin.nextInt();
        cin.close();
        return   choice == 1 ?Inventory.GetProductsByCategroy("mi")
                :choice == 2 ?Inventory.GetProductsByCategroy("em")
                :choice == 3 ?Inventory.GetProductsByCategroy("ks")
                :choice == 4 ?Inventory.GetAllProducts()
                :null;
    }
}
