package fr.neocraft.main.util;

import java.io.Serializable;

import net.minecraft.entity.Entity;

public class Vector4d implements Serializable{

	private static final long serialVersionUID = 546456451117L;
	public double x,y,z, custom;
	public Vector4d(double X,double Y,double Z, double Custom) {
		x=X;
		y=Y;
		z=Z;
		custom=Custom;
	}
	
	public String toString() {
		return "[x:"+x+", y:"+y+", z:"+z+"]";
	}
	
	public boolean isEqual(Vector4d v2)
	{
		return v2.x == x & v2.y == y & v2.z == z && v2.custom == custom;
	}
	
	public boolean isEqual(Vector3d v2)
	{
		return v2.x == x & v2.y == y & v2.z == z;
	}
	
	public Vector7d createSquare(double u, double v, double w)
	{
		return new Vector7d(x-u/2, y - v/2, z - w/2, u/2, v/2, w/2, custom);
	}
	
	
	public static Vector3d getVectorFromEntity(Entity p)
	{
		return new Vector3d((double)p.posX,(double) p.posY, (double)p.posZ);
	}


}
