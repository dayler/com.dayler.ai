package com.dayler.ai.ami.event;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dayler.ai.ami.event.AmiCreateSessionEvent;

public class AmiCreateSessionEventTest {

    private AmiCreateSessionEvent event;

    @Before
    public void setUp() {
        event = new AmiCreateSessionEvent();
    }

    @After
    public void tearDown() {
        event = null;
    }

    @Test
    public void testToString() {
        assertNotNull("No toString method.", event.toString());
    }

}
