package model;

import java.sql.Timestamp;

public class Frase {
    private int id_frase;
    private String parola;
    private Timestamp dataOra;
    private String stato;
    private int ordine;
    private int versione;
    private Timestamp dataOraApprovata;
    private String ssn_utente;
    private int id_pagina;
    private int id_collegamento;
    private int fraseModificata;

    public Frase() {
    }

    public Frase(int id_frase, String parola, Timestamp dataOra, String stato, int ordine, int versione, String ssn_utente, int id_pagina) {
        this.id_frase = id_frase;
        this.parola = parola;
        this.dataOra = dataOra;
        this.stato = stato;
        this.ordine = ordine;
        this.versione = versione;
        this.ssn_utente = ssn_utente;
        this.id_pagina = id_pagina;
    }

    public Frase(int id_frase, String parola, String stato, int ordine, int versione, String ssn_utente, int id_pagina){
        this.id_frase = id_frase;
        this.parola = parola;
        this.stato = stato;
        this.ordine = ordine;
        this.versione = versione;
        this.ssn_utente = ssn_utente;
        this.id_pagina = id_pagina;
    }
    public int getId_frase() {
        return id_frase;
    }

    public void setId_frase(int id_frase) {
        this.id_frase = id_frase;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public Timestamp getDataOra() {
        return dataOra;
    }

    public void setDataOra(Timestamp dataOra) {
        this.dataOra = dataOra;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public int getOrdine() {
        return ordine;
    }

    public void setOrdine(int ordine) {
        this.ordine = ordine;
    }

    public int getVersione() {
        return versione;
    }

    public void setVersione(int versione) {
        this.versione = versione;
    }

    public Timestamp getDataOraApprovata() {
        return dataOraApprovata;
    }

    public void setDataOraApprovata(Timestamp dataOraApprovata) {
        this.dataOraApprovata = dataOraApprovata;
    }

    public String getSsn_utente() {
        return ssn_utente;
    }

    public void setSsn_utente(String ssn_utente) {
        this.ssn_utente = ssn_utente;
    }

    public int getId_pagina() {
        return id_pagina;
    }

    public void setId_pagina(int id_pagina) {
        this.id_pagina = id_pagina;
    }

    public int getId_collegamento() {
        return id_collegamento;
    }

    public void setId_collegamento(int id_collegamento) {
        this.id_collegamento = id_collegamento;
    }

    public int getFraseModificata() {
        return fraseModificata;
    }

    public void setFraseModificata(int fraseModificata) {
        this.fraseModificata = fraseModificata;
    }

    @Override
    public String toString() {
        return "Frase{" +
                "id_frase=" + id_frase +
                ", parola='" + parola + '\'' +
                ", dataOra=" + dataOra +
                ", stato='" + stato + '\'' +
                ", ordine=" + ordine +
                ", versione=" + versione +
                ", dataOraApprovata=" + dataOraApprovata +
                ", ssn_utente='" + ssn_utente + '\'' +
                ", id_pagina=" + id_pagina +
                ", id_collegamento=" + id_collegamento +
                ", fraseModificata=" + fraseModificata +
                '}';
    }
}
