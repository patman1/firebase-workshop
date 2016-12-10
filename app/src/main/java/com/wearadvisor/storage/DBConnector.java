package com.wearadvisor.storage;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wearadvisor.WearAdvisor;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public enum DBConnector {

    INSTANCE(WearAdvisor.instance());

    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;

    private Map<Callback<String>, FirebaseAuth.AuthStateListener> authListeners = new HashMap<>();

    DBConnector(Context context) {
        FirebaseApp firebaseApp = FirebaseApp.initializeApp(context);

        database = FirebaseDatabase.getInstance(firebaseApp);
        firebaseAuth = FirebaseAuth.getInstance(firebaseApp);

        database
                .setPersistenceEnabled(true);
    }



    public void signUp(String name,
                       String pass,
                       OnFailureListener onFailureListener) {
        firebaseAuth
                .createUserWithEmailAndPassword(name, pass)
                .addOnFailureListener(onFailureListener);
    }

    public void signIn(String name, String pass, OnFailureListener onFailureListener) {
        AuthCredential credentials = EmailAuthProvider.getCredential(name, pass);
        firebaseAuth
                .signInWithCredential(credentials)
                .addOnFailureListener(onFailureListener);
    }

    /**
     * null delivered means user not logged in.
     * @param uidCallback
     */
    public void addUserIdListener(final Callback<String> uidCallback) {

        FirebaseAuth.AuthStateListener fireListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    uidCallback.on(user.getUid());
                } else {
                    uidCallback.on(null);
                }
            }
        };
        firebaseAuth.addAuthStateListener(fireListener);
    }

    public void removeUserIdListener(final Callback<String> uidCallback) {
        FirebaseAuth.AuthStateListener fireListener = authListeners.remove(uidCallback);
        firebaseAuth.removeAuthStateListener(fireListener);
    }
}
