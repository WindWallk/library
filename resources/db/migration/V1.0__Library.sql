
CREATE TABLE public.book (
    id bigint NOT NULL,
    name text NOT NULL,
    isbn text NOT NULL,
    author_id bigint NOT NULL,
    rented_by bigint
);


ALTER TABLE public.book OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 27538)
-- Name: book_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.book_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.book_id_seq OWNER TO postgres;

--
-- TOC entry 2841 (class 0 OID 0)
-- Dependencies: 201
-- Name: book_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.book_id_seq OWNED BY public.book.id;


--
-- TOC entry 199 (class 1259 OID 27507)
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person (
    id bigint NOT NULL,
    first_name text NOT NULL,
    last_name text NOT NULL,
    egn text NOT NULL
);


ALTER TABLE public.person OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 27505)
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
-- TOC entry 2842 (class 0 OID 0)
-- Dependencies: 198
-- Name: person_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.person_id_seq OWNED BY public.person.id;


--
-- TOC entry 200 (class 1259 OID 27518)
-- Name: reader; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reader (
    card_id bigint NOT NULL
)
    INHERITS (public.person);


ALTER TABLE public.reader OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 27495)
-- Name: reader_card; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reader_card (
    id bigint NOT NULL,
    number bigint NOT NULL,
    "valid_to" date NOT NULL
);


ALTER TABLE public.reader_card OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 27493)
-- Name: reader_card_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.reader_card_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.reader_card_id_seq OWNER TO postgres;

--
-- TOC entry 2843 (class 0 OID 0)
-- Dependencies: 196
-- Name: reader_card_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.reader_card_id_seq OWNED BY public.reader_card.id;


--
-- TOC entry 2692 (class 2604 OID 27543)
-- Name: book id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book ALTER COLUMN id SET DEFAULT nextval('public.book_id_seq'::regclass);


--
-- TOC entry 2690 (class 2604 OID 27510)
-- Name: person id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person ALTER COLUMN id SET DEFAULT nextval('public.person_id_seq'::regclass);


--
-- TOC entry 2691 (class 2604 OID 27521)
-- Name: reader id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reader ALTER COLUMN id SET DEFAULT nextval('public.person_id_seq'::regclass);


--
-- TOC entry 2689 (class 2604 OID 27498)
-- Name: reader_card id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reader_card ALTER COLUMN id SET DEFAULT nextval('public.reader_card_id_seq'::regclass);


--
-- TOC entry 2706 (class 2606 OID 27550)
-- Name: book book_isbn_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_isbn_key UNIQUE (isbn);


--
-- TOC entry 2708 (class 2606 OID 27548)
-- Name: book book_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_pkey PRIMARY KEY (id);


--
-- TOC entry 2698 (class 2606 OID 27517)
-- Name: person person_egn_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_egn_key UNIQUE (egn);


--
-- TOC entry 2700 (class 2606 OID 27515)
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- TOC entry 2702 (class 2606 OID 27526)
-- Name: reader reader_card_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reader
    ADD CONSTRAINT reader_card_id_key UNIQUE (card_id);


--
-- TOC entry 2694 (class 2606 OID 27502)
-- Name: reader_card reader_card_number_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reader_card
    ADD CONSTRAINT reader_card_number_key UNIQUE (number);


--
-- TOC entry 2696 (class 2606 OID 27500)
-- Name: reader_card reader_card_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reader_card
    ADD CONSTRAINT reader_card_pkey PRIMARY KEY (id);


--
-- TOC entry 2704 (class 2606 OID 27528)
-- Name: reader reader_egn_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reader
    ADD CONSTRAINT reader_egn_key UNIQUE (egn);


--
-- TOC entry 2710 (class 2606 OID 27551)
-- Name: book book_author_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_author_id_fkey FOREIGN KEY (author_id) REFERENCES public.person(id);


--
-- TOC entry 2711 (class 2606 OID 27556)
-- Name: book book_rented_by_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_rented_by_fkey FOREIGN KEY (rented_by) REFERENCES public.reader_card(id);


--
-- TOC entry 2709 (class 2606 OID 27529)
-- Name: reader reader_card_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reader
    ADD CONSTRAINT reader_card_id_fkey FOREIGN KEY (card_id) REFERENCES public.reader_card(id);
