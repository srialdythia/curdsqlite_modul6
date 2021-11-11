package com.example.nameandhobby;

public class DataModel {
    private int id;
    private String nama, hobby;

    public DataModel(int id, String nama, String hobby) {
        this.id = id;
        this.nama = nama;
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "id=" + id +
                ", nama='" + nama + '\'' +
                ", hobby='" + hobby + '\'' +
                '}';
    }

    public void changeItem(String text){

    }

    public void changeString(String nama, String hobby){
        this.nama = nama;
        this.hobby = hobby;

        System.out.println("waloo"+this.nama + this.hobby);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}
