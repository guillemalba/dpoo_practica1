package edu.salleurl.Logic;

import javax.xml.crypto.Data;

public class Rapero {
    private String realName = null;
    private String stageName = null;
    private String birth;
    private String nationality = null;
    private int level = 0;
    private String photo = null;

    private float puntuacio = 0;

    public Rapero () {}

    public Rapero (String realName, String stageName, String birth, String nationality, int level, String photo) {
        this.realName = realName;
        this.stageName = stageName;
        this.birth = birth;
        this.nationality = nationality;
        this.level = level;
        this.photo = photo;
    }

    /**
     * @Finalitat: Retornar el nom real del rapero
     * @Paràmetres: no
     * @Retorn: String
     */
    public String getRealName() {
        return realName;
    }

    /**
     * @Finalitat: Definir el nom real del rapero
     * @Paràmetres: String realName
     * @Retorn: no
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * @Finalitat: Retornar el nom artistic del rapero
     * @Paràmetres: no
     * @Retorn: String
     */
    public String getStageName() {
        return stageName;
    }

    /**
     * @Finalitat: Definir el nom artistic del rapero
     * @Paràmetres: String stageName
     * @Retorn: no
     */
    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    /**
     * @Finalitat: Retornar la data de naixement del rapero
     * @Paràmetres: no
     * @Retorn: String
     */
    public String getBirth() {
        return birth;
    }

    /**
     * @Finalitat: Definir la data de naixement del rapero
     * @Paràmetres: String birth
     * @Retorn: no
     */
    public void setBirth(String birth) {
        this.birth = birth;
    }

    /**
     * @Finalitat: Retornar la nacionalitat del rapero
     * @Paràmetres: no
     * @Retorn: String
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * @Finalitat: Definir la nacionalitat del rapero
     * @Paràmetres: String nationality
     * @Retorn: no
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * @Finalitat: Retornar el nivell del rapero
     * @Paràmetres: no
     * @Retorn: int
     */
    public int getLevel() {
        return level;
    }

    /**
     * @Finalitat: Definir el nivell del rapero
     * @Paràmetres: int level
     * @Retorn: no
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @Finalitat: Retornar la foto del rapero
     * @Paràmetres: no
     * @Retorn: String
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @Finalitat: Definir la foto del rapero
     * @Paràmetres: String photo
     * @Retorn: no
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * @Finalitat: Retornar la puntuacio del rapero
     * @Paràmetres: no
     * @Retorn: float
     */
    public float getPuntuacio() {
        return puntuacio;
    }

    /**
     * @Finalitat: Definir la puntuacio del rapero
     * @Paràmetres: float puntuacio
     * @Retorn: no
     */
    public void setPuntuacio(float puntuacio) {
        this.puntuacio = this.puntuacio + puntuacio;
    }
}
