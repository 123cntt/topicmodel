package com.longuh;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class InsertToSqllite {
	public static void main(String[] args) throws IOException, SQLException {
		File dir = new File("usermodel2");

		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:usertopic.s3db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		for (File file : dir.listFiles()) {
			String prob = Files.readFirstLine(file, Charsets.UTF_8);
			prob = prob.replaceAll(" ", ",");
			System.out.println(prob);
			
			// get userid & articleid
			String fileName = file.getName();
			if(!fileName.endsWith("theta"))
				continue;
			System.out.println(fileName);			
			String userId = fileName.substring(0,fileName.indexOf(".txt"));
//			System.out.println("userId:"+userId);
			String aricleID = fileName.substring(0,fileName.indexOf(".wseg"));
			System.out.println("aricleID:"+aricleID);
//			String userId = fileName.substring(0,fileName.indexOf(ch))
			
			// getArticleId
			stmt = c.createStatement();
			
			String sql = "INSERT INTO userArticleTopicInf (userid,userArticleID,topic0, topic1, topic2, topic3, topic4, topic5, topic6, topic7, topic8, topic9, topic10, topic11, topic12, topic13, topic14, topic15, topic16, topic17, topic18, topic19, topic20, topic21, topic22, topic23, topic24, topic25, topic26, topic27, topic28, topic29, topic30, topic31, topic32, topic33, topic34, topic35, topic36, topic37, topic38, topic39, topic40, topic41, topic42, topic43, topic44, topic45, topic46, topic47, topic48, topic49, topic50, topic51, topic52, topic53, topic54, topic55, topic56, topic57, topic58, topic59, topic60, topic61, topic62, topic63, topic64, topic65, topic66, topic67, topic68, topic69, topic70, topic71, topic72, topic73, topic74, topic75, topic76, topic77, topic78, topic79, topic80, topic81, topic82, topic83, topic84, topic85, topic86, topic87, topic88, topic89, topic90, topic91, topic92, topic93, topic94, topic95, topic96, topic97, topic98, topic99) "
					+ "VALUES ("+userId+",'"+aricleID+"',"+prob.substring(0,prob.length()-1)+")";
			System.out.println(sql);
			
			stmt.executeUpdate(sql);

			stmt.close();
			c.commit();
			System.out.println("Records created successfully");
		}
		c.close();
	}
}
