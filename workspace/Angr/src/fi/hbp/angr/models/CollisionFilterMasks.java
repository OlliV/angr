package fi.hbp.angr.models;

/**
 * Collision filter masks.
 *
 * These masks are used to mask out collision physics
 * eg. when body is destroyed but not yet removed from the
 * world or when player model should not collide with other
 * objects.
 */
public class CollisionFilterMasks {
    public static final short GROUND = 0x01;
    public static final short HANS_BODY = 0x02;
    public static final short HANS_HAND = 0x04;
    public static final short GRENADE = 0x08;
    /**
     * Other than listed here.
     */
    public static final short OTHER = 0x16;
    /**
     * All bodies.
     */
    public static final short ALL = 0xff;
}
