package com.martel.appec02;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.martel.appec02.DAO.PRODUCTOS_DAO;
import com.martel.appec02.Entidad.Productos;

import java.util.ArrayList;
import java.util.List;

public class ConsultarProductosActivity extends AppCompatActivity {

    private Spinner spinCategoria;
    private EditText textStock;
    private Button btnBuscar;
    private Button btnEliminar;
    private ListView listProductos;

    private List<Productos> productosList;
    private List<Productos> productosFiltradosList;
    private ArrayAdapter<Productos> productosAdapter;
    private PRODUCTOS_DAO productosDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_productos);

        productosDao = new PRODUCTOS_DAO();

        spinCategoria = findViewById(R.id.spinCategoria);
        textStock = findViewById(R.id.textStock);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnEliminar = findViewById(R.id.btnEliminar);
        listProductos = findViewById(R.id.listProductos);

        // Configurar el Spinner de Categorías
        List<String> categoriasList = new ArrayList<>();
        categoriasList.add("Computo");
        categoriasList.add("Linea Blanca");
        categoriasList.add("Abarrotes");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoriasList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCategoria.setAdapter(spinnerAdapter);


        // Obtener la lista de productos y configurar el ListView
        productosList = productosDao.listarProductos();
        productosFiltradosList = new ArrayList<>(productosList);
        productosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productosFiltradosList);
        listProductos.setAdapter(productosAdapter);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtrarProductos();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarProductoSeleccionado();
            }
        });

        listProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mostrarDialogoEliminarProducto(position);
            }
        });
    }

    private void filtrarProductos() {
        String categoriaSeleccionada = spinCategoria.getSelectedItem().toString();
        int stockMinimo = Integer.parseInt(textStock.getText().toString());

        productosFiltradosList.clear();

        for (Productos producto : productosList) {
            if (producto.getCategoria().equalsIgnoreCase(categoriaSeleccionada) && producto.getStock() > stockMinimo) {
                productosFiltradosList.add(producto);
            }
        }

        productosAdapter.notifyDataSetChanged();
    }

    private void eliminarProductoSeleccionado() {
        int posicionSeleccionada = listProductos.getCheckedItemPosition();

        if (posicionSeleccionada != ListView.INVALID_POSITION) {
            mostrarDialogoEliminarProducto(posicionSeleccionada);
        } else {
            Toast.makeText(this, "Seleccione un producto para eliminar", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarDialogoEliminarProducto(final int posicion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Producto");
        builder.setMessage("¿Está seguro de que desea eliminar este producto?");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarProducto(posicion);
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void eliminarProducto(int posicion) {
        Productos producto = productosFiltradosList.get(posicion);
        productosFiltradosList.remove(producto);
        productosList.remove(producto);
        productosAdapter.notifyDataSetChanged();
        Toast.makeText(this, "El producto " + producto.getNom_prod() + " fue eliminado correctamente", Toast.LENGTH_SHORT).show();
    }
}