package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;


public class Robot extends TimedRobot {
  //variables de motor
  Talon izq1;
  Talon der1;
  Talon izq2;
  Talon der2;
  //variable de joystick
  Joystick j;
  Timer tiempo;
  //motores de lanzamiento
  Talon lanz1;
  Talon lanz2;
  Talon lanz3;
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  @Override
  public void robotInit() 
  {
    //obtencion del motor mediante el puerto asignado
    izq1 = new Talon(0);
    izq2 = new Talon(1);
    der1 = new Talon(2);
    der2 = new Talon(3);
    tiempo = new Timer();
    //puerto usb del controlador
    j = new Joystick(0);

    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }
  //objeto velocidad de motores
  public void motor_speed(double l,double r){
    //motores derechos e izquierdos
    izq1.set(l);
    der1.set(r);
    izq2.set(l);
    der2.set(r);
  }
  
  @Override
  public void robotPeriodic() {    

  }

  @Override
  public void autonomousInit() 
  {
    m_autoSelected = m_chooser.getSelected();
    System.out.println("Auto selected: " + m_autoSelected);
  }

  @Override
  public void autonomousPeriodic() 
  {
    tiempo.start();
    int vueltas=1;
    for(;vueltas<4;vueltas++)
    {
      while(tiempo.get() < 25){
        if(tiempo.get() >= 8.2 && tiempo.get() < 8.5)//1er freno
        {
          motor_speed(0.0, 0.0);
        }
        if(tiempo.get() >= 8.5 && tiempo.get() < 8.9)  //1er vuelta
        {
          motor_speed(-0.55, -0.55);
        }
        //avanza 2
        if(tiempo.get() >= 12.2 && tiempo.get() < 12.5)  //2da freno
        {
          motor_speed(0.0, 0.0);
        }
        if(tiempo.get() >= 12.5 && tiempo.get() < 12.92)  //2do vuelta 
        {
          motor_speed(-0.55, -0.55);
        }
        //avanza 3
        if(tiempo.get() >= 20.5 && tiempo.get() < 20.8) //3er freno
        {
          motor_speed(0.0, 0.0);
        }
        if(tiempo.get() >= 20.8 && tiempo.get() < 21.24)//3er vuelta
        {
          motor_speed(-0.55, -0.55);
        }
        //avanza 4
        if(tiempo.get() >= 24.24 && tiempo.get() < 24.50)//cuarto freno
        {
          motor_speed(0.0, 0.0);
        }
        if(tiempo.get() >= 24.50 && tiempo.get() < 25.0)//3er vuelta
        {
          motor_speed(-0.55, -0.55);
        }
        if(tiempo.get() >= 25.0 && vueltas<4)
        {
          motor_speed(0.0, 0.0);
          tiempo.reset();
        }
        else
        {
          motor_speed(-0.53, 0.50);
        }
      } 
      //pausa en la lectura de codigo por 15 seg. 
    }
    tiempo.stop();
    motor_speed(0, 0);
  }
  @Override
  public void teleopPeriodic() 
  {
    double LT = j.getRawAxis(2);
    double RT = j.getRawAxis(3);
    double Palanca = j.getRawAxis(0);
    if(LT == 1 )
    {
      motor_speed(0.5,-0.5);    // retroceder si LT es presionado
    }
    if(RT == 1 ){
      motor_speed(-0.5, 0.5);   // avanzar si RT es presionado
    }
    if(RT > 0 && Palanca > 0)
    {
      motor_speed(-0.3, 0.5);  // Avanzar a la Derecha
    }
    if(RT > 0 && Palanca < 0)
    {
      motor_speed(-0.5, 0.3);  // Avanzar a la Izquierda
    }
    
    if(LT > 0 && Palanca > 0)
    {
      motor_speed(0.3, -0.5);  // Retroceder a la Derecha
    }
    if(LT > 0 && Palanca < 0)
    {
      motor_speed(0.5, -0.3);  // Retroceder a la Izquierda
    }
    else
    {
      if(j.getRawAxis(4) == 1)
      {
        motor_speed(-0.3,-0.3); 
      }
      if(j.getRawAxis(4) == -1)
      {
        motor_speed(0.3,0.3); 
      }  
      else
      {
        motor_speed(0,0);  // Apagar Motores
      }
    }
  }

  @Override
  public void testPeriodic() 
  {
    
  }
}