/**
 * 
 */
package com.nuevatel.ai.ami.event;

import static org.junit.Assert.*;

import java.util.ArrayDeque;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.nuevatel.ai.Constants.*;

/**
 * @author asalazar
 *
 */
public class AmiResponseFactoryTest {

    private static final String DIAL_EVENT_DIALSTRING_50400009 = "Dialstring: 50400009";
    private static final String DIAL_EVENT_DEST_UNIQUE_ID_NUEVATEL_1405538947_70 = "DestUniqueID: nuevatel-1405538947.70";
    private static final String DIAL_EVENT_UNIQUE_ID_NUEVATEL_1405538947_69 = "UniqueID: nuevatel-1405538947.69";
    private static final String DIAL_EVENT_CONNECTED_LINE_NAME_UNKNOWN = "ConnectedLineName: <unknown>";
    private static final String DIAL_EVENT_CONNECTED_LINE_NUM_UNKNOWN = "ConnectedLineNum: <unknown>";
    private static final String DIAL_EVENT_CALLER_ID_NAME_UNKNOWN = "CallerIDName: <unknown>";
    private static final String DIAL_EVENT_CALLER_ID_NUM_50400008 = "CallerIDNum: 50400008";
    private static final String DIAL_EVENT_DESTINATION_SIP_50400009_0000003B = "Destination: SIP/50400009-0000003b";
    private static final String DIAL_EVENT_CHANNEL_SIP_50400008_0000003A = "Channel: SIP/50400008-0000003a";
    private static final String DIAL_EVENT_SUB_EVENT_BEGIN = "SubEvent: Begin";
    private static final String DIAL_EVENT_PRIVILEGE_CALL_ALL = "Privilege: call,all";
    private static final String DIAL_EVENT_EVENT_DIAL = "Event: Dial";

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // No op
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // No op
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // No op
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        // No op
    }

    @Test
    public void buildDialEvent() throws InstantiationException, IllegalAccessException {
        ArrayDeque<String> args = new ArrayDeque<String>();
        args.add(DIAL_EVENT_EVENT_DIAL);
        args.add(DIAL_EVENT_PRIVILEGE_CALL_ALL);
        args.add(DIAL_EVENT_SUB_EVENT_BEGIN);
        args.add(DIAL_EVENT_CHANNEL_SIP_50400008_0000003A);
        args.add(DIAL_EVENT_DESTINATION_SIP_50400009_0000003B);
        args.add(DIAL_EVENT_CALLER_ID_NUM_50400008);
        args.add(DIAL_EVENT_CALLER_ID_NAME_UNKNOWN);
        args.add(DIAL_EVENT_CONNECTED_LINE_NUM_UNKNOWN);
        args.add(DIAL_EVENT_CONNECTED_LINE_NAME_UNKNOWN);
        args.add(DIAL_EVENT_UNIQUE_ID_NUEVATEL_1405538947_69);
        args.add(DIAL_EVENT_DEST_UNIQUE_ID_NUEVATEL_1405538947_70);
        args.add(DIAL_EVENT_DIALSTRING_50400009);
        args.add(CARRIAGE_RETURN);
        AmiEventArgs eventArgs = new AmiEventArgs(args);

        Response respAmiEvent = AmiResponseFactory.build(eventArgs);
        assertNotNull("Response event is null", respAmiEvent);
        assertTrue("Response event is not an instance of DialEvent", respAmiEvent instanceof DialEvent);
        DialEvent dialEvent = (DialEvent) respAmiEvent;
        assertEquals("Dial", dialEvent.getName());
        assertEquals("call,all", dialEvent.getPrivilege());
        assertEquals("Begin", dialEvent.getSubEvent());
        assertEquals("SIP/50400008-0000003a", dialEvent.getChannel());
        assertEquals("SIP/50400009-0000003b", dialEvent.getDestination());
        assertEquals("50400008", dialEvent.getCallerIdNum());
        assertEquals("<unknown>", dialEvent.getCallerIdName());
        assertEquals("<unknown>", dialEvent.getConnectedLineNum());
        assertEquals("<unknown>", dialEvent.getConnectedLineName());
        assertEquals("nuevatel-1405538947.69", dialEvent.getUniqueId());
        assertEquals("nuevatel-1405538947.70", dialEvent.getDestUniqueId());
        assertEquals("50400009", dialEvent.getDialstring());
    }

}
