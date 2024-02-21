package impPostgresDAO;

import controller.Controller;
import dao.PaginaDAO;
import database.ConnessioneDatabase;
import model.Pagina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaginaDAOImpl implements PaginaDAO {
    private Connection connection;
    public void inserisciPagina(String titolo, String ssn_autore) throws Exception{
        try {
            PreparedStatement inserisciPaginaPS = connection.prepareStatement(
                    "INSERT INTO pagina (titolo,dataora,ssn_autore) VALUES ('" + titolo + "',current_timestamp,'" + ssn_autore + "')");
            int row = inserisciPaginaPS.executeUpdate();
            inserisciPaginaPS.close();
            connection.close();
        } catch (Exception e){
            System.out.println("Errore: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
public void selectPagina(String ssn_autore, ArrayList<Pagina> listaPagine) throws Exception{
    try {
        PreparedStatement selectPaginaPS = connection.prepareStatement(
                "SELECT id_pagina,titolo,ssn_autore FROM pagina WHERE ssn_autore = '" + ssn_autore + "'");
        ResultSet rs = selectPaginaPS.executeQuery();
        while (rs.next()) {
            Pagina pagina = new Pagina(
            rs.getInt("id_pagina"),
            rs.getString("titolo"),
            rs.getString("ssn_autore")
            );
            listaPagine.add(pagina);
        }
        rs.close();
        selectPaginaPS.close();
        connection.close();
    } catch (Exception e){
        System.out.println("Errore: " + e.getMessage());
        throw new Exception(e.getMessage());
    }
}
public void rimuoviPagina(int id_pagina) throws Exception{
    try {
        PreparedStatement eliminaPaginaPS = connection.prepareStatement(
                "DELETE FROM pagina WHERE id_pagina = '" + id_pagina +"'");
        int row = eliminaPaginaPS.executeUpdate();
        eliminaPaginaPS.close();
        connection.close();
    } catch (Exception e){
        System.out.println("Errore: " + e.getMessage());
        throw new Exception(e.getMessage());
    }
}
public int selectIDpagina(Pagina pagina) throws Exception{
    int row = 0;
    try {
        PreparedStatement selectIDpaginaPS = connection.prepareStatement(
                "SELECT id_pagina FROM pagina WHERE ssn_autore = '" + pagina.getSsn_autore() + "' AND titolo = '" + pagina.getTitolo() +"'");
        ResultSet rs = selectIDpaginaPS.executeQuery();
        rs.next();
        if(rs.getRow() != 0){
             row = rs.getInt("id_pagina");
        }
        rs.close();
        selectIDpaginaPS.close();
        connection.close();
    } catch (Exception e){
        System.out.println("Errore: " + e.getMessage());
        throw new Exception(e.getMessage());
    }
    return row;
}
    public Pagina cercaPagina(String titolo) throws Exception{
        try{
            PreparedStatement cercaPaginaPS = connection.prepareStatement(
                    "SELECT id_pagina,titolo,ssn_autore FROM pagina WHERE titolo = '" + titolo + "'");
            ResultSet rs = cercaPaginaPS.executeQuery();
            Pagina pagina;
            if(rs.next()) {
                pagina = new Pagina(
                        rs.getInt("id_pagina"),
                        rs.getString("titolo"),
                        rs.getString("ssn_autore")
                );
            } else{
                pagina = null;
            }
            rs.close();
            cercaPaginaPS.close();
            connection.close();
            return pagina;
        } catch (Exception e){
            System.out.println("Errore: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
    public PaginaDAOImpl() {
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
