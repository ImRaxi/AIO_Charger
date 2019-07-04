package com.aio.abyss.tasks.utility;

import com.api.API;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.GrandExchange;
import org.rspeer.runetek.api.component.GrandExchangeSetup;
import org.rspeer.runetek.api.component.tab.Equipment;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.providers.RSGrandExchangeOffer;

import java.util.regex.Pattern;

public class AbyssGrandExchange {
    public static final Area GRAND_EXCHANGE = Area.rectangular(3144, 3508, 3184, 3472);
    public static Pattern RING_OF_WEALTH_PATTERN = Pattern.compile("Ring of wealth \\(.*");


    public static boolean buyAtGEIncrease(int itemID, int quantity, int priceIncrease) {
        if (getToGE()) {
            if (priceIncrease == 0) {
                priceIncrease = 5;
            }
            if (Statics.wthdrawAllCoins) {
                if (withdrawAllCoins()) {
                    Statics.wthdrawAllCoins = false;
                }
            } else if (Inventory.contains(995) && !Inventory.isFull()) {
                if (GrandExchange.isOpen()) {
                    RSGrandExchangeOffer boughtItem = GrandExchange.getFirst(a -> a.getItemId() == itemID && a.getProgress() == RSGrandExchangeOffer.Progress.FINISHED);
                    if (boughtItem != null) {
                        GrandExchange.collectAll();
                        return true;
                    } else if (GrandExchange.getView() == GrandExchange.View.BUY_OFFER) {
                        if (GrandExchangeSetup.getItemId() != itemID) {
                            GrandExchangeSetup.setItem(itemID);
                        } else {
                            if (GrandExchangeSetup.getQuantity() != quantity) {
                                GrandExchangeSetup.setQuantity(quantity);
                            } else if (GrandExchangeSetup.increasePrice(priceIncrease)) {
                                GrandExchangeSetup.confirm();
                                Time.sleep(2500);
                            }
                        }
                    } else {
                        GrandExchange.getFirstEmpty().create(RSGrandExchangeOffer.Type.BUY);
                    }
                } else {
                    Npc npc = Npcs.getNearest("Grand Exchange Clerk");
                    if (npc != null) {
                        npc.interact("Exchange");
                        Time.sleepUntil(GrandExchange::isOpen, 3000);
                    }
                }
            } else if (Bank.isOpen()) {
                if (!Inventory.contains(995)) {
                    Bank.withdrawAll(995);
                    Time.sleep(Random.nextInt(300, 350));
                } else if (Inventory.isFull()) {
                    Bank.depositInventory();
                    Time.sleep(Random.nextInt(300, 350));
                }
            } else {
                Bank.open();
                Time.sleepUntil(Bank::isOpen, 2000);
            }
        }
        return false;
    }

    public static boolean buyAtGESetPrice(int itemID, int quantity, int price) {
        if (getToGE()) {
            if (Statics.wthdrawAllCoins) {
                if (withdrawAllCoins()) {
                    Statics.wthdrawAllCoins = false;
                }
            } else if (Inventory.contains(995) && !Inventory.isFull()) {
                if (GrandExchange.isOpen()) {
                    RSGrandExchangeOffer boughtItem = GrandExchange.getFirst(a -> a.getItemId() == itemID && a.getProgress() == RSGrandExchangeOffer.Progress.FINISHED);
                    if (boughtItem != null) {
                        GrandExchange.collectAll();
                        return true;
                    } else if (GrandExchange.getView() == GrandExchange.View.BUY_OFFER) {
                        if (GrandExchangeSetup.getItemId() != itemID) {
                            GrandExchangeSetup.setItem(itemID);
                        } else {
                            if (GrandExchangeSetup.getQuantity() != quantity) {
                                GrandExchangeSetup.setQuantity(quantity);
                            } else if (GrandExchangeSetup.getPricePerItem() != price) {
                                GrandExchangeSetup.setPrice(price);
                            } else {
                                GrandExchangeSetup.confirm();
                                Time.sleep(2500);
                            }
                        }
                    } else {
                        GrandExchange.getFirstEmpty().create(RSGrandExchangeOffer.Type.BUY);
                    }
                } else {
                    Npc npc = Npcs.getNearest("Grand Exchange Clerk");
                    if (npc != null) {
                        npc.interact("Exchange");
                        Time.sleepUntil(GrandExchange::isOpen, 2000);
                    }
                }
            } else if (Bank.isOpen()) {
                if (!Inventory.contains(995)) {
                    Bank.withdrawAll(995);
                    Time.sleep(Random.nextInt(300, 350));
                } else if (Inventory.isFull()) {
                    Bank.depositInventory();
                    Time.sleep(Random.nextInt(300, 350));
                }
            } else {
                Bank.open();
                Time.sleepUntil(Bank::isOpen, 2000);
            }
        }
        return false;
    }

