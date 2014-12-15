package com.dayler.ai.ami.service;

import org.junit.*;

import com.dayler.ai.ami.service.AmiService;
import com.dayler.ai.ami.service.AmiServiceFactory;

import static org.junit.Assert.assertNotNull;

public class AmiServiceFactoryTest {

    private static final int TEST_PING_TIMEOUT = 60000;
    private static final int TEST_LISTENER_COUNT = 4;
    private static final boolean TEST_USE_SSL = false;
    private static final java.lang.String TEST_SECRET = "secret";
    private static final java.lang.String TEST_USERNAME = "manager";
    private static final int TEST_PORT = 5038;
    private static final java.lang.String TEST_HOTNAME = "10.40.99.88";

    private AmiServiceFactory factory;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        factory = new AmiServiceFactory();
        factory.setHostName(TEST_HOTNAME)
                .setPort(TEST_PORT)
                .setUserName(TEST_USERNAME)
                .setSecret(TEST_SECRET)
                .setUseSSL(TEST_USE_SSL)
                .setListenerCount(TEST_LISTENER_COUNT)
                .setPingTimeout(TEST_PING_TIMEOUT);
    }

    @After
    public void tearDown() throws Exception {
        factory = null;
    }

    @Test
    public void getService() {
        AmiService service = factory.getService();
        assertNotNull("Service is null.", service);
    }

}
