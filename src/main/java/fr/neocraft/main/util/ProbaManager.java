package fr.neocraft.main.util;

import java.util.Random;

public class ProbaManager {

	private int nb;
	
	public Random r=new Random();
	
	public void start(int max) {
		nb = r.nextInt(max + 1);
	}
	
	public boolean Do(int chance) {
		if(nb > 0 && chance < nb)
		{
			nb -= chance;
			return true;
		}
		nb -= chance;
		return false;
	}
	
	public boolean DoOrder(int chance) {
		return nb > 0 && chance < nb;
	
	}
	
	public int nextInt(int bound)
	{
		int f = r.nextInt(bound) + 1;
		int i = r.nextInt(21);
		if(i < 15)
		{
			return f /3;
		}else if(i < 15)
		{
			return f /2;
		}else {
			return f;
		}
		
		
	}
	
}
