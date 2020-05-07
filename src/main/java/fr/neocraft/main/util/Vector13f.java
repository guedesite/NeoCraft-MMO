package fr.neocraft.main.util;

import java.io.Serializable;

public class Vector13f implements Serializable{

	private static final long serialVersionUID = 4544575645L;
	public float x,y,z,u,v,w,rx,ry,rz,ru,rv,rw;
	public float custom = 0;
	public Vector13f(float X,float Y,float Z, float U, float V, float W, float rotX, float rotY, float rotZ, float rotU, float rotV, float rotW) {
		x=X;
		y=Y;
		z=Z;
		u=U;
		v=V;
		w=W;
		rx=rotX;
		ry=rotY;
		rz=rotZ;
		ru=rotU;
		rv=rotV;
		rw=rotW;
	}
	
	public String toString() {
		return "[x:"+x+", y:"+y+", z:"+z+", u:"+u+", v:"+v+", w:"+w+", rotX:"+rx+", rotY:"+ry+", rotZ:"+rz+", rotU:"+ru+", rotV:"+rv+", rotZ:"+rz+", custom:"+custom+"]";
	}
	public String toSampleString() {
		return "["+x+":"+y+":"+z+":"+u+":"+v+":"+w+":"+rx+":"+ry+":"+rz+":"+ru+":"+rv+":"+rw+":"+custom+"]";
	}
}
