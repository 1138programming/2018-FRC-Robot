package frc.team1138.robot.MotionProfile;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;

import com.ctre.phoenix.motion.*;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;

public class TrajectoryExecutor {

	/**
	 * The status of the motion profile executer and buffer inside the Talon.
	 * Instead of creating a new one every time we call getMotionProfileStatus,
	 * keep one copy.
	 */
	private MotionProfileStatus leftProfileStatus = new MotionProfileStatus();
	private MotionProfileStatus rightProfileStatus = new MotionProfileStatus();
	/** additional cache for holding the active trajectory point */
	double _pos=0,_vel=0,_heading=0;

	/**
	 * reference to the talon we plan on manipulating. We will not changeMode()
	 * or call set(), just get motion profile status and make decisions based on
	 * motion profile.
	 */
    private TalonSRX leftTalon, rightTalon;
    private Trajectory leftTrajectory, rightTrajectory;
    private final int maxPoint; 
	/**
	 * State machine to make sure we let enough of the motion profile stream to
	 * talon before we fire it.
	 */
	private int _state = 0; 
	/**
	 * Any time you have a state machine that waits for external events, its a
	 * good idea to add a timeout. Set to -1 to disable. Set to nonzero to count
	 * down to '0' which will print an error message. Counting loops is not a
	 * very accurate method of tracking timeout, but this is just conservative
	 * timeout. Getting time-stamps would certainly work too, this is just
	 * simple (no need to worry about timer overflows).
	 */
	private int _loopTimeout = -1;
	/**
	 * If start() gets called, this flag is set and in the control() we will
	 * service it.
	 */
	private boolean _bStart = false;

	/**
	 * Since the CANTalon.set() routine is mode specific, deduce what we want
	 * the set value to be and let the calling module apply it whenever we
	 * decide to switch to MP mode.
	 */
	private SetValueMotionProfile leftSetValue = SetValueMotionProfile.Disable;
	private SetValueMotionProfile rightSetValue = SetValueMotionProfile.Disable;
	/**
	 * How many trajectory points do we wait for before firing the motion
	 * profile.
	 */
	private static final int kMinPointsInTalon = 5;
	/**
	 * Just a state timeout to make sure we don't get stuck anywhere. Each loop
	 * is about 20ms.
	 */
	private static final int kNumLoopsTimeout = 10;
	
	/**
	 * Lets create a periodic task to funnel our trajectory points into our talon.
	 * It doesn't need to be very accurate, just needs to keep pace with the motion
	 * profiler executer.  Now if you're trajectory points are slow, there is no need
	 * to do this, just call _talon.processMotionProfileBuffer() in your teleop loop.
	 * Generally speaking you want to call it at least twice as fast as the duration
	 * of your trajectory points.  So if they are firing every 20ms, you should call 
	 * every 10ms.
	 */
	class PeriodicRunnable implements java.lang.Runnable {
		public void run() 
		{
			rightTalon.processMotionProfileBuffer();    
			leftTalon.processMotionProfileBuffer();
		}
	}
	Notifier _notifer = new Notifier(new PeriodicRunnable());
	

	/**
	 * C'tor
	 * 
	 * @param talon
	 *            reference to Talon object to fetch motion profile status from.
	 */
	public TrajectoryExecutor(TalonSRX left, TalonSRX right, Trajectory leftTraj, Trajectory rightTraj) {
		this.leftTalon = left;
		this.rightTalon = right;
		this.leftTrajectory = leftTraj;
		this.rightTrajectory = rightTraj;
        this.maxPoint = this.leftTrajectory.length();
		/*
		 * since our MP is 50ms per point, set the control frame rate and the
		 * notifer to half that
		 */
		leftTalon.changeMotionControlFramePeriod(25);
		rightTalon.changeMotionControlFramePeriod(25);
		_notifer.startPeriodic(0.025);
	}

	/**
	 * Called to clear Motion profile buffer and reset state info during
	 * disabled and when Talon is not in MP control mode.
	 */
	public void reset() {
		/*
		 * Let's clear the buffer just in case user decided to disable in the
		 * middle of an MP, and now we have the second half of a profile just
		 * sitting in memory.
		 */
		leftTalon.clearMotionProfileTrajectories();
		rightTalon.clearMotionProfileTrajectories();
		/* When we do re-enter motionProfile control mode, stay disabled. */
		leftSetValue = SetValueMotionProfile.Disable;
		rightSetValue = SetValueMotionProfile.Disable;
		/* When we do start running our state machine start at the beginning. */
		this._state = 0;
		_loopTimeout = -1;
		/*
		 * If application wanted to start an MP before, ignore and wait for next
		 * button press
		 */
		_bStart = false;
	}

	/**
	 * 
	 * @return the output value to pass to Talon's set() routine. 0 for disable
	 *         motion-profile output, 1 for enable motion-profile, 2 for hold
	 *         current motion profile trajectory point.
	 */
    public SetValueMotionProfile getLeftValue() {
        return this.leftSetValue;
	}
	
