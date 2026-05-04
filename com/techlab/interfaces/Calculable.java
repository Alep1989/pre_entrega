package com.techlab.interfaces;

/**
 * Interfaz para calcular el precio final de un artículo.
 * Permite que diferentes tipos de artículos implementen su propia lógica de cálculo.
 */
public interface Calculable {
    double calcularPrecioFinal();
}

