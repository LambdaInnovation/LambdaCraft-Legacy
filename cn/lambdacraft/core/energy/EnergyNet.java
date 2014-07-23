package cn.lambdacraft.core.energy;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.WeakHashMap;

import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import cn.lambdacraft.api.energy.events.EnergyTileSourceEvent;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.world.WorldData;

public final class EnergyNet {

	public static final double minConductionLoss = 0.0001D;
	private static final ForgeDirection[] directions = ForgeDirection.values();
	private static EventHandler eventHandler;
	private final Map<IEnergySource, List<EnergyPath>> energySourceToEnergyPathMap = new WeakHashMap();
	private final Map<EntityLiving, Double> entityLivingToShockEnergyMap = new WeakHashMap();
	private static int apiErrorCooldown = 0;

	public static void initialize() {
		eventHandler = new EventHandler();
	}

	public static EnergyNet getForWorld(World world) {
		WorldData worldData = WorldData.get(world);

		return worldData.energyNet;
	}

	public static void onTick(World world) {
		CBCMod.proxy.profilerStartSection("LC");

		EnergyNet energyNet = getForWorld(world);

		for (Map.Entry entry : energyNet.entityLivingToShockEnergyMap
				.entrySet()) {
			EntityLiving target = (EntityLiving) entry.getKey();
			int damage = (((Integer) entry.getValue()).intValue() + 63) / 64;

			// if
			// (target.isEntityAlive())target.attackEntityFrom(IC2DamageSource.electricity,
			// damage);
		}

		energyNet.entityLivingToShockEnergyMap.clear();

		if ((apiErrorCooldown > 0) && (world.provider.dimensionId == 0))
			apiErrorCooldown -= 1;

		CBCMod.proxy.profilerEndSection();
	}

	public void addTileEntity(TileEntity addedTileEntity) {
		if ((!(addedTileEntity instanceof IEnergyTile))) {
			return;
		}
		
		if ((addedTileEntity instanceof IEnergyAcceptor)) {
			List<EnergyPath> reverseEnergyPaths = discover(addedTileEntity,
					true, 2147483647);

			for (EnergyPath reverseEnergyPath : reverseEnergyPaths) {
				IEnergySource energySource = (IEnergySource) reverseEnergyPath.target;

				if ((!this.energySourceToEnergyPathMap
						.containsKey(energySource))
						|| (energySource.getOfferedEnergy() <= reverseEnergyPath.loss)) {
					continue;
				}
				this.energySourceToEnergyPathMap.remove(energySource);
			}
		}

		if ((addedTileEntity instanceof IEnergySource))
			;
	}

	public void removeTileEntity(TileEntity removedTileEntity) {
		if ((!(removedTileEntity instanceof IEnergyTile))) {
			CBCMod.log.warning(new StringBuilder().append("removing ")
					.append(removedTileEntity)
					.append(" from the EnergyNet failed.").toString());

			return;
		}

		if ((removedTileEntity instanceof IEnergyAcceptor)) {
			List<EnergyPath> reverseEnergyPaths = discover(removedTileEntity,
					true, 2147483647);
			Iterator it;

			for (EnergyPath reverseEnergyPath : reverseEnergyPaths) {
				IEnergySource energySource = (IEnergySource) reverseEnergyPath.target;

				if ((!this.energySourceToEnergyPathMap
						.containsKey(energySource))
						|| (energySource.getOfferedEnergy() <= reverseEnergyPath.loss)) {
					continue;
				}
				if ((removedTileEntity instanceof IEnergyConductor))
					this.energySourceToEnergyPathMap.remove(energySource);
				else
					for (it = ((List) this.energySourceToEnergyPathMap
							.get(energySource)).iterator(); it.hasNext();)
						if (((EnergyPath) it.next()).target == removedTileEntity)
							it.remove();
			}

			if ((removedTileEntity instanceof IEnergySource)) {
				this.energySourceToEnergyPathMap.remove(removedTileEntity);
			}
		}
	}

