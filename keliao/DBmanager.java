package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Agent;
import Model.Customer;


public class DBmanager {
	static String url = "jdbc:mysql://localhost:3307/dbcarm"; 
	static final String DRIVER = "com.mysql.jdbc.Driver";
	
	static String dbUser = "keliao";
	static String dbPassword = "1234";
	
	static Connection conn = null;
	
	public static void initial(){
		conn = null;
    			    
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong");
			e.printStackTrace();
		}
		
    	try {
			conn = DriverManager.getConnection(url, dbUser, dbPassword);
			System.out.println("conn success!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("conn connection error!");
			e.printStackTrace();
		}
    	
    } 
	
	public static void closeConn(){
    	try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("conn closing error");
			e.printStackTrace();
		}
    }
	
	/*************************************Customer*****************************************/
	public static void printCustomerTable(){
		Statement stmt = null;
    	String sql = "select * from customer";
    	
    	try{
    		stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery(sql);
    		
    		while(rs.next()){
    			int id = Integer.parseInt(rs.getString("cid"));
    			String name = rs.getString("name");
    			
    			System.out.println(id + ", "+name);
    		}
    		stmt.close();
    		rs.close();

    	}catch (Exception e){
    		System.out.println(e.getMessage());
    	}
	}
	
	public static List<Customer> getCustomers(){
		Statement stmt = null;
		String sql = "select * from customer";
		
		List<Customer> cList = new ArrayList<Customer>();
		try{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				int cid = Integer.parseInt(rs.getString("cid"));
				String name = rs.getString("name");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				int aid = Integer.parseInt(rs.getString("aid"));
				
				cList.add(new Customer(cid, name, email, phone, aid));
			}
			
			stmt.close();
    		rs.close();
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		
		return cList;
	}
	
	public static void insertCustomer(String name, String email, String phone, int aid) {
		Statement stmt = null;
		String sql = "insert into customer (name, email, phone, aid) values ('"+name+"', '"+email+"', '"+phone+"', "+aid+")";
		
		if(!isCusEmailValid(email)) return;
		
		try{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
	    	System.out.println("web: Done customer inserted");
	    	
	    	stmt.close();
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static boolean isCusEmailValid(String email){
		Statement stmt = null;
		String sql = "select email from customer";
		
		try{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){

				String e = rs.getString("email");

				if(email.equals(e)) return false;  //check email if already registered
			}
			
			stmt.close();
    		rs.close();
    		
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		
		return true;
	}
	
	public static Customer getCustomerById(int cid){
		Statement stmt = null;
		String sql = "select * from customer where cid="+cid;
		Customer res = null;

		try{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				int ccid = Integer.parseInt(rs.getString("cid"));
				String name = rs.getString("name");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				int aid = Integer.parseInt(rs.getString("aid"));
				
				res = new Customer(ccid, name, email, phone, aid);
				break;
			}
			
			stmt.close();
    		rs.close();
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		
		return res;
	}
	
	public static void updateCustomerById(int cid, String name, String email, String phone, int aid){
		Statement stmt = null;
		String sql = "update customer set name='"+name+"', email='"+email+"', phone='"+phone+"', aid="+aid+
				" where cid="+cid;
		try{
			stmt = conn.createStatement();
			
			stmt.executeUpdate(sql);
			System.out.println("update customer done!");
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static void deleteCustomerById(int cid){
		Statement stmt = null;
		if(!isCusCIDValid(cid)) return;
		
		try{
			stmt = conn.createStatement();
			String sql = "delete from customer where cid="+cid;
			stmt.executeUpdate(sql);
			System.out.println("delete customer done!");
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
    	
	}
	
	public static boolean isCusCIDValid(int cid){
		Statement stmt = null;
		String sql = "select * from customer where cid="+cid;

		try{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				return true;
			}
			
			stmt.close();
    		rs.close();
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		return false;
	}
	/*************************************Customer end*****************************************/
	
	
	/*************************************Agent*****************************************/
	public static List<Agent> getAgents(){
		Statement stmt = null;
		String sql = "select * from agent";
		
		List<Agent> aList = new ArrayList<Agent>();
		try{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				int aid = Integer.parseInt(rs.getString("aid"));
				String name = rs.getString("name");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				
				aList.add(new Agent(aid, name, email, phone));
			}
			
			stmt.close();
    		rs.close();
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		
		return aList;
	}
	
	public static void insertAgent(String name, String email, String phone) {
		Statement stmt = null;
		String sql = "insert into agent (name, email, phone) values ('"+name+"', '"+email+"', '"+phone+"')";
		
		if(!isAgentEmailValid(email)) return;
		
		try{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
	    	System.out.println("web: Done agent inserted");
	    	
	    	stmt.close();
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static boolean isAgentEmailValid(String email){
		Statement stmt = null;
		String sql = "select email from agent";
		
		try{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){

				String e = rs.getString("email");

				if(email.equals(e)) return false;  //check email if already registered
			}
			
			stmt.close();
    		rs.close();
    		
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		
		return true;
	}
	
	public static Agent getAgentById(int aid){
		Statement stmt = null;
		String sql = "select * from agent where aid="+aid;
		Agent res = null;

		try{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				String name = rs.getString("name");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				int aaid = Integer.parseInt(rs.getString("aid"));
				
				res = new Agent(aaid, name, email, phone);
				break;
			}
			
			stmt.close();
    		rs.close();
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		
		return res;
	}
	
	public static void updateAgentById(int aid, String name, String email, String phone){
		Statement stmt = null;
		String sql = "update agent set name='"+name+"', email='"+email+"', phone='"+phone+"'"+
				" where aid="+aid;
		try{
			stmt = conn.createStatement();
			
			stmt.executeUpdate(sql);
			System.out.println("update agent done!");
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static void deleteAgentById(int aid){
		Statement stmt = null;
		if(!isAgentAIDValid(aid)) return;
		
		try{
			stmt = conn.createStatement();
			String sql = "delete from agent where aid="+aid;
			stmt.executeUpdate(sql);
			System.out.println("delete agent done!");
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
    	
	}
	
	public static boolean isAgentAIDValid(int aid){
		Statement stmt = null;
		String sql = "select * from agent where aid="+aid;

		try{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				return true;
			}
			
			stmt.close();
    		rs.close();
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	/*************************************Agent end*****************************************/
	
	/**************************************History*******************************************/
	public static List<Agent> getHistory(){
		Statement stmt = null;
		String sql = "select * from history";
		
		List<Agent> aList = new ArrayList<Agent>();
		try{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				int aid = Integer.parseInt(rs.getString("aid"));
				String name = rs.getString("name");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				
				aList.add(new Agent(aid, name, email, phone));
			}
			
			stmt.close();
    		rs.close();
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		
		return aList;
	}
	/**************************************History end**************************************/
	public static void main(String[] args){
		initial();
		
		//insertCustomer("rose", "rose@gmail.com", "4729493", 1);
		System.out.println(getCustomers().size());
		//deleteCustomerById(5);
		//updateCustomerById(6, "ross", "ross@gmail.com", "2382", 1);
		
		printCustomerTable();
		
		closeConn();
	}
}
