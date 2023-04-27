package model;

public class Driver {
    public static void main(String[] args) {
        Customer customer;
        Customer customer1;
        customer = new Customer("first", "second", "j@domain.com");
        customer1 = new Customer("second", "third", "testemail@domain.net");

        System.out.println(customer);
    }
}
