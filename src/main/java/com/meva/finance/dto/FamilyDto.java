package com.meva.finance.dto;

import com.meva.finance.model.Family;
import lombok.Data;

@Data
public class FamilyDto {

    private Integer id;
    private String description;

    //Método de converter uma FamilyDto em Family
    public Family converter(FamilyDto familyDto) {
        Family family = new Family();
        family.setId(familyDto.getId());
        family.setDescription(familyDto.getDescription());
        return family;
    }
}
