package xch.sever.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


/**
 * Created by Administrator on 2018/6/12.
 */

public class SocketSever {

    public SocketSever() {
        //List<Socket> socketList = new ArrayList<>();
        InputStream in = null;
        OutputStream out = null;
        Socket socket = null;
        try {
            ServerSocket serverSocket = new ServerSocket(6666);  //监听6666端口号
            while (true) {
                System.out.println("服务器等待客户端连接...");
                socket = serverSocket.accept();  //当没有客户端连接时会一直阻塞在这一步
                //socketList.add(socket);
                System.out.println("连上的客户端IP:" + socket.getLocalAddress().getHostAddress() + "，  端口号：" + socket.getPort());
                //new SeverThread(socket, socketList).start(); //这里传list是用来作群聊回复的
                new SeverThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] s) {
        new SocketSever();
    }

}

