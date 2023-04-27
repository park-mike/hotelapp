package model;

import java.util.Objects;

public class Customer {
    public String firstName;
    private final String lastName;
    private final String email;
    //override toString
    // add regex to validate the email, if not proper email formation throw exception
    // @Override public String toString() {}
    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }

    @Override
    public String toString() {
        return "Summary:  " + firstName + " " + lastName + " " + email;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer customer = (Customer) obj;
        return Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(email, customer.email);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

}
