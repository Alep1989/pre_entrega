package com.techlab.repository;

import com.techlab.interfaces.Identificable;
import java.util.*;

public class Repositorio<T extends Identificable> {
    private List<T> elementos;
    
    public Repositorio() {
        this.elementos = new ArrayList<>();
    }
    
    public void agregar(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("El elemento no puede ser nulo.");
        }
        if (existe(elemento.getCodigo())) {
            throw new IllegalArgumentException("El elemento con código " + elemento.getCodigo() + " ya existe.");
        }
        elementos.add(elemento);
    }
    
    public T obtenerPorCodigo(int codigo) {
        return elementos.stream()
                .filter(e -> e.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }
    
    public void actualizar(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("El elemento no puede ser nulo.");
        }
        int indice = elementos.indexOf(obtenerPorCodigo(elemento.getCodigo()));
        if (indice == -1) {
            throw new IllegalArgumentException("El elemento no existe.");
        }
        elementos.set(indice, elemento);
    }
    
    public boolean eliminar(int codigo) {
        T elemento = obtenerPorCodigo(codigo);
        if (elemento != null) {
            elementos.remove(elemento);
            return true;
        }
        return false;
    }
    
    public boolean existe(int codigo) {
        return obtenerPorCodigo(codigo) != null;
    }
    
    public List<T> obtenerTodos() {
        return new ArrayList<>(elementos);
    }
    
    public int contar() {
        return elementos.size();
    }
}

