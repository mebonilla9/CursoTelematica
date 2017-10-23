package co.appreactor.agendaandroid.modelo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.appreactor.agendaandroid.modelo.Conexion;
import co.appreactor.agendaandroid.modelo.entidades.Persona;

/**
 * Created by lord_nightmare on 17/10/17.
 */

public class PersonaDao extends Conexion {

    public PersonaDao(Context context) {
        super(context);
    }

    public void insertar(Persona persona){
        SQLiteDatabase cnn = null;
        try{
            cnn = abrir();
            ContentValues valores = new ContentValues();
            valores.put("nombre",persona.getNombre());
            valores.put("correo",persona.getCorreo());
            valores.put("telefono",persona.getTelefono());
            valores.put("edad",persona.getEdad());
            valores.put("direccion",persona.getDireccion());
            // registrar y obtener el valor de la llave primaria del registro
            // insertado
            Long id = cnn.insert("persona",null,valores);
            persona.setId(id);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cerrar(cnn);
        }
    }

    public List<Persona> consultar(){
        SQLiteDatabase cnn = null;
        List<Persona> listaPersonas = new ArrayList<>();
        String[] columnas = new String[]{"id","nombre","correo","telefono","edad","direccion"};
        try{
            cnn = abrir();
            Cursor resultados = cnn.query("persona",columnas,null,null,null,null,"nombre");
            if (!resultados.moveToFirst()){
                return listaPersonas;
            }
            do{
                Persona personaGuardar = new Persona();
                personaGuardar.setId(resultados.getLong(resultados.getColumnIndex("id")));
                personaGuardar.setNombre(resultados.getString(resultados.getColumnIndex("nombre")));
                personaGuardar.setCorreo(resultados.getString(resultados.getColumnIndex("correo")));
                personaGuardar.setTelefono(resultados.getString(resultados.getColumnIndex("telefono")));
                personaGuardar.setEdad(resultados.getString(resultados.getColumnIndex("edad")));
                personaGuardar.setDireccion(resultados.getString(resultados.getColumnIndex("direccion")));
                listaPersonas.add(personaGuardar);
            } while (resultados.moveToNext());
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cerrar(cnn);
        }
        return listaPersonas;
    }
}
