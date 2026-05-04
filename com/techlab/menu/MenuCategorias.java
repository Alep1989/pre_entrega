package com.techlab.menu;

import com.techlab.model.Categoria;
import com.techlab.repository.Repositorio;
import com.techlab.utils.Secuencias;
import com.techlab.utils.Validaciones;
import java.util.List;
import java.util.Scanner;

/**
 * Menú para gestionar categorías (CRUD).
 * Responsable única: administrar operaciones CRUD de categorías.
 */
public class MenuCategorias {
    private Repositorio<Categoria> repositorioCategorias;
    private Repositorio<?> repositorioArticulos;
    private Scanner scanner;
    
    public MenuCategorias(Repositorio<Categoria> repositorioCategorias, Repositorio<?> repositorioArticulos, Scanner scanner) {
        this.repositorioCategorias = repositorioCategorias;
        this.repositorioArticulos = repositorioArticulos;
        this.scanner = scanner;
    }
    
    public void mostrarMenu() {
        boolean activo = true;
        while (activo) {
            System.out.println("\n========== MENÚ CATEGORÍAS ==========");
            System.out.println("1. Crear nueva categoría");
            System.out.println("2. Listar categorías");
            System.out.println("3. Actualizar categoría");
            System.out.println("4. Eliminar categoría");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione opción: ");
            
            String opcion = scanner.nextLine().trim();
            
            switch (opcion) {
                case "1":
                    crearCategoria();
                    break;
                case "2":
                    listarCategorias();
                    break;
                case "3":
                    actualizarCategoria();
                    break;
                case "4":
                    eliminarCategoria();
                    break;
                case "5":
                    activo = false;
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
    
    private void crearCategoria() {
        try {
            System.out.print("\nIngrese nombre de la categoría: ");
            String nombre = scanner.nextLine().trim();
            
            Validaciones.validarNoVacio(nombre, "Nombre");
            
            int codigo = Secuencias.generarCodigoCategoria();
            Categoria categoria = new Categoria(codigo, nombre);
            
            repositorioCategorias.agregar(categoria);
            System.out.println("Categoría creada exitosamente con código: " + codigo);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void listarCategorias() {
        List<Categoria> categorias = repositorioCategorias.obtenerTodos();
        
        if (categorias.isEmpty()) {
            System.out.println("\nNo hay categorías registradas.");
            return;
        }
        
        System.out.println("\n========== CATEGORÍAS REGISTRADAS ==========");
        for (Categoria cat : categorias) {
            System.out.println(cat);
        }
    }
    
    private void actualizarCategoria() {
        listarCategorias();
        
        if (repositorioCategorias.obtenerTodos().isEmpty()) {
            return;
        }
        
        try {
            System.out.print("\nIngrese código de la categoría a actualizar: ");
            int codigo = Integer.parseInt(scanner.nextLine().trim());
            
            Categoria categoria = repositorioCategorias.obtenerPorCodigo(codigo);
            if (categoria == null) {
                System.out.println("Categoría no encontrada.");
                return;
            }
            
            System.out.print("Ingrese nuevo nombre: ");
            String nuevoNombre = scanner.nextLine().trim();
            
            Validaciones.validarNoVacio(nuevoNombre, "Nombre");
            
            categoria.setNombre(nuevoNombre);
            repositorioCategorias.actualizar(categoria);
            System.out.println("Categoría actualizada exitosamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un número válido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void eliminarCategoria() {
        listarCategorias();
        
        if (repositorioCategorias.obtenerTodos().isEmpty()) {
            return;
        }
        
        try {
            System.out.print("\nIngrese código de la categoría a eliminar: ");
            int codigo = Integer.parseInt(scanner.nextLine().trim());
            
            Categoria categoria = repositorioCategorias.obtenerPorCodigo(codigo);
            if (categoria == null) {
                System.out.println("Categoría no encontrada.");
                return;
            }
            
            if (repositorioArticulos.contar() > 0) {
                System.out.println("No se puede eliminar. Existen artículos en el sistema.");
                return;
            }
            
            repositorioCategorias.eliminar(codigo);
            System.out.println("Categoría eliminada exitosamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un número válido.");
        }
    }
}

