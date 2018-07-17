CREATE TABLE public.reader_card (
    id bigint NOT NULL,
    number bigint NOT NULL,
    valid_to date NOT NULL
);


ALTER TABLE public.reader_card OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 24245)
-- Name: ReaderCard_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."ReaderCard_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."ReaderCard_id_seq" OWNER TO postgres;

--
-- TOC entry 2857 (class 0 OID 0)
-- Dependencies: 197
-- Name: ReaderCard_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."ReaderCard_id_seq" OWNED BY public.reader_card.id;


--
-- TOC entry 198 (class 1259 OID 24247)
-- Name: book; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.book (
    id bigint NOT NULL,
    isbn integer NOT NULL,
    name text NOT NULL,
    author bigint NOT NULL,
    "rentedBy" bigint NOT NULL
);


ALTER TABLE public.book OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 24253)
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
-- TOC entry 2858 (class 0 OID 0)
-- Dependencies: 199
-- Name: book_author_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.book_author_seq OWNED BY public.book.author;


--
-- TOC entry 200 (class 1259 OID 24255)
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
-- TOC entry 2859 (class 0 OID 0)
-- Dependencies: 200
-- Name: book_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.book_id_seq OWNED BY public.book.id;


--
-- TOC entry 201 (class 1259 OID 24257)
-- Name: book_rentedBy_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."book_rentedBy_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."book_rentedBy_seq" OWNER TO postgres;

--
-- TOC entry 2860 (class 0 OID 0)
-- Dependencies: 201
-- Name: book_rentedBy_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."book_rentedBy_seq" OWNED BY public.book."rentedBy";


--
-- TOC entry 202 (class 1259 OID 24259)
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
-- TOC entry 203 (class 1259 OID 24265)
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
-- TOC entry 2861 (class 0 OID 0)
-- Dependencies: 203
-- Name: person_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.person_id_seq OWNED BY public.person.id;


--
-- TOC entry 204 (class 1259 OID 24267)
-- Name: reader; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reader (
    card_id bigint NOT NULL
)
    INHERITS (public.person);


ALTER TABLE public.reader OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 24273)
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
-- TOC entry 2862 (class 0 OID 0)
-- Dependencies: 205
-- Name: reader_card_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.reader_card_id_seq OWNED BY public.reader.card_id;


--
-- TOC entry 2696 (class 2604 OID 24275)
-- Name: book id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book ALTER COLUMN id SET DEFAULT nextval('public.book_id_seq'::regclass);


--
-- TOC entry 2697 (class 2604 OID 24276)
-- Name: book author; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book ALTER COLUMN author SET DEFAULT nextval('public.book_author_seq'::regclass);


--
-- TOC entry 2698 (class 2604 OID 24277)
-- Name: book rentedBy; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book ALTER COLUMN "rentedBy" SET DEFAULT nextval('public."book_rentedBy_seq"'::regclass);


--
-- TOC entry 2699 (class 2604 OID 24278)
-- Name: person id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person ALTER COLUMN id SET DEFAULT nextval('public.person_id_seq'::regclass);


--
-- TOC entry 2700 (class 2604 OID 24279)
-- Name: reader id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reader ALTER COLUMN id SET DEFAULT nextval('public.person_id_seq'::regclass);


--
-- TOC entry 2701 (class 2604 OID 24280)
-- Name: reader card_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reader ALTER COLUMN card_id SET DEFAULT nextval('public.reader_card_id_seq'::regclass);


--
-- TOC entry 2695 (class 2604 OID 24281)
-- Name: reader_card id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reader_card ALTER COLUMN id SET DEFAULT nextval('public."ReaderCard_id_seq"'::regclass);


--
-- TOC entry 2863 (class 0 OID 0)
-- Dependencies: 197
-- Name: ReaderCard_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."ReaderCard_id_seq"', 1, true);


--
-- TOC entry 2864 (class 0 OID 0)
-- Dependencies: 199
-- Name: book_author_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.book_author_seq', 1, false);


--
-- TOC entry 2865 (class 0 OID 0)
-- Dependencies: 200
-- Name: book_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.book_id_seq', 1, false);


--
-- TOC entry 2866 (class 0 OID 0)
-- Dependencies: 201
-- Name: book_rentedBy_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."book_rentedBy_seq"', 1, false);


--
-- TOC entry 2867 (class 0 OID 0)
-- Dependencies: 203
-- Name: person_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.person_id_seq', 1, true);


--
-- TOC entry 2868 (class 0 OID 0)
-- Dependencies: 205
-- Name: reader_card_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.reader_card_id_seq', 1, false);


--
-- TOC entry 2707 (class 2606 OID 24283)
-- Name: book book_isbn_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_isbn_key UNIQUE (isbn);


--
-- TOC entry 2709 (class 2606 OID 24285)
-- Name: book book_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_pkey PRIMARY KEY (id);


--
-- TOC entry 2711 (class 2606 OID 24287)
-- Name: person person_egn_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_egn_key UNIQUE (egn);


--
-- TOC entry 2713 (class 2606 OID 24289)
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- TOC entry 2703 (class 2606 OID 24291)
-- Name: reader_card reader_card_number_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reader_card
    ADD CONSTRAINT reader_card_number_key UNIQUE (number);


--
-- TOC entry 2705 (class 2606 OID 24293)
-- Name: reader_card reader_card_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reader_card
    ADD CONSTRAINT reader_card_pkey PRIMARY KEY (id);


--
-- TOC entry 2715 (class 2606 OID 24295)
-- Name: reader reader_egn_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reader
    ADD CONSTRAINT reader_egn_key UNIQUE (egn);


--
-- TOC entry 2716 (class 2606 OID 24296)
-- Name: book book_author_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_author_fkey FOREIGN KEY (author) REFERENCES public.person(id);


--
-- TOC entry 2717 (class 2606 OID 24301)
-- Name: reader reader_card_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reader
    ADD CONSTRAINT reader_card_id_fkey FOREIGN KEY (card_id) REFERENCES public.reader_card(id);


