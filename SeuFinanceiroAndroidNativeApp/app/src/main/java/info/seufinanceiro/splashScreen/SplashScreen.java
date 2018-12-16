package info.seufinanceiro.splashScreen;

import android.os.Bundle;

import info.seufinanceiro.R;
import info.seufinanceiro.main.MainActivity;
import info.seufinanceiro.validateToken.ValidateTokenActivity;

public class SplashScreen extends ValidateTokenActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        validateToken(MainActivity.class);
    }

}
