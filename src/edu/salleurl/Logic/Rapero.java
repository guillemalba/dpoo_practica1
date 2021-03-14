package edu.salleurl.Logic;

import javax.xml.crypto.Data;

/**
 * En aquesta classe es defineixen les variables i s'implementen les funcions relacionades amb la informacio dels raperos
 */
public class Rapero {
    private String realName = null;
    private String stageName = null;
    private String birth;
    private String nationality = null;
    private int level = 0;
    private String photo = null;

    private float puntuacio = 0;

    public Rapero (String realName, String stageName, String birth, String nationality, int level, String photo) {
        this.realName = realName;
        this.stageName = stageName;
        this.birth = birth;
        this.nationality = nationality;
        this.level = level;
        this.photo = photo;
    }

    /**
     * Retornar el nom real del rapero
     * @return String del mom real
     */
    public String getRealName() {
        return realName;
    }

    /**
     * Definir el nom real del rapero
     * @param realName string del nom real
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * Retornar el nom artistic del rapero
     * @return String del nom artistic
     */
    public String getStageName() {
        return stageName;
    }

    /**
     * Definir el nom artistic del rapero
     * @param stageName String del nom artistic
     */
    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    /**
     * Retornar la data de naixement del rapero
     * @return String amb la data de naixement
     */
    public String getBirth() {
        return birth;
    }

    /**
     * Definir la data de naixement del rapero
     * @param birth String del naixement
     */
    public void setBirth(String birth) {
        this.birth = birth;
    }

    /**
     * Retornar la nacionalitat del rapero
     * @return String de la nacionalitat
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Definir la nacionalitat del rapero
     * @param nationality String de la nacionalitat
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * Retornar el nivell del rapero
     * @return int del nivell
     */
    public int getLevel() {
        return level;
    }

    /**
     * Definir el nivell del rapero
     * @param level int del nivell
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Retornar la foto del rapero
     * @return String del link
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Definir la foto del rapero
     * @param photo String del link
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Retornar la puntuacio del rapero
     * @return float de la puntuacio
     */
    public float getPuntuacio() {
        return puntuacio;
    }

    /**
     * Definir la puntuacio del rapero
     * @param puntuacio float de la puntuacio
     */
    public void setPuntuacio(float puntuacio) {
        this.puntuacio = this.puntuacio + puntuacio;
    }
}
