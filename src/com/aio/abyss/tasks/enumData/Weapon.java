package com.aio.abyss.tasks.enumData;

import com.aio.abyss.tasks.utility.Statics;
import com.api.API;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.GrandExchange;
import org.rspeer.runetek.api.component.tab.Equipment;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.input.Keyboard;

import java.awt.event.KeyEvent;

public enum Weapon {
    IRON(1, Statics.IRON_SCIMITAR),
    MITHRIL(20, Statics.MITHRIL_SCIMITAR),
    POUCH(40, Statics.RUNE_SCIMITAR);

    private int level, weapon;

    Weapon(int level, int weapon) {
        this.level = level;
        this.weapon = weapon;
    }

    public static Weapon requiredweapon() {
        if (Skills.getCurrentLevel(Skill.ATTACK) < 20) {
            return IRON;
        } else if (Skills.getCurrentLevel(Skill.ATTACK) < 40) {
            return MITHRIL;
        } else {
            return POUCH;
        }
    }

    public boolean hasWielded() {
        return Equipment.contains(weapon);
    }

    public boolean inventoryContains() {
        return Inventory.contains(weapon);
    }

    public boolean wearWeapon() {
        if (!Bank.isOpen() && !GrandExchange.isOpen()) {
            if (API.closeInterfaces()) {
                Item item = Inventory.getFirst(weapon);
                return item != null && item.interact("Wield");
            }
        } else {
            Keyboard.pressEventKey(KeyEvent.VK_ESCAPE);
        }
        return false;
    }

    public int weapon() {
        if (Skills.getCurrentLevel(Skill.ATTACK) < 20) {
            return IRON.weapon;
        } else if (Skills.getCurrentLevel(Skill.ATTACK) < 40) {
            return MITHRIL.weapon;
        } else {
            return POUCH.weapon;
        }
    }
}
