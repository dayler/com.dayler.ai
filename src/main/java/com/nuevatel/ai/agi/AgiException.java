/*
 * AgiException.java
 */
package com.nuevatel.ai.agi;

/**
 * <p>The AgiException class.</p>
 * <p>Nuevatel PCS de Bolivia S.A. (c) 2013</p>
 *
 * @author Eduardo Marin
 * @version 1.0
 * @since 1.6
 */
public class AgiException extends Exception {

    private static final long serialVersionUID = 20140804L;

    private String responseCode;

    private String response;

    /**
     * Creates a new instance of AgiException.
     *
     * @param message String
     */
    public AgiException(String message) {
        super(message);
    }

    public AgiException(String message, String[] retArray) {
        super(message);

        if (retArray != null) {
            responseCode = retArray.length > 0 ? retArray[0] : null;
            response = retArray.length > 1 ? retArray[1] : null;
        }
    }

    /**
     * @return the responseCode
     */
    public String getResponseCode() {
        return responseCode;
    }

    /**
     * @return the response
     */
    public String getResponse() {
        return response;
    }
}
