package source.services;

import source.classes.Product;

public abstract class Search {

    public static Product[] ByName(String key){
        
        return new Product[0];
    }

    public static Product[] ByRef(String ref){

        return new Product[0];
    }

    public static Product[] ByDescription(String desc){

        return new Product[0];
    }

    public static Product[] BySpecs(String spec){

        return new Product[0];
    }

}
