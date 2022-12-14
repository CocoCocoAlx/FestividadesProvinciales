package com.polotic.FiestasProvinciales.controladores;

    import com.polotic.FiestasProvinciales.entidades.*;
import com.polotic.FiestasProvinciales.servicios.PredioServicio;

import java.util.*;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.DeleteMapping;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.PutMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    
    
    @RestController
    @RequestMapping("predio")
    
    public class PredioControlador {
        
        @Autowired
        PredioServicio  predioServicio; 
       
        @GetMapping
       
         private List<Predio> index(){
       return predioServicio.getAll();
       
         }
       
         @GetMapping("/{id}")
       private Predio one(@PathVariable("id") Long id){
           return predioServicio.getById(id);
       }
       
       @PostMapping
       private Long save(@RequestBody Predio predio){
            predioServicio.save(predio);
               return predio.getId();
       
       }
       
       @PutMapping
       
       private Predio update(@RequestBody Predio predio){
             predioServicio.save(predio);
                  return predio;
       }
       
       @DeleteMapping("/{id}")
       private void delete(@PathVariable("id") Long id){
           predioServicio.delete(id);
       }
    
    
    }   
    




    

