package com.techlab.model;

import com.techlab.utils.Validaciones;

/**
 * Clase que representa un artículo electrónico.
 * Extiende Articulo e implementa lógica de cálculo de precio específica.
 */
public class ArticuloElectronico extends Articulo {
    private int garantiaMeses;
    private static final double PORCENTAJE_RECARGO = 0.15;
    private static final int MESES_MINIMOS = 12;
    
    public ArticuloElectronico(int codigo, String nombre, double precio, Categoria categoria, int garantiaMeses) {
        super(codigo, nombre, precio, categoria);
        Validaciones.validarNoNegativo(garantiaMeses, "Garantía en meses");
        this.garantiaMeses = garantiaMeses;
    }
    
    public int getGarantiaMeses() {
        return garantiaMeses;
    }
    
    public void setGarantiaMeses(int garantiaMeses) {
        Validaciones.validarNoNegativo(garantiaMeses, "Garantía en meses");
        this.garantiaMeses = garantiaMeses;
    }
    
    @Override
    public String getTipoArticulo() {
        return "Electrónico";
    }
    
    /**
     * Calcula el precio final con recargo si la garantía supera 12 meses.
     * Si garantía > 12 meses: aplica 15% de recargo
     * Si garantía <= 12 meses: mantiene el precio base
     */
    @Override
    public double calcularPrecioFinal() {
        if (garantiaMeses > MESES_MINIMOS) {
            return precio + (precio * PORCENTAJE_RECARGO);
        }
        return precio;
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format(" | Garantía: %d meses | Precio final: $%.2f", 
                garantiaMeses, calcularPrecioFinal());
    }
}

