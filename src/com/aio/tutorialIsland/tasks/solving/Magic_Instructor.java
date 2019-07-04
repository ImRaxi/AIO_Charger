package com.aio.tutorialIsland.tasks.solving;

import com.aio.tutorialIsland.TutorialIsland;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Magic;
import org.rspeer.runetek.api.component.tab.Spell;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

public class Magic_Instructor extends Task {

    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Varps.get(281) >= 620 && Varps.get(281) <= 670;

    }

    @Override
    public int execute() {
        if (Varps.get(281) == 620 || Varps.get(281) == 640) {
            Npc mage = Npcs.getNearest("Magic Instructor");
            if (mage != null) {
                TutorialIsland.status = "Talking to Magic Instructor";
                mage.interact("Talk-to");
                Time.sleep(Random.nextInt(400, 600));
            } else {
                TutorialIsland.status = "Walking to Magic Instructor";
                Movement.walkTo(new Position(3140, 3087));
                Time.sleep(Random.nextInt(900, 1100));
            }
        } else if (Varps.get(281) == 630) {
            InterfaceComponent w = Interfaces.getComponent(164, 56);
            if (w != null && w.isVisible()) {
                TutorialIsland.status = "Opening Magic";
                w.interact("Magic");
                Time.sleep(Random.nextInt(400, 600));
            }
        } else if (Varps.get(281) == 650) {
            if (!Magic.isSpellSelected()) {
                Magic.cast(Spell.Modern.WIND_STRIKE);
            } else {
                Npc chicken = Npcs.getNearest("Chicken");
                if (chicken != null) {
                    chicken.interact("Cast");
                    Time.sleepUntil(() -> me.getTarget() == null, 30000);
                }
            }
        } else if (Varps.get(281) == 670) {
            Npc mage = Npcs.getNearest("Magic Instructor");
            InterfaceComponent dialog = Dialog.getChatOption(a -> a.contains("Yes.") || a.contains("No, I'm not planning to do that."));
            if (dialog != null) {
                dialog.interact("Continue");
                Time.sleep(250);
            } else if (mage != null) {
                TutorialIsland.status = "Talking with Magic Instructor";
                mage.interact("Talk-to");
                Time.sleep(Random.nextInt(400, 600));
            }
        }
        return Random.nextInt(300, 500);
    }
}
