package source.services;
import java.time.LocalDate;

public class Purchase {
    private LocalDate date = LocalDate.now();
    private String ref;
    private String qte;
    private float price;


    public Purchase(LocalDate date, String ref, String qte, float price) {
        this.date = date;
        this.ref = ref;
        this.qte = qte;
        this.price = price;
    }

    public String StringIt(){
        String returned = this.ref+"|"+this.date+"|"+this.qte+"|"+this.price;
        return returned;
    }

    
    
}
