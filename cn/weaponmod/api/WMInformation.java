/** 
 * Copyright (c) Lambda Innovation Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.cn/
 * 
 * The mod is open-source. It is distributed under the terms of the
 * Lambda Innovation Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * 本Mod是完全开源的，你允许参考、使用、引用其中的任何代码段，但不允许将其用于商业用途，在引用的时候，必须注明原作者。
 */
package cn.weaponmod.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cn.weaponmod.api.information.InformationWeapon;

/**
 * 感动吧，它终于把Client和Server漂亮的分开了！
 * @author WeAthFolD
 *
 */
public class WMInformation {

	private static HashMap<Integer, InformationWeapon> infPool_client = new HashMap<Integer, InformationWeapon>();
	private static HashMap<Integer, InformationWeapon> infPool_server = new HashMap<Integer, InformationWeapon>();
	private static final Random RNG = new Random();
	
	public static void register(ItemStack item, InformationWeapon information, World world) {
		register(item, information, world.isRemote);
	}
	
	public static void register(ItemStack item, InformationWeapon information, boolean isClient) {
		Map<Integer, InformationWeapon> map = isClient ? infPool_client : infPool_server;
		Integer id = getUniqueID(item);
		if(id == 0) id = RNG.nextInt();
		map.put(id, information);
		setUniqueID(item, id);
	}
	
	public static InformationWeapon getInformation(ItemStack item, World world) {
		return getInformation(item, world.isRemote);
	}
	
	public static InformationWeapon getInformation(ItemStack item, boolean isClient) {
		Map<Integer, InformationWeapon> map = isClient ? infPool_client : infPool_server;
		Integer id = getUniqueID(item);
		if(id != 0) 
			return map.get(id);
		return null;
	}
	
	private static int getUniqueID(ItemStack is) {
		NBTTagCompound nbt = is.stackTagCompound;
		if(nbt == null)
			return 0;
		else return nbt.getInteger("uniqueID");
	}
	
	private static void setUniqueID(ItemStack is, int id) {
		if(is.stackTagCompound == null) 
			is.stackTagCompound = new NBTTagCompound();
		NBTTagCompound nbt = is.stackTagCompound;
		nbt.setInteger("uniqueID", id);
	}

}
