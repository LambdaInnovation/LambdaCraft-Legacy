package cn.lambdacraft.api.energy.tile;

public interface IEnConductor extends IEnAcceptor, IEnEmitter {
	public abstract double getConductionLoss();

	public abstract int getInsulationEnergyAbsorption();

	public abstract int getInsulationBreakdownEnergy();

	public abstract int getConductorBreakdownEnergy();

	public abstract void removeInsulation();

	public abstract void removeConductor();
}
