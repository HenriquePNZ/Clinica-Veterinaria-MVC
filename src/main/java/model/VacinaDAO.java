package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VacinaDAO extends DAO {
    private static VacinaDAO instance;

    private VacinaDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static VacinaDAO getInstance() {
        if (instance == null) {
            instance = new VacinaDAO();
        }
        return instance;
    }

    // CRUD    
    public Vacina create(String medicacao, Date dataVacinacao, String lote, Date dataReforco, int idPet) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement(
                "INSERT INTO vacina (medicacao, dataVacinacao, lote, dataReforco, idPet) VALUES (?,?,?,?,?)");
            stmt.setString(1, medicacao);
            stmt.setDate(2, dataVacinacao);
            stmt.setString(3, lote);
            stmt.setDate(4, dataReforco);
            stmt.setInt(5, idPet);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(VacinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("vacina", "id"));
    }

    private Vacina buildObject(ResultSet rs) {
        Vacina vacina = null;
        try {
            vacina = new Vacina(
                rs.getInt("id"),
                rs.getString("medicacao"),
                rs.getDate("dataVacinacao"),
                rs.getString("lote"),
                rs.getDate("dataReforco"),
                rs.getInt("idPet")
            );
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return vacina;
    }

    // Generic Retriever
    public List<Vacina> retrieve(String query) {
        List<Vacina> vacinas = new ArrayList<>();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                vacinas.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return vacinas;
    }

    // RetrieveAll
    public List<Vacina> retrieveAll() {
        return this.retrieve("SELECT * FROM vacina");
    }

    // RetrieveLast
    public List<Vacina> retrieveLast() {
        return this.retrieve("SELECT * FROM vacina WHERE id = " + lastId("vacina", "id"));
    }

    // RetrieveById
    public Vacina retrieveById(int id) {
        List<Vacina> vacinas = this.retrieve("SELECT * FROM vacina WHERE id = " + id);
        return (vacinas.isEmpty() ? null : vacinas.get(0));
    }

    // RetrieveBySimilarName
    public List<Vacina> retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM vacina WHERE nome LIKE '%" + nome + "%'");
    }

    // RetrieveAllByPetId
    public List<Vacina> retrieveAllByPetId(int idPet) {
        String query = "SELECT * FROM vacina WHERE idPet = ?";
        List<Vacina> vacinas = new ArrayList<>();

        try {
            PreparedStatement stmt = DAO.getConnection().prepareStatement(query);
            stmt.setInt(1, idPet);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                vacinas.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar vacinas por ID do pet: " + e.getMessage());
        }

        return vacinas;
    }

    // Update
    public void update(Vacina vacina) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement(
                "UPDATE vacina SET medicacao=?, dataVacinacao=?, lote=?, dataReforco=? WHERE id=?");
            stmt.setString(1, vacina.getMedicacao());
            stmt.setDate(2, vacina.getDataVacinacao());
            stmt.setString(3, vacina.getLote());
            stmt.setDate(4, vacina.getDataReforco());
            stmt.setInt(5, vacina.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    // Delete   
    public void delete(Vacina vacina) {
        try {
            PreparedStatement stmt = DAO.getConnection().prepareStatement("DELETE FROM vacina WHERE id = ?");
            stmt.setInt(1, vacina.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    public void deleteByPetId(int idPet) {
        try {
            PreparedStatement stmt = DAO.getConnection().prepareStatement("DELETE FROM vacina WHERE idPet = ?");
            stmt.setInt(1, idPet);
            executeUpdate(stmt);
            System.out.println("Vacinas associadas ao pet com ID " + idPet + " foram excluídas.");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir vacinas por ID do pet: " + e.getMessage());
        }
    }

}
