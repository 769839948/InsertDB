package com.zdq.java.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import com.zdq.model.Company;
import com.zdq.model.User;

public class JDBC {
	  Connection conn;
	  PreparedStatement ps;
	  ResultSet rs;
	  
	  public Connection getConnection(){
		    String url="jdbc:mysql://localhost:3306/Contact?useSSL=true&useUnicode=true&characterEncoding=gbk";
		    String userName="root";
		    String password="123456";
		    try {
		      Class.forName("com.mysql.jdbc.Driver");
		    } catch (ClassNotFoundException e) {
		      // TODO Auto-generated catch block
		      System.out.println("找不到驱动！");
		      e.printStackTrace();
		    }
		    try {
		      conn=DriverManager.getConnection(url, userName, password);
		      if(conn!=null){
		        System.out.println("connection successful");
		      }
		    } catch (SQLException e) {
		      // TODO Auto-generated catch block
		        System.out.println( "connection fail");
		      e.printStackTrace();
		    }
		    return conn;
		  }
		  /**
		   * 写一个查询数据库语句的方法
		   */
		  public int QuerySql(User user){
			int count = 0;
		    //1、执行静态SQL语句。通常通过Statement实例实现。   
		    // 2、执行动态SQL语句。通常通过PreparedStatement实例实现。   
		    // 3、执行数据库存储过程。通常通过CallableStatement实例实现。
		    System.out.println("query");
		    User u;
//				j.Connection();
		    String sql="select * from User where username='"+user.getUsername()+"'";
		    try {
		      conn=getConnection();//连接数据库
		      ps=conn.prepareStatement(sql);// 2.创建Satement并设置参数
//		      ps.setString(1, user.getUsername());
		      rs=ps.executeQuery(sql);  // 3.ִ执行SQL语句
//		      while (rs.next()) {// 判断是否还有下一个数据
//                 System.out.println("ID:" + rs.getString("password") + "\tNAME:"
//		    	                           + rs.getString("username"));
//               }
		      ResultSet rs=ps.executeQuery();
		      while(rs.next()){
		    	  count ++;
		      }
//		      // 4.处理结果集
//		      while(rs.next()){
//		        u = new User();
//		        u.setUsername(rs.getString("username"));
//		        u.setPassword(rs.getString("password"));
//		        u.setCompany(rs.getString("company"));
//		      }
//		      System.out.println(rs.next());
		    } catch (SQLException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }finally{
		      //释放资源
		      try {
		        rs.close();
		        ps.close();
		        conn.close();
		      } catch (SQLException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		      }
		    }
			return count;
		  }
		  
		  public int QuerySqlCompany(Company company){
				int count = 0;
			    //1、执行静态SQL语句。通常通过Statement实例实现。   
			    // 2、执行动态SQL语句。通常通过PreparedStatement实例实现。   
			    // 3、执行数据库存储过程。通常通过CallableStatement实例实现。
			    System.out.println("query");
			    Company u;
//					j.Connection();
			    String sql="select * from Company where phone ='"+company.getPhone()+"'";
			    try {
			      conn=getConnection();//连接数据库
			      ps=conn.prepareStatement(sql);// 2.创建Satement并设置参数
			        // 3.ִ执行SQL语句
//			      ps.setString(1, company.getPhone());
			      rs=ps.executeQuery(sql);
			      ResultSet rs=ps.executeQuery();
			      //获取结果集中的列名及其类型
		
//			      // 4.处理结果集
			      while(rs.next()){
			    	  count ++;
			      }
//			      System.out.println(rs.next());
			    } catch (SQLException e) {
			      // TODO Auto-generated catch block
			      e.printStackTrace();
			    }finally{
			      //释放资源
			      try {
			        rs.close();
			        ps.close();
			        conn.close();
			      } catch (SQLException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			      }
			    }
				return count;
			  }
		  
		  @SuppressWarnings("null")
		  public void addOrUpdateUser(User user){
//			  System.out.println(user.username);
//			    String sql="insert into User(company,password,username) values(?,?,?)";
//			    userTool(user, sql);
			 if (QuerySql(user) >= 0) {
				 System.out.println("=========="+user.username);
				String sql="update User set company=?,password=? where username=?";
				userTool(user, sql);
			}else {
				System.out.println(user.username);
			    String sql="insert into User(company,password,username) values(?,?,?)";
			    userTool(user, sql);
			}
		    
		  }
		  /**
		   * @return修改数据
		   */
		  public int userTool(User user,String sql){
			  User u;
		    int row=0;
//				j.Connection();
		    try {
		      conn=getConnection();//连接数据库
		      ps=conn.prepareStatement(sql);// 2.创建Satement并设置参数
//					rs=ps.executeQuery(sql);  // 3.ִ执行SQL语句,緊緊用于查找語句
		      //sql語句中寫了幾個字段，下面就必須要有幾個字段
		      ps.setString(1, user.getCompany());
		      ps.setString(2, user.getPassword());
		      ps.setString(3, user.getUsername());
		      // 4.处理结果集
		      row=ps.executeUpdate();
		      System.out.println(row);
		    } catch (SQLException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }finally{
		      //释放资源
		      try {
//						rs.close();
		        ps.close();
		        conn.close();
		      } catch (SQLException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		      }
		    }
		    return row;
		  }
		  /**
		   * @return删除操作
		   */
		  public int delete(User user){
			User u;
		    int row=0;
//				j.Connection();
		    String sql="delete from User where username=?";
		    try {
		      conn=getConnection();//连接数据库
		      ps=conn.prepareStatement(sql);// 2.创建Satement并设置参数
//					rs=ps.executeQuery(sql);  // 3.ִ执行SQL语句,緊緊用于查找語句
		      //sql語句中寫了幾個字段，下面就必須要有幾個字段
		      ps.setString(1, user.getUsername());
		      
		      // 4.处理结果集
		      row=ps.executeUpdate();
		      System.out.println(row);
		    } catch (SQLException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }finally{
		      //释放资源【執行完sql要記得釋放資源】
		      try {
//						rs.close();
		        ps.close();
		        conn.close();
		      } catch (SQLException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		      }
		    }
		    return row;
		  }
		  
