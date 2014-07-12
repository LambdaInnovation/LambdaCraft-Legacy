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
package cn.lambdacraft.mob.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 *
 */
public class ItemDNAFragment extends Item {

	IIcon[] icons = new IIcon[6];
	public static final String[] descriptions = new String[] {
		"headcrab",
		"barnacle",
		"houndeye",
		"snark",
		"vortigaunt",
		"tentacle"
	};	
	
	/**
	 * @param par1
	 */
	public ItemDNAFragment() {
		super();
		setUnlocalizedName("dna");
		setCreativeTab(CreativeTabs.tabMisc);
		this.hasSubtypes = true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister ir) {
		for(int i = 0; i < 6; i++) {
			icons[i] = ir.registerIcon("lambdacraft:dna" + i);
		}
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
    {
    	for(int i = 0; i < 6; i ++)
    		list.add(new ItemStack(par1, 1, i));
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(StatCollector.translateToLocal("tissue." + descriptions[par1ItemStack.getItemDamage()] + ".name"));
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    /**
     * Gets an icon index based on an item's damage value
     */
    public IIcon getIconFromDamage(int i)
    {
    	return icons[i];
    }

}
