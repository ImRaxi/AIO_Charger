package com.aio.tutorialIsland.tasks.solving;

import com.aio.tutorialIsland.TutorialIsland;
import com.aio.tutorialIsland.tasks.utility.Statics;
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

public class Quest_Guide extends Task {

    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Varps.get(281) >= 170 && Varps.get(281) <= 240;
    }

    @Override
    public int execute() {
        if (Varps.get(281) == 170) {
            SceneObject door = SceneObjects.getNearest(a -> a.getId() == 9710 && a.getPosition().equals(new Position(3072, 3090)));
            if (door != null) {
                TutorialIsland.status = "Opening door.";
                door.interact("Open");
                Time.sleep(Random.nextInt(1200, 1400));
            }
        } else if (Varps.get(281) == 200) {
            InterfaceComponent run = Interfaces.getComponent(160, 22);
            if (run != null && run.isVisible()) {
                TutorialIsland.status = "Toggling run.";
                run.interact("Toggle Run");
                Time.sleep(300);
            }
        } else if (Varps.get(281) == 210) {
            SceneObject door = SceneObjects.getNearest(a -> a.getId() == 9716 && a.getPosition().equals(new Position(3086, 3126)));
            if (door != null) {
                TutorialIsland.status = "Opening door.";
                door.interact("Open");
                Time.sleep(Random.nextInt(1200, 1400));
            } else {
                TutorialIsland.status = "Walking to Quest Guide.";
                Movement.walkTo(new Position(3084, 3126));
                Time.sleep(Random.nextInt(700, 800));
            }
        } else if (Varps.get(281) == 220 || Varps.get(281) == 240) {
            Npc guide = Npcs.getNearest(Statics.QUEST_GUIDE);
            if (guide != null) {
                TutorialIsland.status = "Talking with Quest Guide.";
                guide.interact("Talk-to");
                Time.sleep(Random.nextInt(400, 600));
            }
        } else if (Varps.get(281) == 230) {
            InterfaceComponent w = Interfaces.getComponent(164, 52);
            if (w != null && w.isVisible()) {
                TutorialIsland.status = "Opening Quest List";
                w.interact("Quest List");
            }
        }
        return Random.nextInt(300, 500);
    }
}
