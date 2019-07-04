package com.aio.universalTraining.tasks.enums;

import com.aio.universalTraining.tasks.api.Api;
import com.aio.universalTraining.tasks.utility.Areas;
import com.aio.universalTraining.tasks.utility.Statics;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Distance;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;

public enum SlayerMonsters {
    SPIDER("spiders", "spider", Area.rectangular(3239, 3244, 3259, 3227)),
    COW("cows", "cow", Area.polygonal(0, new Position(3242, 3298), new Position(3242, 3298),
            new Position(3254, 3273), new Position(3253, 3255), new Position(3265, 3255), new Position(3265, 3297))),
    SCORION("scorpions", "scorpion", Area.rectangular(3292, 3315, 3305, 3276));

    private String name;
    private String id;
    private Area area;

    SlayerMonsters(String name, String id, Area area) {
        this.name = name;
        this.id = id;
        this.area = area;
    }

    public static Area getMonsterArea() {
        for (SlayerMonsters slayerMonsters : SlayerMonsters.values()) {
            if (Statics.currentTaskName.equals(slayerMonsters.name)) {
                return slayerMonsters.area;
            }
        }
        return null;
    }

    public static String getNpcId() {
        for (SlayerMonsters slayerMonsters : SlayerMonsters.values()) {
            if (Statics.currentTaskName.equals(slayerMonsters.name)) {
                return slayerMonsters.id;
            }
        }
        return "";
    }

    public static void getToSpot() {
        if (Statics.currentTaskName.equals("spiders") && !SPIDER.area.contains(Players.getLocal())) {
            getToSpiders();
        } else if (Statics.currentTaskName.equals("cows") && !COW.area.contains(Players.getLocal())) {
            getToCows();
        } else if (Statics.currentTaskName.equals("scorpions") && !SCORION.area.contains(Players.getLocal())) {
            getToScorpions();
        }
    }

    private static void getToSpiders() {
        if (Distance.between(Players.getLocal(), SPIDER.area.getCenter()) < 100 && Players.getLocal() != null) {
            Movement.walkTo(SPIDER.area.getCenter());
            Time.sleepUntil(() -> SPIDER.area.contains(Players.getLocal()), 2000);
        } else {
            if (Inventory.contains(Statics.COINS)) {
                if (Bank.isOpen()) {
                    Bank.depositAll(Statics.COINS);
                    Time.sleep(750);
                } else {
                    Bank.open();
                    Time.sleepUntil(Bank::isOpen, 2000);
                }
            } else if (Inventory.contains(Statics.LUMBRIDGE_TELEPORT)) {
                Item item = Inventory.getFirst(Statics.LUMBRIDGE_TELEPORT);
                if (item != null) {
                    item.interact("Break");
                    Time.sleep(5000);
                }
            } else {
                Statics.buyLumbridgeTeleport = true;
            }
        }
    }

    private static void getToCows() {
        if (Distance.between(Players.getLocal(), COW.area.getCenter()) < 125 && Players.getLocal() != null) {
            if (Players.getLocal().getPosition().getX() < 3245) {
                Movement.walkTo(new Position(3250, 3226));
                Time.sleep(2000);
            } else {
                Movement.walkTo(new Position(3263, 3256));
                Time.sleepUntil(() -> COW.area.contains(Players.getLocal()), 2000);
            }
        } else {
            if (Inventory.contains(Statics.COINS)) {
                if (Bank.isOpen()) {
                    Bank.depositAll(Statics.COINS);
                    Time.sleep(750);
                } else {
                    Bank.open();
                    Time.sleepUntil(Bank::isOpen, 2000);
                }
            } else if (Inventory.contains(Statics.LUMBRIDGE_TELEPORT)) {
                Item item = Inventory.getFirst(Statics.LUMBRIDGE_TELEPORT);
                if (item != null) {
                    item.interact("Break");
                    Time.sleep(5000);
                }
            } else {
                Statics.buyLumbridgeTeleport = true;
            }
        }
    }

    private static void getToScorpions() {
        if (Distance.between(Players.getLocal(), SCORION.area.getCenter()) < 125 && Players.getLocal() != null) {
            Movement.walkTo(SCORION.area.getCenter());
            Time.sleepUntil(() -> SCORION.area.contains(Players.getLocal()), 2000);
        } else {
            if (Inventory.contains(Statics.COINS)) {
                if (Bank.isOpen()) {
                    Bank.depositAll(Statics.COINS);
                    Time.sleep(750);
                } else {
                    Bank.open();
                    Time.sleepUntil(Bank::isOpen, 2000);
                }
            } else if (Inventory.contains(Statics.RING_OF_DUELING)) {
                Item item = Inventory.getFirst(a -> a.getName().matches(Statics.RING_OF_DUELING.pattern()));
                InterfaceComponent interfaceComponent = Dialog.getChatOption(a -> a.contains("Al Kharid Duel Arena."));
                if (Dialog.isOpen() && Dialog.isViewingChatOptions() && interfaceComponent != null && interfaceComponent.isVisible()) {
                    interfaceComponent.interact("Al Kharid Duel Arena.");
                    Time.sleep(5000);
                } else if (item != null) {
                    item.interact("Rub");
                    Time.sleep(3000);
                }
            } else {
                if (Bank.isOpen()) {
                    if (Bank.contains(Statics.RING_OF_DUELING)) {
                        Bank.withdraw(a -> a.getName().matches(Statics.RING_OF_DUELING.pattern()), 1);
                        Time.sleep(500);
                    } else {
                        Statics.buyRingOfDueling = true;
                    }
                } else if (Areas.GRAND_EXCHANGE.contains(Players.getLocal())) {
                    Bank.open();
                    Time.sleepUntil(Bank::isOpen, 2000);
                } else {
                    Api.teleportToGrandExchange();
                }
            }
        }
    }
}
