package fr.neocraft.main.util;

import java.io.Serializable;

public class Vector6f implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4545645L;
	public int x,y,z,u,v,w;
	public Vector6f(int X,int Y,int Z, int U, int V, int W) {
		x=X;
		y=Y;
		z=Z;
		u=U;
		v=V;
		w=W;
	}
	
	public String toString() {
		return "[x:"+x+", y:"+y+", z:"+z+", u:"+u+", v:"+v+", w:"+w+"]";
	}
	public String toSampleString() {
		return "["+x+":"+y+":"+z+":"+u+":"+v+":"+w+"]";
	}
	
	public boolean isLessOrEqual(Vector6f v2)
	{
		return u <= v2.u & v <= v2.v & w <= v2.w;
	}
	public boolean isMoreOrEqualPos(Vector6f v2)
	{
		return x >= v2.x & y >= v2.y & z >= v2.z;
	}
	public boolean isLessOrEqualPos(Vector6f v2)
	{
		return x <= v2.x & y <= v2.y & z <= v2.z;
	}
	
	public boolean isEqual(Vector6f v2)
	{
		return v2.x == x & v2.y == y & v2.z == z & v2.u == u & v2.v == v & v2.w == w;
	}
	
	public boolean isUnder(Vector3f v2) {
		return v2.x >= x & v2.y >= y & v2.z >= z & v2.x <= x+u & v2.y <= y+v & v2.z <= z+w; 
	}
	
	public boolean isUnderExclu(Vector3f v2) {
		return v2.x > x & v2.y > y & v2.z > z & v2.x < x+u & v2.y < y+v & v2.z < z+w; 
	}
	
	public Vector6f copy() {
		return new Vector6f(x,y,z,u,v,w);
	}
	
}
