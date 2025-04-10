package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConsultaDAO extends DAO {
    private static ConsultaDAO instance;

    private ConsultaDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static ConsultaDAO getInstance() {
        return (instance==null?(instance = new ConsultaDAO()):instance);
    }

// CRUD    
   public Consulta create(Date data, Time horario, String categoria, int idVet, int idPet) {
    try {
        PreparedStatement stmt;
        stmt = DAO.getConnection().prepareStatement("INSERT INTO consulta (data, horario, categoria, idVet, idPet) VALUES (?,?,?,?,?)");
        stmt.setDate(1, data);
        stmt.setTime(2, horario);
        stmt.setString(3, categoria);
        stmt.setInt(4, idVet); 
        stmt.setInt(5, idPet); 
        executeUpdate(stmt);
    } catch (SQLException ex) {
        Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return this.retrieveById(lastId("consulta","id"));
}

 // Build Object
    private Consulta buildObject(ResultSet rs) {
        Consulta consulta = null;
        try {  
            consulta = new Consulta(
                rs.getInt("id"),
                rs.getDate("data"),
                rs.getTime("horario"),
                rs.getString("categoria"),
                rs.getInt("idVet"),
                rs.getInt("idPet")
            );
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return consulta;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Consulta> consultas = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                consultas.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return consultas;
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM consulta");
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM consulta WHERE id = " + lastId("consulta","id"));
    }

    // RetrieveById
    public Consulta retrieveById(int id) {
        List<Consulta> consultas = this.retrieve("SELECT * FROM consulta WHERE id = " + id);
        return (consultas.isEmpty()?null:consultas.get(0));
    }
    
      public Consulta retrieveByPetId(int idPet) {
        List<Consulta> consultas = this.retrieve("SELECT * FROM "
                + "consulta WHERE idPet = " + idPet);
        return (consultas.isEmpty() ? null : consultas.get(0));
    }
    
    public Consulta retrieveByVetId(int idVet) {
        List<Consulta> consultas = this.retrieve("SELECT * FROM "
                + "consulta WHERE idVet = " + idVet);
        return (consultas.isEmpty() ? null : consultas.get(0));
    }   

    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM consulta WHERE nome LIKE '%" + nome + "%'");
    }    
    
      public List retrieveAllByPetId(int idPet) {
        return this.retrieve("SELECT * FROM "
                + "consulta WHERE idPet = " + idPet);
    }

    public List retrieveAllByVetId(int idVet) {
        return this.retrieve("SELECT * FROM "
                + "consulta WHERE idVet = " + idVet);

    }
    
    public boolean existeConsulta(Date dataConsulta, Time horarioConsulta, String categoria, int idVet, int idPet) {
    boolean consultaExiste = false;
    try {
        PreparedStatement stmt = DAO.getConnection().prepareStatement(
            "SELECT * FROM consulta WHERE data = ? AND horario = ? AND categoria = ? AND idVet = ?");
        stmt.setDate(1, dataConsulta);
        stmt.setTime(2, horarioConsulta);
        stmt.setString(3, categoria);
        stmt.setInt(4, idVet);
      

        System.out.println("Verificando consulta existente: " +
            "Data = " + dataConsulta +
            ", Horário = " + horarioConsulta +
            ", Categoria = " + categoria +
            ", Veterinário ID = " + idVet);

        ResultSet rs = stmt.executeQuery();
        consultaExiste = rs.next();

        System.out.println("Consulta encontrada? " + consultaExiste);
        
        rs.close();
        stmt.close();
    } catch (SQLException ex) {
        Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return consultaExiste;
}
    
    public boolean existeConsulta(Date dataConsulta, Time horarioConsulta, String categoria, int idVet) {
    boolean consultaExiste = false;
    try {
        PreparedStatement stmt = DAO.getConnection().prepareStatement(
            "SELECT * FROM consulta WHERE data = ? AND horario = ? AND categoria = ? AND idVet = ?");
        stmt.setDate(1, dataConsulta);
        stmt.setTime(2, horarioConsulta);
        stmt.setString(3, categoria);
        stmt.setInt(4, idVet);

        ResultSet rs = stmt.executeQuery();
        consultaExiste = rs.next();

        rs.close();
        stmt.close();
    } catch (SQLException ex) {
        Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return consultaExiste;
}




        
    // Updade
    public void update(Consulta consulta) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE consulta SET data=?, horario=?, categoria=?, idVet=?, idPet=? WHERE id=?");
            stmt.setDate(1, consulta.getData());
            stmt.setTime(2, consulta.getHorario());
            stmt.setString(3, consulta.getCategoria());
            stmt.setInt(4, consulta.getIdVet());
            stmt.setInt(5, consulta.getIdPet());
            executeUpdate(stmt);
            
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    // Delete   
    public void delete(Consulta consulta) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM consulta WHERE id = ?");
            stmt.setInt(1, consulta.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

}
