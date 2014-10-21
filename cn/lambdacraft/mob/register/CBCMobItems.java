package cn.lambdacraft.mob.register;

import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.mob.entity.EntityAlienSlave;
import cn.lambdacraft.mob.entity.EntityBigMomoa;
import cn.lambdacraft.mob.entity.EntityHLZombie;
import cn.lambdacraft.mob.entity.EntityHeadcrab;
import cn.lambdacraft.mob.entity.EntityHoundeye;
import cn.lambdacraft.mob.entity.EntityMobBull;
import cn.lambdacraft.mob.entity.EntitySentry;
import cn.lambdacraft.mob.entity.EntitySnark;
import cn.lambdacraft.mob.item.ItemBarnaclePlacer;
import cn.lambdacraft.mob.item.ItemDNAFragment;
import cn.lambdacraft.mob.item.ItemSentrySyncer;
import cn.liutils.api.item.DispenserBehaviorSpawner;
import cn.liutils.api.item.LIMobSpawner;
import cpw.mods.fml.common.registry.GameRegistry;

public class CBCMobItems {

	public static Item 
		weapon_snark,
		headcrab0w0,
		barnacle,
		zombie,
		turret,
		sentrySyncer,
		houndeye,
		vortigaunt,
		gonarch,
		mob_bull;
	
	public static ItemDNAFragment dna;

	public static void init(Configuration conf) {
		weapon_snark = new LIMobSpawner(EntitySnark.class).setCreativeTab(CBCMod.cct)
				.setUnlocalizedName("snark").setTextureName("lambdacraft:snark");
		headcrab0w0 = new LIMobSpawner(EntityHeadcrab.class).setCreativeTab(CBCMod.cct)
			.setTextureName("lambdacraft:egg5").setUnlocalizedName("headcrab");
		barnacle = new ItemBarnaclePlacer().setCreativeTab(CBCMod.cct)
			.setUnlocalizedName("barnacle").setTextureName("lambdacraft:egg0");
		zombie = new LIMobSpawner(EntityHLZombie.class).setCreativeTab(CBCMod.cct)
			.setUnlocalizedName("hlzombie").setTextureName("lambdacraft:egg3");
		turret = new LIMobSpawner(EntitySentry.class).setCreativeTab(CBCMod.cct)
			.setTextureName("lambdacraft:turret").setUnlocalizedName("lcturret");
		houndeye = new LIMobSpawner(EntityHoundeye.class)
			.setCreativeTab(CBCMod.cct).setTextureName("lambdacraft:egg1").setUnlocalizedName("houndeye");
		vortigaunt = new LIMobSpawner(EntityAlienSlave.class).setCreativeTab(CBCMod.cct)
				.setTextureName("lambdacraft:egg4").setUnlocalizedName("vortigaunt");
		sentrySyncer = new ItemSentrySyncer();
		dna = new ItemDNAFragment();
		//gonarch = new LIMobSpawner(EntityBigMomoa.class).setCreativeTab(CBCMod.cct).setCreativeTab(CBCMod.cct).setTextureName("lambdacraft:egg6");
		//mob_bull = new LIMobSpawner(EntityMobBull.class).setCreativeTab(CBCMod.cct).setCreativeTab(CBCMod.cct).setTextureName("lambdacraft:egg7");
		
		GameRegistry.registerItem(weapon_snark, "lc_snark");
		GameRegistry.registerItem(headcrab0w0, "lc_headcrab");
		GameRegistry.registerItem(barnacle, "lc_barnacle");
		GameRegistry.registerItem(zombie, "lc_zombie");
		GameRegistry.registerItem(turret, "lc_turret");
		GameRegistry.registerItem(houndeye, "lc_houndeye");
		GameRegistry.registerItem(vortigaunt, "lc_vortigaunt");
		GameRegistry.registerItem(sentrySyncer, "lc_sentry");
		GameRegistry.registerItem(dna, "lc_dna");
		//GameRegistry.registerItem(gonarch, "lc_gonarch");
		//GameRegistry.registerItem(mob_bull, "lc_mob_bull");

		BlockDispenser.dispenseBehaviorRegistry.putObject(weapon_snark, DispenserBehaviorSpawner.INSTANCE);
		BlockDispenser.dispenseBehaviorRegistry.putObject(headcrab0w0, DispenserBehaviorSpawner.INSTANCE);
		BlockDispenser.dispenseBehaviorRegistry.putObject(zombie, DispenserBehaviorSpawner.INSTANCE);
		BlockDispenser.dispenseBehaviorRegistry.putObject(turret, DispenserBehaviorSpawner.INSTANCE);
		BlockDispenser.dispenseBehaviorRegistry.putObject(vortigaunt, DispenserBehaviorSpawner.INSTANCE);
		BlockDispenser.dispenseBehaviorRegistry.putObject(houndeye, DispenserBehaviorSpawner.INSTANCE);
	}

}
