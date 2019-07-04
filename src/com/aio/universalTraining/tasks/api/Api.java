package com.aio.universalTraining.tasks.api;

import com.aio.abyss.tasks.utility.Areas;
import com.aio.universalTraining.UniversalTraining;
import com.aio.universalTraining.tasks.enums.AttackStyle;
import com.aio.universalTraining.tasks.utility.Statics;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.GrandExchange;
import org.rspeer.runetek.api.component.GrandExchangeSetup;
import org.rspeer.runetek.api.component.tab.*;
import org.rspeer.runetek.api.input.Keyboard;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.providers.RSGrandExchangeOffer;

import java.awt.event.KeyEvent;

public class Api {

    /*GRAND EXCHANGE*/

    private static final Area GRAND_EXCHANGE = Area.rectangular(3144, 3508, 3184, 3472);

    public static boolean buy(int itemID, int price, int quantity) {
        if (!GRAND_EXCHANGE.contains(Players.getLocal())) {
            if (!Tab.EQUIPMENT.isOpen()) {
                Tabs.open(Tab.EQUIPMENT);
                Time.sleep(350);
            }

            teleportToGrandExchange();
        }

        if (!GrandExchange.isOpen()) {
            Npc npc = Npcs.getNearest(a -> a.containsAction("Exchange"));
            if (npc != null)
                npc.interact("Exchange");
            Time.sleep(2000);
        }

        if (GrandExchange.isOpen() && GrandExchange.getOffers(a -> a.getItemId() == itemID) != null) {
            if (GrandExchange.collectAll()) {
                return true;
            }
        }

        if (GrandExchangeSetup.isOpen()) {
            if (GrandExchangeSetup.getItem() == null) {
                GrandExchangeSetup.setItem(itemID);
                Time.sleep(1000);
            } else if (GrandExchangeSetup.getPricePerItem() != price) {
                GrandExchangeSetup.setPrice(price);
                Time.sleep(1000);
            } else if (GrandExchangeSetup.getQuantity() != quantity) {
                GrandExchangeSetup.setQuantity(quantity);
                Time.sleep(1000);
            } else if (GrandExchangeSetup.confirm()) {
                Time.sleep(3000);
            }
        }

        if (!GrandExchangeSetup.isOpen() && GrandExchange.getOffers(a -> a.getItemId() == itemID).length < 1 && GrandExchange.createOffer(RSGrandExchangeOffer.Type.BUY)) {
            UniversalTraining.status = "Here";

            Time.sleep(750);
        }

        return false;
    }

    public static void teleportToGrandExchange() {
        Equipment.interact(a -> a.getName().matches(Statics.RING_OF_WEALTH.pattern()), "Grand Exchange");
        Time.sleepUntil(() -> Areas.GRAND_EXCHANGE.contains(Players.getLocal()), 8000);
    }

    public static boolean setCorrectAttackStyle() {
        if (Skills.getCurrentLevel(Skill.ATTACK) == Skills.getCurrentLevel(Skill.STRENGTH) && Skills.getCurrentLevel(Skill.STRENGTH) == Skills.getCurrentLevel(Skill.DEFENCE)
                && AttackStyle.getCurrentStyle() != AttackStyle.ATTACK) {
            AttackStyle.changeStyleTo(AttackStyle.ATTACK);
        } else if (Skills.getCurrentLevel(Skill.ATTACK) - Skills.getCurrentLevel(Skill.STRENGTH) >= 10 && AttackStyle.getCurrentStyle() != AttackStyle.STRENGTH) {
            AttackStyle.changeStyleTo(AttackStyle.STRENGTH);
        } else if (Skills.getCurrentLevel(Skill.STRENGTH) - Skills.getCurrentLevel(Skill.DEFENCE) >= 10 && AttackStyle.getCurrentStyle() != AttackStyle.DEFENCE) {
            AttackStyle.changeStyleTo(AttackStyle.DEFENCE);
        }
        return true;
    }

    public static boolean closeInterfaces() {
        if (!Bank.isOpen() && !GrandExchange.isOpen()) {
            return true;
        } else {
            Keyboard.pressEventKey(KeyEvent.VK_ESCAPE);
        }
        return false;
    }

    public static boolean imUnderAttack() {
        Npc npc = Npcs.getNearest(a -> a.getTarget() == Players.getLocal());
        Player player = Players.getNearest(a -> a.getTarget() == Players.getLocal());
        return Players.getLocal() != null && (npc != null || player != null);
    }
}
