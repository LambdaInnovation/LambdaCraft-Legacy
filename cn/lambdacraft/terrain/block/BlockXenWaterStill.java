package cn.lambdacraft.terrain.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockStationary;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;

public class BlockXenWaterStill extends BlockStationary{
	static int bucketRadioactiveID=0,bucketRadioactiveDamage=0;
	
	public BlockXenWaterStill(int id) {
        super(id, Material.water);
        setCreativeTab(CreativeTabs.tabRedstone);
        blockHardness = 100.0F;
        setLightOpacity(3);
        disableStats();
        this.setUnlocalizedName("xenwaterstill");
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        theIcon = new Icon[] {
                iconRegister.registerIcon("lambdacraft:xenwater_still"),
                iconRegister.registerIcon("lambdacraft:xenwater_flow") };
    }
}
