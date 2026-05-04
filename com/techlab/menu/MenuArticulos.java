package com.techlab.menu;

import com.techlab.model.Articulo;
import com.techlab.model.ArticuloElectronico;
import com.techlab.model.ArticuloAlimenticio;
import com.techlab.model.Categoria;
import com.techlab.repository.Repositorio;
import com.techlab.utils.Secuencias;
import com.techlab.utils.Validaciones;
import java.util.List;
import java.util.Scanner;

/**
 * Menú para gestionar artículos (CRUD).
 * Responsable única: administrar operaciones CRUD de artículos.
 */
public class MenuArticulos {
    private Repositorio<Articulo> repositorioArticulos;
    private Repositorio<Categoria> repositorioCategorias;
    private Scanner scanner;
    
    public MenuArticulos(Repositorio<Articulo> repositorioArticulos, 
                        Repositorio<Categoria> repositorioCategorias, Scanner scanner) {
        this.repositorioArticulos = repositorioArticulos;
        this.repositorioCategorias = repositorioCategorias;
        this.scanner = scanner;
    }
    
    public void mostrarMenu() {
        boolean activo = true;
        while (activo) {
            System.out.println("\n========== MENÚ ARTÍCULOS ==========");
            System.out.println("1. Crear nuevo artículo");
            System.out.println("2. Listar artículos");
            System.out.println("3. Actualizar artículo");
            System.out.println("4. Eliminar artículo");
            System.out.println("5. Ver precio final con descuentos/recargos");
            System.out.println("6. Volver al menú principal");
            System.out.print("Seleccione opción: ");
            
            String opcion = scanner.nextLine().trim();
            
            switch (opcion) {
                case "1":
                    crearArticulo();
                    break;
                case "2":
                    listarArticulos();
                    break;
                case "3":
                    actualizarArticulo();
                    break;
                case "4":
                    eliminarArticulo();
                    break;
                case "5":
                    verPrecioFinal();
                    break;
                case "6":
                    activo = false;
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
    
    private void crearArticulo() {
        try {
            if (repositorioCategorias.contar() == 0) {
                System.out.println("\nError: Debe crear al menos una categoría antes de crear artículos.");
                return;
            }
            
            System.out.print("\nIngrese nombre del artículo: ");
            String nombre = scanner.nextLine().trim();
            Validaciones.validarNoVacio(nombre, "Nombre");
            
            System.out.print("Ingrese precio: $");
            double precio = Double.parseDouble(scanner.nextLine().trim());
            Validaciones.validarPositivo(precio, "Precio");
            
            System.out.println("\nCategorías disponibles:");
            listarCategoriasDisponibles();
            
            System.out.print("Seleccione código de categoría: ");
            int codigoCategoria = Integer.parseInt(scanner.nextLine().trim());
            
            Categoria categoria = repositorioCategorias.obtenerPorCodigo(codigoCategoria);
            if (categoria == null) {
                System.out.println("Categoría no encontrada.");
                return;
            }
            
            System.out.println("\nTipo de artículo:");
            System.out.println("1. Electrónico");
            System.out.println("2. Alimenticio");
            System.out.print("Seleccione tipo: ");
            
            String tipo = scanner.nextLine().trim();
            Articulo articulo;
            int codigo = Secuencias.generarCodigoArticulo();
            
            switch (tipo) {
                case "1":
                    System.out.print("Ingrese garantía en meses: ");
                    int garantia = Integer.parseInt(scanner.nextLine().trim());
                    Validaciones.validarNoNegativo(garantia, "Garantía");
                    articulo = new ArticuloElectronico(codigo, nombre, precio, categoria, garantia);
                    break;
                case "2":
                    System.out.print("Ingrese fecha de vencimiento (YYYY-MM-DD): ");
                    String fecha = scanner.nextLine().trim();
                    articulo = new ArticuloAlimenticio(codigo, nombre, precio, categoria, fecha);
                    break;
                default:
                    System.out.println("Tipo inválido.");
                    return;
            }
            
            repositorioArticulos.agregar(articulo);
            System.out.println("Artículo creado exitosamente con código: " + codigo);
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese valores válidos.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void listarCategoriasDisponibles() {
        List<Categoria> categorias = repositorioCategorias.obtenerTodos();
        for (Categoria cat : categorias) {
            System.out.println(cat);
        }
    }
    
    private void listarArticulos() {
        List<Articulo> articulos = repositorioArticulos.obtenerTodos();
        
        if (articulos.isEmpty()) {
            System.out.println("\nNo hay artículos registrados.");
            return;
        }
        
        System.out.println("\n========== ARTÍCULOS REGISTRADOS ==========");
        for (Articulo art : articulos) {
            System.out.println(art);
        }
    }
    
    private void actualizarArticulo() {
        listarArticulos();
        
        if (repositorioArticulos.obtenerTodos().isEmpty()) {
            return;
        }
        
        try {
            System.out.print("\nIngrese código del artículo a actualizar: ");
            int codigo = Integer.parseInt(scanner.nextLine().trim());
            
            Articulo articulo = repositorioArticulos.obtenerPorCodigo(codigo);
            if (articulo == null) {
                System.out.println("Artículo no encontrado.");
                return;
            }
            
            System.out.println("\nQué desea actualizar?");
            System.out.println("1. Nombre");
            System.out.println("2. Precio");
            System.out.println("3. Categoría");
            
            if (articulo instanceof ArticuloElectronico) {
                System.out.println("4. Garantía en meses");
            } else if (articulo instanceof ArticuloAlimenticio) {
                System.out.println("4. Fecha de vencimiento");
            }
            
            System.out.print("Seleccione opción: ");
            String opcion = scanner.nextLine().trim();
            
            switch (opcion) {
                case "1":
                    actualizarNombre(articulo);
                    break;
                case "2":
                    actualizarPrecio(articulo);
                    break;
                case "3":
                    actualizarCategoria(articulo);
                    break;
                case "4":
                    actualizarEspecifico(articulo);
                    break;
                default:
                    System.out.println("Opción inválida.");
                    return;
            }
            
            repositorioArticulos.actualizar(articulo);
            System.out.println("Artículo actualizado exitosamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un número válido.");
        }
    }
    
    private void actualizarNombre(Articulo articulo) {
        System.out.print("Ingrese nuevo nombre: ");
        String nombre = scanner.nextLine().trim();
        Validaciones.validarNoVacio(nombre, "Nombre");
        articulo.setNombre(nombre);
    }
    
    private void actualizarPrecio(Articulo articulo) {
        System.out.print("Ingrese nuevo precio: $");
        double precio = Double.parseDouble(scanner.nextLine().trim());
        Validaciones.validarPositivo(precio, "Precio");
        articulo.setPrecio(precio);
    }
    
    private void actualizarCategoria(Articulo articulo) {
        System.out.println("\nCategorías disponibles:");
        listarCategoriasDisponibles();
        
        System.out.print("Seleccione nuevo código de categoría: ");
        int codigoCategoria = Integer.parseInt(scanner.nextLine().trim());
        
        Categoria categoria = repositorioCategorias.obtenerPorCodigo(codigoCategoria);
        if (categoria == null) {
            System.out.println("Categoría no encontrada.");
            return;
        }
        
        articulo.setCategoria(categoria);
    }
    
    private void actualizarEspecifico(Articulo articulo) {
        if (articulo instanceof ArticuloElectronico) {
            ArticuloElectronico electronico = (ArticuloElectronico) articulo;
            System.out.print("Ingrese nueva garantía en meses: ");
            int garantia = Integer.parseInt(scanner.nextLine().trim());
            Validaciones.validarNoNegativo(garantia, "Garantía");
            electronico.setGarantiaMeses(garantia);
        } else if (articulo instanceof ArticuloAlimenticio) {
            ArticuloAlimenticio alimenticio = (ArticuloAlimenticio) articulo;
            System.out.print("Ingrese nueva fecha de vencimiento (YYYY-MM-DD): ");
            String fecha = scanner.nextLine().trim();
            alimenticio.setFechaVencimiento(fecha);
        }
    }
    
    private void eliminarArticulo() {
        listarArticulos();
        
        if (repositorioArticulos.obtenerTodos().isEmpty()) {
            return;
        }
        
        try {
            System.out.print("\nIngrese código del artículo a eliminar: ");
            int codigo = Integer.parseInt(scanner.nextLine().trim());
            
            if (repositorioArticulos.eliminar(codigo)) {
                System.out.println("Artículo eliminado exitosamente.");
            } else {
                System.out.println("Artículo no encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un número válido.");
        }
    }
    
    private void verPrecioFinal() {
        listarArticulos();
        
        if (repositorioArticulos.obtenerTodos().isEmpty()) {
            return;
        }
        
        try {
            System.out.print("\nIngrese código del artículo: ");
            int codigo = Integer.parseInt(scanner.nextLine().trim());
            
            Articulo articulo = repositorioArticulos.obtenerPorCodigo(codigo);
            if (articulo == null) {
                System.out.println("Artículo no encontrado.");
                return;
            }
            
            System.out.println("\n========== CÁLCULO DE PRECIO ==========");
            System.out.println("Artículo: " + articulo.getNombre());
            System.out.println("Precio base: $" + articulo.getPrecio());
            
            if (articulo instanceof ArticuloElectronico) {
                ArticuloElectronico electronico = (ArticuloElectronico) articulo;
                System.out.println("Garantía: " + electronico.getGarantiaMeses() + " meses");
                if (electronico.getGarantiaMeses() > 12) {
                    System.out.println("-> Recargo aplicado: 15% (garantía > 12 meses)");
                }
            } else if (articulo instanceof ArticuloAlimenticio) {
                ArticuloAlimenticio alimenticio = (ArticuloAlimenticio) articulo;
                System.out.println("Vencimiento: " + alimenticio.getFechaVencimiento());
                long diasFaltantes = java.time.temporal.ChronoUnit.DAYS
                        .between(java.time.LocalDate.now(), alimenticio.getFechaVencimiento());
                if (diasFaltantes < 0) {
                    System.out.println("-> Descuento aplicado: 50% (producto vencido)");
                } else if (diasFaltantes <= 14) {
                    System.out.println("-> Descuento aplicado: 15% (1-14 días para vencer)");
                } else if (diasFaltantes <= 29) {
                    System.out.println("-> Descuento aplicado: 5% (15-29 días para vencer)");
                } else {
                    System.out.println("-> Sin descuento (30+ días para vencer)");
                }
            }
            
            System.out.println("PRECIO FINAL: $" + String.format("%.2f", articulo.calcularPrecioFinal()));
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un número válido.");
        }
    }
}
