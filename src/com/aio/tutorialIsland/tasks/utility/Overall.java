package com.aio.tutorialIsland.tasks.utility;

import com.aio.tutorialIsland.TutorialIsland;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

public class Overall extends Task {

    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Interfaces.getContinue() != null;
    }

    @Override
    public int execute() {
        if (Interfaces.getContinue() != null) {
            TutorialIsland.status = "Chat continuation.";
            Interfaces.getContinue().interact("Continiue");
            Time.sleep(Random.nextInt(650, 750));
        }
        return Random.nextInt(300, 500);
    }
}
