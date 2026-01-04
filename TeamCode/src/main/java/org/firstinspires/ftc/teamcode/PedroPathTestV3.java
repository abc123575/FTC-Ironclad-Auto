package org.firstinspires.ftc.teamcode;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
@Autonomous(name = "PedroPathTestV3", group = "Autonomous")
@Configurable
public class PedroPathTestV3 extends OpMode{
    private Follower follower;
    private Timer pathTimer, opModeTimer;

    // State Machine
    public enum PathState {
        // Start Position-End Position
        // one state: drive
        // second state: attempt to score the artifact
        DRIVE_STARTPOS_SHOOTPOS,
        SHOOT_PRELOAD,
        DRIVE_SHOOTPOS_NEXT,
        DONE
    }

    PathState pathstate; // Declare object

    // declare all poses
    private final Pose startPoseL = new Pose(20.83492496589358, 121.35334242837655, Math.toRadians(135));
    private final Pose shootPoseL = new Pose(48.0, 84.0, Math.toRadians(180));
    private final Pose shootControl1 = new Pose(61.813091, 99.22373, 0.0);
    private final Pose nextPoseL = new Pose(12.0, 84.0, Math.toRadians(180));
    private final Pose shootControl2 = new Pose(12,96,0.0);
    private final Pose finalPoseL = new Pose(36,108, Math.toRadians(135));

    private PathChain driveStartLtoShootL;
    private PathChain driveShootLtoNextL;

    private PathChain driveNextLtoFinalPoseL;


    // builds the actual paths from pose to pose
    // builds the bezier curve
    public void buildPaths(){
        // put in coordinates for starting pose then for the ending pose
        // curve to control pont
        driveStartLtoShootL = follower.pathBuilder()
                .addPath(new BezierCurve(startPoseL, shootControl1, shootPoseL))
                .setLinearHeadingInterpolation(startPoseL.getHeading(), shootPoseL.getHeading())
                .build();
        // reposition to next point
        driveShootLtoNextL = follower.pathBuilder()
                .addPath(new BezierLine(shootPoseL, nextPoseL))
                .setLinearHeadingInterpolation(shootPoseL.getHeading(), nextPoseL.getHeading())
                .build();


    }

    // starts the state machine
    public void statePathUpdate(){
        switch(pathstate){
            case DRIVE_STARTPOS_SHOOTPOS:
                // Call followPath ONCE
                if (pathTimer.getElapsedTimeSeconds() == 0) {
                    follower.followPath(driveStartLtoShootL, true);
                }

                // Wait until path is finished (starts the next path when robot is not busy/ not moving)
                if (!follower.isBusy()) {
                    setPathstate(PathState.SHOOT_PRELOAD);
                }
                break;

            case SHOOT_PRELOAD:
                // Robot is stationary here
                // Use timer to control shooting duration
                if (pathTimer.getElapsedTimeSeconds() < 0.1) {
                    telemetry.addLine("At shooting pose");
                }

                // TODO: catapult / intake logic here

                if (pathTimer.getElapsedTimeSeconds() > 0.75) {
                    setPathstate(PathState.DRIVE_SHOOTPOS_NEXT);
                }
                break;

            case DRIVE_SHOOTPOS_NEXT:
                if (pathTimer.getElapsedTimeSeconds() == 0) {
                    follower.followPath(driveShootLtoNextL, true);
                }

                if (!follower.isBusy()) {
                    setPathstate(PathState.DONE);
                }
                break;

            case DONE:
                telemetry.addLine("Autonomous complete");
                break;
        }
    }


    // Helps transition from path to path. Helper function
    public void setPathstate(PathState newState) {
        pathstate = newState;
        pathTimer.resetTimer();  // resets the timer for every new path
    }


    @Override
    public void init(){  // the start init button
        pathstate = PathState.DRIVE_STARTPOS_SHOOTPOS;
        pathTimer = new Timer();
        opModeTimer = new Timer();
        follower = Constants.createFollower(hardwareMap);
        // TODO add in any other init mechanisms ( LIMELIGHT, CATAPULTS)

        buildPaths();
        follower.setPose(startPoseL);

    }


    // resets the timer for opMode
    public void start() {
        opModeTimer.resetTimer();
        setPathstate(pathstate);
    }

    @Override
    public void loop(){
        follower.update();
        statePathUpdate();

        telemetry.addData("path state", pathstate.toString());
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.addData("Path time", pathTimer.getElapsedTimeSeconds());
    }
}