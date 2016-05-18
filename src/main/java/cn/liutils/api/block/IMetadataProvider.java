package cn.liutils.api.block;

/**
 * Use this interface to mark a TileEntity so it has the capability to represent metadata.
 * It is currently used by BlockDirectionedMulti, you can use this abstract interface
 * for any other operations.<br/>
 * P.S>本不必如此麻烦的，要是有多重继承的话……
 * @author WeathFolD
 */
public interface IMetadataProvider {
    public int getMetadata();
    public void setMetadata(int meta);
}
