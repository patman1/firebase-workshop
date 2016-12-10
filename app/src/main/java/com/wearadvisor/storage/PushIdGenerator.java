package com.wearadvisor.storage;

// TODO: change to Firebase compatible solution
public class PushIdGenerator {

    public String generate() {
        return "" + System.currentTimeMillis();
    }
}
