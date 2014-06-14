package cn.lambdacraft.xen.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockStationary;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockSlimeStill extends BlockStationary{
	static int bucketRadioactiveID=0,bucketRadioactiveDamage=0;
	
	public BlockSlimeStill(int id) {
        super(id, Material.water);
        setCreativeTab(CreativeTabs.tabRedstone);
        blockHardness = 100.0F;
        setLightOpacity(3);
        disableStats();
        this.setUnlocalizedName("slimestill");
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        theIcon = new Icon[] {
                iconRegister.registerIcon("lambdacraft:slime_still"),
                iconRegister.registerIcon("lambdacraft:slime_flow") };
    }
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(entity instanceof EntityLiving){
			EntityLiving player=(EntityLiving)entity;
			player.addPotionEffect(new PotionEffect(Potion.regeneration.id,30,0));
		}
	}
}
