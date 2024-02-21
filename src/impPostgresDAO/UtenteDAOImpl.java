package impPostgresDAO;

import model.Utente;
import dao.UtenteDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteDAOImpl implements UtenteDAO {

    private Connection connection;

    @Override
    public Utente leggiUtente(String ssn) {
        Utente utente = null;

        try {
            PreparedStatement leggiUtentePS = connection.prepareStatement(
                    "SELECT * FROM utente WHERE ssn ='"+ssn+"'");
            ResultSet rs = leggiUtentePS.executeQuery();
            rs.next();
            if (rs.getRow() != 0) {
                utente = new Utente(
                        rs.getString("ssn"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("mail"),
                        rs.getString("password")
                );
                rs.close();
                leggiUtentePS.close();
                connection.close();
           } else {
                throw new SQLException("Utente con ssn = " + ssn + " non trovato.");
           }
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return utente;
    }
    public void inserisciUtente(String ssn, String nome, String cognome, String mail, String password) throws Exception {
        try {
            PreparedStatement inserisciUtentePS = connection.prepareStatement(
                    "INSERT INTO utente VALUES ('"+ssn+"','"+nome+"','"+cognome+"','"+mail+"','"+password+"')");
            int row = inserisciUtentePS.executeUpdate();
            inserisciUtentePS.close();
            connection.close();
        } catch (Exception e){
            System.out.println("Errore: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public void eliminaUtente(String ssn) throws Exception{
        try {
            PreparedStatement eliminaUtentePS = connection.prepareStatement(
                    "DELETE FROM utente WHERE ssn = '" + ssn +"'");
            int row = eliminaUtentePS.executeUpdate();
            eliminaUtentePS.close();
            connection.close();
        } catch (Exception e){
            System.out.println("Errore: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
    public UtenteDAOImpl() {
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
