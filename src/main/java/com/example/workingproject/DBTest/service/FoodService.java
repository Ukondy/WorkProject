package com.example.workingproject.DBTest.service;

import UtilsClass.Fruit;

import java.util.List;

public interface FoodService {
    void createFruitTable();
    void dropFruitTable();
    void addFruit(String name, String type, Integer isExotic);
    void removeFruitById(Integer id);
    Fruit getFruitById(Integer id);
    List<Fruit> getAllFruit();
    void clearFruitTable();
    void dbRefresh();
}
