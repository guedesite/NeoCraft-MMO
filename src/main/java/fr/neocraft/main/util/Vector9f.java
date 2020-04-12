package fr.neocraft.main.util;

import java.io.Serializable;

public class Vector9f implements Serializable{

	private static final long serialVersionUID = 454545645L;
	public float x,y,z,u,v,w,rx,ry,rz;
	public Vector9f(float X,float Y,float Z, float U, float V, float W, float rotX, float rotY, float rotZ) {
		x=X;
		y=Y;
		z=Z;
		u=U;
		v=V;
		w=W;
		rx=rotX;
		ry=rotY;
		rz=rotZ;
				
	}
	
	public String toString() {
		return "[x:"+x+", y:"+y+", z:"+z+", u:"+u+", v:"+v+", w:"+w+", rotX:"+rx+", rotY:"+ry+", rotZ:"+rz+"]";
	}
	public String toSampleString() {
		return "["+x+":"+y+":"+z+":"+u+":"+v+":"+w+":"+rx+":"+ry+":"+rz+"]";
	}
}
