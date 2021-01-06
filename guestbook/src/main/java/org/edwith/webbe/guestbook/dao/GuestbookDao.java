package org.edwith.webbe.guestbook.dao;

import org.edwith.webbe.guestbook.dto.Guestbook;
import org.edwith.webbe.guestbook.util.DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuestbookDao {
	
	private static String dburl = "jdbc:mysql://localhost:3306/connectdb?useUnicode=true&characterEncoding=utf8&useSSL=false";
	private static String dbUser = "connectuser";
	private static String dbPasswd = "connect123!@#";
	
    public List<Guestbook> getGuestbooks(){
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	}
    	
        List<Guestbook> list = new ArrayList<>();
        
        String sql = "SELECT * FROM guestbook ORDER BY regdate";
        try (Connection conn = DBUtil.getConnection(dburl, dbUser, dbPasswd);
        		PreparedStatement ps = conn.prepareStatement(sql)) {
        	try (ResultSet rs = ps.executeQuery()) {
        		while (rs.next()) {
        			Long id = rs.getLong("id");
        			String content = rs.getString("content");
        			Date date = rs.getDate("regdate");
        			String name = rs.getString("name");
        			
        			Guestbook guestBook = new Guestbook(id, name, content, date);
        			list.add(guestBook);
        		}
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        	
        } catch (Exception ex) {
        	ex.printStackTrace();
        }

        return list;
    }

    public void addGuestbook(Guestbook guestbook){
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	}
        String sql = "INSERT INTO guestbook (id, content, regdate, name) VALUES (id, ?, now(), ?)";
        
        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbPasswd);
        		PreparedStatement ps = conn.prepareStatement(sql)) {
        	ps.setString(1,  guestbook.getContent());
        	ps.setString(2, guestbook.getName());
        	ps.executeUpdate();
        	ps.close();
        	
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
    }
}
