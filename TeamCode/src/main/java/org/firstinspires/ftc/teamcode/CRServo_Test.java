package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "CRServo_Test", group = "Test")
public class CRServo_Test extends OpMode {

    private CRServo testServo;

    @Override
    public void init() {
        testServo = hardwareMap.get(CRServo.class, "testServo");

        telemetry.addLine("CRServo Test Initialized");
        telemetry.addLine("A = spin left");
        telemetry.addLine("B = spin right");
        telemetry.addLine("X = stop");
        telemetry.update();
    }

    @Override
    public void loop() {

        if (gamepad1.a) {
            testServo.setPower(-1.0);   // spin one direction
        } else if (gamepad1.b) {
            testServo.setPower(1.0);    // spin other direction
        } else if (gamepad1.x) {
            testServo.setPower(0.0);    // stop
        }

        telemetry.addData("A pressed", gamepad1.a);
        telemetry.addData("B pressed", gamepad1.b);
        telemetry.addData("X pressed", gamepad1.x);
        telemetry.update();
    }
}
