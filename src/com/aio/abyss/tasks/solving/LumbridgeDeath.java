package com.aio.abyss.tasks.solving;

import com.aio.abyss.Abyss;
import com.aio.abyss.tasks.utility.Areas;
import com.aio.abyss.tasks.utility.Statics;
import com.api.API;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Equipment;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

public class LumbridgeDeath extends Task {

    private Player me;

    @Override
    public int execute() {
        if (Areas.LUMBRIDGE_CASTLE_FLOOR2.contains(me)) {
            if (Equipment.contains(Statics.RING_OF_WEALTH_PATTERN)) {
                Abyss.status = "Teleport to Grand Exchange";
                if (API.openTab(Tab.EQUIPMENT)) {
                    Equipment.interact(a -> a.getName().matches(Statics.RING_OF_WEALTH_PATTERN.pattern()), "Grand Exchange");
                    Time.sleepUntil(() -> !me.isAnimating() || Areas.GRAND_EXCHANGE.contains(me), 4500);
                }
            } else if (Equipment.contains(Statics.AMULET_OF_GLORY_PATTERN)) {
                Abyss.status = "Teleporting to Edgeville";
                if (API.openTab(Tab.EQUIPMENT)) {
                    Equipment.interact(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), "Edgeville");
                    Time.sleepUntil(() -> !me.isAnimating() || Areas.EDGEVILLE.contains(me), 4500);
                }
            } else if (Inventory.contains(Statics.AMULET_OF_GLORY_PATTERN)) {
                if (API.closeInterfaces()) {
                    Item item = Inventory.getFirst(Statics.AMULET_OF_GLORY_PATTERN);
                    if (item != null) {
                        item.interact("Wear");
                        Time.sleep(Random.nextInt(275, 325));
                    }
                }
            } else if (Inventory.contains(Statics.RING_OF_WEALTH_PATTERN)) {
                if (API.closeInterfaces()) {
                    Item item = Inventory.getFirst(Statics.RING_OF_WEALTH_PATTERN);
                    if (item != null) {
                        item.interact("Wear");
                        Time.sleep(Random.nextInt(275, 325));
                    }
                }
            } else if (API.openBank()) {
                if (Bank.contains(Statics.AMULET_OF_GLORY_PATTERN)) {
                    Abyss.status = "Withdraw Glory";
                    Bank.withdraw(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), 1);
                    Time.sleep(Random.nextInt(400, 600));
                } else if (Bank.contains(Statics.RING_OF_WEALTH_PATTERN)) {
                    Abyss.status = "Withdraw Wealth";
                    Bank.withdraw(a -> a.getName().matches(Statics.RING_OF_WEALTH_PATTERN.pattern()), 1);
                    Time.sleep(Random.nextInt(400, 600));
                }
            }
        } else {
            Movement.walkTo(new Position(3207, 3221, 2));
            Time.sleep(Random.nextInt(1750, 2250));
        }
        return Random.nextInt(300, 500);
    }

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && (Areas.LUMBRIDGE_CASTLE.contains(me) || Areas.LUMBRIDGE_CASTLE_FLOOR1.contains(me) || Areas.LUMBRIDGE_CASTLE_FLOOR2.contains(me)) && Varps.get(63) > 5;
    }
}
