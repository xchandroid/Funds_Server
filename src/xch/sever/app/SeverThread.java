package xch.sever.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

/**
 * Created by Administrator on 2018/6/12.
 */

public class SeverThread extends Thread {
    private Socket socket;
    private List<Socket> socketList;
    private InputStream in = null;
    private OutputStream out =null;
    private String sss = "17722875627";
    private DbConnector dbConnector;
    private String response="";
    public SeverThread(Socket socket)
    {
        this.socket = socket;
        //this.socketList = socketList;
    }
    @Override
    public void run() {
        while (true) {   //��ѭ��������ͻ��˽���ͨѶ����ʵ���ǽ������ݣ�
		    //s = getString();
		    //System.out.println("���������յ�");
		    try {
		    	 socket.sendUrgentData(0);   //�����������жϿͻ����Ƿ�Ͽ�����,����Ͽ������Ӿ�������ѭ���ͷ���Դ
		    	 in = socket.getInputStream();
		    	 byte[] bytes = new byte[1024];
		    	 int len = in.read(bytes);
		         String s = new String(bytes,0,len,"utf8");
		         System.out.println("�ͻ��˷�����������"+s);
		         String[] contentStrings = s.split("\\*\\*\\*");  //������*���и�ͻ��˷������ַ���
		         switch (contentStrings[0]) {
				case "insert": //�����ִ�в�������
					dbConnector = new DbConnector();  //�пͻ��˽���ʱ���������ݿ�
					response = dbConnector.insertStudent(contentStrings)+"****##end";
					dbConnector.close(); //�ر����ݿ�����
					out = socket.getOutputStream();
					out.write(response.getBytes("utf8"));//��ͻ��˷����Ƿ����ɹ�
					//socket.shutdownOutput();
				    System.out.print("��������:");
					System.out.println(String.valueOf(response));
					break;
				case"selectall":
				dbConnector = new DbConnector();  //�пͻ��˽���ʱ���������ݿ�
				response = dbConnector.selectAll();
				dbConnector.close();
		         out = socket.getOutputStream();
		         out.write(response.getBytes());
		         //socket.shutdownOutput();   //����ͨ���ر�������ķ�ʽ�ÿͻ��˲�������read()��������Ϊ���˾Ͳ��ܽ���������ͨѶ���ڻط����ַ����м���������Ϊ���ÿͻ�������ѭ��
		         System.out.println("��ͻ��˷������ݳɹ�!!!");
		         break;
		         case "delete":
		        	 dbConnector = new DbConnector();
		        	 response = dbConnector.delete(contentStrings)+"****##end";
		        	 dbConnector.close();
		        	 out = socket.getOutputStream();
		        	 out.write(response.getBytes());
		        	 System.out.println("ɾ������"+response);
		        	 break;
		         case "update":
		        	 dbConnector = new DbConnector();
		        	 response = dbConnector.updata(contentStrings)+"****##end";
		        	 dbConnector.close();
		        	 out = socket.getOutputStream();
		        	 out.write(response.getBytes());
		        	 System.out.println("��������"+response);
		        	 break;
		         case "select":
		        	 dbConnector = new DbConnector();
		        	 response =dbConnector.select(contentStrings);
		        	 dbConnector.close();
		        	 out = socket.getOutputStream();
		        	 out.write(response.getBytes("utf8"));
		        	 System.out.println("��ѯ���ݣ�"+response);
		        	 break;
		        case"getclassdata": //��ȡ�༶����
		        	 dbConnector = new DbConnector();
		        	 response = dbConnector.getClassData();
		        	 dbConnector.close();
		        	 out = socket.getOutputStream();
		        	 out.write(response.getBytes("utf8"));
		        	 System.out.println("�༶�����ǣ�"+response);
		        	 break;
		        case"validateAdmin":
		        	 dbConnector = new DbConnector();
		        	 response = dbConnector.validateAdmin(contentStrings);
		        	 dbConnector.close();
		        	 out = socket.getOutputStream();
		        	 out.write(response.getBytes("utf8"));
		        	 System.out.println("�༶����Ա��¼��"+response);
		        	break;
		        case "validateSuperAdmin":
		        	 dbConnector = new DbConnector();
		        	 response =dbConnector.validateSuperAdmin(contentStrings);
		        	 dbConnector.close();
		        	 out = socket.getOutputStream();
		        	 out.write(response.getBytes("utf8"));
		        	 System.out.println("��������Ա��¼"+response);
		             break;
		        case"addClass":
		        	dbConnector = new DbConnector();
		        	response = dbConnector.addClass(contentStrings);
		        	dbConnector.close();
		        	out = socket.getOutputStream();
		        	out.write(response.getBytes("utf8"));
		        	System.out.println("��Ӱ༶"+response);
		        	break;
		        case"getFunds":
		        	dbConnector = new DbConnector();
		        	response = dbConnector.getFunds(contentStrings);
		        	dbConnector.close();
		        	out = socket.getOutputStream();
		        	out.write(response.getBytes("utf8"));
		        	System.out.println("�����Ϣ:"+response);
		        	break;
		        case"updateFunds":
		        	dbConnector = new DbConnector();
		        	response = dbConnector.updateFunds(contentStrings);
		        	dbConnector.close();
		        	out = socket.getOutputStream();
		        	out.write(response.getBytes("utf8"));
		        	System.out.println("�޸İ��:"+response);
		        	break;
		        case"addFunds":
		        	dbConnector = new DbConnector();
			        response = dbConnector.updateFunds(contentStrings);
			        dbConnector.close();
			        out = socket.getOutputStream();
			        out.write(response.getBytes("utf8"));
			        System.out.println("��Ӱ������:"+response);
		        	break;
		        case"deleteFunds":
		        	dbConnector = new DbConnector();
			        response = dbConnector.updateFunds(contentStrings);
			        dbConnector.close();
			        out = socket.getOutputStream();
			        out.write(response.getBytes("utf8"));
			        System.out.println("ɾ��һ�����Ѽ�¼:"+response);
		        	break;
		        case"apply":
		        	dbConnector = new DbConnector();
			        response = dbConnector.apply(contentStrings);
			        dbConnector.close();
			        out = socket.getOutputStream();
			        //out.write(response.getBytes("utf8"));
			        System.out.println("�����Ϊ�༶����Ա:"+response);
		        	break;
		        case"exmaine":
		        	dbConnector = new DbConnector();
			        response = dbConnector.exmaine(contentStrings);
			        dbConnector.close();
			        out = socket.getOutputStream();
			        out.write(response.getBytes("utf8"));
			        System.out.println("��������ѧ������:"+response);	
		        	break;
		        case"isPass":
		        	dbConnector = new DbConnector();
			        response = dbConnector.isPass(contentStrings);
			        dbConnector.close();
			        out = socket.getOutputStream();
			        out.write(response.getBytes("utf8"));
			        System.out.println("�༶����Ա��Ȩ:"+response);	
		        	break;
		        case"getAdmin":
		        	dbConnector = new DbConnector();
			        response = dbConnector.getAdmin();
			        dbConnector.close();
			        out = socket.getOutputStream();
			        out.write(response.getBytes("utf8"));
			        System.out.println("����Ա�б�:"+response);	
		        	break;
		        case"deleteAdmin":
		        	dbConnector = new DbConnector();
			        response = dbConnector.deleteAdmin(contentStrings);
			        dbConnector.close();
			        out = socket.getOutputStream();
			        out.write(response.getBytes("utf8"));
			        System.out.println("ɾ������Ա:"+response);	
		        	break;
				default:
					break;
				}
		 
			} catch (Exception e) {
				try {
					in.close();
					socket.close();  //���ͻ��������Ͽ�����ʱ�ر�socket�ͷ���Դ
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
				System.out.print("ĳ�ͻ��������Ͽ�����");
				break;   //����׳����쳣����ô����ĳ�ͻ��˶Ͽ�������  ��������ѭ���ͷ���Դ
			}
		   
		    

		    
		    /*
		    if (s.equals("�˳�Ⱥ��"))
		    {
		        System.out.println(socket.getPort()+"�˳�Ⱥ��");
		        in.close();
		        socketList.remove(socket);                               //��һ���ǵ��ͻ����˳�ʱ���Ƴ���Ӧ��socket����
		        socket.close();
		        break;
		    }
		    String name =socket.getPort()+"˵:"+s;
		    
		   
		    ***********************
		    *
		    *
		    File file = new File("E:/AAA/"+"�ͻ��˷���������.txt");
		    if (!file.exists())
		    {
		        File file1 = new File(file.getParent());
		        file1.mkdir();
		    }
		    long l = file.length();
		    RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");  // ��һ�δ����ǽ��յ������ݱ��浽Ӳ���У�������ʦҪ������ݴ浽���ݿ���
		    randomAccessFile.seek(l);
		    randomAccessFile.write(s.getBytes());
		    randomAccessFile.close();
		    System.out.println("���������յ�" + socket.getPort() + "��������Ϣ��" + s);
		    
		    ****************************
		    
		    
		    
		    for (int i = 0;i<socketList.size();i++)
		    {
		    	
		    	try
		    	{
		    	socketList.get(i).getOutputStream().write(name.getBytes());       //forѭ����Ⱥ�Ļظ�
		    	}
		    	catch(SocketException e)
		    	{
		    		socketList.remove(socketList.get(i));
		    	}
		  
		    }
		    
		    */
		    
		}
    }
    
    //�����ݿ�����
    private DbConnector openDbConnector() {
    	return new DbConnector();
		
	}
}
