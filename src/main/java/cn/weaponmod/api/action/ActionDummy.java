package cn.weaponmod.api.action;

public class ActionDummy extends Action {

    public ActionDummy() {
        super(0, "dummy");
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getPriority() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean doesConcurrent(Action other) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getRenderPriority() {
        // TODO Auto-generated method stub
        return 0;
    }

}
