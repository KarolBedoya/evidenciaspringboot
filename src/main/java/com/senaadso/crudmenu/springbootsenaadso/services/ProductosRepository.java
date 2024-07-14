package com.senaadso.crudmenu.springbootsenaadso.services;

import org.springframework.data.jpa.repository.JpaRepository;
import com.senaadso.crudmenu.springbootsenaadso.models.Producto;

public interface ProductosRepository extends JpaRepository<Producto, Integer> {
    //es para leer y escribir los productos desde la base de datos
}
