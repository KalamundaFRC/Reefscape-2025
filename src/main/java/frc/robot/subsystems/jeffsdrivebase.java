//by Patrick
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


import java.lang.reflect.WildcardType;
import java.util.function.Function;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class jeffsdrivebase extends SubsystemBase{
WPI_VictorSPX backleft;
WPI_VictorSPX backright;
WPI_TalonSRX frontleft;
WPI_TalonSRX frontright;

//ADIS16470_IMU gyro;
ADIS16448_IMU gyro;

DifferentialDrive jeffbase;

private ADIS16448_IMU gyroencoder = gyro;
private double DoubleGyroEncoder = gyroencoder.getGyroAngleZ();
public jeffsdrivebase(){
    backleft = new WPI_VictorSPX(3);
    backright = new WPI_VictorSPX(2);
    frontleft = new WPI_TalonSRX(4);
    frontright = new WPI_TalonSRX(1);

    gyro = new ADIS16448_IMU();
    
    frontright.setInverted(true);
    backright.setInverted(true);
    backleft.follow(frontleft);
    backright.follow(frontright);


    jeffbase = new DifferentialDrive(frontleft, frontright);

    }
public void worldconquerer(double forward, double rotate){

    jeffbase.arcadeDrive(forward, -rotate);
}

@Override
public void periodic()
 {
    DoubleGyroEncoder = gyroencoder.getGyroAngleZ();
    System.out.println("Gyro Output " + DoubleGyroEncoder);
 }
} 