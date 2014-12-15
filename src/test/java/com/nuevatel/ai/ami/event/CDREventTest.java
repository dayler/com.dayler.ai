package com.nuevatel.ai.ami.event;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CDREventTest {

    private CDREvent event;

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
        event = new CDREvent();
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
