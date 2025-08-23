// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

// import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.jeffsdrivebase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.INTAKE;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.Commands;
// import edu.wpi.first.wpilibj2.command.WaitCommand;

public final class Autos{
  /** Example static factory for an autonomous command. */
  public static Command TaxiScore(jeffsdrivebase jeffbase,Arm arm,INTAKE intake) {
    return
    arm.movearm(Constants.ArmConstants.ArmAngleScoring).alongWith(
      jeffbase.jeffbasearcade(0, 0).withTimeout(0.5).andThen(
      jeffbase.jeffbasearcade(0.5, 0).withTimeout(0.25)).andThen(
      jeffbase.jeffbasearcade(0, 0).withTimeout(0.5)).andThen(
      jeffbase.jeffbasearcade(-0.5, 0).withTimeout(4)).andThen(
      jeffbase.jeffbasearcade(0, 0).withTimeout(0.5)).andThen(
      intake.moveIntake(-0.5).withTimeout(0.5)).andThen(
        jeffbase.jeffbasearcade(0.5, 0).withTimeout(0.5)));
  }

  public static Command Taxi(jeffsdrivebase jeffbase) {
    return
    jeffbase.jeffbasearcade(0, 0).withTimeout(0.5).andThen(
    jeffbase.jeffbasearcade(0.5, 0).withTimeout(0.25)).andThen(
    jeffbase.jeffbasearcade(0, 0).withTimeout(0.5)).andThen(
    jeffbase.jeffbasearcade(-0.5, 0).withTimeout(1)).andThen(
    jeffbase.jeffbasearcade(0, 0).withTimeout(0.5));
  }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
