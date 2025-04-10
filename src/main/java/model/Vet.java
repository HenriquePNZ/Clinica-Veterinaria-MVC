 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Time;

/**
 *
 * @author Hpanz
 */
public class Vet {

    private int id;
    private String nome;
    private String telefone;
    private String email;
    private String especialidade;
    private Time horarioInicio;
    private Time horarioFim;


    public Vet(int id, String nome, String telefone, String email, String especialidade, Time horariosInicio, Time horarioFim){
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;  
        this.email = email;
        this .especialidade = especialidade;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
    }

    public Time getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(Time horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public Time getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(Time horarioFim) {
        this.horarioFim = horarioFim;
    }
    
     public int getId() {
        return id;
    }
     
     public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
        public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

}
