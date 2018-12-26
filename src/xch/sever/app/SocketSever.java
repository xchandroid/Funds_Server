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
            ServerSocket serverSocket = new ServerSocket(6666);  //����6666�˿ں�
            while (true) {
                System.out.println("�������ȴ��ͻ�������...");
                socket = serverSocket.accept();  //��û�пͻ�������ʱ��һֱ��������һ��
                //socketList.add(socket);
                System.out.println("���ϵĿͻ���IP:" + socket.getLocalAddress().getHostAddress() + "��  �˿ںţ�" + socket.getPort());
                //new SeverThread(socket, socketList).start(); //���ﴫlist��������Ⱥ�Ļظ���
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

