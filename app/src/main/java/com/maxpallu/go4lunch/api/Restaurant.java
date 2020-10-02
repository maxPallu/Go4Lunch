package com.maxpallu.go4lunch.api;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    private String id;
    private String name;
    private String adress;
    private List<String> clients;

    public Restaurant() {}

    public Restaurant(String name, String id) {
        this.name = name;
        this.id = id;
        this.clients = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public List<String> getClients() {
        return clients;
    }

    public void setClients(List<String> clients) {
        this.clients = clients;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
