package pro.sunriseforest.sunriseforestapp_client.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import pro.sunriseforest.sunriseforestapp_client.R;

public class RegistrationActivity extends AppCompatActivity {

    private Button mLogInButton;

    private View.OnClickListener mOnClickListenerLogInButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showToast("добро пожаловать в приложулю");
            // startActivity
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        mLogInButton = findViewById(R.id.log_in_button);
        mLogInButton.setOnClickListener(mOnClickListenerLogInButton);
    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
