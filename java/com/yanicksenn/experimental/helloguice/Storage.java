package com.yanicksenn.experimental.helloguice;

import com.google.protobuf.Message;
import java.util.List;

public interface Storage {
    Message read(Message key);
    List<Message> readAll();
    void write(Message key, Message value);
}
