package com.example.workingproject.DBTest.dao;

import UtilsClass.Fruit;

import java.util.List;

public interface FoodDao {
    void createFruitTable();
    void dropFruitTable();
    void addFruit(String name, String type, Integer exotic);
    void removeFruitById(Integer id);
    Fruit getFruitById(Integer id);
    List<Fruit> getAllFruits();
    void clearFruitTable();
    void dbRefresh();
}
