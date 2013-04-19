package net.suberic.rikchik.language;

import net.suberic.rikchik.language.Tentacle;

public abstract class AbstractTentacle implements Tentacle /*, Comparable */{

    protected float x;
    protected float y;
    protected float x2;
    protected float y2;
    protected float r1;
    protected float r2;
    protected float leg;
    protected float a1;
    protected float a2;	
    protected float d;

    protected int sortvalue;

    public void setX (float value){
	this.x = value;
    }
    public void setY (float value){
	this.y = value;
    }
    public void setX2 (float value){
	this.x2 = value;
    }
    public void setY2 (float value){
	this.y2 = value;
    }
    public void setR1 (float value){
	this.r1 = value;
    }
    public void setR2 (float value){
	this.r2 = value;
    }
    public void setLeg (float value){
	this.leg = value;
    }
    public void setA1 (float value){
	this.a1 = value;
    }
    public void setA2 (float value){
	this.a2 = value;
    }	
    public void setD (float value){
	this.d = value;
    }
    
    public void set (String name, float value){
	if (name.equals ("x")){
	    setX (value);
	} else if (name.equals ("y")){
	    setY (value);
	} else if (name.equals ("x2")){
	    setX2 (value);
	} else if (name.equals ("y2")){
	    setY2 (value);
	} else if (name.equals ("r1")){
	    setR1 (value);
	} else if (name.equals ("r2")){
	    setR2 (value);
	} else if (name.equals ("leg")){
	    setLeg (value);
	} else if (name.equals ("a1")){
	    setA1 (value);
	} else if (name.equals ("a2")){
	    setA2 (value);
	} else if (name.equals ("d")){
	    setD (value);
	}
    }

    public float getX (){
	return this.x;
    }
    public float getY (){
	return this.y;
    }
    public float getX2 (){
	return this.x2;
    }
    public float getY2 (){
	return this.y2;
    }
    public float getR1 (){
	return this.r1;
    }
    public float getR2 (){
	return this.r2;
    }
    public float getLeg (){
	return this.leg;
    }
    public float getA1 (){
	return this.a1;
    }
    public float getA2 (){
	return this.a2;
    }	
    public float getD (){
	return this.d;
    }
    

}
