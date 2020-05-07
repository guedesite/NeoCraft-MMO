package fr.neocraft.main.util;

import java.io.Serializable;

import net.minecraft.entity.Entity;

public class Vector3d implements Serializable{

	private static final long serialVersionUID = 546456451117L;
	public double x,y,z;
	public Vector3d(double X,double Y,double Z) {
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
	
	public Vector6d createSquare(double u, double v, double w)
	{
		return new Vector6d(x-u/2, y - v/2, z - w/2, u/2, v/2, w/2);
	}

	
	public static Vector3d getVectorFromEntity(Entity p)
	{
		return new Vector3d((double)p.posX,(double) p.posY, (double)p.posZ);
	}
}
