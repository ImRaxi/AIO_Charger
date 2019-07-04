package com.aio.universalTraining.tasks.enums;

import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;

import static com.aio.universalTraining.tasks.enums.SlayerEquipment.*;

public enum LevelCheck {
    LEVEL_5(5, IRON),
    LEVEL_10(10, STEEL),
    LEVEL_20(20, BLACK),
    LEVEL_30(30, MITHRIL),
    LEVEL_40(40, ADAMANT),
    LEVEL_50(50, RUNE);

    private int level;
    private SlayerEquipment type;

    LevelCheck(int level, SlayerEquipment type) {
        this.level = level;
        this.type = type;
    }

    public static SlayerEquipment getType() {
        for (LevelCheck levelCheck : LevelCheck.values()) {
            if (Skills.getCurrentLevel(Skill.ATTACK) < levelCheck.level || Skills.getCurrentLevel(Skill.DEFENCE) < levelCheck.level) {
                return levelCheck.type;
            }
        }
        return null;
    }
}
