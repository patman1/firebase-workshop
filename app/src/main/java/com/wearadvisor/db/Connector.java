package com.wearadvisor.db;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.wearadvisor.WearAdvisor;

public enum Connector {
    INSTANCE;

    private final FirebaseAuth firebaseAuth;

    Connector() {
        FirebaseApp.initializeApp(WearAdvisor.instance());
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void authStatus(FirebaseAuth.AuthStateListener listener) {
        firebaseAuth.addAuthStateListener(listener);
    }

    public void stopAuthStatus(FirebaseAuth.AuthStateListener listener) {
        firebaseAuth.removeAuthStateListener(listener);
    }

    public void signUp(String name, String pass) {
        firebaseAuth
                .createUserWithEmailAndPassword(name, pass)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void signIn(String name, String pass) {
        AuthCredential credentials = EmailAuthProvider.getCredential(name, pass);
        firebaseAuth
                .signInWithCredential(credentials)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void ask(String question) {
        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("ask")
                .setValue(question);
    }
}
