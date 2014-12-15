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
public class PeerStatusEventTest {

    private PeerStatusEvent event;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        event = new PeerStatusEvent();
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
