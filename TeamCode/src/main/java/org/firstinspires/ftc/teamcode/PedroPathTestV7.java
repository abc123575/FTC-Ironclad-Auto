package org.firstinspires.ftc.teamcode;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Mechanism.Fling_Logic;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "PedroPathTestV7", group = "Autonomous")
@Configurable
public class PedroPathTestV7 extends OpMode{
    private Follower follower;
    private Timer pathTimer, opModeTimer;

    // ----- INTAKE SETUP -----

    private Fling_Logic shooter = new Fling_Logic();

    // State Machine
    public enum PathState {
        // Start Position-End Position
        // one state: drive
        // second state: attempt to score the artifact
        DRIVE_STARTPOS_SHOOTPOS,
        SHOOT_PRELOAD,
        DRIVE_SHOOTPOS_PARK,
        DRIVE_SHOOT
    }

    PathState pathstate;

    private final Pose startPoseL = new Pose(20.403022670025187, 123.14357682619648, Math.toRadians(135));
    private final Pose shootPoseL = new Pose(60,84, Math.toRadians(180));
    private final Pose ballPoseL = new Pose(12, 84, Math.toRadians(180));
    private final Pose shootingPoseL = new Pose(36,108,Math.toRadians(135));

    // declare variable name object
    // make sure paths are chained together
    private PathChain driveStartLtoShootL,driveShootLtoBallL, driveBalltoShootingPoseL;
    public void buildPaths(){
        // put in coordinates for starting pose then for the ending pose
        driveStartLtoShootL = follower.pathBuilder()
                .addPath(new BezierLine(startPoseL,shootPoseL))
                .setLinearHeadingInterpolation(startPoseL.getHeading(),shootPoseL.getHeading())
                .build();
        driveShootLtoBallL = follower.pathBuilder()
                .addPath(new BezierLine(shootPoseL, ballPoseL))
                .setLinearHeadingInterpolation(shootPoseL.getHeading(),ballPoseL.getHeading())
                .build();
        driveBalltoShootingPoseL = follower.pathBuilder()
                .addPath(new BezierLine(ballPoseL, shootingPoseL))
                .setLinearHeadingInterpolation(ballPoseL.getHeading(),shootingPoseL.getHeading())
                .build();

    }

    public void statePathUpdate(){
        switch(pathstate){
            case DRIVE_STARTPOS_SHOOTPOS:
                follower.followPath(driveStartLtoShootL, true);
                setPathstate(PathState.SHOOT_PRELOAD); //reset timer and make new state
                break;
            case SHOOT_PRELOAD:
                // check if follower has done path one
                if (!follower.isBusy()){
                    if (!shooter.isBusy()) {
                        follower.followPath(driveShootLtoBallL, true);
                        telemetry.addLine("Done path 1");
                        setPathstate(PathState.DRIVE_SHOOTPOS_PARK);
                    } //connected to 192.168.43.1:5555
                }
                break;
            // check iss follower done it's path and if that 5 seconds has elapsed
            case DRIVE_SHOOTPOS_PARK:
                if (!follower.isBusy()){
                    follower.followPath(driveBalltoShootingPoseL, true);
                    telemetry.addLine("Done path2 ");
                    setPathstate(PathState.DRIVE_SHOOT);
                }
                break;
            case DRIVE_SHOOT:
                if (!follower.isBusy()) {
                    if (!shooter.isBusy())
                        telemetry.addLine("Done path 3 ");
                }
                break;

            default:
                telemetry.addLine("no state commanded");
                break;
        }
    }


    public void setPathstate(PathState newState) {
        pathstate = newState;
        pathTimer.resetTimer();
    }


    @Override
    public void init(){
        pathstate = PathState.DRIVE_STARTPOS_SHOOTPOS;
        pathTimer = new Timer();
        opModeTimer = new Timer();
        follower = Constants.createFollower(hardwareMap);
        // TODO add in any other init mechanisms,

        shooter.init(hardwareMap);

        buildPaths();
        follower.setPose(startPoseL);

    }

    public void start() {
        opModeTimer.resetTimer();
        setPathstate(pathstate);
    }

    @Override
    public void loop(){
        follower.update();
        shooter.update();
        statePathUpdate();

        telemetry.addData("path state", pathstate.toString());
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.addData("Path time", pathTimer.getElapsedTimeSeconds());
    }
}