package com.aio.abyss.tasks.enumData;

import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;

public enum AgilityObstacles {
    GNOME_LOG("Log balance", "Walk-across", new Position(2474, 3435, 1), Area.rectangular(2470, 3440, 2477, 3430, 0)),
    GNOME_NET("Obstacle net", "Climb-over", new Position(2475, 3425, 0), Area.rectangular(2470, 3429, 2477, 3425, 0)),
    GNOME_TREE_BRANCH("Tree branch", "Climb", new Position(2473, 3422, 1), Area.rectangular(2476, 3424, 2471, 3422, 1)),
    GNOME_BALANCE_ROPE("Balancing rope", "Walk-on", new Position(2478, 3420, 2), Area.rectangular(2472, 3421, 2477, 3418, 2)),
    GNOME_TREE_BRANCH_2("Tree branch", "Climb-down", new Position(2486, 3419, 2), Area.rectangular(2483, 3421, 2488, 3418, 2)),
    GNOME_NET_2("Obstacle net", "Climb-over", new Position(2485, 3426, 0), Area.rectangular(2482, 3426, 2489, 3417, 0)),
    GNOME_PIPE("Obstacle pipe", "Squeeze-through", new Position(2484, 3431, 0), Area.rectangular(2482, 3432, 2489, 3427, 0));


    private String name;
    private String action;
    private Position startPosition;
    private Area area;

    AgilityObstacles(String name, String action, Position startPosition, Area area) {
        this.name = name;
        this.action = action;
        this.area = area;
        this.startPosition = startPosition;
    }

    public static String getCurrentObstacle() {
        for (AgilityObstacles obstacles : AgilityObstacles.values()) {
            if (obstacles.area.contains(Players.getLocal())) {
                return obstacles.name;
            }
        }
        return "";
    }

    public static boolean interact() {
        if (Players.getLocal().getAnimation() == -1 && !Players.getLocal().isMoving()) {
            SceneObject sceneObject = SceneObjects.getNearest(getCurrentObstacle());
            return sceneObject != null && sceneObject.interact(ActionOpcodes.OBJECT_ACTION_0);
        }
        return false;
    }
}



