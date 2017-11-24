package lyhoangvinh.com.callback;

/**
 * Created by ADMIN on 11/24/2017.
 */

public class Person {
    private String firstname;
    private String lastname;

    public Person(String lastname, String firstname) {
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public Person setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public Person setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }
}