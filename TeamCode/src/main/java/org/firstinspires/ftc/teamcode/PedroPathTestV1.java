package org.firstinspires.ftc.teamcode;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "PedroPathTestV1", group = "Autonomous")
@Configurable
public class PedroPathTestV1 extends OpMode{
    private Follower follower;
    private Timer pathTimer, opModeTimer;

    // State Machine
    public enum PathState {
        // Start Position-End Position
        // one state: drive
        // second state: attempt to score the artifact
        DRIVE_STARTPOS_SHOOTPOS,
        SHOOT_PRELOAD,
        DRIVE_SHOOTPOS_PARK
        }

    PathState pathstate;

    //pedro sucks

    private final Pose startPoseL = new Pose(20.403022670025187, 123.14357682619648, Math.toRadians(138));
    private final Pose shootPoseL = new Pose(51.37291689,91.76526531, Math.toRadians(138));
    private final Pose ballPoseL = new Pose(51.37291689, 63.13659359190556);

    // declare variable name object

    private PathChain driveStartLtoShootL;
    private PathChain driveShootLtoBallL;

    public void buildPaths(){
        // put in coordinates for starting pose then for the ending pose
        driveStartLtoShootL = follower.pathBuilder()
                .addPath(new BezierLine(startPoseL,shootPoseL))
                .setLinearHeadingInterpolation(startPoseL.getHeading(),shootPoseL.getHeading())
                .build();
        driveShootLtoBallL = follower.pathBuilder()
                .addPath(new BezierLine(shootPoseL, ballPoseL))
                .setTangentHeadingInterpolation()
                .build();
    }

    public void statePathUpdate(){
        switch(pathstate){
            case DRIVE_STARTPOS_SHOOTPOS:
                follower.followPath(driveStartLtoShootL, true);
                setPathstate(PathState.SHOOT_PRELOAD); //reset timer and make new state
                break;
            case SHOOT_PRELOAD:
                if (!follower.isBusy()) {
                    telemetry.addLine("Done path 1");
                }
                setPathstate(PathState.DRIVE_SHOOTPOS_PARK);
                break;
            case DRIVE_SHOOTPOS_PARK:
                follower.followPath(driveShootLtoBallL, true);
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
        // TODO add in any other init mechinisms

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
        statePathUpdate();

        telemetry.addData("path state", pathstate.toString());
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.addData("Path time", pathTimer.getElapsedTimeSeconds());
    }
}