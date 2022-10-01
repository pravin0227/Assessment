package assessment3.project.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class jdbcQuestion {

	
	public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "Pravinmore0227@");
        Statement st = conn.createStatement();
        
        //Create Schema(exam) and Table(course)-->
        
        st.execute("CREATE SCHEMA idfc4;");
        
        // Query for Creating Table in course
        
        String create ="CREATE TABLE idfc4.courseData (Cid int not null, Name VARCHAR(20) NOT NULL,"
                        + "Instructor VARCHAR(20),Duration Varchar(10),fees int );";
        boolean status=st.execute(create); // passing the string create in execute function as a Query
        if(!status) {
                System.out.println("Table is Created");
        }
       
        
        // 1. Inserting values
        
        
        String insert = "INSERT INTO idfc4.courseData VALUES(?,?,?,?,?);";
        
        // using prepared statement so we can use set methods for adding values
        PreparedStatement pstmt1 = conn.prepareStatement(insert);
        pstmt1.setInt(1,4);
        pstmt1.setString(2,"jfs");
        pstmt1.setString(3, "Priya");
        pstmt1.setString(4, "4 months");
        pstmt1.setInt(5, 5400);
                        
                int count = pstmt1.executeUpdate(); // returns 1 if successful
        if(count==1) {
                System.out.println("inserted values successfully");
        }
        pstmt1.close();
        
        //Retrieve -->********************
        // directly passed Query for selecting all columns from course table
        ResultSet rs = st.executeQuery("SELECT * FROM idfc4.courseData;");
        ResultSetMetaData resultsetmatadata = rs.getMetaData();
        while(rs.next()) {
                for(int i=1; i<=resultsetmatadata.getColumnCount();i++) {
                        System.out.print(rs.getString(i)+ "  ");
                }
                System.out.println();
        }
        
        // updating values
        
        //creating Update query to replace course name to SpringBoot from java where id=2. 
        String updateValues = "UPDATE idfc4.courseData SET name='blockchain' where Cid=4;";
        PreparedStatement pstmt = conn.prepareStatement(updateValues);     
        pstmt.execute(); // executing the query
        
        boolean chkU=st.execute(updateValues); // returns false if updated
        if(chkU==false) {
                System.out.println("Values Updated successfully :) ");
        }
        pstmt.close();
        
        // Deleting values
        
        // Deleting row.
        String delete = "DELETE FROM idfc4.courseData WHERE Cid=4";
        PreparedStatement pstmt2 = conn.prepareStatement(delete);
        pstmt2.execute();        
        boolean flag=st.execute(delete);
        if(!flag) {
                System.out.println("Value Deleted successfullyt :) ");
        }
        pstmt2.close();
        
        conn.close();
}
	
}