    public static boolean sellAtGe(int itemID) {
        if (getToGE()) {
            if (GrandExchange.isOpen()) {
                RSGrandExchangeOffer soldItem = GrandExchange.getFirst(a -> a.getItemId() == itemID && a.getProgress() == RSGrandExchangeOffer.Progress.FINISHED);
                if (soldItem != null) {
                    GrandExchange.collectAll();
                    return true;
                } else if (GrandExchange.getView() == GrandExchange.View.SELL_OFFER) {
                    if (GrandExchangeSetup.getItemId() != itemID) {
                        GrandExchangeSetup.setItem(itemID);
                    } else {
                        if (GrandExchangeSetup.decreasePrice(2)) {
                            GrandExchangeSetup.confirm();
                            Time.sleep(2000);
                        }
                    }
                } else {
                    GrandExchange.getFirstEmpty().create(RSGrandExchangeOffer.Type.SELL);
                }
            } else {
                Npc npc = Npcs.getNearest("Grand Exchange Clerk");
                if (npc != null) {
                    npc.interact("Exchange");
                    Time.sleepUntil(GrandExchange::isOpen, 2000);
                }
            }
        }
        return false;
    }

    public static boolean sellNoted(int item) {
        if (getToGE()) {
            int notedItem = item + 1;
            if (GrandExchange.isOpen()) {
                RSGrandExchangeOffer soldItem = GrandExchange.getFirst(a -> a.getItemId() == item && a.getProgress() == RSGrandExchangeOffer.Progress.FINISHED);
                if (soldItem != null) {
                    GrandExchange.collectAll();
                    return true;
                } else if (GrandExchange.getView() == GrandExchange.View.SELL_OFFER) {
                    if (GrandExchangeSetup.getItemId() != item) {
                        GrandExchangeSetup.setItem(notedItem);
                    } else {
                        if (GrandExchangeSetup.decreasePrice(3)) {
                            GrandExchangeSetup.confirm();
                            Time.sleep(2000);
                        }
                    }
                } else {
                    GrandExchange.getFirstEmpty().create(RSGrandExchangeOffer.Type.SELL);
                }
            } else {
                Npc npc = Npcs.getNearest("Grand Exchange Clerk");
                if (npc != null) {
                    npc.interact("Exchange");
                    Time.sleepUntil(GrandExchange::isOpen, 2000);
                }
            }
        }
        return false;
    }

    public static boolean withdrawAllCoins() {
        if (Bank.isOpen()) {
            if (Bank.contains(995)) {
                return Bank.withdrawAll(995);
            } else {
                return false;
            }
        } else {
            Bank.open();
            Time.sleepUntil(Bank::isOpen, 2000);
        }
        return false;
    }

    private static boolean getToGE() {
        if (GRAND_EXCHANGE.contains(Players.getLocal())) {
            return true;
        } else if (Equipment.contains(RING_OF_WEALTH_PATTERN)) {
            if (API.closeInterfaces() && API.openTab(Tab.EQUIPMENT)) {
                Equipment.interact(a -> a.getName().matches(RING_OF_WEALTH_PATTERN.pattern()), "Grand Exchange");
                Time.sleepUntil(() -> !Players.getLocal().isAnimating() || GRAND_EXCHANGE.contains(Players.getLocal()), 4500);
            }
        } else if (Inventory.contains(RING_OF_WEALTH_PATTERN)) {
            Item item = Inventory.getFirst(RING_OF_WEALTH_PATTERN);
            if (item != null) {
                item.interact("Wear");
            }
        } else if (Bank.isOpen()) {
            if (!Inventory.isFull()) {
                Bank.withdraw(a -> a.getName().matches(Statics.RING_OF_WEALTH_PATTERN.pattern()), 1);
                Time.sleep(Random.nextInt(400, 600));
            } else {
                Bank.depositInventory();
                Time.sleep(Random.nextInt(300, 400));
            }
        } else {
            Bank.open();
            Time.sleepUntil(Bank::isOpen, 2000);
        }
        return false;
    }
}
