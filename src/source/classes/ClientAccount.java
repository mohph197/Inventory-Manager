package source.classes;
import source.classes.users.Client;
import source.services.FileHandler;

public class ClientAccount {
    private  String clientAccountPath;

    public ClientAccount(Client client) {
        this.clientAccountPath = client.getDirectoryPath()+"/account.txt";
        if(!FileHandler.FileExists(clientAccountPath)) FileHandler.Add(clientAccountPath,
            "mi 0\nem 0\nks 0");
    }

    public boolean AddPurchase(Purchase pur){
        String prodCat = Inventory.GetProdByRef(pur.getRefProd()).getCategory();
        String oldData = FileHandler.GetDataByRef(clientAccountPath, prodCat);
        String oldPrice = oldData.split(" ")[1];
        String newData = oldData.replace(oldPrice, String.valueOf(Integer.parseInt(oldPrice) + pur.getPrice() * pur.getQte()));
        return FileHandler.ModifyData(clientAccountPath, oldData, newData);
    }
    
}
