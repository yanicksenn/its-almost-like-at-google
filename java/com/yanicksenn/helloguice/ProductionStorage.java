package com.yanicksenn.helloguice;

import com.google.protobuf.Message;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductionStorage implements Storage {

    // Pretend this is writing to a database / filesystem / etc.
    private final HashMap<Message, Message> storage = new HashMap<>();

    @Override
    public Message read(Message key) {
        return storage.get(key);
    }

    @Override
    public List<Message> readAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void write(Message key, Message value) {
        storage.put(key, value);
    }
}
