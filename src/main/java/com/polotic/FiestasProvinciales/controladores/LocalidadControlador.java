package com.polotic.FiestasProvinciales.controladores;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.polotic.FiestasProvinciales.entidades.Localidad;
import com.polotic.FiestasProvinciales.servicios.LocalidadServicio;

@RestController
@RequestMapping("localidades")

public class LocalidadControlador implements WebMvcConfigurer {

    @Autowired
    LocalidadServicio localidadServicio;

    @GetMapping
    private ModelAndView inicio()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Listado de localidades");
        maw.addObject("vista", "localidad/inicio");
        maw.addObject("localidad", localidadServicio.mostrarTodos());
        return maw;

    }

    @GetMapping("/agregar")
    private ModelAndView agregar(Localidad localidad)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Agregar localidad");
        maw.addObject("vista", "localidad/agregar");
        return maw;

    }

    @PostMapping("/agregar")
    public ModelAndView guardar(@RequestParam("archivo") MultipartFile archivo,
    @Valid Localidad localidad, BindingResult br, RedirectAttributes ra)
    {
        if (archivo.isEmpty())
            br.reject("archivo", "Por favor, cargar un archivo válido");

        if (br.hasErrors()) {
            return this.agregar(localidad);
        }

        localidadServicio.guardar(localidad);

        String tipo = archivo.getContentType();
        String extension = "." + tipo.substring(tipo.indexOf('/') + 1, tipo.length());
        
        ModelAndView maw = this.inicio();   

        localidadServicio.guardar(localidad);
        maw.addObject("correcto", "El archivo fue cargado exitosamente");
        return maw;
    }

    @GetMapping("/editar/{id}")
    private ModelAndView editar(@PathVariable("id") Long id, Localidad localidad, boolean estaGuardado) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Editar localidad");
        maw.addObject("vista", "localidad/editar");
        

        if (estaGuardado)
            maw.addObject("localidad", localidadServicio.seleccionarPorId(id));

        return maw;
    }

    @PutMapping("editar/{id}")
    private ModelAndView actualizar(@PathVariable("id") Long id,
    @RequestParam(value = "archivo", required = false) MultipartFile archivo,
    @Valid Localidad localidad, BindingResult br, RedirectAttributes ra)
    {
        if (br.hasErrors())
        {
        return this.editar(id, localidad, false);
        }

        Localidad registro = localidadServicio.seleccionarPorId(id);
        registro.setNombre(localidad.getNombre());
        registro.setInformacion(localidad.getInformacion());
        ModelAndView maw = this.inicio();

        
        localidadServicio.guardar(localidad);
        maw.addObject("correcto","localidad editada correctamente.");
        return maw;
    }

    @DeleteMapping("/id")
    private ModelAndView borrar(@PathVariable("id") Long id)
    {
        localidadServicio.borrar(id);
        ModelAndView maw = this.inicio();
        maw.addObject("correcto", "localidad eliminada correctamente.");
        return maw;
    }
    
    
}
