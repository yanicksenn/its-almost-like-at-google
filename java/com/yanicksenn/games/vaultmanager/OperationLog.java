package com.yanicksenn.games.vaultmanager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.yanicksenn.games.vaultmanager.proto.Operation;
import com.yanicksenn.games.vaultmanager.proto.OperationLogEntry;
import com.yanicksenn.games.vaultmanager.proto.OperationResult;

import java.util.ArrayList;

@Singleton
public class OperationLog {

    private final ArrayList<OperationLogEntry> operationLogs;

    @Inject
    OperationLog() {
        this.operationLogs = new ArrayList<>();
    }

    public void log(Operation operation, OperationResult result) {
        operationLogs.add(OperationLogEntry.newBuilder()
            .setOperation(operation)
            .setResult(result)
            .build());

        System.out.printf("date: %d-%02d-%02d, operation: %s, result: %s%n",
            operation.getDate().getYear(),
            operation.getDate().getMonth(),
            operation.getDate().getDay(),
            operation.getOperationCase(),
            result.getCodeCase());
    }
}
