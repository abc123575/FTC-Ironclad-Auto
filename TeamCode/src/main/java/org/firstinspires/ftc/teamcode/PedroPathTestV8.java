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
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "Pedro Pathing V8", group = "Autonomous")
@Configurable // Panels
public class PedroPathTestV8 extends OpMode {

    private TelemetryManager panelsTelemetry; // Panels Telemetry instance
    public Follower follower; // Pedro Pathing follower instance
    private int pathState; // Current autonomous path state (state machine)
    private ElapsedTime pathTimer; // Timer for path state machine
    private Paths paths; // Paths defined in the Paths class

    @Override
    public void init() {
        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(72, 8, Math.toRadians(90)));

        pathTimer = new ElapsedTime();
        paths = new Paths(follower); // Build paths

        panelsTelemetry.debug("Status", "Initialized");
        panelsTelemetry.update(telemetry);
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

        public PathChain Path1;
        public PathChain Path2;
        public PathChain Path3;
        public PathChain Path4;
        public PathChain Path5;
        public PathChain path6;
        public PathChain path7;
        public PathChain path8;
        public PathChain Path9;

        public Paths(Follower follower) {
            Path1 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(20.403, 123.144), new Pose(60.000, 84.000))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                    .build();

            Path2 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(60.000, 84.000), new Pose(12.000, 84.000))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                    .build();

            Path3 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(12.000, 84.000), new Pose(36.000, 108.000))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .build();

            Path4 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(36.000, 108.000), new Pose(60.000, 60.600))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                    .build();

            Path5 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(60.000, 60.600),
                                    new Pose(33.031, 62.243),
                                    new Pose(14.000, 59.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(190))
                    .build();

            path6 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(14.000, 59.000),
                                    new Pose(27.141, 73.624),
                                    new Pose(36.000, 108.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(190), Math.toRadians(135))
                    .build();

            path7 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(36.000, 108.000),
                                    new Pose(26.506, 60.607),
                                    new Pose(60.300, 35.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                    .build();

            path8 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(60.300, 35.000), new Pose(15.300, 34.900))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                    .build();

            Path9 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(15.300, 34.900),
                                    new Pose(9.312, 60.258),
                                    new Pose(36.000, 108.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .build();
        }
    }

    public int autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(paths.Path1, true);
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {
                    setPathState(2);
                }
                break;
            case 2:
                follower.followPath(paths.Path2, true);
                setPathState(3);
                break;
            case 3:
                if (!follower.isBusy()) {
                    setPathState(4);
                }
                break;
            case 4:
                follower.followPath(paths.Path3, true);
                setPathState(5);
                break;
            case 5:
                if (!follower.isBusy()) {
                    setPathState(6);
                }
                break;
            case 6:
                setPathState(7);
                break;
            case 7:
                    setPathState(8);
                break;
            case 8:
                follower.followPath(paths.Path4, true);
                setPathState(9);
                break;
            case 9:
                if (!follower.isBusy()) {
                    setPathState(10);
                }
                break;
            case 10:
                follower.followPath(paths.Path5, true);
                setPathState(11);
                break;
            case 11:
                if (!follower.isBusy()) {
                    setPathState(12);
                }
                break;
            case 12:
                follower.followPath(paths.path6, true);
                setPathState(13);
                break;
            case 13:
                if (!follower.isBusy()) {
                    setPathState(14);
                }
                break;
            case 14:
                setPathState(15);
                break;
            case 15:
                    setPathState(16);
                break;
            case 16:
                follower.followPath(paths.path7, true);
                setPathState(17);
                break;
            case 17:
                if (!follower.isBusy()) {
                    setPathState(18);
                }
                break;
            case 18:
                follower.followPath(paths.path8, true);
                setPathState(19);
                break;
            case 19:
                if (!follower.isBusy()) {
                    setPathState(20);
                }
                break;
            case 20:
                follower.followPath(paths.Path9, true);
                setPathState(21);
                break;
            case 21:
                if (!follower.isBusy()) {
                    setPathState(22);
                }
                break;
            case 22:
                setPathState(23);
                break;
            case 23:
                    setPathState(24);
                break;
            case 24:
                requestOpModeStop();
                pathState = -1;
                break;
        }
        return pathState;
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.reset();
    }
}
