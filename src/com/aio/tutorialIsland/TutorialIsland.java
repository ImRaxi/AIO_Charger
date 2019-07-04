package com.aio.tutorialIsland;

import com.aio.tutorialIsland.tasks.solving.*;
import com.aio.tutorialIsland.tasks.utility.Overall;
import com.aio.tutorialIsland.tasks.utility.Statics;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.Worlds;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

import java.awt.*;

@ScriptMeta(name = "tutorialIsland", developer = "Raxi", version = 1.2, category = ScriptCategory.MONEY_MAKING, desc = "Going through Tutorial island like a boss.")
public class TutorialIsland extends TaskScript implements RenderListener {
    public static String status = "Loading Script";
    public static int randomInt = Random.nextInt(25, 50);
    public static int a = 0;

    @Override
    public void onStart() {
        Statics.setGraphics = true;
        Statics.setEscapeClose = true;
        Statics.setShiftDropping = true;
        submit(new Overall(), new Gielinor_Guide(), new Survival_Expert(), new Master_Chef(), new Quest_Guide(), new Mining_Instructor(),
                new Combat_Instructor(), new Banker(), new Account_Guide(), new Brother_Brace(), new Magic_Instructor(), new SetSettings(), new Walk_To_GrandExchange());
    }

    @Override
    public void onStop() {
        Log.info("Stopped Script");
    }

    @Override
    public void notify(RenderEvent renderEvent) {
        Graphics g = renderEvent.getSource();
        int y = 20;
        int x = 20;
        g.drawString("Welcome to Tutorial Island " + Players.getLocal().getName() + "!", x, y += 20);
        g.drawString("Current status: " + status, x, y += 20);
        g.drawString("Current world: " + Worlds.getCurrent(), x, y += 20);
        g.drawString("Varps: " + Varps.get(1055), x, y += 20);
    }
}
