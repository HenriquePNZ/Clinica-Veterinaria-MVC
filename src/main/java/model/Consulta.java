package model;

import java.sql.Date;
import java.sql.Time;

public class Consulta {

    private int id;
    private Date data;
    private Time horario;
    private String categoria;
    private int idVet;
    private int idPet;

    public Consulta(int id, Date data, Time horario, String categoria, int idVet, int idPet) {
        this.id = id;
        this.data = data;
        this.horario = horario;
        this.categoria = categoria;
        this.idVet = idVet;
        this.idPet = idPet;
    }

    public int getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHorario() {
        return horario;
    }

    public void setHorario(Time horario) {
        this.horario = horario;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    public int getIdVet() {
        return idVet;
    }

    public void setIdVet(Integer idVet) {
        this.idVet = idVet;
    }

   
    public int getIdPet() {  
        return idPet;
    }
    
    public void setIdPet(Integer idPet){
        this.idPet = idPet;
    }
    
       @Override
    public String toString() {
        return "Consulta{\nCódigo Consulta: " + id
                + "\nData: " + data
                + "\nHorário: " + horario
                + "\nCategoria Consulta: " + categoria
                + "\nid Veterinário: " + idVet
                + "\nid Animal: " + idPet
                + "\n}";
    }
}
