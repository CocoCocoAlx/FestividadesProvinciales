package com.polotic.FiestasProvinciales.servicios;


import com.polotic.FiestasProvinciales.entidades.*;
import com.polotic.FiestasProvinciales.repositorios.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class PredioServicio {

@Autowired

PredioRepositorio  predioRepositorio;

public List<Predio> getAll(){
    List<Predio> lista = new ArrayList<>();
      predioRepositorio.findAll().forEach(registro -> lista.add(registro));
            return lista;

}

public Predio getById(Long id){
return predioRepositorio.findById(id).get();

}

public void save(Predio predio){
   predioRepositorio.save(predio);

}

public void delete(Long id){
predioRepositorio.deleteById(id);

}
    
}






    

