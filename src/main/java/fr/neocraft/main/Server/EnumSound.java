package fr.neocraft.main.Server;

import fr.neocraft.main.Reference;

public enum EnumSound {
	NeoMChange(Reference.MOD_ID + ":menu.change"),
	NeoMClick(Reference.MOD_ID + ":menu.click"),
	NeoMClose(Reference.MOD_ID + ":menu.close"),
	NeoMLvl1(Reference.MOD_ID + ":menu.lvl1"),
	NeoMLvl2(Reference.MOD_ID + ":menu.lvl2"),
	NeoMLvl3(Reference.MOD_ID + ":menu.lvl3"),
	NeoMLvl4(Reference.MOD_ID + ":menu.lvl4"),
	NeoMNope(Reference.MOD_ID + ":menu.nope"),
	NeoMOpen(Reference.MOD_ID + ":menu.open"),
	NeoILoot(Reference.MOD_ID + ":item.loot"),
	NeoEHorraire(Reference.MOD_ID + ":horraire"),
	FuturError(Reference.MOD_ID + ":futur.error"), 
	FuturSuccess(Reference.MOD_ID + ":futur.success"), 
	FuturAction(Reference.MOD_ID + ":futur.action"), 
	NeoEnderDrag("mob.enderdragon.end");
 
    private String SOUND;
 
    EnumSound(String envUrl) {
        this.SOUND = envUrl;
    }
 
    public String getSound() {
        return SOUND;
    }
}
