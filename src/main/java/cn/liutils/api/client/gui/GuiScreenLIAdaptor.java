/**
 * 
 */
package cn.liutils.api.client.gui;

import java.util.Set;

import net.minecraft.client.gui.GuiScreen;

/**
 * @author WeathFolD
 *
 */
public abstract class GuiScreenLIAdaptor extends GuiScreen {

    protected final LIGuiScreen screen;
    
    public GuiScreenLIAdaptor(int xSize, int ySize) {
        screen = new LIGuiScreen(xSize, ySize);
    }
    
    /**
     * 绘制pages
     */
    public void drawElements(int mx, int my) {
        update();
        screen.drawElements(mx, my);
    }
    
    /**
     * 处理鼠标按下时的行为
     */
    @Override
    public void mouseClicked(int par1, int par2, int par3) {
        update();
        screen.mouseClicked(par1, par2, par3);
        super.mouseClicked(par1, par2, par3);
    }
    
    private void update() {
        screen.updateScreenSize(width, height);
        Set<LIGuiPage> pages = screen.getActivePages();
        pages.clear();
        updateActivedPages(pages);
    }
    
    public abstract void updateActivedPages(Set<LIGuiPage> pages);
    
}
