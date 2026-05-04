package com.techlab.model;

import com.techlab.interfaces.Identificable;
import com.techlab.utils.Validaciones;

/**
 * Clase que representa una categoría de artículos.
 * Una categoría agrupa artículos con características similares.
 */
public class Categoria implements Identificable {
    private int codigo;
    private String nombre;
    
    public Categoria(int codigo, String nombre) {
        Validaciones.validarNoVacio(nombre, "Nombre de categoría");
        this.codigo = codigo;
        this.nombre = nombre;
    }
    
    @Override
    public int getCodigo() {
        return codigo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        Validaciones.validarNoVacio(nombre, "Nombre de categoría");
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return String.format("[%d] %s", codigo, nombre);
    }
}

