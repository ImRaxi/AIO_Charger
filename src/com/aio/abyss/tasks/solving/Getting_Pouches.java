package com.aio.abyss.tasks.solving;

import com.aio.abyss.Abyss;
import com.aio.abyss.tasks.enumData.Armor;
import com.aio.abyss.tasks.enumData.AttackStyle;
import com.aio.abyss.tasks.enumData.Weapon;
import com.aio.abyss.tasks.utility.AbyssGrandExchange;
import com.aio.abyss.tasks.utility.Areas;
import com.aio.abyss.tasks.utility.Statics;
import com.api.API;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Distance;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Equipment;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;
import org.rspeer.runetek.api.local.Health;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

import java.util.regex.Pattern;

public class Getting_Pouches extends Task {

    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Statics.gettingPouches && !Statics.getSmallPouch;
    }

    @Override
    public int execute() {
        Armor requiredArmor = Armor.requiredArmor();
        Weapon requiredWeapon = Weapon.requiredweapon();
        if (!requiredWeapon.hasWielded()) {
            if (requiredWeapon.inventoryContains()) {
                Abyss.status = "Wearing Weapon";
                requiredWeapon.wearWeapon();
                Time.sleep(Random.nextInt(350, 450));
            } else if (Bank.isOpen()) {
                if (Bank.contains(requiredWeapon.weapon())) {
                    Bank.withdraw(requiredWeapon.weapon(), 1);
                    Time.sleep(Random.nextInt(275, 325));
                } else {
                    Statics.buyWeapon = true;
                }
            } else if (Areas.EDGEVILLE.contains(me) || Areas.GRAND_EXCHANGE.contains(me)) {
                API.openBank();
            } else {
                Abyss.status = "Teleporting to Edgeville";
                if (API.openTab(Tab.EQUIPMENT)) {
                    Equipment.interact(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), "Edgeville");
                    Time.sleepUntil(() -> !me.isAnimating() || Areas.EDGEVILLE.contains(me), 4500);
                }
            }
        } else if (!requiredArmor.hasWieldedAll()) {
            if (requiredArmor.inventoryContainsMissingItem()) {
                Abyss.status = "Wearing Armor";
                requiredArmor.wearMissingItem();
                Time.sleep(Random.nextInt(400, 500));
            } else if (Bank.isOpen()) {
                if (Bank.contains(requiredArmor.armorPart())) {
                    Bank.withdraw(requiredArmor.armorPart(), 1);
                    Time.sleep(Random.nextInt(400, 500));
                } else {
                    Statics.buyArmor = true;
                }
            } else if (Areas.EDGEVILLE.contains(me) || Areas.GRAND_EXCHANGE.contains(me)) {
                API.openBank();
            } else {
                Abyss.status = "Teleporting to Edgeville";
                if (API.openTab(Tab.EQUIPMENT)) {
                    Equipment.interact(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), "Edgeville");
                    Time.sleepUntil(() -> !me.isAnimating() || Areas.EDGEVILLE.contains(me), 4500);
                }
            }
        } else if (!Equipment.contains(Statics.AMULET_OF_GLORY_PATTERN)) {
            if (Inventory.contains(Statics.AMULET_OF_GLORY_PATTERN)) {
                Item item = Inventory.getFirst(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()));
                if (item != null) {
                    if (API.closeInterfaces()) {
                        Abyss.status = "Wear Amulet of glory";
                        item.interact(ActionOpcodes.ITEM_ACTION_1);
                    }
                }
            } else {
                Abyss.status = "Buying Amulet of glory";
                AbyssGrandExchange.buyAtGEIncrease(Statics.AMULET_OF_GLORY_FULL, 1, 0);
            }
        } else if (Inventory.contains(Statics.RING_OF_WEALTH_PATTERN)) {
            if (API.openBank()) {
                Bank.deposit(a -> a.getName().matches(Statics.RING_OF_WEALTH_PATTERN.pattern()), 1);
            }
        } else if (Equipment.contains(Statics.RING_OF_WEALTH_PATTERN)) {
            Equipment.unequip(a -> a.getName().matches(Statics.RING_OF_WEALTH_PATTERN.pattern()));
            Time.sleep(Random.nextInt(275, 325));
        } else if ((Inventory.containsAnyExcept(Statics.LOBSTER) || !Inventory.contains(Statics.LOBSTER)) && !Areas.ABYSS_OUTSIDE.contains(me)) {
            if (API.openBank()) {
                if (Inventory.containsAnyExcept(Statics.LOBSTER)) {
                    Abyss.status = "Deposit Inventory";
                    Bank.depositInventory();
                    Time.sleep(Random.nextInt(275, 325));
                } else if (!Inventory.contains(Statics.LOBSTER)) {
                    if (Bank.contains(Statics.LOBSTER)) {
                        Abyss.status = "Withdraw lobsters";
                        Bank.withdrawAll(Statics.LOBSTER);
                        Time.sleep(Random.nextInt(275, 325));
                    } else {
                        Statics.buyLobster = true;
                    }
                }
            }
        } else if (Areas.ABYSS_OUTSIDE.contains(me)) {
            if (Inventory.contains(Pattern.compile(".*pouch$"))) {
                Statics.gettingPouches = false;
            }
            if (Health.getPercent() > 55) {
                if (Areas.ABYSS_KILL_1.contains(me)) {
                    if (AttackStyle.getCurrentStyle() != AttackStyle.ATTACK) {
                        AttackStyle.changeStyleTo(AttackStyle.ATTACK);
                    }
                    if (Varps.get(172) == 0) {
                        InterfaceComponent interfaceComponent = Interfaces.getComponent(593, 29);
                        if (interfaceComponent != null && interfaceComponent.isVisible()) {
                            interfaceComponent.interact("Auto retaliate");
                            Time.sleep(Random.nextInt(275, 325));
                        } else {
                            API.openTab(Tab.COMBAT);
                        }
                    }
                    Pickable drop = Pickables.getNearest(a -> a.getId() == 5510 || a.getId() == 5512 || a.getId() == 5514);
                    if (drop == null) {
                        Npc npc = Npcs.getNearest(a -> a.getName().equals("Abyssal leech") || a.getName().equals("Abyssal guardian") && a.getTarget() == me);
                        if (npc == null) {
                            npc = Npcs.getNearest(a -> a.getName().equals("Abyssal leech") || a.getName().equals("Abyssal guardian") && a.getTarget() == null && a.getAnimation() == -1);
                        }
                        if (npc != null) {
                            if (me.getTarget() == null) {
                                Abyss.status = "Attack abyss";
                                npc.interact("Attack");
                                Time.sleepUntil(() -> me.getTarget() == null, 5000);
                            }
                        }
                    } else {
                        Abyss.status = "Pick pouch";
                        drop.interact("Take");
                        Time.sleepUntil(() -> !me.isMoving(), 1000);
                    }
                } else {
                    Abyss.status = "Walking";
                    Movement.walkTo(Areas.ABYSS_KILL_1.getCenter());
                    Time.sleep(800);
                }
            } else {
                if (Inventory.contains(Statics.LOBSTER)) {
                    Item item = Inventory.getFirst(Statics.LOBSTER);
                    if (item != null) {
                        Abyss.status = "Eating";
                        item.interact("Eat");
                        Time.sleep(350);
                    }
                } else {
                    if (API.openTab(Tab.EQUIPMENT)) {
                        Abyss.status = "Teleporting";
                        Equipment.interact(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), "Edgeville");
                        Time.sleepUntil(() -> !me.isAnimating() || Areas.EDGEVILLE.contains(me), 4500);
                    }
                }
            }
        } else if (!Abyss.escape()) {
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
            Statics.hopWorld = true;
        }
        return Random.nextInt(300, 500);
    }
}
