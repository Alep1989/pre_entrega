package com.techlab.model;

import com.techlab.utils.Validaciones;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Clase que representa un artículo alimenticio.
 * Extiende Articulo e implementa lógica de cálculo de precio con descuento según vencimiento.
 */
public class ArticuloAlimenticio extends Articulo {
    private LocalDate fechaVencimiento;
    
    public ArticuloAlimenticio(int codigo, String nombre, double precio, Categoria categoria, String fechaVencimiento) {
        super(codigo, nombre, precio, categoria);
        Validaciones.validarFecha(fechaVencimiento);
        this.fechaVencimiento = LocalDate.parse(fechaVencimiento);
    }
    
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }
    
    public void setFechaVencimiento(String fechaVencimiento) {
        Validaciones.validarFecha(fechaVencimiento);
        this.fechaVencimiento = LocalDate.parse(fechaVencimiento);
    }
    
    @Override
    public String getTipoArticulo() {
        return "Alimenticio";
    }
    
    /**
     * Calcula el precio final aplicando descuento según días para vencimiento.
     * - Si falta 30+ días: sin descuento
     * - Si falta 15-29 días: 5% de descuento
     * - Si falta 1-14 días: 15% de descuento
     * - Si ya venció: 50% de descuento (liquidación)
     */
    @Override
    public double calcularPrecioFinal() {
        LocalDate hoy = LocalDate.now();
        long diasFaltantes = ChronoUnit.DAYS.between(hoy, fechaVencimiento);
        
        if (diasFaltantes < 0) {
            // Vencido: 50% de descuento
            return precio * 0.5;
        } else if (diasFaltantes <= 14) {
            // 1-14 días: 15% de descuento
            return precio * 0.85;
        } else if (diasFaltantes <= 29) {
            // 15-29 días: 5% de descuento
            return precio * 0.95;
        }
        // 30+ días: sin descuento
        return precio;
    }
    
    @Override
    public String toString() {
        long diasFaltantes = ChronoUnit.DAYS.between(LocalDate.now(), fechaVencimiento);
        String estado = diasFaltantes < 0 ? "VENCIDO" : "Vence en " + diasFaltantes + " días";
        return super.toString() + String.format(" | Vencimiento: %s (%s) | Precio final: $%.2f", 
                fechaVencimiento, estado, calcularPrecioFinal());
    }
}

