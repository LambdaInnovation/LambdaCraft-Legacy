package cn.liutils.api.client.render;

import cn.liutils.core.proxy.LIClientProps;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderEmptyBlock implements ISimpleBlockRenderingHandler {

    public RenderEmptyBlock() {
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID,
            RenderBlocks renderer) {
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
            Block block, int modelId, RenderBlocks renderer) {
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelID) {
        return false;
    }

    @Override
    public int getRenderId() {
        return LIClientProps.RENDER_TYPE_EMPTY;
    }

}
