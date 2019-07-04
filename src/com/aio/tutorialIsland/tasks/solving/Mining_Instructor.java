package com.aio.tutorialIsland.tasks.solving;

import com.aio.tutorialIsland.TutorialIsland;
import com.aio.tutorialIsland.tasks.utility.Statics;
import com.api.API;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

public class Mining_Instructor extends Task {

    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Varps.get(281) >= 250 && Varps.get(281) <= 360;
    }

    @Override
    public int execute() {
        SceneObject ladder = SceneObjects.getNearest(9726);
        if (Varps.get(281) == 250) {
            if (ladder != null) {
                TutorialIsland.status = "Climbing down.";
                ladder.interact("Climb-down");
                Time.sleep(Random.nextInt(400, 600));
            }
        } else if (Varps.get(281) == 260 || Varps.get(281) == 330) {
            Npc mi = Npcs.getNearest(Statics.MINING_INSTRUCTOR);
            if (mi != null) {
                TutorialIsland.status = "Talking with Mining Instructor .";
                mi.interact("Talk-to");
                Time.sleep(Random.nextInt(400, 600));
            } else {
                Movement.walkTo(new Position(3081, 9507));
                Time.sleep(Random.nextInt(900, 1100));
            }
        } else if (Varps.get(281) == 300) {
            SceneObject mineRock = SceneObjects.getNearest(10080);
            if (mineRock != null) {
                TutorialIsland.status = "Mining Tin ore.";
                mineRock.interact("Mine");
                Time.sleepUntil(() -> Inventory.contains(Statics.TIN_ORE) || me.getAnimation() == -1, 5000);
            }
        } else if (Varps.get(281) == 310) {
            SceneObject mineRock = SceneObjects.getNearest(10079);
            if (mineRock != null) {
                TutorialIsland.status = "Mining Copper ore.";
                mineRock.interact("Mine");
                Time.sleepUntil(() -> Inventory.contains(Statics.COPPER_ORE) || me.getAnimation() == -1, 5000);
            }
        } else if (Varps.get(281) == 320) {
            Item w = Inventory.getFirst(Statics.COPPER_ORE);
            if (w != null) {
                TutorialIsland.status = "Smelting bronze bar.";
                API.useItemOnObject(w, 10082);
                Time.sleepUntil(() -> !Inventory.contains(Statics.COPPER_ORE) && me.getAnimation() == -1, 5000);
            }
        } else if (Varps.get(281) == 340) {
            SceneObject anvil = SceneObjects.getNearest(2097);
            if (anvil != null) {
                TutorialIsland.status = "Smithing dagger.";
                anvil.interact("Smith");
                Time.sleep(Random.nextInt(1500, 1750));
            }
        } else if (Varps.get(281) == 350) {
            InterfaceComponent w = Interfaces.getComponent(312, 2);
            if (w != null && w.isVisible()) {
                TutorialIsland.status = "Smithing dagger.";
                w.interact("Smith 1");
                Time.sleep(Random.nextInt(400, 600));
            }
        } else if (Varps.get(281) == 360) {
            SceneObject door = SceneObjects.getNearest(9718);
            if (door != null) {
                TutorialIsland.status = "Opening door.";
                door.interact("Open");
                Time.sleep(Random.nextInt(900, 1100));
            }
        }


        return Random.nextInt(300, 500);
    }
}
