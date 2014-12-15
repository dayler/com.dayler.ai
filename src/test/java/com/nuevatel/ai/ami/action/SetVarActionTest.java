package com.nuevatel.ai.ami.action;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SetVarActionTest {

    private SetVarAction setVarAction;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // No op
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // No op
    }

    @Before
    public void setUp() throws Exception {
        setVarAction = new SetVarAction("123", "my_channel", "my_var", "value");
    }

    @After
    public void tearDown() throws Exception {
        setVarAction = null;
    }

    @Test
    public void testToCommand() {
        StringBuilder rawCmd = setVarAction.toCommand();
        assertNotNull("Output command is null.", rawCmd);

        String testCmd = "Action:SetVar\r\n"
                + "ActionID:123\r\n"
                + "Channel:my_channel\r\n"
                + "Variable:my_var\r\n"
                + "Value:value\r\n"
                + "\r\n";

        assertEquals("Command body is not the expected", testCmd, rawCmd.toString());
    }

}
