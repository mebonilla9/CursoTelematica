package co.appreactor.eventos.negocio.fragmentos;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import co.appreactor.eventos.persistencia.dto.FechaDTO;

/**
 * Created by lord_nightmare on 10/10/17.
 */
@SuppressLint("ValidFragment")
public class DialogoFecha extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    /**
     * Parametro que hace de referencia al campo de texto que va a contener la fecha seleccionada
     * en el dialogo
     */
    private EditText textoFecha;
    private FechaDTO fecha;

    /**
     * Constructor que parametriza por referencia el campo de texto que obtiene la fecha seleccionada
     *
     * @param textoFecha EditText que recibe una fecha seleccionada.
     */
    public DialogoFecha(EditText textoFecha, FechaDTO fecha) {
        this.textoFecha = textoFecha;
        this.fecha = fecha;
    }

    /**
     * Permite la creacion de un objeto de la Clase DatePickerDialog con la fecha del sistema
     * seleccionada por defecto.
     *
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Obtener una instancia del calendario del sistema
        Calendar calendario = Calendar.getInstance();

        Toast.makeText(getActivity(), "Unix Date: "+calendario.getTime().getTime(), Toast.LENGTH_LONG).show();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int diaMes = calendario.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, anio, mes, diaMes);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // visualizacion de fecha en la interfaz grafica, no se utiliza a nivel logico
        String fechaSeleccionada = dayOfMonth + "-" + (month+1) + "-" + year;
        textoFecha.setText(fechaSeleccionada);
        // Asignacion de fecha como long
        Calendar calendario = new GregorianCalendar(year,month,dayOfMonth);
        fecha.setFechaUnix(calendario.getTimeInMillis());
        fecha.setFechaTexto(fechaSeleccionada);
    }
}