	public int emitEnergyFrom(IEnergySource energySource, int amount) {

		if (!this.energySourceToEnergyPathMap.containsKey(energySource)) {
			this.energySourceToEnergyPathMap.put(
					energySource,
					discover((TileEntity) energySource, false,
							energySource.getOfferedEnergy()));
		}

		List activeEnergyPaths = new Vector();
		double totalInvLoss = 0.0D;

		for (EnergyPath energyPath : this.energySourceToEnergyPathMap
				.get(energySource)) {
			assert ((energyPath.target instanceof IEnergySink));

			IEnergySink energySink = (IEnergySink) energyPath.target;

			if ((energySink.demandedEnergyUnits() > 0) && (energyPath.loss < amount)) {
				totalInvLoss += 1.0D / energyPath.loss;
				activeEnergyPaths.add(energyPath);
			}
		}

		Collections.shuffle(activeEnergyPaths);

		// TODO:这里取巧的用了个size检查，求排错
		if (activeEnergyPaths.size() <= 0)
			return amount;
		for (int i = activeEnergyPaths.size() - amount; i > 0; i--) {
			EnergyPath removedEnergyPath = (EnergyPath) activeEnergyPaths
					.remove(activeEnergyPaths.size() - 1);
			totalInvLoss -= 1.0D / removedEnergyPath.loss;
		}

		Map<EnergyPath, Double> suppliedEnergyPaths = new HashMap();
		new Vector();

		while ((!activeEnergyPaths.isEmpty()) && (amount > 0)) {
			int energyConsumed = 0;
			double newTotalInvLoss = 0.0D;

			List<EnergyPath> currentActiveEnergyPaths = activeEnergyPaths;
			activeEnergyPaths = new Vector();

			activeEnergyPaths.iterator();

			for (EnergyPath energyPath : currentActiveEnergyPaths) {
				IEnergySink energySink = (IEnergySink) energyPath.target;

				int energyProvided = (int) Math
						.floor(Math.round(amount / totalInvLoss
								/ energyPath.loss * 100000.0D) / 100000.0D);
				int energyLoss = (int) Math.floor(energyPath.loss);

				if (energyProvided > energyLoss) {
					double energyReturned = energySink.injectEnergyUnits(energyPath.targetDirection, energyProvided
									- energyLoss);

					if ((energyReturned == 0)
							&& (energySink.demandedEnergyUnits() > 0)) {
						activeEnergyPaths.add(energyPath);
						newTotalInvLoss += 1.0D / energyPath.loss;
					} else if (energyReturned >= energyProvided - energyLoss) {
						energyReturned = energyProvided - energyLoss;

						if (apiErrorCooldown == 0) {
							apiErrorCooldown = 100;

							String c = "not a tile entity";
							if ((energySink instanceof TileEntity)) {
								TileEntity te = (TileEntity) energySink;
								c = new StringBuilder()
										.append(te.worldObj == null ? "unknown"
												: Integer
														.valueOf(te.worldObj.provider.dimensionId))
										.append(":").append(te.xCoord)
										.append(",").append(te.yCoord)
										.append(",").append(te.zCoord)
										.toString();
							}
							CBCMod.log
									.warning(new StringBuilder()
											.append("API ERROR: ")
											.append(energySink)
											.append(" (")
											.append(c)
											.append(") didn't implement demandsEnergy() properly, no energy from injectEnergy accepted (")
											.append(energyReturned)
											.append(") although demandsEnergy() requested ")
											.append(energyProvided - energyLoss)
											.append(".").toString());
						}
					}

					energyConsumed += energyProvided - energyReturned;

					double energyInjected = energyProvided - energyLoss
							- energyReturned;

					if (!suppliedEnergyPaths.containsKey(energyPath))
						suppliedEnergyPaths.put(energyPath,
								Double.valueOf(energyInjected));
					else
						suppliedEnergyPaths.put(
								energyPath,
								Double.valueOf(energyInjected
										+ suppliedEnergyPaths.get(energyPath)
												.intValue()));
				} else {
					activeEnergyPaths.add(energyPath);
					newTotalInvLoss += 1.0D / energyPath.loss;
				}
			}

			if ((energyConsumed == 0) && (!activeEnergyPaths.isEmpty())) {
				EnergyPath removedEnergyPath = (EnergyPath) activeEnergyPaths
						.remove(activeEnergyPaths.size() - 1);
				newTotalInvLoss -= 1.0D / removedEnergyPath.loss;
			}

			totalInvLoss = newTotalInvLoss;
			amount -= energyConsumed;
		}

		for (Map.Entry<EnergyPath, Double> entry : suppliedEnergyPaths.entrySet()) {
			EnergyPath energyPath = (EnergyPath) entry.getKey();
			double energyInjected = entry.getValue();

			energyPath.totalEnergyConducted += energyInjected;

			if (energyInjected > energyPath.minInsulationEnergyAbsorption) {
				List<EntityLiving> entitiesNearEnergyPath = ((TileEntity) energySource).worldObj
						.getEntitiesWithinAABB(EntityLiving.class,
								AxisAlignedBB.getBoundingBox(
										energyPath.minX - 1,
										energyPath.minY - 1,
										energyPath.minZ - 1,
										energyPath.maxX + 2,
										energyPath.maxY + 2,
										energyPath.maxZ + 2));

				for (EntityLiving entityLiving : entitiesNearEnergyPath) {
					double maxShockEnergy = 0;

					for (IEnergyConductor energyConductor : energyPath.conductors) {
						TileEntity te = (TileEntity) energyConductor;

						if (entityLiving.boundingBox
								.intersectsWith(AxisAlignedBB.getBoundingBox(
										te.xCoord - 1, te.yCoord - 1,
										te.zCoord - 1, te.xCoord + 2,
										te.yCoord + 2, te.zCoord + 2))) {
							double shockEnergy = energyInjected
									- energyConductor
											.getInsulationEnergyAbsorption();

							if (shockEnergy > maxShockEnergy)
								maxShockEnergy = shockEnergy;
							if (energyConductor.getInsulationEnergyAbsorption() == energyPath.minInsulationEnergyAbsorption)
								break;
						}
					}
					if (this.entityLivingToShockEnergyMap
							.containsKey(entityLiving))
						this.entityLivingToShockEnergyMap
								.put(entityLiving,
										Double.valueOf(this.entityLivingToShockEnergyMap
												.get(entityLiving).intValue()
												+ maxShockEnergy));
					else {
						this.entityLivingToShockEnergyMap.put(entityLiving, Double.valueOf(maxShockEnergy));
					}
				}

				if (energyInjected >= energyPath.minInsulationBreakdownEnergy) {
					for (IEnergyConductor energyConductor : energyPath.conductors) {
						if (energyInjected >= energyConductor
								.getInsulationBreakdownEnergy()) {
							energyConductor.removeInsulation();

							if (energyConductor.getInsulationEnergyAbsorption() < energyPath.minInsulationEnergyAbsorption)
								energyPath.minInsulationEnergyAbsorption = energyConductor
										.getInsulationEnergyAbsorption();
						}
					}
				}
			}

			if (energyInjected >= energyPath.minConductorBreakdownEnergy)
				for (IEnergyConductor energyConductor : energyPath.conductors)
					if (energyInjected >= energyConductor
							.getConductorBreakdownEnergy())
						energyConductor.removeConductor();
		}

		return amount;
	}

