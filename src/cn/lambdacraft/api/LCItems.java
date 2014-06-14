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
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.api;

import java.util.logging.Level;

import cn.lambdacraft.core.CBCMod;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;

/**
 * 
 * WeAthFolD: 谁来帮我填下这个坑吧……
 * 
 * mkpoli: 这坑填不起(·ω<)
 * 
 * 好吧我填了一点 喵呜~
 * 
 * 其实我已经填完了233
 * 
 * @author WeAthFold mkpoli
 */
public class LCItems {

	public static ItemStack getCBCItem(String var0) {
		if (ModLoader.isModLoaded("LambdaCraft")) {
			try {
				Object var2 = Class.forName("cn.lambdacraft.crafting.register.CBCItems").getField(var0)
						.get((Object) null);
				return var2 instanceof ItemStack ? (ItemStack) var2 : null;
			} catch (Exception var3) {
				CBCMod.log.log(Level.ALL, "Exception occurred while getting mod's item, info:" + var3);
				return null;
			}
		}
		return null;
	}
	
	public static ItemStack getDMItem(String var0) {
		if (ModLoader.isModLoaded("LambdaCraft")) {
			try {
				Object var2 = Class.forName("cn.lambdacraft.deathmatch.register.DMItems").getField(var0)
						.get((Object) null);
				return var2 instanceof ItemStack ? (ItemStack) var2 : null;
			} catch (Exception var3) {
				CBCMod.log.log(Level.ALL, "Exception occurred while getting mod's item, info:" + var3);
				return null;
			}
		}
		return null;
	}
	
	public static ItemStack getMobItem(String var0) {
		if (ModLoader.isModLoaded("LambdaCraft")) {
			try {
				Object var2 = Class.forName("cn.lambdacraft.mob.register.CBCMobItems").getField(var0)
						.get((Object) null);
				return var2 instanceof ItemStack ? (ItemStack) var2 : null;
			} catch (Exception var3) {
				CBCMod.log.log(Level.ALL, "Exception occurred while getting mod's item, info:" + var3);
				return null;
			}
		}
		return null;
	}
}
