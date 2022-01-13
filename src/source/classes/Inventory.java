package source.classes;
import java.util.LinkedList;
import source.services.FileHandler;

public abstract class Inventory {
    public final static String InventoryPath = "./data/inventory/";

    public static boolean AddProduct(Product prod, int qte){
        if(DoesExist(prod.getRef(), prod.getCategory()) == null){
            FileHandler.Add(InventoryPath+prod.getCategory()+".txt", prod.StringIt()+"|"+qte);
            return true;
        }else return ChangeProdQuantity(prod.getRef(), qte, prod.getCategory());
    }

    public static boolean ChangeProdQuantity(String ref, int qte, String cat){
        String temp = DoesExist(ref, cat);
        if(temp == null ) return false;
        String temp1 = temp.replace(temp.split("|")[6], String.valueOf(qte));
        FileHandler.ModifyData(InventoryPath+cat+".txt", temp, temp1);
        return true;
    }

    public static String DoesExist(String ref, String cat){
        return FileHandler.GetDataByRef(InventoryPath+cat+".txt", ref);
    }

    public static boolean ChangePrice(String ref, float price, String cat){
        String temp = DoesExist(ref, cat);
        if(temp == null) return false;
        String temp1 = temp.replace(temp.split("|")[5], String.valueOf(price));
        FileHandler.ModifyData(InventoryPath+cat+".txt", temp, temp1);
        return true;
    }

    public static int AvailableQuantity(String ref, String cat){
            String temp = DoesExist(ref, cat);
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
        String temp = FileHandler.GetContent(InventoryPath+"mi.txt")+FileHandler.GetContent(InventoryPath+"em.txt")+FileHandler.GetContent(InventoryPath+"ks.txt");
        String[] tempArray = temp.split("\n");
        int size = tempArray.length;
        Product[] result = new Product[size];
        for(int i=0; i<size; i++){
           result[i] = Product.ObjectIt(tempArray[i]);
        }
        return result;
    }

    public static String GetProdNameByRef(String ref, String cat){
        if(DoesExist(ref, cat) != null) return FileHandler.GetDataByRef(InventoryPath+cat+".txt", ref).split("|")[2];
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
