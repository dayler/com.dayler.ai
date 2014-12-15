/*
 * AgiConn.java
 */
package com.dayler.ai.agi;

import com.dayler.ai.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>The AgiConn class.</p>
 * <p>Nuevatel PCS de Bolivia S.A. (c) 2013</p>
 *
 * @author Eduardo Marin
 * @version 1.0
 * @since 1.6
 */
class AgiConn implements Runnable, AgiCmdExec {

    /* public constants */
    public static int DEFAULT_TIMEOUT = 120;

    /* constants for state */
    public static final byte OFFLINE = 0;
    public static final byte ONLINE = 1;

    /**
     * The agiAppMap.
     */
    private Map<String, AgiApp> agiAppMap;

    /**
     * The socket.
     */
    private Socket socket;

    /**
     * The state.
     */
    private volatile byte state;

    private boolean readVars;

    private AgiAppThread agiAppThread;

    private volatile boolean wait;

    /**
     * The ret.
     */
    private String ret;

    /**
     * Creates a new instance of AgiConn.
     *
     * @param agiAppMap Map<String, AgiApp>
     * @param socket    Socket
     */
    AgiConn(Map<String, AgiApp> agiAppMap, Socket socket) {
        this.agiAppMap = agiAppMap;
        this.socket = socket;
        state = OFFLINE;
        readVars = false;
        wait = false;
    }

    /**
     * Interrupts this.
     */
    private void interrupt() {
        if (state == ONLINE) {
            wait = false;
            if (agiAppThread != null && !agiAppThread.isInterrupted()) agiAppThread.interrupt();
            try {
                if (socket != null && !socket.isClosed()) socket.close();
            } catch (IOException ioe) {
            }
            state = OFFLINE;
        }
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        try {
            if (socket != null && !socket.isClosed())
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            state = ONLINE;
            List<String> vars = new ArrayList<String>();
            while (socket != null && !socket.isClosed()) {
                String read = reader.readLine();
                if (read != null) {
                    if (!readVars) {
                        vars.add(read);
                        if (read.length() == 0) {
                            readVars = true;
                            AgiVars agiVars = new AgiVars(vars);
                            vars = null;
                            String agiAppClassName = agiVars.getAgiRequest().substring(agiVars.getAgiRequest().lastIndexOf('/') + 1);
                            AgiApp agiApp = agiAppMap.get(agiAppClassName);
                            if (agiApp != null) {
                                Logger.getLogger("com.nuevatel.asterisk.fastagi").logp(Level.FINE, getClass().getName(), "run", agiAppClassName + " AgiApp requested");
                                Thread.currentThread().setName(agiApp.getClass().getName());
                                agiAppThread = new AgiAppThread(agiVars, agiApp);
                                agiAppThread.start();
                            } else throw new UnsupportedOperationException(agiAppClassName + " not found");
                        }
                    } else {
                        if (wait) setRet(read);
                        if (read.compareTo("HANGUP") == 0) break;
                    }
                } else break;
            }
        } catch (SocketException se) {
        } catch (Exception e) {
            Logger.getLogger("com.nuevatel.asterisk.fastagi").logp(Level.WARNING, getClass().getName(), "run", e.getMessage(), e);
        }
        try {
            if (reader != null) reader.close();
        } catch (IOException ioe) {
        }
        interrupt();
    }

    /**
     * Writes the str.
     *
     * @param str String
     * @throws Exception
     */
    private void write(String str) throws Exception {
        if (socket != null && !socket.isClosed()) {
            socket.getOutputStream().write(str.getBytes());
            socket.getOutputStream().flush();
        }
    }

    /**
     * Executes the command.
     *
     * @param cmd String
     * @return String
     * @throws Exception
     */
    private synchronized String execute(String cmd) throws Exception {
        try {
            // ret
            ret = null;
            // wait
            wait = true;
            write(cmd);
            long currentTime = System.currentTimeMillis();
            long timeoutTime = currentTime + DEFAULT_TIMEOUT * 1000;
            while (wait && (currentTime = System.currentTimeMillis()) < timeoutTime) wait(timeoutTime - currentTime);
        } catch (Exception e) {
            throw e;
        } finally {
            wait = false;
        }
        return ret;
    }

    /**
     * Sets the ret.
     *
     * @param ret String
     */
    private synchronized void setRet(String ret) {
        this.ret = ret;
        wait = false;
        notify();
    }

    @Override
    public void answer() throws Exception {
        String tmpRet = execute(ANSWER + NL);
        if (tmpRet != null) {
            String[] retArray = tmpRet.split("( result=)");
            if (isFailure(retArray, Constants.AGI_RESPONSE_SUCCESSFUL)) throw new AgiException(tmpRet);
        }
    }

    @Override
    public String getData(String file, int timeout, int maxDigits) throws Exception {
        String tmpCmd = GET_DATA + " " + file;
        if (timeout > 0) tmpCmd += " " + timeout;
        if (maxDigits > 0) tmpCmd += " " + maxDigits;
        tmpCmd += NL;
        String tmpRet = execute(tmpCmd);
        if (tmpRet != null) {
            String[] retArray = tmpRet.split("( result=)");
            if (isSuccess(retArray)) {
                int tmpIndex = retArray[1].indexOf(' ');
                if (tmpIndex > 0) return retArray[1].substring(0, tmpIndex);
                else return retArray[1];
            } else throw new AgiException(tmpRet);
        } else return "";
    }

    @Override
    public String getData(String file, int timeout) throws Exception {
        return getData(file, timeout, -1);
    }

