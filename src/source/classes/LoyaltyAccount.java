package source.classes;
import source.classes.users.Client;
import source.services.FileHandler;

public class LoyaltyAccount {
    private String clientAccountPath;
    private final String[] categoriesDetailed = {"Informatique et Mobiles", "Electromenager", "Kits Solaires"};

    public LoyaltyAccount(Client client) {
        this.clientAccountPath = client.getDirectoryPath()+"/account.txt";
        if(!FileHandler.FileExists(clientAccountPath)) FileHandler.Add(clientAccountPath,"mi 0\nem 0\nks 0");
    }

    public boolean RecordPurchase(Purchase pur){
        String prodCat = Inventory.GetProdByRef(pur.getRefProd()).getCategory();
        String oldData = FileHandler.GetDataByRef(clientAccountPath, prodCat);
        String oldPrice = oldData.split(" ")[1];
        String newData = oldData.replace(oldPrice, String.valueOf(Float.parseFloat(oldPrice) + pur.getPrice() * pur.getQte()));
        return FileHandler.ModifyData(clientAccountPath, oldData, newData);
    }
    
    public void ShowDetails() {
        String[] data = FileHandler.GetContent(clientAccountPath).split("\n");
        for (int i = 0; i < 3; i++) {
            float price = Float.parseFloat(data[i].split(" ")[1]);
            float discount = price* (data[i].split(" ")[0] == "mi" ?5
                                    :data[i].split(" ")[0] == "em" ?10
                                    :data[i].split(" ")[0] == "ks" ?15
                                    :0) / 100.0f;
            System.out.println((i+1)+"- "+categoriesDetailed[i]+": "+price+" --> "+discount);
        }
    }

    public float GetDiscount(String[] categories) {
        float discount = 0;
        for (String cat : categories) {
            String oldData = FileHandler.GetDataByRef(clientAccountPath, cat);
            float oldPrice = Float.parseFloat(oldData.split(" ")[1]);
            discount += oldPrice *  (cat == "mi" ?5
                                    :cat == "em" ?10
                                    :cat == "ks" ?15
                                    :0) / 100.0f;
            FileHandler.ModifyData(clientAccountPath, oldData, cat + "|0");
        }
        return discount;
    }
}
