package com.polotic.FiestasProvinciales.controladores;

import java.io.File;
import java.nio.file.Paths;

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

import com.polotic.FiestasProvinciales.entidades.Predio;
import com.polotic.FiestasProvinciales.servicios.FiestaServicio;
import com.polotic.FiestasProvinciales.servicios.PredioServicio;

@RestController
@RequestMapping("predios")
public class PredioControlador implements WebMvcConfigurer {

    @Autowired
    FiestaServicio fiestaServicio;

    @Autowired
    PredioServicio predioServicio;

    @GetMapping
    private ModelAndView inicio() {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Listado de predios");
        maw.addObject("vista", "predios/inicio");
        maw.addObject("predios", predioServicio.mostrarTodos());
        return maw;

    }
   @GetMapping("/{id}")
    private ModelAndView uno(@PathVariable("id") Long id) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Detalle de la festividad #" + id);
        maw.addObject("vista", "predios/ver");
        maw.addObject("predios", predioServicio.seleccionarPorId(id));
        return maw;
    }

    @GetMapping("/agregar")
    private ModelAndView agregar(Predio predio) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Agregar festividad");
        maw.addObject("vista", "predios/agregar");
        return maw;

    }

    @PostMapping("/agregar")
    public ModelAndView guardar(@RequestParam("archivo") MultipartFile archivo,
            @Valid Predio predio, BindingResult br, RedirectAttributes ra) {
        if (archivo.isEmpty())
            br.reject("archivo", "Por favor, cargar un archivo válido");

        if (br.hasErrors()) {
            return this.agregar(predio);
        }

        predioServicio.guardar(predio);

        String tipo = archivo.getContentType();
        String extension = "." + tipo.substring(tipo.indexOf('/') + 1, tipo.length());
        String foto = predio.getId() + extension;
        String ruta = Paths.get("src/main/resources/static/images/fiestas", foto).toAbsolutePath().toString();
        ModelAndView maw = this.inicio();

        try {
            archivo.transferTo(new File(ruta));
        } catch (Exception e) {
            maw.addObject("error", "No se pudo guardar la imagen");
            return maw;
        }

        predio.setFoto(foto);
        predioServicio.guardar(predio);
        maw.addObject("exito", "Predio agregada exitosamente");
        return maw;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id, Predio predio) {
        return this.editar(id, predio, true);
    }

    public ModelAndView editar(@PathVariable("id") Long id, Predio predio, boolean estaGuardado) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Editar festividad");
        maw.addObject("vista", "predios/editar");
        maw.addObject("predio", predioServicio.mostrarTodos());

        if (estaGuardado)
            maw.addObject("predio", predioServicio.seleccionarPorId(id));
        else
            predio.setFoto(predioServicio.seleccionarPorId(id).getFoto());

        return maw;
    }

    @PutMapping("/editar/{id}")
    private ModelAndView actualizar(@PathVariable("id") Long id,
            @RequestParam(value = "archivo", required = false) MultipartFile archivo,
            @Valid Predio predio, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            return this.editar(id, predio, false);
        }

        Predio registro = predioServicio.seleccionarPorId(id);
        registro.setNombre(predio.getNombre());
        registro.setDescripcion(predio.getDescripcion());
        registro.setFechaPresentacion(predio.getFechaPresentacion());
        //registro.setFechaFin(predio.getFechaFin());
        registro.setEnlace(predio.getEnlace());
        //registro.setPredio(predio.getPredio());
        ModelAndView maw = this.inicio();

        if (!archivo.isEmpty()) {
            String tipo = archivo.getContentType();
            String extension = "." + tipo.substring(tipo.indexOf('/') + 1, tipo.length());
            String foto = predio.getId() + extension;
            String ruta = Paths.get("scr/main/resources/static/images/predio", foto).toAbsolutePath().toString();

            try {
                archivo.transferTo(new File(ruta));
            } catch (Exception error) {
                maw.addObject("error", "No se pudo guardar el archivo.");
                return maw;
            }

            registro.setFoto(foto);
        }

        predioServicio.guardar(predio);
        maw.addObject("exito", "Predio editado correctamente.");
        return maw;
    }

    @DeleteMapping("/{id}")
    private ModelAndView borrar(@PathVariable("id") Long id) {
        predioServicio.borrar(id);
        ModelAndView maw = this.inicio();
        maw.addObject("exito", "Predio agregado correctamente.");
        return maw;
    }
}
