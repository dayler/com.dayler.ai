/**
 * 
 */
package com.nuevatel.ai.ami.event;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dayler
 *
 */
public class NewStateEventTest {

    private NewStateEvent event;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        event = new NewStateEvent();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        event = null;
    }

    @Test
    public void testToString() {
        assertNotNull("No toString method", event.toString());
    }

}
