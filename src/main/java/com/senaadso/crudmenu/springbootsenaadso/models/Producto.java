package com.senaadso.crudmenu.springbootsenaadso.models;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name="productos")
public class Producto {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String categoria;
    private String nombre;

    @Column(columnDefinition = "Text")
    private String descripcion;
    private double precio;
    private Date fechaCreacion;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
