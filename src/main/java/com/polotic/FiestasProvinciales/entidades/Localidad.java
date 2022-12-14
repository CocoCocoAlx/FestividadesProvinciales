package com.polotic.FiestasProvinciales.entidades;


import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.*;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name="localidades")

public class Localidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @NotBlank(message = "Campo obligatorio")
    @Size(max = 250, message= "Nombre demasiado largo")
    private String nombre;

    @NotBlank(message = "Campo obligatorio")
    @Size(max = 250, message= "Nombre demasiado largo")
    private String informacion;

    private int distancia;

    @ManyToOne
    @JsonBackReference
    private Provincia provincias;


}
