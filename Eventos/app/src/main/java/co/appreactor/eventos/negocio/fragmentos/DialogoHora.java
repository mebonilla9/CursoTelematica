package co.appreactor.eventos.negocio.fragmentos;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.widget.EditText;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by lord_nightmare on 10/10/17.
 */

@SuppressLint("ValidFragment")
public class DialogoHora extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private EditText textoHora;

    public DialogoHora(EditText textoHora) {
        this.textoHora = textoHora;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minutos = calendario.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hora, minutos, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String horaSeleccionada = ((hourOfDay < 10) ? "0" + hourOfDay : hourOfDay) +
                            ":" + ((minute < 10) ? "0" + minute : minute);
        textoHora.setText(horaSeleccionada);
    }
}
