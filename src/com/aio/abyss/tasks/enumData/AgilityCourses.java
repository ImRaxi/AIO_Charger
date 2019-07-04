package com.aio.abyss.tasks.enumData;

import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.movement.position.Position;

import java.util.Arrays;
import java.util.List;

public enum AgilityCourses {

    GNOME_VILLAGE(1, new Position(2474, 3436, 0), AgilityObstacles.GNOME_LOG, AgilityObstacles.GNOME_NET,
            AgilityObstacles.GNOME_TREE_BRANCH, AgilityObstacles.GNOME_BALANCE_ROPE, AgilityObstacles.GNOME_TREE_BRANCH_2,
            AgilityObstacles.GNOME_NET_2, AgilityObstacles.GNOME_PIPE);
    /*DRAYNOR_VILLAGE(30, new Position(3104, 3277, 0), AgilityObstacles.DRAYNOR_ROUGH_WALL, AgilityObstacles.DRAYNOR_TIGHT_ROPE,
            AgilityObstacles.DRAYNOR_TIGHT_ROPE2, AgilityObstacles.DRAYNOR_NARROW_WALL, AgilityObstacles.DRAYNOR_WALL,
            AgilityObstacles.DRAYNOR_GAP, AgilityObstacles.DRAYNOR_CRATE),
    VARROCK(40, new Position(3222, 3415, 0), AgilityObstacles.VARROCK_ROUGH_WALL, AgilityObstacles.VARROCK_CLOTHES_LINE,
            AgilityObstacles.VARROCK_GAP, AgilityObstacles.VARROCK_WALL, AgilityObstacles.VARROCK_GAP2, AgilityObstacles.VARROCK_GAP3,
            AgilityObstacles.VARROCK_GAP4, AgilityObstacles.VARROCK_LEDGE, AgilityObstacles.VARROCK_EDGE),
    FALADOR(50, new Position(3037, 3339, 0), AgilityObstacles.FALADOR_ROUGH_WALL, AgilityObstacles.FALADOR_TIGHTROPE,
            AgilityObstacles.FALADOR_HAND_HOLDS, AgilityObstacles.FALADOR_GAP, AgilityObstacles.FALADOR_GAP2, AgilityObstacles.FALADOR_TIGHTROPE2,
            AgilityObstacles.FALADOR_TIGHTROPE3, AgilityObstacles.FALADOR_GAP3, AgilityObstacles.FALADOR_LEDGE, AgilityObstacles.FALADOR_LEDGE2,
            AgilityObstacles.FALADOR_LEDGE3, AgilityObstacles.FALADOR_LEDGE4, AgilityObstacles.FALADOR_EDGE);*/

    private int level;
    private Position startingPosition;
    private AgilityObstacles[] obstacles;

    AgilityCourses(int level, Position startingPosition, AgilityObstacles... obstacles) {
        this.level = level;
        this.startingPosition = startingPosition;
        this.obstacles = obstacles;
    }

    public static AgilityCourses getCurrentCourse() {
        AgilityCourses courses = GNOME_VILLAGE;
        for (AgilityCourses course : AgilityCourses.values()) {
            if (Skills.getCurrentLevel(Skill.AGILITY) >= course.level) {
                courses = course;
            }
        }
        return courses;
    }

    public Position getStartingPosition() {
        return startingPosition;
    }

    public List<AgilityObstacles> agilityObstaclesList() {
        return Arrays.asList(obstacles);
    }
}
