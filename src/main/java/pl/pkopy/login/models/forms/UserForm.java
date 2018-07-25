package pl.pkopy.login.models.forms;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserForm {

    @NotEmpty
    private String userName;
    @NotEmpty
    private String email;
    private String location;
    @NotEmpty
    private String password;

    public UserForm(){

    }


}
