package org.firstinspires.ftc.teamcode.pedroPathing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="j", group="Test")
public class j extends LinearOpMode {

    private DcMotor FL;
    private DcMotor FR;
    private DcMotor BL;
    private DcMotor BR;

    @Override
    public void runOpMode() throws InterruptedException {

        // Initialize motors
        FL = hardwareMap.get(DcMotor.class, "FLmotor");
        FR = hardwareMap.get(DcMotor.class, "FRmotor");
        BL = hardwareMap.get(DcMotor.class, "BLmotor");
        BR = hardwareMap.get(DcMotor.class, "BRmotor");

        // Set correct directions for mecanum
        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);
        FR.setDirection(DcMotor.Direction.FORWARD);
        BR.setDirection(DcMotor.Direction.FORWARD);

        // Wait for start button
        waitForStart();

        if (opModeIsActive()) {

            // Drive forward at 50% power
            FL.setPower(0.5);
            FR.setPower(0.5);
            BL.setPower(0.5);
            BR.setPower(0.5);

            // Run for 2 seconds
            sleep(1000);

            // Stop motors
            FL.setPower(0);
            FR.setPower(0);
            BL.setPower(0);
            BR.setPower(0);
        }
    }
}
