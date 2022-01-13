package source.classes;
import java.util.LinkedList;
import source.services.FileHandler;

public abstract class Inventory {
    public final static String InventoryPath = "./data/inventory/";
    public final static String[] categories = {"mi", "em", "ks"};

    public static boolean AddProduct(Product prod, int qte){
        if(DoesExist(prod.getRef())) return
            ChangeProdQuantity(prod.getRef(), AvailableQuantity(prod.getRef()) + qte);
        FileHandler.Add(InventoryPath+prod.getCategory()+".txt", prod.StringIt()+"|"+qte);
        return true; 
    }

    public static boolean ChangeProdQuantity(String ref, int qte){
        String prodData = GetProdDataByRef(ref);
        if(prodData == null ) return false;
        String newData = prodData.replace(prodData.split("|")[6], String.valueOf(qte));
        FileHandler.ModifyData(InventoryPath+(prodData.split("|")[1])+".txt", prodData, newData);
        return true;
    }

    public static boolean DoesExist(String ref){
        Product[] result = GetAllProducts();
        for (Product product : result) {
            if (product.getRef() == ref) return true;
        }
        return false;
    }

    public static boolean ChangePrice(String ref, float price, String cat){
        String temp = GetProdDataByRef(ref);
        if(temp == null) return false;
        String temp1 = temp.replace(temp.split("|")[5], String.valueOf(price));
        FileHandler.ModifyData(InventoryPath+cat+".txt", temp, temp1);
        return true;
    }

    public static int AvailableQuantity(String ref){
            String temp = GetProdDataByRef(ref);
            return Integer.parseInt(temp.split("|")[6]);
        }

    public static Product[] GetProductsByCategroy(String cat){
        String temp = FileHandler.GetContent(InventoryPath+cat+".txt");
        String[] tempArray = temp.split("\n");
        int size = tempArray.length;
        Product[] result = new Product[size];
        for(int i=0; i<size; i++){
           result[i] = Product.ObjectIt(tempArray[i]);
        }
        return result;
    }

    public static Product[] GetAllProducts(){
        String temp = "";
        for (String cat : categories) {
            temp += FileHandler.GetContent(InventoryPath+cat+".txt");
        }
        String[] tempArray = temp.split("\n");
        int size = tempArray.length;
        Product[] result = new Product[size];
        for(int i=0; i<size; i++){
           result[i] = Product.ObjectIt(tempArray[i]);
        }
        return result;
    }

    public static String GetProdDataByRef(String ref){
        for (String cat : categories) {
            String prodStr = FileHandler.GetDataByRef(InventoryPath+cat+".txt", ref);
            if (prodStr!=null) return prodStr;
        }
        return null;
    }

    public static Product[] SearchProducts(String method, String key) {
        Product[] temp = GetAllProducts();
        LinkedList<Product> result = new LinkedList<Product>(); 
        for (Product product : temp) {
            String field = method == "name" ?product.getName()
                    : method == "cat" ?product.getCategory()
                    : method == "ref" ?product.getRef()
                    : method == "desc" ?product.getDescription()
                    : method == "spec" ?product.getSpecs()
                    : null;
            if (field == null) return null;
            if (field.indexOf(key) != -1) result.add(product);
            }
        return (Product[])result.toArray();
    }
}
