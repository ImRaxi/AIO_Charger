package com.api;

import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.GrandExchange;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.input.Keyboard;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;
import org.rspeer.runetek.api.scene.SceneObjects;

import java.awt.event.KeyEvent;

public class API {

    public static void useItemOnObject(Item item, String name) {
        if (Inventory.isItemSelected()) {
            SceneObject sceneObject = SceneObjects.getNearest(a -> a.getName().equals(name));
            if (sceneObject != null) {
                sceneObject.interact("Use");
            }
        } else {
            item.interact("Use");
        }
    }

    public static void useItemOnObject(Item item, int id) {
        if (Inventory.isItemSelected()) {
            SceneObject sceneObject = SceneObjects.getNearest(a -> a.getId() == id);
            if (sceneObject != null) {
                sceneObject.interact("Use");
            }
        } else {
            item.interact("Use");
        }
    }

    public static void selectAutoCastSpell(int spellNumber) {
        InterfaceComponent spell = Interfaces.getComponent(201, 1, spellNumber);
        if (spell != null && spell.isVisible()) {
            spell.interact(ActionOpcodes.INTERFACE_ACTION);
        } else {
            if (Tab.COMBAT.isOpen()) {
                InterfaceComponent choose = Interfaces.getComponent(593, 25);
                if (choose != null && choose.isVisible()) {
                    choose.interact(ActionOpcodes.INTERFACE_ACTION);
                }
            } else {
                openTab(Tab.COMBAT);
            }
        }
    }

    public static boolean openTab(Tab tab) {
        if (tab.isOpen()) {
            return true;
        } else {
            InterfaceComponent interfaceComponent = Interfaces.getComponent(548, tab.getComponentIndex());
            return interfaceComponent != null && interfaceComponent.isVisible() && interfaceComponent.interact(ActionOpcodes.INTERFACE_ACTION);
        }
    }

    public static boolean openBank() {
        if (Bank.isOpen()) {
            return true;
        } else {
            Bank.open();
            Time.sleepUntil(Bank::isOpen, Random.nextInt(3000, 4000));
        }
        return false;
    }

    public static boolean closeInterfaces() {
        if (!Bank.isOpen() && !GrandExchange.isOpen()) {
            return true;
        } else {
            Keyboard.pressEventKey(KeyEvent.VK_ESCAPE);
        }
        return false;
    }

    public static boolean isInClanChat() {
        InterfaceComponent interfaceComponent = Interfaces.getComponent(7, 5);
        return interfaceComponent != null && !interfaceComponent.getText().toLowerCase().equals("None".toLowerCase());
    }

    public static boolean joinClanChat(String name) {
        InterfaceComponent joinButton = Interfaces.getComponent(7, 21);
        InterfaceComponent enterName = Interfaces.getComponent(162, 45);
        if (enterName != null && enterName.isVisible()) {
            if (enterName.getText().toLowerCase().contains(name.toLowerCase())) {
                Keyboard.pressEnter();
                return true;
            } else {
                Keyboard.sendText(name);
                Time.sleep(Random.nextInt(500, 700));
            }
        } else if (joinButton != null) {
            joinButton.interact("Join Chat");
            Time.sleep(Random.nextInt(1500, 2000));
        }
        return false;
    }

    public static int notedItem(int itemID) {
        return itemID + 1;
    }
}
