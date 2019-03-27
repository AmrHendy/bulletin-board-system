package ssh;

import com.jcraft.jsch.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class SSHHandler {

    public void execCommand(String userName, String ip, int port, String password, String command) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSch.setConfig("StrictHostKeyChecking", "no");
                    JSch.setConfig("kex", "diffie-hellman-group1-sha1,diffie-hellman-group14-sha1,diffie-hellman-group-exchange-sha1,diffie-hellman-group-exchange-sha256");

                    JSch jsch = new JSch();
                    Session session = jsch.getSession(userName, ip);
                    session.setPassword(password);
                    session.connect();

                    Channel channel = session.openChannel("shell");
                    channel.connect();

                    OutputStream output = channel.getOutputStream();
                    PrintStream printStream = new PrintStream(output, true);
                    InputStream commandOutput = channel.getInputStream();

                    printStream.println(command);

                    byte[] temp = new byte[1024];
                    while (true) {
                        while (commandOutput.available() > 0) {
                            int readByte = commandOutput.read(temp, 0, 1024);
                            if (readByte < 0) {
                                break;
                            }
                            System.out.print(new String(temp, 0, readByte));
                        }
                        if (channel.isClosed()) {
                            if (commandOutput.available() > 0) {
                                continue;
                            }
                            System.out.println("exit-status: " + channel.getExitStatus());
                            break;
                        }
                    }
                    
                    channel.disconnect();
                    session.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}