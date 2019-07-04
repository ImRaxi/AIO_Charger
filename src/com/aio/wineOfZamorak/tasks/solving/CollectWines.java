package com.aio.wineOfZamorak.tasks.solving;

import com.aio.wineOfZamorak.WineOfZamorak;
import com.aio.wineOfZamorak.tasks.utility.Areas;
import com.aio.wineOfZamorak.tasks.utility.Statics;
import com.api.API;
import com.api.HopWorld;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.Worlds;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.*;
import org.rspeer.runetek.api.local.Health;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

public class CollectWines extends Task {
    private Player me;
    private int junkItems[] = {Statics.AMULET_OF_GLORY_EMPTY, Statics.COINS, Statics.MIND_RUNE, Statics.FIRE_RUNE, API.notedItem(Statics.BURNING_AMULET_FULL),
            API.notedItem(Statics.LOBSTER)};


    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null;
    }

    @Override
    public int execute() {
        if (Areas.CHAOS_TEMPLE.contains(me) && !Inventory.isFull() && Inventory.contains(Statics.LAW_RUNE)) {
            Pickable wine = Pickables.getNearest(a -> a.getId() == Statics.WINE_OF_ZAMORAK);
            if (enemyIsVisible()) {
                WineOfZamorak.status = "Hopping cuz of enemy";
                HopWorld.hopToRandomP2p();
                WineOfZamorak.wineGrabbed = 0;
            } else if (!me.isAnimating() && WineOfZamorak.wineGrabbed == 2) {
                WineOfZamorak.status = "Hopping after 2 wines";
                if (Statics.badWorld == Worlds.getCurrent()) {
                    HopWorld.hopToRandomP2p();
                } else {
                    WineOfZamorak.wineGrabbed = 0;
                    Statics.badWorld = -1;
                }
            } else if (API.closeInterfaces() && API.openTab(Tab.MAGIC)) {
                if (wine != null) {
                    if (Game.isLoggedIn() && !me.isAnimating() && Magic.isSpellSelected()) {
                        WineOfZamorak.status = "Telekinetic Wine";
                        wine.interact("Cast");
                        Time.sleep(Random.nextInt(750, 1000));
                        Time.sleepUntil(() -> !me.isAnimating(), Random.nextInt(3000, 4000));
                        WineOfZamorak.wineGrabbed += 1;
                        Statics.badWorld = Worlds.getCurrent();
                    } else {
                        WineOfZamorak.status = "Selecting Spell";
                        Magic.interact(Spell.Modern.TELEKINETIC_GRAB, "Cast");
                        Time.sleep(Random.nextInt(250, 350));
                    }
                }
            }
        } else if (Areas.LUMBRIDGE_CASTLE.contains(me) || Areas.LUMBRIDGE_CASTLE_FLOOR1.contains(me) || Areas.LUMBRIDGE_CASTLE_FLOOR2.contains(me)) {
            if (Equipment.contains(Statics.AMULET_OF_GLORY_PATTERN)) {
                if (API.closeInterfaces() && API.openTab(Tab.EQUIPMENT)) {
                    WineOfZamorak.status = "Teleport to Edgeville";
                    Equipment.interact(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), "Edgeville");
                    Time.sleepUntil(() -> Areas.EDGEVILLE.contains(me) && !me.isAnimating(), Random.nextInt(4000, 4500));
                }
            } else if (Inventory.contains(Statics.AMULET_OF_GLORY_PATTERN)) {
                Item item = Inventory.getFirst(Statics.AMULET_OF_GLORY_PATTERN);
                if (API.closeInterfaces() && API.openTab(Tab.INVENTORY) && item != null) {
                    WineOfZamorak.status = "Wear Amulet of glory";
                    item.interact("Wear");
                    Time.sleep(Random.nextInt(350, 500));
                }
            }
        } else if (Areas.GRAND_EXCHANGE.contains(me) || Areas.EDGEVILLE.contains(me)) {
            if (Areas.GRAND_EXCHANGE.contains(me) && Equipment.contains(Statics.RING_OF_WEALTH_EMPTY)) {
                if (API.closeInterfaces() && API.openTab(Tab.EQUIPMENT)) {
                    if (Equipment.unequip(Statics.RING_OF_WEALTH_EMPTY)) {
                        WineOfZamorak.status = "Sell ring of wealth";
                        Statics.sellRingOfWealth = true;
                    }
                }
            } else if (Inventory.contains(Statics.STAFF_OF_AIR)) {
                Item item = Inventory.getFirst(Statics.STAFF_OF_AIR);
                if (API.closeInterfaces() && API.openTab(Tab.INVENTORY) && item != null) {
                    WineOfZamorak.status = "Wielding Staff of air";
                    item.interact("Wield");
                    Time.sleep(Random.nextInt(450, 500));
                }
            } else if (Inventory.contains(Statics.LOBSTER)) {
                Item item = Inventory.getFirst(Statics.LOBSTER);
                if (API.closeInterfaces() && API.openTab(Tab.INVENTORY) && item != null) {
                    item.interact("Eat");
                    Time.sleep(Random.nextInt(600, 900));
                }
            } else if (Inventory.contains(Statics.AMULET_OF_GLORY_PATTERN) && !Equipment.contains(Statics.AMULET_OF_GLORY_PATTERN)) {
                Item item = Inventory.getFirst(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()));
                if (API.closeInterfaces() && API.openTab(Tab.INVENTORY) && item != null) {
                    WineOfZamorak.status = "Wearing Amulet of glory";
                    item.interact("Wear");
                    Time.sleep(Random.nextInt(450, 500));
                }
            } else if (Equipment.contains(Statics.STAFF_OF_AIR) && (Inventory.contains(Statics.LAW_RUNE) && Inventory.getFirst(Statics.LAW_RUNE).getStackSize() > 3 && Inventory.getFirst(Statics.LAW_RUNE).getStackSize() < 29)
                    && Inventory.contains(Statics.BURNING_AMULET_PATTERN) && !Inventory.contains(Statics.RING_OF_WEALTH_PATTERN) && !Inventory.contains(junkItems)
                    && Equipment.contains(Statics.AMULET_OF_GLORY_PATTERN) && Health.getPercent() > 85) {
                if (Equipment.contains(Statics.RING_OF_WEALTH_PATTERN)) {
                    if (API.closeInterfaces() && API.openTab(Tab.EQUIPMENT)) {
                        WineOfZamorak.status = "Unequiping Ring of Wealth";
                        Equipment.unequip(a -> a.getName().matches(Statics.RING_OF_WEALTH_PATTERN.pattern()));
                        Time.sleep(Random.nextInt(350, 400));
                    }
                } else {
                    Item item = Inventory.getFirst(a -> a.getName().matches(Statics.BURNING_AMULET_PATTERN.pattern()));
                    if (item != null) {
                        InterfaceComponent chat = Dialog.getChatOption(a -> a.contains("Okay, teleport to level 41 Wilderness.") || a.contains("Lava Maze"));
                        Item ammy = Inventory.getFirst(a -> a.getName().matches(Statics.BURNING_AMULET_PATTERN.pattern()));
                        if (chat != null && chat.isVisible()) {
                            WineOfZamorak.status = "Teleporting chat process";
                            chat.interact("Continue");
                            Time.sleep(Random.nextInt(600, 800));
                            Time.sleepUntil(() -> me.getAnimation() == -1 && !Areas.GRAND_EXCHANGE.contains(me) && !Areas.EDGEVILLE.contains(me), Random.nextInt(3500, 4500));
                        } else if (ammy != null) {
                            if (API.closeInterfaces() && API.openTab(Tab.INVENTORY)) {
                                WineOfZamorak.status = "Using Amulet to Teleport";
                                ammy.interact("Rub");
                                Time.sleep(Random.nextInt(800, 1200));
                            }
                        }
                    }
                }
            } else if (API.openBank()) {
                if (Inventory.contains(junkItems)) {
                    WineOfZamorak.status = "Depositing junk items";
                    for (int i : junkItems) {
                        if (Inventory.contains(i)) {
                            Bank.depositAll(i);
                            Time.sleep(Random.nextInt(450, 500));
                        }
                    }
                } else if (Inventory.contains(Statics.WINE_OF_ZAMORAK)) {
                    WineOfZamorak.status = "Depositing all Wine of zamorak";
                    Bank.depositAll(Statics.WINE_OF_ZAMORAK);
                    Time.sleep(Random.nextInt(450, 500));
                    if (Bank.contains(Statics.WINE_OF_ZAMORAK)) {
                        WineOfZamorak.winesCollected = Bank.getFirst(Statics.WINE_OF_ZAMORAK).getStackSize();
                    }
                } else if (Health.getPercent() <= 85 && !Inventory.contains(Statics.LOBSTER)) {
                    if (Bank.contains(Statics.LOBSTER)) {
                        Bank.withdraw(Statics.LOBSTER, 1);
                        Time.sleep(Random.nextInt(450, 500));
                    } else {
                        Statics.buyLobster = true;
                    }
                } else if (!Equipment.contains(Statics.STAFF_OF_AIR) && !Inventory.contains(Statics.STAFF_OF_AIR)) {
                    if (Bank.contains(Statics.STAFF_OF_AIR)) {
                        WineOfZamorak.status = "Withdrawing Staff of air";
                        Bank.withdraw(Statics.STAFF_OF_AIR, 1);
                        Time.sleep(Random.nextInt(450, 500));
                    } else if (Game.isLoggedIn()) {
                        WineOfZamorak.status = "Buying Staff of air";
                        Statics.buyStaffOfAir = true;
                    }
                } else if (!Inventory.contains(Statics.LAW_RUNE) || (Inventory.contains(Statics.LAW_RUNE) && Inventory.getFirst(Statics.LAW_RUNE).getStackSize() <= 3)) {
                    if (Bank.contains(Statics.LAW_RUNE)) {
                        WineOfZamorak.status = "Withdrawing Law rune";
                        Bank.withdraw(Statics.LAW_RUNE, 28);
                        Time.sleep(Random.nextInt(450, 500));
                    } else if (Game.isLoggedIn()) {
                        WineOfZamorak.status = "Buying Law rune";
                        Statics.buyLawRunes = true;
                    }
                } else if (!Inventory.contains(Statics.BURNING_AMULET_PATTERN)) {
                    if (Bank.contains(Statics.BURNING_AMULET_PATTERN)) {
                        WineOfZamorak.status = "Withdrawing Burning amulet";
                        Bank.withdraw(a -> a.getName().matches(Statics.BURNING_AMULET_PATTERN.pattern()), 1);
                        Time.sleep(Random.nextInt(450, 500));
                    } else if (Game.isLoggedIn()) {
                        WineOfZamorak.status = "Buying Burning amulet";
                        Statics.buyBurningAmulet = true;
                    }
                } else if (Inventory.contains(Statics.RING_OF_WEALTH_PATTERN)) {
                    WineOfZamorak.status = "Depositing Ring of wealth";
                    Bank.deposit(a -> a.getName().matches(Statics.RING_OF_WEALTH_PATTERN.pattern()), 1);
                    Time.sleep(Random.nextInt(450, 500));
                } else if (Inventory.contains(Statics.LAW_RUNE) && Inventory.getFirst(Statics.LAW_RUNE).getStackSize() > 28) {
                    WineOfZamorak.status = "Depositing Law runes";
                    Bank.depositAll(Statics.LAW_RUNE);
                    Time.sleep(Random.nextInt(450, 500));
                } else if (Equipment.contains(Statics.AMULET_OF_GLORY_EMPTY) || !Equipment.contains(Statics.AMULET_OF_GLORY_PATTERN)) {
                    Bank.withdraw(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), 1);
                    Time.sleep(Random.nextInt(450, 500));
                }
            }
        } else if (Areas.WILDERNESS_AREA.contains(me) && !Areas.CHAOS_TEMPLE.contains(me) && !Inventory.isFull()) {
            if (enemyIsVisible() && !imUnderAttack()) {
                HopWorld.hopToRandomP2p();
            } else {
                if (me.getPosition().getY() > 3830) {
                    WineOfZamorak.status = "Walk to first position";
                    Movement.walkTo(Areas.FIRST_WALK_POSITION);
                    Time.sleep(Random.nextInt(1200, 1600));
                } else {
                    WineOfZamorak.status = "Walk to Chaos temple";
                    Movement.walkTo(Areas.CHAOS_TEMPLE.getCenter());
                    Time.sleep(Random.nextInt(1200, 1600));
                }
            }
        } else if ((Inventory.isFull() || Inventory.contains(Statics.LAW_RUNE)) && !Areas.EDGEVILLE.contains(me)) {
            if (me.getPosition().getY() > 3758) {
                WineOfZamorak.status = "Walk to Teleport Spot";
                Movement.walkTo(Areas.TELEPORT_POSITION);
                Time.sleep(Random.nextInt(1250, 1750));
            } else {
                WineOfZamorak.status = "Teleport to Edgeville";
                if (API.openTab(Tab.EQUIPMENT)) {
                    Equipment.interact(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), "Edgeville");
                    Time.sleepUntil(() -> Areas.EDGEVILLE.contains(me) && !me.isAnimating(), Random.nextInt(4000, 4500));
                }
            }
        }
        return Random.nextInt(300, 500);
    }

    private boolean enemyIsVisible() {
        int myCombatLevel = me.getCombatLevel();
        int wildernessLevel = 38;
        Area area = Area.rectangular(2944, 3828, 2967, 3812);
        Player enemy[] = Players.getLoaded(a -> a.getCombatLevel() > 10 && a.getCombatLevel() < myCombatLevel + wildernessLevel && area.contains(a));
        return me != null && enemy != null && enemy.length > 1;
    }

    private boolean imUnderAttack() {
        Npc npc = Npcs.getNearest(a -> a.getTarget() == me);
        return me != null && npc != null;
    }
}
