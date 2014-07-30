package cn.lambdacraft.deathmatch.flashlight;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import java.util.EnumSet;
import cn.lambdacraft.core.CBCPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumSkyBlock;

public class ClientTickHandler implements ITickHandler {
	public static int ticks = 0;

	/* the flag if the flashlight button has been pressed */
	public boolean flag = false;

	@Override
	public void tickStart(EnumSet var1, Object... var2) {
	}

	@Override
	public void tickEnd(EnumSet var1, Object... var2) {
		if (var1.equals(EnumSet.of(TickType.RENDER))) {
			this.onRenderTick();
		} else if (var1.equals(EnumSet.of(TickType.CLIENT))) {
			GuiScreen var3 = Minecraft.getMinecraft().currentScreen;
			if (var3 != null) {
				this.onTickInGUI(var3);
			} else {
				this.onTickInGame();
			}
		}
	}

	@Override
	public EnumSet ticks() {
		return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return "Flashlight.ClientTickHandler";
	}

	public void onRenderTick() {
	}

	public void onTickInGUI(GuiScreen var1) {
	}

	public void onTickInGame() {
		boolean var1 = false;
		int var5;
		++ticks;

		if (ticks >= 5) {
			Minecraft mc = Minecraft.getMinecraft();
			EntityPlayerSP player = mc.thePlayer;
			ticks = 0;
			LightValue.resetAll();
			LightValue.emptyData();
			
			MovingObjectPosition result = player.rayTrace(36.0D, 1.0F);
			if (flag && CBCPlayer.armorStat[3] && result != null)
			{
				int blockX = result.blockX;
				int blockY = result.blockY;
				int blockZ = result.blockZ;
				var5 = result.sideHit;

				if (var5 == 0) {
					--blockY;
				} else if (var5 == 1) {
					++blockY;
				} else {
					if (var5 == 2) {
						--blockZ;
					}

					if (var5 == 3) {
						++blockZ;
					}

					if (var5 == 4) {
						--blockX;
					}

					if (var5 == 5) {
						++blockX;
					}
				}

				LightValue.addData(blockX, blockY, blockZ);
				Minecraft.getMinecraft().theWorld.setLightValue(EnumSkyBlock.Block, blockX, blockY, blockZ, 15);
				Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, blockX, blockY + 1, blockZ);
				Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, blockX - 1, blockY, blockZ);
				Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, blockX, blockY, blockZ - 1);
			}
		}

	}
}
