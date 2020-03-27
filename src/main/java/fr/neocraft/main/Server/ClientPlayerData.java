package fr.neocraft.main.Server;

import java.io.Serializable;
import java.util.ArrayList;

import fr.neocraft.main.Server.Quest.QuestClientGuiInfo;
import fr.neocraft.main.util.Vector3f;

public class ClientPlayerData implements Serializable{

	private static final long serialVersionUID = 32463413463453445L;

	public String grade;
	
	public String classe;
	public String race;

	public int power;
	
	public ArrayList<Vector3f> posMap = new  ArrayList<Vector3f>();
	public QuestClientGuiInfo[] clientQuest ;
	
	public double[] reputation = new double[2];
	
}
