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
package cn.lambdacraft.core.proxy;

import java.util.HashSet;
import java.util.Set;

import cn.lambdacraft.core.CBCMod;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * 加载代理。
 * 
 * @author WeAthFOlD, Mkpoli, HopeAsd
 * 
 */
public class Proxy {

	public static Set<String> languages = new HashSet();
	
	static {
		languages.add("zh_CN");
		languages.add("en_US");
		languages.add("zh_TW");
		languages.add("ja_JP");
	}

	public void profilerStartSection(String section) {

	}

	public void profilerEndSection() {

	}

	public void profilerEndStartSection(String section) {

	}

	public static boolean isRendering() {
		return !isSimulating();
	}

	public static boolean isSimulating() {
		return !FMLCommonHandler.instance().getEffectiveSide().isClient();
	}

	public void init() {
		for (String lang : languages) {
			LanguageRegistry.instance().loadLocalization(
					"/assets/lambdacraft/lang/" + lang + ".lang", lang,
					false);
		}
	}

	public static void logExceptionMessage(TileEntity te, String... addition) {
		StringBuilder sb = new StringBuilder(
				"LambdaCraft has caught a exception during runtime. \n");
		sb.append("TileEntity : ").append(te == null ? "null" : te.toString())
				.append("\n");
		sb.append("Additional Information : \n");
		for (String s : addition) {
			sb.append(s).append("\n");
		}
		CBCMod.log.severe(sb.toString());
	}

	public static void logExceptionMessage(Entity entity, String... addition) {
		StringBuilder sb = new StringBuilder(
				"LambdaCraft has caught a exception during runtime. \n");
		sb.append("Entity : ")
				.append(entity == null ? "null" : entity.toString())
				.append("\n");
		sb.append("Additional Information : \n");
		for (String s : addition) {
			sb.append(s).append("\n");
		}
		CBCMod.log.severe(sb.toString());
	}

	public void displayError(String error) {
		throw new RuntimeException(
				("LambdaCraft Error\n\n=== LambdaCraft Error ===\n\n" + error + "\n\n===============================\n")
						.replace("\n", System.getProperty("line.separator")));
	}

	public void preInit() {
	}

}
