package games.absolutephoenix.harvestcity.registry;
import games.absolutephoenix.harvestcity.registry.utility.CropRegister;

@SuppressWarnings("unused")
public class HCCrops {

    //region Spring Crops
    public static CropRegister BLUE_JAZZ = new CropRegister(
            "jazz",
            "seeds",
            new int[]{1, 2, 2, 2},
            new String[]{"spring"},
            "blue_jazz"
    );
    public static CropRegister CAULIFLOWER = new CropRegister(
            "cauliflower",
            "seeds",
            new int[]{1, 2, 4, 4, 1},
            new String[]{"spring"}
    );
    public static CropRegister COFFEE_BEAN = new CropRegister(
            "coffee_bean",
            new int[]{1, 2, 2, 2},
            2,
            new String[]{"spring"}
    );
    public static CropRegister GARLIC = new CropRegister(
            "garlic",
            "seeds",
            new int[]{1, 1, 1, 1},
            new String[]{"spring"}
    );
    public static CropRegister GREEN_BEAN = new CropRegister(
            "bean",
            "starter",
            new int[]{1, 1, 1, 3, 4},
            3,
            new String[]{"spring"},
            "green_bean"
    );
    public static CropRegister KALE = new CropRegister(
            "kale",
            "seeds",
            new int[]{1, 2, 2, 1},
            new String[]{"spring"}
    );
    public static CropRegister PARSNIP = new CropRegister(
            "parsnip",
            "seeds",
            new int[]{1, 1, 1, 1},
            new String[]{"spring"}
    );
    public static CropRegister POTATO = new CropRegister(
            "potato",
            "seeds",
            new int[]{1, 1, 1, 2, 1},
            new String[]{"spring"}
    );
    public static CropRegister RHUBARB = new CropRegister(
            "rhubarb",
            "seeds",
            new int[]{2, 2, 2, 3, 4},
            new String[]{"spring"}
    );
    public static CropRegister STRAWBERRY = new CropRegister(
            "strawberry",
            "seeds",
            new int[]{1, 1, 2, 2, 2},
            new String[]{"spring"}
    );
    public static CropRegister TULIP = new CropRegister(
            "tulip",
            "bulb",
            new int[]{1, 1, 2, 2},
            new String[]{"spring"}
    );
    public static CropRegister UNMILLED_RICE = new CropRegister(
            "rice",
            "shoot",
            new int[]{1, 2, 3, 2, 1},
            new String[]{"summer"},
            0,
            0f,
            "unmilled_rice"
    );
    public static CropRegister FIBER = new CropRegister(
            "fiber",
            "seeds",
            new int[]{1, 2, 2, 2},
            new String[]{"spring", "summer", "fall", "winter"}
    );
    public static CropRegister TEA_LEAVES = new CropRegister(
            "tea",
            "sapling",
            new int[]{2, 4, 6, 6, 6},
            new String[]{"fall"},
            "tea_leaves"
    );
    public static CropRegister ANCIENT_FRUIT = new CropRegister(
            "ancient",
            "seeds",
            new int[]{2, 7, 7, 7, 5},
            7,
            new String[]{"spring", "summer", "fall"},
            "ancient_fruit"
    );
    //endregion
    //region Summer Crops
    public static CropRegister BLUEBERRY = new CropRegister(
            "blueberry",
            "seeds",
            new int[]{1, 3, 3, 4, 2},
            2,
            new String[]{"summer"}
    );
    public static CropRegister CORN = new CropRegister(
            "corn",
            "seeds",
            new int[]{2, 3, 3, 3, 3},
            4,
            new String[]{"summer", "fall"}
    );
    public static CropRegister HOPS = new CropRegister(
            "hops",
            "starter",
            new int[]{1, 1, 2, 3, 4},
            1,
            new String[]{"summer", "fall"}
    );
    public static CropRegister HOT_PEPPER = new CropRegister(
            "pepper",
            "seeds",
            new int[]{1, 1, 1, 1, 1},
            3,
            new String[]{"summer"},
            "hot_pepper"
    );
    public static CropRegister MELON = new CropRegister(
            "melon",
            "seeds",
            new int[]{1, 2, 3, 3, 3},
            new String[]{"summer"}
    );
    public static CropRegister POPPY = new CropRegister(
            "poppy",
            "seeds",
            new int[]{1, 2, 2, 2},
            new String[]{"summer"}
    );
    public static CropRegister RADISH = new CropRegister(
            "radish",
            "seeds",
            new int[]{2, 1, 2, 1},
            new String[]{"summer"}
    );
    public static CropRegister RED_CABBAGE = new CropRegister(
            "red_cabbage",
            "seeds",
            new int[]{2, 1, 2, 2, 2},
            new String[]{"summer"}
    );
    public static CropRegister STARFRUIT = new CropRegister(
            "starfruit",
            "seeds",
            new int[]{2, 3, 2, 3, 3},
            new String[]{"summer"}
    );
    public static CropRegister SUMMER_SPANGLE = new CropRegister(
            "spangle",
            "seeds",
            new int[]{1, 2, 3, 2},
            new String[]{"summer"},
            "summer_spangle"
    );
    public static CropRegister SUNFLOWER = new CropRegister(
            "sunflower",
            "seeds",
            new int[]{1, 2, 3, 2},
            new String[]{"summer", "fall"}
    );
    public static CropRegister TOMATO = new CropRegister(
            "tomato",
            "seeds",
            new int[]{2, 2, 2, 2, 3},
            4,
            new String[]{"summer"}
    );
    public static CropRegister WHEAT = new CropRegister(
            "wheat",
            "seeds",
            new int[]{1, 1, 1, 1},
            new String[]{"summer", "fall"}
    );
    public static CropRegister PINEAPPLE = new CropRegister(
            "pineapple",
            "seeds",
            new int[]{1, 3, 3, 4, 3},
            7,
            new String[]{"summer"}
    );
    public static CropRegister TARO_ROOT = new CropRegister(
            "taro",
            "tuber",
            new int[]{1, 3, 3, 4, 3},
            7,
            new String[]{"summer"},
            "taro_root"
    );
    //endregion
    //region Fall Crops
    public static CropRegister AMARANTH = new CropRegister(
            "amaranth",
            "seeds",
            new int[]{1, 2, 2, 2},
            new String[]{"fall"}
    );
    public static CropRegister ARTICHOKE = new CropRegister(
            "artichoke",
            "seeds",
            new int[]{2, 2, 1, 2, 1},
            new String[]{"fall"}
    );
    public static CropRegister BEET = new CropRegister(
            "beet",
            "seeds",
            new int[]{1, 1, 2, 2},
            new String[]{"fall"}
    );
    public static CropRegister BOK_CHOY = new CropRegister(
            "bok_choy",
            "seeds",
            new int[]{1, 1, 1, 1},
            new String[]{"fall"}
    );
    public static CropRegister CRANBERRIES = new CropRegister(
            "cranberry",
            "seeds",
            new int[]{1, 2, 1, 1, 2},
            5,
            new String[]{"fall"},
            "cranberries"
    );
    public static CropRegister EGGPLANT = new CropRegister(
            "eggplant",
            "seeds",
            new int[]{1, 1, 1, 1, 1},
            5,
            new String[]{"fall"}
    );
    public static CropRegister FAIRY_ROSE = new CropRegister(
            "fairy",
            "seeds",
            new int[]{1, 4, 4, 3},
            new String[]{"fall"},
            "fairy_rose"
    );
    public static CropRegister GRAPE = new CropRegister(
            "grape",
            "starter",
            new int[]{1, 1, 2, 3, 3},
            3,
            new String[]{"fall"}
    );
    public static CropRegister PUMPKIN = new CropRegister(
            "pumpkin",
            "seeds",
            new int[]{1, 2, 3, 4, 3},
            new String[]{"fall"}
    );
    public static CropRegister YAM = new CropRegister(
            "yam",
            "seeds",
            new int[]{1, 3, 3, 3},
            new String[]{"fall"}
    );
    public static CropRegister SWEET_EMERALD_BERRY = new CropRegister(
            "rare",
            "seeds",
            new int[]{2, 4, 6, 6, 6},
            new String[]{"fall"},
            "sweet_emerald_berry"
    );
    //endregion
    //region Winter Crops
    //endregion

}