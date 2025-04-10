package model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DAO {
    public static final String DBURL = "jdbc:h2:./ClinicaMVCHenrique.db";
    private static Connection con;
    protected static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static Connection getConnection() {
        if (con == null) {
            try {
                con = DriverManager.getConnection(DBURL);
                if (con != null) {
                    DatabaseMetaData meta = con.getMetaData();
                }
            } catch (SQLException e) {
                System.err.println("Exception: " + e.getMessage());
            }
        }
        return con;
    }

    protected ResultSet getResultSet(String query) {
        Statement s = null;
        ResultSet rs = null;
        try {
            s = con.createStatement();
            rs = s.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        } finally {
            // You might want to close the statement in a different context
        }
        return rs;
    }

    protected int executeUpdate(PreparedStatement queryStatement) {
        int update = 0;
        try {
            update = queryStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        } finally {
            try {
                queryStatement.close(); // Fechando o PreparedStatement
            } catch (SQLException e) {
                System.err.println("Exception while closing statement: " + e.getMessage());
            }
        }
        return update;
    }

    protected int lastId(String tableName, String primaryKey) {
        Statement s = null;
        int lastId = -1;
        try {
            s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT MAX(" + primaryKey + ") AS id FROM " + tableName);
            if (rs.next()) {
                lastId = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return lastId;
    }

    public static void terminar() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    // Create tables
    protected final boolean createTable() {
        try {
            // Usando uma única conexão
            Connection connection = DAO.getConnection();
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection.");
            }
            PreparedStatement stmt;

            // Table tutor:
            stmt = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS tutor( " +
                "id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                "nome VARCHAR(255), " +
                "telefone VARCHAR(20), " +
                "email VARCHAR(255), " +
                "endereco VARCHAR(255))"
            );
            executeUpdate(stmt);
            System.out.println("Table 'tutor' created successfully or already exists.");

            // Table pet:
            stmt = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS pet( " +
                "id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                "nome VARCHAR(255), " +
                "raca VARCHAR(100), " +
                "Peso VARCHAR(255), " +
                "idade INTEGER, " +
                "sexo VARCHAR(10), " +
                "corPelagem VARCHAR(100), " +
                "estadoReprodutivo VARCHAR(100), " +
                "especie VARCHAR(50), " +
                "idTutor INT)"
            );
            executeUpdate(stmt);
            System.out.println("Table 'pet' created successfully or already exists.");

            // Table vet:
            stmt = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS vet( " +
                "id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                "nome VARCHAR(255), " +
                "telefone VARCHAR(20), " +
                "email VARCHAR(255), " +
                "especialidade VARCHAR(100), " +
                "horarioInicio TIME, " +
                "horarioFim TIME)" 
            );
            executeUpdate(stmt);
            System.out.println("Table 'vet' created successfully or already exists.");

            // Table consulta:
            stmt = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS consulta( " +
                "id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                "data DATE, " +
                "horario TIME, " +
                "categoria VARCHAR(100), " +
                "idVet INT, " +
                "idPet INT)"
            );
            executeUpdate(stmt);
            System.out.println("Table 'consulta' created successfully or already exists.");

            // Table vacina:
            stmt = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS vacina( " +
                "id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                "medicacao VARCHAR(255), " +
                "dataVacinacao DATE, " +
                "lote VARCHAR(100), " +
                "dataReforco DATE, " +
                "idPet INT)"
            );
            executeUpdate(stmt);
            System.out.println("Table 'vacina' created successfully or already exists.");
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Error creating tables", ex);
        }
        return false;
    }
}
