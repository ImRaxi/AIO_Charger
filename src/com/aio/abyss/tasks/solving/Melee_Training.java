package com.aio.abyss.tasks.solving;

import com.aio.abyss.Abyss;
import com.aio.abyss.tasks.enumData.Armor;
import com.aio.abyss.tasks.enumData.AttackStyle;
import com.aio.abyss.tasks.enumData.Weapon;
import com.aio.abyss.tasks.utility.AbyssGrandExchange;
import com.aio.abyss.tasks.utility.Areas;
import com.aio.abyss.tasks.utility.Statics;
import com.api.API;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.*;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;
import org.rspeer.runetek.api.local.Health;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

public class Melee_Training extends Task {
    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Skills.getCurrentLevel(Skill.DEFENCE) < 40;
    }

    @Override
    public int execute() {
        Armor requiredArmor = Armor.requiredArmor();
        Weapon requiredWeapon = Weapon.requiredweapon();
        if (!requiredWeapon.hasWielded()) {
            if (requiredWeapon.inventoryContains()) {
                Abyss.status = "Wearing Weapon";
                requiredWeapon.wearWeapon();
            } else if (requiredWeapon.weapon() == Statics.RUNE_SCIMITAR) {
                AbyssGrandExchange.buyAtGEIncrease(requiredWeapon.weapon(), 1, 0);
            } else {
                AbyssGrandExchange.buyAtGESetPrice(requiredWeapon.weapon(), 1, 5000);
            }
        } else if (!requiredArmor.hasWieldedAll()) {
            if (requiredArmor.inventoryContainsMissingItem()) {
                Abyss.status = "Wearing Armor";
                requiredArmor.wearMissingItem();
                Time.sleep(Random.nextInt(400, 500));
            } else {
                Abyss.status = "Buying Armor";
                AbyssGrandExchange.buyAtGESetPrice(requiredArmor.armorPart(), 1, 5000);
            }
        } else if (!Equipment.contains(Statics.COMBAT_BRACELET_PATTERN)) {
            if (Inventory.contains(Statics.COMBAT_BRACELET_PATTERN)) {
                Item item = Inventory.getFirst(a -> a.getName().matches(Statics.COMBAT_BRACELET_PATTERN.pattern()));
                if (item != null) {
                    if (API.closeInterfaces()) {
                        Abyss.status = "Wear Combat Bracelet";
                        if (API.openTab(Tab.INVENTORY)) {
                            item.interact(ActionOpcodes.ITEM_ACTION_1);
                        }
                    }
                }
            } else {
                Abyss.status = "Buying Combat Bracelet";
                AbyssGrandExchange.buyAtGEIncrease(Statics.COMBAT_BRACELET_FULL, 1, 0);
            }
        } else if (!Equipment.contains(Statics.AMULET_OF_GLORY_PATTERN)) {
            if (Inventory.contains(Statics.AMULET_OF_GLORY_PATTERN)) {
                Item item = Inventory.getFirst(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()));
                if (item != null) {
                    if (API.closeInterfaces()) {
                        Abyss.status = "Wear Amulet of glory";
                        if (API.openTab(Tab.INVENTORY)) {
                            item.interact(ActionOpcodes.ITEM_ACTION_1);
                        }
                    }
                }
            } else {
                Abyss.status = "Buying Amulet of glory";
                AbyssGrandExchange.buyAtGEIncrease(Statics.AMULET_OF_GLORY_FULL, 1, 0);
            }
        } else if (!Equipment.contains(Statics.RING_OF_WEALTH_PATTERN)) {
            if (Inventory.contains(Statics.RING_OF_WEALTH_PATTERN)) {
                Item item = Inventory.getFirst(a -> a.getName().matches(Statics.RING_OF_WEALTH_PATTERN.pattern()));
                if (item != null) {
                    if (API.closeInterfaces()) {
                        Abyss.status = "Wear Ring of wealth";
                        if (API.openTab(Tab.INVENTORY)) {
                            item.interact(ActionOpcodes.ITEM_ACTION_1);
                        }
                    }
                }
            } else {
                Abyss.status = "Buying Ring of wealth";
                AbyssGrandExchange.buyAtGEIncrease(Statics.RING_OF_WEALTH_FULL, 1, 0);
            }
        } else if (!Inventory.contains(Statics.LOBSTER)) {
            if (API.openBank()) {
                if (Inventory.contains(API.notedItem(Statics.LOBSTER))) {
                    Bank.depositAll(API.notedItem(Statics.LOBSTER));
                    Time.sleep(Random.nextInt(300, 350));
                } else if (Bank.contains(Statics.LOBSTER)) {
                    Abyss.status = "Withdrawing lobsters";
                    Bank.withdraw(Statics.LOBSTER, 25);
                    Time.sleep(Random.nextInt(400, 700));
                } else {
                    Statics.buyLobster = true;
                }
            }
        } else if (Inventory.contains(Statics.COINS)) {
            if (API.openBank()) {
                Abyss.status = "Deposit all coins";
                Bank.depositAll(Statics.COINS);
                Time.sleep(Random.nextInt(300, 350));
            }
        } else if (Areas.MONASTERY.contains(me)) {
            if (Skills.getCurrentLevel(Skill.ATTACK) == Skills.getCurrentLevel(Skill.STRENGTH) && Skills.getCurrentLevel(Skill.STRENGTH) == Skills.getCurrentLevel(Skill.DEFENCE)
                    && AttackStyle.getCurrentStyle() != AttackStyle.ATTACK) {
                AttackStyle.changeStyleTo(AttackStyle.ATTACK);
            } else if (Skills.getCurrentLevel(Skill.ATTACK) - Skills.getCurrentLevel(Skill.STRENGTH) >= 10 && AttackStyle.getCurrentStyle() != AttackStyle.STRENGTH) {
                AttackStyle.changeStyleTo(AttackStyle.STRENGTH);
            } else if (Skills.getCurrentLevel(Skill.STRENGTH) - Skills.getCurrentLevel(Skill.DEFENCE) >= 10 && AttackStyle.getCurrentStyle() != AttackStyle.DEFENCE) {
                AttackStyle.changeStyleTo(AttackStyle.DEFENCE);
            } else if (Health.getPercent() < 30) {
                Item item = Inventory.getFirst(a -> a.containsAction("Eat"));
                if (item != null) {
                    Abyss.status = "Eating food";
                    if (API.openTab(Tab.INVENTORY)) {
                        item.interact("Eat");
                    }
                    Time.sleep(Random.nextInt(650, 800));
                }
            } else if (me.getTarget() == null) {
                if (Players.getLoaded().length > 1) {
                    Statics.hopWorld = true;
                } else {
                    Abyss.status = "Attacking Monk";
                    Npc npc = Npcs.getNearest(a -> a.getId() == 2579 && a.getTarget() == me);
                    if (npc == null) {
                        npc = Npcs.getNearest(a -> a.getId() == 2579 && a.getTarget() == null && a.getAnimation() == -1);
                    }
                    if (npc != null) {
                        npc.interact("Attack");
                        Time.sleepUntil(() -> me.getTarget() == null || Health.getPercent() < 30, 15000);
                    }
                }
            }
        } else {
            if (API.closeInterfaces() && API.openTab(Tab.EQUIPMENT)) {
                Abyss.status = "Teleporting to Monastery";
                if (API.closeInterfaces() && API.openTab(Tab.EQUIPMENT)) {
                    Equipment.interact(a -> a.getName().matches(Statics.COMBAT_BRACELET_PATTERN.pattern()), "Monastery");
                    Time.sleepUntil(() -> me.getAnimation() == -1 || Areas.MONASTERY.contains(me), 4500);
                }
            }
        }
        return Random.nextInt(500, 800);
    }
}
