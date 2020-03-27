package fr.neocraft.main.Server.Quest;

import java.io.Serializable;

public class QuestClientGuiInfo implements Serializable {

	private static final long serialVersionUID = 1454354551L;

	public Object[][] conditions;
	public Object[][] recompense;
	public String titre;
	public String texte;
	
}
