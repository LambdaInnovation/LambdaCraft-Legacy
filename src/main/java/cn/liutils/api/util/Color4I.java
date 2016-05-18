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
package cn.liutils.api.util;

/**
 * @author mkpoli
 *
 */
public class Color4I {
    public int red;
    public int green;
    public int blue;
    public int alpha;
    

    public Color4I(int red, int green, int blue, int alpha) {
        this.red = formatColorFloat(red);
        this.green = formatColorFloat(green);
        this.blue = formatColorFloat(blue);
        this.alpha = formatColorFloat(alpha);
    }
    

    public Color4I(int red, int green, int blue) {
        this.red = formatColorFloat(red);
        this.green = formatColorFloat(green);
        this.blue = formatColorFloat(blue);
        this.alpha = 255;
    }
    
    public void setValue(int r, int g, int b, int a) {
        red = r;
        green = g;
        blue = b;
        alpha = a;
    }
    

    private int formatColorFloat(int source) {
        int result;
        if (source < 0) {
            result = 0;
        } else if (source > 255) {
            result = 255;
        } else {
            result = source;
        }
        return result;
    }
    
    @Override
    public String toString() {
        return String.format("[Type : Color 4f  Red : %d  Green : %d  Blue : %d  Alpha : %d]", red, green, blue, alpha);
    }
    
}
