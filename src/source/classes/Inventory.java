package source.classes;
// import java.util.logging.FileHandler;
import source.services.FileHandler;

public class Inventory {
    static void AddProduct(Product prod){
        String str = prod.StringIt();
        FileHandler.Add("./Data/Inventory.txt", str);

    }

    static void ChangeQuantity(String ref, int qte){
        
    }

    static void ChangePrice(float price){

    }

    static Product[] GetProducts(){

        return new Product[0];
    }

    static int GetQuantity(String ref){

        return 0;
    }

    static Product[] SearchProduct(String method, String key) {
        
        return new Product[0];
    }
    
}
