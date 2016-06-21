package com.pl.tagc.tagcwebapp;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class BooleanObject.
 */
@XmlRootElement
public class BooleanObject {

    /**
     * The id.
     */
    @SuppressWarnings("unused")
    private String id = "test";

    /**
     * The c bool.
     */
    private Boolean bool;

    /**
     * Instantiates a new boolean object.
     */
    public BooleanObject() {
    }

    /**
     * Instantiates a new boolean object.
     *
     * @param bool the c bool
     */
    public BooleanObject(Boolean bool) {
        this.bool = bool;
    }

    /**
     * Gets the c bool.
     *
     * @return the c bool
     */
    public Boolean getbool() {
        return bool;
    }

    /**
     * Sets the c bool.
     *
     * @param bool the new c bool
     */
    public void setbool(Boolean bool) {
        this.bool = bool;
    }


}