package source.classes;
import java.util.LinkedList;

import source.services.FileHandler;

public abstract class Inventory {
    public final static String inventoryPath = "./data/inventory/";
    public final static String[] categories = {"mi", "em", "ks"};

    public static boolean AddProduct(Product prod, int qte){
        if(DoesExist(prod.getRef())) return
            ChangeProdQuantity(prod.getRef(), AvailableQuantity(prod.getRef()) + qte);
        FileHandler.CreateDIR(inventoryPath);
        FileHandler.Add(inventoryPath+prod.getCategory()+".txt", prod.StringIt()+" "+qte);
        return true; 
    }

    public static boolean ChangeProdQuantity(String ref, int qte){
        String prodLine = GetProdDataByRef(ref);
        if(prodLine == null ) return false;
        String[] prodData = prodLine.split(" ");
        prodData[6] = String.valueOf(qte);
        String newData = "";
        for (String string : prodData) {
            newData += string+" ";
        }
        newData = newData.substring(0, newData.length() - 1);
        FileHandler.ModifyData(inventoryPath+prodData[1]+".txt", prodLine, newData);
        return true;
    }

    public static boolean DoesExist(String ref){
        Product[] result = GetAllProducts();
        if (result == null) return false;
        for (Product product : result) {
            if (product.getRef().equals(ref)) return true;
        }
        return false;
    }

    public static boolean ChangePrice(String ref, float price, String cat){
        String temp = GetProdDataByRef(ref);
        if(temp == null) return false;
        String temp1 = temp.replace(temp.split(" ")[5], String.valueOf(price));
        FileHandler.ModifyData(inventoryPath+cat+".txt", temp, temp1);
        return true;
    }

    public static int AvailableQuantity(String ref){
        String temp = GetProdDataByRef(ref);
        if (temp == null) return 0;
        return Integer.parseInt(temp.split(" ")[6]);
    }

    public static Product[] GetProductsByCategroy(String cat){
        String temp = FileHandler.GetContent(inventoryPath+cat+".txt");
        if (temp == null) return new Product[0];
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
            String catContent = FileHandler.GetContent(inventoryPath+cat+".txt");
            if (catContent != null) temp += catContent;
        }
        if (temp.equals("")) {
            return new Product[0];
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
            String prodStr = FileHandler.GetDataByRef(inventoryPath+cat+".txt", ref);
            if (prodStr != null) return prodStr;
        }
        return null;
    }

    public static Product GetProdByRef(String ref){
        String prodStr = GetProdDataByRef(ref);
        if (prodStr == null) return null;
        return Product.ObjectIt(prodStr);
    }

    public static Product[] SearchProducts(String method, String key) {
        Product[] temp = GetAllProducts();
        if (temp == null) return new Product[0];
        LinkedList<Product> result = new LinkedList<Product>();
        for (Product product : temp) {
            String field=method == "name" ?product.getName()
                        :method == "ref" ?product.getRef()
                        :method == "desc" ?product.getDescription()
                        :method == "spec" ?product.getSpecs()
                        :null;
            if (field == null) return new Product[0];
            if (field.indexOf(key) != -1) result.add(product);
        }
        // Product[] finalResult = new Product[result.size()];
        // for (int i = 0; i < finalResult.length; i++) {
        //     finalResult[i] = result.get(i);
        // }
        // return finalResult;
        return result.toArray(new Product[result.size()]);
    }
    
    public static String getProdPath(String ref){
        for (String cat : categories) {
            String prodData = FileHandler.GetDataByRef(inventoryPath + cat + ".txt", ref);
            if (prodData != null) return inventoryPath + cat + ".txt";
        }
        return null;
    }
}
