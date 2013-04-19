package net.suberic.rikchik.language;

public interface Tentacle {

    public void setX (float value);
    public void setY (float value);
    public void setX2 (float value);
    public void setY2 (float value);
    public void setR1 (float value);
    public void setR2 (float value);
    public void setLeg (float value);
    public void setA1 (float value);
    public void setA2 (float value);
    public void setD (float value);
    public void set (String name, float value);

    public float getX ();
    public float getY ();
    public float getX2 ();
    public float getY2 ();
    public float getR1 ();
    public float getR2 ();
    public float getLeg ();
    public float getA1 ();
    public float getA2 ();
    public float getD ();

    public String toXMLString();

}
