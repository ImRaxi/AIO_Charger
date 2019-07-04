package com.aio.tutorialIsland.tasks.solving;

import com.aio.tutorialIsland.TutorialIsland;
import com.aio.tutorialIsland.tasks.utility.Statics;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Equipment;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

public class Combat_Instructor extends Task {

    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Varps.get(281) >= 370 && Varps.get(281) <= 500;
    }

    @Override
    public int execute() {
        if (Varps.get(281) == 370 || Varps.get(281) == 410) {
            Npc ci = Npcs.getNearest(Statics.COMBAT_INSTRUCTOR);
            if (ci != null) {
                TutorialIsland.status = "Talking with Combat Instructor.";
                ci.interact("Talk-to");
                Time.sleep(Random.nextInt(900, 1100));
            }
        } else if (Varps.get(281) == 390) {
            InterfaceComponent w = Interfaces.getComponent(164, 54);
            if (w != null && w.isVisible()) {
                TutorialIsland.status = "Opening equipment";
                w.interact("Worn Equipment");
                Time.sleep(Random.nextInt(400, 600));
            }
        } else if (Varps.get(281) == 400) {
            InterfaceComponent w = Interfaces.getComponent(387, 17);
            if (w != null && w.isVisible()) {
                TutorialIsland.status = "Opening equipment stats";
                w.interact("View equipment stats");
                Time.sleep(Random.nextInt(400, 600));
            }
        } else if (Varps.get(281) == 405) {
            Item w = Inventory.getFirst("Bronze dagger");
            if (w != null) {
                TutorialIsland.status = "Wielding bronze dagger";
                w.interact("Wield");
                Time.sleep(Random.nextInt(400, 600));
            }
        } else if (Varps.get(281) == 420) {
            Item sword = Inventory.getFirst(Statics.BRONZE_SWORD);
            Item shield = Inventory.getFirst(Statics.WOODEN_SHIELD);
            if (sword != null && !Equipment.contains(Statics.BRONZE_SWORD)) {
                TutorialIsland.status = "Equipping bronze sword";
                sword.interact("Wield");
                Time.sleep(Random.nextInt(400, 600));
            } else if (shield != null && !Equipment.contains(Statics.WOODEN_SHIELD)) {
                TutorialIsland.status = "Equipping wooden shield";
                shield.interact("Wield");
                Time.sleep(Random.nextInt(400, 600));
            }
        } else if (Varps.get(281) == 430) {
            InterfaceComponent w = Interfaces.getComponent(164, 50);
            if (w != null && w.isVisible()) {
                TutorialIsland.status = "Opening combat options";
                w.interact("Combat Options");
                Time.sleep(Random.nextInt(400, 600));
            }
        } else if (Varps.get(281) == 440) {
            SceneObject door = SceneObjects.getNearest(9719);
            if (door != null) {
                TutorialIsland.status = "Opening door";
                door.interact("Open");
                Time.sleep(Random.nextInt(400, 600));
            }
        } else if (Varps.get(281) == 450 || Varps.get(281) == 460) {
            Npc rat = Npcs.getNearest("Giant rat");
            if (rat != null) {
                TutorialIsland.status = "Killing rat";
                rat.interact("Attack");
                Time.sleepUntil(() -> me.getTarget() == null, 30000);
            }
        } else if (Varps.get(281) == 470) {
            Npc ci = Npcs.getNearest(Statics.COMBAT_INSTRUCTOR);
            if (ci != null) {
                if (me.distance(ci) < 2) {
                    TutorialIsland.status = "Talking with Combat Instructor";
                    ci.interact("Talk-to");
                    Time.sleep(Random.nextInt(400, 600));
                } else {
                    TutorialIsland.status = "Walking to Combat Instructor";
                    Movement.walkTo(ci.getPosition());
                    Time.sleep(Random.nextInt(1800, 2200));
                }
            }
        } else if (Varps.get(281) == 480 || Varps.get(281) == 490) {
            Npc rat = Npcs.getNearest("Giant rat");
            Item shortbow = Inventory.getFirst(Statics.SHORTBOW);
            Item arrow = Inventory.getFirst(Statics.BRONZE_ARROW);
            if (shortbow != null && !Equipment.contains(Statics.SHORTBOW)) {
                TutorialIsland.status = "Wielding shortbow";
                shortbow.interact("Wield");
                Time.sleep(Random.nextInt(400, 600));
            } else if (arrow != null && !Equipment.contains(Statics.BRONZE_ARROW)) {
                TutorialIsland.status = "Wielding bronze arrow";
                arrow.interact("Wield");
                Time.sleep(Random.nextInt(400, 600));
            } else if (rat != null) {
                TutorialIsland.status = "Killing rat";
                rat.interact("Attack");
                Time.sleepUntil(() -> me.getTarget() == null, 30000);
            }

        } else if (Varps.get(281) == 500) {
            SceneObject ladder = SceneObjects.getNearest("Ladder");
            if (ladder != null) {
                TutorialIsland.status = "Climbing ladder";
                ladder.interact("Climb-up");
                Time.sleep(Random.nextInt(400, 600));
            } else {
                Movement.walkTo(new Position(3111, 9524));
                Time.sleep(Random.nextInt(400, 600));
            }
        }
        return Random.nextInt(300, 500);
    }
}
