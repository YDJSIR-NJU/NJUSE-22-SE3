package com.assignment.collect;

import com.assignment.collect.enums.UserRole;
import com.assignment.collect.po.user.User;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Author: XiaYu
 * @Date 2022/2/19 10:38
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class JdbcSimpleDatasourceApplicatonTests {

    @Autowired
    private DataSource dataSource;


    @Test
    public void springDataSourceTest() {
        System.out.println(dataSource instanceof HikariDataSource);
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select  * from user");
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId((long) resultSet.getInt("id"));
                user.setUname(resultSet.getString("uname"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setUserRole(UserRole.valueOf(resultSet.getString("user_role")));
                user.setCreateTime(resultSet.getTime("create_time"));
            }
            System.out.println(user);
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