	/**
	 * 
	 * @return the output value to pass to Talon's set() routine. 0 for disable
	 *         motion-profile output, 1 for enable motion-profile, 2 for hold
	 *         current motion profile trajectory point.
	 */
    public SetValueMotionProfile getRightValue() {
        return this.rightSetValue;
    }

	/**
	 * Called every loop.
	 */
	public void control() {
		/* Get the motion profile status every loop */
		this.leftTalon.getMotionProfileStatus(this.leftProfileStatus);
		this.rightTalon.getMotionProfileStatus(this.rightProfileStatus);
        SmartDashboard.putString("In Loop", "Looping");
		/*
		 * track time, this is rudimentary but that's okay, we just want to make
		 * sure things never get stuck.
		 */
		if (_loopTimeout < 0) {
            /* do nothing, timeout is disabled */
            SmartDashboard.putString("In Loop", "Doing Nothing");
		} else {
			/* our timeout is nonzero */
			if (_loopTimeout == 0) {
				/*
				 * something is wrong. Talon is not present, unplugged, breaker
				 * tripped
				 */
				Instrumentation.OnNoProgress();
			} else {
				--_loopTimeout;
			}
		} 

		/* first check if we are in MP mode */
		if (this.leftTalon.getControlMode() != ControlMode.MotionProfile || this.rightTalon.getControlMode() != ControlMode.MotionProfile) {
			/*
			 * we are not in MP mode. We are probably driving the robot around
			 * using gamepads or some other mode.
			 */
			this._state = 0;
            _loopTimeout = -1;
            SmartDashboard.putString("Mode", "Doing Nothing");
		} else {
			/*
			 * we are in MP control mode. That means: starting Mps, checking Mp
			 * progress, and possibly interrupting MPs if thats what you want to
			 * do.
			 */
            SmartDashboard.putString("Mode", "Doing Something");
			switch (_state) {
				case 0: /* wait for application to tell us to start an MP */
					if (_bStart) {
						_bStart = false;
	
						this.leftSetValue = SetValueMotionProfile.Disable;
						this.rightSetValue = SetValueMotionProfile.Disable;
						startFilling();
						/*
						 * MP is being sent to CAN bus, wait a small amount of time
						 */
						_state = 1;
						_loopTimeout = kNumLoopsTimeout;
					}
					break;
				case 1: /*
						 * wait for MP to stream to Talon, really just the first few
						 * points
						 */
					/* do we have a minimum numberof points in Talon */
					if (leftProfileStatus.btmBufferCnt > kMinPointsInTalon && rightProfileStatus.btmBufferCnt > kMinPointsInTalon) {
						/* start (once) the motion profile */
						leftSetValue = SetValueMotionProfile.Enable;
						rightSetValue = SetValueMotionProfile.Enable;
						/* MP will start once the control frame gets scheduled */
						_state = 2;
						_loopTimeout = kNumLoopsTimeout;
					}
					break;
				case 2: /* check the status of the MP */
					/*
					 * if talon is reporting things are good, keep adding to our
					 * timeout. Really this is so that you can unplug your talon in
					 * the middle of an MP and react to it.
					 */
					if (leftProfileStatus.isUnderrun == false && rightProfileStatus.isUnderrun == false) {
						_loopTimeout = kNumLoopsTimeout;
					}
					/*
					 * If we are executing an MP and the MP finished, start loading
					 * another. We will go into hold state so robot servo's
					 * position.
					 */
					if (leftProfileStatus.activePointValid && leftProfileStatus.isLast) {
						/*
						 * because we set the last point's isLast to true, we will
						 * get here when the MP is done
						 */
						leftSetValue = SetValueMotionProfile.Hold;
						_state = 0;
						_loopTimeout = -1;
					}
					if (rightProfileStatus.activePointValid && rightProfileStatus.isLast) {
						/*
						 * because we set the last point's isLast to true, we will
						 * get here when the MP is done
						 */
						rightSetValue = SetValueMotionProfile.Hold;
						_state = 0;
						_loopTimeout = -1;
					}
					break;
			}

			/* Get the motion profile status every loop */
			leftTalon.getMotionProfileStatus(leftProfileStatus);
			_heading = leftTalon.getActiveTrajectoryHeading();
			_pos = leftTalon.getActiveTrajectoryPosition();
			_vel = leftTalon.getActiveTrajectoryVelocity();

			rightTalon.getMotionProfileStatus(rightProfileStatus);
			_heading = rightTalon.getActiveTrajectoryHeading();
			_pos = rightTalon.getActiveTrajectoryPosition();
			_vel = rightTalon.getActiveTrajectoryVelocity();

			/* printfs and/or logging */
			// Instrumentation.process(_status, _pos, _vel, _heading);
			SmartDashboard.putNumber("Left Heading", leftTalon.getActiveTrajectoryHeading());
			SmartDashboard.putNumber("Right Heading", rightTalon.getActiveTrajectoryHeading());
		}
	}
	/**
	 * Find enum value if supported.
	 * @param durationMs
	 * @return enum equivalent of durationMs
	 */
	private TrajectoryDuration GetTrajectoryDuration(int durationMs)
	{	 
		/* create return value */
		TrajectoryDuration retval = TrajectoryDuration.Trajectory_Duration_0ms;
		/* convert duration to supported type */
		retval = retval.valueOf(durationMs);
		/* check that it is valid */
		if (retval.value != durationMs) {
			DriverStation.reportError("Trajectory Duration not supported - use configMotionProfileTrajectoryPeriod instead", false);		
		}
		/* pass to caller */
		return retval;
	}
	/** Start filling the MPs to all of the involved Talons. */
	private void startFilling() {
		/* since this example only has one talon, just update that one */
		startFilling(this.leftTrajectory, this.rightTrajectory, this.maxPoint);
	}

