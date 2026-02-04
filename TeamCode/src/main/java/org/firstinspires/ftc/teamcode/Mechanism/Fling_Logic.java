package org.firstinspires.ftc.teamcode.Mechanism;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Fling_Logic {
    private DcMotor LLaunch, RLaunch;
    private DcMotor intakeMotor;

    private ElapsedTime timer = new ElapsedTime();

    private enum FlingState {
        START_FLING,   // first action: arms up
        IDLE,          // waiting for paths
        LOWER_ARMS     // lower arms at shooting positions
    }

    private FlingState state;

    private final double ARMS_UP = 1.0;
    private final double ARMS_DOWN = -1.0;
    private final double ARMS_TIME = 0.5; // time to fully move arms
    private final double INTAKE_POWER = 1;

    public void init(HardwareMap hwMap) {
        LLaunch = hwMap.get(DcMotor.class, "LLaunch");
        RLaunch = hwMap.get(DcMotor.class, "RLaunch");
        intakeMotor = hwMap.get(DcMotor.class, "intake_motor");

        // start arms in neutral
        LLaunch.setPower(0);
        RLaunch.setPower(0);
        intakeMotor.setPower(0); // intake can run always

        state = FlingState.START_FLING;
        timer.reset();
    }

    public void update() {
        switch (state) {
            case START_FLING:
                // immediately fling arms up
                LLaunch.setPower(ARMS_UP);
                RLaunch.setPower(ARMS_DOWN);

                if (timer.seconds() > ARMS_TIME) {
                    // done flinging
                    LLaunch.setPower(0);
                    RLaunch.setPower(0);
                    state = FlingState.IDLE;
                    timer.reset();
                }
                break;

            case IDLE:
                // just keep intake running
                intakeMotor.setPower(1);
                break;

            case LOWER_ARMS:
                LLaunch.setPower(ARMS_DOWN);
                RLaunch.setPower(ARMS_UP);

                if (timer.seconds() > ARMS_TIME) {
                    LLaunch.setPower(0);
                    RLaunch.setPower(0);
                    state = FlingState.IDLE;
                    timer.reset();
                }
                break;
        }
    }

    // call this when you reach shootingPoseL, PATH6, or PATH9
    public void lowerArms() {
        state = FlingState.LOWER_ARMS;
        timer.reset();
    }
    public void setArmsUp() {
        LLaunch.setPower(ARMS_UP);
        RLaunch.setPower(ARMS_DOWN);
    }


    public boolean isBusy() {
        return state != FlingState.IDLE;
    }
}
