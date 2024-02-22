CREATE TYPE public."stato_type" AS ENUM (
	'accettato',
	'rifiutato',
	'sostituito',
	'in_attesa');

CREATE SEQUENCE public.frase_id_frase_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 2147483647
    START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.pagina_id_pagina_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 2147483647
    START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE public.utente (
                               ssn varchar(9) NOT NULL,
                               nome varchar(45) NOT NULL,
                               cognome varchar(45) NOT NULL,
                               mail varchar(100) NOT NULL,
                               "password" varchar(255) NOT NULL,
                               CONSTRAINT ssn_check CHECK ((length(ltrim((ssn)::text)) = 9)),
                               CONSTRAINT utente_pkey PRIMARY KEY (ssn)
);

CREATE TABLE public.pagina (
                               id_pagina serial4 NOT NULL,
                               titolo varchar(125) NOT NULL,
                               dataora timestamp NOT NULL,
                               ssn_autore bpchar(9) NOT NULL,
                               CONSTRAINT pagina_pkey PRIMARY KEY (id_pagina),
                               CONSTRAINT titolo_unico UNIQUE (titolo),
                               CONSTRAINT fk_autore FOREIGN KEY (ssn_autore) REFERENCES public.utente(ssn) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE public.frase (
                              id_frase serial4 NOT NULL,
                              parola varchar(255) NOT NULL,
                              dataora timestamp NOT NULL,
                              stato public."stato_type" NOT NULL,
                              ordinefrase int4 NOT NULL,
                              versione int4 DEFAULT 1 NOT NULL,
                              dataoraapprovata timestamp NULL,
                              ssn_utente bpchar(9) NOT NULL,
                              id_pagina int4 NOT NULL,
                              id_collegamento int4 NULL,
                              id_frasemod int4 NULL,
                              CONSTRAINT frase_pkey PRIMARY KEY (id_frase),
                              CONSTRAINT fk_collegamento FOREIGN KEY (id_collegamento) REFERENCES public.pagina(id_pagina) ON DELETE SET NULL ON UPDATE CASCADE,
                              CONSTRAINT fk_frasemod FOREIGN KEY (id_frasemod) REFERENCES public.frase(id_frase) ON DELETE SET NULL ON UPDATE CASCADE,
                              CONSTRAINT fk_pagina FOREIGN KEY (id_pagina) REFERENCES public.pagina(id_pagina) ON DELETE CASCADE ON UPDATE CASCADE,
                              CONSTRAINT fk_utente FOREIGN KEY (ssn_utente) REFERENCES public.utente(ssn) ON DELETE CASCADE ON UPDATE CASCADE
);

create trigger imposta_stato_sostituito after
    insert
    or
update
    on
    public.frase for each row
    when (((new.id_frasemod is not null)
    and (new.stato = 'accettato'::stato_type))) execute function update_stato_frase();

CREATE OR REPLACE FUNCTION public.cronologia_frasi(v_idpagina integer, v_ordine integer)
 RETURNS text
 LANGUAGE plpgsql
AS $function$
declare
testo text := '';
vfrase varchar(100);
v_versione integer;
cursore refcursor;
cursore2 refcursor;

begin
open cursore for
select parola, versione
from frase
where id_pagina = v_idpagina and stato in ('accettato','sostituito') and ordinefrase = v_ordine
order by versione;

loop
fetch cursore into vfrase, v_versione;
		exit when not found;
		testo := testo || v_versione || ')' || vfrase || E'\n';
end loop;
return testo;
end;
$function$
;

CREATE OR REPLACE FUNCTION public.storico_pagina(datapubb timestamp without time zone, v_idpagina integer)
 RETURNS text
 LANGUAGE plpgsql
AS $function$
declare
testo text := '';
vfrase varchar(100);
cursore refcursor;

begin
open cursore for
select f1.parola
from frase as f1
where (f1.dataoraapprovata <= datapubb or f1.dataoraapprovata is null) and f1.id_pagina = v_idpagina
  and (f1.stato = 'accettato' or f1.stato = 'sostituito')
  and f1.versione = 	(select max(f2.versione)
                        from frase as f2
                        where (f2.dataoraapprovata <= datapubb or f2.dataoraapprovata is null)
                          and f2.id_pagina = f1.id_pagina
                          and (f2.stato = 'accettato' or f2.stato = 'sostituito')
                          and f2.ordinefrase = f1.ordinefrase)
order by f1.ordinefrase;
loop
fetch cursore into vfrase;
		exit when not found;
		testo := testo || vfrase || E'\n';
end loop;
return testo;
end;
$function$
;

CREATE OR REPLACE FUNCTION public.testo_pagina(v_idpagina integer)
 RETURNS text
 LANGUAGE plpgsql
AS $function$
declare
testo text := '';
vfrase varchar(100);
cursore refcursor;
begin
open cursore for
select parola
from frase
where id_pagina = v_idpagina and stato = 'accettato'
order by ordinefrase ;

loop
fetch cursore into vfrase;
		exit when not found;
		testo := testo || vfrase || E'\n';
end loop;
return testo;
end;
$function$
;

CREATE OR REPLACE FUNCTION public.update_stato_frase()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin
update frase
set stato = 'sostituito'
where ordinefrase = new.ordinefrase
  and id_pagina = new.id_pagina
  and stato = 'accettato'
  and id_frase <> new.id_frase;

return new;

end;
$function$
;
