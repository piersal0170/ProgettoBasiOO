package model;

import java.sql.Timestamp;

public class Pagina {
    private int id_pagina;
    private String titolo;
    private Timestamp dataOra;
    private String ssn_autore;

    public Pagina(int id_pagina, String titolo, Timestamp dataOra, String ssn_autore){
        this.id_pagina = id_pagina;
        this.titolo = titolo;
        this.dataOra = dataOra;
        this.ssn_autore = ssn_autore;
    }
    public Pagina(String titolo, String ssn_autore){
        this.titolo = titolo;
        this.ssn_autore = ssn_autore;
    }
    public Pagina(){}
    public Pagina(int id_pagina,String titolo, String ssn_autore){
        this.id_pagina = id_pagina;
        this.titolo = titolo;
        this.ssn_autore = ssn_autore;
    }

    public int getId_pagina() {
        return id_pagina;}

    public void setId_pagina(int id_pagina) {
        this.id_pagina = id_pagina;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Timestamp getDataOra() {
        return dataOra;
    }

    public void setDataOra(Timestamp dataOra) {
        this.dataOra = dataOra;
    }

    public String getSsn_autore() {
        return ssn_autore;
    }

    public void setSsn_autore(String ssn_autore) {
        this.ssn_autore = ssn_autore;
    }

    @Override
    public String toString() {
        return "Pagina{" +
                "id_pagina=" + id_pagina +
                ", titolo='" + titolo + '\'' +
                ", dataOra=" + dataOra +
                ", ssn_autore='" + ssn_autore + '\'' +
                '}';
    }
}