	public long getTotalEnergyEmitted(TileEntity tileEntity) {
		long ret = 0L;

		if ((tileEntity instanceof IEnergyConductor)) {
			List<EnergyPath> reverseEnergyPaths = discover(tileEntity, true,
					2147483647);

			for (EnergyPath reverseEnergyPath : reverseEnergyPaths) {
				IEnergySource energySource = (IEnergySource) reverseEnergyPath.target;

				if ((!this.energySourceToEnergyPathMap
						.containsKey(energySource))
						|| (energySource.getOfferedEnergy() <= reverseEnergyPath.loss)) {
					continue;
				}
				for (EnergyPath energyPath : this.energySourceToEnergyPathMap
						.get(energySource)) {
					if (((tileEntity instanceof IEnergyConductor))
							&& (energyPath.conductors.contains(tileEntity))) {
						ret += energyPath.totalEnergyConducted;
					}
				}
			}
		}

		if (((tileEntity instanceof IEnergySource))
				&& (this.energySourceToEnergyPathMap.containsKey(tileEntity))) {
			for (EnergyPath energyPath : this.energySourceToEnergyPathMap
					.get(tileEntity)) {
				ret += energyPath.totalEnergyConducted;
			}

		}

		return ret;
	}

	public long getTotalEnergySunken(TileEntity tileEntity) {
		long ret = 0L;

		if (((tileEntity instanceof IEnergyConductor))
				|| ((tileEntity instanceof IEnergySink))) {
			List<EnergyPath> reverseEnergyPaths = discover(tileEntity, true,
					2147483647);

			for (EnergyPath reverseEnergyPath : reverseEnergyPaths) {
				IEnergySource energySource = (IEnergySource) reverseEnergyPath.target;

				if ((!this.energySourceToEnergyPathMap
						.containsKey(energySource))
						|| (energySource.getOfferedEnergy() <= reverseEnergyPath.loss)) {
					continue;
				}
				for (EnergyPath energyPath : this.energySourceToEnergyPathMap
						.get(energySource)) {
					if ((((tileEntity instanceof IEnergySink)) && (energyPath.target == tileEntity))
							|| (((tileEntity instanceof IEnergyConductor)) && (energyPath.conductors
									.contains(tileEntity)))) {
						ret += energyPath.totalEnergyConducted;
					}
				}
			}
		}

		return ret;
	}

