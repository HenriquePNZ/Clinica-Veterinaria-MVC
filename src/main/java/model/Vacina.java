package model;

import java.sql.Date;

public class Vacina {
    private int id;
    private String medicacao;
    private Date dataVacinacao;
    private String lote;
    private Date dataReforco;
    private int idPet;

    
    public Vacina(int id, String medicacao, Date dataVacinacao, String lote, Date dataReforco, int idPet){
        this.id = id;
        this.medicacao = medicacao;
        this.dataVacinacao = dataVacinacao;
        this.lote = lote;
        this.dataReforco = dataReforco;
        this.idPet = idPet;
    }

    public int getIdPet() {
        return idPet;
    }

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }
    public int getId() {
        return id;
    }

    public String getMedicacao() {
        return medicacao;
    }

    public void setMedicacao(String medicacao) {
        this.medicacao = medicacao;
    }

    public Date getDataVacinacao() {
        return dataVacinacao;
    }

    public void setDataVacinacao(Date dataVacinacao) {
        this.dataVacinacao = dataVacinacao;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getDataReforco() {
        return dataReforco;
    }

    public void setDataReforco(Date dataReforco) {
        this.dataReforco = dataReforco;
    }
    
    @Override
    public String toString() {        
        String desc = "Vacina{" + "medicacao=" + medicacao + ", dataVacinacao=" + dataVacinacao + ", lote=" + lote + ", dataReforco=" + dataReforco + ", idPet=" + idPet +'}';
        return desc;
    }  
    
}
