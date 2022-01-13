package source.classes.users;

import source.classes.User;
import source.exceptions.ObjectDoesntExistException;
import source.services.FileHandler;

public class Agent extends User{
    public static final String filePath = "./data/users/agents.txt";

    public Agent(String id, String name, String surname, String address, String password) throws ObjectDoesntExistException {
        super(id, name, surname, address, password);
        if (FileHandler.GetDataByRef(filePath, id) == null)
            throw new ObjectDoesntExistException();
    }

    public Agent(String name, String surname, String address, String password) {
        super(name, surname, address, password);
        FileHandler.CreateDIR("./data/users");
        FileHandler.Add(filePath, StringIt());
    }

    public String StringIt() {
		String returned = this.id+"|"+this.name+"|"+this.surname+"|"+this.address+"|"+this.password;
		return returned;
	}

    static void InitPurchase() {
        
    }

    static void ReturnProduct(String ref) {
        
    }

    @Override
    public void ShowActions() {

    }
}