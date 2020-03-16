package fr.neocraft.main.util;

import java.io.Serializable;

import net.minecraft.entity.Entity;

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
	
	public Vector6f createSquare(int u, int v, int w)
	{
		return new Vector6f(x-u/2, y - v/2, z - w/2, u/2, v/2, w/2);
	}
	
	
	public static Vector3f getVectorFromEntity(Entity p)
	{
		return new Vector3f((int)p.posX,(int) p.posY, (int)p.posZ);
	}
}
