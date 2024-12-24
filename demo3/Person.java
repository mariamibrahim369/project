package sample.demo3;


import java.time.LocalDate;
import java.util.Scanner;
public abstract class Person {
    private String username;
    private String password;
    private String dateofbirth;

    Person(String username, String password,String dateofbirth) {
        this.username=username;
        this.password=password;
        this.dateofbirth=dateofbirth;

    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }
    public String getDateofbirth() {
        return dateofbirth;
    }

    public String toString() {
        return "Username: " + getUsername() + "\n" + "Password: " + getPassword() + "\n"+ "Date of birth:" + getDateofbirth() ;
    }
}
