package source.classes;

import source.services.FileHandler;
import source.services.Purchase;

public class Cart {
    private Client client;
    private String filePath;

    public Cart(Client client) {
        this.client = client;
        this.filePath = client.getDirectoryPath()+"/cart.txt";
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
            String[] line = dataLines[i].split("|");
            line[1] = Inventory.GetProdNameByRef(line[1]);
            System.out.println((i+1)+"- "+line[0]+"|"+line[1]+"|"+line[2]+"|"+line[3]+"|"+line[4]);
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

