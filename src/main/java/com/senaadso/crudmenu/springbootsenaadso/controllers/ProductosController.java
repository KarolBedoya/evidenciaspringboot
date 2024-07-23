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
//Indica que la clase es un controlador de Spring MVC que manejará las solicitudes web.
@RequestMapping("productos")

//Todas las rutas definidas en este controlador estarán prefijadas con /productos
public class ProductosController {

    @Autowired
    //Permite la inyección de dependencias para el objeto ProductosRepository sin necesidad de crear una instancia manualmente.

    private ProductosRepository repo;
    //Permite el acceso a las operaciones de base de datos definidas en el repositorio ProductosRepository.

    @GetMapping({"","/"})
    //Maneja las solicitudes GET a la URL /productos
    //Recupera la lista de productos, la ordena por el campo Id en orden descendente, y la añade al modelo para ser mostrada en la vista productos/index
    public String mostrarListaProductos(Model model){
        List<Producto> productos =repo.findAll(Sort.by(Sort.Direction.DESC,"Id"));
        model.addAttribute("productos", productos);

        return "productos/index";
    }

    @GetMapping("/crear")
    //Muestra la página para crear un nuevo producto y añade un objeto ProductoDto vacío al modelo.
    public String showCreatePage(Model model){
        ProductoDto productoDto = new ProductoDto();
        model.addAttribute("productoDto", productoDto);

        return "productos/crearproducto";
    }

    @PostMapping("/crear")
    //Valida y procesa el formulario de creación de un nuevo producto. Si hay errores, se vuelve a la vista de creación; si no, se guarda el nuevo producto en la base de datos.
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
    //Muestra la página para editar un producto existente, recupera el producto por su id y lo añade al modelo junto con un ProductoDto
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
    //Valida y procesa el formulario de edición de un producto existente. Si hay errores, se vuelve a la vista de edición; si no, se actualiza el producto en la base de datos
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
    //Elimina un producto de la base de datos basado en su id
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
