package com.aio.abyss.tasks.solving;

import com.aio.abyss.Abyss;
import com.aio.abyss.tasks.utility.Areas;
import com.aio.abyss.tasks.utility.Statics;
import com.api.API;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

public class Mining_Training extends Task {

    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Skills.getCurrentLevel(Skill.MINING) < 31;
    }

    @Override
    public int execute() {
        SceneObject essenceRock = SceneObjects.getNearest(7471);
        if (Inventory.contains(Statics.BRONZE_PICKAXE)) {
            if (Inventory.containsAnyExcept(Statics.BRONZE_PICKAXE, Statics.RUNE_ESSENCE, Statics.PURE_ESSENCE)) {
                if (Areas.VARROCK_EAST_BANK.contains(me)) {
                    if (API.openBank()) {
                        Abyss.status = "Deposit all except bronze pickaxe";
                        Bank.depositAllExcept(Statics.BRONZE_PICKAXE, Statics.RUNE_ESSENCE, Statics.PURE_ESSENCE);
                    }
                } else {
                    Abyss.status = "Walk to Varrock east bank";
                    Movement.walkTo(Areas.VARROCK_EAST_BANK.getCenter());
                    Time.sleep(Random.nextInt(1750, 2250));
                }
            } else if (essenceRock != null) {
                if (!Inventory.isFull()) {
                    if (!me.isAnimating()) {
                        Abyss.status = "Mining essence " + "Level: " + Skills.getCurrentLevel(Skill.MINING);
                        essenceRock.interact("Mine");
                        Time.sleepUntil(() -> !me.isAnimating() || Inventory.isFull(), 60000);
                    }
                } else {
                    Abyss.status = "DropAlll";
                    if (API.openTab(Tab.INVENTORY)) {
                        Item essences[] = Inventory.getItems();
                        for (Item i : essences) {
                            if (i.getId() == Statics.RUNE_ESSENCE || i.getId() == Statics.PURE_ESSENCE) {
                                i.interact("Drop");
                                Time.sleep(Random.nextInt(550, 650));
                            }
                        }
                    }
                }
            } else if (Areas.AUBURY_RUNES_SHOP.contains(me)) {
                Npc npc = Npcs.getNearest("Aubury");
                if (npc != null) {
                    Abyss.status = "Talk to Aubury";
                    npc.interact("Teleport");
                    Time.sleep(Random.nextInt(2750, 3250));
                }
            } else {
                Movement.walkTo(Areas.AUBURY_RUNES_SHOP.getCenter());
                Time.sleep(Random.nextInt(1750, 2250));
            }
        } else if (Game.isLoggedIn()) {
            Statics.buyBronzePickaxe = true;
        }
        return Random.nextInt(500, 800);
    }
}
