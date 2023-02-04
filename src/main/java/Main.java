import ru.javarush.entity.*;

public class Main {

    public static void main(String[] args) {

        MyTestClass myTestClass = new MyTestClass();

        myTestClass.newFilmWasMade();
        myTestClass.customerReturnInventoryToStore();
        Customer customer = myTestClass.createCustomer();
        myTestClass.customerRentInventory(customer);
    }
}
