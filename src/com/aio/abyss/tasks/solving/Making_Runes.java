package com.aio.abyss.tasks.solving;

import com.aio.abyss.Abyss;
import com.aio.abyss.tasks.enumData.Altar;
import com.aio.abyss.tasks.enumData.Armor;
import com.aio.abyss.tasks.utility.Areas;
import com.aio.abyss.tasks.utility.Statics;
import com.api.API;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.Worlds;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Distance;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.*;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;
import org.rspeer.runetek.api.local.Health;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

import java.util.regex.Pattern;

public class Making_Runes extends Task {

    private Player me;
    private Pattern runeName = Pattern.compile(".*rune$");

    private int itemsToDeposit[] = {API.notedItem(Statics.STAMINA_POTION), API.notedItem(Statics.LOBSTER), Statics.VIAL, API.notedItem(Statics.AMULET_OF_GLORY_FULL), Statics.COINS,
            Statics.AMULET_OF_GLORY_EMPTY, API.notedItem(Statics.AMULET_OF_GLORY_EMPTY), API.notedItem(Statics.PURE_ESSENCE), Statics.RUNE_FULL_HELM, Statics.RUNE_KITESHIELD, Statics.RUNE_SCIMITAR, Statics.VARROCK_TELEPORT,
            Statics.MITHRIL_PLATEBODY, Statics.MITHRIL_PLATELEGS, Statics.ABYSSAL_BOOK, Statics.RUNE_CHAINBODY, Statics.RUNE_PLATELEGS};

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null;
    }

    @Override
    public int execute() {
        if (Areas.INSIDE_EARTH_ALTAR.contains(me) || Areas.INSIDE_FIRE_ALTAR.contains(me) || Areas.INSIDE_NATURE_ALTAR.contains(me)) {
            if (!Inventory.isFull() && (Statics.emptySmallPouch || Statics.emptyMediumPouch || Statics.emptyLargePouch || Statics.emptyGiantPouch)) {
                if (Statics.emptyGiantPouch) {
                    Abyss.status = "Empty Giant pouch";
                    if (emptyPouch(Statics.GIANT_POUCH)) {
                        Statics.emptyGiantPouch = false;
                    }
                } else if (Statics.emptyLargePouch) {
                    Abyss.status = "Empty Large pouch";
                    if (emptyPouch(Statics.LARGE_POUCH)) {
                        Statics.emptyLargePouch = false;
                    }
                } else if (Statics.emptySmallPouch) {
                    Abyss.status = "Empty Small pouch";
                    if (emptyPouch(Statics.SMALL_POUCH)) {
                        Statics.emptySmallPouch = false;
                        Time.sleep(Random.nextInt(300, 400));
                    }
                } else if (Statics.emptyMediumPouch) {
                    Abyss.status = "Empty Medium pouch";
                    if (emptyPouch(Statics.MEDIUM_POUCH)) {
                        Statics.emptyMediumPouch = false;
                    }
                }
            } else if (Inventory.contains(Statics.PURE_ESSENCE)) {
                SceneObject altar = SceneObjects.getNearest("Altar");
                if (altar != null) {
                    Abyss.status = "Crafting runes";
                    altar.interact("Craft-rune");
                    Time.sleepUntil(() -> !Inventory.contains(Statics.PURE_ESSENCE) && me.getAnimation() == -1, 3000);
                }
            } else {
                if (Skills.getCurrentLevel(Skill.RUNECRAFTING) > 0) {
                    Statics.fillSmallPouch = true;
                }
                if (Skills.getCurrentLevel(Skill.RUNECRAFTING) >= 25) {
                    Statics.fillMediumPouch = true;
                }
                if (Skills.getCurrentLevel(Skill.RUNECRAFTING) >= 50) {
                    Statics.fillLargePouch = true;
                }
                if (Skills.getCurrentLevel(Skill.RUNECRAFTING) >= 75) {
                    Statics.fillGiantPouch = true;
                }
                if (!API.isInClanChat()) {
                    Statics.joinClanChat = true;
                }

                if (API.openTab(Tab.EQUIPMENT)) {
                    Abyss.status = "Teleporting to Edgeville";
                    Equipment.interact(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), "Edgeville");
                    Time.sleepUntil(() -> !me.isAnimating() || Areas.EDGEVILLE.contains(me), 4500);
                }
            }
        } else if (Areas.ABYSS_INSIDE.contains(me)) {
            if (Inventory.contains(5511, 5513, 5515) || Statics.getSmallPouch) {
                if (Statics.getSmallPouch && Inventory.contains(Statics.SMALL_POUCH)) {
                    Statics.getSmallPouch = false;
                }
                if (Statics.getSmallPouch && Inventory.isFull()) {
                    Item item = Inventory.getFirst(Statics.PURE_ESSENCE);
                    if (item != null) {
                        item.interact("Drop");
                        Time.sleep(Random.nextInt(500, 700));
                    }
                }
                Npc npc = Npcs.getNearest("Dark mage");
                InterfaceComponent interfaceComponent = Dialog.getChatOption(a -> a.contains("I need your help with something...") || a.contains("Can I have a new essence pouch?"));
                if (interfaceComponent != null && interfaceComponent.isVisible()) {
                    interfaceComponent.interact("Continue");
                    Time.sleep(Random.nextInt(275, 325));
                } else if (npc != null) {
                    Abyss.status = "Talking with Dark mage";
                    npc.interact("Talk-to");
                    Time.sleep(Random.nextInt(1250, 1750));
                }
            } else {
                SceneObject entrance = SceneObjects.getNearest(Altar.getAltarEntrance());
                if (entrance != null) {
                    Abyss.status = "Entering altar";
                    entrance.interact("Exit-through");
                    Time.sleepUntil(() -> Altar.altarArea().contains(me) || !me.isMoving(), 750);
                }
            }
        } else if (Areas.ABYSS_OUTSIDE.contains(me)) {
            if (Inventory.contains(Statics.PURE_ESSENCE)) {
                SceneObject sceneObject = SceneObjects.getNearest("Gap", "Rock");
                if (sceneObject != null) {
                    Abyss.status = "Passing to Inside abyss";
                    sceneObject.interact(ActionOpcodes.OBJECT_ACTION_0);
                }
            } else {
                Abyss.status = "Teleporting to Edgeville";
                if (API.openTab(Tab.EQUIPMENT)) {
                    Equipment.interact(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), "Edgeville");
                    Time.sleepUntil(() -> !me.isAnimating() || Areas.EDGEVILLE.contains(me), 4500);
                }
            }
        } else if (Abyss.escape()) {
            Abyss.status = "Escaping";
            Statics.hopWorld = true;
        } else if (Inventory.contains(Statics.PURE_ESSENCE) && Inventory.isFull() && (!Statics.fillSmallPouch || Statics.getSmallPouch) && !Statics.fillMediumPouch && !Statics.fillLargePouch && !Statics.fillGiantPouch
                && Equipment.contains(Statics.AMULET_OF_GLORY_PATTERN)) {
            SceneObject essMine = SceneObjects.getNearest(7471);
            if (essMine != null) {
                if (API.openTab(Tab.EQUIPMENT)) {
                    Abyss.status = "Teleporting to Edgeville";
                    Equipment.interact(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), "Edgeville");
                    Time.sleep(500);
                    Time.sleepUntil(() -> !me.isAnimating() || Areas.EDGEVILLE.contains(me), 4500);
                }
            } else if ((!Movement.isStaminaEnhancementActive() && Inventory.contains(Statics.STAMINA_POTION_PATTERN) && Movement.getRunEnergy() < 80) || Movement.getRunEnergy() < 20) {
                Item item = Inventory.getFirst(a -> a.getName().matches(Statics.STAMINA_POTION_PATTERN.pattern()));
                if (item != null) {
                    Abyss.status = "Drinking stamina potion";
                    item.interact("Drink");
                    Time.sleep(Random.nextInt(400, 600));
                }
            } else {
                if (Worlds.getCurrent() == 319) {
                    Statics.hopWorld = true;
                } else {
                    if (!Areas.GRAND_EXCHANGE.contains(me)) {
                        Npc npc = Npcs.getNearest("Mage of Zamorak");
                        if (npc != null && Distance.between(me, npc) < 15) {
                            npc.interact("Teleport");
                            Time.sleepUntil(() -> Areas.ABYSS_OUTSIDE.contains(me), 500);
                        } else {
                            if (me.getY() <= 3520) {
                                SceneObject wall = SceneObjects.getNearest("Wilderness Ditch");
                                InterfaceComponent interfaceComponent = Interfaces.getComponent(475, 11);
                                if (interfaceComponent != null && interfaceComponent.isVisible()) {
                                    interfaceComponent.interact("Enter Wilderness");
                                    Time.sleep(Random.nextInt(400, 600));
                                    Time.sleepUntil(() -> me.getY() > 3520 && !me.isAnimating(), 3000);
                                } else if (wall != null) {
                                    wall.interact("Cross");
                                    Time.sleepUntil(() -> (me.getY() > 3520 && !me.isAnimating()) || interfaceComponent != null && interfaceComponent.isVisible(), 3000);
                                }
                            } else {
                                Abyss.status = "Walking To Zamorak Mage";
                                Movement.walkTo(new Position(3105, 3556));
                                Time.sleep(Random.nextInt(400, 600));
                            }
                        }
                    } else {
                        if (API.closeInterfaces()) {
                            if (API.openTab(Tab.EQUIPMENT)) {
                                Abyss.status = "Teleporting to Edgeville";
                                Equipment.interact(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), "Edgeville");
                                Time.sleep(500);
                                Time.sleepUntil(() -> !me.isAnimating() || Areas.EDGEVILLE.contains(me), 4500);
                            }
                        }
                    }
                }
            }
        } else if (Areas.EDGEVILLE.contains(me) || Areas.GRAND_EXCHANGE.contains(me)) {
            if (Inventory.contains(Statics.ADAMANT_PICKAXE) && !Equipment.contains(Statics.ADAMANT_PICKAXE)) {
                Abyss.status = "Wear Adamant pickaxe";
                if (API.closeInterfaces() && API.openTab(Tab.INVENTORY)) {
                    Armor.wearItem(Statics.ADAMANT_PICKAXE);
                    Time.sleep(Random.nextInt(400, 450));
                }
            } else if (Inventory.contains(Statics.ADAMANT_PICKAXE) && Equipment.contains(Statics.ADAMANT_PICKAXE)) {
                if (API.openBank()) {
                    Abyss.status = "Depositing Adamant Pickaxe Overflow";
                    Bank.depositAll(Statics.ADAMANT_PICKAXE);
                    Time.sleep(Random.nextInt(400, 450));
                }
            } else if (Inventory.contains(Statics.ADAMANT_PLATEBODY) && !Equipment.contains(Statics.ADAMANT_PLATEBODY)) {
                Abyss.status = "Wear Adamant chainbody";
                if (API.closeInterfaces() && API.openTab(Tab.INVENTORY)) {
                    Armor.wearItem(Statics.ADAMANT_PLATEBODY);
                    Time.sleep(Random.nextInt(400, 450));
                }
            } else if (Inventory.contains(Statics.ADAMANT_PLATEBODY) && Equipment.contains(Statics.ADAMANT_PLATEBODY)) {
                if (API.openBank()) {
                    Abyss.status = "Depositing Adamant Chainbody Overflow";
                    Bank.depositAll(Statics.ADAMANT_PLATEBODY);
                    Time.sleep(Random.nextInt(400, 450));
                }
            } else if (Inventory.contains(Statics.ADAMANT_PLATELEGS) && !Equipment.contains(Statics.ADAMANT_PLATELEGS)) {
                Abyss.status = "Wear Adamant platelegs";
                if (API.closeInterfaces() && API.openTab(Tab.INVENTORY)) {
                    Armor.wearItem(Statics.ADAMANT_PLATELEGS);
                    Time.sleep(Random.nextInt(400, 450));
                }
            } else if (Inventory.contains(Statics.ADAMANT_PLATELEGS) && Equipment.contains(Statics.ADAMANT_PLATELEGS)) {
                if (API.openBank()) {
                    Abyss.status = "Depositing Adamant Platelegs Overflow";
                    Bank.depositAll(Statics.ADAMANT_PLATELEGS);
                    Time.sleep(Random.nextInt(400, 450));
                }
            } else if (Equipment.contains(Statics.RUNE_KITESHIELD)) {
                Abyss.status = "Unequip Rune kiteshield";
                if (Inventory.isFull()) {
                    if (API.openBank()) {
                        Bank.depositInventory();
                        Time.sleep(Random.nextInt(275, 325));
                    }
                } else {
                    Equipment.unequip(Statics.RUNE_KITESHIELD);
                    Time.sleep(Random.nextInt(400, 600));
                }
            } else if (Equipment.contains(Statics.RUNE_FULL_HELM)) {
                Abyss.status = "Unequip Rune full helm";
                if (Inventory.isFull()) {
                    if (API.openBank()) {
                        Bank.depositInventory();
                        Time.sleep(Random.nextInt(275, 325));
                    }
                } else {
                    Equipment.unequip(Statics.RUNE_FULL_HELM);
                    Time.sleep(Random.nextInt(400, 600));
                }
            } else if (Equipment.contains(Statics.RING_OF_WEALTH_PATTERN)) {
                Abyss.status = "Unequip ring of wealth";
                if (Inventory.isFull()) {
                    if (API.openBank()) {
                        Bank.depositInventory();
                        Time.sleep(Random.nextInt(275, 325));
                    }
                } else {
                    if (API.closeInterfaces() && API.openTab(Tab.EQUIPMENT)) {
                        Equipment.unequip(a -> a.getName().matches(Statics.RING_OF_WEALTH_PATTERN.pattern()));
                        Time.sleep(Random.nextInt(275, 325));
                    }
                }
            } else if (!Equipment.contains(Statics.AMULET_OF_GLORY_PATTERN) && Inventory.contains(Statics.AMULET_OF_GLORY_PATTERN) && !Inventory.contains(API.notedItem(Statics.AMULET_OF_GLORY_FULL))) {
                Item item = Inventory.getFirst(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()));
                if (item != null) {
                    Abyss.status = "Wearing glory";
                    if (API.closeInterfaces()) {
                        if (API.openTab(Tab.INVENTORY)) {
                            item.interact("Wear");
                            Time.sleep(Random.nextInt(650, 800));
                        }
                    }
                }
            } else if (Health.getPercent() < 100 && Inventory.contains(a -> a.containsAction("Eat"))) {
                Item item = Inventory.getFirst(a -> a.containsAction("Eat"));
                if (item != null) {
                    Abyss.status = "Eating food";
                    if (API.closeInterfaces()) {
                        if (API.openTab(Tab.INVENTORY)) {
                            item.interact("Eat");
                            Time.sleep(Random.nextInt(650, 800));
                        }
                    }
                }
            } else if (Statics.fillGiantPouch && Inventory.contains(Statics.GIANT_POUCH) && Inventory.getCount(Statics.PURE_ESSENCE) >= 12 && !Statics.getSmallPouch) {
                Abyss.status = "Fill Giant pouch";
                if (fillPouch(Statics.GIANT_POUCH)) {
                    Time.sleep(Random.nextInt(275, 325));
                    Statics.emptyGiantPouch = true;
                    Statics.fillGiantPouch = false;
                }
            } else if (Statics.fillLargePouch && Inventory.contains(Statics.LARGE_POUCH) && Inventory.getCount(Statics.PURE_ESSENCE) >= 9 && !Statics.getSmallPouch) {
                Abyss.status = "Fill Large pouch";
                if (fillPouch(Statics.LARGE_POUCH)) {
                    Time.sleep(Random.nextInt(275, 325));
                    Statics.emptyLargePouch = true;
                    Statics.fillLargePouch = false;
                }
            } else if (Statics.fillSmallPouch && Inventory.contains(Statics.SMALL_POUCH) && Inventory.getCount(Statics.PURE_ESSENCE) >= 3 && !Statics.getSmallPouch) {
                Abyss.status = "Fill Small pouch";
                if (fillPouch(Statics.SMALL_POUCH)) {
                    Time.sleep(Random.nextInt(275, 325));
                    Statics.emptySmallPouch = true;
                    Statics.fillSmallPouch = false;
                }
            } else if (Statics.fillMediumPouch && Inventory.contains(Statics.MEDIUM_POUCH) && Inventory.getCount(Statics.PURE_ESSENCE) >= 6 && !Statics.getSmallPouch) {
                Abyss.status = "Fill Medium pouch";
                if (fillPouch(Statics.MEDIUM_POUCH)) {
                    Time.sleep(Random.nextInt(275, 325));
                    Statics.emptyMediumPouch = true;
                    Statics.fillMediumPouch = false;
                }
            } else if (API.openBank()) {
                if (Inventory.contains(itemsToDeposit)) {
                    Abyss.status = "Deposit junk items";
                    for (int item : itemsToDeposit) {
                        Item it = Inventory.getFirst(item);
                        if (it != null) {
                            Bank.depositAll(item);
                            Time.sleep(Random.nextInt(400, 500));
                        }
                    }
                } else if (!Equipment.contains(Statics.ADAMANT_PICKAXE) && Game.isLoggedIn()) {
                    Abyss.status = "Withdrawing Adamant pickaxe";
                    if (Bank.contains(Statics.ADAMANT_PICKAXE)) {
                        Bank.withdraw(Statics.ADAMANT_PICKAXE, 1);
                        Time.sleep(Random.nextInt(400, 600));
                    } else if (Game.isLoggedIn()) {
                        Statics.buyAddyPick = true;
                    }
                } else if (!Equipment.contains(Statics.ADAMANT_PLATEBODY) && Game.isLoggedIn()) {
                    Abyss.status = "Withdrawing Adamant chainbody";
                    if (Bank.contains(Statics.ADAMANT_PLATEBODY)) {
                        Bank.withdraw(Statics.ADAMANT_PLATEBODY, 1);
                        Time.sleep(Random.nextInt(400, 600));
                    } else if (Game.isLoggedIn()) {
                        Statics.buyAdamantChainbody = true;
                    }
                } else if (!Equipment.contains(Statics.ADAMANT_PLATELEGS) && Game.isLoggedIn()) {
                    Abyss.status = "Withdrawing Adamant platelegs";
                    if (Bank.contains(Statics.ADAMANT_PLATELEGS)) {
                        Bank.withdraw(Statics.ADAMANT_PLATELEGS, 1);
                        Time.sleep(Random.nextInt(400, 600));
                    } else if (Game.isLoggedIn()) {
                        Statics.buyAdamantPlatelegs = true;
                    }
                } else if (Inventory.contains(Statics.RING_OF_WEALTH_PATTERN)) {
                    Abyss.status = "Depositing Ring of wealth";
                    Bank.depositAll(a -> a.getName().matches(Statics.RING_OF_WEALTH_PATTERN.pattern()));
                    Time.sleep(Random.nextInt(400, 600));
                } else if (Inventory.getCount(Statics.LOBSTER) > 1) {
                    Abyss.status = "Deposit Lobsters";
                    Bank.depositAll(Statics.LOBSTER);
                    Time.sleep(Random.nextInt(400, 600));
                } else if (Inventory.contains(runeName)) {
                    Abyss.status = "Depositing runes";
                    Bank.depositAll(a -> a.getName().matches(runeName.pattern()));
                    Time.sleep(550);
                    Abyss.tripCounter += 1;
                } else if (Equipment.contains(Statics.AMULET_OF_GLORY_EMPTY) || !Equipment.contains(Statics.AMULET_OF_GLORY_PATTERN)) {
                    Abyss.status = "Withdrawing glory";
                    if (Bank.contains(Statics.AMULET_OF_GLORY_PATTERN)) {
                        if (!Inventory.isFull()) {
                            Bank.withdraw(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), 1);
                            Time.sleep(Random.nextInt(400, 600));
                        } else {
                            Bank.depositAll(Statics.PURE_ESSENCE);
                            Time.sleep(Random.nextInt(275, 325));
                        }
                    } else {
                        if (Bank.contains(Statics.AMULET_OF_GLORY_EMPTY)) {
                            if (Bank.getWithdrawMode() == Bank.WithdrawMode.NOTE) {
                                if (Bank.withdrawAll(Statics.AMULET_OF_GLORY_EMPTY)) {
                                    Time.sleep(Random.nextInt(1000, 1250));
                                    Statics.sellGlory = true;
                                    Statics.buyGlory = true;
                                    Bank.setWithdrawMode(Bank.WithdrawMode.ITEM);
                                }
                            } else {
                                Bank.setWithdrawMode(Bank.WithdrawMode.NOTE);
                            }
                        } else {
                            Statics.buyGlory = true;
                        }
                    }
                } else if (Skills.getCurrentLevel(Skill.RUNECRAFTING) > 0 && !Inventory.contains(Statics.SMALL_POUCH) && !Statics.getSmallPouch) {
                    Abyss.status = "Withdrawing Small pouch";
                    if (Bank.contains(Statics.SMALL_POUCH)) {
                        Bank.withdraw(Statics.SMALL_POUCH, 1);
                        Time.sleep(Random.nextInt(400, 600));
                    } else {
                        Statics.getSmallPouch = true;
                    }
                } else if (Skills.getCurrentLevel(Skill.RUNECRAFTING) >= 25 && !Inventory.contains(Statics.MEDIUM_POUCH) && !Statics.getSmallPouch) {
                    Abyss.status = "Withdrawing Medium pouch";
                    if (Bank.contains(Statics.MEDIUM_POUCH)) {
                        Bank.withdraw(Statics.MEDIUM_POUCH, 1);
                        Time.sleep(Random.nextInt(400, 600));
                    } else {
                        Statics.gettingPouches = true;
                    }
                } else if (Skills.getCurrentLevel(Skill.RUNECRAFTING) >= 50 && !Inventory.contains(Statics.LARGE_POUCH) && !Statics.getSmallPouch) {
                    Abyss.status = "Withdrawing Large pouch";
                    if (Bank.contains(Statics.LARGE_POUCH)) {
                        Bank.withdraw(Statics.LARGE_POUCH, 1);
                        Time.sleep(Random.nextInt(400, 600));
                    } else {
                        Statics.gettingPouches = true;
                    }
                } else if (Skills.getCurrentLevel(Skill.RUNECRAFTING) >= 75 && !Inventory.contains(Statics.GIANT_POUCH) && !Statics.getSmallPouch) {
                    Abyss.status = "Withdrawing Giant pouch";
                    if (Bank.contains(Statics.GIANT_POUCH)) {
                        Bank.withdraw(Statics.GIANT_POUCH, 1);
                        Time.sleep(Random.nextInt(400, 600));
                    } else {
                        Statics.gettingPouches = true;
                    }
                } else if (!Inventory.contains(Statics.STAMINA_POTION_PATTERN)) {
                    Abyss.status = "Withdrawing Stamina potion";
                    if (Bank.contains(Statics.STAMINA_POTION_PATTERN)) {
                        Bank.withdraw(a -> a.getName().matches(Statics.STAMINA_POTION_PATTERN.pattern()), 1);
                        Time.sleep(Random.nextInt(400, 600));
                    } else {
                        Statics.buyStaminPotion = true;
                    }
                } else if (Health.getPercent() < 100) {
                    Abyss.status = "Withdrawing lobsters";
                    if (Bank.contains(Statics.LOBSTER)) {
                        if (Health.getCurrent() > Health.getLevel() - 12) {
                            Bank.withdraw(Statics.LOBSTER, 1);
                            Time.sleep(Random.nextInt(400, 600));
                        } else {
                            Bank.withdraw(Statics.LOBSTER, 2);
                            Time.sleep(Random.nextInt(400, 600));
                        }
                    } else {
                        Statics.buyLobster = true;
                    }
                } else if (!Inventory.contains(Statics.PURE_ESSENCE) || !Inventory.isFull()) {
                    Abyss.status = "Withdrawing Pure Essence";
                    if (Bank.contains(Statics.PURE_ESSENCE) && Bank.getCount(Statics.PURE_ESSENCE) >= 100) {
                        Bank.withdrawAll(Statics.PURE_ESSENCE);
                        Time.sleep(Random.nextInt(400, 600));
                    } else {
                        Statics.buyPureEssence = true;
                    }
                }
            }
        } else {
            Abyss.status = "Teleporting to Edgeville";
            if (API.openTab(Tab.EQUIPMENT)) {
                Equipment.interact(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), "Edgeville");
                Time.sleep(500);
                Time.sleepUntil(() -> !me.isAnimating() || Areas.EDGEVILLE.contains(me), 4500);
            }
        }
        return Random.nextInt(300, 500);
    }

    private boolean emptyPouch(String a) {
        Item item = Inventory.getFirst(a);
        if (item != null) {
            if (me.getAnimation() == -1) {
                if (API.openTab(Tab.INVENTORY)) {
                    return item.interact("Empty");
                }
            }
        }
        return false;
    }

    private boolean fillPouch(String a) {
        Item item = Inventory.getFirst(a);
        if (item != null) {
            if (API.closeInterfaces()) {
                if (API.openTab(Tab.INVENTORY)) {
                    return item.interact("Fill");
                }
            }
        }
        return false;
    }
}
