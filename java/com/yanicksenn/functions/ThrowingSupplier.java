package com.yanicksenn.functions;

@FunctionalInterface
public interface ThrowingSupplier<T> {
    T get() throws Exception;
}