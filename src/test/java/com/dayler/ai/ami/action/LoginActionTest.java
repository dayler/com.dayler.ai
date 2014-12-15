/**
 *
 */
package com.dayler.ai.ami.action;

import org.junit.*;

import com.dayler.ai.ami.action.LoginAction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author asalazar
 */
public class LoginActionTest {

    private static final String TEST_SECRET = "secret";
    private static final String TEST_USERNAME = "manager";
    private static final String TEST_ACTIONID = "123";

    private LoginAction loginAction;

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
        loginAction = new LoginAction(TEST_USERNAME, TEST_SECRET);
        loginAction.setActionId(TEST_ACTIONID);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void toCommand() {
        StringBuilder rawCmd = loginAction.toCommand();
        assertNotNull("Command is null", rawCmd);

        String testCmd = "Action:Login\r\n"
                + "ActionID:123\r\n"
                + "Username:manager\r\n"
                + "Secret:secret\r\n"
                + "\r\n";
        System.out.println(testCmd);
        System.out.println("***");
        System.out.println(rawCmd.toString());
        System.out.println("***");

        assertEquals("Command is not the expected.", testCmd, rawCmd.toString());
    }

}
