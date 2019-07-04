package com.aio.abyss.tasks.enumData;

import com.aio.abyss.tasks.utility.Statics;
import com.api.API;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.component.tab.*;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;

public enum Armor {
    IRON(1, Statics.IRON_FULL_HELM, Statics.IRON_PLATEBODY, Statics.IRON_PLATELEGS, Statics.IRON_KITESHIELD),
    MITHRIL(20, Statics.MITHRIL_FULL_HELM, Statics.MITHRIL_PLATEBODY, Statics.MITHRIL_PLATELEGS, Statics.MITHRIL_KITESHIELD),
    POUCH(40, Statics.RUNE_FULL_HELM, Statics.RUNE_CHAINBODY, Statics.RUNE_PLATELEGS, Statics.RUNE_KITESHIELD);

    private int level;
    private int items[];

    Armor(int level, int... items) {
        this.level = level;
        this.items = items;
    }

    public static Armor requiredArmor() {
        if (Skills.getCurrentLevel(Skill.DEFENCE) < 20) {
            return IRON;
        } else if (Skills.getCurrentLevel(Skill.DEFENCE) < 40) {
            return MITHRIL;
        } else {
            return POUCH;
        }
    }

    public static boolean wearItem(int itemID) {
        Item item = Inventory.getFirst(itemID);
        if (API.closeInterfaces())
            if (API.openTab(Tab.INVENTORY)) {
                return item != null && item.interact(ActionOpcodes.ITEM_ACTION_1);
            }
        return false;
    }

    public boolean hasWieldedAll() {
        for (int item : items) {
            if (!Equipment.contains(item)) {
                return false;
            }
        }
        return true;
    }

    private int missingItem() {
        for (int item : items) {
            if (!Equipment.contains(item)) {
                if (Skills.getCurrentLevel(Skill.DEFENCE) >= level) {
                    return item;
                }
            }
        }
        return 0;
    }

    public boolean inventoryContainsMissingItem() {
        return Inventory.contains(missingItem());
    }

    public boolean wearMissingItem() {
        if (API.closeInterfaces()) {
            Item item = Inventory.getFirst(missingItem());
            if (API.openTab(Tab.INVENTORY)) {
                return item != null && item.interact(ActionOpcodes.ITEM_ACTION_1);
            }
        }
        return false;
    }

    public int armorPart() {
        if (Skills.getCurrentLevel(Skill.DEFENCE) < 20) {
            return IRON.missingItem();
        } else if (Skills.getCurrentLevel(Skill.DEFENCE) < 40) {
            return MITHRIL.missingItem();
        } else {
            return POUCH.missingItem();
        }
    }
}
