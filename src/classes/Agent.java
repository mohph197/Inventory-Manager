package classes;

public class Agent {
    static void InitPurchase() {

    }

    static void ReturnProduct(String ref) {
        Product[] results = Search.ByRef(ref);
        if (results.length == 1) {
            Stock.AddProduct(results[0]);
        }
    }
}