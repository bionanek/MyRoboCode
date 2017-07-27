package rsa.Common;

/**
 * Created by jakuburban on 20/07/2017.
 */
import java.lang.Math;

public class Vector2D {

  protected double dX;
  protected double dY;

  public double getX() {
    return dX;
  }

  public double getY() {
    return dY;
  }

  // Constructor methods ....

  public Vector2D() {
    dX = dY = 0.0;
  }

  public Vector2D( double dX, double dY ) {
    this.dX = dX;
    this.dY = dY;
  }

  // Convert vector to a string ...

  public String toString() {
    return "Vector2D(" + dX + ", " + dY + ")";
  }

  // Compute magnitude of vector ....

  public double length() {
    return Math.sqrt ( dX*dX + dY*dY );
  }

  // Sum of two vectors ....

  public Vector2D add( Vector2D v1 ) {
    Vector2D v2 = new Vector2D( this.dX + v1.dX, this.dY + v1.dY );
    return v2;
  }

  // Subtract vector v1 from v .....

  public Vector2D sub( Vector2D v1 ) {
    Vector2D v2 = new Vector2D( this.dX - v1.dX, this.dY - v1.dY );
    return v2;
  }

  // Scale vector by a constant ...

  public Vector2D scale( double scaleFactor ) {
    Vector2D v2 = new Vector2D( this.dX*scaleFactor, this.dY*scaleFactor );
    return v2;
  }

  // Normalize a vectors length....

  public Vector2D normalize() {
    Vector2D v2 = new Vector2D();

    double length = Math.sqrt( this.dX*this.dX + this.dY*this.dY );
    if (length != 0) {
      v2.dX = this.dX/length;
      v2.dY = this.dY/length;
    }

    return v2;
  }

  public Vector2D div(double d)
  {
    return new Vector2D(dX / d, dY / d);
  }

  public Vector2D mul( double d)
  {
    return new Vector2D(dX * d, dY * d);
  }

  // Dot product of two vectors .....

  public double dotProduct ( Vector2D v1 ) {
    return this.dX*v1.dX + this.dY*v1.dY;
  }

  // Distance between two vectors

  public static double distance(Vector2D v1, Vector2D v2) {
    return Math.sqrt((Math.pow(v1.getX(), 2) + Math.pow(v2.getY(), 2)));
  }
}