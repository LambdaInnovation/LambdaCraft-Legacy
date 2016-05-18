/**
 * Copyright (C) Lambda-Innovation, 2013-2014
 * This code is open-source. Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 */
package cn.liutils.api.client.shape;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector2d;

import cn.liutils.api.client.util.RenderUtils;
import cn.liutils.api.util.Pair;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;

/**
 * 绘制一个由y=x^2绕y轴旋转确定的三维圆弧罩子。
 * 现在纵向贴图的映射似乎还有问题的样子……
 * @author WeAthFolD
 */
public class ShapeGenSquaredArc {
    
    public static List<Vector2d> temp = new ArrayList<Vector2d>();
    
    private static int edges = 0;
    private static int size = 0;
    private static boolean reverse = false;
    public static void drawArc(double freq, int ed, double radius, boolean uvMirror, double uOffset) {
        edges = ed;
        Pair<Vec3, Pair<Double, Double>>[] vecs = getShape(freq, edges);
        size = vecs.length;
        //reverse = uvMirror;
        reverse = false;
        int times = vecs.length / edges;
        Tessellator t = Tessellator.instance;
        double uMax = 2D;
        //GL11.glScaled(radius, radius * 2, radius);
        t.startDrawingQuads();//结果还是quads
        for(int i = 0; i < times - 1; i++) {
            int begin = i * edges;
            for(int j = begin; j < begin + edges; j++) {
                Pair<Vec3, Pair<Double, Double> > v0, v1, v2, v3;
                if(j == edges - 1) {
                    v0 = vecs[j];
                    v1 = vecs[begin];
                    v2 = vecs[next(j)];
                    v3 = vecs[next(begin)];
                } else {
                    v0 = vecs[j];
                    v1 = vecs[j + 1];
                    v2 = vecs[next(j)];
                    v3 = vecs[next(j + 1)];
                }
                Vector2d uv0 = calcUV(v0),
                         uv1 = calcUV(v1),
                         uv2 = calcUV(v2),
                         uv3 = calcUV(v3);
                RenderUtils.addVertex(v1.first, uv1.x + uOffset, uv1.y);
                RenderUtils.addVertex(v3.first, uv3.x + uOffset, uv3.y);
                RenderUtils.addVertex(v2.first, uv2.x + uOffset, uv2.y);
                RenderUtils.addVertex(v0.first, uv0.x + uOffset, uv0.y);
            } 
        }
        
        t.draw();
    }
    
    private static Vector2d calcUV(Pair<Vec3, Pair<Double, Double> > vec) {
        return new Vector2d(
                reverse ? 1 : -1 * (vec.second.second / Math.PI / 2 - 0.5)
                , vec.first.yCoord);
    }
    
    private static int next(int i) {
        return (i + edges) % size;
    }
    
    /**
     * @param freq Sample frequency, the higher the better effects
     *     edges edges per circle, the higher the better effects
     * @return Second of the pair: first radius, second rotation arc
     */
    public static Pair<Vec3, Pair<Double, Double> >[] getShape(double freq, int edges) {
        temp.clear();
        
        for(double x = 0.0; x < 0.5; x += freq) {
            double h = getHByX(x);
            temp.add(new Vector2d(x, h));
        }
        
        for(double y = 0.75; y > 0; y -= freq) {
            double rad = getRadByH(y);
            temp.add(new Vector2d(rad, y));
        }
        
        Pair<Vec3, Pair<Double, Double>>[] vecs = new Pair[temp.size() * edges];
        int ind = 0;
        for(Vector2d vec : temp) {
            double darc = 2 * Math.PI / edges; //Δα per vertex iteration.
            double arc = 0.0D;
            for(int i = 0; i < edges; i++) {
                vecs[ind++] = new Pair(Vec3.createVectorHelper(Math.sin(arc) * vec.x, vec.y, Math.cos(arc) * vec.x), new Pair(vec.x, arc));
                arc += darc;
            }
        }
        
        return vecs;
    }
    
    private static double getRadByH(double h) {
        return Math.sqrt(1 - h);
    }
    
    private static double getHByX(double x) {
        return -(x * x) + 1;
    }

}
