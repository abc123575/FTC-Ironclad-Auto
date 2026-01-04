package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.TelemetryManager;
import com.bylazar.telemetry.PanelsTelemetry;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;
import com.pedropathing.geometry.Pose;

@Autonomous(name = "bet", group = "Autonomous")
@Configurable
public class bet extends OpMode {

    private TelemetryManager panelsTelemetry;
    public Follower follower;

    private int pathState;
    private Paths paths;

    @Override
    public void init() {
        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();

        follower = Constants.createFollower(hardwareMap);

        // 🔴 MUST MATCH PATH START
        follower.setStartingPose(
                new Pose(23.262, 125.667, Math.toRadians(-35))
        );

        paths = new Paths(follower);

        panelsTelemetry.debug("Status", "Initialized");
        panelsTelemetry.update(telemetry);
    }

    // 🔴 REQUIRED TO START MOVEMENT
    @Override
    public void start() {
        pathState = 0;
        follower.followPath(paths.Path1);
    }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();

        panelsTelemetry.debug("Path State", pathState);
        panelsTelemetry.debug("Busy", follower.isBusy());
        panelsTelemetry.debug("X", follower.getPose().getX());
        panelsTelemetry.debug("Y", follower.getPose().getY());
        panelsTelemetry.debug("Heading", follower.getPose().getHeading());
        panelsTelemetry.update(telemetry);
    }

    // ✅ SIMPLE 1-PATH STATE MACHINE
    public int autonomousPathUpdate() {

        switch (pathState) {

            case 0: // following Path1
                if (!follower.isBusy()) {
                    pathState = 1; // done
                }
                break;

            case 1:
                // finished, do nothing
                break;
        }

        return pathState;
    }

    // ---------------- PATHS ----------------

    public static class Paths {
        public PathChain Path1;

        public Paths(Follower follower) {

            Path1 = follower.pathBuilder()
                    .addPath(new BezierLine(
                            new Pose(23.262, 125.667),
                            new Pose(54.339, 104.086)
                    ))
                    .setLinearHeadingInterpolation(
                            Math.toRadians(-35),
                            Math.toRadians(-90)
                    )
                    .build();
        }
    }
}
