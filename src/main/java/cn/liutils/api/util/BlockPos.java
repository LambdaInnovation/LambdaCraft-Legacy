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
package cn.liutils.api.util;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

/**
 * Utility class providing position and the block instance.
 * @author WeAthFolD
 */
public class BlockPos {

    public int x, y, z;
    public Block block;
    
    public BlockPos(int par1, int par2, int par3) {
        x = par1;
        y = par2;
        z = par3;
    }

    public BlockPos(int par1, int par2, int par3, Block bID) {
        this(par1, par2, par3);
        block = bID;
    }
    
    public BlockPos(TileEntity te) {
        this(te.xCoord, te.yCoord, te.zCoord, te.blockType);
    }

    /**
     * Judegs only position.
     */
    @Override
    public boolean equals(Object b) {
        if (b == null || !(b instanceof BlockPos))
            return false;
        BlockPos bp = (BlockPos) b;
        return (x == bp.x && y == bp.y && z == bp.z);
    }
    
    @Override
    public int hashCode() {
        return x << 8 + y << 4 + z;
    }

    public BlockPos copy() {
        return new BlockPos(x, y, z, block);
    }

}
