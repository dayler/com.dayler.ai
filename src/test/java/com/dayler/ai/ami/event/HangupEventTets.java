/**
 * 
 */
package com.dayler.ai.ami.event;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dayler.ai.ami.event.HangupEvent;

/**
 * @author dayler
 *
 */
public class HangupEventTets {

    private HangupEvent event;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        event = new HangupEvent();
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
        assertNotNull("No ToString event", event.toString());
    }

}
