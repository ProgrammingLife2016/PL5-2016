package com.pl.tagc.tagcwebapp;

import java.util.logging.Level;
import java.util.logging.Logger;

import controller.Controller;

/**
 * The Class Main.
 */
final class Main {

	/**
	 * Instantiates a new main.
	 */
	private Main() {
	}

	/** T
	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		try {
			@SuppressWarnings("unused")
			Controller controller = new Controller();
			Thread.currentThread().join();
		} catch (InterruptedException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}