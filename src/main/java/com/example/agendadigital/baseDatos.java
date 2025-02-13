package com.example.agendadigital;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class baseDatos extends SQLiteOpenHelper {

    SQLiteDatabase db;
    private static final String DB_NAME = "dbd.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "datos";
    private static final String ID_COL = "id";
    private static final String NOMBRE_COL = "nombre";
    private static final String APELLIDOS_COL = "apellidos";
    private static final String DESC_COL = "descripcion";
    private static final String MAIL_COL = "mail";
    private static final String TLF_COL = "telefono";
    private static final String DIR_COL = "direccion";
    private static final String ACTIVO_COL = "activo";
    public baseDatos(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NOMBRE_COL + " TEXT, "
                + APELLIDOS_COL + " TEXT, "
                + DESC_COL + " TEXT, "
                + MAIL_COL + " TEXT, "
                + TLF_COL + " TEXT, "
                + DIR_COL + " TEXT, "
                + ACTIVO_COL + " INTEGER "
                + ");" ;
        db.execSQL(query);
    }
    public void AgregarDatos(String nombre, String apellidos, String descripcion, String mail, String telefono, String direccion, boolean activo) {

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOMBRE_COL, nombre);
        values.put(APELLIDOS_COL, apellidos);
        values.put(DESC_COL, descripcion);
        values.put(MAIL_COL, mail);
        values.put(TLF_COL, telefono);
        values.put(DIR_COL, direccion);
        if(activo==true)values.put(ACTIVO_COL, 1);
        else values.put(ACTIVO_COL, 0);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public void EditarDatos(int id, String nombre, String apellidos, String descripcion, String mail, String telefono, String direccion, boolean activo) {

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_COL, id);
        values.put(NOMBRE_COL, nombre);
        values.put(APELLIDOS_COL, apellidos);
        values.put(DESC_COL, descripcion);
        values.put(MAIL_COL, mail);
        values.put(TLF_COL, telefono);
        values.put(DIR_COL, direccion);
        if(activo==true)values.put(ACTIVO_COL, 1);
        else values.put(ACTIVO_COL, 0);
        db.update(TABLE_NAME, values, "id=?", new String[]{(String.valueOf(id))});
        db.close();
    }
    public void BorrarDatos(int id) {

        db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id=?", new String[]{(String.valueOf(id))});
        db.close();
    }
    public ArrayList<Contacto> PedirLista() {
        ArrayList<Contacto> lst = new ArrayList<Contacto>();
        db = this.getWritableDatabase();
        String peticion = "SELECT * FROM datos";
        Cursor c = db.rawQuery(peticion, null);
        while(c.moveToNext()){
            lst.add(new Contacto(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getInt(7)));
        }
        c.close();
        db.close();
        return lst;
    }
    public Contacto BuscarporID(int id) {

        db = this.getWritableDatabase();
        String peticion = "SELECT * FROM datos WHERE id = '"+ id +"';";
        Cursor c = db.rawQuery(peticion, null);
        c.moveToFirst();
        Contacto cnt = new Contacto(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getInt(7));
        c.close();
        db.close();
        return cnt;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}