// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.Arm;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.INTAKE;
import frc.robot.subsystems.jeffsdrivebase;
import frc.robot.subsystems.Climb;
// import frc.robot.Constants.ClimbConstrants;

// import java.nio.file.ClosedFileSystemException;

// import edu.wpi.first.math.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;



/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer extends SequentialCommandGroup {
  // The robot's subsystems and commands are defined here...
  private final INTAKE m_INTAKE = new INTAKE(); 
  private final jeffsdrivebase m_Jeffsdrivebase = new jeffsdrivebase();
  private final Climb m_Climb = new Climb();
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Arm m_arm = new Arm();
  private static SendableChooser<Command> autochooser;
  
  Command a_taxiscoremid = Autos.TaxiScore(m_Jeffsdrivebase, m_arm, m_INTAKE);
  Command a_taximid = Autos.Taxi(m_Jeffsdrivebase);

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final CommandXboxController m_toaster =
      new CommandXboxController(OperatorConstants.kToasterControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Addes a autochooser for matches
    autochooser = new SendableChooser<>();
    autochooser.addOption("TaxiScoreMid", a_taxiscoremid);
    autochooser.addOption("TaxiMid", a_taximid);
    
    SmartDashboard.putData("autochooser",autochooser);
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    m_Jeffsdrivebase.setDefaultCommand(
      new RunCommand(
        //Drive base
        () -> m_Jeffsdrivebase.worldconquerer(
          m_driverController.getLeftY(),
           m_driverController.getRightX()),
            m_Jeffsdrivebase));
    //Moves arm when no buttons pressed
    m_arm.setDefaultCommand(
      m_arm.movearm(ArmConstants.ArmAngleStowed)
    );
    // *Arm Controls* //
    m_driverController.rightBumper().whileTrue(m_arm.movearm(ArmConstants.ArmAngleScoring));
    m_driverController.leftTrigger().whileTrue(m_arm.movearm(ArmConstants.ArmAngleGround).alongWith(m_INTAKE.moveIntake(.8)));
    m_driverController.rightTrigger().whileTrue(m_arm.movearm(ArmConstants.ArmAngleScoring).alongWith(m_INTAKE.moveIntake(-0.25)));
    
    // **CLimb stuff** //
    //Toaster defrost
    m_toaster.a().whileTrue(m_Climb.moveClimb(0.5));
    m_toaster.a().onTrue(m_arm.movearm(ArmConstants.ArmAngleGround));
    //Toaster Toast lever
    m_toaster.axisGreaterThan(0, 0.67).whileTrue(m_Climb.moveClimb(0.5));
    m_toaster.axisGreaterThan(0, 0.67).onTrue(m_arm.movearm(ArmConstants.ArmAngleGround));
    //Xbox Controller A button
    m_driverController.a().whileTrue(m_Climb.moveClimb(0.5));
    m_driverController.a().onTrue(m_arm.movearm(ArmConstants.ArmAngleGround));
    
    }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    // return Autos.TaxiScore(m_Jeffsdrivebase,m_arm,m_INTAKE);
    return autochooser.getSelected();
  }
}
