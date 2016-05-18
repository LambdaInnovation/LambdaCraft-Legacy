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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.item.CBCGenericItem;
import cn.lambdacraft.mob.ModuleMob;
import cn.lambdacraft.mob.register.CBCMobBlocks;
import cn.liutils.api.util.BlockPos;

/**
 * @author WeAthFolD
 *
 */
public class ItemSentrySyncer extends CBCGenericItem {

    /**
     * @param par1
     */
    public ItemSentrySyncer() {
        super();
        this.setCreativeTab(CBCMod.cct);
        this.setIAndU("syncer");
    }
    
    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
            int par6, int par7, float par8, float par9, float par10)
    {
        if(!par3World.isRemote) {
            --par1ItemStack.stackSize;
            ForgeDirection dir = ForgeDirection.values()[par7];
            par4 += dir.offsetX;
            par5 += dir.offsetY;
            par6 += dir.offsetZ;
            ModuleMob.placeMap.put(new BlockPos(par4, par5, par6, CBCMobBlocks.sentryRay), par2EntityPlayer);
            par3World.setBlock(par4, par5, par6, CBCMobBlocks.sentryRay, par7, 0x03);
        }
        return false;
    }

}
