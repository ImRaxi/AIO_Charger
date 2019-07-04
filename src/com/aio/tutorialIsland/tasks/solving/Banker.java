package com.aio.tutorialIsland.tasks.solving;

import com.aio.tutorialIsland.TutorialIsland;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

public class Banker extends Task {

    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Varps.get(281) >= 510 && Varps.get(281) <= 520;

    }

    @Override
    public int execute() {
        if (Varps.get(281) == 510) {
            SceneObject bank = SceneObjects.getNearest("Bank booth");
            if (bank != null) {
                TutorialIsland.status = "Using bank booth";
                bank.interact("Use");
                Time.sleep(Random.nextInt(400, 600));
            }
        } else if (Varps.get(281) == 520) {
            SceneObject poll = SceneObjects.getNearest("Poll booth");
            if (poll != null) {
                TutorialIsland.status = "Using poll booth";
                poll.interact("Use");
                Time.sleep(Random.nextInt(400, 600));
            }
        }
        return Random.nextInt(300, 500);
    }
}
