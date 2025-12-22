package org.firstinspires.ftc.teamcode;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.pedroPathing.MyAutoV2;
@Autonomous
public class SampleAutoPathing extends OpMode{
    private Follower follower;
    private Timer pathTimer, opModeTimer;

    // State Machine
    public enum PathState {
        // Start Position-End Position
        // one state: drive
        // second state: attempt to score the artifact
        DRIVE_STARTPOS_SHOOTPOS,
        SHOOT_PRELOAD
    }

    PathState pathstate;

    private final Pose startPoseL = new Pose(20.403022670025187, 123.14357682619648, Math.toRadians(138));
    private final Pose shootPoseL = new Pose(20.403022670025187,123.14357682619648, Math.toRadians(145));

    // declare variable name object

    private PathChain driveStartLtoShootL;

    public void buildPaths(){
        // put in coordinates for starting pose then for the ending pose
        driveStartLtoShootL = follower.pathBuilder()
                .addPath(new BezierLine(startPoseL,shootPoseL))
                .setLinearHeadingInterpolation(startPoseL.getHeading(),shootPoseL.getHeading())
                .build();
    }

    public void setPathUpdate(){
        switch(pathstate){
            case DRIVE_STARTPOS_SHOOTPOS:
                follower.followPath(driveStartLtoShootL, true);
                pathstate = PathState.SHOOT_PRELOAD;
                break;
            case SHOOT_PRELOAD:


        }
    }


    @Override
    public void init(){

    }

    @Override
    public void loop(){

    }
}