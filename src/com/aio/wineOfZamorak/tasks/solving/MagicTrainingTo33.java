package com.aio.wineOfZamorak.tasks.solving;

import com.aio.abyss.Abyss;
import com.aio.wineOfZamorak.WineOfZamorak;
import com.aio.wineOfZamorak.tasks.utility.Areas;
import com.aio.wineOfZamorak.tasks.utility.Statics;
import com.api.API;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.*;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

public class MagicTrainingTo33 extends Task {
    private Player me;
    private int junkList[] = {API.notedItem(Statics.AMULET_OF_GLORY_FULL), Statics.COINS};

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Skills.getCurrentLevel(Skill.MAGIC) < 33;
    }

    @Override
    public int execute() {
        if (Inventory.containsAll(Statics.MIND_RUNE, Statics.FIRE_RUNE, Statics.LAW_RUNE)) {
            if (Skills.getCurrentLevel(Skill.MAGIC) < 25) {
                if (Areas.GRAND_EXCHANGE.contains(me)) {
                    if (API.closeInterfaces() && API.openTab(Tab.EQUIPMENT)) {
                        Abyss.status = "Teleporting to Draynor Village";
                        Equipment.interact(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), "Draynor Village");
                        Time.sleepUntil(() -> !me.isAnimating() || !Areas.GRAND_EXCHANGE.contains(me), 4500);
                    }
                } else if (Areas.MUDSKIPPER_JAIL.contains(me)) {
                    if (Skills.getCurrentLevel(Skill.MAGIC) < 13 && Varps.get(108) != 3) {
                        InterfaceComponent interfaceComponent = Interfaces.getComponent(201, 1, 1);
                        if (interfaceComponent != null && interfaceComponent.isVisible()) {
                            interfaceComponent.interact(ActionOpcodes.INTERFACE_ACTION);
                            Time.sleep(Random.nextInt(250, 350));
                        } else if (API.openTab(Tab.COMBAT)) {
                            InterfaceComponent comb2 = Interfaces.getComponent(593, 25);
                            if (comb2 != null && comb2.isVisible()) {
                                comb2.interact("Choose spell");
                                Time.sleep(Random.nextInt(250, 350));
                            }
                        }
                    } else if (Skills.getCurrentLevel(Skill.MAGIC) >= 13 && Varps.get(108) != 9) {
                        InterfaceComponent interfaceComponent = Interfaces.getComponent(201, 1, 4);
                        if (interfaceComponent != null && interfaceComponent.isVisible()) {
                            interfaceComponent.interact(ActionOpcodes.INTERFACE_ACTION);
                            Time.sleep(Random.nextInt(250, 350));
                        } else if (API.openTab(Tab.COMBAT)) {
                            InterfaceComponent comb2 = Interfaces.getComponent(593, 25);
                            if (comb2 != null && comb2.isVisible()) {
                                comb2.interact("Choose spell");
                                Time.sleep(Random.nextInt(250, 350));
                            }
                        }
                    } else {
                        Npc npc = Npcs.getNearest(a -> a.getName().equals("Black knight") && a.getAnimation() == -1);
                        if (npc != null && me.getTarget() == null) {
                            WineOfZamorak.status = "Attacking knight";
                            npc.interact("Attack");
                            Time.sleep(Random.nextInt(2000, 5000));
                        }
                    }
                } else {
                    WineOfZamorak.status = "Walk to Mudskipper jail";
                    Movement.walkTo(Areas.MUDSKIPPER_JAIL.getCenter());
                    Time.sleep(Random.nextInt(2000, 2500));
                }
            } else {
                if (me.getAnimation() == -1) {
                    if (API.closeInterfaces() && API.openTab(Tab.MAGIC)) {
                        InterfaceComponent interfaceComponent = Interfaces.getComponent(218, 19);
                        if (interfaceComponent != null && interfaceComponent.isVisible()) {
                            interfaceComponent.interact("Cast");
                            Time.sleep(Random.nextInt(500, 800));
                            Time.sleepUntil(() -> me.getAnimation() == -1, Random.nextInt(4000, 4500));
                        }
                    }
                }
            }
        } else if (Inventory.contains(Statics.STAFF_OF_AIR) && !Equipment.contains(Statics.STAFF_OF_AIR)) {
            if (API.closeInterfaces() && API.openTab(Tab.INVENTORY)) {
                Item item = Inventory.getFirst(Statics.STAFF_OF_AIR);
                if (item != null) {
                    WineOfZamorak.status = "Wielding Staff of Air";
                    item.interact("Wield");
                    Time.sleep(Random.nextInt(250, 350));
                }
            }
        } else if (Inventory.contains(Statics.AMULET_OF_GLORY_PATTERN) && !Equipment.contains(Statics.AMULET_OF_GLORY_PATTERN) && !Inventory.contains(API.notedItem(Statics.AMULET_OF_GLORY_FULL))) {
            if (API.closeInterfaces() && API.openTab(Tab.INVENTORY)) {
                Item item = Inventory.getFirst(Statics.AMULET_OF_GLORY_PATTERN);
                if (item != null) {
                    WineOfZamorak.status = "Wear Amulet of Glory";
                    item.interact("Wear");
                    Time.sleep(Random.nextInt(250, 350));
                }
            }
        } else if (Inventory.contains(Statics.RING_OF_WEALTH_PATTERN) && !Equipment.contains(Statics.RING_OF_WEALTH_PATTERN)) {
            if (API.closeInterfaces() && API.openTab(Tab.INVENTORY)) {
                Item item = Inventory.getFirst(Statics.RING_OF_WEALTH_PATTERN);
                if (item != null) {
                    WineOfZamorak.status = "Wear Ring of Wealth";
                    item.interact("Wear");
                    Time.sleep(Random.nextInt(250, 350));
                }
            }
        } else if (Areas.GRAND_EXCHANGE.contains(me)) {
            if (API.openBank()) {
                if (Inventory.contains(junkList)) {
                    WineOfZamorak.status = "Depositing junk items";
                    for (int a : junkList) {
                        if (Inventory.contains(a)) {
                            Bank.depositAll(a);
                            Time.sleep(Random.nextInt(450, 500));
                        }
                    }
                } else if (!Inventory.contains(Statics.STAFF_OF_AIR) && !Equipment.contains(Statics.STAFF_OF_AIR)) {
                    if (Bank.contains(Statics.STAFF_OF_AIR)) {
                        WineOfZamorak.status = "Withdraw Staff of air";
                        Bank.withdraw(Statics.STAFF_OF_AIR, 1);
                        Time.sleep(Random.nextInt(450, 500));
                    } else if (Game.isLoggedIn()) {
                        WineOfZamorak.status = "Buy Staff of Air";
                        Statics.buyStaffOfAir = true;
                    }
                } else if (!Inventory.contains(Statics.AMULET_OF_GLORY_PATTERN) && !Equipment.contains(Statics.AMULET_OF_GLORY_PATTERN)) {
                    if (Bank.contains(Statics.AMULET_OF_GLORY_PATTERN)) {
                        WineOfZamorak.status = "Withdraw Amulet of Glory";
                        Bank.withdraw(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), 1);
                        Time.sleep(Random.nextInt(450, 500));
                    } else if (Game.isLoggedIn()) {
                        WineOfZamorak.status = "Buy Amulet of Glory";
                        Statics.buyAmuletOfGlory = true;
                    }
                } else if (!Inventory.contains(Statics.RING_OF_WEALTH_PATTERN) && !Equipment.contains(Statics.RING_OF_WEALTH_PATTERN)) {
                    if (Bank.contains(Statics.RING_OF_WEALTH_PATTERN)) {
                        WineOfZamorak.status = "Withdraw Ring of Wealth";
                        Bank.withdraw(a -> a.getName().matches(Statics.RING_OF_WEALTH_PATTERN.pattern()), 1);
                        Time.sleep(Random.nextInt(450, 500));
                    } else if (Game.isLoggedIn()) {
                        WineOfZamorak.status = "Buy Ring of Wealth";
                        Statics.buyRingOfWealth = true;
                    }
                } else if (!Inventory.contains(Statics.MIND_RUNE)) {
                    if (Bank.contains(Statics.MIND_RUNE)) {
                        WineOfZamorak.status = "Withdraw Mind Runes";
                        Bank.withdrawAll(Statics.MIND_RUNE);
                        Time.sleep(Random.nextInt(250, 350));
                    } else {
                        WineOfZamorak.status = "Buying Mind runes";
                        Statics.buyMindRunes = true;
                    }
                } else if (!Inventory.contains(Statics.FIRE_RUNE)) {
                    if (Bank.contains(Statics.FIRE_RUNE)) {
                        WineOfZamorak.status = "Withdraw Fire Runes";
                        Bank.withdrawAll(Statics.FIRE_RUNE);
                        Time.sleep(Random.nextInt(250, 350));
                    } else {
                        WineOfZamorak.status = "Buying Fire runes";
                        Statics.buyFireRunes = true;
                    }
                } else if (!Inventory.contains(Statics.LAW_RUNE)) {
                    if (Bank.contains(Statics.LAW_RUNE)) {
                        WineOfZamorak.status = "Withdraw Law Runes";
                        Bank.withdrawAll(Statics.LAW_RUNE);
                        Time.sleep(Random.nextInt(250, 350));
                    } else {
                        WineOfZamorak.status = "Buying Law runes";
                        Statics.buyLawRunes = true;
                    }
                }
            }
        }
        return Random.nextInt(300, 500);
    }
}
