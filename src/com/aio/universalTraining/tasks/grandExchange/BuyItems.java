package com.aio.universalTraining.tasks.grandExchange;

import com.aio.abyss.tasks.utility.Areas;
import com.aio.universalTraining.UniversalTraining;
import com.aio.universalTraining.tasks.api.Api;
import com.aio.universalTraining.tasks.enums.Food;
import com.aio.universalTraining.tasks.utility.Statics;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

public class BuyItems extends Task {

    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && (Statics.buyGlory || Statics.buyWealth || Statics.buyEquipmentpart || Statics.buyFood || Statics.buyGamesNecklace || Statics.buyLumbridgeTeleport
                || Statics.buyRingOfDueling);
    }

    @Override
    public int execute() {
        if (!Inventory.contains(Statics.COINS)) {
            UniversalTraining.status = "Withdraw coins";
            if (Bank.isOpen() && Bank.contains(Statics.COINS)) {
                Bank.withdrawAll(Statics.COINS);
                Time.sleep(800);
            } else if (Areas.GRAND_EXCHANGE.contains(me)) {
                Bank.open();
                Time.sleepUntil(Bank::isOpen, 2000);
            } else {
                Api.teleportToGrandExchange();
            }
        } else if (Statics.buyGlory) {
            UniversalTraining.status = "Buying Amulet of glory";
            if (Api.buy(Statics.AMULET_OF_GLORY_FULL, 20000, 1)) {
                Statics.buyGlory = false;
            }
        } else if (Statics.buyWealth) {
            UniversalTraining.status = "Buying Ring of wealth";
            if (Api.buy(Statics.RING_OF_WEALTH_FULL, 20000, 1)) {
                Statics.buyWealth = false;
            }
        } else if (Statics.buyEquipmentpart) {
            UniversalTraining.status = "Buying " + Statics.equipmentToGet + " Equipment part";
            if (Api.buy(Statics.equipmentToGet, 50000, 1)) {
                Statics.buyEquipmentpart = false;
                Statics.equipmentToGet = -1;
            }
        } else if (Statics.buyFood) {
            UniversalTraining.status = "Buying Food";
            if (Api.buy(Food.getBestFood(), 1500, 200)) {
                Statics.buyFood = false;
            }
        } else if (Statics.buyGamesNecklace) {
            UniversalTraining.status = "Buying Games necklace";
            if (Api.buy(Statics.GAMES_NECKLACE_FULL, 10000, 1)) {
                Statics.buyGamesNecklace = false;
            }
        } else if (Statics.buyLumbridgeTeleport) {
            UniversalTraining.status = "Buying Lumbridge Teleport";
            if (Api.buy(Statics.LUMBRIDGE_TELEPORT, 5000, 1)) {
                Statics.buyLumbridgeTeleport = false;
            }
        } else if (Statics.buyRingOfDueling) {
            UniversalTraining.status = "Buying Ring Of Dueling";
            if (Api.buy(Statics.RING_OF_DUELING_FULL, 5000, 1)) {
                Statics.buyRingOfDueling = false;
            }
        }
        return 500;
    }
}
