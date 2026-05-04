package com.techlab.utils;

/**
 * Clase utilitaria para generar códigos secuenciales automáticos.
 * Asegura que los códigos sean únicos y auto-incrementales.
 */
public class Secuencias {
    private static int contadorArticulos = 1000;
    private static int contadorCategorias = 100;
    
    public static int generarCodigoArticulo() {
        return ++contadorArticulos;
    }
    
    public static int generarCodigoCategoria() {
        return ++contadorCategorias;
    }
}

