package co.appreactor.controlesdinamicos;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout txtGenerar;
    private Button btnGenerar;
    private LinearLayout lnContenedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.lnContenedor = findViewById(R.id.lnContenedor);
        this.btnGenerar = findViewById(R.id.btnGenerar);
        this.txtGenerar = findViewById(R.id.txtGenerar);

        btnGenerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int veces = Integer.parseInt(txtGenerar.getEditText().getText().toString());
                for (int i = 0; i < veces; i++) {
                    EditText celda = new EditText(MainActivity.this);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    celda.setLayoutParams(params);
                    lnContenedor.addView(celda);
                }
            }
        });
    }
}
