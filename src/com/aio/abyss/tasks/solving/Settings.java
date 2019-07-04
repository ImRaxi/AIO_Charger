package com.aio.abyss.tasks.solving;

import com.aio.abyss.Abyss;
import com.aio.abyss.tasks.utility.Areas;
import com.aio.abyss.tasks.utility.Statics;
import com.aio.tutorialIsland.TutorialIsland;
import com.api.API;
import com.api.HopWorld;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.Worlds;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Equipment;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

public class Settings extends Task {
    private Player me;
    private InterfaceComponent resizedMode;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        resizedMode = Interfaces.getComponent(548, 33);
        return me != null && ((Interfaces.getContinue() != null && !Areas.INSIDE_NATURE_ALTAR.contains(me) && !Areas.INSIDE_FIRE_ALTAR.contains(me) && !Areas.INSIDE_EARTH_ALTAR.contains(me)
                && !Areas.AGILITY_TRAINNG_AREA.contains(me) && !Areas.AGILITY_TRAINNG_AREA_UPSTAIRS.contains(me) && !Areas.AGILITY_TRAINNG_AREA_UPSTAIRS2.contains(me))
                || (Movement.getRunEnergy() >= 25 && !Movement.isRunEnabled()) || (Statics.hopWorld && !Areas.ABYSS_OUTSIDE.contains(me)) || (Equipment.contains(Statics.RING_OF_WEALTH_EMPTY)
                || Inventory.contains(Statics.RING_OF_WEALTH_EMPTY) && !Statics.sellWealth)) || resizedMode == null
                || (!API.isInClanChat() && Areas.EDGEVILLE.contains(me) && Game.isLoggedIn() && Statics.joinClanChat);
    }

    @Override
    public int execute() {
        if (Interfaces.getContinue() != null && Interfaces.getContinue().isVisible()) {
            Abyss.status = "Chat Continue";
            Interfaces.getContinue().interact("Continue");
            Time.sleep(Random.nextInt(650, 750));
        } else if (Movement.getRunEnergy() >= 25 && !Movement.isRunEnabled()) {
            Abyss.status = "Turning on Run Energy";
            Movement.toggleRun(true);
            Time.sleep(Random.nextInt(225, 300));
        } else if (Statics.hopWorld) {
            Abyss.status = "Hopping to random world";
            if (Worlds.getCurrent() == -1 || Worlds.getCurrent() == Statics.badWorld && me.getTarget() == null) {
                HopWorld.hopToRandomP2p();
            } else {
                Statics.hopWorld = false;
                Statics.badWorld = Worlds.getCurrent();
            }
        } else if (!API.isInClanChat() && Game.isLoggedIn() && Statics.joinClanChat) {
            if (API.openTab(Tab.CLAN_CHAT)) {
                if (API.joinClanChat(Abyss.muleName)) {
                    Statics.joinClanChat = false;
                }
            }
        } else if (Equipment.contains(Statics.RING_OF_WEALTH_EMPTY) || Inventory.contains(Statics.RING_OF_WEALTH_EMPTY)) {
            Abyss.status = "Changing Ring of Wealth";
            if (Equipment.contains(Statics.RING_OF_WEALTH_EMPTY)) {
                if (API.openTab(Tab.EQUIPMENT)) {
                    Equipment.unequip(Statics.RING_OF_WEALTH_EMPTY);
                }
            } else if (Inventory.contains(Statics.RING_OF_WEALTH_EMPTY)) {
                Statics.sellWealth = true;
            }
        } else if (resizedMode == null) {
            TutorialIsland.status = "Setting Fixed Mode";
            if (Tab.OPTIONS.isOpen()) {
                InterfaceComponent fixedMode = Interfaces.getComponent(261, 33);
                if (fixedMode != null) {
                    fixedMode.interact("Fixed mode");
                    Time.sleep(Random.nextInt(1000, 1500));
                }
            } else {
                InterfaceComponent settings = Interfaces.getComponent(164, 38);
                if (settings != null) {
                    settings.interact("Options");
                    Time.sleep(Random.nextInt(250, 350));
                }
            }
        }
        return Random.nextInt(300, 500);
    }
}

