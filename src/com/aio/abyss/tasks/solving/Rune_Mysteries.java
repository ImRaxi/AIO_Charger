package com.aio.abyss.tasks.solving;

import com.aio.abyss.Abyss;
import com.aio.abyss.tasks.utility.AbyssGrandExchange;
import com.aio.abyss.tasks.utility.Areas;
import com.aio.abyss.tasks.utility.Statics;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

public class Rune_Mysteries extends Task {

    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Varps.get(63) < 6;
    }

    @Override
    public int execute() {
        if (Inventory.containsAll(Statics.VARROCK_TELEPORT, Statics.LUMBRIDGE_TELEPORT)) {
            if (Varps.get(63) == 0) {
                Npc npc = Npcs.getNearest("Duke Horacio");
                if (npc != null) {
                    SceneObject door = SceneObjects.getNearest(a -> a.getId() == 1543 && a.getPosition().equals(new Position(3207, 3222, 1)));
                    if (door == null) {
                        InterfaceComponent chat = Dialog.getChatOption(a -> a.contains("Talk about something else.")
                                || a.contains("Have you any quests for me?") || a.contains("Sure, no problem."));
                        if (chat != null && chat.isVisible()) {
                            chat.interact("Continue");
                            Time.sleep(256);
                        } else {
                            npc.interact("Talk-to");
                            Time.sleep(Random.nextInt(1250, 1750));
                        }
                    } else {
                        door.interact("Open");
                        Time.sleep(Random.nextInt(1250, 1750));
                    }
                } else {
                    Abyss.status = "Walking to Duke Horacio";
                    Movement.walkTo(new Position(3207, 3222, 1));
                    Time.sleep(Random.nextInt(1750, 2250));
                }
            } else if (Varps.get(63) == 1 || Varps.get(63) == 5) {
                if (Areas.UNDERGROUND_WIZARD_TOWER.contains(me)) {
                    Npc npc = Npcs.getNearest("Sedridor");
                    if (npc != null) {
                        SceneObject door = SceneObjects.getNearest(a -> a.getId() == 1535 && a.getPosition().equals(new Position(3108, 9570)));
                        if (door == null) {
                            InterfaceComponent chat = Dialog.getChatOption(a -> a.contains("I'm looking for the head wizard.")
                                    || a.contains("Ok, here you are."));
                            if (chat != null && chat.isVisible()) {
                                chat.interact("Continue");
                                Time.sleep(256);
                            } else {
                                Abyss.status = "Talk to Sedridor";
                                npc.interact("Talk-to");
                                Time.sleep(Random.nextInt(1250, 1750));
                            }
                        } else {
                            Abyss.status = "Open door";
                            door.interact("Open");
                            Time.sleep(Random.nextInt(1250, 1750));
                        }
                    }
                } else {
                    SceneObject ledderDown = SceneObjects.getNearest(a -> a.getId() == 2147 && a.getPosition().equals(new Position(3104, 3162)));
                    if (ledderDown != null && Areas.WIZARD_TOWER_LEDDER_ROOM.contains(me)) {
                        Abyss.status = "Climbing down to wizard tower basement";
                        ledderDown.interact("Climb-down");
                        Time.sleepUntil(() -> Areas.UNDERGROUND_WIZARD_TOWER.contains(me), 2000);
                    } else {
                        Abyss.status = "Walking to Wizard tower";
                        Movement.walkTo(Areas.WIZARD_TOWER_LEDDER_ROOM.getCenter());
                        Time.sleep(Random.nextInt(1750, 2250));
                    }
                }
            } else if (Varps.get(63) == 2) {
                InterfaceComponent chat = Dialog.getChatOption(a -> a.contains("Yes, certainly."));
                if (chat != null && chat.isVisible()) {
                    chat.interact("Continue");
                    Time.sleep(256);
                } else {
                    Npc npc = Npcs.getNearest("Sedridor");
                    if (npc != null) {
                        Abyss.status = "Talk to Sedridor";
                        npc.interact("Talk-to");
                        Time.sleep(Random.nextInt(1250, 1750));
                    }
                }
            } else if (Varps.get(63) == 3 || Varps.get(63) == 4) {
                if (Areas.AUBURY_RUNES_SHOP.contains(me)) {
                    InterfaceComponent chat = Dialog.getChatOption(a -> a.contains("I have been sent here with a package for you."));
                    if (chat != null && chat.isVisible()) {
                        chat.interact("Continue");
                        Time.sleep(256);
                    } else {
                        Npc npc = Npcs.getNearest("Aubury");
                        if (npc != null) {
                            Abyss.status = "Talk to Aubury";
                            npc.interact("Talk-to");
                            Time.sleep(Random.nextInt(1250, 1750));
                        }
                    }
                } else {
                    Abyss.status = "Walking to Augury shop";
                    Movement.walkTo(Areas.AUBURY_RUNES_SHOP.getCenter());
                    Time.sleep(Random.nextInt(1750, 2250));
                }
            }
        } else if (!Inventory.contains(Statics.VARROCK_TELEPORT)) {
            AbyssGrandExchange.buyAtGEIncrease(Statics.VARROCK_TELEPORT, 10, 0);
            Time.sleep(Random.nextInt(500, 750));
        } else if (!Inventory.contains(Statics.LUMBRIDGE_TELEPORT)) {
            AbyssGrandExchange.buyAtGEIncrease(Statics.LUMBRIDGE_TELEPORT, 10, 0);
            Time.sleep(Random.nextInt(500, 750));
        }
        return Random.nextInt(500, 800);
    }
}
