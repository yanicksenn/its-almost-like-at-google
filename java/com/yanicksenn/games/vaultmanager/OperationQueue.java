package com.yanicksenn.games.vaultmanager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.yanicksenn.games.vaultmanager.proto.Operation;
import com.yanicksenn.libraries.dates.Dates;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

@Singleton
public class OperationQueue {

    private final OperationHandlers operationHandlers;
    private final PriorityQueue<Operation> queue;

    @Inject
    OperationQueue(OperationHandlers operationHandlers) {
        this.operationHandlers = operationHandlers;
        this.queue = new PriorityQueue<>(
            Comparator.comparing(Operation::getDate, Dates.COMPARATOR));
    }

    public void enqueue(Operation operation) {
        Objects.requireNonNull(operation);
        queue.add(operation);
    }

    public void handle() {
        if (queue.isEmpty()) {
            throw new IllegalStateException("queue is empty");
        }

        Operation operation = queue.poll();
        operationHandlers.handle(operation);
    }

    public boolean hasMoreOperations() {
        return !queue.isEmpty();
    }
}
