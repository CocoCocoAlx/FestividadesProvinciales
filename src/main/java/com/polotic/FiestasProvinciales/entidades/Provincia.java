package com.polotic.FiestasProvinciales.entidades;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name="provincias")


public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @NotBlank(message = "Campo obligatorio")
    @Size(max = 250, message= "Nombre demasiado largo")
    private String nombre;

    @NotBlank(message = "Campo obligatorio")
    @Size(max = 250, message= "Nombre demasiado largo")
    private String informacion;
   
}
