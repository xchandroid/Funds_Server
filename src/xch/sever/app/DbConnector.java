package xch.sever.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.PreparedStatement;

public class DbConnector {
	//����Connection����
  private Connection con;
    //����������
  private String driver = "com.mysql.cj.jdbc.Driver";
    //URLָ��Ҫ���ʵ����ݿ���funds
  private String url = "jdbc:mysql://localhost:3306/funds?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true";
    //MySQL����ʱ���û���
  private String user = "root";
    //MySQL����ʱ������
  private String password = "20130901xch";
//  public static void main(String[] args) {
//	DbConnector dbConnector = new DbConnector();
//	dbConnector.insertStudent(new String[] {"1504405243","20130901xch","л����","��","1208718872","18077760737","1"});
//}
   public DbConnector()
    {
   
    try {
        //������������
        Class.forName(driver);
        //1.getConnection()����������MySQL���ݿ⣡��
        con = (Connection) DriverManager.getConnection(url,user,password);
        if(!con.isClosed())
            System.out.println("�ɹ��������ݿ�!");
        
    } catch(ClassNotFoundException e) {   
        //���ݿ��������쳣����
        System.out.println("Sorry,can`t find the Driver!");   
        e.printStackTrace();   
        } catch(SQLException e1) {
        //���ݿ�����ʧ���쳣����
        e1.printStackTrace();  
        }catch (Exception e2) {
        // TODO: handle exception
        e2.printStackTrace();
    }
}
    public String selectAll() {
    	//2.����statement���������ִ��SQL��䣡��
        Statement statement;
		try {
			statement = (Statement) con.createStatement();
			//Ҫִ�е�SQL���
	        String sql = "select * from diary";
	        //3.ResultSet�࣬������Ż�ȡ�Ľ��������
	        ResultSet rs = statement.executeQuery(sql);
	 
	        String title = null;
	        String time = null;
	        String content = null;
	        String one = null;
	        String ss="";  // �����ݿ��е�����ȫ����ʽ��Ϊ�ַ����ش��ͻ���
	        int id =0;
	        while(rs.next()){
	            //��ȡtitle��������
	            title = rs.getString("title");
	            //��ȡtime��������
	            time = rs.getString("time");
	          //��ȡcontent��������
	            content = rs.getString("content");
	          //��ȡid��������
	            id = rs.getInt("id");
	            one = rs.getString("one");
	           ss+=id+"***"+title+"***"+time+"***"+content+"***"+one+"****";  //�ĸ��Ǵ����и�һ�����ݣ������Ǵ����и�ÿ������
	        }
	        rs.close();
	        return ss+"##end";  //����װ�õ��ַ������ظ��ͻ���,�����ǽ������λ
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
        
		return null;
	}
    public String insertStudent(String[]strings) {
  
		String sqlString = "insert into student(s_id,pwd,name,sex,qq,phonenumber,class_id,class_name,is_admin,is_apply)values(?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sqlString);
			preparedStatement.setInt(1, Integer.parseInt(strings[1])); //��sql������ֵ�������Ǵ�1��ʼ�ģ������Ǵ�0
			preparedStatement.setString(2, strings[2]);
			preparedStatement.setString(3, strings[3]);
			preparedStatement.setString(4, strings[4]);
			preparedStatement.setString(5, strings[5]);
			preparedStatement.setString(6, strings[6]);
			preparedStatement.setInt(7,Integer.parseInt(strings[7]));
			preparedStatement.setString(8, strings[8]);
			preparedStatement.setInt(9,0);//��ע���ѧ��Ĭ�ϲ��ǰ༶����Ա����ϵͳ����Ա�������ͨ��
			preparedStatement.setInt(10, 0);//��ע���ѧ��Ĭ�ϲ������Ϊ����Ա
			int i=preparedStatement.executeUpdate();    //���������Ӱ�������Ϊ1 ��˵���������ݳɹ�
			if (i==1) {
				return "ע��ɹ�";
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return "insert fail";
	}
    
   public String select(String[]strings)
   {
		//2.����statement���������ִ��SQL��䣡��
       Statement statement;
		try {
			statement = (Statement) con.createStatement();
			//Ҫִ�е�SQL���
	        String sql = strings[1];  //����ѯ������ڿͻ�������ֱ�Ӵ�����
	        //3.ResultSet�࣬������Ż�ȡ�Ľ��������
//	        ResultSet rs = statement.executeQuery(sql);
//	 
//	        String title = null;
//	        String time = null;
//	        String content = null;
//	        String one = null;
//	        String ss="";  // �����ݿ��е�����ȫ����ʽ��Ϊ�ַ����ش��ͻ���
//	        int id =0;
//	        while(rs.next()){
//	            //��ȡstuname��������
//	            title = rs.getString("title");
//	            //��ȡstuid��������
//	            time = rs.getString("time");
//	            content = rs.getString("content");
//	            id = rs.getInt("id");
//	            one = rs.getString("one");
//	           ss+=id+"***"+title+"***"+time+"***"+content+"***"+one+"****";  //�ĸ��Ǵ����и�һ�����ݣ������Ǵ����и�ÿ������
//	        }
//	        rs.close();
	        ResultSet rs =statement.executeQuery(sql);
	        if (rs.next()) {
	        	String class_id = rs.getString("class_id");
	        	String name = rs.getString("class_name");
	        	return "ѧ����¼�ɹ�****"+class_id+"****"+name+"****##end";
			}
			
	       // return ss+"##end";  //����װ�õ��ַ������ظ��ͻ���,�����ǽ������λ
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return "��¼ʧ��****##end";
   }
   
   public String delete(String[]strings)
   {
	   String sql =strings[1];  //ɾ�����ֱ�Ӵӿͻ��˴�����
	   PreparedStatement ptmt;
	try {
		ptmt = (PreparedStatement) con.prepareStatement(sql);
	    int len=ptmt.executeUpdate();
	    if (len>0) {
			return "delete success";
		}
	} catch (SQLException e) {
		// TODO �Զ����ɵ� catch ��
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	    return "update fail";
   }
   
   
   //��ȡ���а༶��Ϣ
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
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
	   return null;
	
   }
   
   
   
   //��ȡ�༶�ľ�������
   public String getFunds(String []strings) {
	   
	   String sql = "select detail,remainder from class where class_id ="+strings[1];
	try {
		Statement statement;
		statement = con.createStatement();
		ResultSet resultSet =statement.executeQuery(sql);
		if (resultSet.next()) {
			String result = resultSet.getString("detail")+"++++"+resultSet.getString("remainder")+"++++��ȡ������Ϣ****##end";
			return result;
		}
	} catch (SQLException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
	    return null;
   }
   
   
   //��֤�༶����Ա��¼
   public String validateAdmin(String [] strings) {
	   String sql = strings[1];
	   try {
		Statement statement = con.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		if(resultSet.next()) {
			if (resultSet.getInt("is_admin")==1) {
				String id = resultSet.getString("class_id");
				String name = resultSet.getString("class_name");
				return "�༶����Ա��¼�ɹ�****"+id+"****"+name+"****##end";
			}
		}
	} catch (SQLException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
	   return "�༶����Ա��¼ʧ��****##end";
	
   } 
   //��������Ա��¼��֤
   public String validateSuperAdmin(String[] strings) {
	   String sql = strings[1];
	   try {
		Statement statement = con.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		if(resultSet.next()) {
			return "��������Ա��¼�ɹ�****##end";
		}
	} catch (SQLException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
	   return "��������Ա��¼ʧ��****##end";
	
   }
   
   
   //��������Ա��Ӱ༶��Ϣ
   public String addClass(String[]strings) {
	   String sql = "insert into class(class_name,remainder,detail) values(?,?,?)";
	   try {
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, strings[1]);
		preparedStatement.setString(2, strings[2]);
		preparedStatement.setString(3, strings[3]);
		int i = preparedStatement.executeUpdate();
		if (i==1) {
			return "��ӳɹ�!****##end";
		}
	} catch (SQLException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
	   return "���ʧ��!****##end";
	
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
						String result = resultSet.getString("detail")+"++++"+resultSet.getString("remainder")+"++++������Ϣ���³ɹ�****##end";
						return result;
					}	
		}
	    }catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
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
						String result = resultSet.getString("detail")+"++++"+resultSet.getString("remainder")+"++++������Ϣ��ӳɹ�****##end";
						return result;
					}	
		}
	    }catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
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
						String result = resultSet.getString("detail")+"++++"+resultSet.getString("remainder")+"++++������Ϣɾ���ɹ�****##end";
						return result;
					}	
		}
	    }catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
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
				return "����ɹ����ȴ���������Ա���";
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return null;
	}
   
   
   //��ȡ�����Ϊ�༶����Ա������
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
			            //��ȡname��������
			           name = resultSet.getString("name");
			            //��ȡclass_name��������
			            class_name = resultSet.getString("class_name");
			           ss+=s_id+"***"+name+"***"+class_id+"***"+class_name+"****";  //�ĸ��Ǵ����и�һ�����ݣ������Ǵ����и�ÿ������
			        
			}
			return ss+"##end";
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	
		return null;
	}
   
   
   
   //��ȡ����Ա����
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
			            //��ȡname��������
			           name = resultSet.getString("name");
			            //��ȡclass_name��������
			            class_name = resultSet.getString("class_name");
			           ss+=s_id+"***"+name+"***"+class_id+"***"+class_name+"****";  //�ĸ��Ǵ����и�һ�����ݣ������Ǵ����и�ÿ������
			        
			}
			return ss+"##end";
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	
		return null;
	}
   
   
   
   //�Ƿ�ͨ����Ϊ�༶����Ա��֤
   public String isPass(String[] contentStrings) {
	   PreparedStatement preparedStatement ;
	   String sql = "select is_admin from student where class_id=? and is_admin=?";
	   try {
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1, Integer.parseInt(contentStrings[1]));
		preparedStatement.setInt(2,1);
		ResultSet resultSet =preparedStatement.executeQuery();
	
		if (resultSet.last()) {
			return "ÿ����ֻ����һ���༶����Ա****##end";
		}
		else {
			sql = "update student set is_admin=? where s_id=?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, 1);
			preparedStatement.setInt(2, Integer.parseInt(contentStrings[2]));
			int i =preparedStatement.executeUpdate();
			if (i>0) {
				return "��Ȩͨ��****##end";
			}
		}
	} catch (SQLException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
	return null;
}
   
   
   public String deleteAdmin(String[] contentStrings) {
	   String sql = "update student set is_admin=? where s_id=?";
	   PreparedStatement preparedStatement;
	   try {
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1, 0); //��is_admin�ֶ���Ϊ0�������ǹ���Ա��
		preparedStatement.setInt(2, Integer.parseInt(contentStrings[2]));
		int i =preparedStatement.executeUpdate();
		if (i>0) {
			return "����Աɾ���ɹ�****##end";
		}
	} catch (SQLException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
	return null;
}
   
    
    public void close() {
	try {
		con.close();
		System.out.println("�ѹر����ݿ�����...");
	} catch (SQLException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}	
	}

}

