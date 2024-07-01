package com.yanicksenn.libraries.functions;

@FunctionalInterface
public interface ThrowingSupplier<T> {
    T get() throws Exception;
}