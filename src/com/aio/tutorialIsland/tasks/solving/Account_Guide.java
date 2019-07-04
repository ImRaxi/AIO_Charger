package com.aio.tutorialIsland.tasks.solving;

import com.aio.tutorialIsland.TutorialIsland;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

public class Account_Guide extends Task {
    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Varps.get(281) >= 525 && Varps.get(281) <= 540;

    }

    @Override
    public int execute() {
        if (Varps.get(281) == 525) {
            SceneObject door = SceneObjects.getNearest(a -> a.getPosition().equals(new Position(3125, 3124))
                    && a.getName().contains("Door"));
            if (door != null) {
                TutorialIsland.status = "Opening door";
                door.interact("Open");
                Time.sleep(Random.nextInt(400, 600));
            }
        } else if (Varps.get(281) == 530 || Varps.get(281) == 532) {
            Npc ag = Npcs.getNearest("Account guide");
            if (ag != null) {
                TutorialIsland.status = "Talking with Account Guide";
                ag.interact("Talk-to");
                Time.sleep(Random.nextInt(400, 600));
            }
        } else if (Varps.get(281) == 531) {
            InterfaceComponent w = Interfaces.getComponent(164, 36);
            if (w != null && w.isVisible()) {
                TutorialIsland.status = "Opening account management";
                w.interact("Account Management");
                Time.sleep(Random.nextInt(400, 600));
            }
        } else if (Varps.get(281) == 540) {
            SceneObject door = SceneObjects.getNearest(a -> a.getPosition().equals(new Position(3130, 3124))
                    && a.getName().contains("Door"));
            if (door != null) {
                TutorialIsland.status = "Opening door";
                door.interact("Open");
                Time.sleep(Random.nextInt(400, 600));
            }
        }
        return Random.nextInt(300, 500);
    }
}
