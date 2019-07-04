package com.aio.abyss.tasks.solving;

import com.aio.abyss.Abyss;
import com.aio.abyss.tasks.enumData.AgilityObstacles;
import com.aio.abyss.tasks.utility.Areas;
import com.aio.abyss.tasks.utility.Statics;
import com.api.API;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.*;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

public class Agility_Training extends Task {

    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Skills.getCurrentLevel(Skill.AGILITY) < 40;
    }

    @Override
    public int execute() {
        SceneObject lever = SceneObjects.getNearest("Lever");
        if (Areas.MONASTERY.contains(me)) {
            Abyss.status = "Teleporting to Edgeville";
            if (API.openTab(Tab.EQUIPMENT)) {
                Equipment.interact(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), "Edgeville");
                Time.sleepUntil(() -> me.getAnimation() == -1 || Areas.EDGEVILLE.contains(me), 4500);
            }
        } else if (Areas.EDGEVILLE.contains(me)) {
            if (Inventory.contains(Statics.LOBSTER)) {
                Abyss.status = "Opening bank";
                if (API.openBank()) {
                    Bank.depositAll(Statics.LOBSTER);
                }
            } else if (lever != null) {
                InterfaceComponent chat = Dialog.getChatOption(a -> a.contains("Yes please, don't show this message again."));
                if (chat != null && chat.isVisible()) {
                    chat.interact("Continue");
                    Time.sleepUntil(() -> !me.isAnimating(), 2000);
                } else {
                    lever.interact("Pull");
                    Time.sleep(Random.nextInt(1750, 2250));
                }
            } else {
                Abyss.status = "Walk to lever";
                Movement.walkTo(new Position(3093, 3477));
                Time.sleep(Random.nextInt(1750, 2250));
            }
        } else if (Areas.WILDERNESS_LEVER_AREA.contains(me)) {
            if (lever != null) {
                Abyss.status = "Pull wilderness lever";
                lever.interact("Pull");
                Time.sleepUntil(() -> !me.isAnimating(), 2000);
            }
        } else if (Areas.TREE_GNOME_STRONGHOLD.contains(me) || Areas.AGILITY_TRAINNG_AREA_UPSTAIRS.contains(me) || Areas.AGILITY_TRAINNG_AREA_UPSTAIRS2.contains(me) || Areas.AGILITY_TRAINNG_AREA.contains(me)) {
            Abyss.status = "Training agility, lvl: " + Skills.getCurrentLevel(Skill.AGILITY);
            SceneObject sceneObject = SceneObjects.getNearest(AgilityObstacles.getCurrentObstacle());
            if (sceneObject != null) {
                if (AgilityObstacles.interact()) {
                    Time.sleep(Random.nextInt(400, 600));
                }
            } else {
                Abyss.status = "Walk to Agility training area";
                Movement.walkTo(new Position(2474, 3436));
                Time.sleep(Random.nextInt(1750, 2250));
            }
        } else {
            SceneObject gate = SceneObjects.getNearest(190);
            if (gate != null) {
                InterfaceComponent chat = Dialog.getChatOption(a -> a.contains("Sorry"));
                if (chat != null && chat.isVisible()) {
                    chat.interact("Continue");
                    Time.sleep(256);
                } else {
                    Abyss.status = "Open gate";
                    gate.interact("Open");
                    Time.sleep(Random.nextInt(1250, 1750));
                }
            } else {
                Abyss.status = "Walk to Tree Gnome Area";
                Movement.walkTo(new Position(2461, 3380));
                Time.sleep(Random.nextInt(1750, 2250));
            }
        }
        return Random.nextInt(500, 800);
    }
}
