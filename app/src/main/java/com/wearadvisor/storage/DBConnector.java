package com.wearadvisor.storage;

import android.content.Context;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.wearadvisor.WearAdvisor;
import com.google.firebase.database.FirebaseDatabase;

public enum DBConnector {

    INSTANCE(WearAdvisor.instance());

    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;

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
}