    @Override
    public String getData(String file) throws Exception {
        return getData(file, -1, -1);
    }

    @Override
    public void hangup() throws Exception {
        String tmpRet = execute(HANGUP + NL);
        if (tmpRet != null) {
            String[] retArray = tmpRet.split("( result=)");
            if (isFailure(retArray, Constants.AGI_RESPONSE_OTHERWISE)) throw new AgiException(tmpRet);
        }
    }

    @Override
    public void noop() throws Exception {
        String tmpRet = execute(NOOP + NL);
        if (tmpRet != null) {
            String[] retArray = tmpRet.split("( result=)");
            if (isFailure(retArray, Constants.AGI_RESPONSE_SUCCESSFUL)) throw new AgiException(tmpRet);
        }
    }

    @Override
    public char streamFile(String file, String escapeDigits) throws Exception {
        String tmpCmd = STREAM_FILE + " " + file + " \"";
        if (escapeDigits != null && escapeDigits.length() > 0) tmpCmd += escapeDigits;
        tmpCmd += "\"" + NL;
        String tmpRet = execute(tmpCmd);
        if (tmpRet != null) {
            String[] retArray = tmpRet.split("( result=)");
            if (isSuccess(retArray)) {
                int tmpIndex = retArray[1].indexOf(' ');
                if (tmpIndex > 0) return (char) Integer.parseInt(retArray[1].substring(0, tmpIndex));
                else return (char) Integer.parseInt(retArray[1]);
            } else {
                throw new AgiException(tmpRet, retArray);
            }
        } else return 0;
    }

    @Override
    public void streamFile(String file) throws Exception {
        streamFile(file, null);
    }

    @Override
    public char waitForDigit(int timeout) throws Exception {
        String tmpRet = execute(WAIT_FOR_DIGIT + " " + timeout + NL);
        if (tmpRet != null) {
            String[] retArray = tmpRet.split("( result=)");
            if (isSuccess(retArray)) return (char) Integer.parseInt(retArray[1]);
            else throw new AgiException(tmpRet);
        } else return 0;
    }

    @Override
    public void dial(String sipExtension, String options, String callerId)
            throws Exception {
//        exec("DIAL", sipExtension + "," + options + "," + "f(" + callerId + ")");
        exec("DIAL", sipExtension + "," + options + "," + "f(" + callerId + "),g");
        // exten => 1234,n,DIAL(SIP/someguy,10,g)
    }

    @Override
    public String getVariable(String variable)
            throws Exception {
        String value = null;
        String tmpRet = execute(GET_VARIABLE + " " + variable + "\n");
        if (tmpRet != null) {
            String[] retArray = tmpRet.split("( result=)");
            if (isSuccess(retArray)) {
                String[] varValue = retArray[1].split("[\\(\\)]");
                if (varValue.length == 2) value = varValue[1];
            } else throw new AgiException(tmpRet);
        }
        return value;
    }

    @Override
    public void voiceMail(String mailbox) throws Exception {
        exec(VOICEMAIL, mailbox);
    }

    @Override
    public void voiceMailMain(String mailbox) throws Exception {
        exec("VOICEMAILMAIN", mailbox);
    }

    @Override
    public void waitSeconds(String seconds) throws Exception {
        exec("WAIT", seconds);
    }

    @Override
    public int setVariable(String variable, String value)
            throws Exception {
        int result = -1;
        String tmpRet = execute(SET_VARIABLE + " " + variable + " " + value + "\n");
        if (tmpRet != null) {
            String[] retArray = tmpRet.split("( result=)");
            if (isSuccess(retArray)) result = Integer.parseInt(retArray[1]);
            else throw new AgiException(tmpRet);
        }
        return result;
    }

    public int exec(String command, String options)
            throws Exception {
        int result = -1;
        String tmpRet = execute(EXEC + " " + command + " " + options + "\n");
        if (tmpRet != null) {
            String[] retArray = tmpRet.split("( result=)");

            if (isSuccess(retArray)) {
                result = Integer.parseInt(retArray[1]);
            } else {
                throw new AgiException(tmpRet, retArray);
            }
        }
        return result;
    }

    private boolean isSuccess(String[] retArray) {
        return (retArray.length == Constants.AGI_RESPONSE_LENGTH) && (retArray[0].compareTo(Constants.AGI_RESPONSE_CODE) == 0) && (!retArray[1].startsWith(Constants.AGI_RESPONSE_FAILURE));
    }

    private boolean isFailure(String[] retArray, String response) {
        return (retArray.length != Constants.AGI_RESPONSE_LENGTH) || (retArray[0].compareTo(Constants.AGI_RESPONSE_CODE) != 0) || (retArray[1].compareTo(response) != 0);
    }

    /**
     * The AgiAppThread class.
     */
    private class AgiAppThread extends Thread {

        /* private variables */
        private AgiVars agiVars;
        private AgiApp agiApp;

        /**
         * Creates a new instance of AgiAppThread.
         *
         * @param agiVars AgiVars
         * @param agiApp  AgiApp
         */
        private AgiAppThread(AgiVars agiVars, AgiApp agiApp) {
            super(agiApp.getClass().getName());
            this.agiVars = agiVars;
            this.agiApp = agiApp;
        }

        @Override
        public void run() {
            try {
                agiApp.run(agiVars, AgiConn.this);
            } catch (InterruptedException ie) {
            } catch (Exception e) {
                Logger.getLogger("com.nuevatel.asterisk.fastagi").logp(Level.WARNING, getClass().getName(), "run", e.getMessage(), e);
            }
            AgiConn.this.interrupt();
        }
    }
}
