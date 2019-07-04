package com.aio;

import com.aio.universalTraining.tasks.api.Api;
import com.aio.universalTraining.tasks.utility.Statics;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;

import java.awt.*;

@ScriptMeta(name = "TEST", developer = "Raxi", version = 1.0, category = ScriptCategory.MONEY_MAKING, desc = "TEST")

public class Test extends Script implements RenderListener {
    private String a;


    @Override
    public int loop() {
        a = "Hi";
        if (Api.buy(Statics.AMULET_OF_GLORY_FULL, 20000, 1)) {
            org.rspeer.ui.Log.info("WORKSS");
        }
        return Random.nextInt(300, 500);
    }

    @Override
    public void notify(RenderEvent renderEvent) {
        Graphics g = renderEvent.getSource();
        int x = 20;
        int y = 20;
        g.drawString(a, x, y);
    }
}
