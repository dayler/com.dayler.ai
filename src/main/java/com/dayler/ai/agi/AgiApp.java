/*
 * AgiApp.java
 */
package com.dayler.ai.agi;

/**
 * <p>The AgiApp interface.</p>
 * <p>Nuevatel PCS de Bolivia S.A. (c) 2013</p>
 *
 * @author Eduardo Marin
 * @version 1.0
 * @since 1.6
 */
public interface AgiApp {

    String getName();

    /**
     * Runs this.
     *
     * @param vars AgiVars
     * @param cmd  AgiCmdExec
     * @throws Exception
     */
    public void run(AgiVars vars, AgiCmdExec cmd) throws Exception;
}
