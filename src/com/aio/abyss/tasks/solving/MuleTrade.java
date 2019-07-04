package com.aio.abyss.tasks.solving;

import com.aio.abyss.Abyss;
import com.aio.abyss.tasks.utility.Areas;
import com.aio.abyss.tasks.utility.Statics;
import com.api.API;
import com.api.HopWorld;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Worlds;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Trade;
import org.rspeer.runetek.api.component.tab.Equipment;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

public class MuleTrade extends Task {

    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Statics.tradeLootMule && (Areas.EDGEVILLE.contains(me) || Areas.GRAND_EXCHANGE.contains(me));
    }

    @Override
    public int execute() {
        if (Areas.EDGEVILLE.contains(me)) {
            if (Worlds.getCurrent() != 318) {
                HopWorld.hopTo(18);
            } else if (Inventory.contains(Statics.NATURE_RUNE) && Inventory.contains(Statics.COINS) || Trade.isOpen()) {
                if (API.closeInterfaces()) {
                    if (Trade.isOpen(true)) {
                        if (Trade.getTheirItems(a -> a.getId() == Statics.COINS) != null) {
                            if (Trade.hasOtherAccepted()) {
                                Abyss.status = "Accept trade";
                                Trade.accept();
                                Time.sleep(Random.nextInt(2000, 3000));
                                Statics.tradeLootMule = false;
                                Statics.badWorld = Worlds.getCurrent();
                                Statics.hopWorld = true;
                            }
                        }
                    } else if (Trade.isOpen(false)) {
                        if (!Trade.contains(true, Statics.COINS)) {
                            Abyss.status = "Offering coins";
                            Trade.offerAll(Statics.COINS);
                            Time.sleep(Random.nextInt(750, 1000));
                        } else if (!Trade.contains(true, Statics.NATURE_RUNE)) {
                            Abyss.status = "Offering nature runes";
                            Trade.offerAll(Statics.NATURE_RUNE);
                            Time.sleep(Random.nextInt(750, 1000));
                        } else if (Trade.contains(false, Statics.COINS)) {
                            if (Trade.hasOtherAccepted()) {
                                Abyss.status = "Accept trade";
                                Trade.accept();
                                Time.sleep(Random.nextInt(1000, 1500));
                            }
                        }
                    } else {
                        Abyss.status = "Trading with mule";
                        Player mule = Players.getNearest(a -> a.getName().toLowerCase().equals(Abyss.muleName.toLowerCase()));
                        if (mule != null) {
                            if (API.closeInterfaces()) {
                                mule.interact("Trade with");
                                Time.sleepUntil(() -> Trade.isOpen(false), 10000, 15000);
                            }
                        }
                    }
                }
            } else if (API.openBank()) {
                if (!Inventory.isFull()) {
                    if (Bank.contains(Statics.NATURE_RUNE)) {
                        Abyss.status = "Withdraw all nature runes";
                        Bank.withdrawAll(Statics.NATURE_RUNE);
                        Time.sleep(Random.nextInt(400, 500));
                    } else if (Bank.contains(Statics.COINS)) {
                        Abyss.status = "Withdraw all coins";
                        Bank.withdrawAll(Statics.COINS);
                        Time.sleep(Random.nextInt(400, 500));
                    }
                } else {
                    Bank.depositInventory();
                    Time.sleep(Random.nextInt(400, 500));
                }
            }
        } else if (API.closeInterfaces() && API.openTab(Tab.EQUIPMENT)) {
            Abyss.status = "Teleporting to Edgeville";
            Equipment.interact(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), "Edgeville");
            Time.sleepUntil(() -> !me.isAnimating() || Areas.EDGEVILLE.contains(me), 4500);
        }
        return Random.nextInt(400, 500);
    }
}