	private List<EnergyPath> discover(TileEntity emitter, boolean reverse,
			double lossLimit) {
		Map<TileEntity, EnergyBlockLink> reachedTileEntities = new HashMap();
		LinkedList tileEntitiesToCheck = new LinkedList();

		tileEntitiesToCheck.add(emitter);
		double currentLoss;
		while (!tileEntitiesToCheck.isEmpty()) {
			TileEntity currentTileEntity = (TileEntity) tileEntitiesToCheck
					.remove();
			if (currentTileEntity.isInvalid()) {
				continue;
			}
			currentLoss = 0.0D;

			if (currentTileEntity != emitter)
				currentLoss = reachedTileEntities.get(currentTileEntity).loss;

			List<EnergyTarget> validReceivers = getValidReceivers(
					currentTileEntity, reverse);

			for (EnergyTarget validReceiver : validReceivers) {
				if (validReceiver.tileEntity == emitter)
					continue;
				double additionalLoss = 0.0D;

				if ((validReceiver.tileEntity instanceof IEnergyConductor)) {
					additionalLoss = ((IEnergyConductor) validReceiver.tileEntity)
							.getConductionLoss();

					if (additionalLoss < 0.0001D)
						additionalLoss = 0.0001D;

					if (currentLoss + additionalLoss >= lossLimit) {
						continue;
					}
				}
				if ((!reachedTileEntities.containsKey(validReceiver.tileEntity))
						|| (reachedTileEntities.get(validReceiver.tileEntity).loss > currentLoss
								+ additionalLoss)) {
					reachedTileEntities.put(validReceiver.tileEntity,
							new EnergyBlockLink(validReceiver.direction,
									currentLoss + additionalLoss));

					if ((validReceiver.tileEntity instanceof IEnergyConductor)) {
						tileEntitiesToCheck.remove(validReceiver.tileEntity);
						tileEntitiesToCheck.add(validReceiver.tileEntity);
					}
				}
			}
		}

		List<EnergyPath> energyPaths = new LinkedList();

		for (Map.Entry entry : reachedTileEntities.entrySet()) {
			TileEntity tileEntity = (TileEntity) entry.getKey();

			if (((!reverse) && ((tileEntity instanceof IEnergySink)))
					|| ((reverse) && ((tileEntity instanceof IEnergySource)))) {
				EnergyBlockLink energyBlockLink = (EnergyBlockLink) entry
						.getValue();

				EnergyPath energyPath = new EnergyPath();

				if (energyBlockLink.loss > 0.1D)
					energyPath.loss = energyBlockLink.loss;
				else {
					energyPath.loss = 0.1D;
				}

				energyPath.target = tileEntity;
				energyPath.targetDirection = energyBlockLink.direction;

				if ((!reverse) && ((emitter instanceof IEnergySource))) {
					while (true) {
						if(energyBlockLink == null)
							break;
						tileEntity = tileEntity.worldObj.getBlockTileEntity
								(tileEntity.xCoord + energyPath.targetDirection.offsetX
										, tileEntity.yCoord + energyPath.targetDirection.offsetY,
										tileEntity.zCoord + energyPath.targetDirection.offsetZ);

						if (tileEntity != emitter) {
							if ((tileEntity instanceof IEnergyConductor)) {
								IEnergyConductor energyConductor = (IEnergyConductor) tileEntity;

								if (tileEntity.xCoord < energyPath.minX)
									energyPath.minX = tileEntity.xCoord;
								if (tileEntity.yCoord < energyPath.minY)
									energyPath.minY = tileEntity.yCoord;
								if (tileEntity.zCoord < energyPath.minZ)
									energyPath.minZ = tileEntity.zCoord;
								if (tileEntity.xCoord > energyPath.maxX)
									energyPath.maxX = tileEntity.xCoord;
								if (tileEntity.yCoord > energyPath.maxY)
									energyPath.maxY = tileEntity.yCoord;
								if (tileEntity.zCoord > energyPath.maxZ)
									energyPath.maxZ = tileEntity.zCoord;

								energyPath.conductors.add(energyConductor);

								if (energyConductor
										.getInsulationEnergyAbsorption() < energyPath.minInsulationEnergyAbsorption)
									energyPath.minInsulationEnergyAbsorption = energyConductor
											.getInsulationEnergyAbsorption();
								if (energyConductor
										.getInsulationBreakdownEnergy() < energyPath.minInsulationBreakdownEnergy)
									energyPath.minInsulationBreakdownEnergy = energyConductor
											.getInsulationBreakdownEnergy();
								if (energyConductor
										.getConductorBreakdownEnergy() < energyPath.minConductorBreakdownEnergy)
									energyPath.minConductorBreakdownEnergy = energyConductor
											.getConductorBreakdownEnergy();

								energyBlockLink = reachedTileEntities
										.get(tileEntity);
								if (energyBlockLink == null) {
									System.out
											.println(new StringBuilder()
													.append("An energy network pathfinding entry is corrupted.\nThis could happen due to incorrect Minecraft behavior or a bug.\n\n(Technical information: energyBlockLink, tile entities below)\nE: ")
													.append(emitter)
													.append(" (")
													.append(emitter.xCoord)
													.append(",")
													.append(emitter.yCoord)
													.append(",")
													.append(emitter.zCoord)
													.append(")\n")
													.append("C: ")
													.append(tileEntity)
													.append(" (")
													.append(tileEntity.xCoord)
													.append(",")
													.append(tileEntity.yCoord)
													.append(",")
													.append(tileEntity.zCoord)
													.append(")\n")
													.append("R: ")
													.append(energyPath.target)
													.append(" (")
													.append(energyPath.target.xCoord)
													.append(",")
													.append(energyPath.target.yCoord)
													.append(",")
													.append(energyPath.target.zCoord)
													.append(")").toString());
								}

								continue;
							}
							if (tileEntity == null) {
								break;
							}
							System.out
									.println(new StringBuilder()
											.append("EnergyNet: EnergyBlockLink corrupted (")
											.append(energyPath.target)
											.append(" [")
											.append(energyPath.target.xCoord)
											.append(" ")
											.append(energyPath.target.yCoord)
											.append(" ")
											.append(energyPath.target.zCoord)
											.append("] -> ").append(tileEntity)
											.append(" [")
											.append(tileEntity.xCoord)
											.append(" ")
											.append(tileEntity.yCoord)
											.append(" ")
											.append(tileEntity.zCoord)
											.append("] -> ").append(emitter)
											.append(" [")
											.append(emitter.xCoord).append(" ")
											.append(emitter.yCoord).append(" ")
											.append(emitter.zCoord)
											.append("])").toString());

							break;
						}
					}
				}

				energyPaths.add(energyPath);
			}
		}

		return energyPaths;
	}

