package com.aio.universalTraining.tasks.enums;

import com.aio.universalTraining.tasks.utility.Statics;
import org.rspeer.runetek.api.component.tab.Equipment;

public enum SlayerEquipment {

    IRON(Statics.IRON_SCIMITAR, Statics.IRON_FULL_HELM, Statics.IRON_KITESHIELD, Statics.IRON_PLATEBODY, Statics.IRON_PLATELEGS),
    STEEL(Statics.STEEL_SCIMITAR, Statics.STEEL_FULL_HELM, Statics.STEEL_KITESHIELD, Statics.STEEL_PLATEBODY, Statics.STEEL_PLATELEGS),
    BLACK(Statics.BLACK_SCIMITAR, Statics.BLACK_FULL_HELM, Statics.BLACK_KITESHIELD, Statics.BLACK_PLATEBODY, Statics.BLACK_PLATELEGS),
    MITHRIL(1),
    ADAMANT(1),
    RUNE(1);

    private int items[];

    SlayerEquipment(int... items) {
        this.items = items;
    }

    public boolean containsALL() {
        for (int item : items) {
            if (!Equipment.contains(item)) {
                Statics.equipmentToGet = item;
                return false;
            }
        }
        return true;
    }

    public int missingItem() {
        for (int item : items) {
            if (!Equipment.contains(item)) {
                return item;
            }
        }
        return 0;
    }
}
