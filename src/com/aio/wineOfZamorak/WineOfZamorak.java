package com.aio.wineOfZamorak;

import com.aio.wineOfZamorak.tasks.solving.BuyItems;
import com.aio.wineOfZamorak.tasks.solving.CollectWines;
import com.aio.wineOfZamorak.tasks.solving.MagicTrainingTo33;
import com.aio.wineOfZamorak.tasks.solving.Settings;
import com.aio.wineOfZamorak.tasks.utility.Statics;
import org.rspeer.runetek.api.Worlds;
import org.rspeer.runetek.api.commons.StopWatch;
import org.rspeer.runetek.event.listeners.ChatMessageListener;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.ChatMessageEvent;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.TaskScript;

import java.awt.*;

@ScriptMeta(name = "WineOfZamorakGrabber", developer = "Raxi", version = 1.1, category = ScriptCategory.MONEY_MAKING, desc = "Grabbing Wine of zamorak")
public class WineOfZamorak extends TaskScript implements RenderListener, ChatMessageListener {
    public static String status = "Starting Script";
    public static int wineGrabbed = 0;
    public static int winesCollected = 0;
    private StopWatch stopWatch;
    private int winePrice = 1766;
    private int lawRunePrice = 207;
    private int deathCounter = 0;


    @Override
    public void onStart() {
        stopWatch = StopWatch.start();
        Statics.badWorld = Worlds.getCurrent();
        Statics.hopWorld = true;
        submit(new Settings(), new BuyItems(), new MagicTrainingTo33(), new CollectWines());
    }

    @Override
    public void notify(RenderEvent renderEvent) {
        Graphics g2D = renderEvent.getSource();
        int y = 20;
        int x = 20;
        g2D.drawString("Welcome to Wine of zamorak Grabber!", x, y += 25);
        g2D.drawString("Time: " + stopWatch.toElapsedString(), x, y += 25);
        g2D.drawString("Status: " + status, x, y += 25);
        g2D.drawString("Deaths: " + deathCounter, x, y += 25);
        g2D.drawString("Wines collected: " + winesCollected + " Profit: ( " + ((winesCollected * winePrice) - (winesCollected * lawRunePrice)) + " )", x, y += 25);
    }

    @Override
    public void notify(ChatMessageEvent chatMessageEvent) {
        if (chatMessageEvent.getMessage().contains("Oh dear, you are dead!")) {
            deathCounter += 1;
        } else if (chatMessageEvent.getMessage().contains("You haven't got enough")) {
            Statics.wthdrawAllCoins = true;
        }
    }
}
