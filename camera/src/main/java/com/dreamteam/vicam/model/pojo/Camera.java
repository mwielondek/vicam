package com.dreamteam.vicam.model.pojo;

/**
 * Created by fsommar on 2014-04-01.
 */
public class Camera {

    private String ip, name;
    private short port;
    private int id;

    public Camera(String ip, String name, short port, int id) {
        this.ip = ip;
        this.name = name;
        this.port = port;
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getPort() {
        return port;
    }

    public void setPort(short port) {
        this.port = port;
    }

    public int getId() {
        return id;
    }
}