	public List<TileEntity> discoverTargets(TileEntity emitter,
			boolean reverse, int lossLimit) {
		List<EnergyPath> paths = discover(emitter, reverse, lossLimit);
		List targets = new LinkedList();
		for (EnergyPath path : paths) {
			targets.add(path.target);
		}
		return targets;
	}

	private List<EnergyTarget> getValidReceivers(TileEntity emitter,
			boolean reverse) {
		List validReceivers = new LinkedList();

		for (ForgeDirection lcdirection : directions) {
			TileEntity target = getOffsetTile(emitter, lcdirection);
			
			if (!(target instanceof IEnergyTile))
				continue;
			ForgeDirection inverseDirection = lcdirection.getOpposite();

			if (((reverse) || (!(emitter instanceof IEnergyEmitter)) || (!((IEnergyEmitter) emitter)
					.emitsEnergyTo(target, lcdirection)))
					&& ((!reverse) || (!(emitter instanceof IEnergyAcceptor)) || (!((IEnergyAcceptor) emitter)
							.acceptsEnergyFrom(target, lcdirection)))) {
				continue;
			}
			if (((reverse) || (!(target instanceof IEnergyAcceptor)) || (!((IEnergyAcceptor) target)
					.acceptsEnergyFrom(emitter, inverseDirection)))
					&& ((!reverse) || (!(target instanceof IEnergyEmitter)) || (!((IEnergyEmitter) target)
							.emitsEnergyTo(emitter, inverseDirection)))) {
				continue;
			}
			validReceivers.add(new EnergyTarget(target, inverseDirection));
		}

		return validReceivers;
	}
	
	private TileEntity getOffsetTile(TileEntity tile, ForgeDirection dir) {
		return tile.worldObj.getBlockTileEntity(
					tile.xCoord + dir.offsetX,
					tile.yCoord + dir.offsetY,
					tile.zCoord + dir.offsetZ
				);
	}

	public static class EventHandler {
		public EventHandler() {
			MinecraftForge.EVENT_BUS.register(this);
		}

		@ForgeSubscribe
		public void onEnergyTileLoad(EnergyTileLoadEvent event) {
			EnergyNet.getForWorld(event.world).addTileEntity((TileEntity) event.energyTile);
		}

		@ForgeSubscribe
		public void onEnergyTileUnload(EnergyTileUnloadEvent event) {
			EnergyNet.getForWorld(event.world).removeTileEntity(
					(TileEntity) event.energyTile);
		}

		@ForgeSubscribe
		public void onEnergyTileSource(EnergyTileSourceEvent event) {
			event.amount = EnergyNet.getForWorld(event.world).emitEnergyFrom(
					(IEnergySource) event.energyTile, event.amount);
		}
	}
}