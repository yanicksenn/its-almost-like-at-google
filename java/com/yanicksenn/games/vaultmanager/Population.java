package com.yanicksenn.games.vaultmanager;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;
import com.yanicksenn.games.vaultmanager.proto.Human;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Singleton
public class Population {
    private final HashMap<Human.Id, Human> humansById;
    private final SetMultimap<Human.Properties.Gender, Human> humansByGender;

    @Inject
    Population() {
        this.humansById = new HashMap<>();
        this.humansByGender = HashMultimap.create();
    }

    void update(Human human) {
        Objects.requireNonNull(human);
        humansById.put(human.getId(), human);
        humansByGender.put(human.getProperties().getGender(), human);
    }

    public Human.Id nextId() {
        return Human.Id.newBuilder()
            .setId(humansById.size() + 1)
            .build();
    }

    public Optional<Human> findHuman(Human.Id id) {
        Objects.requireNonNull(id);
        return Optional.ofNullable(humansById.get(id));
    }

    public ImmutableSet<Human> getHumans() {
        return ImmutableSet.copyOf(humansById.values());
    }

    public ImmutableSet<Human> getMales() {
        return ImmutableSet.copyOf(humansByGender.get(Human.Properties.Gender.MALE));
    }

    public int getMalesSize() {
        return humansByGender.get(Human.Properties.Gender.MALE).size();
    }

    public ImmutableSet<Human> getFemales() {
        return ImmutableSet.copyOf(humansByGender.get(Human.Properties.Gender.FEMALE));
    }

    public int getFemalesSize() {
        return humansByGender.get(Human.Properties.Gender.FEMALE).size();
    }

    public int size() {
        return humansById.size();
    }
}
