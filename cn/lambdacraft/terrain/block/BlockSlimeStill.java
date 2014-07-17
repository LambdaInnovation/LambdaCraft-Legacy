package cn.lambdacraft.terrain.block;

import cn.lambdacraft.core.LCMod;
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
import net.minecraft.world.World;

public class BlockSlimeStill extends BlockStaticLiquid{
	
	public BlockSlimeStill() 
	{
		super(Material.water);
        this.setCreativeTab(LCMod.cct);
        this.setHardness(100.0F);
        this.setLightOpacity(3);
        this.setBlockName("slimestill");
        this.setBlockTextureName("lambdacraft:slime_still");
        this.disableStats();
        this.setCreativeTab(LCMod.cct);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(entity instanceof EntityLiving){
			EntityLiving player=(EntityLiving)entity;
			player.addPotionEffect(new PotionEffect(Potion.regeneration.id,30,0));
		}
	}
}
