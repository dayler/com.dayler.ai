/**
 * 
 */
package com.nuevatel.ai.ami.action;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author asalazar
 *
 */
public class AgiActionTest {

    private AgiAction acction;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        acction = new AgiAction("actionId", "channel", "command", "commandId");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        acction = null;
    }

    @Test
    public void toCommand() {
        StringBuilder rawCmd = acction.toCommand();
        assertNotNull("Command is null", rawCmd);

        String testCmd = "Action:AGI\r\n"
                + "ActionID:actionId\r\n"
                + "Channel:channel\r\n"
                + "Command:command\r\n"
                + "CommandID:commandId\r\n"
                + "\r\n";

        System.out.println(rawCmd.toString());
        assertEquals("Command is not the expected.", testCmd, rawCmd.toString());
    }
}
