package com.example.workingproject.DBTest.dao;

import UtilsClass.Fruit;
import com.example.workingproject.DBTest.DBConnection;
import io.qameta.allure.Step;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodDaoJDBCImpl implements FoodDao {
    private final Connection connection = DBConnection.getJDBCConnection();

    @Override
    @Step("Функциональный шаг: создание таблицы")
    public void createFruitTable() {
        String sql = "CREATE TABLE IF NOT EXISTS FOOD (FOOD_ID INT AUTO_INCREMENT NOT NULL, FOOD_NAME VARCHAR(30), FOOD_TYPE VARCHAR(30), FOOD_EXOTIC NUMBER(1) )";
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Step("Функциональный шаг: удаление таблицы")
    public void dropFruitTable() {
        String sql = "DROP TABLE IF EXISTS FOOD";
        try(Statement statement = connection.createStatement();){
            statement.executeUpdate(sql);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Step("Шаг 2.1: Добавление нового фрукта")
    public void addFruit(String name, String type, Integer exotic) {
        String sql = "INSERT INTO FOOD (FOOD_NAME, FOOD_TYPE, FOOD_EXOTIC) VALUES(?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1 , name);
            statement.setString(2 , type);
            statement.setInt(3 , exotic);
            statement.executeUpdate();
            connection.commit();
            System.out.printf("фрукт %s %s добавлен в базу данных" + System.lineSeparator(), name, type);
        } catch(SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    @Step("Шаг 2.4: Удаление фрукта по индексу")
    public void removeFruitById(Integer id) {
        String sql = "DELETE FROM FOOD WHERE FOOD_ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch(SQLException e) {
            try {
                connection.rollback();
            } catch(SQLException ex) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException(e);
        }
    }

    @Step("Шаг 2.2: Получение фрукта по индексу")
    public Fruit getFruitById(Integer id) {
        String sql = "SELECT * FROM FOOD WHERE FOOD_ID = ?";
        Fruit fruit = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                fruit = new Fruit(
                        resultSet.getInt("FOOD_ID"),
                        resultSet.getString("FOOD_NAME"),
                        resultSet.getString("FOOD_TYPE"),
                        resultSet.getInt("FOOD_EXOTIC")
                );
            }
            connection.commit();
        } catch(SQLException e) {
            try {
                connection.rollback();
            } catch(SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

        return fruit;
    }

    @Override
    @Step("Шаг 2.3: Получение всех фруктов")
    public List<Fruit> getAllFruits() {
        List<Fruit> list = new ArrayList<>();
        String sql = "SELECT FOOD_ID, FOOD_NAME, FOOD_TYPE, FOOD_EXOTIC FROM FOOD";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet results = statement.executeQuery();
            while(results.next()) {
                Integer id = results.getInt("FOOD_ID");
                String name = results.getString("FOOD_NAME");
                String type = results.getString("FOOD_TYPE");
                Integer exotic = results.getInt("FOOD_EXOTIC");
                Fruit fruit = new Fruit(id, name, type, exotic);
                list.add(fruit);
                connection.commit();
            }
        } catch(SQLException e) {
            try {
                connection.rollback();
            } catch(SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    @Step("Функциональный шаг: очистка таблицы")
    public void clearFruitTable() {
        String sql = "TRUNCATE TABLE FOOD";
        try(Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(sql);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Step("Функционалльный шаг: Перезагрузка Базы Данных(удаление, создание и заполнение стандартными данными)")
    public void dbRefresh() {
        dropFruitTable();
        createFruitTable();
        addFruit("Апельсин", "FRUIT", 1);
        addFruit("Капуста", "VEGETABLE", 0);
        addFruit("Помидор", "VEGETABLE", 0);
        addFruit("Яблоко", "FRUIT", 0);
    }
}