package frc.team1138.robot.MotionProfile;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Ways {
    public static final Waypoint[] CROSS_LINE = new Waypoint[] {
        new Waypoint(0/12.0 ,54/12.0 ,Pathfinder.d2r(0)),
        new Waypoint(120/12.0 ,54/12.0 ,Pathfinder.d2r(0)),
    };

    public static final Waypoint[] TEST_45 = new Waypoint[] {
        new Waypoint(0/12.0 ,54/12.0 ,Pathfinder.d2r(0)),
        new Waypoint(120/12.0 ,54/12.0 ,Pathfinder.d2r(45)),
    };

    public static final Waypoint[] RIGHT_NEAR_SWITCH = new Waypoint[]{
        new Waypoint(1.75 ,3.8333333333333335 ,Pathfinder.d2r(0)),
        new Waypoint(10 ,1.6666666666666667 ,Pathfinder.d2r(0)),
        new Waypoint(14.25 ,5.333333333333333 ,Pathfinder.d2r(90.0))
    };

    public static final Waypoint[] RIGHT_NEAR_SCALE_PART_1 = new Waypoint[]{
        new Waypoint(2.0833333333333335 ,3.8333333333333335 ,Pathfinder.d2r(0)),
        new Waypoint(12.5 ,1.6666666666666667 ,Pathfinder.d2r(0))
    };

    public static final Waypoint[] RIGHT_NEAR_SCALE_PART_2 = new Waypoint[]{
        new Waypoint(12.5 ,1.6666666666666667 ,Pathfinder.d2r(0)),
        new Waypoint(23.75 ,1.6666666666666667 ,Pathfinder.d2r(0)),
    };

    public static final Waypoint[] RIGHT_NEAR_SCALE_PART_3 = new Waypoint[]{
        new Waypoint(23.75 ,1.6666666666666667 ,Pathfinder.d2r(0)),
        new Waypoint(26.0 ,3.0 ,Pathfinder.d2r(90.0))
    };

    public static final Waypoint[] RIGHT_FAR_SCALE_Part1 = new Waypoint[]{
        new Waypoint(2.0833333333333335 ,3.8333333333333335 ,Pathfinder.d2r(0)),
        new Waypoint(12.5 ,2.5 ,Pathfinder.d2r(0)),
        new Waypoint(19.583333333333332 ,10 ,Pathfinder.d2r(90.0)),
    };

    public static final Waypoint[] RIGHT_FAR_SCALE_Part2 = new Waypoint[]{
        new Waypoint(19.583333333333332 ,10 ,Pathfinder.d2r(90.0)),
        new Waypoint(19.583333333333332 ,15.0 ,Pathfinder.d2r(90.0)),
        new Waypoint(24.416666666666668 ,19.333333333333332 ,Pathfinder.d2r(0))
    };

    public static final Waypoint[] RIGHT_FAR_SCALE_PART3 = new Waypoint[]{
        new Waypoint(19.583333333333332 ,15 ,Pathfinder.d2r(90.0)),
        new Waypoint(23.416666666666668 ,19.5 ,Pathfinder.d2r(0))     
    };

    public static final Waypoint[] MID_2_RIGHT_SWITCH = new Waypoint[] {
        new Waypoint(1.9166666666666667 ,12.916666666666666 ,Pathfinder.d2r(0)),
        new Waypoint(9.583333333333334 ,8.5 ,Pathfinder.d2r(0))
    };

    public static final Waypoint[] MID_2_LEFT_SWITCH = new Waypoint[] {
        new Waypoint(1.9166666666666667 ,12.916666666666666 ,Pathfinder.d2r(0)),
        new Waypoint(9.583333333333334 ,18.75 ,Pathfinder.d2r(0))
    };

    public static final Waypoint[] LEFT_NEAR_SWITCH = new Waypoint[] {
        new Waypoint(1.6666666666666667 ,23.166666666666668 ,Pathfinder.d2r(0)),
        new Waypoint(9.166666666666666 ,25.416666666666668 ,Pathfinder.d2r(0)),
        new Waypoint(14 ,21.25 ,Pathfinder.d2r(90.0))
    };

    public static final Waypoint[] LEFT_NEAR_SCALE = new Waypoint[]{
        new Waypoint(1.6666666666666667 ,23.166666666666668 ,Pathfinder.d2r(0)),
        new Waypoint(23.75 ,25.416666666666668 ,Pathfinder.d2r(0)),
        new Waypoint(27.166666666666668 ,22.833333333333332 ,Pathfinder.d2r(-90.0))
    };

    public static final Waypoint[] LEFT_FAR_SCALE = new Waypoint[]{
        new Waypoint(2 ,3.75 ,Pathfinder.d2r(0)),
        new Waypoint(12.5 ,3.75 ,Pathfinder.d2r(0)),
        new Waypoint(19.166666666666668 ,10.5 ,Pathfinder.d2r(90.0)),
        new Waypoint(19.166666666666668 ,15.833333333333334 ,Pathfinder.d2r(90.0)),
        new Waypoint(23.583333333333332 ,20.5 ,Pathfinder.d2r(0))
    };
}