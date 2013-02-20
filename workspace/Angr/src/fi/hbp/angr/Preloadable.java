package fi.hbp.angr;

/**
 * Interface for classes that support preloading of assets.
 *
 * Implementing this interface implies that the class supports
 * preloading of at least some of its assets.
 */
public interface Preloadable {
    /**
     * Start preloading assets.
     *
     * This method should only contain asynchronous loading calls described in:
     * http://code.google.com/p/libgdx/wiki/AssetManager#Loading_Assets
     */
    public void preload();

    public void create();

    /**
     * Unload assets.
     *
     * This method should mainly contain dispose/unload method calls described in:
     * http://code.google.com/p/libgdx/wiki/AssetManager#Disposing_Assets
     */
    public void unload();
}
