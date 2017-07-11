package rsa;

import java.awt.Color;
import robocode.AdvancedRobot;
import robocode.Robot;
import robocode.RobotStatus;

import static robocode.util.Utils.normalRelativeAngle;

/**
 * Created by jakuburban on 10/07/2017.
 */
public class MyRobot extends AdvancedRobot {

  private int others;

  public void run(){
    setBodyColor(Color.black);
    setGunColor(Color.red);
    setRadarColor(Color.white);
    setBulletColor(Color.blue);
    setScanColor(Color.orange);

    others = getOthers();

    goTo(getBattleFieldWidth() ,getBattleFieldHeight());
    goTo(0 ,0);

  }

  private void goTo(double x, double y){

    x -= getX();
    y -= getY();

    double angleToTarget = Math.atan2(x, y);
    double targetAngle = normalRelativeAngle(angleToTarget - getHeadingRadians());

    double distance = Math.hypot(x, y);

    double turnAngle = Math.atan(Math.tan(targetAngle));
    setTurnRightRadians(turnAngle);
    if(targetAngle == turnAngle){
      setAhead(distance);
    } else {
      setBack(distance);
    }

  }

}
