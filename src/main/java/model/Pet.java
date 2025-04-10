
package model;

public class Pet {
    private int id;
    private String nome;
    private String raca;
    private float peso;
    private int idade;
    private String sexo;
    private String corPelagem;
    private String estadoReprodutivo;
    private String especie;
    private int idTutor;

    public Pet(int id, String nome, String raca, float peso, int idade, String sexo, String corPelagem, String estadoReprodutivo, String especie, int idTutor) {
        this.id = id;
        this.nome = nome;
        this.raca = raca;
        this.peso = peso;
        this.idade = idade;
        this.sexo = sexo;
        this.corPelagem = corPelagem;
        this.estadoReprodutivo = estadoReprodutivo; 
        this.especie = especie;
        this.idTutor = idTutor;
    }

 
      public int getIdTutor() {
        return idTutor;
    }

    public void setIdTutor(int idTutor) {
        this.idTutor = idTutor;
    }
    
    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
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

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCorPelagem() {
        return corPelagem;
    }

    public void setCorPelagem(String corPelagem) {
        this.corPelagem = corPelagem;
    }
    
    public String getEstadoReprodutivo() {
        return estadoReprodutivo;
    }

    public void setEstadoReprodutivo(String estadoReprodutivo) {
        this.estadoReprodutivo = estadoReprodutivo;
    }
    
    @Override
public String toString() {
    return "Pet{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            ", raca='" + raca + '\'' +
            ", historicoPeso=" + peso +
            ", idade=" + idade +
            ", sexo='" + sexo + '\'' +
            ", corPelagem='" + corPelagem + '\'' +
            ", estadoReprodutivo='" + estadoReprodutivo + '\'' +
            ", especie='" + especie + '\'' +
            ", idTutor=" + idTutor +
            '}';
}

}