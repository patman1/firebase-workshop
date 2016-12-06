package com.wearadvisor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.wearadvisor.db.Connector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.question)
    EditText questionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ask)
    void ask() {
        String question = questionText.getText().toString();
        Connector.INSTANCE.ask(question);
    }

}
