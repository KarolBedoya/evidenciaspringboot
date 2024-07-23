package com.senaadso.crudmenu.springbootsenaadso.models;

import jakarta.validation.constraints.*;


public class ProductoDto {
    @NotEmpty(message ="El nombre es obligatorio")
    //Asegura que el campo no sea nulo ni vacío

    private String nombre;

    @NotEmpty(message ="El nombre de la categoría es obligatorio")
    private String categoria;

    @Min(0)
    //Asegura que el valor del campo sea mayor o igual a un valor mínimo especificado.
    private double precio;

    @Size(max = 3000, message = "La descripcion no debe exceder los 3000 caracteres")
    private String descripcion;

    public @NotEmpty(message = "El nombre es obligatorio") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotEmpty(message = "El nombre es obligatorio") String nombre) {
        this.nombre = nombre;
    }

    public @NotEmpty(message = "El nombre de la categoría es obligatorio") String getCategoria() {
        return categoria;
    }

    public void setCategoria(@NotEmpty(message = "El nombre de la categoría es obligatorio") String categoria) {
        this.categoria = categoria;
    }

    @Min(0)
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(@Min(0) double precio) {
        this.precio = precio;
    }

    public @Size(max = 3000, message = "La descripcion no debe exceder los 3000 caracteres") String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@Size(max = 3000, message = "La descripcion no debe exceder los 3000 caracteres") String descripcion) {
        this.descripcion = descripcion;
    }
}
