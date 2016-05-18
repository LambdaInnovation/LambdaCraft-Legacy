package cn.lambdacraft.terrain.block;

import cn.lambdacraft.core.CBCMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class XenBlock extends Block{

    protected XenBlock(Material material) {
        super(material);
        this.setCreativeTab(CBCMod.cct);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1)
    {
        this.blockIcon = par1.registerIcon("lambdacraft:" + this.getTextureName());
    }

}
