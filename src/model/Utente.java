package model;

public class Utente {
    private String ssn;
    private String nome;
    private String cognome;
    private String mail;
    private String password;
    public Utente(String ssn, String nome, String cognome, String mail, String password){
        this.ssn = ssn;
        this.nome = nome;
        this.cognome = cognome;
        this.mail = mail;
        this.password = password;
    };
    public Utente(){}
    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "ssn='" + ssn + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}


