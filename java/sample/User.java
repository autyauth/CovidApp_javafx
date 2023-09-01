package sample;

public class User {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String userName;
    private String password;
    private String date;
    private String vaccine1;
    private String vaccine2;
    private String vaccine3;
    private String vaccine4;
    private String address;

    public User(String firstName, String lastName, String phoneNumber, String email, String userName,
                String password, String date,String address, String vaccine1, String vaccine2, String vaccine3, String vaccine4)
    {

        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.date = date;
        this.address = address;
        this.vaccine1 = vaccine1;
        this.vaccine2 = vaccine2;
        this.vaccine3 = vaccine3;
        this.vaccine4 = vaccine4;
    }




    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getDate() {
        return date;
    }

    public String getVaccine1() {
        return vaccine1;
    }

    public String getVaccine2() {
        return vaccine2;
    }

    public String getVaccine3() {
        return vaccine3;
    }

    public String getVaccine4() {
        return vaccine4;
    }

    public String getAddress() {
        return address;
    }
}
