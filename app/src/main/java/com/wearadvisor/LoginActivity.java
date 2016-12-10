package com.wearadvisor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.wearadvisor.storage.DBConnector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

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

    @OnClick(R.id.sign_in)
    void signIn() {
    }

    @OnClick(R.id.sign_up)
    void signUp() {
    }
}
