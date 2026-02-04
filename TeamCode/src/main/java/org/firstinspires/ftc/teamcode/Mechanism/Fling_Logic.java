package org.firstinspires.ftc.teamcode.Mechanism;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Fling_Logic {
    private DcMotor LLaunch, RLaunch;
    private DcMotor intakeMotor;

    private ElapsedTime stateTimer = new ElapsedTime();
    private enum FlingState{
        DELAY,
        IDLE,
        SPIN_UP,
        END

    }

    private FlingState flingState;


    // -------- Intake (Spin Up) Constants --------
    private double INTAKE_ON = -1.0;
    private double INTAKE_OFF = 0.0;
    private double INTAKE_REVERSE = 1.0;
    private double INTAKE_TIME = 0.2; // keeps track of time

    // --------- Launch Logic ---------
    private double ARMS_DOWN = -1.0;
    private double  ARMS_UP = 1.0;
    private double ARMS_TIME = 1; // TO DO: test the time

    public void init(HardwareMap hwMap){
        LLaunch = hwMap.get(DcMotor.class, "LLaunch");
        RLaunch = hwMap.get(DcMotor.class, "RLaunch");
        intakeMotor = hwMap.get(DcMotor.class, "intake_motor");

        // tune PIDF
        // run using pinpoint

        flingState = FlingState.DELAY;

        LLaunch.setPower(0);
        RLaunch.setPower(0);
        intakeMotor.setPower(0);

    }

    public void update() {
        switch (flingState) {
            case DELAY:
                if (stateTimer.seconds() > 1) {
                    stateTimer.reset();
                    flingState = FlingState.IDLE;
                }
                break;
            case IDLE:

                    LLaunch.setPower(ARMS_UP);
                    RLaunch.setPower(ARMS_DOWN);
                    if (stateTimer.seconds() > 1) {
                        stateTimer.reset();
                        flingState = FlingState.SPIN_UP;



                }
                break;
            case SPIN_UP:
                intakeMotor.setPower(1);
                stateTimer.reset();
                flingState = FlingState.END;
                break;
                // ADD REVERSE if there are more or if there exists more using distance sensor
            case END:
                LLaunch.setPower(ARMS_DOWN);
                RLaunch.setPower(ARMS_UP);
                if (stateTimer.seconds() > ARMS_TIME) {
                    LLaunch.setPower(ARMS_DOWN);
                    RLaunch.setPower(ARMS_UP);

                    stateTimer.reset();
                }
                break;


        }



    }

    public boolean isBusy() {
        return flingState != FlingState.DELAY;
    }



}
