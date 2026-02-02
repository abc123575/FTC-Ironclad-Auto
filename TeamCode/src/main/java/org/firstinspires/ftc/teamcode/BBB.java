/* ============================================================= *
 *           Pedro Pathing Visualizer — Auto-Generated           *
 *                                                               *
 *  Version: 1.7.0.                                              *
 *  Copyright (c) 2026 Matthew Allen                             *
 *                                                               *
 *  THIS FILE IS AUTO-GENERATED — DO NOT EDIT MANUALLY.          *
 *  Changes will be overwritten when regenerated.                *
 * ============================================================= */

package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "Pedro Pathing Autonomous", group = "Autonomous")
@Configurable // Panels
public class BBB extends OpMode {

    private TelemetryManager panelsTelemetry; // Panels Telemetry instance
    public Follower follower; // Pedro Pathing follower instance
    private int pathState; // Current autonomous path state (state machine)
    private ElapsedTime pathTimer; // Timer for path state machine
    private Paths paths; // Paths defined in the Paths class
    private DcMotor intakeMotor;
    private DcMotor LLaunch, RLaunch;



    @Override
    public void init() {
        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(56, 8, Math.toRadians(90)));

        pathTimer = new ElapsedTime();
        paths = new Paths(follower); // Build paths

        panelsTelemetry.debug("Status", "Initialized");
        panelsTelemetry.update(telemetry);
        intakeMotor = hardwareMap.get(DcMotor.class, "intake_motor");
        LLaunch = hardwareMap.get(DcMotor.class, "LLaunch");
        RLaunch = hardwareMap.get(DcMotor.class, "RLaunch");
        RLaunch.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        follower.update(); // Update Pedro Pathing
        pathState = autonomousPathUpdate(); // Update autonomous state machine

        // Log values to Panels and Driver Station
        panelsTelemetry.debug("Path State", pathState);
        panelsTelemetry.debug("X", follower.getPose().getX());
        panelsTelemetry.debug("Y", follower.getPose().getY());
        panelsTelemetry.debug("Heading", follower.getPose().getHeading());
        panelsTelemetry.update(telemetry);
    }

    public static class Paths {

        public PathChain line1;
        public PathChain line2;
        public PathChain line3;
        public PathChain line4;
        public PathChain line5;
        public PathChain line6;
        public PathChain line7;
        public PathChain line8;
        public PathChain line9;
        public PathChain line10;
        public PathChain line11;
        public PathChain line12;

        public Paths(Follower follower) {
            line1 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(56.000, 8.000), new Pose(56.000, 36.000))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                    .build();

            line2 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(56.000, 36.000), new Pose(23.000, 36.000))
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            line3 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(23.000, 36.000), new Pose(58.214, 34.524))
                    )
                    .setTangentHeadingInterpolation()
                    .setReversed()
                    .build();

            line4 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(58.214, 34.524),
                                    new Pose(69.373, 90.470),
                                    new Pose(30.000, 120.000)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            line5 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(30.000, 120.000),
                                    new Pose(63.652, 92.248),
                                    new Pose(56.000, 60.000)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            line6 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(56.000, 60.000), new Pose(23.000, 60.000))
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            line7 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(23.000, 60.000), new Pose(56.000, 60.000))
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            line8 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(56.000, 60.000),
                                    new Pose(64.064, 93.740),
                                    new Pose(30.000, 120.000)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            line9 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(30.000, 120.000),
                                    new Pose(53.560, 99.220),
                                    new Pose(56.000, 84.000)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            line10 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(56.000, 84.000), new Pose(23.000, 84.000))
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            line11 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(23.000, 84.000), new Pose(56.000, 84.000))
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            line12 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(56.000, 84.000),
                                    new Pose(50.642, 102.722),
                                    new Pose(29.838, 120.076)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();
        }
    }

    public int autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(paths.line1, true);
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {
                    setPathState(2);
                }
                break;
            case 2:
                intakeMotor.setPower(1);
                follower.followPath(paths.line2, true);
                setPathState(3);
                break;
            case 3:
                if (!follower.isBusy()) {
                    setPathState(4);
                }
                break;
            case 4:
                follower.followPath(paths.line3, true);
                setPathState(5);
                break;
            case 5:
                if (!follower.isBusy()) {
                    setPathState(6);
                }
                break;
            case 6:
                intakeMotor.setPower(0);
                follower.followPath(paths.line4, true);
                setPathState(7);
                break;
            case 7:
                if (!follower.isBusy()) {
                    setPathState(8);
                }
                break;
            case 8:
                LLaunch.setPower(1);
                RLaunch.setPower(1);
                pathTimer.reset();
                follower.followPath(paths.line5, true);
                setPathState(9);
                break;
            case 9:
                if (pathTimer.seconds() >= 0.5) {

                    // reverse arms
                    LLaunch.setPower(-1);
                    RLaunch.setPower(-1);
                }
                if (!follower.isBusy()) {
                    setPathState(10);
                }
                break;
            case 10:
                intakeMotor.setPower(1);
                follower.followPath(paths.line6, true);
                setPathState(11);
                break;
            case 11:
                if (!follower.isBusy()) {
                    setPathState(12);
                }
                break;
            case 12:
                follower.followPath(paths.line7, true);
                setPathState(13);
                break;
            case 13:
                if (!follower.isBusy()) {
                    setPathState(14);
                }
                break;
            case 14:
                intakeMotor.setPower(0);
                follower.followPath(paths.line8, true);
                setPathState(15);
                break;
            case 15:
                if (!follower.isBusy()) {
                    setPathState(16);
                }
                break;
            case 16:
                LLaunch.setPower(1);
                RLaunch.setPower(1);
                pathTimer.reset();
                follower.followPath(paths.line9, true);
                setPathState(17);
                break;
            case 17:
                if (pathTimer.seconds() >= 0.5) {

                    // reverse arms
                    LLaunch.setPower(-1);
                    RLaunch.setPower(-1);
                }
                if (!follower.isBusy()) {
                    setPathState(18);
                }
                break;
            case 18:
                intakeMotor.setPower(1);
                follower.followPath(paths.line10, true);
                setPathState(19);
                break;
            case 19:
                if (!follower.isBusy()) {
                    setPathState(20);
                }
                break;
            case 20:
                follower.followPath(paths.line11, true);
                setPathState(21);
                break;
            case 21:
                if (!follower.isBusy()) {
                    setPathState(22);
                }
                break;
            case 22:
                intakeMotor.setPower(0);
                follower.followPath(paths.line12, true);
                setPathState(23);
                break;
            case 23:
                if (!follower.isBusy()) {
                    setPathState(24);
                }
                break;
            case 24:
                LLaunch.setPower(1);
                RLaunch.setPower(1);
                pathTimer.reset();
                setPathState(25);
                break;
            case 25:
                if (pathTimer.seconds() >= 0.5) {

                    // reverse arms
                    LLaunch.setPower(-1);
                    RLaunch.setPower(-1);

                    requestOpModeStop();
                    pathState = -1;
                }
                break;
        }
        return pathState;
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.reset();
    }
}