		  public void addOrUpdateCompany(Company company){
				 if (QuerySqlCompany(company) > 0) {
					String sql="update Company set username = ?,home= ?,department= ?,position= ?,email= ?,qq= ?,age= ?,company= ?,sortId = ? where phone = ?";
					updateCompany(company, sql);
				}else {
				    String sql="insert into Company(username,phone,home,department,position,email,qq,age,company,sortId) values(?,?,?,?,?,?,?,?,?,?)";
				    addCompany(company, sql);
				}
				 
		  }
		  public int updateCompany(Company company,String sql){
			  Company u =new Company();
		    int row=0;
//				j.Connection();
		    try {
		      conn=getConnection();//连接数据库
		      ps=conn.prepareStatement(sql);
		      // 2.创建Satement并设置参数
              //rs=ps.executeQuery();  
		      // 3.ִ执行SQL语句,緊緊用于查找語句
		      //sql語句中寫了幾個字段，下面就必須要有幾個字段
		      ps.setString(1, company.getUsername());
		      ps.setString(10, company.getPhone());
		      ps.setString(2, company.getHome());
		      ps.setString(3, company.getDepartment());
		      ps.setString(4, company.getPosition());
		      ps.setString(5, company.getEmail());
		      ps.setString(6, company.getQq());
		      ps.setInt(7, company.getAge());
		      ps.setString(8, company.getCompany());
		      ps.setInt(9, company.getSortId());
		      // 4.处理结果集
		      row=ps.executeUpdate();
//		      System.out.println(row+user.getUsername()+user.getPassword());
		    } catch (SQLException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }finally{
		      try {
		        ps.close();
		        conn.close();
		      } catch (SQLException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		      }
		      
		    }
		    return row;
		  }
				 
		  @SuppressWarnings("null")
		  public int addCompany(Company company,String sql){
			  Company u =new Company();
		    int row=0;
//				j.Connection();
		    try {
		      conn=getConnection();//连接数据库
		      ps=conn.prepareStatement(sql);
		      // 2.创建Satement并设置参数
              //rs=ps.executeQuery();  
		      // 3.ִ执行SQL语句,緊緊用于查找語句
		      //sql語句中寫了幾個字段，下面就必須要有幾個字段
		      ps.setString(1, company.getUsername());
		      ps.setString(2, company.getPhone());
		      ps.setString(3, company.getHome());
		      ps.setString(4, company.getDepartment());
		      ps.setString(5, company.getPosition());
		      ps.setString(6, company.getEmail());
		      ps.setString(7, company.getQq());
		      ps.setInt(8, company.getAge());
		      ps.setString(9, company.getCompany());
		      ps.setInt(10, company.getSortId());
		      // 4.处理结果集
		      row = ps.executeUpdate();
//		      System.out.println(row+user.getUsername()+user.getPassword());
		    } catch (SQLException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }finally{
		      try {
		        ps.close();
		        conn.close();
		      } catch (SQLException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		      }
		      
		    }
		    return row;
		  }
		  
		  public static void main(String[] args) {
		    JDBC j=new JDBC();
			j.getConnection();
//		    j.QuerySql();//在控制台顯示出查找方法
		    CompanyService companyService = new CompanyService();
		    try {
				List<Company> company = companyService.getAllByExcel("/Users/zhang/Documents/鲁东大学电话簿.xls");
				for (int i=0;i< company.size();i ++){
					System.out.println(company.get(i).getPhone());
					User user = new User();
					user.setCompany(company.get(i).getCompany());
					user.setUsername(company.get(i).getPhone());
					user.setPassword("123456");
					j.addOrUpdateUser(user);
					j.addOrUpdateCompany(company.get(i));
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//				UserInfo u=new UserInfo();
//				u.setUserId(5);
//				u.setUserName("cool");
//				u.setPassword("123abc");
//				j.update(u);////在控制台顯示出修改方法
	}
}
