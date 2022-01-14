package source.classes.users;

import source.classes.Product;
import source.classes.User;

public class Manager extends User{

    public Manager(String name, String surname, String address, String password) {
        super(name, surname, address, password);
    }

    public Manager(String id, String name, String surname, String address, String password) {
        super(id, name, surname, address, password);
    }

    @Override
    public void ShowActions() {
    }

    @Override
    protected void UseSelection(Product product) {
    }
    //TODO: implement missing methods
}
