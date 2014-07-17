package cn.lambdacraft.deathmatch.flashlight;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumSkyBlock;
import cn.lambdacraft.core.LCPlayer;

public class FlashTickHandler {
	public static int ticks = 0;

	/* the flag if the flashlight button has been pressed */
	public boolean flag = false;
	
	public void onClientTick() {
		GuiScreen var3 = Minecraft.getMinecraft().currentScreen;
		if (var3 != null) {
			this.onTickInGUI(var3);
		} else {
			this.onTickInGame();
		}
	}

	public void onRenderTick() {
	}

	private void onTickInGUI(GuiScreen var1) {
	}

	private void onTickInGame() {
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
			if (flag && LCPlayer.armorStat[3] && result != null)
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
