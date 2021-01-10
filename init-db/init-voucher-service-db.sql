--
-- PostgreSQL database dump
--

-- Dumped from database version 10.15
-- Dumped by pg_dump version 10.15

-- Started on 2021-01-11 01:42:27

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
-- TOC entry 2799 (class 1262 OID 16607)
-- Name: voucher_service_db; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE voucher_service_db;


ALTER DATABASE voucher_service_db OWNER TO postgres;

\connect voucher_service_db

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
-- TOC entry 2801 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 197 (class 1259 OID 16610)
-- Name: voucher; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.voucher (
    id integer NOT NULL,
    phone_number text NOT NULL,
    voucher_code text NOT NULL,
    created_date timestamp with time zone
);


ALTER TABLE public.voucher OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 16608)
-- Name: voucher_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.voucher ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.voucher_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000000
    CACHE 1
);


--
-- TOC entry 2672 (class 2606 OID 16614)
-- Name: voucher voucher_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.voucher
    ADD CONSTRAINT voucher_pkey PRIMARY KEY (id);


-- Completed on 2021-01-11 01:42:28

--
-- PostgreSQL database dump complete
--

