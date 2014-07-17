/**
 * 
 */
package cn.lambdacraft.deathmatch.client.renderer;

import cn.lambdacraft.core.proxy.LCClientProps;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

/**
 * @author Administrator
 * 
 */
public class RenderEmptyBlock implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int v) {
		return false;
	}

	@Override
	public int getRenderId() {
		return LCClientProps.RENDER_TYPE_EMPTY;
	}

}
