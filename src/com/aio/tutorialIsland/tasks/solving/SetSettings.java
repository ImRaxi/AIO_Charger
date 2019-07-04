package com.aio.tutorialIsland.tasks.solving;

import com.aio.tutorialIsland.tasks.utility.Statics;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.component.tab.Tabs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

public class SetSettings extends Task {

    @Override
    public boolean validate() {
        Player me = Players.getLocal();
        return me != null && (Statics.setGraphics || Statics.setEscapeClose || Statics.setShiftDropping);
    }

    @Override
    public int execute() {
        if (Statics.setGraphics) {
            if (Tab.OPTIONS.isOpen()) {
                InterfaceComponent interfaceComponent = Interfaces.getFirst(261, a -> a.containsAction("Fixed mode"));
                if (interfaceComponent != null && interfaceComponent.isVisible() && interfaceComponent.interact("Fixed mode")) {
                    Statics.setGraphics = false;
                }
            } else {
                Tabs.open(Tab.OPTIONS);
                Time.sleepUntil(Tab.OPTIONS::isOpen, Random.nextInt(400, 600));
            }
        } else if (Statics.setEscapeClose) {
            if (Varps.get(1224) == 172395585) {
                InterfaceComponent escBtn = Interfaces.getComponent(121, 103);
                InterfaceComponent keyBindingBtn = Interfaces.getFirst(261, a -> a.containsAction("Keybinding"));
                InterfaceComponent controlsBtn = Interfaces.getComponent(261, 1, 6);
                if (escBtn != null && escBtn.isVisible() && escBtn.interact("Select")) {
                    Statics.setEscapeClose = false;
                } else if (keyBindingBtn != null && keyBindingBtn.isVisible() && keyBindingBtn.interact("Keybinding")) {
                    Time.sleepUntil(() -> escBtn != null && escBtn.isVisible(), Random.nextInt(1000, 2000));
                } else if (controlsBtn != null && controlsBtn.isVisible() && controlsBtn.interact("Controls")) {
                    Time.sleepUntil(() -> keyBindingBtn != null && keyBindingBtn.isVisible(), Random.nextInt(1000, 2000));
                } else {
                    Tabs.open(Tab.OPTIONS);
                    Time.sleepUntil(Tab.OPTIONS::isOpen, Random.nextInt(1000, 1500));
                }
            } else {
                Statics.setEscapeClose = false;
            }
        } else if (Statics.setShiftDropping) {
            if (Varps.get(1055) == (-2147474176)) {
                InterfaceComponent shiftFroppingBtn = Interfaces.getFirst(261, a -> a.containsAction("Toggle shift click to drop"));
                InterfaceComponent controlsBtn = Interfaces.getComponent(261, 1, 6);
                if (shiftFroppingBtn != null && shiftFroppingBtn.isVisible() && shiftFroppingBtn.interact("Toggle shift click to drop")) {
                    Statics.setShiftDropping = false;
                } else if (controlsBtn != null && controlsBtn.isVisible() && controlsBtn.interact("Controls")) {
                    Time.sleepUntil(() -> shiftFroppingBtn != null && shiftFroppingBtn.isVisible(), Random.nextInt(1000, 2000));
                } else {
                    Tabs.open(Tab.OPTIONS);
                    Time.sleepUntil(Tab.OPTIONS::isOpen, Random.nextInt(1000, 1500));
                }
            } else {
                Statics.setShiftDropping = false;
            }
        }
        return Random.nextInt(500, 800);
    }
}
