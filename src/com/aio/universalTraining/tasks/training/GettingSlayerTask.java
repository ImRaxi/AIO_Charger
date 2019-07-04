package com.aio.universalTraining.tasks.training;

import com.aio.universalTraining.UniversalTraining;
import com.aio.universalTraining.tasks.api.Api;
import com.aio.universalTraining.tasks.utility.Areas;
import com.aio.universalTraining.tasks.utility.Statics;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

public class GettingSlayerTask extends Task {

    private Player me;
    private boolean wait;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Statics.getNewSlayerTask;
    }

    @Override
    public int execute() {
        if (Areas.BURTHORPE_SLAYER_MASTER.contains(me)) {
            InterfaceComponent chatBoxOptions = Dialog.getChatOption(a -> a.contains("What's a slayer?") || a.contains("Wow, can you teach me?"));
            if (Dialog.isOpen() && !Dialog.canContinue() && chatBoxOptions != null && chatBoxOptions.isVisible()) {
                UniversalTraining.status = "Getting slayer Task";
                chatBoxOptions.interact("Continue");
                Time.sleep(1000);
            } else {
                if (wait && !Dialog.isOpen()) {
                    Statics.getNewSlayerTask = false;
                } else {
                    Npc npc = Npcs.getNearest(Statics.TURAEL);
                    if (npc != null) {
                        if (npc.interact("Assignment")) {
                            Time.sleepUntil(Dialog::isOpen, 5000);
                            wait = true;
                        }
                    } else {
                        Movement.walkTo(new Position(2925, 3538));
                        Time.sleep(2000);
                    }
                }
            }
        } else if (Inventory.contains(Statics.GAMES_NECKLACE) && !Api.imUnderAttack()) {
            if (Dialog.isOpen() && !Dialog.canContinue() && Dialog.isViewingChatOptions()) {
                UniversalTraining.status = "Teleporting to Burthorpe";
                InterfaceComponent interfaceComponent = Dialog.getChatOption(a -> a.toLowerCase().contains("burthorpe") || a.toLowerCase().contains("Barbarian Outpost"));
                if (interfaceComponent != null && interfaceComponent.isVisible()) {
                    if (Random.nextInt(0, 100) > 92) {
                        interfaceComponent.interact("Barbarian Outpost.");
                        Time.sleepUntil(() -> Areas.BURTHORPE_SLAYER_MASTER.contains(me), 5000);
                    } else {
                        interfaceComponent.interact("Burthorpe.");
                        Time.sleepUntil(() -> Areas.BURTHORPE_SLAYER_MASTER.contains(me), 5000);
                    }
                }
            } else {
                UniversalTraining.status = "Rubbing necklace";
                Item item = Inventory.getFirst(Statics.GAMES_NECKLACE);
                if (item != null) {
                    item.interact("Rub");
                    Time.sleep(1000);
                }
            }
        } else {
            Api.teleportToGrandExchange();
        }
        return 500;
    }
}
