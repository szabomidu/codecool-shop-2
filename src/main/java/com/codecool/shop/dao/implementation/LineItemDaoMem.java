package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.LineItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LineItemDaoMem implements LineItemDao {
    private List<LineItem> data = new ArrayList<>();
    private static LineItemDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private LineItemDaoMem() {
    }

    public static LineItemDaoMem getInstance() {
        if (instance == null) {
            instance = new LineItemDaoMem();
        }
        return instance;
    }

    @Override
    public void add(LineItem lineItem) {
        lineItem.setId(data.size() + 1);
        data.add(lineItem);
    }

    @Override
    public void addAll(LineItem... lineItems) {
        Arrays.stream(lineItems).forEach(this::add);
    }

    @Override
    public LineItem find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<LineItem> getAll() {
        return data;
    }
}
