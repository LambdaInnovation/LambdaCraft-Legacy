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
package cn.lambdacraft.deathmatch.item;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.item.CBCGenericItem;
import cn.lambdacraft.deathmatch.block.TileHealthCharger;
import cn.lambdacraft.deathmatch.entity.EntityMedkit;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

/**
 * @author mkpoli
 */
public class ItemMedkit extends CBCGenericItem {

	public static final int MAX_STORE = 3;

	public enum EnumAddingType {
		EFFECT, DURATION, NONE;
	}

	public ItemMedkit() {
		super();
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("medkit");
		this.setIconName("medkit");
		this.setMaxStackSize(1);
		this.setMaxDamage(25); // 最多使用25次
	}

	public static boolean isMedkitFull(ItemStack medkit) {
		boolean b = false;
		for (int i = 0; i < MAX_STORE; i++) {
			b = b || isSlotAvailable(medkit, i);
		}
		return !b;
	}

	public static int getAvailableSlot(ItemStack medkit) {
		for (int i = 0; i < MAX_STORE; i++) {
			if (isSlotAvailable(medkit, i))
				return i;
		}
		return -1;
	}
	
	private static Set<Integer> allowedPotions = new HashSet<Integer>();
	static {
		allowedPotions.add(Potion.damageBoost.id);
		allowedPotions.add(Potion.digSpeed.id);
		allowedPotions.add(Potion.fireResistance.id);
		allowedPotions.add(Potion.heal.id);
		allowedPotions.add(Potion.invisibility.id);
		allowedPotions.add(Potion.jump.id);
		allowedPotions.add(Potion.moveSpeed.id);
		allowedPotions.add(Potion.nightVision.id);
		allowedPotions.add(Potion.regeneration.id);
		allowedPotions.add(Potion.resistance.id);
		allowedPotions.add(Potion.waterBreathing.id);
	}

	/**
	 * Try adding all the effects of a specific potion item into the medkit.
	 * @param medkit
	 * @param potion
	 * @param type
	 * @return How many effect(s) have(s) been added
	 */
	public static int tryAddEffectTo(ItemStack medkit, ItemStack potion,
			EnumAddingType type) {
		List<PotionEffect> list = 
				Items.potionitem.getEffects(potion.getItemDamage());
		NBTTagCompound nbt = loadCompound(potion);
		int res = 0;
		try {
			for (PotionEffect e : list) {
				if(!allowedPotions.contains(e.getPotionID()))
					continue;
				for (int i = 0; i < 3; i++) {
					int original = e.getDuration();
					if(type == EnumAddingType.DURATION)
						TileHealthCharger.fldDuration.set(e, (int)((Integer)TileHealthCharger.fldDuration.get(e) * 1.3));
					if (isSlotAvailable(medkit, i)) {
						addEffect(medkit, e, i);
						++res;
						break;
					}
					TileHealthCharger.fldDuration.set(e, original);
				}
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/*
	@Override
	public ItemStack onItemRightClick(ItemStack medkit, World par2World,
			EntityPlayer pl) {

		// DEBUG ONLY : ADD ONE POTION IN PLAYER's INV TO THE MEDKIT
		for (ItemStack is : pl.inventory.mainInventory) {
			if (is == null)
				continue;
			if (isMedkitFull(medkit))
				break;
			if (is.getItem() instanceof ItemPotion) {
				List<PotionEffect> list = Item.potion.getEffects(is
						.getItemDamage());
				for (PotionEffect p : list) {
					tryAddEffectTo(medkit, is, EnumAddingType.NONE);
				}
			}
		}

		return medkit;

	}
	*/

	@Override
	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
			int par6, int par7, float par8, float par9, float par10) {
		if (!par3World.isRemote)
			ItemMedkit.spawnMedkitAt(par1ItemStack, par3World,
					par2EntityPlayer, par4, par5, par6, par7);
		if (!par2EntityPlayer.capabilities.isCreativeMode) {
			par1ItemStack.splitStack(1);
		}
		return true;
	}

	public static void spawnMedkitAt(ItemStack itemStack, World par0World,
			EntityPlayer player, int par1, int par2, int par3, int side) {
		Entity entity = null;
		double x = par1 + 0.5, y = par2 + 0.5, z = par3 + 0.5;
		if (side == 0) {
			return;
		} else if (side == 1) {
			y += 0.8;
		} else if (side == 2) {
			z -= 0.8;
		} else if (side == 3) {
			z += 0.8;
		} else if (side == 4) {
			x -= 0.8;
		} else if (side == 5) {
			x += 0.8;
		}
		entity = new EntityMedkit(par0World, player, x, y, z, itemStack);
		par0World.spawnEntityInWorld(entity);
	}

	private static PotionEffect getEffect(ItemStack item, int slot) {
		NBTTagCompound nbt = loadCompound(item);
		int id = nbt.getInteger("id" + slot);
		if (id == 0)
			return null;
		int duration = nbt.getInteger("duration" + slot);
		int amplifier = nbt.getInteger("amplifier" + slot);

		return new PotionEffect(id, duration, amplifier);
	}

	private static void addEffect(ItemStack medkit, PotionEffect eff, int slot) {
		NBTTagCompound nbt = loadCompound(medkit);
		nbt.setInteger("id" + slot, eff.getPotionID());
		nbt.setInteger("duration" + slot, eff.getDuration());
		nbt.setInteger("amplifier" + slot, eff.getAmplifier());
	}

	private static boolean isSlotAvailable(ItemStack medkit, int slot) {
		return getEffect(medkit, slot) == null;
	}

	public static void clearEffects(ItemStack medkit) {
		NBTTagCompound nbt = loadCompound(medkit);
		for (int i = 0; i < MAX_STORE; i++) {
			nbt.setInteger("id" + i, 0);
			nbt.setInteger("duration" + i, 0);
			nbt.setInteger("amplifier" + i, 0);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		for (int i = 0; i < MAX_STORE; i++) {
			String potionName = getPotionName(par1ItemStack, i);
			if (potionName.equals(""))
				continue;

			par3List.add(StatCollector.translateToLocal(potionName));
		}
	}

	private String getPotionName(ItemStack itemStack, int slot) {
		PotionEffect eff = getEffect(itemStack, slot);
		if (eff == null)
			return "";
		String name = EnumChatFormatting.RED
				+ StatCollector.translateToLocal(eff.getEffectName());
		// name += " " + StatCollector.translateToLocal("potion.potency." +
		// eff.getAmplifier()).trim();
		if (eff.getDuration() > 20) {
			name += EnumChatFormatting.GRAY + " ("
					+ Potion.getDurationString(eff) + ")";
		}
		return name;
	}

	private static NBTTagCompound loadCompound(ItemStack stack) {
		if (stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
		return stack.stackTagCompound;
	}

	public void setPotions(ItemStack stack, ItemStack p1,
			ItemStack p2, ItemStack potion3Stack) {
		if (!(p1.getItem() == Items.potionitem)
				|| !(p1.getItem() == Items.potionitem)
				|| !(p1.getItem() == Items.potionitem))
			return;
		if (p1.getTagCompound().getBoolean("isBadEffect") == true)
			return;

	}
}