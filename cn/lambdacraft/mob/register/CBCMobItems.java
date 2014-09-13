package cn.lambdacraft.mob.register;

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
		headcrab0w0 = new LCMobSpawner(EntityHeadcrab.class, "headcrab")
				.setIconName("egg5");
		barnacle = new ItemBarnaclePlacer().setTextureName("egg0");
		zombie = new LCMobSpawner(EntityHLZombie.class, "hlzombie").setIconName("egg3");
		turret = new LCMobSpawner(EntitySentry.class, "turret");
		houndeye = new LCMobSpawner(EntityHoundeye.class, "houndeye").setIconName("egg1");
		vortigaunt = new LCMobSpawner(EntityAlienSlave.class, "vortigaunt")
				.setIconName("egg4");
		sentrySyncer = new ItemSentrySyncer();
		dna = new ItemDNAFragment();

		dispenserBehaviorSpawner = new DispenserBehaviorSpawner();
		BlockDispenser.dispenseBehaviorRegistry.putObject(weapon_snark, dispenserBehaviorSpawner);
		BlockDispenser.dispenseBehaviorRegistry.putObject(headcrab0w0, dispenserBehaviorSpawner);
		BlockDispenser.dispenseBehaviorRegistry.putObject(zombie, dispenserBehaviorSpawner);
		BlockDispenser.dispenseBehaviorRegistry.putObject(turret, dispenserBehaviorSpawner);
		BlockDispenser.dispenseBehaviorRegistry.putObject(vortigaunt, dispenserBehaviorSpawner);
		BlockDispenser.dispenseBehaviorRegistry.putObject(houndeye, dispenserBehaviorSpawner);
	}

}
