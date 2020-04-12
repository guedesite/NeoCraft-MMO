package fr.neocraft.main.util;

import java.io.Serializable;

public class Vector7f implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9545645L;
	public float x,y,z,u,v,w, custom;
	public Vector7f(float X,float Y,float Z, float U, float V, float W, float Custom) {
		x=X;
		y=Y;
		z=Z;
		u=U;
		v=V;
		w=W;
		custom=Custom;
	}
	
	public String toString() {
		return "[x:"+x+", y:"+y+", z:"+z+", u:"+u+", v:"+v+", w:"+w+", custom:"+custom+"]";
	}
	public String toSampleString() {
		return "["+x+":"+y+":"+z+":"+u+":"+v+":"+w+":"+custom+"]";
	}
	

	
}
