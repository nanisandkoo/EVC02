package com.martel.appec02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.martel.appec02.DAO.PRODUCTOS_DAO;
import com.martel.appec02.Entidad.Productos;

import java.util.ArrayList;
import java.util.List;

public class NuevoProductoActivity extends AppCompatActivity {

    private EditText txtId;
    private EditText txtNombre;
    private Spinner spCategoria;
    private EditText txtStock;
    private EditText txtPrecio;
    private Button btnNuevo;
    private Button btnGuardar;
    private Button btnConsultar;

    private PRODUCTOS_DAO productosDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);

        productosDao = new PRODUCTOS_DAO();

        txtId = findViewById(R.id.txtId);
        txtNombre = findViewById(R.id.txtNombre);
        spCategoria = findViewById(R.id.spCategoria);
        txtStock = findViewById(R.id.txtStock);
        txtPrecio = findViewById(R.id.txtPrecio);
        btnNuevo = findViewById(R.id.btnNuevo);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnConsultar = findViewById(R.id.btnConsultar);

        List<String> categoriasList = new ArrayList<>();
        categoriasList.add("Computo");
        categoriasList.add("Linea Blanca");
        categoriasList.add("Abarrotes");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoriasList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(spinnerAdapter);

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarCampos();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarProducto();
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarConsultarProductosActivity();
            }
        });
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        spCategoria.setSelection(0);
        txtStock.setText("");
        txtPrecio.setText("");
    }

    private void guardarProducto() {
        int id = Integer.parseInt(txtId.getText().toString());
        String nombre = txtNombre.getText().toString();
        String categoria = spCategoria.getSelectedItem().toString();
        int stock = Integer.parseInt(txtStock.getText().toString());
        double precio = Double.parseDouble(txtPrecio.getText().toString());

        Productos producto = new Productos(id, nombre, categoria, stock, precio);

        String resultado = productosDao.GrabarProducto(producto);

        Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
    }

    private void cargarConsultarProductosActivity() {
        Intent intent = new Intent(NuevoProductoActivity.this, ConsultarProductosActivity.class);
        startActivity(intent);
    }
}