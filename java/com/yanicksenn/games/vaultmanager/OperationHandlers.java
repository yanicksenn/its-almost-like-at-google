package com.yanicksenn.games.vaultmanager;

import com.google.common.collect.ImmutableMap;
import com.yanicksenn.games.vaultmanager.proto.Operation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.yanicksenn.games.vaultmanager.proto.OperationResult;

import java.util.Objects;

import static com.yanicksenn.games.vaultmanager.proto.Operation.OperationCase.DEATH_OPERATION;
import static com.yanicksenn.games.vaultmanager.proto.Operation.OperationCase.PLOP_OPERATION;

@Singleton
public class OperationHandlers implements OperationHandler {

    private final OperationLog operationLog;
    private final ImmutableMap<Operation.OperationCase, OperationHandler> operationHandlerByCase;

    @Inject
    OperationHandlers(
            OperationLog operationLog,
            PlopOperationHandler plopOperationHandler,
            DeathOperationHandler deathOperationHandler) {
        this.operationLog = operationLog;
        this.operationHandlerByCase = ImmutableMap.<Operation.OperationCase, OperationHandler>builder()
                .put(PLOP_OPERATION, plopOperationHandler)
                .put(DEATH_OPERATION, deathOperationHandler)
                .build();
    }

    @Override
    public OperationResult handle(Operation operation) {
        Objects.requireNonNull(operation);

        OperationHandler operationHandler =
                operationHandlerByCase.get(operation.getOperationCase());
        if (operationHandler == null) {
            throw new IllegalArgumentException(
                String.format("operation case %s unknown", operation.getOperationCase()));
        }

        OperationResult result = operationHandler.handle(operation);
        operationLog.log(operation, result);
        return result;
    }
}
