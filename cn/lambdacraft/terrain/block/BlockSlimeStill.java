package cn.lambdacraft.terrain.block;

import cn.lambdacraft.core.CBCMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockSlimeStill extends BlockStaticLiquid{
	
	public BlockSlimeStill() 
	{
		super(Material.water);
        this.setCreativeTab(CBCMod.cct);
        this.setHardness(100.0F);
        this.setLightOpacity(3);
        this.setBlockName("slimestill");
        this.setBlockTextureName("slime_still");
        this.disableStats();
        this.setCreativeTab(CBCMod.cct);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(entity instanceof EntityLiving){
			EntityLiving player=(EntityLiving)entity;
			player.addPotionEffect(new PotionEffect(Potion.regeneration.id,30,0));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1)
	{
	    this.blockIcon = par1.registerIcon("lambdacraft:" + this.getTextureName());
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return this.blockIcon;
    }
}
