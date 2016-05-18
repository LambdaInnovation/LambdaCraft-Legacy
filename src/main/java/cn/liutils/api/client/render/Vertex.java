/**
 * Copyright (C) Lambda-Innovation, 2013-2014
 * This code is open-source. Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 */
package cn.liutils.api.client.render;

import javax.vecmath.Vector2d;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;
import cn.liutils.api.client.util.RenderUtils;
import cn.liutils.api.util.Pair;

/**
 * @author WeAthFolD
 *
 */
public class Vertex extends Pair<Vec3, Vector2d> {
    public Vertex(double x, double y, double z, double u, double v) {
        super(RenderUtils.newV3(x, y, z), new Vector2d(u, v));
    }

    public Vertex(Vec3 vec3, double u, double v) {
        super(vec3, new Vector2d(u, v));
    }

    public void addTo(Tessellator t) {
        t.addVertexWithUV(first.xCoord, first.yCoord, first.zCoord, second.x,
                second.y);
    }
}