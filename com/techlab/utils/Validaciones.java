package com.techlab.utils;

/**
 * Clase utilitaria para validaciones comunes del sistema.
 * Centraliza toda la lógica de validación para evitar duplicación de código.
 */
public class Validaciones {
    
    public static void validarNoVacio(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " no puede estar vacío.");
        }
    }
    
    public static void validarPositivo(double valor, String nombreCampo) {
        if (valor <= 0) {
            throw new IllegalArgumentException(nombreCampo + " debe ser mayor a 0.");
        }
    }
    
    public static void validarNoNegativo(int valor, String nombreCampo) {
        if (valor < 0) {
            throw new IllegalArgumentException(nombreCampo + " no puede ser negativo.");
        }
    }
    
    public static void validarFecha(String fecha) {
        if (fecha == null || !fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Fecha debe estar en formato YYYY-MM-DD.");
        }
    }
}

