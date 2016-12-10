package com.wearadvisor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.wearadvisor.storage.Callback;
import com.wearadvisor.storage.DBConnector;
import com.wearadvisor.storage.DBConnector.Cancellable;
import com.wearadvisor.storage.DBConnector.EmptyCancellable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.user_name)
    TextView userName;

    Cancellable usernameCancellable = new EmptyCancellable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String uid = DBConnector.INSTANCE.getUid();
        if (uid != null) {
            usernameCancellable = DBConnector.INSTANCE
                    .getUsername(uid, new Callback<String>() {
                        @Override
                        public void on(String username) {
                            userName.setText(username);
                        }
                    });
        }
    }

    @Override
    protected void onPause() {
        usernameCancellable.cancel();
        super.onPause();
    }

    @OnClick(R.id.change_name)
    void updateUserName() {
        String uid = DBConnector.INSTANCE.getUid();

        if (uid != null) {
            String username = userName
                    .getText()
                    .toString();

            DBConnector.INSTANCE
                    .setUserName(uid,username);
        }
    }
}
