package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.Command;

// import com.ctre.phoenix.motorcontrol.can.VictorSPX.;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
// import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
// import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.MathUtil;


public class Arm extends SubsystemBase{
  DoublePublisher xPub;
  DoublePublisher yPub;

    private WPI_VictorSPX ArmMotor1 = new WPI_VictorSPX(5);
    private WPI_VictorSPX ArmMotor2 = new WPI_VictorSPX(6);
    private DutyCycleEncoder ArmDutyCycleEncode = new DutyCycleEncoder(1);
    // private EncoderSim ArmEncoder = new EncoderSim(0);
    private PIDController ArmPidCon = new PIDController(5.0, 0, 0);
    private double DoubleEncoderOutput = ArmDutyCycleEncode.get();
    public Arm(){
        ArmMotor2.follow(ArmMotor1);
        // ArmMotor2.setInverted(true);

        // Get the default instance of NetworkTables that was created automatically
        // when the robot program starts
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        // Get the table within that instance that contains the data. There can
        // be as many tables as you like and exist to make it easier to organize
        // your data. In this case, it's a table called datatable.
        NetworkTable table = inst.getTable("datatable");
        // Start publishing topics within that table that correspond to the X and Y values
        // for some operation in your program.
        // The topic names are actually "/datatable/x" and "/datatable/y".
        xPub = table.getDoubleTopic("x").publish();
        yPub = table.getDoubleTopic("y").publish();
    }

    public Command movearm(double position){
        return runEnd(
            () -> {
                //makes a calculation using ArmPidCon.calculate
                Double target = MathUtil.clamp(position,Constants.ArmConstants.ArmLowerBoundLimit,Constants.ArmConstants.ArmUpperBoundLimit);
                Double result = MathUtil.clamp(ArmPidCon.calculate(ArmDutyCycleEncode.get(),target),-1*Constants.ArmConstants.ArmVelocityLimit,Constants.ArmConstants.ArmVelocityLimit);  
                ArmMotor1.set(result);
            },
            () -> {
                ArmMotor1.set(0);
            
            });
    }
    public Command rawcontrol(double speed){
        return run(
            () -> {
                ArmMotor1.set(speed);
            });
    }

    public Command BetterRaw(double speed){
        return startEnd(
            ()->{
                ArmMotor1.set(speed);
            }, 
            ()->{
                ArmMotor1.set(0);
            });
    }
    double time = 0;
    @Override
    public void periodic(){
        // // Publish values that are constantly increasing.
        // xPub.set(time);
        // yPub.set(DoubleEncoderOutput);
        // time += 0.05;
        // DoubleEncoderOutput = ArmDutyCycleEncode.get();
        // System.out.println("Arm Encoder " + DoubleEncoderOutput);
    }
}
