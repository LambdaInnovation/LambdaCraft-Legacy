package cn.lambdacraft.terrain.block;

import cn.lambdacraft.core.CBCMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockSlimeFlow extends BlockDynamicLiquid 
{
    
    public BlockSlimeFlow()
    {
        super(Material.water);
        this.setCreativeTab(CBCMod.cct);
        this.setHardness(100.0F);
        this.setLightOpacity(3);
        this.setBlockName("slimeflow");
        this.setBlockTextureName("slime_flow");
        this.disableStats();
        this.setCreativeTab(CBCMod.cct);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1)
    {
        this.blockIcon = par1.registerIcon("lambdacraft:" + this.getTextureName());
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return this.blockIcon;
    }
}
