package com.api;

import com.aio.abyss.tasks.utility.Statics;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.api.Worlds;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;
import org.rspeer.runetek.api.scene.Players;

public class HopWorld {

    public static void hopTo(int world) {
        world += 300;
        InterfaceComponent worldList = Interfaces.getComponent(69, 0);
        if (worldList != null) {
            if (API.openTab(Tab.LOGOUT)) {
                InterfaceComponent toHopTo = Interfaces.getComponent(69, 16, world);
                if (toHopTo != null) {
                    InterfaceComponent dialog = Dialog.getChatOption(a -> a.equals("Yes. In future"));
                    if (dialog != null && dialog.isVisible()) {
                        Time.sleepUntil(() -> Statics.badWorld != Worlds.getCurrent(), 7500);
                        Time.sleep(5000);
                    } else {
                        toHopTo.interact(ActionOpcodes.INTERFACE_ACTION);
                        Time.sleepUntil(() -> (Statics.badWorld != Worlds.getCurrent() && Players.getLocal() != null) || (dialog != null && dialog.isVisible()), 7500);
                    }
                }
            }
        } else if (API.closeInterfaces() && API.openTab(Tab.LOGOUT)) {
            InterfaceComponent worldSwitcher = Interfaces.getComponent(182, 3);
            if (worldSwitcher != null) {
                worldSwitcher.interact("World Switcher");
                Time.sleep(Random.nextInt(1000, 1250));
            }
        }
    }

    public static void hopToRandomP2p() {
        int worlds[] = {3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15, 17, 19, 20, 21, 22, 23, 27, 28, 29, 30, 31, 32, 33, 34, 36, 37, 38, 39, 40,
                41, 44, 46, 47, 48, 50, 51, 52, 54, 55, 56, 57, 58, 59, 60, 62, 67, 68, 70, 74, 75, 76, 77, 78, 86, 95, 121, 122, 124, 144, 145, 146,
                164, 165, 166, 191, 192, 193, 194, 195, 196, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225};
        int world = worlds[Random.nextInt(worlds.length)];
        world += 300;
        InterfaceComponent worldList = Interfaces.getComponent(69, 0);
        if (worldList != null) {
            if (API.openTab(Tab.LOGOUT)) {
                InterfaceComponent toHopTo = Interfaces.getComponent(69, 16, world);
                if (toHopTo != null) {
                    InterfaceComponent dialog = Dialog.getChatOption(a -> a.contains("Yes. In future"));
                    if (dialog != null && dialog.isVisible()) {
                        dialog.interact("Continue");
                        Time.sleepUntil(() -> Statics.badWorld != Worlds.getCurrent(), 7500);
                    } else {
                        toHopTo.interact(ActionOpcodes.INTERFACE_ACTION);
                        Time.sleepUntil(() -> (Statics.badWorld != Worlds.getCurrent() && Players.getLocal() != null) || (dialog != null && dialog.isVisible()), Random.nextInt(5000, 6000));
                    }
                }
            }
        } else if (API.closeInterfaces() && API.openTab(Tab.LOGOUT)) {
            InterfaceComponent worldSwitcher = Interfaces.getComponent(182, 3);
            if (worldSwitcher != null) {
                worldSwitcher.interact("World Switcher");
                Time.sleep(Random.nextInt(1000, 1250));
            }
        }
    }
}
