package com.umbum.pos.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository
public class ProductRepo {

    private Connection conn;

    public ProductRepo() {
        String user = "umbum";
        String pw = "vogh1657*";
        String url = "jdbc:oracle:thin:@192.168.0.2:1521:orcl";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
