package cn.lambdacraft.mob.register;

import net.minecraft.item.Item;
import cn.lambdacraft.mob.entity.EntityAlienSlave;
import cn.lambdacraft.mob.entity.EntityHLZombie;
import cn.lambdacraft.mob.entity.EntityHeadcrab;
import cn.lambdacraft.mob.entity.EntityHoundeye;
import cn.lambdacraft.mob.entity.EntitySentry;
import cn.lambdacraft.mob.item.ItemBarnaclePlacer;
import cn.lambdacraft.mob.item.ItemDNAFragment;
import cn.lambdacraft.mob.item.ItemSentrySyncer;
import cn.lambdacraft.mob.item.LCMobSpawner;
import cn.liutils.core.register.Config;
import cn.liutils.core.register.ConfigHandler;

public class CBCMobItems {

	public static Item weapon_snark, headcrab0w0, barnacle, zombie, turret,
			sentrySyncer, houndeye, vortigaunt;
	public static ItemDNAFragment dna;

	public static void init(Config conf) {
		weapon_snark = new LCMobSpawner(ConfigHandler.getItemId(conf, "snark",
				1));
		headcrab0w0 = new LCMobSpawner(ConfigHandler.getItemId(conf,
				"headcrab", 1), EntityHeadcrab.class, "headcrab")
				.setIconName("egg5");
		barnacle = new ItemBarnaclePlacer(ConfigHandler.getItemId(conf,
				"barnacle", 1)).setIconName("egg0");
		zombie = new LCMobSpawner(ConfigHandler.getItemId(conf, "zombie", 1),
				EntityHLZombie.class, "hlzombie").setIconName("egg3");
		turret = new LCMobSpawner(ConfigHandler.getItemId(conf, "turret", 1),
				EntitySentry.class, "turret");
		houndeye = new LCMobSpawner(
				ConfigHandler.getItemId(conf, "houndeye", 1),
				EntityHoundeye.class, "houndeye").setIconName("egg1");
		vortigaunt = new LCMobSpawner(ConfigHandler.getItemId(conf,
				"vortigaunt", 1), EntityAlienSlave.class, "vortigaunt")
				.setIconName("egg4");
		sentrySyncer = new ItemSentrySyncer(ConfigHandler.getItemId(conf,
				"syncer", 1));
		dna = new ItemDNAFragment(ConfigHandler.getItemId(conf, "dna", 1));

	}

}
