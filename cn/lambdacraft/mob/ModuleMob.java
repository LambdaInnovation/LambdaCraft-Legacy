package cn.lambdacraft.mob;

import java.util.HashMap;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.biome.BiomeGenBase;
import cn.lambdacraft.core.LCMod;
import cn.lambdacraft.core.proxy.LCGeneralProps;
import cn.lambdacraft.mob.block.tile.TileSentryRay;
import cn.lambdacraft.mob.entity.EntityAlienSlave;
import cn.lambdacraft.mob.entity.EntityBarnacle;
import cn.lambdacraft.mob.entity.EntityHLZombie;
import cn.lambdacraft.mob.entity.EntityHeadcrab;
import cn.lambdacraft.mob.entity.EntityHoundeye;
import cn.lambdacraft.mob.entity.EntitySentry;
import cn.lambdacraft.mob.entity.EntityShockwave;
import cn.lambdacraft.mob.entity.EntitySnark;
import cn.lambdacraft.mob.network.MessageSentry;
import cn.lambdacraft.mob.register.CBCMobBlocks;
import cn.lambdacraft.mob.register.CBCMobItems;
import cn.liutils.api.util.BlockPos;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "LambdaCraft|Living", name = "LambdaCraft Living", version = LCMod.VERSION, dependencies = LCMod.DEPENDENCY_CORE)
public class ModuleMob {

	/**
	 * 放置时临时采用的映射表。
	 */
	public static HashMap<EntityPlayer, TileSentryRay> tileMap = new HashMap();
	public static HashMap<BlockPos, EntityPlayer> placeMap = new HashMap();
	

	@SidedProxy(clientSide = "cn.lambdacraft.mob.proxy.ClientProxy", serverSide = "cn.lambdacraft.mob.proxy.Proxy")
	public static cn.lambdacraft.mob.proxy.Proxy proxy;

	@Instance("LambdaCraft|Living")
	public static ModuleMob instance;

	@EventHandler()
	public void preInit(FMLPreInitializationEvent Init) {
		proxy.preInit();
	}

	@EventHandler()
	public void init(FMLInitializationEvent Init) {
		CBCMobItems.init(LCMod.config);
		CBCMobBlocks.init(LCMod.config);

		try {
			boolean spawnOverworld = LCMod.config.get("general", "spawnInOverworld", true).getBoolean(true);
			if(spawnOverworld){
				EntityRegistry.addSpawn(EntityHeadcrab.class, 7, 0, 50, EnumCreatureType.monster, GENERIC_GEN);
				EntityRegistry.addSpawn(EntityHoundeye.class, 10, 0, 70, EnumCreatureType.monster, GENERIC_GEN);
				EntityRegistry.addSpawn(EntityAlienSlave.class, 1, 0, 70, EnumCreatureType.monster, GENERIC_GEN);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//EntityRegistry.addSpawn(EntityBarnacle.class, 5, 0, 65, EnumCreatureType.monster, BARNACLE_GEN);

		LCMod.netHandler.registerMessage(MessageSentry.Handler.class, MessageSentry.class, LCMod.getUniqueNetChannel(), Side.CLIENT);
		
		EntityRegistry.registerModEntity(EntitySnark.class, "snark",
				LCGeneralProps.ENT_ID_SNARK, LCMod.instance, 48, 3, true);
		EntityRegistry.registerModEntity(EntityHeadcrab.class, "headcrab",
				LCGeneralProps.ENT_ID_HEADCRAB, LCMod.instance, 48, 3, true);
		EntityRegistry.registerModEntity(EntityBarnacle.class, "barnacle",
				LCGeneralProps.ENT_ID_BARNACLE, LCMod.instance, 84, 5, false);
		EntityRegistry.registerModEntity(EntityHLZombie.class, "hlzombie",
				LCGeneralProps.ENT_ID_ZOMBIE, LCMod.instance, 48, 3, true);
		EntityRegistry.registerModEntity(EntitySentry.class, "turret",
				LCGeneralProps.ENT_ID_TURRET, LCMod.instance, 48, 3, true);
		EntityRegistry.registerModEntity(EntityHoundeye.class, "houndeye",
				LCGeneralProps.ENT_ID_HOUNDEYE, LCMod.instance, 48, 3, true);
		EntityRegistry.registerModEntity(EntityShockwave.class, "shockwave",
				LCGeneralProps.ENT_ID_SHOCKWAVE, LCMod.instance, 32, 3, false);
		EntityRegistry.registerModEntity(EntityAlienSlave.class, "vortigaunt",
				LCGeneralProps.ENT_ID_VORTIGAUNT, LCMod.instance, 48, 3, true);
		proxy.init();
	}

	private static final BiomeGenBase GENERIC_GEN[] = { BiomeGenBase.beach,
			BiomeGenBase.desert, BiomeGenBase.desertHills,
			BiomeGenBase.extremeHills, BiomeGenBase.forest,
			BiomeGenBase.forestHills, BiomeGenBase.frozenOcean,
			BiomeGenBase.frozenRiver, BiomeGenBase.jungle,
			BiomeGenBase.swampland, BiomeGenBase.plains }, BARNACLE_GEN[] = {
			BiomeGenBase.beach, BiomeGenBase.desert, BiomeGenBase.desertHills,
			BiomeGenBase.extremeHills, BiomeGenBase.forest,
			BiomeGenBase.forestHills, BiomeGenBase.frozenOcean,
			BiomeGenBase.frozenRiver, BiomeGenBase.jungle,
			BiomeGenBase.swampland, BiomeGenBase.plains, BiomeGenBase.hell };

	@EventHandler()
	public void postInit(FMLPostInitializationEvent Init) {
	}

	@EventHandler()
	public void serverStarting(FMLServerStartingEvent event) {
	}

}