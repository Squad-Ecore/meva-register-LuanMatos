package com.meva.finance.dto;

import com.meva.finance.model.Family;
import com.meva.finance.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String cpf;
    private String name;
    private Character genre;
    private Date birth;
    private String state;
    private String city;
    private FamilyDto familyDto;

    //Converte o tipo UserDto recebido na requisição POST para um User
    public User converter() {
        Family family = familyDto.converter(familyDto);
        return new User(cpf, name, genre, birth, state, city, family);
    }
}

