package controller;

import gui.ProfiloUtente;
import impPostgresDAO.FraseDAOimpl;
import impPostgresDAO.PaginaDAOImpl;
import model.Frase;
import model.Pagina;
import model.Utente;
import impPostgresDAO.UtenteDAOImpl;

import javax.swing.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Controller {

    public Utente utente;

    public Pagina pagina;
    public ArrayList<Pagina> listaPagine;
    public ArrayList<Frase> listaFrasi;
    public Frase fraseDaModificare;
    public ArrayList<String> listaProposte;
    private ArrayList<Integer> lIdProposteMod;

    public Frase frase;
    public Map<String, Timestamp> versioni;

    public String getNomeUtente() {
        return (this.utente.getNome() + " " + this.utente.getCognome());
    }

    public String login(String ssn, String password) {
        UtenteDAOImpl utenteDao = new UtenteDAOImpl();
        this.utente = utenteDao.leggiUtente(ssn);
        if (this.utente != null) {
            System.out.println("Utente: " + this.utente.getNome());
            if (!this.utente.getPassword().equals(password)) {
                return "Password errata";
            } else{
                return "";
            }
        } else {
            return "Utente non trovato";
        }
    }

    public boolean inserisciUtente(String ssn, String nome, String cognome, String mail, String password) {
        this.utente = new Utente(ssn, nome, cognome, mail, password);
        UtenteDAOImpl utenteDAO = new UtenteDAOImpl();
        try {
            utenteDAO.inserisciUtente(ssn, nome, cognome, mail, password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean eliminaUtente() {
        UtenteDAOImpl utenteDAO = new UtenteDAOImpl();
        String ssn = this.utente.getSsn();
        try {
            utenteDAO.eliminaUtente(ssn);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean inserisciPagina(String text) {
        String ssn_autore = this.utente.getSsn();
        this.pagina = new Pagina(text, ssn_autore);
        PaginaDAOImpl paginaDAO = new PaginaDAOImpl();
        try {
            paginaDAO.inserisciPagina(text, ssn_autore);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<String> titoliPagine() {
        ArrayList<String> titoli = new ArrayList<String>();
        this.listaPagine = new ArrayList<Pagina>();
        PaginaDAOImpl paginaDAO = new PaginaDAOImpl();
        try {
            paginaDAO.selectPagina(this.utente.getSsn(), this.listaPagine);
            for (Pagina pagina : this.listaPagine) {
                titoli.add(pagina.getTitolo());
            }
        } catch (Exception e) {
            titoli = null;
        }
        return titoli;
    }

    public boolean rimuoviPagina(int index) {
        PaginaDAOImpl paginaDAO = new PaginaDAOImpl();
        try {
            this.pagina = listaPagine.get(index);
            int id = this.pagina.getId_pagina();
            paginaDAO.rimuoviPagina(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean inserisciFrase(String text, int ordine, int index) {
        Pagina pagina = this.listaPagine.get(index);
        return inserisciFrase(text, ordine, pagina);

    }
    public boolean inserisciFrase(String text, int ordine) {
        return inserisciFrase(text, ordine, this.pagina);
    }
    private boolean inserisciFrase(String text, int ordine, Pagina pagina) {
        FraseDAOimpl fraseDAOimpl = new FraseDAOimpl();
        try {
            fraseDAOimpl.inserisciFrase(text, ordine, pagina.getSsn_autore(), pagina.getTitolo());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public ArrayList<String> frasiSingole(int index) {
        ArrayList<String> frasi = new ArrayList<String>();
        this.listaFrasi = new ArrayList<Frase>();
        FraseDAOimpl fraseDAOimpl = new FraseDAOimpl();
        this.pagina = listaPagine.get(index);
        try {
            fraseDAOimpl.selectFrasi(this.pagina.getId_pagina(), this.listaFrasi);
            for (Frase frase : this.listaFrasi) {
                frasi.add(frase.getParola());
            }
        } catch (Exception e) {
            frasi = null;
        }
        return frasi;
    }

    public boolean inserisciFraseModificata(String frase, int index){
        FraseDAOimpl fraseDAOimpl = new FraseDAOimpl();
        this.fraseDaModificare = this.listaFrasi.get(index);
        if(!frase.equals("")){
            try{
                fraseDAOimpl.inserisciFraseMod(frase,this.fraseDaModificare.getOrdine(),this.fraseDaModificare.getVersione() + 1,this.utente.getSsn(), this.pagina.getId_pagina(), this.fraseDaModificare.getId_frase());
                return true;
            }catch (Exception e){
                return false;
            }
        } else{
            return false;
        }
    }
    public String visualizzaTesto(int index){
        String testo;
        this.pagina = listaPagine.get(index);
        FraseDAOimpl fraseDAOimpl = new FraseDAOimpl();
        try{
            testo = fraseDAOimpl.selectTesto(this.pagina.getId_pagina());
        } catch(Exception e){
            testo = null;
        }
        return testo;
    }
    public int prendiOrdine(){
        int ordine;
        FraseDAOimpl fraseDAOimpl = new FraseDAOimpl();
        try {
            ordine = fraseDAOimpl.selectOrdineFrase(this.pagina.getId_pagina());
        } catch (Exception e){
            ordine = 0;
        }
        return ordine;
    }
    public boolean cercaPagina(String titolo) {
        PaginaDAOImpl paginaDAO = new PaginaDAOImpl();
        try{
            this.pagina = paginaDAO.cercaPagina(titolo);
            if(this.pagina == null){
                return false;
            } else {
                return true;
            }
        } catch (Exception e){
            return false;
        }
    }
    public boolean controlloPaginaSSN(){
        if(this.pagina.getSsn_autore().equals(this.utente.getSsn())){
            return false;
        } else{
            return true;
        }
    }
    public ArrayList<String> frasiRicerca() {
        ArrayList<String> frasi = new ArrayList<String>();
        this.listaFrasi = new ArrayList<Frase>();
        FraseDAOimpl fraseDAOimpl = new FraseDAOimpl();
        try {
            fraseDAOimpl.selectFrasi(this.pagina.getId_pagina(), this.listaFrasi);
            for (Frase frase : this.listaFrasi) {
                frasi.add(frase.getParola());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            frasi = null;
        }
        return frasi;
    }
    public boolean proponiFraseMod(String frase, int index){
        FraseDAOimpl fraseDAOimpl = new FraseDAOimpl();
        this.fraseDaModificare = this.listaFrasi.get(index);
        if(!frase.equals("")){
            try{
                fraseDAOimpl.propostaFraseMod(frase,this.fraseDaModificare.getOrdine(),this.fraseDaModificare.getVersione() + 1,this.utente.getSsn(), this.pagina.getId_pagina(), this.fraseDaModificare.getId_frase());
                return true;
            }catch (Exception e){
                return false;
            }
        } else{
            return false;
        }
    }
    public String cronologiaFrasi(int index){
        String testo;
        FraseDAOimpl fraseDAOimpl = new FraseDAOimpl();
        Frase frase = this.listaFrasi.get(index);
        try{
            testo = fraseDAOimpl.cronologiaFrasi(this.pagina.getId_pagina(), frase.getOrdine());
            System.out.println(testo);
        } catch(Exception e){
            testo = null;
        }
        return testo;
    }

    public ArrayList<String> visualizzaProposte(){
        this.listaProposte = new ArrayList<>();
        this.lIdProposteMod = new ArrayList<>();
        FraseDAOimpl fraseDAOimpl = new FraseDAOimpl();
        try{
            fraseDAOimpl.listaProposte(this.listaProposte,this.lIdProposteMod);
        } catch (Exception e){
            this.listaProposte = null;
        }
        return this.listaProposte;
    }
    public boolean creaCollegamento(int index){
        String check = visualizzaCollegamento(index);
        if(check == null) {
            try {
                FraseDAOimpl fraseDAOimpl2 = new FraseDAOimpl();
                fraseDAOimpl2.creaCollegamento(this.pagina.getId_pagina(), this.frase.getId_frase());
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }
    public String visualizzaCollegamento(int index){
        String collegamento;
        this.frase = listaFrasi.get(index);
        FraseDAOimpl fraseDAOimpl = new FraseDAOimpl();
        try {
            collegamento = fraseDAOimpl.visualizzaCollegamento(this.frase.getId_frase());
        }catch (Exception e){
            collegamento = null;
        }
        return collegamento;
    }

    public Map<String, Timestamp> versioniPagina(){
        this.versioni = new HashMap<>();
        FraseDAOimpl fraseDAOimpl = new FraseDAOimpl();
        try {
            fraseDAOimpl.versioniPagina(this.versioni,this.pagina.getId_pagina());
        } catch (Exception e){
            this.versioni = null;
        }
        return this.versioni;
    }

    public String storicoPagina(String dataora){
        String testo;
        FraseDAOimpl fraseDAOimpl = new FraseDAOimpl();
        try{
            testo = fraseDAOimpl.storicoPagina(this.versioni.get(dataora), this.pagina.getId_pagina());
        } catch (Exception e){
            testo = null;
        }
        return testo;
    }
    public boolean updateModifica(int index, boolean prova) {
        int idfrase = this.lIdProposteMod.get(index);
        FraseDAOimpl fraseDAOimpl = new FraseDAOimpl();
        if(prova){
            try {
                fraseDAOimpl.updateModAccettato(idfrase);
                return true;
            } catch (Exception e){
                return false;
            }
        } else{
            try {
                fraseDAOimpl.updateModRifiutato(idfrase);
                return true;
            } catch (Exception e){
                return false;
            }
        }
    }
    public String recuperaPagina(String titolo){
        boolean ricerca = cercaPagina(titolo);
        if(ricerca){
            FraseDAOimpl fraseDAOimpl = new FraseDAOimpl();
            String testo;
            try{
                testo = fraseDAOimpl.selectTesto(this.pagina.getId_pagina());
            } catch(Exception e) {
                testo = null;
            }
            return testo;
        } else{
            return "La pagina non esiste";
        }
    }
}