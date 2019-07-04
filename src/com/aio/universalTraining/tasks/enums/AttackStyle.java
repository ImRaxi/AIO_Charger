package com.aio.universalTraining.tasks.enums;

import com.api.API;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;

public enum AttackStyle {
    ATTACK(0, 3),
    STRENGTH(1, 7),
    SHARED(2, 11),
    DEFENCE(3, 15);

    private static int varp = 43;
    private static int componentIndex = 593;
    private int varpValue;
    private int componentID;

    AttackStyle(int varpValue, int componentID) {
        this.varpValue = varpValue;
        this.componentID = componentID;
    }

    public static AttackStyle getCurrentStyle() {
        AttackStyle style = ATTACK;
        for (AttackStyle a : AttackStyle.values()) {
            if (a.varpValue == Varps.get(varp)) {
                style = a;
            }
        }
        return style;
    }

    public static boolean changeStyleTo(AttackStyle attackStyle) {
        int compID = attackStyle.componentID;
        InterfaceComponent interfaceComponent = Interfaces.getComponent(componentIndex, compID);
        if (Tab.COMBAT.isOpen()) {
            return interfaceComponent != null && interfaceComponent.isVisible() && interfaceComponent.interact(ActionOpcodes.INTERFACE_ACTION);
        } else {
            API.openTab(Tab.COMBAT);
        }
        return false;
    }
}
