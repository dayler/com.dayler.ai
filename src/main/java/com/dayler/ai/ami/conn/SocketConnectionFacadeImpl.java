/**
 *
 */
package com.dayler.ai.ami.conn;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Default implementation of the SocketConnectionFacade interface using java.io.
 *
 * @author srt, asalazar
 * @version $Id: SocketConnectionFacadeImpl.java 1377 2009-10-17 03:24:49Z srt $
 */
public class SocketConnectionFacadeImpl implements SocketConnectionFacade {
    static final Pattern CRNL_PATTERN = Pattern.compile("\r\n");

    static final Pattern NL_PATTERN = Pattern.compile("\n");

    private Socket socket;

    private Scanner scanner;

    private BufferedWriter writer;

    /**
     * Creates a new instance for use with the Manager API that uses CRNL ("\r\n") as line delimiter.
     *
     * @param host        the foreign host to connect to.
     * @param port        the foreign port to connect to.
     * @param ssl         <code>true</code> to use SSL, <code>false</code> otherwise.
     * @param timeout     0 incidcates default
     * @param readTimeout see {@link Socket#setSoTimeout(int)}
     * @throws IOException if the connection cannot be established.
     */
    public SocketConnectionFacadeImpl(String host, int port, boolean ssl, int timeout, int readTimeout) throws IOException {
        Socket socket;

        if (ssl) {
            socket = SSLSocketFactory.getDefault().createSocket();
        } else {
            socket = SocketFactory.getDefault().createSocket();
        }

        socket.setSoTimeout(readTimeout);
        socket.connect(new InetSocketAddress(host, port), timeout);

        initialize(socket, CRNL_PATTERN);
    }

    /**
     * Creates a new instance for use with FastAGI that uses NL ("\n") as line delimiter.
     *
     * @param socket the underlying socket.
     * @throws IOException if the connection cannot be initialized.
     */
    SocketConnectionFacadeImpl(Socket socket) throws IOException {
        initialize(socket, NL_PATTERN);
    }

    private void initialize(Socket socket, Pattern pattern) throws IOException {
        this.socket = socket;

        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        this.scanner = new Scanner(reader);
        this.scanner.useDelimiter(pattern);
        this.writer = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    public String readLine() throws IOException {
        String line;
        try {
            line = scanner.next();
        } catch (IllegalStateException e) {
            if (scanner.ioException() != null) {
                throw scanner.ioException();
            } else {
                // throw new IOException("No more lines available", e); // JDK6
                throw new IOException("No more lines available: " + e.getMessage());
            }
        } catch (NoSuchElementException e) {
            if (scanner.ioException() != null) {
                throw scanner.ioException();
            } else {
                // throw new IOException("No more lines available", e); // JDK6
                throw new IOException("No more lines available: " + e.getMessage());
            }
        }

        return line;
    }

    public void write(String s) throws IOException {
        writer.write(s);
    }

    public void flush() throws IOException {
        writer.flush();
    }

    public void close() throws IOException {
        socket.close();
        scanner.close();
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    public InetAddress getLocalAddress() {
        return socket.getLocalAddress();
    }

    public int getLocalPort() {
        return socket.getLocalPort();
    }

    public InetAddress getRemoteAddress() {
        return socket.getInetAddress();
    }

    public int getRemotePort() {
        return socket.getPort();
    }
}
