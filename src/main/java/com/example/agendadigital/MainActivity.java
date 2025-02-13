package com.example.agendadigital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SimpleCustomAdapter.ItemClickListener{

    public static final String TAG = MainActivity.class.getSimpleName();
    private int editando=-1;
    private boolean buscando=false;
    private RecyclerView rcView;
    private baseDatos dbHandler;

    private EditText form_Nombre, form_Apellidos, form_Descripcion, form_Mail, form_Telefono, form_Direccion;
    private Switch form_activo;
    private TextView txt_nombre, txt_apellidos, txt_desc, txt_mail,txt_tlf, txt_dir, txt_activo;
    private Button btn_guardar, btn_atras_CE, btn_borrar;
    private ArrayList<Contacto> array = new ArrayList<Contacto>();
    private FloatingActionButton btn_crear;
    private androidx.appcompat.widget.Toolbar toolbar;
    private SearchView buscador;
    private RecyclerView lista;
    ArrayList<Contacto> contactos = new ArrayList<>();
    ArrayList<Contacto> listafiltrada = new ArrayList<>();;
    SimpleCustomAdapter adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CargarMain();
    }

    public void CargarContactos(){
        contactos = dbHandler.PedirLista();
    }
    public void CargarMain(){
        setContentView(R.layout.activity_main);

        dbHandler = new baseDatos(MainActivity.this);

        btn_crear = findViewById(R.id.floatingActionButton);
        toolbar = findViewById(R.id.toolbar);
        buscador = findViewById(R.id.searchView);
        rcView = findViewById(R.id.rcView);

        btn_crear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editando=-1;
                CargarForm();
            }
        });

        CargarContactos();
        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String busqueda) {
                if(busqueda!=""){ buscando=true;}
                else buscando=false;
                filtros(busqueda);
                return false;
            }
        });

        adaptador = new SimpleCustomAdapter(contactos);
        rcView.setAdapter(adaptador);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        adaptador.setOnClickListener(this);
    }
    public void filtros(String busqueda){
        listafiltrada = new ArrayList<Contacto>();
        for (Contacto item : contactos) {
            if (item.getNombre().toLowerCase().contains(busqueda.toLowerCase())) {
                listafiltrada.add(item);
            }
        }
        if (listafiltrada.isEmpty()) {
            Toast.makeText(this, "No hay contactos con ese nombre.", Toast.LENGTH_SHORT).show();
        }
        adaptador = new SimpleCustomAdapter(listafiltrada);
        rcView.setAdapter(adaptador);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        adaptador.setOnClickListener(this);
    }
    public void CargarForm(){
        setContentView(R.layout.crear_editar);
        //Formulario
        btn_atras_CE = findViewById(R.id.btn_atras_CE);
        form_Nombre  = findViewById(R.id.form_Nombre);
        form_Apellidos  = findViewById(R.id.Form_Apellidos);
        form_Descripcion  = findViewById(R.id.Form_Descripcion);
        form_Mail  = findViewById(R.id.Form_Mail);
        form_Telefono  = findViewById(R.id.Form_Telefono);
        form_Direccion  = findViewById(R.id.Form_Direccion);
        btn_borrar = findViewById(R.id.btn_borrar);
        btn_guardar = findViewById(R.id.btn_guardar);
        form_activo = findViewById(R.id.bool_activo);

        if(editando!=-1){
            Contacto cnt = dbHandler.BuscarporID(editando);
            form_Nombre.setText(cnt.nombre);
            form_Apellidos.setText(cnt.apellidos);
            form_Descripcion.setText(cnt.descripcion);
            form_Mail.setText(cnt.mail);
            form_Telefono.setText(cnt.telefono);
            form_Direccion.setText(cnt.direccion);
            boolean activo;
            if(cnt.activo==true) form_activo.setChecked(true);
            else form_activo.setChecked(false);
        }
        else{
            form_Nombre.setText("Nombre");
            form_Apellidos.setText("Apellidos");
            form_Descripcion.setText("Descripcion");
            form_Mail.setText("Mail");
            form_Telefono.setText("Telefono");
            form_Direccion.setText("Direccion");
            form_activo.setChecked(false);
        }

        btn_atras_CE.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editando=-1;
                CargarMain();
            }
        });
        btn_guardar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(editando==-1){
                    dbHandler.AgregarDatos(form_Nombre.getText().toString(), form_Apellidos.getText().toString(), form_Descripcion.getText().toString(), form_Mail.getText().toString(), form_Telefono.getText().toString(), form_Direccion.getText().toString(), form_activo.isChecked());
                }
                else{
                    dbHandler.EditarDatos(editando, form_Nombre.getText().toString(), form_Apellidos.getText().toString(), form_Descripcion.getText().toString(), form_Mail.getText().toString(), form_Telefono.getText().toString(), form_Direccion.getText().toString(), form_activo.isChecked());
                }
                CargarMain();
            }
        });
        btn_borrar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(editando!=-1){
                    dbHandler.BorrarDatos(editando);
                }
                CargarMain();
            }
        });
    }
    public void CargarDet(int id){
        setContentView(R.layout.detalles);
        txt_nombre = findViewById(R.id.txt_nombre);
        txt_apellidos = findViewById(R.id.txt_apellidos);
        txt_desc = findViewById(R.id.txt_desc);
        txt_mail = findViewById(R.id.txt_mail);
        txt_tlf = findViewById(R.id.txt_tlf);
        txt_dir = findViewById(R.id.txt_dir);
        txt_activo = findViewById(R.id.txt_activo);
        if(id!=-1){
            Contacto cnt = dbHandler.BuscarporID(id);
            txt_nombre.setText(cnt.nombre);
            txt_apellidos.setText(cnt.apellidos);
            txt_desc.setText(cnt.descripcion);
            txt_mail.setText(cnt.mail);
            txt_tlf.setText(cnt.telefono);
            txt_dir.setText(cnt.direccion);
            if(cnt.activo) txt_activo.setText("Activo");
            else txt_activo.setText("Inactivo");

        }
        else{
            txt_nombre.setText("");
            txt_apellidos.setText("");
            txt_desc.setText("");
            txt_mail.setText("");
            txt_tlf.setText("");
            txt_dir.setText("");
            txt_activo.setText("");
        }

        Button btn_atras_D, btn_editar;

        btn_atras_D = findViewById(R.id.btn_atras_D);

        btn_atras_D.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CargarMain();
            }
        });
        btn_editar = findViewById(R.id.btn_editar);
        btn_editar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editando=id;
                CargarForm();
            }
        });
    }
    @Override
    public void onClick(View view, int position) {
        if(buscando) CargarDet(listafiltrada.get(position).getID());
        else CargarDet(contactos.get(position).getID());
        buscando=false;
    }

}