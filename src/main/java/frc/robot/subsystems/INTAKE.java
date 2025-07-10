package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class INTAKE extends SubsystemBase {
    WPI_VictorSPX jeff;
    public INTAKE(){
        jeff =  new WPI_VictorSPX(0);

    }
    public Command moveIntake(Double velocity){
        return run(
        () ->{

            jeff.set(velocity);

        });
            
        
    }
    
public void setIntake(double speed){
    jeff.set(speed);
}

}