/*DRAYNOR_ROUGH_WALL("Rough wall", "Climb", new Position(3103, 3279, 0), Area.rectangular(
            new Position(3080, 3284, 0),
            new Position(3112, 3244, 0), 0
    )),
    DRAYNOR_TIGHT_ROPE("Tightrope", "Cross", new Position(3098, 3277, 3), Area.rectangular(
            new Position(3102, 3282, 3),
            new Position(3097, 3274, 3), 3
    )),
    DRAYNOR_TIGHT_ROPE2("Tightrope", "Cross", new Position(3092, 3276, 3), Area.rectangular(
            new Position(3092, 3277, 3),
            new Position(3088, 3274, 3), 3
    )),
    DRAYNOR_NARROW_WALL("Narrow wall", "Balance", new Position(3089, 3264, 3), Area.rectangular(
            new Position(3094, 3267, 3),
            new Position(3089, 3265, 3), 3
    )),
    DRAYNOR_WALL("Wall", "Jump-up", new Position(3088, 3256, 3), Area.rectangular(
            new Position(3088, 3261, 3),
            new Position(3088, 3257, 3), 3
    )),
    DRAYNOR_GAP("Gap", "Jump", new Position(3095, 3255, 3), Area.rectangular(
            new Position(3087, 3255, 3),
            new Position(3094, 3255, 3), 3
    )),
    DRAYNOR_CRATE("Crate", "Climb-down", new Position(3102, 3261, 3), Area.rectangular(
            new Position(3096, 3256, 3),
            new Position(3101, 3261, 3), 3
    )),
    VARROCK_ROUGH_WALL("Rough wall", "Climb", new Position(3221, 3414, 0), Area.rectangular(
            new Position(3245, 3411, 0),
            new Position(3218, 3424, 0), 0
    )),
    VARROCK_CLOTHES_LINE("Clothes line", "Cross", new Position(3213, 3414, 3), Area.rectangular(
            new Position(3219, 3410, 3),
            new Position(3214, 3419, 3), 3
    )),
    VARROCK_GAP("Gap", "Leap", new Position(3200, 3416, 3), Area.rectangular(
            new Position(3208, 3413, 3),
            new Position(3201, 3419, 3), 3
    )),
    VARROCK_WALL("Wall", "Balance", new Position(3191, 3415, 1), Area.rectangular(
            new Position(3197, 3416, 1),
            new Position(3194, 3416, 1), 1
    )),
    VARROCK_GAP2("Gap", "Leap", new Position(3193, 3401, 3), Area.rectangular(
            new Position(3192, 3406, 3),
            new Position(3198, 3402, 3), 3
    )),
    VARROCK_GAP3("Gap", "Leap", new Position(3209, 3397, 3), Area.polygonal(3,
            new Position(3208, 3403, 3),
            new Position(3202, 3403, 3),
            new Position(3202, 3399, 3),
            new Position(3183, 3399, 3),
            new Position(3183, 3388, 3),
            new Position(3195, 3388, 3),
            new Position(3208, 3395, 3)
    )),
    VARROCK_GAP4("Gap", "Leap", new Position(3233, 3402, 3), Area.rectangular(
            new Position(3218, 3393, 3),
            new Position(3232, 3402, 3), 3
    )),
    VARROCK_LEDGE("Ledge", "Hurdle", new Position(3236, 3409, 3), Area.rectangular(
            new Position(3236, 3403, 3),
            new Position(3240, 3408, 3), 3
    )),
    VARROCK_EDGE("Edge", "Jump-off", new Position(3236, 3416, 3), Area.rectangular(
            new Position(3240, 3410, 3),
            new Position(3236, 3415, 3), 3
    )),
    FALADOR_ROUGH_WALL("Rough wall", "Climb", new Position(3036, 3341, 0), Area.rectangular(
            3055, 3369, 3006, 3329, 0
    )),
    FALADOR_TIGHTROPE("Tightrope", "Cross", new Position(3040, 3343, 3), Area.rectangular(
            3036, 3343, 3040, 3342, 3
    )),
    FALADOR_HAND_HOLDS("Hand holds", "Cross", new Position(3050, 3350, 3), Area.rectangular(
            3043, 3341, 3051, 3349, 3
    )),
    FALADOR_GAP("Gap", "Jump", new Position(3048, 3359, 3), Area.rectangular(
            3050, 3357, 3048, 3358, 3
    )),
    FALADOR_GAP2("Gap", "Jump", new Position(3044, 3361, 3), Area.rectangular(
            3048, 3361, 3045, 3367, 3
    )),
    FALADOR_TIGHTROPE2("Tightrope", "Cross", new Position(3034, 3361, 3), Area.rectangular(
            3041, 3364, 2999, 3361, 3
    )),
    FALADOR_TIGHTROPE3("Tightrope", "Cross", new Position(3026, 3353, 3), Area.rectangular(
            3029, 3355, 3026, 3352, 3
    )),
    FALADOR_GAP3("Gap", "Jump", new Position(3016, 3352, 3), Area.rectangular(
            3021, 3353, 3009, 3358, 3
    )),
    FALADOR_LEDGE("Ledge", "Jump", new Position(3015, 3345, 3), Area.rectangular(
            3016, 3349, 3022, 3343, 3
    )),
    FALADOR_LEDGE2("Ledge", "Jump", new Position(3011, 3343, 3), Area.rectangular(
            3014, 3346, 3011, 3344, 3
    )),
    FALADOR_LEDGE3("Ledge", "Jump", new Position(3012, 3334, 3), Area.rectangular(
            3009, 3342, 3013, 3335, 3
    )),
    FALADOR_LEDGE4("Ledge", "Jump", new Position(3018, 3332, 3), Area.rectangular(
            3017, 3334, 3012, 3331, 3
    )),
    FALADOR_EDGE("Edge", "Jump", new Position(3025, 3332, 3), Area.rectangular(
            3019, 3332, 3024, 3335, 3
    ));*/
