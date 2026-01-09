package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Constants {

    /* ===================== PEDRO CONSTANTS (UNCHANGED) ===================== */

    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(9)
            .forwardZeroPowerAcceleration(-45.6)
            .lateralZeroPowerAcceleration(-65.5)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.06, 0.0, 0.0001, 0.025))
            .headingPIDFCoefficients(new PIDFCoefficients(0.4, 0.0, 0.0001, 0.025))
            .drivePIDFCoefficients(
                    new FilteredPIDFCoefficients(0.50, 0.00, 0.001, 0.025, 0.025)
            );

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("FRmotor")
            .rightRearMotorName("BRmotor")
            .leftRearMotorName("BLmotor")
            .leftFrontMotorName("FLmotor")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .xVelocity(48.88)
            .yVelocity(37.97);

    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(6.75)
            .strafePodX(3.5)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(
                    GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD
            )
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD);

    public static PathConstraints pathConstraints = new PathConstraints(
            0.99,
            100,
            1,
            1
    );

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .pinpointLocalizer(localizerConstants)
                .build();
    }

    // IMU TEST ADDITIONS (SAFE)

    // IMU reference ONLY for testing
    private static IMU testImu;

    /**
     * Call this ONLY in a test OpMode if you want to verify IMU yaw.
     * This does NOT affect Pedro or Pinpoint.
     */
    public static void initImuTest(HardwareMap hardwareMap) {
        testImu = hardwareMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot orientation =
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                );

        testImu.initialize(new IMU.Parameters(orientation));
    }

    /**
     * Returns IMU yaw in degrees for testing.
     * Safe to call ONLY if initImuTest() was called.
     */
    public static double getImuYawDegrees() {
        if (testImu == null) return 0.0;

        return testImu
                .getRobotYawPitchRollAngles()
                .getYaw(AngleUnit.DEGREES);
    }
}
