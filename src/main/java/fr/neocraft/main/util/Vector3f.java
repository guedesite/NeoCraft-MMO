package fr.neocraft.main.util;

import java.io.Serializable;

public class Vector3f implements Serializable{

	private static final long serialVersionUID = 546456451117L;
	public int x,y,z;
	public Vector3f(int X,int Y,int Z) {
		x=X;
		y=Y;
		z=Z;
	}
	
	public String toString() {
		return "[x:"+x+", y:"+y+", z:"+z+"]";
	}
	
	public boolean isEqual(Vector3f v2)
	{
		return v2.x == x & v2.y == y & v2.z == z;
	}
}
