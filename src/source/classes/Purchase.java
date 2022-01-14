package source.classes;
import java.time.LocalDate;

import source.services.FileHandler;

public class Purchase {
    private String ref;
    private String refProd;
    private LocalDate date;
    private int qte;
    private float price;

    public Purchase(String ref, String refProd, LocalDate date, int qte, float price) {
        this.ref = ref;
        this.refProd = refProd;
        this.date = date;
        this.qte = qte;
        this.price = price;
    }

    public Purchase(String refProd, int qte, float price) {
        this.date = LocalDate.now();
        this.ref = FileHandler.GenerateUID(2);
        this.qte = qte;
        this.price = price;
    }

    public String StringIt(){
        String returned = this.ref+" "+this.refProd+" "+this.date+" "+this.qte+" "+this.price;
        return returned;
    }

    public static Purchase ObjectIt(String data) {
        if (data == null) return null;
        String[] dataArray = data.split(" ");
        return new Purchase(dataArray[0], dataArray[1], LocalDate.parse(dataArray[2]),
                            Integer.parseInt(dataArray[3]), Float.parseFloat(dataArray[4]));
    }

    public String getRef() {
        return ref;
    }

    public String getRefProd() {
        return refProd;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getQte() {
        return qte;
    }

    public float getPrice() {
        return price;
    }
}
