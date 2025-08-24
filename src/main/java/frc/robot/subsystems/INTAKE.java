//by Patrick
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class INTAKE extends SubsystemBase {
    AddressableLED m_led;
    AddressableLEDBuffer m_LedBuffer;
    WPI_VictorSPX jeff;
    DigitalInput leftlimitSwitch;
    DigitalInput rightlimitSwitch;

    // private boolean LEDON = false;
    
    public INTAKE(){
        jeff =  new WPI_VictorSPX(7);
        m_led = new AddressableLED(9);
        m_LedBuffer = new AddressableLEDBuffer(10);
        m_led.setLength(m_LedBuffer.getLength());

        m_led.setData(m_LedBuffer);
        m_led.start();

        leftlimitSwitch = new DigitalInput(10);
        leftlimitSwitch = new DigitalInput(11);
    }
    public Command moveIntake(Double velocity){
        return startEnd(
        () ->{

            jeff.set(velocity);

        },
        () ->{

            jeff.set(0);

        });
            
        
    }

    // public void ledstatechange(){
    //     if(LEDON){
    //         LEDON = false;
    //     } else {
    //         LEDON = true;
    //     }
        
    // }

public void setIntake(double speed){
    jeff.set(speed);
    // if (LEDON){
    //     if (leftlimitSwitch.get() || rightlimitSwitch.get()) {
    //         for (var i = 0; i < m_LedBuffer.getLength(); i++) {
    //             // Sets the specified LED to the RGB values for red
    //             m_LedBuffer.setRGB(i, 0, 255, 0);
    //         }
            
    //         m_led.setData(m_LedBuffer);
    //     } else {
    //         for (var i = 0; i < m_LedBuffer.getLength(); i++) {
    //             // Sets the specified LED to the RGB values for red
    //             m_LedBuffer.setRGB(i, 255, 0, 0);
    //         }
            
    //         m_led.setData(m_LedBuffer);
    //     }
    // } else {
    //     for (var i = 0; i < m_LedBuffer.getLength(); i++) {
    //         // Sets the specified LED to the RGB values for red
    //         m_LedBuffer.setRGB(i, 0, 0, 0);
    //     }
    // }
    
}

}