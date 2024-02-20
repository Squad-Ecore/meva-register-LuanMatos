package com.meva.finance.dto;

import com.meva.finance.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto  implements Serializable {

    private String cpf;

    private String name;

    private Character genre;

    private Date birth;
    @NotNull
    @NotEmpty
    private String state;
    @NotNull
    @NotEmpty
    private String city;
    @NotNull
    private FamilyDto familyDto;

    public UserDto(User user){

    }

    //Converte o tipo UserDto recebido na requisição POST para um User
    public User converter() {
        //User user = new User();
        return User().
    }



}
