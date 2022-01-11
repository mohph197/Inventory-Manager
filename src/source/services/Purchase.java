package source.services;
import java.time.LocalDate;

public class Purchase {
    private LocalDate date;
    private String ref;
    private String qte;
    private float price;


    public Purchase(String ref, String qte, float price) {
        this.date = LocalDate.now();
        this.ref = ref;
        this.qte = qte;
        this.price = price;
    }

    public String StringIt(){
        String returned = this.ref+"|"+this.date+"|"+this.qte+"|"+this.price;
        return returned;
    }

    
}
