package com.pl.tagc.tagcwebapp;

/**
 * Created by Matthijs on 24-4-2016.
 */

import java.util.HashSet;

/**
 * Class that contains the graph nodes.
 */
public class AdaptedStrand {

	/**
	 * Node id.
	 */
	private int id;

	/**
	 * Genomes that contain this node.
	 */
	private HashSet<String> genomes;

	/**
	 * The refrence genome of this node.
	 */
	private String referenceGenome;

	/**
	 * Coordinate of this node in the refr genome.
	 */
	private int referenceCoordinate;

	
	/**
	 * Instantiates a new adapted strand.
	 */
	public AdaptedStrand() { }

	/**
	 * Constructor to create a node.
	 *
	 * @param id
	 *            The node id.
	 * @param sequence
	 *            The sequence of the node.
	 * @param newGenomes
	 *            The genomes id's passing through.
	 * @param referenceGenome
	 *            The reference genome id.
	 * @param referenceCoordinate
	 *            the reference coordinate.
	 */
	public AdaptedStrand(int id, String sequence, HashSet<String> newGenomes, 
			String referenceGenome,	int referenceCoordinate) {
		this.id = id;
		genomes = newGenomes;
		this.referenceGenome = referenceGenome;
		this.referenceCoordinate = referenceCoordinate;
	}

	/**
	 * Instantiates a new strand.
	 *
	 * @param id the id
	 */
	public AdaptedStrand(int id) {
		this.id = id;
	}

	/**
	 * Get the node id.
	 *
	 * @return The node id.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get the genomes passing through the node.
	 *
	 * @return The genomes id's.
	 */
	public HashSet<String> getGenomes() {
		return genomes;
	}

	/**
	 * Get the reference genome.
	 *
	 * @return The reference genome id.
	 */
	public String getReferenceGenome() {
		return referenceGenome;
	}

	/**
	 * Set the reference genome id.
	 *
	 * @param referenceGenome
	 *            The refrence genome id.
	 */
	public void setReferenceGenome(String referenceGenome) {
		this.referenceGenome = referenceGenome;
	}

	/**
	 * Get the reference coordinate.
	 *
	 * @return The reference coordinate.
	 */
	public int getReferenceCoordinate() {
		return referenceCoordinate;
	}

	/**
	 * Set the reference coordinate.
	 *
	 * @param referenceCoordinate
	 *            The reference coordinate.
	 */
	public void setReferenceCoordinate(int referenceCoordinate) {
		this.referenceCoordinate = referenceCoordinate;
	}

	/**
	 * Set the node id.
	 *
	 * @param id
	 *            The node id.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Sets the genomes.
	 *
	 * @param genomes the new genomes
	 */
	public void setGenomes(HashSet<String> genomes) {
		this.genomes = genomes;
		
	} 
	
}
