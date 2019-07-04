package com.aio.wineOfZamorak.tasks.solving;

import com.aio.abyss.Abyss;
import com.aio.wineOfZamorak.tasks.utility.Statics;
import com.api.API;
import com.api.HopWorld;
import com.api.personalGrandExchange.PersonalGrandExchange;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.Worlds;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

public class Settings extends Task {

    private Player me;

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && Interfaces.getContinue() != null || Statics.hopWorld || (Movement.getRunEnergy() >= 25 && !Movement.isRunEnabled()) || Varps.get(172) == 0
                || Statics.wthdrawAllCoins;
    }

    @Override
    public int execute() {
        if (Interfaces.getContinue() != null) {
            Interfaces.getContinue().interact("Continue");
            Time.sleep(Random.nextInt(650, 750));
        } else if (Statics.hopWorld) {
            if (Statics.badWorld == Worlds.getCurrent() || Statics.badWorld == -1) {
                Abyss.status = "Hopworld";
                HopWorld.hopToRandomP2p();
            } else {
                Statics.hopWorld = false;
            }
        } else if (Movement.getRunEnergy() >= 25 && !Movement.isRunEnabled()) {
            Abyss.status = "Turning on Run Energy";
            Movement.toggleRun(true);
            Time.sleep(Random.nextInt(225, 300));
        } else if (Varps.get(172) == 0) {
            InterfaceComponent interfaceComponent = Interfaces.getComponent(593, 29);
            if (interfaceComponent != null && interfaceComponent.isVisible()) {
                interfaceComponent.interact("Auto retaliate");
                Time.sleep(Random.nextInt(275, 325));
            } else {
                API.openTab(Tab.COMBAT);
            }
        } else if (Statics.wthdrawAllCoins) {
            if (PersonalGrandExchange.withdrawAllCoins()) {
                Statics.wthdrawAllCoins = false;
            }
        }
        return Random.nextInt(300, 500);
    }
}
