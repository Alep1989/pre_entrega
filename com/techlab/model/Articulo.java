package com.techlab.model;

import com.techlab.interfaces.Identificable;
import com.techlab.interfaces.Calculable;
import com.techlab.utils.Validaciones;

/**
 * Clase abstracta que representa un artículo base en el sistema.
 * Define la estructura común para todos los tipos de artículos.
 * Implementa tanto Identificable como Calculable.
 */
public abstract class Articulo implements Identificable, Calculable {
    protected int codigo;
    protected String nombre;
    protected double precio;
    protected Categoria categoria;
    
    public Articulo(int codigo, String nombre, double precio, Categoria categoria) {
        Validaciones.validarNoVacio(nombre, "Nombre de artículo");
        Validaciones.validarPositivo(precio, "Precio");
        
        if (categoria == null) {
            throw new IllegalArgumentException("La categoría no puede ser nula.");
        }
        
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }
    
    @Override
    public int getCodigo() {
        return codigo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        Validaciones.validarNoVacio(nombre, "Nombre de artículo");
        this.nombre = nombre;
    }
    
    public double getPrecio() {
        return precio;
    }
    
    public void setPrecio(double precio) {
        Validaciones.validarPositivo(precio, "Precio");
        this.precio = precio;
    }
    
    public Categoria getCategoria() {
        return categoria;
    }
    
    public void setCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("La categoría no puede ser nula.");
        }
        this.categoria = categoria;
    }
    
    public abstract String getTipoArticulo();
    
    @Override
    public String toString() {
        return String.format("[%d] %s - $%.2f - %s - Tipo: %s", 
                codigo, nombre, precio, categoria.getNombre(), getTipoArticulo());
    }
}
