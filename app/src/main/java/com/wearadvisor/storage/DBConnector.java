package com.wearadvisor.storage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
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
     *
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

    @Nullable
    public String getUid() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return null;
    }

    public void removeUserIdListener(final Callback<String> uidCallback) {
        FirebaseAuth.AuthStateListener fireListener = authListeners.remove(uidCallback);
        firebaseAuth.removeAuthStateListener(fireListener);
    }

    public void setUserName(@NonNull String uid, @NonNull String name) {
        database
                .getReference()
                .child("users")
                .child(uid)
                .setValue(name)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.getCause();
                    }
                });
    }

    public Cancellable getUsername(@NonNull String uid, final Callback<String> callback) {
        DatabaseReference ref = database
                .getReference()
                .child("users")
                .child(uid);

        return observeReference(ref, new Callback<DataSnapshot>() {
            @Override
            public void on(DataSnapshot dataSnapshot) {
                callback.on(dataSnapshot.getValue(String.class));
            }
        });
    }

    public Cancellable observeReference(final DatabaseReference reference, final Callback<DataSnapshot> callback) {

        final ValueEventListener listener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.on(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return new Cancellable() {
            @Override
            public void cancel() {
                reference.removeEventListener(listener);
            }
        };
    }

    public interface Cancellable {
        void cancel();
    }

    public static class EmptyCancellable implements Cancellable {
        @Override
        public void cancel() {
        }
    }
}
