package com.aio.abyss.tasks.enumData;

import com.aio.abyss.tasks.utility.Areas;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.movement.position.Area;

public enum Altar {

    EARTH(9, 24972, 14900, Areas.INSIDE_EARTH_ALTAR),
    FIRE(14, 24971, 14901, Areas.INSIDE_FIRE_ALTAR),
    NATURE(44, 24975, 14905, Areas.INSIDE_NATURE_ALTAR);

    private int level;
    private int altarEntrance;
    private int altarStoneID;
    private Area ALTAR_AREA;

    Altar(int level, int altarEntrance, int altarStoneID, Area ALTAR_AREA) {
        this.level = level;
        this.altarEntrance = altarEntrance;
        this.altarStoneID = altarStoneID;
        this.ALTAR_AREA = ALTAR_AREA;
    }

    public static int getAltarEntrance() {
        Altar altars = EARTH;
        for (Altar altar : Altar.values()) {
            if (Skills.getCurrentLevel(Skill.RUNECRAFTING) >= altar.level) {
                altars = altar;
            }
        }
        return altars.altarEntrance;
    }

    public static int getAltarStone() {
        Altar altars = EARTH;
        for (Altar altar : Altar.values()) {
            if (Skills.getCurrentLevel(Skill.RUNECRAFTING) >= altar.level) {
                altars = altar;
            }
        }
        return altars.altarStoneID;
    }

    public static Area altarArea() {
        Altar altars = EARTH;
        for (Altar altar : Altar.values()) {
            if (Skills.getCurrentLevel(Skill.RUNECRAFTING) >= altar.level) {
                altars = altar;
            }
        }
        return altars.ALTAR_AREA;
    }
}
