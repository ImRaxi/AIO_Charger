package com.aio.universalTraining.tasks.enums;

import com.aio.universalTraining.tasks.utility.Statics;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;

public enum Food {
    SALMON(15, Statics.SALMON),
    TUNA(20, Statics.TUNA),
    LOBSTER(30, Statics.LOBSTER),
    MONKFISH(99, Statics.MONKFISH);

    private int level, id;

    Food(int level, int id) {
        this.level = level;
        this.id = id;
    }

    public static int getBestFood() {
        for (Food food : Food.values()) {
            if (Skills.getCurrentLevel(Skill.HITPOINTS) < food.level) {
                return food.id;
            }
        }
        return 0;
    }
}
