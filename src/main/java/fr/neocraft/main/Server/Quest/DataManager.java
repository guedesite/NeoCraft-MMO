package fr.neocraft.main.Server.Quest;

import java.util.ArrayList;
import java.util.Map;

import fr.neocraft.dialogue.PnjDialogue;
import fr.neocraft.dialogue.PnjDialogueManager;
import fr.neocraft.pnj.PnjData;
import fr.neocraft.pnj.PnjDataManager;
import fr.neocraft.quest.QuestData;
import fr.neocraft.quest.QuestDataManager;
import net.minecraft.entity.player.EntityPlayer;

public class DataManager {
	private static Map<Integer, QuestData> allQuest;
	private static Map<Integer, PnjData> allPnj;
	private static Map<Double, PnjDialogue> allDialogue;
	
	public static QuestData getQuestById(int id) {
		return allQuest.get((Integer)id);
	}
	public static PnjData getPnjById(int id) {
		return allPnj.get((Integer)id);
	}
	
	public static void register() {
		ArrayList<QuestData> q = QuestDataManager.LoadAllQuest("assets/quest.dat");
		for(QuestData qd:q)
		{
			allQuest.put(qd.id, qd);
		}
		
		ArrayList<PnjData> q2 = PnjDataManager.LoadAllPnj("assets/pnj.dat");
		for(PnjData qd:q2)
		{
			allPnj.put(qd.ID, qd);
		}
		
		ArrayList<PnjDialogue> q3 = PnjDialogueManager.LoadAllPnj("assets/dialogue.dat");
		for(PnjDialogue qd:q3)
		{
			allDialogue.put(qd.ID, qd);
		}
	}
	
	

	
}
