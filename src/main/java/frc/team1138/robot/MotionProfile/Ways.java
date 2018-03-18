package frc.team1138.robot.MotionProfile;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Ways {
    public static final Waypoint[] CROSS_LINE = new Waypoint[] {
        new Waypoint(0/12.0 ,54/12.0 ,Pathfinder.d2r(0)),
        new Waypoint(120/12.0 ,54/12.0 ,Pathfinder.d2r(0)),
    };

    public static final Waypoint[] RIGHT_NEAR_SWITCH = new Waypoint[]{
        new Waypoint(1.6666666666666667 ,3.75 ,Pathfinder.d2r(0)),
        new Waypoint(9.666666666666666 ,1.6666666666666667 ,Pathfinder.d2r(0)),
        new Waypoint(14.166666666666666 ,5.75 ,Pathfinder.d2r(90))
    };

    public static final Waypoint[] RIGHT_NEAR_SCALE = new Waypoint[]{
        new Waypoint(2 ,3.75 ,Pathfinder.d2r(0)),
        new Waypoint(23.75 ,1.5833333333333333 ,Pathfinder.d2r(0)),
        new Waypoint(27.166666666666668 ,3.4 ,Pathfinder.d2r(90.0))
    };

    public static final Waypoint[] RIGHT_FAR_SCALE_Part1 = new Waypoint[]{
        new Waypoint(2 ,3.75 ,Pathfinder.d2r(0)),
        new Waypoint(12.5 ,3.75 ,Pathfinder.d2r(0)),
        new Waypoint(19.166666666666668 ,10.5 ,Pathfinder.d2r(90.0))        
    };

    public static final Waypoint[] RIGHT_FAR_SCALE_Part2 = new Waypoint[]{
        new Waypoint(19.166666666666668 ,10.5 ,Pathfinder.d2r(90.0)),
        new Waypoint(19.166666666666668 ,21.666666666666668 ,Pathfinder.d2r(90.0)),
    };

    public static final Waypoint[] RIGHT_FAR_SCALE_PART3 = new Waypoint[]{
        new Waypoint(19.166666666666668 ,21.666666666666668 ,Pathfinder.d2r(90.0)),
        new Waypoint(23.583333333333332 ,25.583333333333332 ,Pathfinder.d2r(0)),
        new Waypoint(26.75 ,22.25 ,Pathfinder.d2r(-90.0))    
    };

    public static final Waypoint[] MID_2_RIGHT_SWITCH = new Waypoint[] {
        new Waypoint(1.9166666666666667 ,12.916666666666666 ,Pathfinder.d2r(0)),
        new Waypoint(10.416666666666666 ,9.083333333333334 ,Pathfinder.d2r(0))
    };

    public static final Waypoint[] MID_2_LEFT_SWITCH = new Waypoint[] {
        new Waypoint(1.9166666666666667 ,12.916666666666666 ,Pathfinder.d2r(0)),
        new Waypoint(10 ,18 ,Pathfinder.d2r(0))
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