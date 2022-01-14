package source.classes;

import source.classes.users.Client;
import source.services.FileHandler;

public class Cart {
    private Client client;
    private String filePath;

    public Cart(Client client) {
        this.client = client;
        this.filePath = client.getDirectoryPath()+"/cart.txt";
    }

    public Purchase[] GetCartPurchases() {
        String[] data = FileHandler.GetContent(filePath).split("\n");
        Purchase[] purchases = new Purchase[data.length];
        for (int i = 0; i < data.length; i++) {
            purchases[i] = Purchase.ObjectIt(data[i]);
        }
        return purchases;
    }

    public void ShowCart() {
        String data = FileHandler.GetContent(filePath);
        if (data == null || data.equals("")) {
            System.out.println("Your Cart is empty!");
            return;
        }
        String[] dataLines = data.split("\n");
        float cost = 0;
        for (int i = 0; i < dataLines.length; i++) {
            String[] line = dataLines[i].split(" ");
            dataLines[i] = dataLines[i].replace(line[1], Inventory.GetProdDataByRef(line[1]).split(" ")[2]);
            System.out.println((i+1)+"- "+dataLines[i]);
            cost += Float.parseFloat(line[4]) * Integer.parseInt(line[3]);
        }
        System.out.println("The cost is: "+cost);
    }

    public boolean AddPurchase(Purchase pur) {
        return FileHandler.Add(filePath, pur.StringIt());
    }

    public Client getClient() {
        return client;
    }

    public String getFilePath() {
        return filePath;
    }
}

