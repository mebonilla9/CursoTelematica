package co.appreactor.webview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView wvNavegador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.wvNavegador = (WebView) findViewById(R.id.wvNavegador);
        this.wvNavegador.setWebViewClient(new WebViewClient());
        this.wvNavegador.loadUrl("https://android-arsenal.com");
        this.wvNavegador.getSettings().setJavaScriptEnabled(true);
    }
}
