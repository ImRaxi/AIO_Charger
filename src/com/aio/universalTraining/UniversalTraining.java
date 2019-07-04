package com.aio.universalTraining;

import com.aio.universalTraining.tasks.basicItems.GetBasicItems;
import com.aio.universalTraining.tasks.grandExchange.BuyItems;
import com.aio.universalTraining.tasks.settings.SetSettings;
import com.aio.universalTraining.tasks.training.CompletingSlayerTask;
import com.aio.universalTraining.tasks.training.GettingSlayerTask;
import com.aio.universalTraining.tasks.utility.Statics;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.chatter.Chat;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.event.listeners.ChatMessageListener;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.ChatMessageEvent;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.TaskScript;

import java.awt.*;

@ScriptMeta(developer = "Raxi", name = "UniverealTraining", version = 1.0, desc = ":)")
public class UniversalTraining extends TaskScript implements RenderListener, ChatMessageListener {

    public static String status = "Universal Training Script made by Raxi";

    @Override
    public void onStart() {
        Statics.depositAllItems = true;
        Statics.setGraphics = true;
        Statics.slayerExperience = Skills.getExperience(Skill.SLAYER);
        submit(new SetSettings(), new BuyItems(), new GetBasicItems(), new GettingSlayerTask(), new CompletingSlayerTask());
    }

    @Override
    public void notify(RenderEvent renderEvent) {
        Graphics graphics = renderEvent.getSource();
        int x = 20;
        int y = 40;
        graphics.drawString("Current status: " + status, x, y);
        graphics.drawString("Slayer level: " + Statics.slayerExperience + " (" + Skills.getCurrentLevel(Skill.SLAYER) + ")", x, y += 20);
        graphics.drawString("Slayer task: " + Statics.currentTaskName, x, y += 20);
        graphics.drawString("Slayer amount to kill left: " + Statics.amountToKill, x, y += 20);
    }

    @Override
    public void notify(ChatMessageEvent chatMessageEvent) {
        if (chatMessageEvent.getMessage().contains("kill")) {
            Statics.currentTaskName = chatMessageEvent.getMessage().substring(24, chatMessageEvent.getMessage().indexOf(";"));
            Statics.amountToKill = Integer.parseInt(chatMessageEvent.getMessage().substring(chatMessageEvent.getMessage().lastIndexOf("only ") + 5, chatMessageEvent.getMessage().indexOf(" more")));
        } else if (chatMessageEvent.getMessage().toLowerCase().contains("You need".toLowerCase())) {
            Statics.getNewSlayerTask = true;
        } else if (chatMessageEvent.getMessage().toLowerCase().equals("Hi".toLowerCase()) || chatMessageEvent.getMessage().toLowerCase().equals("Hello".toLowerCase())) {
            Time.sleep(3500);
            Chat.send("Hi");
        } else if (chatMessageEvent.getMessage().toLowerCase().equals("Sup".toLowerCase())) {
            Time.sleep(3500);
            Chat.send("Slayin ;p");
        } else if (chatMessageEvent.getMessage().toLowerCase().contains("return to a slayer master")) {
            Statics.currentTaskName = "none";
            Statics.getNewSlayerTask = true;
            Statics.amountToKill = 0;
        }
    }
}
