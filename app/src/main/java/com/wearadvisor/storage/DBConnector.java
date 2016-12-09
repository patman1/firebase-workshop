package com.wearadvisor.storage;

import com.google.firebase.database.FirebaseDatabase;

public class DBConnector {

    private FirebaseDatabase database;

    private void initialize() {
        database
                .setPersistenceEnabled(true);
    }
}
