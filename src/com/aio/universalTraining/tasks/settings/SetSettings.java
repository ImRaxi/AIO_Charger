package com.aio.universalTraining.tasks.settings;

import com.aio.abyss.Abyss;
import com.aio.universalTraining.UniversalTraining;
import com.aio.universalTraining.tasks.utility.Statics;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.component.tab.Tabs;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

public class SetSettings extends Task {

    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Statics.setGraphics || (Movement.getRunEnergy() >= 25 && !Movement.isRunEnabled() || (Dialog.isOpen() && Dialog.canContinue() && !Dialog.isViewingChatOptions()));
    }

    @Override
    public int execute() {
        if (Statics.setGraphics) {
            UniversalTraining.status = "Setting fixed mode";
            if (Tab.OPTIONS.isOpen()) {
                InterfaceComponent interfaceComponent = Interfaces.getFirst(261, a -> a.containsAction("Fixed mode"));
                if (interfaceComponent != null && interfaceComponent.isVisible() && interfaceComponent.interact("Fixed mode")) {
                    Statics.setGraphics = false;
                }
            } else {
                Tabs.open(Tab.OPTIONS);
                Time.sleepUntil(Tab.OPTIONS::isOpen, Random.nextInt(400, 600));
            }
        } else if (Movement.getRunEnergy() >= 25 && !Movement.isRunEnabled()) {
            Abyss.status = "Turning on Run Energy";
            Movement.toggleRun(true);
            Time.sleep(Random.nextInt(225, 300));
        } else if (Dialog.isOpen() && Dialog.canContinue() && !Dialog.isViewingChatOptions()) {
            Dialog.processContinue();
            Time.sleep(750);
        }
        return Random.nextInt(500, 800);
    }
}
