package com.maxpallu.go4lunch.api;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    private String name;
    private String adress;
    private List<String> clients;

    public Restaurant() {}

    public Restaurant(String name, String adress) {
        this.name = name;
        this.adress = adress;
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
}
