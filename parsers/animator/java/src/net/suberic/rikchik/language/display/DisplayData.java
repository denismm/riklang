package net.suberic.rikchik.language.display;

public class DisplayData{

    public static double RAD_DEG = Math.PI / 180.0;

    private static float scale = 1;
    private static float margin = 0;

    public static void setScale (float newScale){
	scale = newScale;
    }

    public static float getScale(){
	return scale;
    }

    public static void setMargin (float newMargin){
	margin = newMargin;
    }

    public static float getMargin(){
	return margin;
    }

    public static int roundCoord(float value){
	return Math.round((value+margin) * scale);
    }
    
    public static int roundSize(float value){
	return Math.round((value) * scale);
    }

    public static int roundDimension(float value){
	return Math.round((value + margin*2) * scale);
    }
    
    
    private DisplayData(){}

    public static float xFromPoint (float x, float theta, float r){
	double thetaRad = RAD_DEG * theta; 
	double retX =  x + (Math.cos(thetaRad)*r);
	return (float) retX;
    }

    public static float yFromPoint(float y, float theta, float r){
	double thetaRad = RAD_DEG * theta; 
	double retY =  y - (Math.sin(thetaRad)*r);
	return (float) retY;
    }

    /**
       returns the angle in degrees of the line from x,y to x2,y2. 
    */
    public static float degreesBetweenPoints (float x, float y, float x2, float y2){
	float a = y-y2;
	float b = x2-x;
	double rads = Math.atan2(a,b);
	double degrees = rads / RAD_DEG;
	/*float xyDist = distanceBetweenPoints(x,y,x2,y2);
	  float a2 = -yFromPoint(0, (float) degrees, xyDist); //should match a
	  float b2 = xFromPoint(0, (float) degrees, xyDist); //should match b
	  float abDist = distanceBetweenPoints(b,a,b2,a2);
	  if (abDist > xyDist){
	  System.err.println ("hey! " + degrees + ":" + abDist);
	  degrees *= -1;
	  } else {
	  System.err.println ("okay " + degrees + ":" + abDist);
	  }
	*/
	return (float)degrees;
    }

    public static float distanceBetweenPoints (float x, float y, float x2, float y2){
	float retDist = (float)Math.sqrt((x2-x)*(x2-x) + (y2-y)*(y2-y));
	return retDist;
    }

    /**
       returns the total length of a bend.
    */
    public static float lengthOfBend(float radius, float arcAngle,
				     float startLeg, float endLeg){
	float diameter = (float)(radius * 2 * Math.PI);
	float sectorRatio = arcAngle / 360;
	float length = startLeg + diameter*sectorRatio + endLeg;
	return length;
    }

    /**
       returns the total length of an elliptical bend.
    */
    public static float lengthOfBend(float radius, float radius2, 
				     float arcAngle,
				     float startLeg, float endLeg){
	float approxRadius = (float)Math.sqrt((radius*radius + radius2* radius2) / 2);
	float diameter = (float)(approxRadius * 2 * Math.PI);
	float sectorRatio = arcAngle / 360;
	float length = startLeg + diameter*sectorRatio + endLeg;
	return length;
    }

    /**
       converts an angle from a circle's reference to an ellipse's.
    */
    public static float convertAngle(float angle, float radius1, float radius2){
	if ((radius1 == radius2) || (angle % 90 == 0)){
	    return angle;
	} else {
	    float retAngle = degreesBetweenPoints
		(0,0,
		 xFromPoint(0,angle,radius1),
		 yFromPoint(0,angle,radius1)*radius2/radius1);
	    retAngle += Math.round(angle / 360) * 360;
	    //System.err.println("altered: " + (retAngle-angle));
	    return retAngle;
	}
    }

    public static float interpolateValue (float value1, float value2, float doneRatio){
	return (value1 + (value2 - value1) * doneRatio);
    }

}

