package com.aio.tutorialIsland.tasks.solving;

import com.aio.tutorialIsland.TutorialIsland;
import com.aio.tutorialIsland.tasks.utility.Statics;
import com.api.API;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

public class Master_Chef extends Task {

    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Varps.get(281) >= 120 && Varps.get(281) <= 160;
    }

    @Override
    public int execute() {
        if (Varps.get(281) == 120) {
            SceneObject gate = SceneObjects.getNearest(a -> a.getId() == 9470 && a.getPosition().equals(new Position(3089, 3092)));
            if (gate != null) {
                TutorialIsland.status = "Opening gate.";
                gate.interact("Open");
                Time.sleep(Random.nextInt(1200, 1400));
            }
        } else if (Varps.get(281) == 130) {
            SceneObject door = SceneObjects.getNearest(a -> a.getId() == 9709 && a.getPosition().equals(new Position(3079, 3084)));
            if (door != null) {
                TutorialIsland.status = "Opening door.";
                door.interact("Open");
                Time.sleep(Random.nextInt(1200, 1400));
            }
        } else if (Varps.get(281) == 140) {
            Npc npc = Npcs.getNearest(Statics.MASTER_CHEF);
            if (npc != null) {
                TutorialIsland.status = "Talking with Master Chef.";
                npc.interact("Talk-to");
                Time.sleep(Random.nextInt(400, 600));
            }
        } else if (Varps.get(281) == 150) {
            TutorialIsland.status = "Making bread dough.";
            Item flour = Inventory.getFirst(Statics.POT_OF_FLOUR);
            Inventory.use(a -> a.getId() == Statics.BUCKET_OF_WATER, flour);
            Time.sleep(300);
        } else if (Varps.get(281) == 160) {
            Item item = Inventory.getFirst(Statics.BREAD_DOUGH);
            TutorialIsland.status = "Baking bread.";
            API.useItemOnObject(item, "Range");
            Time.sleepUntil(() -> !Inventory.contains(Statics.BREAD_DOUGH), 5000);
        }
        return Random.nextInt(300, 500);
    }
}
