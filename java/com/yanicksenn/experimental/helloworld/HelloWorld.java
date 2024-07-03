package com.yanicksenn.experimental.helloworld;

import com.yanicksenn.libraries.flags.Flags;

public final class HelloWorld {
    public static void main(String[] args) {
        Flags.init(args);
        String name = Flags.get("name").orElse("World");
        System.out.println(String.format("Hello, %s!", name));
    }
}
