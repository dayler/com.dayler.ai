/**
 *
 */
package com.dayler.ai.ami.service.cache;

import com.dayler.ai.ami.service.cache.SessionCache;
import com.dayler.ai.ami.service.cache.SessionCacheFactory;
import com.dayler.ai.ami.service.context.SessionCtx;
import com.dayler.ai.ami.service.context.SessionCtxImpl;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.dayler.common.exception.OperationException;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author asalazar
 */
public class SessionCacheImplTest {

    private static final String TEST_TMP_VALUE = "value";
    private static final String TMP_KEY = "tmpKey";
    private static final int TEST_EXPIRE_AFTER_ACCESS = 150;
    private static final int TEST_EXPIRE_AFTER_WRITE = 500;
    private static final String TEST_SESSION_4 = "4";
    private static final String TEST_SESSION_3 = "3";
    private static final String TEST_SESSION_2 = "2";
    private static final String TEST_SESSION_1 = "1";
    private SessionCache cache;

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
        cache = new SessionCacheFactory()
                .setConcurrencyLevel(1)
                .setExpireAfterWrite(TEST_EXPIRE_AFTER_WRITE)
                .setExpireAfterAccess(TEST_EXPIRE_AFTER_ACCESS)
                .setCacheLoader(new CacheLoader<String, SessionCtx>() {

                    @Override
                    public SessionCtx load(String key) throws Exception {
                        System.out.println("On created session id: " + key);
                        return new SessionCtxImpl(key);
                    }
                })
                .setRemovalListener(new RemovalListener<String, SessionCtx>() {

                    @Override
                    public void onRemoval(RemovalNotification<String, SessionCtx> notification) {
                        if (notification.wasEvicted()) {
                            System.out.println("On removed session id: " + notification.getKey());
                        }
                    }
                })
                .getCache();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        cache = null;
    }

    @Test
    public void create() {
        assertNotNull("Null cache object", cache);
    }

    /**
     * Test Get session.
     *
     * @throws OperationException
     */
    @Test
    public void getSession() throws OperationException {

        SessionCtx tmpSession = cache.getSession(TEST_SESSION_1);
        assertNotNull("tmp session is null", tmpSession);
        tmpSession.putCtxVar(TMP_KEY, TEST_TMP_VALUE);
        tmpSession = cache.getSession(TEST_SESSION_1);
        assertEquals("Not same session id", TEST_SESSION_1, tmpSession.getSessionID());
        assertEquals("Not same tmp value.", TEST_TMP_VALUE, tmpSession.getContextVar(TMP_KEY));

        tmpSession = cache.getSession(TEST_SESSION_2);
        assertNotNull("tmp session is null", tmpSession);
        tmpSession.putCtxVar(TMP_KEY, TEST_TMP_VALUE);
        tmpSession = cache.getSession(TEST_SESSION_2);
        assertEquals("Not same session id", TEST_SESSION_2, tmpSession.getSessionID());
        assertEquals("Not same tmp value.", TEST_TMP_VALUE, tmpSession.getContextVar(TMP_KEY));

        tmpSession = cache.getSession(TEST_SESSION_2);
        assertNotNull("tmp session is null", tmpSession);
        tmpSession.putCtxVar(TMP_KEY, TEST_TMP_VALUE);
        tmpSession = cache.getSession(TEST_SESSION_2);
        assertEquals("Not same session id", TEST_SESSION_2, tmpSession.getSessionID());
        assertEquals("Not same tmp value.", TEST_TMP_VALUE, tmpSession.getContextVar(TMP_KEY));

        tmpSession = cache.getSession(TEST_SESSION_3);
        assertNotNull("tmp session is null", tmpSession);
        tmpSession.putCtxVar(TMP_KEY, TEST_TMP_VALUE);
        tmpSession = cache.getSession(TEST_SESSION_3);
        assertEquals("Not same session id", TEST_SESSION_3, tmpSession.getSessionID());
        assertEquals("Not same tmp value.", TEST_TMP_VALUE, tmpSession.getContextVar(TMP_KEY));

        tmpSession = cache.getSession(TEST_SESSION_4);
        assertNotNull("tmp session is null", tmpSession);
        tmpSession.putCtxVar(TMP_KEY, TEST_TMP_VALUE);
        tmpSession = cache.getSession(TEST_SESSION_4);
        assertEquals("Not same session id", TEST_SESSION_4, tmpSession.getSessionID());
        assertEquals("Not same tmp value.", TEST_TMP_VALUE, tmpSession.getContextVar(TMP_KEY));
    }

    /**
     * Expire inserted session after X time.
     *
     * @throws OperationException
     * @throws InterruptedException
     */
    @Test
    public void expireAfterWrite() throws OperationException, InterruptedException {
        SessionCtx tmpSession = cache.getSession(TEST_SESSION_1);
        assertNotNull("tmp session is null", tmpSession);
        tmpSession.putCtxVar(TMP_KEY, TEST_TMP_VALUE);
        tmpSession = cache.getSession(TEST_SESSION_1);
        assertEquals("Not same session id", TEST_SESSION_1, tmpSession.getSessionID());
        assertEquals("Not same tmp value.", TEST_TMP_VALUE, tmpSession.getContextVar(TMP_KEY));

        Thread.sleep(TEST_EXPIRE_AFTER_WRITE + 100);

        cache.getSession(TEST_SESSION_1);
        cache.getSession(TEST_SESSION_1);
        cache.getSession(TEST_SESSION_1);

        tmpSession = cache.getSession(TEST_SESSION_1);
        assertNotNull("tmp session is null", tmpSession);
        assertEquals("Not same session id", TEST_SESSION_1, tmpSession.getSessionID());
        assertNull("Not same tmp value.", tmpSession.getContextVar(TMP_KEY));
    }

    @Test
    public void setExpireAfterAccess() throws OperationException, InterruptedException {
        SessionCtx tmpSession = cache.getSession(TEST_SESSION_1);
        assertNotNull("tmp session is null", tmpSession);
        tmpSession.putCtxVar(TMP_KEY, TEST_TMP_VALUE);
        tmpSession = cache.getSession(TEST_SESSION_1);
        assertEquals("Not same session id", TEST_SESSION_1, tmpSession.getSessionID());
        assertEquals("Not same tmp value.", TEST_TMP_VALUE, tmpSession.getContextVar(TMP_KEY));

        Thread.sleep(TEST_EXPIRE_AFTER_ACCESS - 50);

        tmpSession = cache.getSession(TEST_SESSION_1);
        assertEquals("Not same session id", TEST_SESSION_1, tmpSession.getSessionID());
        assertEquals("Not same tmp value.", TEST_TMP_VALUE, tmpSession.getContextVar(TMP_KEY));

        Thread.sleep(TEST_EXPIRE_AFTER_ACCESS + 50);

        tmpSession = cache.getSession(TEST_SESSION_1);
        assertNotNull("tmp session is null", tmpSession);
        assertEquals("Not same session id", TEST_SESSION_1, tmpSession.getSessionID());
        assertNull("Not same tmp value.", tmpSession.getContextVar(TMP_KEY));
    }
}
