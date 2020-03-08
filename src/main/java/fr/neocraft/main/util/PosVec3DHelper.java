package fr.neocraft.main.util;

public class PosVec3DHelper {

	public static Vector6f getMinPosAndVec(Vector3f pos1, Vector3f pos2)
	{
		Vector6f v = new Vector6f(0,0,0,0,0,0);
		if(pos1.x < pos2.x)
		{
			v.x = pos1.x;
			v.u = pos2.x - pos1.x;
		} else {
			v.x = pos2.x;
			v.u = pos1.x - pos2.x;
		}
		if(pos1.y < pos2.y)
		{
			v.y = pos1.y;
			v.v = pos2.y - pos1.y;
		} else {
			v.y = pos2.y;
			v.v = pos1.y - pos2.y;
		}
		if(pos1.z < pos2.z)
		{
			v.z = pos1.z;
			v.w = pos2.z - pos1.z;
		} else {
			v.z = pos2.z;
			v.w = pos1.z - pos2.z;
		}
		return v;
	}
	
}
