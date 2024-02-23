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

    //Construtor necessário na chamada do URI do método "update"
    public UserDto(User user) {
        this.cpf = user.getCpf();
        this.name = user.getName();
        this.genre = user.getGenre();
        this.birth = user.getBirth();
        this.state = user.getState();
        this.city = user.getCity();
        this.familyDto = convertFamilyToFamilyDto(user.getFamily());
    }

    // Método para converter Family para FamilyDto
    private FamilyDto convertFamilyToFamilyDto(Family family) {
        FamilyDto familyDto = new FamilyDto();
        familyDto.setId(family.getId());
        familyDto.setDescription(family.getDescription());
        return familyDto;
    }
}

