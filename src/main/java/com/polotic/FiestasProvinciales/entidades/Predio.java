package com.polotic.FiestasProvinciales.entidades;


import java.util.List;

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

   //  @OneToMany(mappedBy = "predio")
   //  private List<Fiesta> fiestas;
    
    @Override
     public String toString(){
        return this.ubicación;
     }
    

}
