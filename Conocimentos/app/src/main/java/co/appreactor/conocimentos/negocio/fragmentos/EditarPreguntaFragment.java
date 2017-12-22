package co.appreactor.conocimentos.negocio.fragmentos;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import co.appreactor.conocimentos.R;
import co.appreactor.conocimentos.persistencia.entidades.Pregunta;
import co.appreactor.conocimentos.persistencia.servicio.PreguntaTestService;


/**
 * A simple {@link Fragment} subclass.
 *
 * El fragmento de Editar Pregunta no trabajara como un fragmento clasico,
 * su herencia se basa en los fragmentos de Dialogo (Modal)
 */
// anotacion para convertir a nuestro fragmento personalizado en un fragmento valido
@SuppressLint("ValidFragment")
public class EditarPreguntaFragment extends DialogFragment {

    private TextInputLayout txtPregunta;
    private Button btnGuardarEditar;

    // referencia de pregunta que estamos editando
    private Pregunta preguntaEditar;

    // referencia del campo de texto que visualiza la pregunta en el fragmento actual
    private TextView lblValorPregunta;

    public EditarPreguntaFragment(Pregunta preguntaEditar, TextView lblValorPregunta) {
        this.preguntaEditar = preguntaEditar;
        this.lblValorPregunta = lblValorPregunta;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_editar_pregunta, container, false);
        this.btnGuardarEditar = vista.findViewById(R.id.btnGuardarEditar);
        this.txtPregunta = vista.findViewById(R.id.txtPregunta);

        // asignar el valor de la pregunta al campo de texto
        txtPregunta.getEditText().setText(preguntaEditar.getPregunta());
        asignarEventos();

        return vista;
    }

    private void asignarEventos() {
        btnGuardarEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // actualizar el valor de texto desde la ventana modal al fragmento
                preguntaEditar.setPregunta(txtPregunta.getEditText().getText().toString());
                lblValorPregunta.setText(preguntaEditar.getPregunta());

                // invocar al webservice para editar la pregunta
                new PreguntaTestService(getActivity()).actualizarPregunta(preguntaEditar);

                dismiss();
            }
        });
    }

}
