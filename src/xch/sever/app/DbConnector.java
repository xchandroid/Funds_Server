package xch.sever.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.PreparedStatement;

public class DbConnector {
	//声明Connection对象
  private Connection con;
    //驱动程序名
  private String driver = "com.mysql.cj.jdbc.Driver";
    //URL指向要访问的数据库名funds
  private String url = "jdbc:mysql://localhost:3306/funds?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true";
    //MySQL配置时的用户名
  private String user = "root";
    //MySQL配置时的密码
  private String password = "20130901xch";
//  public static void main(String[] args) {
//	DbConnector dbConnector = new DbConnector();
//	dbConnector.insertStudent(new String[] {"1504405243","20130901xch","谢昌宏","男","1208718872","18077760737","1"});
//}
   public DbConnector()
    {
   
    try {
        //加载驱动程序
        Class.forName(driver);
        //1.getConnection()方法，连接MySQL数据库！！
        con = (Connection) DriverManager.getConnection(url,user,password);
        if(!con.isClosed())
            System.out.println("成功连接数据库!");
        
    } catch(ClassNotFoundException e) {   
        //数据库驱动类异常处理
        System.out.println("Sorry,can`t find the Driver!");   
        e.printStackTrace();   
        } catch(SQLException e1) {
        //数据库连接失败异常处理
        e1.printStackTrace();  
        }catch (Exception e2) {
        // TODO: handle exception
        e2.printStackTrace();
    }
}
    public String selectAll() {
    	//2.创建statement类对象，用来执行SQL语句！！
        Statement statement;
		try {
			statement = (Statement) con.createStatement();
			//要执行的SQL语句
	        String sql = "select * from diary";
	        //3.ResultSet类，用来存放获取的结果集！！
	        ResultSet rs = statement.executeQuery(sql);
	 
	        String title = null;
	        String time = null;
	        String content = null;
	        String one = null;
	        String ss="";  // 将数据库中的内容全部格式化为字符串回传客户端
	        int id =0;
	        while(rs.next()){
	            //获取title这列数据
	            title = rs.getString("title");
	            //获取time这列数据
	            time = rs.getString("time");
	          //获取content这列数据
	            content = rs.getString("content");
	          //获取id这列数据
	            id = rs.getInt("id");
	            one = rs.getString("one");
	           ss+=id+"***"+title+"***"+time+"***"+content+"***"+one+"****";  //四个星代表切割一行数据，三颗星代表切割每列数据
	        }
	        rs.close();
	        return ss+"##end";  //将封装好的字符串返回给客户端,后面是结束标记位
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
        
		return null;
	}
    public String insertStudent(String[]strings) {
  
		String sqlString = "insert into student(s_id,pwd,name,sex,qq,phonenumber,class_id,class_name,is_admin,is_apply)values(?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sqlString);
			preparedStatement.setInt(1, Integer.parseInt(strings[1])); //给sql参数赋值的索引是从1开始的，而不是从0
			preparedStatement.setString(2, strings[2]);
			preparedStatement.setString(3, strings[3]);
			preparedStatement.setString(4, strings[4]);
			preparedStatement.setString(5, strings[5]);
			preparedStatement.setString(6, strings[6]);
			preparedStatement.setInt(7,Integer.parseInt(strings[7]));
			preparedStatement.setString(8, strings[8]);
			preparedStatement.setInt(9,0);//新注册的学生默认不是班级管理员，由系统管理员审核申请通过
			preparedStatement.setInt(10, 0);//新注册的学生默认不申请成为管理员
			int i=preparedStatement.executeUpdate();    //如果返回受影响的行数为1 即说明插入数据成功
			if (i==1) {
				return "注册成功";
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "insert fail";
	}
    
   public String select(String[]strings)
   {
		//2.创建statement类对象，用来执行SQL语句！！
       Statement statement;
		try {
			statement = (Statement) con.createStatement();
			//要执行的SQL语句
	        String sql = strings[1];  //将查询的语句在客户端生成直接传过来
	        //3.ResultSet类，用来存放获取的结果集！！
//	        ResultSet rs = statement.executeQuery(sql);
//	 
//	        String title = null;
//	        String time = null;
//	        String content = null;
//	        String one = null;
//	        String ss="";  // 将数据库中的内容全部格式化为字符串回传客户端
//	        int id =0;
//	        while(rs.next()){
//	            //获取stuname这列数据
//	            title = rs.getString("title");
//	            //获取stuid这列数据
//	            time = rs.getString("time");
//	            content = rs.getString("content");
//	            id = rs.getInt("id");
//	            one = rs.getString("one");
//	           ss+=id+"***"+title+"***"+time+"***"+content+"***"+one+"****";  //四个星代表切割一行数据，三颗星代表切割每列数据
//	        }
//	        rs.close();
	        ResultSet rs =statement.executeQuery(sql);
	        if (rs.next()) {
	        	String class_id = rs.getString("class_id");
	        	String name = rs.getString("class_name");
	        	return "学生登录成功****"+class_id+"****"+name+"****##end";
			}
			
	       // return ss+"##end";  //将封装好的字符串返回给客户端,后面是结束标记位
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "登录失败****##end";
   }
   
   public String delete(String[]strings)
   {
	   String sql =strings[1];  //删除语句直接从客户端传过来
	   PreparedStatement ptmt;
	try {
		ptmt = (PreparedStatement) con.prepareStatement(sql);
	    int len=ptmt.executeUpdate();
	    if (len>0) {
			return "delete success";
		}
	} catch (SQLException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
     return "delete fail";
    
   }
   
   
   public String updata(String[]strings)
   {
	   String sql = "update diary set title=?,time=?,content=? where id =?";
	   PreparedStatement ptmt;
		try {
			ptmt = (PreparedStatement) con.prepareStatement(sql);
			ptmt.setString(1, strings[2]);
			ptmt.setString(2, strings[3]);
			ptmt.setString(3, strings[4]);
			ptmt.setInt(4,Integer.parseInt((strings[1])));
		    int len=ptmt.executeUpdate();
		    if (len>0) {
				 return "update success";
			}
		 
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	    return "update fail";
   }
   
   
   //获取所有班级信息
   public String getClassData() {
	   String sql = "select * from class";
	   String result ="";
	   try {
		Statement statement = con.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			result += resultSet.getInt("class_id")+"***"+resultSet.getString("class_name")+"***"+resultSet.getString("remainder")+"***"+resultSet.getString("detail")+"****";
		}
		result +="##end";
		return result;
	} catch (SQLException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	   return null;
	
   }
   
   
   
   //获取班级的经费详情
   public String getFunds(String []strings) {
	   
	   String sql = "select detail,remainder from class where class_id ="+strings[1];
	try {
		Statement statement;
		statement = con.createStatement();
		ResultSet resultSet =statement.executeQuery(sql);
		if (resultSet.next()) {
			String result = resultSet.getString("detail")+"++++"+resultSet.getString("remainder")+"++++获取经费信息****##end";
			return result;
		}
	} catch (SQLException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	    return null;
   }
   
   
   //验证班级管理员登录
   public String validateAdmin(String [] strings) {
	   String sql = strings[1];
	   try {
		Statement statement = con.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		if(resultSet.next()) {
			if (resultSet.getInt("is_admin")==1) {
				String id = resultSet.getString("class_id");
				String name = resultSet.getString("class_name");
				return "班级管理员登录成功****"+id+"****"+name+"****##end";
			}
		}
	} catch (SQLException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	   return "班级管理员登录失败****##end";
	
   } 
   //超级管理员登录验证
   public String validateSuperAdmin(String[] strings) {
	   String sql = strings[1];
	   try {
		Statement statement = con.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		if(resultSet.next()) {
			return "超级管理员登录成功****##end";
		}
	} catch (SQLException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	   return "超级管理员登录失败****##end";
	
   }
   
   
   //超级管理员添加班级信息
   public String addClass(String[]strings) {
	   String sql = "insert into class(class_name,remainder,detail) values(?,?,?)";
	   try {
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, strings[1]);
		preparedStatement.setString(2, strings[2]);
		preparedStatement.setString(3, strings[3]);
		int i = preparedStatement.executeUpdate();
		if (i==1) {
			return "添加成功!****##end";
		}
	} catch (SQLException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	   return "添加失败!****##end";
	
   }
   
   public String updateFunds(String[]strings) {
	    String sql = "update class set detail =?,remainder=? where class_id =?";
	    try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, strings[1]);
			preparedStatement.setString(2, strings[2]);
			preparedStatement.setInt(3, Integer.valueOf(strings[3]));
			int i = preparedStatement.executeUpdate();
			if (i==1) {
				sql = "select detail,remainder from class where class_id ="+strings[3];
					Statement statement;
					statement = con.createStatement();
					ResultSet resultSet =statement.executeQuery(sql);
					if (resultSet.next()) {
						String result = resultSet.getString("detail")+"++++"+resultSet.getString("remainder")+"++++经费信息更新成功****##end";
						return result;
					}	
		}
	    }catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	    return null;
   }
   
   
   public String addFunds(String[] strings) {
	   String sql = "update class set detail =?,remainder=? where class_id =?";
	    try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, strings[1]);
			preparedStatement.setString(2, strings[2]);
			preparedStatement.setInt(3, Integer.valueOf(strings[3]));
			int i = preparedStatement.executeUpdate();
			if (i==1) {
				sql = "select detail,remainder from class where class_id ="+strings[3];
					Statement statement;
					statement = con.createStatement();
					ResultSet resultSet =statement.executeQuery(sql);
					if (resultSet.next()) {
						String result = resultSet.getString("detail")+"++++"+resultSet.getString("remainder")+"++++经费信息添加成功****##end";
						return result;
					}	
		}
	    }catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
   
   
   public String deleteFunds(String[] strings) {
	   String sql = "update class set detail =?,remainder where class_id =?";
	    try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, strings[1]);
			preparedStatement.setString(2, strings[2]);
			preparedStatement.setInt(3, Integer.valueOf(strings[3]));
			int i = preparedStatement.executeUpdate();
			if (i==1) {
				sql = "select detail,remainder from class where class_id ="+strings[3];
					Statement statement;
					statement = con.createStatement();
					ResultSet resultSet =statement.executeQuery(sql);
					if (resultSet.next()) {
						String result = resultSet.getString("detail")+"++++"+resultSet.getString("remainder")+"++++经费信息删除成功****##end";
						return result;
					}	
		}
	    }catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
   
   
   public String apply(String[] contentStrings) {
		String sql = "update student set is_apply =? where s_id =?";
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1,1);
			preparedStatement.setInt(2,Integer.parseInt(contentStrings[1]));
			int i = preparedStatement.executeUpdate();
			if (i==1) {
				return "申请成功，等待超级管理员审核";
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
   
   
   //获取申请成为班级管理员的名单
   public String exmaine(String[] contentStrings) {
		String sql = "select * from student where is_apply=? and is_admin=?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, 1);
			preparedStatement.setInt(2, 0);
			ResultSet resultSet = preparedStatement.executeQuery();
			String s_id="";
			String class_id="";
			String name="";
			String class_name="";
			String ss="";
			while (resultSet.next()) {
			           s_id = resultSet.getString("s_id");
			           class_id = resultSet.getString("class_id");
			            //获取name这列数据
			           name = resultSet.getString("name");
			            //获取class_name这列数据
			            class_name = resultSet.getString("class_name");
			           ss+=s_id+"***"+name+"***"+class_id+"***"+class_name+"****";  //四个星代表切割一行数据，三颗星代表切割每列数据
			        
			}
			return ss+"##end";
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	
		return null;
	}
   
   
   
   //获取管理员名单
   public String getAdmin() {
	   String sql = "select * from student where is_admin=?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, 1);
			ResultSet resultSet = preparedStatement.executeQuery();
			String s_id="";
			String class_id="";
			String name="";
			String class_name="";
			String ss="";
			while (resultSet.next()) {
			           s_id = resultSet.getString("s_id");
			           class_id = resultSet.getString("class_id");
			            //获取name这列数据
			           name = resultSet.getString("name");
			            //获取class_name这列数据
			            class_name = resultSet.getString("class_name");
			           ss+=s_id+"***"+name+"***"+class_id+"***"+class_name+"****";  //四个星代表切割一行数据，三颗星代表切割每列数据
			        
			}
			return ss+"##end";
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	
		return null;
	}
   
   
   
   //是否通过成为班级管理员验证
   public String isPass(String[] contentStrings) {
	   PreparedStatement preparedStatement ;
	   String sql = "select is_admin from student where class_id=? and is_admin=?";
	   try {
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1, Integer.parseInt(contentStrings[1]));
		preparedStatement.setInt(2,1);
		ResultSet resultSet =preparedStatement.executeQuery();
	
		if (resultSet.last()) {
			return "每个班只允许一个班级管理员****##end";
		}
		else {
			sql = "update student set is_admin=? where s_id=?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, 1);
			preparedStatement.setInt(2, Integer.parseInt(contentStrings[2]));
			int i =preparedStatement.executeUpdate();
			if (i>0) {
				return "授权通过****##end";
			}
		}
	} catch (SQLException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	return null;
}
   
   
   public String deleteAdmin(String[] contentStrings) {
	   String sql = "update student set is_admin=? where s_id=?";
	   PreparedStatement preparedStatement;
	   try {
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1, 0); //将is_admin字段设为0即代表不是管理员了
		preparedStatement.setInt(2, Integer.parseInt(contentStrings[2]));
		int i =preparedStatement.executeUpdate();
		if (i>0) {
			return "管理员删除成功****##end";
		}
	} catch (SQLException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	return null;
}
   
    
    public void close() {
	try {
		con.close();
		System.out.println("已关闭数据库连接...");
	} catch (SQLException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}	
	}

}

