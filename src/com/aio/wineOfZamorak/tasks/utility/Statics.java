package com.aio.wineOfZamorak.tasks.utility;

import java.util.regex.Pattern;

public class Statics {
    public static boolean hopWorld;
    public static int badWorld;

    public static int AMULET_OF_GLORY_FULL = 11978;
    public static int AMULET_OF_GLORY_EMPTY = 1704;
    public static int BURNING_AMULET_FULL = 21166;
    public static int RING_OF_WEALTH_FULL = 11980;
    public static int RING_OF_WEALTH_EMPTY = 2572;
    public static int COINS = 995;
    public static int STAFF_OF_AIR = 1381;
    public static int FIRE_RUNE = 554;
    public static int MIND_RUNE = 558;
    public static int LAW_RUNE = 563;
    public static int LOBSTER = 379;
    public static int WINE_OF_ZAMORAK = 245;

    public static Pattern AMULET_OF_GLORY_PATTERN = Pattern.compile("Amulet of glory\\(.*");
    public static Pattern RING_OF_WEALTH_PATTERN = Pattern.compile("Ring of wealth \\(.*");
    public static Pattern BURNING_AMULET_PATTERN = Pattern.compile("Burning amulet\\(.*");

    public static boolean buyStaffOfAir = false;
    public static boolean buyAmuletOfGlory = false;
    public static boolean buyRingOfWealth = false;
    public static boolean sellRingOfWealth = false;
    public static boolean buyMindRunes = false;
    public static boolean buyFireRunes = false;
    public static boolean buyLawRunes = false;
    public static boolean buyBurningAmulet = false;
    public static boolean wthdrawAllCoins = false;
    public static boolean buyLobster = false;

}
