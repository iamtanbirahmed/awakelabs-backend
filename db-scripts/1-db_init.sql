
CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;






--
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


CREATE EXTENSION IF NOT EXISTS "pgcrypto" WITH SCHEMA public VERSION "1.3";


COMMENT ON EXTENSION "pgcrypto" IS 'used for crypgraphic functionalities';


--
-- Name: create_phi_encrypted_heart_rate_fn(character varying, boolean, integer, bigint, uuid, character varying); Type: FUNCTION; Schema: public; Owner: root
--

CREATE FUNCTION public.create_phi_encrypted_heart_rate_fn(in_phi_values character varying, in_motion boolean, in_patient_id integer, in_time bigint, in_vital_sign_id uuid, in_sym_key character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
DECLARE
var_phi_values text;
var_sym_key text;
new_id UUID;
final_id varchar;
BEGIN

SELECT cast(in_phi_values as text) into var_phi_values;
SELECT cast(in_sym_key as text) into var_sym_key;

 INSERT INTO public.heart_rate(
	 phi_values,motion, patient_id, "time", vital_sign_id)
	VALUES (
			pgp_sym_encrypt(var_phi_values,var_sym_key),
			in_motion,
			in_patient_id,
			in_time,
			in_vital_sign_id)returning  id into new_id;
	SELECT cast(new_id as varchar) into final_id;
	return final_id;

END;
$$;


ALTER FUNCTION public.create_phi_encrypted_heart_rate_fn(in_phi_values character varying, in_motion boolean, in_patient_id integer, in_time bigint, in_vital_sign_id uuid, in_sym_key character varying) OWNER TO root;

--
-- Name: create_phi_encrypted_vital_sign_fn(character varying, integer, bigint, double precision, character varying); Type: FUNCTION; Schema: public; Owner: root
--

CREATE FUNCTION public.create_phi_encrypted_vital_sign_fn(in_phi_values character varying, in_patient_id integer, in_time bigint, in_battery_level double precision, in_sym_key character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
DECLARE
var_phi_values text;
var_sym_key text;
new_id UUID;
final_id varchar;
BEGIN

SELECT cast(in_phi_values as text) into var_phi_values;
SELECT cast(in_sym_key as text) into var_sym_key;

 INSERT INTO public.vital_sign(
	phi_values, battery_level, patient_id, "time")
	VALUES (
			pgp_sym_encrypt(var_phi_values,var_sym_key),
			in_battery_level,
			in_patient_id,
			in_time
			) returning  id into new_id;
	SELECT cast(new_id as varchar) into final_id;
	return final_id;

END;
$$;


ALTER FUNCTION public.create_phi_encrypted_vital_sign_fn(in_phi_values character varying, in_patient_id integer, in_time bigint, in_battery_level double precision, in_sym_key character varying) OWNER TO root;

--
-- Name: get_phi_encrypted_heart_rate(text); Type: FUNCTION; Schema: public; Owner: root
--

CREATE FUNCTION public.get_phi_encrypted_heart_rate(sim_key text) RETURNS TABLE(id uuid, phi_values character varying, patient_id integer, "time" bigint, motion boolean, vital_sign_id uuid)
    LANGUAGE plpgsql
    AS $$
BEGIN
	RETURN QUERY SELECT
	heart_rate.id,
	cast(pgp_sym_decrypt(heart_rate.phi_values,sim_key) as character varying),
	heart_rate.patient_id,
	heart_rate."time",
	heart_rate.motion,
	heart_rate.vital_sign_id
	FROM public.heart_rate;
END;
$$;


ALTER FUNCTION public.get_phi_encrypted_heart_rate(sim_key text) OWNER TO root;

--
-- Name: get_phi_encrypted_vital_sign(text); Type: FUNCTION; Schema: public; Owner: root
--

CREATE FUNCTION public.get_phi_encrypted_vital_sign(sim_key text) RETURNS TABLE(id uuid, phi_values character varying, battery_level double precision, patient_id integer, "time" bigint)
    LANGUAGE plpgsql
    AS $$
BEGIN
	RETURN QUERY SELECT
	vital_sign.id,
	cast(pgp_sym_decrypt(vital_sign.phi_values,sim_key) as character varying),
	vital_sign.battery_level,
	vital_sign.patient_id,
	vital_sign."time"
	FROM public.vital_sign;
END;
$$;


ALTER FUNCTION public.get_phi_encrypted_vital_sign(sim_key text) OWNER TO root;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: heart_rate; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.heart_rate (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    motion boolean,
    patient_id integer,
    "time" bigint,
    vital_sign_id uuid,
    phi_values bytea
);


ALTER TABLE public.heart_rate OWNER TO root;

--
-- Name: vital_sign; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.vital_sign (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    battery_level double precision,
    patient_id integer,
    "time" bigint,
    phi_values bytea
);


ALTER TABLE public.vital_sign OWNER TO root;

--
-- Data for Name: heart_rate; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.heart_rate (id, motion, patient_id, "time", vital_sign_id, phi_values) FROM stdin;
\.


--
-- Data for Name: vital_sign; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.vital_sign (id, battery_level, patient_id, "time", phi_values) FROM stdin;
\.


--
-- Name: heart_rate heart_rate_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.heart_rate
    ADD CONSTRAINT heart_rate_pkey PRIMARY KEY (id);


--
-- Name: vital_sign vital_sign_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.vital_sign
    ADD CONSTRAINT vital_sign_pkey PRIMARY KEY (id);


--
-- Name: heart_rate vital_sign_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.heart_rate
    ADD CONSTRAINT vital_sign_fk FOREIGN KEY (vital_sign_id) REFERENCES public.vital_sign(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

--
-- PostgreSQL database cluster dump complete
--

