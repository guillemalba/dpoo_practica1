package edu.salleurl.Logic;

import javax.xml.crypto.Data;

public class Rapero {
    private String realName = null;
    private String stageName = null;
    private String birth;
    private String nationality = null;
    private int level = 0;
    private String photo = null;

    private int puntuacio = 0;


    public Rapero () {}

    public Rapero (String realName, String stageName, String birth, String nationality, int level, String photo) {
        this.realName = realName;
        this.stageName = stageName;
        this.birth = birth;
        this.nationality = nationality;
        this.level = level;
        this.photo = photo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getPuntuacio() {
        return puntuacio;
    }

    public void setPuntuacio(int puntuacio) {
        this.puntuacio = this.puntuacio + puntuacio;
    }
}
