CREATE DATABASE mobicare
  WITH ENCODING='UTF8'
       CONNECTION LIMIT=-1;

CREATE TABLE public.department
(
    id integer NOT NULL,
    name character varying(30) NOT NULL,
    budget numeric(9, 2) NOT NULL DEFAULT 0.00,
    CONSTRAINT pk_department_id PRIMARY KEY (id),
    CONSTRAINT unique_department_name UNIQUE (name)
)
WITH (
    OIDS = FALSE
);

ALTER TABLE public.department
    OWNER to postgres;
    
CREATE TABLE public.employee
(
    id integer NOT NULL,
    name character varying(30) NOT NULL,
    income numeric(7, 2) NOT NULL DEFAULT 0.00,
    department_id integer NOT NULL,
    CONSTRAINT pk_employee_id PRIMARY KEY (id),
    CONSTRAINT fk_employee_department_id FOREIGN KEY (department_id)
        REFERENCES public.department (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)
WITH (
    OIDS = FALSE
);

ALTER TABLE public.employee
    OWNER to postgres;

CREATE SEQUENCE public.employee_seq
    INCREMENT 1
    START 1
;

ALTER SEQUENCE public.employee_seq
    OWNER TO postgres;

CREATE SEQUENCE public.department_seq
    INCREMENT 1
    START 1
;

ALTER SEQUENCE public.department_seq
    OWNER TO postgres;