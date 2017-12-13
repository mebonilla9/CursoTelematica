package co.appreactor.conocimentos.persistencia.entidades;

import java.io.Serializable;

/**
 * Created by lord_nightmare on 1/12/17.
 */

public class Account implements Serializable {

    private String Email;
    private String Password;
    private String ConfirmPassword;


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }
}
