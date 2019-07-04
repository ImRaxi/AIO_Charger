package com.aio.abyss.tasks.utility;

import java.util.regex.Pattern;

public class Statics {
    public static boolean hopWorld;
    public static int badWorld;

    public static int LOBSTER = 379;
    public static int AMULET_OF_GLORY_FULL = 11978;
    public static int AMULET_OF_GLORY_EMPTY = 1704;
    public static int RING_OF_WEALTH_FULL = 11980;
    public static int RING_OF_WEALTH_EMPTY = 2572;
    public static int COMBAT_BRACELET_FULL = 11972;
    public static int COINS = 995;
    public static int VARROCK_TELEPORT = 8007;
    public static int LUMBRIDGE_TELEPORT = 8008;
    public static int BRONZE_PICKAXE = 1265;
    public static int RUNE_ESSENCE = 1436;
    public static int PURE_ESSENCE = 7936;
    public static int VIAL = 229;
    public static int STAMINA_POTION = 12625;
    public static int IRON_FULL_HELM = 1153;
    public static int IRON_PLATEBODY = 1115;
    public static int IRON_PLATELEGS = 1067;
    public static int IRON_KITESHIELD = 1191;
    public static int IRON_SCIMITAR = 1323;
    public static int MITHRIL_FULL_HELM = 1159;
    public static int MITHRIL_PLATEBODY = 1121;
    public static int MITHRIL_PLATELEGS = 1071;
    public static int MITHRIL_KITESHIELD = 1197;
    public static int MITHRIL_SCIMITAR = 1329;
    public static int RUNE_FULL_HELM = 1163;
    public static int RUNE_CHAINBODY = 1113;
    public static int RUNE_PLATELEGS = 1079;
    public static int RUNE_KITESHIELD = 1201;
    public static int RUNE_SCIMITAR = 1333;
    public static int ADAMANT_PICKAXE = 1271;
    public static int ADAMANT_PLATEBODY = 1123;
    public static int ADAMANT_PLATELEGS = 1073;
    public static int ABYSSAL_BOOK = 5520;
    public static int NATURE_RUNE = 561;
    public static String SMALL_POUCH = "Small pouch";
    public static String MEDIUM_POUCH = "Medium pouch";
    public static String LARGE_POUCH = "Large pouch";
    public static String GIANT_POUCH = "Giant pouch";

    public static Pattern AMULET_OF_GLORY_PATTERN = Pattern.compile("Amulet of glory\\(.*");
    public static Pattern RING_OF_WEALTH_PATTERN = Pattern.compile("Ring of wealth \\(.*");
    public static Pattern COMBAT_BRACELET_PATTERN = Pattern.compile("Combat bracelet\\(.*");
    public static Pattern STAMINA_POTION_PATTERN = Pattern.compile("Stamina potion\\(.*");

    public static boolean buyLobster = false;
    public static boolean sellWealth = false;
    public static boolean buyWealth = false;
    public static boolean buyBronzePickaxe = false;
    public static boolean buyPureEssence = false;
    public static boolean buyStaminPotion = false;
    public static boolean sellGlory = false;
    public static boolean buyGlory = false;
    public static boolean wthdrawAllCoins = false;
    public static boolean gettingPouches = false;
    public static boolean getSmallPouch = false;
    public static boolean buyWeapon = false;
    public static boolean buyArmor = false;
    public static boolean buyAddyPick = false;
    public static boolean buyRuneChainbody = false;
    public static boolean buyRunePlatelegs = false;
    public static boolean buyAdamantPlatelegs = false;
    public static boolean buyAdamantChainbody = false;
    public static boolean fillSmallPouch = false;
    public static boolean fillMediumPouch = false;
    public static boolean fillLargePouch = false;
    public static boolean fillGiantPouch = false;
    public static boolean emptySmallPouch = false;
    public static boolean emptyMediumPouch = false;
    public static boolean emptyLargePouch = false;
    public static boolean emptyGiantPouch = false;
    public static boolean tradeLootMule = false;
    public static boolean joinClanChat = false;


}
