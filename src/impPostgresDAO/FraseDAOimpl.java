package impPostgresDAO;

import dao.FraseDAO;
import database.ConnessioneDatabase;
import model.Frase;
import model.Pagina;

import java.security.spec.ECField;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class FraseDAOimpl implements FraseDAO {
    private Connection connection;
    public void inserisciFrase(String frase, int ordine, String ssn_autore, String titolo) throws Exception{
        try {
            PreparedStatement inserisciFrasePS = connection.prepareStatement(
                    "INSERT INTO frase (parola,dataora,stato,ordinefrase,ssn_utente,id_pagina) SELECT '" + frase + "', current_timestamp,'accettato'," + ordine + ",'" + ssn_autore + "', id_pagina " +
                            "FROM pagina  WHERE titolo = '" + titolo + "' AND ssn_autore = '" + ssn_autore +"'");
            int row = inserisciFrasePS.executeUpdate();
            inserisciFrasePS.close();
            connection.close();
        } catch (Exception e){
            System.out.println("Errore: " + e.getMessage());
            throw new Exception(e.getMessage());
           }
    }
    public void selectFrasi(int id_pagina, ArrayList<Frase> listaFrasi) throws Exception{
        try {
            PreparedStatement selectFrasiPS = connection.prepareStatement(
                    "SELECT id_frase,parola,stato,ordinefrase,versione,ssn_utente,id_pagina FROM frase WHERE id_pagina = " + id_pagina +" AND stato = 'accettato' ORDER BY ordinefrase");
            ResultSet rs = selectFrasiPS.executeQuery();
            while (rs.next()) {
                Frase frase = new Frase(
                rs.getInt("id_frase"),
                rs.getString("parola"),
                rs.getString("stato"),
                rs.getInt("ordinefrase"),
                rs.getInt("versione"),
                rs.getString("ssn_utente"),
                rs.getInt("id_pagina")
                );
                listaFrasi.add(frase);
            }
            rs.close();
            selectFrasiPS.close();
            connection.close();
        } catch (Exception e){
            System.out.println("Errore: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
    public void inserisciFraseMod(String parola, int ordine, int versione, String ssn_utente, int id_pagina, int fraseModificata) throws Exception{
        try {
            PreparedStatement inserisciFrasePS = connection.prepareStatement(
                    "INSERT INTO frase (parola,dataora,stato,ordinefrase,versione,dataoraapprovata,ssn_utente,id_pagina,id_frasemod) VALUES ('" + parola + "', current_timestamp,'accettato'," + ordine + "," + versione + "," +
                            "current_timestamp, '" + ssn_utente + "'," + id_pagina + "," + fraseModificata + ")");
            int row = inserisciFrasePS.executeUpdate();
            inserisciFrasePS.close();
            connection.close();
        } catch (Exception e){
            System.out.println("Errore: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
    public String selectTesto(int id_pagina) throws Exception{
        String testo = null;
        try{
            PreparedStatement selectTestoPS = connection.prepareStatement(
                    "SELECT testo_pagina(" + id_pagina + ")");
            ResultSet rs = selectTestoPS.executeQuery();
            if(rs.next()){
                testo = rs.getString(1);
            }
            rs.close();
            selectTestoPS.close();
            connection.close();
        } catch (Exception e){
            System.out.println("Errore: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
        return testo;
    }
    public int selectOrdineFrase(int id_pagina) throws Exception{
        int ordine = 0;
        try{
            PreparedStatement selectOrdineFrasePS = connection.prepareStatement(
                    "SELECT ordinefrase FROM frase WHERE id_pagina = " + id_pagina + " AND stato = 'accettato'");
            ResultSet rs = selectOrdineFrasePS.executeQuery();
            while(rs.next()){
                ordine = rs.getInt("ordinefrase");
            }
            rs.close();
            selectOrdineFrasePS.close();
            connection.close();
        } catch (Exception e){
            System.out.println("Errore: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
        return ordine;
    }
    public void propostaFraseMod(String parola, int ordine, int versione, String ssn_utente, int id_pagina, int fraseModificata) throws Exception {
        try {
            PreparedStatement inserisciFrasePS = connection.prepareStatement(
                    "INSERT INTO frase (parola,dataora,stato,ordinefrase,versione,ssn_utente,id_pagina,id_frasemod) VALUES ('" + parola + "', current_timestamp,'in_attesa'," + ordine + "," + versione + "," +
                            "'" + ssn_utente + "'," + id_pagina + "," + fraseModificata + ")");
            int row = inserisciFrasePS.executeUpdate();
            inserisciFrasePS.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public String  cronologiaFrasi(int id_pagina, int ordine) throws Exception{
        String testo = null;
        try {
            PreparedStatement cronologiaFrasiPS = connection.prepareStatement(
                    "SELECT cronologia_frasi(" + id_pagina + "," + ordine +")");
            ResultSet rs = cronologiaFrasiPS.executeQuery();
            if(rs.next()){
                testo = rs.getString(1);
            }
            rs.close();
            cronologiaFrasiPS.close();
            connection.close();
        } catch (Exception e){
            System.out.println("Errore: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
        return testo;
    }
    public void listaProposte(ArrayList<String> lista, ArrayList<Integer> lIdfrase) throws Exception{
        try{
            PreparedStatement listaPropostePS = connection.prepareStatement(
                    "SELECT F1.parola AS F1_parola, F2.parola AS F2_parola, P.titolo AS P_titolo, F2.id_frase AS F2_id_frase " +
                            "FROM frase AS F1, frase AS F2, pagina AS P WHERE F1.id_frase = F2.id_frasemod AND F1.stato = 'accettato' AND F2.stato = 'in_attesa' AND F1.id_pagina = P.id_pagina ORDER BY F2.dataora");
            ResultSet rs = listaPropostePS.executeQuery();
            while(rs.next()){
                String frase1 = rs.getString("F1_parola");
                String frase2 = rs.getString("F2_parola");
                String  titolo = rs.getString("P_titolo");
                int id_frase = rs.getInt("F2_id_frase");
                lista.add("Frase originale: " + frase1 + "\nModifica proposta: " + frase2 + " \nTitolo pagina: " + titolo);
                lIdfrase.add(id_frase);
            }
            rs.close();
            listaPropostePS.close();
            connection.close();
        } catch (Exception e){
            System.out.println("Errore: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public void creaCollegamento(int id_pagina, int id_frase) throws Exception{
        try{
            PreparedStatement creaCollegamentoPS = connection.prepareStatement(
                    "UPDATE frase SET id_collegamento = " + id_pagina + " WHERE id_frase = " + id_frase);
            int row = creaCollegamentoPS.executeUpdate();
            creaCollegamentoPS.close();
            connection.close();
        } catch (Exception e){
            System.out.println("Errore: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
    public String visualizzaCollegamento(int id_frase) throws Exception{
        String collegamento = null;
        try {
            PreparedStatement visualizzaCollegamento = connection.prepareStatement(
                    "SELECT P.titolo AS P_titolo FROM frase AS F, pagina AS P WHERE P.id_pagina = F.id_collegamento AND F.id_frase = " + id_frase + " ");
            ResultSet rs = visualizzaCollegamento.executeQuery();
            if(rs.next()) {
                collegamento = rs.getString("P_titolo");
            }
            rs.close();
            visualizzaCollegamento.close();
            connection.close();
        } catch (Exception e){
            System.out.println("Errore: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
        return collegamento;
    }

    public void versioniPagina(Map<String, Timestamp> versioni, int id_pagina) throws Exception {
        try{
            PreparedStatement versioniPaginaPS = connection.prepareStatement(
                    "SELECT dataoraapprovata FROM frase WHERE dataoraapprovata is not null AND id_pagina = " + id_pagina + " ");
            ResultSet rs = versioniPaginaPS.executeQuery();
            while(rs.next()){
                Timestamp dataora = rs.getTimestamp("dataoraapprovata");
                versioni.put(dataora.toString(), dataora);
            }
            rs.close();
            versioniPaginaPS.close();
            connection.close();
        } catch (Exception e){
            System.out.println("Errore: " +  e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public String storicoPagina(Timestamp timestamp, int id_pagina) throws Exception{
        String testo = null;
        try {
            PreparedStatement storicoPaginaPS = connection.prepareStatement(
                    "SELECT storico_pagina('" + timestamp + "'," + id_pagina + ")");
            System.out.println("SQL: " + storicoPaginaPS.toString());
            ResultSet rs = storicoPaginaPS.executeQuery();
            if(rs.next()){
                testo = rs.getString(1);
            }
            rs.close();
            storicoPaginaPS.close();
            connection.close();
        } catch (Exception e){
            System.out.println("Errore: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
        return testo;
    }

    public void updateModAccettato(int id_frase) throws Exception{
        try{
            PreparedStatement updateModifica = connection.prepareStatement(
                    "UPDATE frase SET stato = 'accettato' , dataoraapprovata = current_timestamp WHERE id_frase = " + id_frase + " ");
            int row = updateModifica.executeUpdate();
            updateModifica.close();
            connection.close();
        } catch (Exception e){
            System.out.println("Errore: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
    public void updateModRifiutato(int id_frase) throws Exception{
        try{
            PreparedStatement updateModifica = connection.prepareStatement(
                    "UPDATE frase SET stato = 'rifiutato' WHERE id_frase = " + id_frase + " ");
            int row = updateModifica.executeUpdate();
            updateModifica.close();
            connection.close();
        } catch (Exception e){
            System.out.println("Errore: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
    public FraseDAOimpl() {
        try {
            connection =  ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
