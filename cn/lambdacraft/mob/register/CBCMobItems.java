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
import cn.lambdacraft.mob.item.DispenserBehaviorSpawner;
import cn.lambdacraft.mob.item.ItemBarnaclePlacer;
import cn.lambdacraft.mob.item.ItemDNAFragment;
import cn.lambdacraft.mob.item.ItemSentrySyncer;
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
	public static DispenserBehaviorSpawner dispenserBehaviorSpawner;

	public static void init(Configuration conf) {
		weapon_snark = new LIMobSpawner();
		headcrab0w0 = new LIMobSpawner(EntityHeadcrab.class).setTextureName("lambdacraft:egg5");
		barnacle = new ItemBarnaclePlacer().setTextureName("egg0");
		zombie = new LIMobSpawner(EntityHLZombie.class).setTextureName("lambdacraft:egg3");
		turret = new LIMobSpawner(EntitySentry.class);
		houndeye = new LIMobSpawner(EntityHoundeye.class).setTextureName("lambdacraft:egg1");
		vortigaunt = new LIMobSpawner(EntityAlienSlave.class).setTextureName("lambdacraft:egg4");
		sentrySyncer = new ItemSentrySyncer();
		dna = new ItemDNAFragment();
		gonarch = new LIMobSpawner(EntityBigMomoa.class).setCreativeTab(CBCMod.cct).setTextureName("lambdacraft:egg6");
		mob_bull = new LIMobSpawner(EntityMobBull.class).setCreativeTab(CBCMod.cct).setTextureName("lambdacraft:egg7");
		
		GameRegistry.registerItem(weapon_snark, "lc_snark");
		GameRegistry.registerItem(headcrab0w0, "lc_headcrab");
		GameRegistry.registerItem(barnacle, "lc_barnacle");
		GameRegistry.registerItem(zombie, "lc_zombie");
		GameRegistry.registerItem(turret, "lc_turret");
		GameRegistry.registerItem(houndeye, "lc_houndeye");
		GameRegistry.registerItem(vortigaunt, "lc_vortigaunt");
		GameRegistry.registerItem(sentrySyncer, "lc_sentry");
		GameRegistry.registerItem(dna, "lc_dna");
		GameRegistry.registerItem(gonarch, "lc_gonarch");
		GameRegistry.registerItem(mob_bull, "lc_mob_bull");

		dispenserBehaviorSpawner = new DispenserBehaviorSpawner();
		BlockDispenser.dispenseBehaviorRegistry.putObject(weapon_snark, dispenserBehaviorSpawner);
		BlockDispenser.dispenseBehaviorRegistry.putObject(headcrab0w0, dispenserBehaviorSpawner);
		BlockDispenser.dispenseBehaviorRegistry.putObject(zombie, dispenserBehaviorSpawner);
		BlockDispenser.dispenseBehaviorRegistry.putObject(turret, dispenserBehaviorSpawner);
		BlockDispenser.dispenseBehaviorRegistry.putObject(vortigaunt, dispenserBehaviorSpawner);
		BlockDispenser.dispenseBehaviorRegistry.putObject(houndeye, dispenserBehaviorSpawner);
	}

}
