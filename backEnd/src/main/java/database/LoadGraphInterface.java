package database;

import java.util.List;

/**
 * Created by user on 25/05/16.
 */
public interface LoadGraphInterface {

    /**
     * Request the specified data to be loaded into the backend.
     *
     * @param minX      The left bound of the nodes to be loaded.
     * @param maxX      The right bound of the nodes to be loaded.
     * @param zoomLevel The zoomLevel to calculate the Nodes for.
     * @param ids       The id's of the genomes or the parents to be loaded.
     */
    void loadData(int minX, int maxX, float zoomLevel, List<String> ids);

}
