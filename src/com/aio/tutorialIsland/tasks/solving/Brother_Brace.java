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
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

public class Brother_Brace extends Task {
    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Varps.get(281) >= 550 && Varps.get(281) <= 610;

    }

    @Override
    public int execute() {
        if (Varps.get(281) == 550 || Varps.get(281) == 570 || Varps.get(281) == 600) {
            Npc bb = Npcs.getNearest("Brother Brace");
            if (bb != null) {
                TutorialIsland.status = "Talking with Brother Brace";
                bb.interact("Talk-to");
                Time.sleep(Random.nextInt(400, 600));
            } else {
                TutorialIsland.status = "Walking to Brother Brace";
                Movement.walkTo(new Position(3126, 3106));
                Time.sleep(Random.nextInt(900, 1100));
            }
        } else if (Varps.get(281) == 560) {
            InterfaceComponent w = Interfaces.getComponent(164, 55);
            if (w != null && w.isVisible()) {
                TutorialIsland.status = "Opening prayer";
                w.interact("Prayer");
                Time.sleep(Random.nextInt(400, 600));
            }
        } else if (Varps.get(281) == 580) {
            InterfaceComponent w = Interfaces.getComponent(164, 37);
            if (w != null && w.isVisible()) {
                TutorialIsland.status = "Opening friends list";
                w.interact("Friends list");
                Time.sleep(Random.nextInt(400, 600));
            }
        } else if (Varps.get(281) == 610) {
            SceneObject door = SceneObjects.getNearest(a -> a.getPosition().equals(new Position(3122, 3102))
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
