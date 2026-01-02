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

@Autonomous
@Configurable
public class simplepath extends OpMode {

    private Follower follower;
    private Timer pathTimer, opModeTimer;
    private Paths paths;

    /* =======================
       State Machine
       ======================= */
    public enum PathState {
        DRIVE_PATH1,
        DRIVE_PATH2,
        DONE
    }

    private PathState pathState;

    /* =======================
       Paths Container
       ======================= */
    public static class Paths {
        public PathChain Path1;
        public PathChain Path2;

        public Paths(Follower follower) {

            Path1 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(56.000, 8.000),
                                    new Pose(55.763, 48.336)
                            )
                    )
                    .setLinearHeadingInterpolation(
                            Math.toRadians(90),
                            Math.toRadians(90)
                    )
                    .build();

            Path2 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(55.763, 48.336),
                                    new Pose(55.890, 26.273)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();
        }
    }

    /* =======================
       State Logic
       ======================= */
    private void statePathUpdate() {
        switch (pathState) {

            case DRIVE_PATH1:
                follower.followPath(paths.Path1, true);
                setPathState(PathState.DRIVE_PATH2);
                break;

            case DRIVE_PATH2:
                if (!follower.isBusy()) {
                    follower.followPath(paths.Path2, true);
                    setPathState(PathState.DONE);
                }
                break;

            case DONE:
                if (!follower.isBusy()) {
                    telemetry.addLine("All paths complete");
                }
                break;
        }
    }

    private void setPathState(PathState newState) {
        pathState = newState;
        pathTimer.resetTimer();
    }

    /* =======================
       OpMode Lifecycle
       ======================= */
    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);

        pathTimer = new Timer();
        opModeTimer = new Timer();

        paths = new Paths(follower);

        // Starting pose MUST match Path1 start
        follower.setPose(new Pose(56.000, 8.000, Math.toRadians(90)));

        pathState = PathState.DRIVE_PATH1;
    }

    @Override
    public void start() {
        opModeTimer.resetTimer();
        setPathState(pathState);
    }

    @Override
    public void loop() {
        follower.update();
        statePathUpdate();

        telemetry.addData("Path State", pathState);
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading", follower.getPose().getHeading());
        telemetry.addData("Path Time (s)", pathTimer.getElapsedTimeSeconds());
    }
}
