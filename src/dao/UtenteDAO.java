package dao;

import model.Utente;

public interface UtenteDAO {
    Utente leggiUtente(String ssn);
    void inserisciUtente(String ssn, String nome, String cognome, String mail, String password) throws Exception;
    void eliminaUtente(String ssn) throws Exception;
}
