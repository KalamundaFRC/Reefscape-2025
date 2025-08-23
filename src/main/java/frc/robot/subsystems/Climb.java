package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
// import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimbConstrants;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class Climb extends SubsystemBase {
    
    
    SparkMax climbmotor;
    private final RelativeEncoder Encoder;
    private final PIDController PIDLoop = new PIDController(0.1, 0, 0);
    
    public Climb(){
        climbmotor =  new SparkMax(8,MotorType.kBrushless);
        Encoder = climbmotor.getEncoder();


    }
    
    public Command MoveClimberFancy(){
        return runEnd(
        () ->{
            Double target = MathUtil.clamp(ClimbConstrants.ClimbPosition,ClimbConstrants.ClimbLimit,1);
            Double result = MathUtil.clamp(PIDLoop.calculate(Encoder.getPosition(),target),-1*ClimbConstrants.ClimbVelocityLimit,ClimbConstrants.ClimbVelocityLimit);
            climbmotor.set(result);
        },
        () ->{
            climbmotor.set(0);
        });
    }

    public Command moveClimb(Double velocity){
        return runEnd(
        () ->{
            if (Encoder.getPosition()<=ClimbConstrants.ClimbLimit){
                climbmotor.set(velocity);
            } else {
                climbmotor.set(0);
            }
            

        },
        () ->{

            climbmotor.set(0);

        });
     
        
    }
    @Override
    public void periodic(){
        // // Publish values that are constantly increasing.
        // double DoubleEncoderOutput;
        // DoubleEncoderOutput = Encoder.getPosition();
        // System.out.println("Climb Encoder " + DoubleEncoderOutput);
    }
    }