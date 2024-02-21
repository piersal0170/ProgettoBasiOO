package dao;

import model.Pagina;

import java.util.ArrayList;

public interface PaginaDAO {
    void inserisciPagina(String titolo, String ssn_autore) throws Exception;
    void selectPagina(String ssn_autore, ArrayList<Pagina> listaPagine) throws Exception;
    void rimuoviPagina(int id_pagina) throws Exception;
    int selectIDpagina(Pagina pagina) throws Exception;
    public Pagina cercaPagina(String titolo) throws Exception;
}
