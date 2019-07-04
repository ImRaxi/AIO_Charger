package com.aio.universalTraining.tasks.training;

import com.aio.abyss.tasks.utility.Areas;
import com.aio.universalTraining.UniversalTraining;
import com.aio.universalTraining.tasks.api.Api;
import com.aio.universalTraining.tasks.enums.Food;
import com.aio.universalTraining.tasks.enums.LevelCheck;
import com.aio.universalTraining.tasks.enums.SlayerMonsters;
import com.aio.universalTraining.tasks.utility.Statics;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;
import org.rspeer.runetek.api.local.Health;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

import java.util.Objects;

public class CompletingSlayerTask extends Task {

    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Skills.getCurrentLevel(Skill.SLAYER) < 70;
    }

    @Override
    public int execute() {
        Item food = Inventory.getFirst(a -> a.containsAction("Eat"));
        if (Statics.currentTaskName.equals("none") && Inventory.contains(Statics.ENCHANTED_GEM)) {
            UniversalTraining.status = "Checking for current slayer task via Enchanted gem";
            Item item = Inventory.getFirst(Statics.ENCHANTED_GEM);
            if (item != null) {
                item.interact("Check");
                Time.sleep(500);
            }
        } else if (Objects.requireNonNull(SlayerMonsters.getMonsterArea()).contains(me)) {
            if (Api.setCorrectAttackStyle()) {
                if (Health.getPercent() < 50) {
                    UniversalTraining.status = "Healing up";
                    if (food != null) {
                        food.interact("Eat");
                        return 750;
                    } else {
                        Api.teleportToGrandExchange();
                    }
                }

                if (me.getTarget() == null && !me.isAnimating()) {
                    if (Statics.slayerExperience != Skills.getExperience(Skill.SLAYER)) {
                        Statics.amountToKill -= 1;
                        Statics.slayerExperience = Skills.getExperience(Skill.SLAYER);
                    }

                    if (Random.nextInt(0, 100) > 90) {
                        UniversalTraining.status = "Checking for current slayer task via Enchanted gem";
                        Item item = Inventory.getFirst(Statics.ENCHANTED_GEM);
                        if (item != null) {
                            item.interact("Check");
                            Time.sleep(500);
                        }
                    }

                    UniversalTraining.status = "Searching for target and attacking";
                    Npc npc = Npcs.getNearest(a -> a.getName().toLowerCase().contains(SlayerMonsters.getNpcId()) && a.getTarget() == me && SlayerMonsters.getMonsterArea().contains(a));
                    if (npc == null) {
                        npc = Npcs.getNearest(a -> a.getName().toLowerCase().contains(SlayerMonsters.getNpcId()) && a.getTarget() == null && a.getAnimation() == -1 && SlayerMonsters.getMonsterArea().contains(a));
                    }
                    if (npc != null) {
                        npc.interact("Attack");
                        Time.sleepUntil(() -> me.getTarget() != null, 2000);
                    }
                }
            }
        } else if (Objects.requireNonNull(LevelCheck.getType()).containsALL()) {
            Item noted = Inventory.getFirst(Item::isNoted);
            if (Statics.depositAllItems && Bank.isOpen()) {
                UniversalTraining.status = "Depositing inventory";
                if (Bank.depositInventory()) {
                    Time.sleep(750);
                    Statics.depositAllItems = false;
                }
            } else if (noted != null) {
                UniversalTraining.status = "Depositing noted item";
                if (Bank.isOpen()) {
                    Bank.depositAll(Item::isNoted);
                    Time.sleep(750);
                } else {
                    Bank.open();
                    Time.sleepUntil(Bank::isOpen, 2000);
                }
            } else if (!Inventory.contains(Statics.ENCHANTED_GEM)) {
                UniversalTraining.status = "Withdrawing enchanted gem";
                if (Bank.isOpen()) {
                    if (Bank.contains(Statics.ENCHANTED_GEM)) {
                        Bank.withdraw(Statics.ENCHANTED_GEM, 1);
                        Time.sleep(750);
                    }
                } else {
                    Bank.open();
                    Time.sleepUntil(Bank::isOpen, 2000);
                }
            } else if (!Inventory.contains(Statics.GAMES_NECKLACE) && Areas.GRAND_EXCHANGE.contains(me)) {
                UniversalTraining.status = "Getting Games necklace";
                if (Bank.isOpen()) {
                    if (Bank.contains(Statics.GAMES_NECKLACE)) {
                        Bank.withdraw(a -> a.getName().matches(Statics.GAMES_NECKLACE.pattern()), 1);
                        Time.sleep(750);
                    } else {
                        Statics.buyGamesNecklace = true;
                    }
                } else {
                    Bank.open();
                    Time.sleepUntil(Bank::isOpen, 2000);
                }
            } else if (food == null) {
                UniversalTraining.status = "Getting food";
                if (Bank.isOpen()) {
                    if (Bank.contains(Food.getBestFood())) {
                        Bank.withdraw(Food.getBestFood(), 15);
                        Time.sleep(750);
                    } else {
                        Statics.buyFood = true;
                    }
                } else if (Areas.GRAND_EXCHANGE.contains(me)) {
                    Bank.open();
                    Time.sleepUntil(Bank::isOpen, 2000);
                } else {
                    Api.teleportToGrandExchange();
                }
            } else {
                SlayerMonsters.getToSpot();
            }
        } else if (Inventory.contains(Statics.equipmentToGet)) {
            UniversalTraining.status = "Gearing up";
            Item item = Inventory.getFirst(a -> a.getId() == Statics.equipmentToGet && (a.containsAction("Wield") || a.containsAction("Wear")));
            if (item != null) {
                item.interact(ActionOpcodes.ITEM_ACTION_1);
                Time.sleep(750);
            }
        } else if (Bank.isOpen()) {
            UniversalTraining.status = "Withdrawing required equipment";
            if (Bank.contains(Statics.equipmentToGet)) {
                Bank.withdraw(Statics.equipmentToGet, 1);
                Time.sleep(750);
            } else {
                Statics.buyEquipmentpart = true;
            }
        } else if (Areas.GRAND_EXCHANGE.contains(me)) {
            UniversalTraining.status = "Opening bank";
            Bank.open();
            Time.sleepUntil(Bank::isOpen, 2000);
        } else {
            Api.teleportToGrandExchange();
        }
        return 500;
    }
}