	private void startFilling(Trajectory leftTraj, Trajectory rightTraj, int totalCnt) {

		/* create an empty point */
		TrajectoryPoint leftPoint = new TrajectoryPoint();
		TrajectoryPoint righPoint = new TrajectoryPoint();
		/* did we get an underrun condition since last time we checked ? */
		if (leftProfileStatus.hasUnderrun) {
			/* better log it so we know about it */
			Instrumentation.OnUnderrun();
			/*
			 * clear the error. This flag does not auto clear, this way 
			 * we never miss logging it.
			 */
			leftTalon.clearMotionProfileHasUnderrun(0);
		}
		if (rightProfileStatus.hasUnderrun) {
			/* better log it so we know about it */
			Instrumentation.OnUnderrun();
			/*
			 * clear the error. This flag does not auto clear, this way 
			 * we never miss logging it.
			 */
			rightTalon.clearMotionProfileHasUnderrun(0);
		}
		/*
		 * just in case we are interrupting another MP and there is still buffer
		 * points in memory, clear it.
		 */
		leftTalon.clearMotionProfileTrajectories();
		rightTalon.clearMotionProfileTrajectories();
		/* set the base trajectory period to zero, use the individual trajectory period below */
		leftTalon.configMotionProfileTrajectoryPeriod(Constants.kBaseTrajPeriodMs, Constants.kTimeoutMs);
		rightTalon.configMotionProfileTrajectoryPeriod(Constants.kBaseTrajPeriodMs, Constants.kTimeoutMs);
		// SmartDashboard.putString("MP Stats", _status.toString());
		/* This is fast since it's just into our TOP buffer */
		for (int i = 0; i < totalCnt; ++i) {
            double left_positionRot = leftTraj.get(i).position;
			double left_velocityRPM = leftTraj.get(i).velocity;
			double left_heading = leftTraj.get(i).heading;
			SmartDashboard.putNumber("left head", left_heading);
			System.out.println(left_heading);

			double right_positionRot = rightTraj.get(i).position;
			double right_velocityRPM = rightTraj.get(i).velocity;
			double right_heading = rightTraj.get(i).heading;
			SmartDashboard.putNumber("right head", right_heading);

			/* for each point, fill our structure and pass it to API */
			leftPoint.position = left_positionRot * Constants.kSensorUnitsPerRotation; //Convert Revolutions to Units
			righPoint.position = right_positionRot * Constants.kSensorUnitsPerRotation; //Convert Revolutions to Units
			
			leftPoint.velocity = left_velocityRPM * Constants.kSensorUnitsPerRotation / 600.0; //Convert RPM to Units/100ms
			righPoint.velocity = right_velocityRPM * Constants.kSensorUnitsPerRotation / 600.0; //Convert RPM to Units/100ms

			leftPoint.headingDeg = left_heading; /* future feature - not used in this example*/
			righPoint.headingDeg = right_heading; /* future feature - not used in this example*/

			leftPoint.profileSlotSelect0 = 0; /* which set of gains would you like to use [0,3]? */
			righPoint.profileSlotSelect0 = 0; /* which set of gains would you like to use [0,3]? */

			leftPoint.profileSlotSelect1 = 0; /* future feature  - not used in this example - cascaded PID [0,1], leave zero */
			righPoint.profileSlotSelect1 = 0; /* future feature  - not used in this example - cascaded PID [0,1], leave zero */

			leftPoint.timeDur = GetTrajectoryDuration((int) (leftTraj.get(i).dt * 1000));
			leftPoint.zeroPos = false;

			righPoint.timeDur = GetTrajectoryDuration((int) (rightTraj.get(i).dt * 1000));
			righPoint.zeroPos = false;

			if (i == 0) {
				leftPoint.zeroPos = true; /* set this to true on the first point */
				righPoint.zeroPos = true;
			}
			leftPoint.isLastPoint = false;
			righPoint.isLastPoint = false;
			if ((i + 1) == totalCnt) {
				leftPoint.isLastPoint = true; /* set this to true on the last point  */
				righPoint.isLastPoint = true; /* set this to true on the last point  */
			}
			leftTalon.pushMotionProfileTrajectory(leftPoint);
			rightTalon.pushMotionProfileTrajectory(righPoint);
		}
	}
	/**
	 * Called by application to signal Talon to start the buffered MP (when it's
	 * able to).
	 */
	public void startMotionProfile() {
		_bStart = true;
	}
}