package fr.neocraft.main.util;

import java.util.Random;

import fr.neocraft.main.entity.EntityNeoCreature;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

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
	
	public static Vector3d RotateArroundBase(Vector3d v, double d)  {
		
		double sin = Math.sin(d);
		double cos = Math.cos(d);
		double x = v.x;
		double z = v.z;
		v.x = x * cos + z * sin; 
		v.z = z * cos - x * sin; 
		
		 /*   var sinTheta = sin(theta);
		    var cosTheta = cos(theta);
		    
		    for (var n=0; n<nodes.length; n++) {
		        var node = nodes[n];
		        var x = node[0];
		        var z = node[2];
		        node[0] = x * cosTheta + z * sinTheta;
		        node[2] = z * cosTheta - x * sinTheta;
		    } */
		return v;
	}
	
	public static Vector3d[] Create3DCubeAtBase(Vector3d v) {
		Vector3d[] finale = new Vector3d[24];
		finale[0] = new Vector3d(0,0,0);
		finale[1] = new Vector3d(v.x,0,0);
		
		finale[2] = new Vector3d(v.x,0,0);
		finale[3] = new Vector3d(v.x,0,v.z);
		
		finale[4] = new Vector3d(v.x,0,v.z);
		finale[5] = new Vector3d(0,0,v.z);
		
		finale[6] = new Vector3d(0,0,v.z);
		finale[7] = new Vector3d(0,0,0);
		
		
		
		finale[8] = new Vector3d(0,v.y,0);
		finale[9] = new Vector3d(v.x,v.y,0);
		
		finale[10] = new Vector3d(v.x,v.y,0);
		finale[11] = new Vector3d(v.x,v.y,v.z);
		
		finale[12] = new Vector3d(v.x,v.y,v.z);
		finale[13] = new Vector3d(0,v.y,v.z);
		
		finale[14] = new Vector3d(0,v.y,v.z);
		finale[15] = new Vector3d(0,v.y,0);
		
		
		
		finale[16] = new Vector3d(0,0,0);
		finale[17] = new Vector3d(0,v.y,0);
		
		finale[18] = new Vector3d(0,0,v.z);
		finale[19] = new Vector3d(0,v.y,v.z);
		
		finale[20] = new Vector3d(v.x,0,v.z);
		finale[21] = new Vector3d(v.x,v.y,v.z);
		
		finale[22] = new Vector3d(v.x,0,0);
		finale[23] = new Vector3d(v.x,v.y,0);
		
		return finale;
	}
	
	public static float getDistanceSquared(double posX, double posY, double posZ, int p_71569_1_, int p_71569_2_, int p_71569_3_)
    {
        float f = (float)(posX - p_71569_1_);
        float f1 = (float)(posY - p_71569_2_);
        float f2 = (float)(posZ - p_71569_3_);
        return f * f + f1 * f1 + f2 * f2;
    }
	
	 public static Vector3d findRandomTarget(EntityNeoCreature p_75462_0_, int p_75462_1_, int p_75462_2_, Vec3 p_75462_3_)
	    {
	        Random random = p_75462_0_.getRNG();
	        boolean flag = false;
	        int k = 0;
	        int l = 0;
	        int i1 = 0;
	        float f = -99999.0F;
	        boolean flag1;
	        
	        if (p_75462_0_.hasHome())
	        {
	        	Vector4d v = p_75462_0_.getHomePosition();
	            double d0 = (double)(getDistanceSquared(v.x, v.y, v.z, MathHelper.floor_double(p_75462_0_.posX), MathHelper.floor_double(p_75462_0_.posY), MathHelper.floor_double(p_75462_0_.posZ)) + 4.0F);
	            double d1 = (double)(v.custom + (float)p_75462_1_);
	            flag1 = d0 < d1 * d1;
	        }
	        else
	        {
	            flag1 = false;
	        }

	        for (int l1 = 0; l1 < 10; ++l1)
	        {
	            int j1 = random.nextInt(2 * p_75462_1_) - p_75462_1_;
	            int i2 = random.nextInt(2 * p_75462_2_) - p_75462_2_;
	            int k1 = random.nextInt(2 * p_75462_1_) - p_75462_1_;

	            if (p_75462_3_ == null || (double)j1 * p_75462_3_.xCoord + (double)k1 * p_75462_3_.zCoord >= 0.0D)
	            {
	                j1 += MathHelper.floor_double(p_75462_0_.posX);
	                i2 += MathHelper.floor_double(p_75462_0_.posY);
	                k1 += MathHelper.floor_double(p_75462_0_.posZ);

	                if (!flag1 || p_75462_0_.isWithinHomeDistance(j1, i2, k1))
	                {
	                    float f1 = 0;

	                    if (f1 > f)
	                    {
	                        f = f1;
	                        k = j1;
	                        l = i2;
	                        i1 = k1;
	                        flag = true;
	                    }
	                }
	            }
	        }

	        if (flag)
	        {
	            return new Vector3d((double)k, (double)l, (double)i1);
	        }
	        else
	        {
	            return null;
	        }
	    }
	
}
