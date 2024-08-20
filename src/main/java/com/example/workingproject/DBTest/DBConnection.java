package com.example.workingproject.DBTest;

import UtilsClass.Fruit;
import io.qameta.allure.Step;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:h2:tcp://localhost:9092/mem:testdb";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "pass";
    private static SessionFactory sessionFactory;

    @Step("Шаг 1: Получение соеденения с Базой Данных")
    public static Connection getJDBCConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Соеденение не установлено");
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static SessionFactory getSessionFactoryProperties() {
        if(sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                configuration.addAnnotatedClass(Fruit.class);

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());

                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null){
            try {
                Configuration configuration = new Configuration().configure();
                sessionFactory = configuration.buildSessionFactory();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        return sessionFactory;
    }
}
