package cn.liutils.core.entity;

/**
 * A simple sample point with a timer, data used by EntityTrail.
 * @author WeAthFolD
 */
public class SamplePoint {

    public double x, y, z;
    public int tick;

    public SamplePoint(double p1, double p2, double p3, int t) {
        x = p1;
        y = p2;
        z = p3;
        tick = t;
    }

}
