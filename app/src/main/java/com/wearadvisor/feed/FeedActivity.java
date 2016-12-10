package com.wearadvisor.feed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wearadvisor.ProfileActivity;
import com.wearadvisor.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedActivity extends AppCompatActivity {

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
}
