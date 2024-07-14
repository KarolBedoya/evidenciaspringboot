package com.senaadso.crudmenu.springbootsenaadso.controllers;


import com.senaadso.crudmenu.springbootsenaadso.models.Producto;
import com.senaadso.crudmenu.springbootsenaadso.models.ProductoDto;
import com.senaadso.crudmenu.springbootsenaadso.services.ProductosRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("productos")
public class ProductosController {

    @Autowired
    private ProductosRepository repo;

    @GetMapping({"","/"})
    public String mostrarListaProductos(Model model){
        List<Producto> productos =repo.findAll(Sort.by(Sort.Direction.DESC,"Id"));
        model.addAttribute("productos", productos);

        return "productos/index";
    }

    @GetMapping("/crear")
    public String showCreatePage(Model model){
        ProductoDto productoDto = new ProductoDto();
        model.addAttribute("productoDto", productoDto);

        return "productos/crearproducto";
    }

    @PostMapping("/crear")
    public String CrearProducto(
            @Valid @ModelAttribute ProductoDto productoDto, BindingResult resultado){
        if(resultado.hasErrors()){
            return "productos/CrearProducto";
        }
        Date fechaCreacion = new Date(System.currentTimeMillis());

        //Registro en base de datos del nuevo registro
        Producto prod = new Producto();
        prod.setNombre(productoDto.getNombre());
        prod.setCategoria(productoDto.getCategoria());
        prod.setDescripcion(productoDto.getDescripcion());
        prod.setPrecio(productoDto.getPrecio());
        prod.setFechaCreacion((java.sql.Date) fechaCreacion);

        repo.save(prod);

        return "redirect:/productos";
    }


    @GetMapping("/editar")
    public String showEditPage(Model model, @RequestParam int id){
        try{
            Producto prod = repo.findById(id).get();
            model.addAttribute("producto", prod);

            ProductoDto productoDto = new ProductoDto();
            productoDto.setNombre(prod.getNombre());
            productoDto.setCategoria(prod.getCategoria());
            productoDto.setDescripcion(prod.getDescripcion());
            productoDto.setPrecio(prod.getPrecio());

            model.addAttribute("productoDto", productoDto);

        }catch(Exception ex){
            System.out.println("Excepción al editar " + ex.getMessage());
            return "/productos";
        }

        return "/productos/EditarProducto";
    }

    @PostMapping("editar")
    public String actualizarProducto(Model model, @RequestParam int id, @Valid @ModelAttribute ProductoDto productoDto, BindingResult resultado){

        try {
            Producto producto = repo.findById(id).get();
            model.addAttribute("producto", producto);
            //Si hay errores
            if(resultado.hasErrors()){
                return "productos/EditarProductos";
            }
            Date fechaCreacion = new Date(System.currentTimeMillis());
            producto.setNombre(productoDto.getNombre());
            producto.setCategoria(productoDto.getCategoria());
            producto.setDescripcion(productoDto.getDescripcion());
            producto.setPrecio(productoDto.getPrecio());

            repo.save(producto);


        }catch (Exception ex){
            System.out.println("Excepcion al grabar la edición: " +ex.getMessage());
        }

        return "redirect:/productos";
    }

    @GetMapping("/eliminar")
    public String eliminarProducto(@RequestParam int id){
        try{
        Producto producto = repo.findById(id).get();
        repo.delete(producto);
        }catch(Exception ex){
            System.out.println("Excepción al eliminar");
        }

        return "redirect:/productos";
    }
}
