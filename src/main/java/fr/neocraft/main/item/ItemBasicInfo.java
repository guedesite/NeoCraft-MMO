package fr.neocraft.main.item;

import fr.neocraft.main.Reference;
import fr.neocraft.main.Init.ItemMod;
import net.minecraft.item.Item;

public class ItemBasicInfo extends Item{

	public ItemBasicInfo(String name) {
		this.setTextureName(Reference.MOD_ID + ":"+name);
		this.setUnlocalizedName(name);
			
	}
	
}
