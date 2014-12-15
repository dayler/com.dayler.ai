package com.nuevatel.ai.ami.event;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AmiDiscardSessionEventTest {

    private AmiDiscardSessionEvent event;

    @Before
    public void setUp() throws Exception {
        event = new AmiDiscardSessionEvent();
    }

    @After
    public void tearDown() throws Exception {
        event = null;
    }

    @Test
    public void testToString() {
        assertNotNull("No toString method", event.toString());
    }

}
