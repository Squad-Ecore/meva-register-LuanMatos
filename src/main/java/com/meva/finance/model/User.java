package com.meva.finance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_meva")
public class User {

    @Id
    private String cpf;
    private String name;
    private Character genre;
    private Date birth;
    private String state;
    private String city;
    @ManyToOne
    @JoinColumn(name = "id_family", nullable = false)
    private Family family;
}
