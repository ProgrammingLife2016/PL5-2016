package com.pl.tagc.tagcwebapp;

import org.apache.commons.collections.map.HashedMap;

import javax.xml.bind.annotation.XmlRootElement;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * The Class NodeListObject.
 */
@XmlRootElement
public class MetaDataColorObject {

	/** The id. */
	@SuppressWarnings("unused")
	private String id = "test";

	/** The c list. */
	private Map<String, Color> list;

	/**
	 * Instantiates a new node list object.
	 */
	public MetaDataColorObject() {
	}

	/**
	 * Instantiates a new node list object.
	 *
	 * @param list the c list
	 */
	public MetaDataColorObject(Map<String, Color> list) {
		this.list = list;
	}

	/**
	 * Gets the c list, with color converted to a String.
	 *
	 * @return the c list
	 */
	public Map<String, String> getlist() {
		Map<String, String> out = new HashMap<>();
		for(String id : list.keySet()) {
			String hex = Integer.toHexString(list.get(id).getRGB() & 0xffffff);
			while (hex.length() < 6) {
				hex = "0" + hex;
			}
			out.put(id, hex);
		}
		return out;
	}

	/**
	 * Sets the c list.
	 *
	 * @param list the new c list
	 */
	public void setlist(Map<String, Color> list) {
		this.list = list;
	};
}