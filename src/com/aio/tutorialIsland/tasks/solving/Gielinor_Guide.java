package com.aio.tutorialIsland.tasks.solving;

import com.aio.tutorialIsland.TutorialIsland;
import com.aio.tutorialIsland.tasks.utility.Statics;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

public class Gielinor_Guide extends Task {

    private Player me;
    private int tab[] = {106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 122, 123, 124, 125, 127, 129, 130, 131};

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Varps.get(281) >= 1 && Varps.get(281) <= 7;
    }

    @Override
    public int execute() {
        if (Varps.get(281) == 1) {
            TutorialIsland.status = "MakeOver";
            while (TutorialIsland.a < TutorialIsland.randomInt) {
                int x = Random.nextInt(0, tab.length);
                InterfaceComponent makeover = Interfaces.getComponent(269, tab[x]);
                if (makeover != null && makeover.isVisible()) {
                    TutorialIsland.a += 1;
                    makeover.interact(ActionOpcodes.BUTTON_INPUT);
                    Time.sleep(150);
                }
            }
            InterfaceComponent accept = Interfaces.getComponent(269, 99);
            if (accept != null && accept.isVisible()) {
                accept.interact(ActionOpcodes.BUTTON_INPUT);
                Time.sleep(150);
            }
        } else if (Varps.get(281) == 7 || Varps.get(281) == 2) {
            Npc npc = Npcs.getNearest(Statics.GIELINOR_GUIDE);
            InterfaceComponent dialog = Dialog.getChatOption(a -> a.contains("I am an"));
            if (dialog != null) {
                dialog.interact("Continue");
                Time.sleep(250);
            } else if (npc != null) {
                TutorialIsland.status = "Talking with Gielindor guide.";
                npc.interact("Talk-to");
                Time.sleep(300);
            }
        } else if (Varps.get(281) == 3) {
            InterfaceComponent w = Interfaces.getComponent(164, 38);
            if (w != null && w.isVisible()) {
                TutorialIsland.status = "Opening settings";
                w.interact("Options");
                Time.sleep(Random.nextInt(400, 600));
            }
        }
        return Random.nextInt(300, 500);
    }
}
