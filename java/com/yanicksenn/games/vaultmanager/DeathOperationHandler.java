package com.yanicksenn.games.vaultmanager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.yanicksenn.games.vaultmanager.proto.DeathOperation;
import com.yanicksenn.games.vaultmanager.proto.Human;
import com.yanicksenn.games.vaultmanager.proto.Operation;
import com.yanicksenn.games.vaultmanager.proto.OperationResult;

import java.util.Optional;

@Singleton
public class DeathOperationHandler implements OperationHandler {

    private final Population population;

    @Inject
    public DeathOperationHandler(Population population) {
        this.population = population;
    }

    @Override
    public OperationResult handle(Operation operation) {
        if (!operation.hasDeathOperation()) {
            return failed("operation case %s cannot be handled", operation.getOperationCase());
        }

        DeathOperation deathOperation = operation.getDeathOperation();
        Human.Id humanId = deathOperation.getHumanId();
        Optional<Human> humanOpt = population.findHuman(humanId);
        if (humanOpt.isEmpty()) {
            return failed("human %s does not exist", humanId.getId());
        }

        Human human = humanOpt.get();
        Human.States states = human.getStates();
        if (states.getDead()) {
            return ignored("human %s is already dead", humanId.getId());
        }
        Human humanUpdated = human.toBuilder()
            .setStates(states.toBuilder()
                .setDead(true)
                .setDateOfDeath(operation.getDate()))
            .build();

        population.update(humanUpdated);
        return success();
    }
}
