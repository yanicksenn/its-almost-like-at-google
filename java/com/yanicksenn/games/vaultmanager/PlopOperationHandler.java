package com.yanicksenn.games.vaultmanager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.yanicksenn.games.vaultmanager.proto.Human;
import com.yanicksenn.games.vaultmanager.proto.Operation;
import com.yanicksenn.games.vaultmanager.proto.OperationResult;
import com.yanicksenn.games.vaultmanager.proto.PlopOperation;

@Singleton
public class PlopOperationHandler implements OperationHandler {

    private final Population population;

    @Inject
    PlopOperationHandler(Population population) {
        this.population = population;
    }

    @Override
    public OperationResult handle(Operation operation) {
        if (!operation.hasPlopOperation()) {
            return failed(String.format("operation case %s cannot be handled", operation.getOperationCase()));
        }

        PlopOperation plopOperation = operation.getPlopOperation();
        population.update(Human.newBuilder()
            .setId(plopOperation.getHumanId())
            .setProperties(plopOperation.getHumanProperties())
            .build());
        return success();
    }
}
