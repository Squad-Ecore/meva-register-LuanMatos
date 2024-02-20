package com.meva.finance.dto;

import com.meva.finance.model.Family;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FamilyDto {

    @NotNull
    private Integer id;
    @NotNull @NotEmpty
    private String description;

    //Método de converter uma FamilyDto em Family
    public Family converter(FamilyDto familyDto){
        Family family = new Family();
        family.setId(familyDto.getId());
        family.setDescription(familyDto.getDescription());
        return family;
    }

    //Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
