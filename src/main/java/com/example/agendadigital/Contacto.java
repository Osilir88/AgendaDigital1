package com.example.agendadigital;

public class Contacto {
    public int id;
    public String nombre;
    public String apellidos;
    public String descripcion;
    public String mail;
    public String telefono;
    public String direccion;
    public boolean activo;

    public Contacto(int id, String nombre, String apellidos, String descripcion, String mail, String telefono, String direccion, int activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.descripcion = descripcion;
        this.mail = mail;
        this.telefono = telefono;
        this.direccion = direccion;
        if(activo>=1)this.activo = true;
        else this.activo = false;
    }
    public String getNombre() {
        return nombre+", "+apellidos;
    }
    public int getID() {
        return id;
    }
}