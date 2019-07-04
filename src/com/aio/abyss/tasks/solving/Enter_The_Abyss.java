package com.aio.abyss.tasks.solving;

import com.aio.abyss.Abyss;
import com.aio.abyss.tasks.utility.Areas;
import com.aio.abyss.tasks.utility.Statics;
import com.api.API;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Equipment;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

public class Enter_The_Abyss extends Task {

    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Varps.get(492) <= 4 && Varps.get(491) <= 57344;
    }

    @Override
    public int execute() {
        SceneObject sceneObject = SceneObjects.getNearest(7471);
        if (Varps.get(492) == 0) {
            Npc npc = Npcs.getNearest("Mage of zamorak");
            if (npc != null && me.getY() > 3520) {
                Abyss.status = "Talking to Zamorak mage";
                npc.interact("Talk-to");
                Time.sleep(Random.nextInt(1250, 1750));
            } else if (Players.getLocal().getY() > 3520 && sceneObject == null) {
                Abyss.status = "Walk to zamorak mage";
                Movement.walkTo(new Position(3105, 3556));
                Time.sleep(1000);
            } else if (Areas.EDGEVILLE.contains(me)) {
                Abyss.status = "Jumping over ditch";
                InterfaceComponent interfaceComponent = Interfaces.getComponent(475, 11);
                SceneObject wall = SceneObjects.getNearest("Wilderness Ditch");
                if (Inventory.containsAnyExcept(Statics.VARROCK_TELEPORT) || !Inventory.contains(Statics.VARROCK_TELEPORT)) {
                    if (Bank.isOpen()) {
                        if (Inventory.containsAnyExcept(Statics.VARROCK_TELEPORT)) {
                            Abyss.status = "Deposit inventory";
                            Bank.depositInventory();
                            Time.sleep(Random.nextInt(225, 300));
                        } else if (!Inventory.contains(Statics.VARROCK_TELEPORT)) {
                            Bank.withdrawAll(Statics.VARROCK_TELEPORT);
                            Time.sleep(Random.nextInt(225, 300));
                        }
                    } else {
                        Abyss.status = "Opening bank";
                        Bank.open();
                        Time.sleepUntil(Bank::isOpen, 2000);
                    }
                } else if (interfaceComponent != null && interfaceComponent.isVisible()) {
                    interfaceComponent.interact("Enter Wilderness");
                    Time.sleep(Random.nextInt(400, 600));
                    Time.sleepUntil(() -> me.getY() > 3520 && !me.isAnimating(), 3000);
                } else if (wall != null) {
                    wall.interact("Cross");
                    Time.sleepUntil(() -> (me.getY() > 3520 && !me.isAnimating()) || interfaceComponent != null && interfaceComponent.isVisible(), 3000);
                }
            } else {
                Abyss.status = "Teleporting to edgeville";
                if (API.openTab(Tab.EQUIPMENT)) {
                    Equipment.interact(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), "Edgeville");
                    Time.sleepUntil(() -> !me.isAnimating() || Areas.EDGEVILLE.contains(me), 4500);
                }
            }
        } else if (Varps.get(492) == 1) {
            if (Areas.ZAMORAK_MAGE_VARROCK_HOUSE.contains(me)) {
                Npc npc = Npcs.getNearest("Mage of Zamorak");
                InterfaceComponent chat = Dialog.getChatOption(a -> a.contains("Where do you get your runes from?") || a.contains("Yes"));
                if (chat != null && chat.isVisible()) {
                    chat.interact("Continue");
                    Time.sleep(Random.nextInt(400, 600));
                } else if (npc != null) {
                    npc.interact("Talk-to");
                    Time.sleep(Random.nextInt(1250, 1750));
                }
            } else {
                Abyss.status = "Getting to Zammy mage in varrock";
                Movement.walkTo(Areas.ZAMORAK_MAGE_VARROCK_HOUSE.getCenter());
                Time.sleep(Random.nextInt(1750, 2250));
            }
        } else if (Varps.get(492) == 2 && Varps.get(491) != 57344) {
            if (Varps.get(491) == 0) {
                if (Areas.AUBURY_RUNES_SHOP.contains(me)) {
                    Npc npc = Npcs.getNearest("Aubury");
                    if (npc != null) {
                        npc.interact("Teleport");
                        Time.sleep(3500);
                    }
                } else {
                    Movement.walkTo(Areas.AUBURY_RUNES_SHOP.getCenter());
                    Time.sleep(Random.nextInt(1750, 2250));
                }
            } else if (Varps.get(491) == 16384) {
                SceneObject sceneObject1 = SceneObjects.getNearest(7471);
                if (sceneObject1 != null) {
                    Abyss.status = "Teleporting to Draynor";
                    if (API.openTab(Tab.EQUIPMENT)) {
                        Equipment.interact(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), "Draynor Village");
                        Time.sleepUntil(() -> !me.isAnimating(), 4500);
                    }
                } else if (Areas.UNDERGROUND_WIZARD_TOWER.contains(me)) {
                    Npc npc = Npcs.getNearest("Sedridor");
                    if (npc != null) {
                        SceneObject door = SceneObjects.getNearest(a -> a.getId() == 1535 && a.getPosition().equals(new Position(3108, 9570)));
                        if (door == null) {
                            Abyss.status = "Teleporting to mine";
                            npc.interact("Teleport");
                            Time.sleep(Random.nextInt(1250, 1750));
                        } else {
                            Abyss.status = "Open door";
                            door.interact("Open");
                            Time.sleep(Random.nextInt(1250, 1750));
                        }
                    }
                } else if (Areas.WIZARD_TOWER_LEDDER_ROOM.contains(me)) {
                    SceneObject ledder = SceneObjects.getNearest("Ladder");
                    if (ledder != null) {
                        ledder.interact("Climb-down");
                        Time.sleep(Random.nextInt(1750, 2250));
                    }
                } else {
                    Movement.walkTo(Areas.WIZARD_TOWER_LEDDER_ROOM.getCenter());
                    Time.sleep(Random.nextInt(1750, 2250));
                }
            } else if (Varps.get(491) == 24576) {
                SceneObject sceneObject1 = SceneObjects.getNearest(7471);
                if (sceneObject1 != null) {
                    if (API.openTab(Tab.EQUIPMENT)) {
                        Equipment.interact(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), "Edgeville");
                        Time.sleepUntil(() -> !me.isAnimating() || Areas.EDGEVILLE.contains(me), 4500);
                    }
                } else if (Areas.EDGEVILLE.contains(me) || Areas.WILDERNESS_LEVER_AREA.contains(me)) {
                    SceneObject lever = SceneObjects.getNearest("Lever");
                    if (lever != null) {
                        InterfaceComponent chat = Dialog.getChatOption(a -> a.contains("Yes please, don't show this message again."));
                        if (chat != null && chat.isVisible()) {
                            chat.interact("Continue");
                            Time.sleep(256);
                        } else {
                            lever.interact("Pull");
                            Time.sleep(Random.nextInt(1750, 2250));
                        }
                    } else {
                        Abyss.status = "Walk to lever";
                        Movement.walkTo(new Position(3093, 3477));
                        Time.sleep(Random.nextInt(1750, 2250));
                    }
                } else if (Areas.ARDOUGNE_ESSENCE_AREA.contains(me)) {
                    Npc npc = Npcs.getNearest("Wizard Cromperty");
                    if (npc != null) {
                        npc.interact("Teleport");
                        Time.sleep(3500);
                    }
                } else {
                    Movement.walkTo(Areas.ARDOUGNE_ESSENCE_AREA.getCenter());
                    Time.sleep(Random.nextInt(1750, 2250));
                }
            }
        } else if ((Varps.get(491) == 57344 && (Varps.get(492) == 2 || Varps.get(492) == 4)) || Varps.get(492) == 3) {
            if (Areas.ZAMORAK_MAGE_VARROCK_HOUSE.contains(me)) {
                Npc npc = Npcs.getNearest("Mage of Zamorak");
                InterfaceComponent chat = Dialog.getChatOption(a -> a.contains("dangerous"));
                if (chat != null && chat.isVisible()) {
                    chat.interact("Continue");
                    Time.sleep(Random.nextInt(400, 600));
                } else if (npc != null) {
                    npc.interact("Talk-to");
                    Time.sleep(Random.nextInt(1250, 1750));
                }
            } else {
                SceneObject sceneObject1 = SceneObjects.getNearest(7471);
                if (sceneObject1 != null) {
                    Item vTab = Inventory.getFirst(Statics.VARROCK_TELEPORT);
                    if (vTab != null) {
                        vTab.interact("Break");
                        Time.sleep(5000);
                    }
                } else {
                    Abyss.status = "Getting to Zammy mage in varrock";
                    Movement.walkTo(Areas.ZAMORAK_MAGE_VARROCK_HOUSE.getCenter());
                    Time.sleep(Random.nextInt(1750, 2250));
                }
            }
        }
        return Random.nextInt(300, 500);
    }
}
