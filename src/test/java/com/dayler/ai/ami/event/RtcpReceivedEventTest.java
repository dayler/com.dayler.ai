/**
 * 
 */
package com.dayler.ai.ami.event;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dayler.ai.ami.event.RtcpReceivedEvent;

/**
 * @author dayler
 *
 */
public class RtcpReceivedEventTest {

    private RtcpReceivedEvent event;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        event = new RtcpReceivedEvent();
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
