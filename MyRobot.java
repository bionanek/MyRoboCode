package rsa;

import java.awt.Color;
import robocode.AdvancedRobot;
import robocode.HitWallEvent;
import robocode.RobotStatus;
import robocode.ScannedRobotEvent;
import robocode.StatusEvent;
import rsa.Common.Vector2D;

import static robocode.util.Utils.normalRelativeAngle;

/**
 * Created by jakuburban on 10/07/2017.
 */
public class MyRobot extends AdvancedRobot {

  private byte scanDir = 1;

  private RobotStatus robotStatus;
  private int movementDirection = 1;
  private double previousEnergy = 100;
  private Vector2D previousEnemyPosition = new Vector2D();


  public void run(){
    robotSetup();

    setAdjustRadarForRobotTurn(true);

    while(true) {
      setTurnRadarRight(360);
      execute();
    }
  }

  public void onStatus(StatusEvent e) {
    this.robotStatus = e.getStatus();
  }

  public void onScannedRobot(ScannedRobotEvent e) {
    //setTurnRadarRight(getHeading() - getRadarHeading() +  normalizeBearing(e.getBearing()));
    scanDir *= -1;
    setTurnRadarRight(360 * scanDir);
    setTurnGunRight(getHeading() - getGunHeading() +  e.getBearing());

    Vector2D enemyPos = getEnemyPosition(e);
    if(enemyPos != previousEnemyPosition)
      goTo(enemyPos.getX(), enemyPos.getY());

    turnSidewaysToEnemy(e);

    if(getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 10) // check if the gun is cool and pointed at the target before shooting
      setFire(Math.min(400 / e.getDistance(), 3)); // set fire power appropriate to distance, it's floored down if math gives power higher than 3 because 3 is maximum value for power

    dodgeBullet(e);
    previousEnergy = e.getEnergy();
    previousEnemyPosition = enemyPos;
    execute();
  }

  public void onHitWall(HitWallEvent e) {

  }

  private double normalizeBearing(double angle) {
    while (angle > 180) angle -= 360;
    while (angle < -180) angle += 360;

    return angle;
  }

  private void dodgeBullet(ScannedRobotEvent e) {
    double changeInEnergy = previousEnergy - e.getEnergy();
    if (changeInEnergy > 0
        && changeInEnergy <= 3) { // because energy of fire costs between 0.1 and 3
      // Dodge!
      movementDirection = -movementDirection;
      setAhead(200 * movementDirection);
      execute();
    }
  }

  private void turnSidewaysToEnemy(ScannedRobotEvent e) {
    setTurnRight(normalizeBearing(e.getBearing()) + 90 - 30 * movementDirection);
    execute();
  }

  private Vector2D getEnemyPosition(ScannedRobotEvent e){
    double angleToEnemy = e.getBearing();

    //Calculate the angle to the scanned enemy
    double angle = Math.toRadians((robotStatus.getHeading() + angleToEnemy) % 360);

    //Calculate the coordinates of the robot

    double enemyX = (robotStatus.getX() + Math.sin(angle) * e.getDistance());
    double enemyY = (robotStatus.getY() + Math.cos(angle) * e.getDistance());

    return new Vector2D(enemyX, enemyY);
  }

  private void goTo(double x, double y){

    x -= getX();
    y -= getY();

    double angleToTarget = Math.atan2(x, y);
    double targetAngle = normalRelativeAngle(angleToTarget - getHeadingRadians());

    double distance = Math.hypot(x, y) - 100;

    double turnAngle = Math.atan(Math.tan(targetAngle));
    setTurnRightRadians(turnAngle);
    if(targetAngle == turnAngle){
      setAhead(distance);
    } else {
      setBack(distance);
    }
    execute();
  }

  private void robotSetup() {
    setBodyColor(Color.red);
    setGunColor(Color.white);
    setRadarColor(Color.white);
    setBulletColor(Color.yellow);
    setScanColor(Color.orange);
  }
}
