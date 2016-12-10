package com.wearadvisor.feed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.wearadvisor.ProfileActivity;
import com.wearadvisor.R;
import com.wearadvisor.storage.DBConnector;
import com.wearadvisor.storage.PushIdGenerator;
import com.wearadvisor.storage.entities.Question;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedActivity extends AppCompatActivity {

    @BindView(R.id.question)
    TextView question;

    PushIdGenerator pushIdGenerator = new PushIdGenerator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.open_profile)
    void openProfile() {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    @OnClick(R.id.ask)
    void askQuestion() {
        String id = pushIdGenerator.generate();
        String text = question.getText().toString();
        String owner = DBConnector.INSTANCE.getUid();
        long timestamp = System.currentTimeMillis();
        Question question = new Question(id, text, owner, timestamp);
        DBConnector.INSTANCE
                .addQuestion(question);
    }
}
