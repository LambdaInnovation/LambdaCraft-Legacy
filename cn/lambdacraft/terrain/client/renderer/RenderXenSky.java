/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.terrain.client.renderer;

import static org.lwjgl.opengl.GL11.*;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IRenderHandler;

/**
 * @author mkpoli
 * 
 */
public class RenderXenSky extends IRenderHandler {
	private Minecraft mc;

	public RenderXenSky() {
		//this.glSkyList2 = (this.glSkyList = (this.starGLCallList = ReflectionHelper.getPrivateValue(RenderGlobal.class, renderGlobal, "starGLCallList")) + 1) + 1;
		//混淆使用它
		//this.glSkyList2 = (this.glSkyList = (this.starGLCallList = ReflectionHelper.getPrivateValue(RenderGlobal.class, renderGlobal, "field_72772_v")) + 1) + 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		this.mc = mc;
		renderSky();
	}

	private void renderSky() {
		{ // Starting
			glDisable(GL_FOG);
			glDisable(GL_ALPHA_TEST);
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			RenderHelper.disableStandardItemLighting();
			glDepthMask(false);
		}
		{ // Rendering
			glPushMatrix();
			glPushAttrib(GL_ENABLE_BIT);
			glCullFace(GL_FRONT);
			glColor4f(1, 1, 1, 1);

			fomartAndBind("rt"); 
			glBegin(GL_QUADS);
			{
				glTexCoord2f(1, 1);
				glVertex3f(100f, -100f, -100f);
				glTexCoord2f(0, 1);
				glVertex3f(-100f, -100f, -100f);
				glTexCoord2f(0, 0);
				glVertex3f(-100f, 100f, -100f);
				glTexCoord2f(1, 0);
				glVertex3f(100f, 100f, -100f);
			}
			glEnd();
			

			fomartAndBind("bk");
			glBegin(GL_QUADS);
			{
				glTexCoord2f(1, 1);
				glVertex3f(100f, -100f, 100f);
				glTexCoord2f(0, 1);
				glVertex3f(100f, -100f, -100f);
				glTexCoord2f(0, 0);
				glVertex3f(100f, 100f, -100f);
				glTexCoord2f(1, 0);
				glVertex3f(100f, 100f, 100f);
			}
			glEnd();

			fomartAndBind("lf"); 
			glBegin(GL_QUADS);
			{
				glTexCoord2f(1, 1);
				glVertex3f(-100f, -100f, 100f);
				glTexCoord2f(0, 1);
				glVertex3f(100f, -100f, 100f);
				glTexCoord2f(0, 0);
				glVertex3f(100f, 100f, 100f);
				glTexCoord2f(1, 0);
				glVertex3f(-100f, 100f, 100f);
			}
			glEnd();

			fomartAndBind("ft"); 
			glBegin(GL_QUADS);
			{
				glTexCoord2f(1, 1);
				glVertex3f(-100f, -100f, -100f);
				glTexCoord2f(0, 1);
				glVertex3f(-100f, -100f, 100f);
				glTexCoord2f(0, 0);
				glVertex3f(-100f, 100f, 100f);
				glTexCoord2f(1, 0);
				glVertex3f(-100f, 100f, -100f);
			}
			glEnd();

			fomartAndBind("up");
			glBegin(GL_QUADS);
			{
				glTexCoord2f(1, 1);
				glVertex3f(-100f, 100f, -100f);
				glTexCoord2f(0, 1);
				glVertex3f(-100f, 100f, 100f);
				glTexCoord2f(0, 0);
				glVertex3f(100f, 100f, 100f);
				glTexCoord2f(1, 0);
				glVertex3f(100f, 100f, -100f);
			}
			glEnd();


			glScalef(1f, 1f, -1f);
			fomartAndBind("dn");
			glBegin(GL_QUADS);
			{
				glTexCoord2f(0, 0);
				glVertex3f(-100f, -100f, -100f);
				glTexCoord2f(1, 0);
				glVertex3f(-100f, -100f, 100f);
				glTexCoord2f(1, 1);
				glVertex3f(100f, -100f, 100f);
				glTexCoord2f(0, 1);
				glVertex3f(100f, -100f, -100f);
			}
			glEnd();
			
			glCullFace(GL_BACK);
			
			glPopAttrib();
			glPopMatrix();
		}
		{ // Ending
			glDepthMask(true);
			glEnable(GL_TEXTURE_2D);
			glEnable(GL_ALPHA_TEST);
		}

	}

	private void fomartAndBind(String s) {
		mc.renderEngine.bindTexture(new ResourceLocation(String.format(cn.lambdacraft.core.prop.ClientProps.SKYBOX_PATH, s)));
	}
}
