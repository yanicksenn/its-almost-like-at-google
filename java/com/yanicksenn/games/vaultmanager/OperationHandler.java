package com.yanicksenn.games.vaultmanager;

import com.yanicksenn.games.vaultmanager.proto.Operation;
import com.yanicksenn.games.vaultmanager.proto.OperationResult;

public interface OperationHandler {
    OperationResult handle(Operation operation);

    default OperationResult success() {
        return OperationResult.newBuilder()
                .setSuccess(OperationResult.Success.getDefaultInstance())
                .build();
    }

    default OperationResult ignored(String message, Object... args) {
        return OperationResult.newBuilder()
                .setIgnored(OperationResult.Ignored.newBuilder()
                    .setMessage(String.format(message, args)))
                .build();
    }

    default OperationResult failed(String message, Object... args) {
        return OperationResult.newBuilder()
                .setFailed(OperationResult.Failed.newBuilder()
                    .setMessage(String.format(message, args)))
                .build();
    }
}
