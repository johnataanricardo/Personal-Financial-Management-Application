package info.seufinanceiro.main;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import info.seufinanceiro.R;

public class WebApp extends AppCompatActivity {

    private WebView mWebView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_app);

        mWebView = (WebView) findViewById(R.id.activity_main_web_view);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        mWebView.loadUrl("https://seufinanceiro.info");
        mWebView.setWebViewClient(new HelloWebViewClient());
    }

    private class HelloWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            webView.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(view.GONE);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK)&& mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                WebApp.this);

        alertDialogBuilder.setTitle("Sair");

        alertDialogBuilder
                .setMessage("Você quer sair")
                .setCancelable(false)
                .setPositiveButton("Sim",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        WebApp.this.finish();
                    }
                })
                .setNegativeButton("Não",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

}
