package cn.liutils.core.debug;

import java.util.List;

public interface IModifierProvider {
    public Object extractInstance();
    public List<FieldModifier> getModifiers();
    public String getName();
}
