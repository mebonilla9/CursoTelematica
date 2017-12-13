package co.appreactor.conocimentos.negocio.fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.appreactor.conocimentos.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RespuestaFragment extends Fragment {


    public RespuestaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_respuesta, container, false);
    }

}
