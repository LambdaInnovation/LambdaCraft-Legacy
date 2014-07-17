/**
 * 
 */
package cn.lambdacraft.core.block;

import cn.lambdacraft.core.LCMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * @author Administrator
 *
 */
public class LCBlock extends Block {

	public LCBlock(Material p_i45394_1_) {
		super(p_i45394_1_);
		this.setCreativeTab(LCMod.cct);
	}
	
	public LCBlock setCCTMisc() {
		setCreativeTab(LCMod.cctMisc);
		return this;
	}

}
