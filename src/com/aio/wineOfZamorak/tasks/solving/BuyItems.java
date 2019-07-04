package com.aio.wineOfZamorak.tasks.solving;

import com.aio.wineOfZamorak.tasks.utility.Statics;
import com.api.personalGrandExchange.PersonalGrandExchange;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

public class BuyItems extends Task {

    @Override
    public boolean validate() {
        Player me = Players.getLocal();
        return me != null && Statics.buyStaffOfAir || Statics.buyAmuletOfGlory || Statics.sellRingOfWealth || Statics.buyRingOfWealth || Statics.buyMindRunes
                || Statics.buyFireRunes || Statics.buyLawRunes || Statics.buyBurningAmulet || Statics.buyLobster;
    }

    @Override
    public int execute() {
        if (Statics.buyStaffOfAir) {
            if (PersonalGrandExchange.buyAtGESetPrice(Statics.STAFF_OF_AIR, 1, 5000)) {
                Statics.buyStaffOfAir = false;
            }
        } else if (Statics.buyAmuletOfGlory) {
            if (PersonalGrandExchange.buyAtGEIncrease(Statics.AMULET_OF_GLORY_FULL, 25, 3)) {
                Statics.buyAmuletOfGlory = false;
            }
        } else if (Statics.sellRingOfWealth) {
            if (PersonalGrandExchange.sellAtGe(Statics.RING_OF_WEALTH_EMPTY)) {
                Statics.sellRingOfWealth = false;
                Statics.buyRingOfWealth = true;
            }
        } else if (Statics.buyRingOfWealth) {
            if (PersonalGrandExchange.buyAtGEIncrease(Statics.RING_OF_WEALTH_FULL, 1, 3)) {
                Statics.buyRingOfWealth = false;
            }
        } else if (Statics.buyMindRunes) {
            if (PersonalGrandExchange.buyAtGEIncrease(Statics.MIND_RUNE, 1000, 5)) {
                Statics.buyMindRunes = false;
            }
        } else if (Statics.buyFireRunes) {
            if (PersonalGrandExchange.buyAtGEIncrease(Statics.FIRE_RUNE, 2000, 5)) {
                Statics.buyFireRunes = false;
            }
        } else if (Statics.buyLawRunes) {
            if (PersonalGrandExchange.buyAtGEIncrease(Statics.LAW_RUNE, 500, 5)) {
                Statics.buyLawRunes = false;
            }
        } else if (Statics.buyBurningAmulet) {
            if (PersonalGrandExchange.buyAtGEIncrease(Statics.BURNING_AMULET_FULL, 25, 5)) {
                Statics.buyBurningAmulet = false;
            }
        } else if (Statics.buyLobster) {
            if (PersonalGrandExchange.buyAtGEIncrease(Statics.LOBSTER, 100, 5)) {
                Statics.buyLobster = false;
            }
        }
        return Random.nextInt(300, 500);
    }
}
