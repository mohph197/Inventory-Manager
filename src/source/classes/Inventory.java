package source.classes;
import java.util.LinkedList;
import source.services.FileHandler;

public class Inventory {
    public final static String InventoryPath = "./data/inventory.txt";

        public static boolean AddProduct(Product prod, int qte){
        if(DoesExist(prod.getRef()) == null){
            FileHandler.Add(InventoryPath, prod.StringIt()+"|"+qte);
            return true;
        }else return ChangeProdQuantity(prod.getRef(), qte);
    }

    public static boolean ChangeProdQuantity(String ref, int qte){
        String temp = DoesExist(ref);
        if(temp == null ) return false;
        String temp1 = temp.replace(temp.split("|")[6], String.valueOf(qte));
        FileHandler.ModifyData(InventoryPath, temp, temp1);
        return true;
    }

    public static String DoesExist(String ref){
        return FileHandler.GetDataByRef(InventoryPath, ref);
    }

    public static boolean ChangePrice(String ref, float price){
        String temp = DoesExist(ref);
        if(temp == null) return false;
        String temp1 = temp.replace(temp.split("|")[5], String.valueOf(price));
        FileHandler.ModifyData(InventoryPath, temp, temp1);
        return true;
    }

    public static int AvailableQuantity(String ref){
            String temp = DoesExist(ref);
            return Integer.parseInt(temp.split("|")[6]);
        }

    public static Product[] GetProducts(){
        String temp = FileHandler.GetContent(InventoryPath);
        String[] tempArray = temp.split("\n");
        int size = tempArray.length;
        Product[] result = new Product[size];
        for(int i=0; i<size; i++){
           result[i] = Product.ObjectIt(tempArray[i]);
        }
        return result;
    }

    public static String GetProdNameByRef(String ref){
        if(DoesExist(ref) != null) return FileHandler.GetDataByRef(InventoryPath, ref).split("|")[2];
        return null;
    }

    public static Product[] SearchProducts(String method, String key) {
        Product[] temp = GetProducts();
        LinkedList<Product> result = new LinkedList<Product>(); 
        for (Product product : temp) {
            String field = method == "name" ?product.getName()
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
