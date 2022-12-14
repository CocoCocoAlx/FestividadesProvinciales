package com.polotic.FiestasProvinciales.entidades;


import javax.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Predio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   
    private long id;
    private String ubicación;
    private int capacidad;

    @Override
     public String toString(){
        return this.ubicación;
     }
    

}
