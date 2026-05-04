package com.techlab.menu;

import com.techlab.model.Articulo;
import com.techlab.model.Categoria;
import com.techlab.repository.Repositorio;
import java.util.Scanner;

/**
 * Menú principal de la aplicación.
 * Responsable única: gestionar la navegación entre menús secundarios.
 */
public class Menu {
    private MenuCategorias menuCategorias;
    private MenuArticulos menuArticulos;
    private Scanner scanner;
    
    public Menu(Repositorio<Categoria> repositorioCategorias, Repositorio<Articulo> repositorioArticulos, Scanner scanner) {
        this.scanner = scanner;
        this.menuCategorias = new MenuCategorias(repositorioCategorias, repositorioArticulos, scanner);
        this.menuArticulos = new MenuArticulos(repositorioArticulos, repositorioCategorias, scanner);
    }
    
    public void mostrarMenuPrincipal() {
        boolean activo = true;
        
        System.out.println("\n======== BIENVENIDO AL SISTEMA DE GESTION ========");
        System.out.println("========    DE ARTICULOS Y CATEGORIAS     ========");
        
        while (activo) {
            System.out.println("\n========== MENÚ PRINCIPAL ==========");
            System.out.println("1. Gestionar Categorías");
            System.out.println("2. Gestionar Artículos");
            System.out.println("3. Salir");
            System.out.print("Seleccione opción: ");
            
            String opcion = scanner.nextLine().trim();
            
            switch (opcion) {
                case "1":
                    menuCategorias.mostrarMenu();
                    break;
                case "2":
                    menuArticulos.mostrarMenu();
                    break;
                case "3":
                    System.out.println("\n¡Hasta luego!");
                    activo = false;
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
}

