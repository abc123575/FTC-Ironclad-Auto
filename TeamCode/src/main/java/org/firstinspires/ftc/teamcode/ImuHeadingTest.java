package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp(name = "IMU Heading Test", group = "Test")
public class ImuHeadingTest extends OpMode {

    private IMU imu;

    @Override
    public void init() {
        // Get IMU from hardware map (name MUST match Robot Config)
        imu = hardwareMap.get(IMU.class, "imu");

        // IMPORTANT: set this to how your Control Hub is mounted
        RevHubOrientationOnRobot orientation =
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                );

        // Initialize IMU with correct orientation
        imu.initialize(new IMU.Parameters(orientation));
    }

    @Override
    public void loop() {
        YawPitchRollAngles angles = imu.getRobotYawPitchRollAngles();

        telemetry.addData("Yaw (deg)", angles.getYaw(AngleUnit.DEGREES));
        telemetry.addData("Pitch (deg)", angles.getPitch(AngleUnit.DEGREES));
        telemetry.addData("Roll (deg)", angles.getRoll(AngleUnit.DEGREES));
        telemetry.update();
    }
}
