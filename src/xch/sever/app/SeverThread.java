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
        while (true) {   //死循环不断与客户端进行通讯（其实就是交换数据）
		    //s = getString();
		    //System.out.println("服务器接收到");
		    try {
		    	 socket.sendUrgentData(0);   //这句代码用来判断客户端是否断开连接,如果断开了连接就跳出死循环释放资源
		    	 in = socket.getInputStream();
		    	 byte[] bytes = new byte[1024];
		    	 int len = in.read(bytes);
		         String s = new String(bytes,0,len,"utf8");
		         System.out.println("客户端发过来的内容"+s);
		         String[] contentStrings = s.split("\\*\\*\\*");  //以三个*号切割客户端发来的字符串
		         switch (contentStrings[0]) {
				case "insert": //如果是执行插入命令
					dbConnector = new DbConnector();  //有客户端接入时就连接数据库
					response = dbConnector.insertStudent(contentStrings)+"****##end";
					dbConnector.close(); //关闭数据库连接
					out = socket.getOutputStream();
					out.write(response.getBytes("utf8"));//向客户端返回是否插入成功
					//socket.shutdownOutput();
				    System.out.print("插入数据:");
					System.out.println(String.valueOf(response));
					break;
				case"selectall":
				dbConnector = new DbConnector();  //有客户端接入时就连接数据库
				response = dbConnector.selectAll();
				dbConnector.close();
		         out = socket.getOutputStream();
		         out.write(response.getBytes());
		         //socket.shutdownOutput();   //不能通过关闭输出流的方式让客户端不阻塞在read()方法，因为关了就不能进行连续的通讯，在回发的字符串中加入结束标记为来让客户端跳出循环
		         System.out.println("向客户端返回数据成功!!!");
		         break;
		         case "delete":
		        	 dbConnector = new DbConnector();
		        	 response = dbConnector.delete(contentStrings)+"****##end";
		        	 dbConnector.close();
		        	 out = socket.getOutputStream();
		        	 out.write(response.getBytes());
		        	 System.out.println("删除数据"+response);
		        	 break;
		         case "update":
		        	 dbConnector = new DbConnector();
		        	 response = dbConnector.updata(contentStrings)+"****##end";
		        	 dbConnector.close();
		        	 out = socket.getOutputStream();
		        	 out.write(response.getBytes());
		        	 System.out.println("更新数据"+response);
		        	 break;
		         case "select":
		        	 dbConnector = new DbConnector();
		        	 response =dbConnector.select(contentStrings);
		        	 dbConnector.close();
		        	 out = socket.getOutputStream();
		        	 out.write(response.getBytes("utf8"));
		        	 System.out.println("查询数据："+response);
		        	 break;
		        case"getclassdata": //获取班级数据
		        	 dbConnector = new DbConnector();
		        	 response = dbConnector.getClassData();
		        	 dbConnector.close();
		        	 out = socket.getOutputStream();
		        	 out.write(response.getBytes("utf8"));
		        	 System.out.println("班级数据是："+response);
		        	 break;
		        case"validateAdmin":
		        	 dbConnector = new DbConnector();
		        	 response = dbConnector.validateAdmin(contentStrings);
		        	 dbConnector.close();
		        	 out = socket.getOutputStream();
		        	 out.write(response.getBytes("utf8"));
		        	 System.out.println("班级管理员登录："+response);
		        	break;
		        case "validateSuperAdmin":
		        	 dbConnector = new DbConnector();
		        	 response =dbConnector.validateSuperAdmin(contentStrings);
		        	 dbConnector.close();
		        	 out = socket.getOutputStream();
		        	 out.write(response.getBytes("utf8"));
		        	 System.out.println("超级管理员登录"+response);
		             break;
		        case"addClass":
		        	dbConnector = new DbConnector();
		        	response = dbConnector.addClass(contentStrings);
		        	dbConnector.close();
		        	out = socket.getOutputStream();
		        	out.write(response.getBytes("utf8"));
		        	System.out.println("添加班级"+response);
		        	break;
		        case"getFunds":
		        	dbConnector = new DbConnector();
		        	response = dbConnector.getFunds(contentStrings);
		        	dbConnector.close();
		        	out = socket.getOutputStream();
		        	out.write(response.getBytes("utf8"));
		        	System.out.println("班费信息:"+response);
		        	break;
		        case"updateFunds":
		        	dbConnector = new DbConnector();
		        	response = dbConnector.updateFunds(contentStrings);
		        	dbConnector.close();
		        	out = socket.getOutputStream();
		        	out.write(response.getBytes("utf8"));
		        	System.out.println("修改班费:"+response);
		        	break;
		        case"addFunds":
		        	dbConnector = new DbConnector();
			        response = dbConnector.updateFunds(contentStrings);
			        dbConnector.close();
			        out = socket.getOutputStream();
			        out.write(response.getBytes("utf8"));
			        System.out.println("添加班费详情:"+response);
		        	break;
		        case"deleteFunds":
		        	dbConnector = new DbConnector();
			        response = dbConnector.updateFunds(contentStrings);
			        dbConnector.close();
			        out = socket.getOutputStream();
			        out.write(response.getBytes("utf8"));
			        System.out.println("删除一条经费记录:"+response);
		        	break;
		        case"apply":
		        	dbConnector = new DbConnector();
			        response = dbConnector.apply(contentStrings);
			        dbConnector.close();
			        out = socket.getOutputStream();
			        //out.write(response.getBytes("utf8"));
			        System.out.println("申请成为班级管理员:"+response);
		        	break;
		        case"exmaine":
		        	dbConnector = new DbConnector();
			        response = dbConnector.exmaine(contentStrings);
			        dbConnector.close();
			        out = socket.getOutputStream();
			        out.write(response.getBytes("utf8"));
			        System.out.println("返回申请学生名单:"+response);	
		        	break;
		        case"isPass":
		        	dbConnector = new DbConnector();
			        response = dbConnector.isPass(contentStrings);
			        dbConnector.close();
			        out = socket.getOutputStream();
			        out.write(response.getBytes("utf8"));
			        System.out.println("班级管理员授权:"+response);	
		        	break;
		        case"getAdmin":
		        	dbConnector = new DbConnector();
			        response = dbConnector.getAdmin();
			        dbConnector.close();
			        out = socket.getOutputStream();
			        out.write(response.getBytes("utf8"));
			        System.out.println("管理员列表:"+response);	
		        	break;
		        case"deleteAdmin":
		        	dbConnector = new DbConnector();
			        response = dbConnector.deleteAdmin(contentStrings);
			        dbConnector.close();
			        out = socket.getOutputStream();
			        out.write(response.getBytes("utf8"));
			        System.out.println("删除管理员:"+response);	
		        	break;
				default:
					break;
				}
		 
			} catch (Exception e) {
				try {
					in.close();
					socket.close();  //当客户端主动断开连接时关闭socket释放资源
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				System.out.print("某客户端主动断开连接");
				break;   //如果抛出了异常，那么就是某客户端断开连接了  跳出无限循环释放资源
			}
		   
		    

		    
		    /*
		    if (s.equals("退出群聊"))
		    {
		        System.out.println(socket.getPort()+"退出群聊");
		        in.close();
		        socketList.remove(socket);                               //这一段是当客户端退出时，移除相应的socket对象
		        socket.close();
		        break;
		    }
		    String name =socket.getPort()+"说:"+s;
		    
		   
		    ***********************
		    *
		    *
		    File file = new File("E:/AAA/"+"客户端发来的数据.txt");
		    if (!file.exists())
		    {
		        File file1 = new File(file.getParent());
		        file1.mkdir();
		    }
		    long l = file.length();
		    RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");  // 这一段代码是将收到的内容保存到硬盘中，但是老师要求把数据存到数据库中
		    randomAccessFile.seek(l);
		    randomAccessFile.write(s.getBytes());
		    randomAccessFile.close();
		    System.out.println("服务器接收到" + socket.getPort() + "发来的信息：" + s);
		    
		    ****************************
		    
		    
		    
		    for (int i = 0;i<socketList.size();i++)
		    {
		    	
		    	try
		    	{
		    	socketList.get(i).getOutputStream().write(name.getBytes());       //for循环是群聊回复
		    	}
		    	catch(SocketException e)
		    	{
		    		socketList.remove(socketList.get(i));
		    	}
		  
		    }
		    
		    */
		    
		}
    }
    
    //打开数据库连接
    private DbConnector openDbConnector() {
    	return new DbConnector();
		
	}
}
