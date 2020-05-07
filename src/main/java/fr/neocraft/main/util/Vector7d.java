package fr.neocraft.main.util;

import java.io.Serializable;

public class Vector7d  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9545645L;
	public double x,y,z,u,v,w, custom;
	public Vector7d(double X,double Y,double Z, double U, double V, double W, double Custom) {
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
