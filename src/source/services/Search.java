package source.services;

import source.classes.Product;

abstract class Search {

    static Product[] ByName(String key){
        
        return new Product[0];
    }

    static Product[] ByRef(String ref){

        return new Product[0];
    }

    static Product[] ByDescription(String desc){

        return new Product[0];
    }

    static Product[] BySpecs(String spec){

        return new Product[0];
    }

}
