package com.martel.appec02.DAO;

import com.martel.appec02.Entidad.Productos;
import java.util.ArrayList;
import java.util.List;

public class PRODUCTOS_DAO {
    private List<Productos> productosList;

    // Resto del código de la clase PRODUCTOS_DAO

    public List<Productos> listarProductos() {
        return productosList;
    }

    public PRODUCTOS_DAO() {
        productosList = new ArrayList<>();
    }

    public String GrabarProducto(Productos producto) {
        // Verificar si el código del producto ya existe en la colección
        for (Productos p : productosList) {
            if (p.getId_prod() == producto.getId_prod()) {
                return "El Producto: " + producto.getNom_prod() + " ya existe.";
            }
        }

        // Agregar el producto a la colección
        productosList.add(producto);

        return "El Producto: " + producto.getNom_prod() + " fue registrado correctamente.";
    }

    public List<String> ListarProductosCategoriaStock(String categoria, int stockMinimo) {
        List<String> productosEncontrados = new ArrayList<>();

        // Buscar productos que coincidan con la categoría y tengan stock mayor al valor enviado
        for (Productos producto : productosList) {
            if (producto.getCategoria().equalsIgnoreCase(categoria) && producto.getStock() > stockMinimo) {
                productosEncontrados.add(producto.getNom_prod());
            }
        }

        return productosEncontrados;
    }

    public String EliminarProducto(int codigoProducto) {
        // Buscar el producto por su código y eliminarlo de la colección
        for (Productos producto : productosList) {
            if (producto.getId_prod() == codigoProducto) {
                productosList.remove(producto);
                return "El Producto " + producto.getNom_prod() + " fue eliminado correctamente.";
            }
        }

        return "No se encontró ningún producto con el código especificado.";
    }
}

