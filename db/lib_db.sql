--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4
-- Dumped by pg_dump version 10.4

-- Started on 2018-07-13 12:28:02

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12924)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
--

DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;


CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2831 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 589 (class 1247 OID 21653)
-- Name: readercard; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.readercard AS (
    number bigint,
    "validTo" date
);


ALTER TYPE public.readercard OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 197 (class 1259 OID 21654)
-- Name: book; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.book (
    id text NOT NULL,
    name text NOT NULL,
    author bigint NOT NULL,
    "rentedBy" public.readercard
);


ALTER TABLE public.book OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 21660)
-- Name: book_author_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.book_author_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.book_author_seq OWNER TO postgres;

--
-- TOC entry 2832 (class 0 OID 0)
-- Dependencies: 198
-- Name: book_author_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.book_author_seq OWNED BY public.book.author;


--
-- TOC entry 199 (class 1259 OID 21662)
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person (
    id bigint NOT NULL,
    first_name text NOT NULL,
    last_name text NOT NULL,
    egn integer NOT NULL
);


ALTER TABLE public.person OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 21668)
-- Name: person_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.person_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.person_id_seq OWNER TO postgres;

--
-- TOC entry 2833 (class 0 OID 0)
-- Dependencies: 200
-- Name: person_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.person_id_seq OWNED BY public.person.id;


--
-- TOC entry 201 (class 1259 OID 21670)
-- Name: reader; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reader (
    card public.readercard NOT NULL
)
    INHERITS (public.person);


ALTER TABLE public.reader OWNER TO postgres;

--
-- TOC entry 2687 (class 2604 OID 21676)
-- Name: book author; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book ALTER COLUMN author SET DEFAULT nextval('public.book_author_seq'::regclass);


--
-- TOC entry 2688 (class 2604 OID 21677)
-- Name: person id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person ALTER COLUMN id SET DEFAULT nextval('public.person_id_seq'::regclass);


--
-- TOC entry 2689 (class 2604 OID 21678)
-- Name: reader id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reader ALTER COLUMN id SET DEFAULT nextval('public.person_id_seq'::regclass);

--
-- TOC entry 2834 (class 0 OID 0)
-- Dependencies: 198
-- Name: book_author_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.book_author_seq', 1, false);


--
-- TOC entry 2835 (class 0 OID 0)
-- Dependencies: 200
-- Name: person_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.person_id_seq', 1, true);


--
-- TOC entry 2691 (class 2606 OID 21680)
-- Name: book book_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_pkey PRIMARY KEY (id);

--
-- TOC entry 2693 (class 2606 OID 21779)
-- Name: person person_egn_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_egn_key UNIQUE (egn);

--
-- TOC entry 2693 (class 2606 OID 21682)
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- TOC entry 2695 (class 2606 OID 21684)
-- Name: reader reader_card_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reader
    ADD CONSTRAINT reader_card_key UNIQUE (card);


--
-- TOC entry 2696 (class 2606 OID 21685)
-- Name: book book_author_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_author_fkey FOREIGN KEY (author) REFERENCES public.person(id);


--
-- TOC entry 2697 (class 2606 OID 21690)
-- Name: book book_rentedBy_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT "book_rentedBy_fkey" FOREIGN KEY ("rentedBy") REFERENCES public.reader(card);


--
-- TOC entry 2830 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--
