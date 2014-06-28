package cn.weaponmod.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import cn.liutils.api.register.IChannelProcess;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.proxy.WMGeneralProps;
import cn.weaponmod.register.WMPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetExplosion implements IChannelProcess {

	public static void sendNetPacket(World world, float bx, float by, float bz,
			float strengh) {
		ByteArrayOutputStream bos = WMPacketHandler.getStream(WMGeneralProps.NET_ID_EXPLOSION, 16);
		DataOutputStream outputStream = new DataOutputStream(bos);

		int dimension = world.provider.dimensionId;
		try {
			outputStream.writeFloat(bx);
			outputStream.writeFloat(by);
			outputStream.writeFloat(bz);
			outputStream.writeFloat(strengh);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = WMGeneralProps.NET_CHANNEL_CLIENT;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		System.out.println("Sending explosion packet from dimension " + dimension);
		PacketDispatcher.sendPacketToAllInDimension(packet, dimension);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onPacketData(DataInputStream inputStream, Player player) {
		float bx = 0, by = 0, bz = 0, st = 0;
		try {
			bx = inputStream.readFloat();
			by = inputStream.readFloat();
			bz = inputStream.readFloat();
			st = inputStream.readFloat();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("Recieved explosion package");
		WeaponHelper.clientExplode(Minecraft.getMinecraft().theWorld, st, bx, by, bz);
	}
	
}
