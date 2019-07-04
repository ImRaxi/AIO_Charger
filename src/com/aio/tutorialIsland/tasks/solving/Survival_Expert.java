package com.aio.tutorialIsland.tasks.solving;

import com.aio.tutorialIsland.TutorialIsland;
import com.aio.tutorialIsland.tasks.utility.Areas;
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

public class Survival_Expert extends Task {

    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && (Varps.get(281) >= 10 && Varps.get(281) <= 90);
    }

    @Override
    public int execute() {
        if (Varps.get(281) == 10) {
            if (Areas.STARTING_HOUSE.contains(me)) {
                TutorialIsland.status = "Leaving starting house.";
                SceneObject door = SceneObjects.getNearest(a -> a.getId() == 9398 && a.getPosition().equals(new Position(3098, 3107)));
                if (door != null) {
                    door.interact("Open");
                    Time.sleepUntil(() -> !Areas.STARTING_HOUSE.contains(me), 1500);
                }
            }
        } else if (Varps.get(281) == 20 || Varps.get(281) == 60) {
            Npc npc = Npcs.getNearest(Statics.SURVIVAL_EXPERT);
            if (npc != null) {
                TutorialIsland.status = "Talking with Survival Expert.";
                npc.interact("Talk-to");
                Time.sleep(Random.nextInt(400, 600));
            }
        } else if (Varps.get(281) == 30) {
            InterfaceComponent interfaceComponent = Interfaces.getComponent(164, 53);
            if (interfaceComponent != null && interfaceComponent.isVisible()) {
                TutorialIsland.status = "Opening Inventory.";
                interfaceComponent.interact("Inventory");
                Time.sleep(250);
            }
        } else if (Varps.get(281) == 40) {
            if (!Inventory.contains(Statics.RAW_SHRIMP)) {
                Npc npc = Npcs.getNearest(Statics.FISHING_SPOT);
                if (npc != null) {
                    TutorialIsland.status = "Fishing shrimp.";
                    npc.interact("Net");
                    Time.sleepUntil(() -> me.getAnimation() != -1 || Inventory.contains(Statics.RAW_SHRIMP), 5000);
                }
            }
        } else if (Varps.get(281) == 50) {
            InterfaceComponent interfaceComponent = Interfaces.getComponent(164, 51);
            if (interfaceComponent != null && interfaceComponent.isVisible()) {
                TutorialIsland.status = "Opening Skills.";
                interfaceComponent.interact("Skills");
                Time.sleep(250);
            }
        } else if (Varps.get(281) == 70) {
            if (!Inventory.contains(Statics.LOGS)) {
                SceneObject sceneObject = SceneObjects.getNearest(Statics.TREE);
                if (sceneObject != null) {
                    TutorialIsland.status = "Chopping down tree.";
                    sceneObject.interact("Chop down");
                    Time.sleepUntil(() -> Inventory.contains(Statics.LOGS) || me.getAnimation() == -1, 10000);
                }
            }
        } else if (Varps.get(281) == 80) {
            SceneObject sceneObject = SceneObjects.getNearest(a -> a.getId() == Statics.FIRE && a.getPosition().equals(me.getPosition()));
            if (sceneObject != null) {
                TutorialIsland.status = "Moving from fire.";
                Movement.walkTo(new Position(me.getX(), me.getY() + 1));
                Time.sleep(300);
            } else {
                if (me.getAnimation() == -1) {
                    TutorialIsland.status = "Lighting fire.";
                    Item tinderbox = Inventory.getFirst(Statics.TINDERBOX);
                    Inventory.use(a -> a.getName().equals("Logs"), tinderbox);
                }
            }
        } else if (Varps.get(281) == 90) {
            Item item = Inventory.getFirst(Statics.RAW_SHRIMP);
            TutorialIsland.status = "Cooking shrimps.";
            if (item != null) {
                API.useItemOnObject(item, "Fire");
                Time.sleepUntil(() -> !Inventory.contains(Statics.RAW_SHRIMP), 5000);
            }
        }
        return Random.nextInt(300, 500);
    }
}
