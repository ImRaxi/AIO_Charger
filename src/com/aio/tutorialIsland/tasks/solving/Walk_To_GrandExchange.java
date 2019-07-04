package com.aio.tutorialIsland.tasks.solving;

import com.aio.tutorialIsland.tasks.utility.Areas;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.component.tab.Tabs;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

public class Walk_To_GrandExchange extends Task {
    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null;

    }

    @Override
    public int execute() {
        if (!Areas.GRAND_EXCHANGE.contains(me)) {
            while (me.isMoving() && !Inventory.isEmpty()) {
                if (Tab.INVENTORY.isOpen()) {
                    Item item = Inventory.getFirst(a -> a.containsAction("Drop"));
                    if (item != null) {
                        item.interact("Drop");
                        Time.sleep(500);
                    }
                } else {
                    Tabs.open(Tab.INVENTORY);
                    Time.sleepUntil(Tab.INVENTORY::isOpen, Random.nextInt(400, 600));
                }
            }
            Movement.walkTo(new Position(3187, 3489, 0));
            Time.sleepUntil(() -> Areas.GRAND_EXCHANGE.contains(me), Random.nextInt(1000, 2000));
        }

        return Random.nextInt(300, 500);
    }
}
