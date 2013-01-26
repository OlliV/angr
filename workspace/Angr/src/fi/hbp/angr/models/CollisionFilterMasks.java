package fi.hbp.angr.models;

public class CollisionFilterMasks {
    public static final short WALL = 0x01;
    public static final short HANS_BODY = 0x02;
    public static final short HANS_HAND = 0x04;
    public static final short GRENADE = 0x08;
    public static final short ALL = 0xff;
}
