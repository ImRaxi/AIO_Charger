package com.aio.wineOfZamorak.tasks.utility;

import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;

public class Areas {
    public static final Area GRAND_EXCHANGE = Area.rectangular(3144, 3508, 3184, 3472);
    public static final Area EDGEVILLE = Area.rectangular(3109, 3467, 3073, 3520);
    public static final Area LUMBRIDGE_CASTLE = Area.rectangular(3202, 3233, 3226, 3202, 0);
    public static final Area LUMBRIDGE_CASTLE_FLOOR1 = Area.rectangular(3202, 3233, 3226, 3202, 1);
    public static final Area LUMBRIDGE_CASTLE_FLOOR2 = Area.rectangular(3202, 3233, 3226, 3202, 2);
    public static final Area MUDSKIPPER_JAIL = Area.rectangular(3009, 3196, 3020, 3178);
    public static final Area WILDERNESS_AREA = Area.rectangular(3042, 3846, 2945, 3812);
    public static final Area CHAOS_TEMPLE = Area.rectangular(2946, 3824, 2957, 3817);

    public static final Position FIRST_WALK_POSITION = new Position(2988, 3828);
    public static final Position TELEPORT_POSITION = new Position(2953, 3754);

}
