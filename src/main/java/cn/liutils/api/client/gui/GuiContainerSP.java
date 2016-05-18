/**
 * 
 */
package cn.liutils.api.client.gui;

import java.util.Set;

import cn.liutils.api.client.gui.part.LIGuiPart;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

/**
 * 单页的GuiContainerLI。
 * @author WeathFolD
 */
public abstract class GuiContainerSP extends GuiContainer {
    
    protected final LIGuiScreen screen;
    protected LIGuiPage page;
    
    protected class SinglePage extends LIGuiPage {

        public SinglePage() {
            super(null, "main", 0, 0);
        }

        @Override
        public void drawPage() {
            GuiContainerSP.this.drawPage();
        }

        @Override
        public void addElements(Set<LIGuiPart> set) {
            GuiContainerSP.this.addElements(set);
        }

        @Override
        public void onPartClicked(LIGuiPart part, float subX, float subY) {
            GuiContainerSP.this.onPartClicked(part, subX, subY);
        }
        
    }

    public GuiContainerSP(int xSize, int ySize, Container ct) {
        super(ct);
        this.xSize = xSize;
        this.ySize = ySize;
        screen = new LIGuiScreen(xSize, ySize);
        page = new SinglePage();
        screen.getActivePages().add(page);
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
    
    /**
     * 绘制pages
     */
    protected void drawElements(int mx, int my) {
        update();
        screen.drawElements(mx, my);
    }
    
    private void update() {
        screen.updateScreenSize(width, height);
    }
    
    /**
     * 绘制GUI。注意此时原点已经在GUI右上角。
     */
    protected void drawPage() {}
    
    /**
     * 在GUI被创建时添加GUI元素
     * @param set
     */
    protected abstract void addElements(Set<LIGuiPart> set);
    
    /**
     * 处理按钮被按下时的行为
     */
    protected abstract void onPartClicked(LIGuiPart part, float mx, float my);
    
}
