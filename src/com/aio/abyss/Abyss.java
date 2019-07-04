package com.aio.abyss;

import com.aio.abyss.tasks.solving.*;
import com.aio.abyss.tasks.utility.Areas;
import com.aio.abyss.tasks.utility.Statics;
import com.api.API;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Worlds;
import org.rspeer.runetek.api.commons.StopWatch;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.tab.Equipment;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.event.listeners.ChatMessageListener;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.ChatMessageEvent;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.TaskScript;

import java.awt.*;

@ScriptMeta(name = "abyss", developer = "Raxi", version = 2.64, category = ScriptCategory.MONEY_MAKING, desc = "Crafting natures")

public class Abyss extends TaskScript implements RenderListener, ChatMessageListener {

    public static String status = "Loading Script";
    public static int tripCounter = 0;
    public static String muleName = "nieniebojesi";
    private static int deathCounter = 0;
    private StopWatch stopWatch;

    public static boolean escape() {
        Player player = Players.getNearest(a -> a.getTarget() == Players.getLocal() && Areas.WILDY_DANGEROUS_AREA.contains(a));
        if (player != null && Areas.WILDY_DANGEROUS_AREA.contains(Players.getLocal())) {
            if (API.openTab(Tab.EQUIPMENT)) {
                Equipment.interact(a -> a.getName().matches(Statics.AMULET_OF_GLORY_PATTERN.pattern()), "Edgeville");
                Time.sleepUntil(() -> !Players.getLocal().isAnimating() || Areas.EDGEVILLE.contains(Players.getLocal()), 4500);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onStart() {
        stopWatch = StopWatch.start();
        Statics.hopWorld = true;
        Statics.badWorld = Worlds.getCurrent();
        submit(new Settings(), new MuleTrade(), new BuyItems(), new LumbridgeDeath(), new Melee_Training(), new Agility_Training(), new Rune_Mysteries(), new Mining_Training(), new Enter_The_Abyss(),
                new Getting_Pouches(), new Making_Runes());
    }

    @Override
    public void notify(RenderEvent renderEvent) {
        Graphics g2D = renderEvent.getSource();
        int y = 20;
        int x = 20;
        g2D.drawString("Welcome to abyss crafter!", x, y += 20);
        g2D.drawString("Current status: " + status, x, y += 15);
        g2D.drawString("Time: " + stopWatch.toElapsedString(), x, y += 15);
        g2D.drawString("Current world: " + Worlds.getCurrent(), x, y += 15);
        if (Skills.getCurrentLevel(Skill.MINING) >= 31) {
            g2D.drawString("Runecrafting level: " + Skills.getCurrentLevel(Skill.RUNECRAFTING), x, y += 15);
            g2D.drawString("Trips: " + tripCounter, x, y += 15);
            g2D.drawString("DeathCounter: " + deathCounter, x, y += 15);
        }
    }

    @Override
    public void notify(ChatMessageEvent chatMessageEvent) {
        if (chatMessageEvent.getMessage().contains("You haven't got enough")) {
            Statics.wthdrawAllCoins = true;
        } else if (chatMessageEvent.getMessage().contains("Oh dear, you are dead!")) {
            Statics.hopWorld = true;
            deathCounter += 1;
            Statics.emptySmallPouch = false;
            Statics.emptyMediumPouch = false;
            Statics.emptyLargePouch = false;
            Statics.emptyGiantPouch = false;
        } else if (chatMessageEvent.getMessage().toLowerCase().equals("zapraszam".toLowerCase()) && Skills.getCurrentLevel(Skill.RUNECRAFTING) >= 44) {
            Statics.tradeLootMule = true;
            Statics.wthdrawAllCoins = false;
        }
    }
}
