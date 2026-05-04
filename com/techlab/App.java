package com.techlab;

import com.techlab.model.Articulo;
import com.techlab.model.Categoria;
import com.techlab.menu.Menu;
import com.techlab.repository.Repositorio;
import java.util.Scanner;

/**
 * Esta es la clase principal que arranca el programa.
 */
public class App {
    public static void main(String[] args) {
        Repositorio<Categoria> repositorioCategorias = new Repositorio<>();
        Repositorio<Articulo> repositorioArticulos = new Repositorio<>();
        Scanner scanner = new Scanner(System.in);
        
        try {
            Menu menu = new Menu(repositorioCategorias, repositorioArticulos, scanner);
            menu.mostrarMenuPrincipal();
        } finally {
            scanner.close();
        }
    }
}
