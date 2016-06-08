package com.pl.tagc.tagcwebapp;

import java.util.ArrayList;
import java.util.List;

import phylogenetictree.PhylogeneticTree;
import controller.Controller;
import ribbonnodes.RibbonNode;

/**
 * The Class BackEndAdapter.
 */
public final class BackEndAdapter implements BackEndInterface {

	private static BackEndAdapter ba = null;
	private static Controller controller = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pl.tagc.tagcwebapp.BackEndInterface#getRibbonNodes(int, int,
	 * int)
	 */
	@Override
	public ArrayList<RibbonNode> getRibbonNodes(int minX, int maxX, int zoomLevel) {
		return controller.getRibbonNodes(minX, maxX, zoomLevel);
	}

	@Override
	public List<String> setActiveGenomes(ArrayList<String> activeGenomes) {
		return controller.setActiveGenomes(activeGenomes);
	}

	/**
	 * Get the singleton adapter instance.
	 *
	 * @return The adapter instance.
	 */
	public static BackEndAdapter getInstance() {
		return ba;
	}

	/**
	 * Creates the adapter instance.
	 *
	 * @param controller the controller
	 */
	public static void createInstance(Controller controller) {
		if (ba == null) {
			ba = new BackEndAdapter();
		}
	}
	@Override
	public PhylogeneticTree loadPhylogeneticTree(int treeId) {
		// TODO Auto-generated method stub
		return null;
	}
}
