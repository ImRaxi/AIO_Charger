package com.aio.universalTraining.tasks.basicItems;

import com.aio.universalTraining.UniversalTraining;
import com.aio.universalTraining.tasks.api.Api;
import com.aio.universalTraining.tasks.utility.Statics;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Equipment;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class GetBasicItems extends Task {

    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && (!Equipment.contains(Statics.AMULET_OF_GLORY) || !Equipment.contains(Statics.RING_OF_WEALTH));
    }

    @Override
    public int execute() {
        if (!Equipment.contains(Statics.AMULET_OF_GLORY) && Inventory.contains(Statics.AMULET_OF_GLORY)) {
            UniversalTraining.status = "Wearing Amulet of glory";
            Item item = Inventory.getFirst(a -> a.getName().matches(Statics.AMULET_OF_GLORY.pattern()));
            if (item != null && Api.closeInterfaces()) {
                item.interact(ActionOpcodes.ITEM_ACTION_1);
                Time.sleep(750);
            }
        } else if (!Equipment.contains(Statics.RING_OF_WEALTH) && Inventory.contains(Statics.RING_OF_WEALTH)) {
            UniversalTraining.status = "Wearing ring of wealth";
            Item item = Inventory.getFirst(a -> a.getName().matches(Statics.RING_OF_WEALTH.toString()));
            Log.info("Here");
            if (item != null && Api.closeInterfaces()) {
                item.interact(ActionOpcodes.ITEM_ACTION_1);
                Time.sleep(750);
            }
        } else if (Bank.isOpen()) {
            UniversalTraining.status = "Searching for Amulet of glory";
            if (!Inventory.contains(Statics.AMULET_OF_GLORY) && !Equipment.contains(Statics.AMULET_OF_GLORY)) {
                if (Bank.contains(a -> a.getName().matches(Statics.AMULET_OF_GLORY.pattern()))) {
                    Bank.withdraw(a -> a.getName().matches(Statics.AMULET_OF_GLORY.pattern()), 1);
                    Time.sleep(500);
                } else {
                    Statics.buyGlory = true;
                }
            } else if (!Inventory.contains(Statics.RING_OF_WEALTH) && !Equipment.contains(Statics.RING_OF_WEALTH)) {
                UniversalTraining.status = "Searching for Ring of wealth";
                if (Bank.contains(a -> a.getName().matches(Statics.RING_OF_WEALTH.pattern()))) {
                    Bank.withdraw(a -> a.getName().matches(Statics.RING_OF_WEALTH.pattern()), 1);
                    Time.sleep(500);
                } else {
                    Statics.buyWealth = true;
                }
            }
        } else {
            UniversalTraining.status = "Opening bank";
            Bank.open();
            Time.sleepUntil(Bank::isOpen, 2000);
        }

        return 500;
    }
}
