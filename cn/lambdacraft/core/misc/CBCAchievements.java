/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.core.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.config.Configuration;
import cn.lambdacraft.crafting.register.CBCBlocks;
import cn.lambdacraft.crafting.register.CBCItems;

/**
 * Mod的成就类。
 * 
 * @author Mkpoli
 * 
 */
public class CBCAchievements {

	public static Achievement[] oreAchievements = new Achievement[3];

	/* Radioactive Beryl */
	public static Achievement radioactiveBeryl;

	/* Oh my teeth! */
	public static Achievement ohMyTeeth;

	/* Let's Moe! */
	public static Achievement letsMoe;

	/* The Page of Achs */
	public static AchievementPage achpage;

	public static void init(Configuration conf) {
		
		try {

			oreAchievements[0] = (new Achievement(conf.get("achievements",
					"nuclearRawMaterial", "uranium").getString(), "nuclearRawMaterial", 0, 0,
					CBCBlocks.uraniumOre, (Achievement) null))
					.registerStat();
			oreAchievements[1] = (new Achievement(conf.get("achievements", "newTinOre",
					"tin").getString(), "newTinOre", 2, 0, CBCBlocks.oreTin,
					(Achievement) null)).registerStat();
			oreAchievements[2] = (new Achievement(conf.get("achievements",
					"newCopperOre", "copper").getString(), "newCopperOre", 4, 0,
					CBCBlocks.oreCopper, (Achievement) null))
					.registerStat();
			radioactiveBeryl = (new Achievement(conf.get("achievements", 
					"radioactiveBeryl", "radioactive").getString(), "radioactiveBeryl", 1, 2,
					CBCItems.ingotUranium, oreAchievements[0]))
					.registerStat();
			ohMyTeeth = (new Achievement(conf.get("achievements", "ohMyTeeth", "steel").getString(),
					"ohMyTeeth", 3, 0, CBCItems.ingotSteel, (Achievement) null))
					.registerStat();
			letsMoe = (new Achievement(conf.get("achievements", "letsMoe", "moe").getString(),
					"letsMoe", 12, 12, CBCItems.halfLife01, (Achievement) null))
					.registerStat();
//			System.out.println("finish achievements");
			achpage = new AchievementPage("LambdaCraft", oreAchievements[0],

			oreAchievements[1], oreAchievements[2], radioactiveBeryl,
					ohMyTeeth, letsMoe);

			AchievementPage.registerAchievementPage(achpage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CBCAchievements() {

	}

	public static void getAchievement(EntityPlayer player, Achievement ach) {
		player.addStat(ach, 1);
	}
}