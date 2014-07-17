package cn.lambdacraft.mob.register;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import cn.lambdacraft.mob.entity.EntityAlienSlave;
import cn.lambdacraft.mob.entity.EntityHLZombie;
import cn.lambdacraft.mob.entity.EntityHeadcrab;
import cn.lambdacraft.mob.entity.EntityHoundeye;
import cn.lambdacraft.mob.entity.EntitySentry;
import cn.lambdacraft.mob.item.DispenserBehaviorSpawner;
import cn.lambdacraft.mob.item.ItemBarnaclePlacer;
import cn.lambdacraft.mob.item.ItemDNAFragment;
import cn.lambdacraft.mob.item.ItemSentrySyncer;
import cn.lambdacraft.mob.item.LCMobSpawner;

public class CBCMobItems {

	public static Item weapon_snark, headcrab0w0, barnacle, zombie, turret,
			sentrySyncer, houndeye, vortigaunt;
	public static ItemDNAFragment dna;
	public static DispenserBehaviorSpawner dispenserBehaviorSpawner;

	public static void init(Configuration conf) {
		weapon_snark = new LCMobSpawner();
		headcrab0w0 = new LCMobSpawner(EntityHeadcrab.class, "headcrab").setTextureName("lambdacraft:egg5");
		barnacle = new ItemBarnaclePlacer();
		zombie = new LCMobSpawner(EntityHLZombie.class, "hlzombie").setTextureName("lambdacraft:egg3");
		turret = new LCMobSpawner(EntitySentry.class, "turret");
		houndeye = new LCMobSpawner(EntityHoundeye.class, "houndeye").setTextureName("lambdacraft:egg1");
		vortigaunt = new LCMobSpawner(EntityAlienSlave.class, "vortigaunt").setTextureName("lambdacraft:egg4");
		sentrySyncer = new ItemSentrySyncer();
		dna = new ItemDNAFragment();
		
		GameRegistry.registerItem(weapon_snark, "weapon_snark");
		GameRegistry.registerItem(headcrab0w0, "headcrab0w0");
		GameRegistry.registerItem(barnacle, "barnacle");
		GameRegistry.registerItem(zombie, "lc_zombie");
		GameRegistry.registerItem(turret, "lc_turret");
		GameRegistry.registerItem(houndeye, "houndeye");
		GameRegistry.registerItem(vortigaunt, "vortigaunt");
		GameRegistry.registerItem(sentrySyncer, "sentrySyncer");
		GameRegistry.registerItem(dna, "lc_dna");

		dispenserBehaviorSpawner = new DispenserBehaviorSpawner();
		BlockDispenser.dispenseBehaviorRegistry.putObject(weapon_snark, dispenserBehaviorSpawner);
		BlockDispenser.dispenseBehaviorRegistry.putObject(headcrab0w0, dispenserBehaviorSpawner);
		BlockDispenser.dispenseBehaviorRegistry.putObject(zombie, dispenserBehaviorSpawner);
		BlockDispenser.dispenseBehaviorRegistry.putObject(turret, dispenserBehaviorSpawner);
		BlockDispenser.dispenseBehaviorRegistry.putObject(vortigaunt, dispenserBehaviorSpawner);
		BlockDispenser.dispenseBehaviorRegistry.putObject(houndeye, dispenserBehaviorSpawner);
	}

}
