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

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.deathmatch.item.ArmorHEV.EnumAttachment;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

/**
 * @author WeAthFolD
 */
public class ItemAttachment extends Item {

	EnumAttachment[] attaches = EnumAttachment.values();
	Icon[] icons = new Icon[attaches.length];
	
	/**
	 * @param par1
	 */
	public ItemAttachment() {
		super();
		setUnlocalizedName("attaches");
		setCreativeTab(CBCMod.cctMisc);
		this.hasSubtypes = true;
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir)
    {
        for(int i = 0; i < icons.length; i++) {
        	icons[i] = ir.registerIcon("lambdacraft:attach" + i);
        }
    }
	
    @Override
	@SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value
     */
    public Icon getIconFromDamage(int par1)
    {
        return icons[par1];
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
    	int dmg = par1ItemStack.getItemDamage();
    	list.add(StatCollector.translateToLocal(attaches[dmg].toString()));
    }
    
    @Override
	@SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
    {
    	for(int i = 0; i < icons.length; i ++) {
    		list.add(new ItemStack(par1, 1, i));
    	}
    }


}
