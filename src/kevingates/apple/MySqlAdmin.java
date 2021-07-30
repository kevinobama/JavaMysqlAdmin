package kevingates.apple;
 
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author kevin gates
 * @email kevinobamatheus@gmail.com
 */
public class MySqlAdmin {	
 
	public static void main(String[] args) {	 		
		try {
			StringBuilder csvData = new StringBuilder();
            Connection conn=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/wechatecommerce?useSSL=false&useUnicode=true","root","654321");
          
            Map<String,String> tables = MySqlAdmin.tables(conn);
    		for(Map.Entry<String, String>  table: tables.entrySet()) {   			    	 
    			System.out.println(table.getKey()+"("+table.getValue()+"),,");
    			
    			csvData.append(table.getKey()+"("+table.getValue()+"),,");    			    			
    			csvData.append(MySqlAdmin.getTableStructure(table.getKey(),conn));
    		}
    
    		conn.close();
    		
    		FileWriter csvFile = new FileWriter("database.csv");
    		csvFile.write(csvData.toString());
    		csvFile.close();
		      
        } catch(Exception e) { 
        	System.out.println(e);
        }
	}
	
	public static Map<String,String> tables(Connection conn) {
		Map<String,String> tableNames = new HashMap<>();
        try {
            Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT TABLE_NAME,table_comment FROM INFORMATION_SCHEMA.TABLES WHERE table_schema='wechatecommerce';");
            
            while(rs.next()) {            	
            	tableNames.put(rs.getString(1),rs.getString(2));
            }
    
        } catch(Exception e) { 
        	System.out.println(e);
        }
        
        return tableNames;
	}
	
	public static StringBuilder getTableStructure(String table,Connection conn) {
		Map<String,String> tableNames = new HashMap<>();
		StringBuilder csvData = new StringBuilder();
        try {             
            Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery("SHOW FULL COLUMNS FROM "+table+";");
                        
            while(rs.next()) {
            	System.out.println(rs.getString(1)+","+rs.getString(2)+","+rs.getString(9));
            	csvData.append(rs.getString(1)+","+rs.getString(2)+","+rs.getString(9));
            }
        } catch(Exception e) { 
        	System.out.println(e);
        }
        
        return csvData;
	}
}