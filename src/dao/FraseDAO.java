package dao;

import model.Frase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

public interface FraseDAO {
    void inserisciFrase(String frase, int ordine, String ssn_autore, String titolo) throws Exception;
    public void selectFrasi(int id_pagina, ArrayList<Frase> listaFrasi) throws Exception;
    public void inserisciFraseMod(String parola, int ordine, int versione, String ssn_utente, int id_pagina, int fraseModificata) throws Exception;
    public String selectTesto(int id_pagina) throws Exception;
    public int selectOrdineFrase(int id_pagina) throws Exception;
    public void propostaFraseMod(String parola, int ordine, int versione, String ssn_utente, int id_pagina, int fraseModificata) throws Exception;
    public String  cronologiaFrasi(int id_pagina, int ordine) throws Exception;
    public void listaProposte(ArrayList<String> lista, ArrayList<Integer> lIdfrase) throws Exception;
    public void creaCollegamento(int id_pagina, int id_frase) throws Exception;
    public String visualizzaCollegamento(int id_frase) throws Exception;
    public void versioniPagina(Map<String, Timestamp> versioni, int id_pagina) throws Exception;
    public String storicoPagina(Timestamp timestamp, int id_pagina) throws Exception;
    public void updateModAccettato(int id_frase) throws Exception;
    public void updateModRifiutato(int id_frase) throws Exception;

    }
