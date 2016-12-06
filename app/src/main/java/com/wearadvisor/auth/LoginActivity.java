package com.wearadvisor.auth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.wearadvisor.MainActivity;
import com.wearadvisor.R;
import com.wearadvisor.db.Connector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener {

    @BindView(R.id.name)
    EditText username;

    @BindView(R.id.pass)
    EditText pass;

    @BindView(R.id.error_message)
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Connector.INSTANCE.authStatus(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Connector.INSTANCE.stopAuthStatus(this);
    }

    @OnClick(R.id.sign_in)
    void signIn() {
        Connector.INSTANCE
                .signIn(username.getText().toString(), pass.getText().toString());
    }

    @OnClick(R.id.sign_up)
    void signUp() {
        Connector.INSTANCE
                .signUp(username.getText().toString(), pass.getText().toString());
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

}
