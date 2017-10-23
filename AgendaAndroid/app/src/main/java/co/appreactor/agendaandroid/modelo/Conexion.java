package co.appreactor.agendaandroid.modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lord_nightmare on 17/10/17.
 */

public class Conexion extends SQLiteOpenHelper {

    // Nombre oficial de la base de datos
    private static final String NOMBRE_BASE_DATOS = "agenda";

    // Numero de la version de SQLite que va a ser usada
    private static final int VERSION = 1;

    public Conexion(Context context) {
        super(context, NOMBRE_BASE_DATOS, null, VERSION);
    }

    /**
     * Metodo que define la creacion de las DDL's de la base de datos
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE persona (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "correo TEXT NOT NULL," +
                "telefono TEXT NOT NULL," +
                "edad TEXT NOT NULL," +
                "direcion TEXT NOT NULL )";
        // Cargar ddl a la base de datos
        db.execSQL(sql);
    }

    /**
     * Metodo que se utiliza para actualizar o modificar la version de sqlite que la aplicación
     * esta usando
     *
     * @param db instancia de la base de datos
     * @param oldVersion representa la version actual de la base datos
     * @param newVersion representa la nueva version de la base datos
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drops de tablas
        // alters de tablas
        // Salvar informacion -> Siempre de primeras
        String sql = "DROP TABLE persona"; //no es la manera mas recomendada (Generica)
        db.execSQL(sql);
        db.setVersion(newVersion);
        onCreate(db);
        // tareas de recovery
    }

    protected SQLiteDatabase abrir(){
        // retornar base datos solo lectura
        // return getReadableDatabase();
        // retornar base de datos lectura y escritura
        return getWritableDatabase();
    }

    protected void cerrar(SQLiteDatabase db){
        if (db != null){
            db.close();
        }
    }
}
