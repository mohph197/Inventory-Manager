package source.classes;
import java.io.IOException;
import java.util.Scanner;

public abstract class Client {
    private String id;
    private String name;
    private String surname;
    private String address;

    public Client(String id, String name, String surname, String address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    public String StringIt(){
		String returned = this.id+"|"+this.name+"|"+this.surname+"|"+this.address+"|";
		return returned;
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
                Cart.ShowCart(id);
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
        int choice = 1;
        if (products.length > 1) {
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
        }
        System.out.println("The Product you selected is:");
        System.out.println(products[choice - 1].StringIt());
        System.out.println("So you want to:");
        System.out.println("1- Add to Cart   0- Go Back");
        System.out.print("Choose a number: ");
        choice = cin.nextInt();
        if (choice == 1) 
        cin.close();
    }
    private void RequestDiscount() {}
    private void CheckAccount() {}
}
