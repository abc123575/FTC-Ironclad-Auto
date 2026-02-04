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
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Mechanism.Fling_Logic;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

    // declare variable name object

    @Autonomous(name = "PedroPathTest10-Right", group = "Autonomous")
    @Configurable
    public class PedroPathTestV10Right extends OpMode{
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
            DRIVE_SHOOT,
            PATH4,
            PATH5,
            PATH6,
            PATH7,
            PATH8,
            PATH9,


        }

        org.firstinspires.ftc.teamcode.PedroPathTestV11.PathState pathstate;

        private final Pose startPoseL = new Pose(122.6, 121.290, Math.toRadians(45));
        private final Pose shootPoseL = new Pose(90,83.9, Math.toRadians(0));
        private final Pose ballPoseL = new Pose(130, 84, Math.toRadians(0));
        private final Pose shootingPoseL = new Pose(108.5,108,Math.toRadians(45));
        private final Pose paths4 = new Pose(90,60.6,Math.toRadians(0));
        private final Pose paths5 = new Pose(127.06456382454412,60,Math.toRadians(0));
        private final Pose paths6 = new Pose(108.5,108.5,Math.toRadians(45));
        private final Pose paths7 = new Pose(90 ,35,Math.toRadians(0));
        private final Pose paths8 = new Pose(124.56241639090332,34.9,Math.toRadians(0));
        private final Pose paths9 = new Pose(108.5,108.5,Math.toRadians(45));
        // declare variable name object

        // make sure paths are chained together
        private PathChain driveStartLtoShootL,driveShootLtoBallL, driveBalltoShootingPoseL, driveShootingPoseLtopaths4, drivepaths4topaths5, drivepaths5topaths6, drivepaths6topaths7, drivepaths7topaths8, drivepaths8topaths9;
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
            driveShootingPoseLtopaths4 = follower.pathBuilder()
                    .addPath(new BezierLine(shootingPoseL, paths4))
                    .setLinearHeadingInterpolation(shootPoseL.getHeading(),paths4.getHeading())
                    .build();
            drivepaths4topaths5 = follower.pathBuilder()
                    .addPath(new BezierLine(paths4, paths5)
                    )
                    .setLinearHeadingInterpolation(paths4.getHeading(),paths5.getHeading())
                    .build();
            drivepaths5topaths6 = follower.pathBuilder()
                    .addPath(new BezierLine(paths5, paths6)
                    )
                    .setLinearHeadingInterpolation(paths5.getHeading(),paths6.getHeading())
                    .build();
            drivepaths6topaths7 = follower.pathBuilder()
                    .addPath(new BezierLine(paths6, paths7))
                    .setLinearHeadingInterpolation(paths6.getHeading(),paths7.getHeading())
                    .build();
            drivepaths7topaths8 = follower.pathBuilder()
                    .addPath(new BezierLine(paths7, paths8))
                    .setLinearHeadingInterpolation(paths7.getHeading(),paths8.getHeading())
                    .build();
            drivepaths8topaths9 = follower.pathBuilder()
                    .addPath(new BezierLine(paths8, paths9))
                    .setLinearHeadingInterpolation(paths8.getHeading(),paths9.getHeading())
                    .build();

        }

        public void statePathUpdate() {
            switch (pathstate) {

                case DRIVE_STARTPOS_SHOOTPOS:
                    if (!follower.isBusy() && !shooter.isBusy()) {
                        follower.followPath(driveStartLtoShootL, true);
                        telemetry.addLine("Started path 1");
                        setPathstate(org.firstinspires.ftc.teamcode.PedroPathTestV11.PathState.SHOOT_PRELOAD);
                    }
                    break;

                case SHOOT_PRELOAD:
                    if (!follower.isBusy()) {
                        // After reaching shootPoseL, lift arms to prep for shooting
                        shooter.setArmsUp();
                        follower.followPath(driveShootLtoBallL, true);
                        setPathstate(org.firstinspires.ftc.teamcode.PedroPathTestV11.PathState.DRIVE_SHOOTPOS_PARK);
                        telemetry.addLine("Reached shootPoseL, arms up, moving to ballPoseL");
                    }
                    break;

                case DRIVE_SHOOTPOS_PARK:
                    if (!follower.isBusy()) {
                        follower.followPath(driveBalltoShootingPoseL, true);
                        setPathstate(org.firstinspires.ftc.teamcode.PedroPathTestV11.PathState.DRIVE_SHOOT);
                        telemetry.addLine("Moving to shootingPoseL");
                    }
                    break;

                case DRIVE_SHOOT:
                    if (!follower.isBusy()) {
                        shooter.lowerArms(); // lower at shootingPoseL
                        follower.followPath(driveShootingPoseLtopaths4, true);
                        telemetry.addLine("Started path 4");
                        setPathstate(org.firstinspires.ftc.teamcode.PedroPathTestV11.PathState.PATH4);
                    }
                    break;

                case PATH4:
                    if (!follower.isBusy()) {
                        follower.followPath(drivepaths4topaths5, true);
                        setPathstate(org.firstinspires.ftc.teamcode.PedroPathTestV11.PathState.PATH5);
                        telemetry.addLine("PATH4 complete, moving to PATH5");
                    }
                    break;

                case PATH5:
                    if (!follower.isBusy()) {
                        follower.followPath(drivepaths5topaths6, true);
                        setPathstate(org.firstinspires.ftc.teamcode.PedroPathTestV11.PathState.PATH6);
                        telemetry.addLine("PATH5 complete, moving to PATH6");
                    }
                    break;

                case PATH6:
                    if (!follower.isBusy()) {
                        shooter.lowerArms(); // lower at path6
                        follower.followPath(drivepaths6topaths7, true);
                        telemetry.addLine("Started path 7");
                        setPathstate(org.firstinspires.ftc.teamcode.PedroPathTestV11.PathState.PATH7);
                    }
                    break;

                case PATH7:
                    if (!follower.isBusy()) {
                        follower.followPath(drivepaths7topaths8, true);
                        setPathstate(org.firstinspires.ftc.teamcode.PedroPathTestV11.PathState.PATH8);
                        telemetry.addLine("PATH7 complete, moving to PATH8");
                    }
                    break;

                case PATH8:
                    if (!follower.isBusy()) {
                        follower.followPath(drivepaths8topaths9, true);
                        setPathstate(org.firstinspires.ftc.teamcode.PedroPathTestV11.PathState.PATH9);
                        telemetry.addLine("PATH8 complete, moving to PATH9");
                    }
                    break;

                case PATH9:
                    if (!follower.isBusy()) {
                        shooter.lowerArms(); // lower at end
                        telemetry.addLine("Reached end");
                    }
                    break;

                default:
                    telemetry.addLine("No state commanded");
                    break;
            }
        }


        public void setPathstate(org.firstinspires.ftc.teamcode.PedroPathTestV11.PathState newState) {
            pathstate = newState;
            pathTimer.resetTimer();
        }


        @Override
        public void init(){
            pathstate = org.firstinspires.ftc.teamcode.PedroPathTestV11.PathState.DRIVE_STARTPOS_SHOOTPOS;
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