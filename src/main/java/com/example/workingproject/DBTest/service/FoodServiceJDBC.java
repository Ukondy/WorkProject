package com.example.workingproject.DBTest.service;

import UtilsClass.Fruit;
import com.example.workingproject.DBTest.dao.FoodDao;
import com.example.workingproject.DBTest.dao.FoodDaoJDBCImpl;

import java.util.List;

public class FoodServiceJDBC implements FoodService {
    private final FoodDao foodDao = new FoodDaoJDBCImpl();

    @Override
    public void createFruitTable() {foodDao.createFruitTable();}

    @Override
    public void dropFruitTable() {foodDao.dropFruitTable();}

    @Override
    public void addFruit(String name, String type, Integer isExotic) {foodDao.addFruit(name, type, isExotic);}

    @Override
    public void removeFruitById(Integer id) {foodDao.removeFruitById(id);}

    @Override
    public Fruit getFruitById(Integer id) {return foodDao.getFruitById(id);}

    @Override
    public List<Fruit> getAllFruit() {return foodDao.getAllFruits();}

    @Override
    public void clearFruitTable() {foodDao.clearFruitTable();}

    @Override
    public void dbRefresh() {foodDao.dbRefresh();}
}
