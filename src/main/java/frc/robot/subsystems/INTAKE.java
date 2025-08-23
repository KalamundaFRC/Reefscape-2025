//by Patrick
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj2.command.Command;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class INTAKE extends SubsystemBase {
    AddressableLED m_led;
    AddressableLEDBuffer m_LedBuffer;
    WPI_VictorSPX jeff;
    
    public INTAKE(){
        jeff =  new WPI_VictorSPX(7);
        m_led = new AddressableLED(9);
        m_LedBuffer = new AddressableLEDBuffer(10);
        m_led.setLength(m_LedBuffer.getLength());

        m_led.setData(m_LedBuffer);
        m_led.start();
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

public void setIntake(double speed){
    jeff.set(speed);
    for (var i = 0; i < m_LedBuffer.getLength(); i++) {
        // Sets the specified LED to the RGB values for red
        m_LedBuffer.setRGB(i, 255, 0, 0);
     }
     
     m_led.setData(m_LedBuffer);
}

}