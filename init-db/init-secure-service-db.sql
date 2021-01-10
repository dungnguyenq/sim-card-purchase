--
-- PostgreSQL database dump
--

-- Dumped from database version 10.15
-- Dumped by pg_dump version 10.15

-- Started on 2021-01-11 01:41:16

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2800 (class 1262 OID 16618)
-- Name: secure_service_db; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE secure_service_db;


ALTER DATABASE secure_service_db OWNER TO postgres;

\connect secure_service_db

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12924)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2802 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 197 (class 1259 OID 16621)
-- Name: otp_code; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.otp_code (
    id integer NOT NULL,
    phone_number text NOT NULL,
    secret_key text NOT NULL,
    created_date timestamp with time zone NOT NULL,
    modified_date timestamp with time zone NOT NULL,
    is_used boolean DEFAULT false NOT NULL
);


ALTER TABLE public.otp_code OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 16619)
-- Name: otp_code_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.otp_code ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.otp_code_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000000
    CACHE 1
);


--
-- TOC entry 2673 (class 2606 OID 16629)
-- Name: otp_code otp_code_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.otp_code
    ADD CONSTRAINT otp_code_pkey PRIMARY KEY (id);


-- Completed on 2021-01-11 01:41:16

--
-- PostgreSQL database dump complete
--

