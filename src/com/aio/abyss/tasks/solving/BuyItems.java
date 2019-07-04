package com.aio.abyss.tasks.solving;

import com.aio.abyss.Abyss;
import com.aio.abyss.tasks.enumData.Armor;
import com.aio.abyss.tasks.enumData.Weapon;
import com.aio.abyss.tasks.utility.AbyssGrandExchange;
import com.aio.abyss.tasks.utility.Statics;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

public class BuyItems extends Task {
    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Statics.buyLobster || Statics.sellWealth || Statics.buyWealth || Statics.buyBronzePickaxe || Statics.buyPureEssence || Statics.buyStaminPotion
                || Statics.sellGlory || Statics.buyGlory || Statics.buyWeapon || Statics.buyArmor || Statics.buyAddyPick || Statics.buyRuneChainbody || Statics.buyRunePlatelegs
                || Statics.buyAdamantChainbody || Statics.buyAdamantPlatelegs;
    }

    @Override
    public int execute() {
        if (Statics.buyLobster) {
            Abyss.status = "Buying lobsters";
            if (AbyssGrandExchange.buyAtGEIncrease(Statics.LOBSTER, 1000, 0)) {
                Statics.buyLobster = false;
            }
        } else if (Statics.sellWealth) {
            Abyss.status = "Selling Ring of wealth";
            if (AbyssGrandExchange.sellAtGe(Statics.RING_OF_WEALTH_EMPTY)) {
                Statics.sellWealth = false;
                Statics.buyWealth = true;
            }
        } else if (Statics.buyWealth) {
            Abyss.status = "Buying Ring of wealth";
            if (AbyssGrandExchange.buyAtGEIncrease(Statics.RING_OF_WEALTH_FULL, 1, 0)) {
                Statics.buyWealth = false;
            }
        } else if (Statics.buyBronzePickaxe) {
            if (AbyssGrandExchange.buyAtGESetPrice(Statics.BRONZE_PICKAXE, 1, 5000)) {
                Statics.buyBronzePickaxe = false;
                Time.sleep(Random.nextInt(1250, 1500));
            }
        } else if (Statics.buyPureEssence) {
            if (AbyssGrandExchange.buyAtGEIncrease(Statics.PURE_ESSENCE, 10000, 0)) {
                Statics.buyPureEssence = false;
            }
        } else if (Statics.buyStaminPotion) {
            if (AbyssGrandExchange.buyAtGEIncrease(Statics.STAMINA_POTION, 25, 0)) {
                Statics.buyStaminPotion = false;
            }
        } else if (Statics.sellGlory) {
            if (AbyssGrandExchange.sellNoted(Statics.AMULET_OF_GLORY_EMPTY)) {
                Statics.sellGlory = false;
            }
        } else if (Statics.buyGlory) {
            if (AbyssGrandExchange.buyAtGEIncrease(Statics.AMULET_OF_GLORY_FULL, 25, 0)) {
                Statics.buyGlory = false;
            }
        } else if (Statics.buyWeapon) {
            if (AbyssGrandExchange.buyAtGEIncrease(Weapon.POUCH.weapon(), 1, 0)) {
                Statics.buyWeapon = false;
            }
        } else if (Statics.buyArmor) {
            if (AbyssGrandExchange.buyAtGEIncrease(Armor.POUCH.armorPart(), 1, 0)) {
                Statics.buyArmor = false;
            }
        } else if (Statics.buyAddyPick) {
            if (AbyssGrandExchange.buyAtGESetPrice(Statics.ADAMANT_PICKAXE, 1, 10000)) {
                Statics.buyAddyPick = false;
            }
        } else if (Statics.buyRuneChainbody) {
            if (AbyssGrandExchange.buyAtGEIncrease(Statics.RUNE_CHAINBODY, 1, 0)) {
                Statics.buyRuneChainbody = false;
            }
        } else if (Statics.buyRunePlatelegs) {
            if (AbyssGrandExchange.buyAtGEIncrease(Statics.RUNE_PLATELEGS, 1, 0)) {
                Statics.buyRunePlatelegs = false;
            }
        } else if (Statics.buyAdamantChainbody) {
            if (AbyssGrandExchange.buyAtGEIncrease(Statics.ADAMANT_PLATEBODY, 1, 0)) {
                Statics.buyAdamantChainbody = false;
            }
        } else if (Statics.buyAdamantPlatelegs) {
            if (AbyssGrandExchange.buyAtGEIncrease(Statics.ADAMANT_PLATELEGS, 1, 0)) {
                Statics.buyAdamantPlatelegs = false;
            }
        }
        return Random.nextInt(500, 800);
    }
}
