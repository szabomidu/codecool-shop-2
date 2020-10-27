ALTER TABLE IF EXISTS ONLY public.product DROP CONSTRAINT IF EXISTS fk_supplier_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.product DROP CONSTRAINT IF EXISTS fk_category_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.order DROP CONSTRAINT IF EXISTS fk_user_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.lineitem DROP CONSTRAINT IF EXISTS fk_order_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.lineitem DROP CONSTRAINT IF EXISTS fk_product_id CASCADE;


DROP TABLE IF EXISTS public.supplier;
CREATE TABLE public.supplier (
    id serial NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
    description varchar NOT NULL
);

DROP TABLE IF EXISTS public.product_category;
CREATE TABLE public.product_category (
    id serial NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
    description varchar NOT NULL
);

DROP TABLE IF EXISTS public.product;
CREATE TABLE public.product (
    id serial NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
    description varchar NOT NULL,
    price decimal NOT NULL,
    currency varchar NOT NULL,
    supplier_id integer NOT NULL,
    category_id integer NOT NULL
);

DROP TABLE IF EXISTS public.user;
CREATE TABLE public.user (
    id serial NOT NULL PRIMARY KEY,
    name varchar NOT NULL
);

DROP TABLE IF EXISTS public.order;
CREATE TABLE public.order (
    id serial NOT NULL PRIMARY KEY,
    user_id integer NOT NULL,
    first_name varchar,
    last_name varchar,
    email varchar,
    address varchar,
    zip_code integer,
    city varchar,
    country varchar
);

DROP TABLE IF EXISTS public.lineitem;
CREATE TABLE public.lineitem (
    id serial NOT NULL PRIMARY KEY,
    order_id integer NOT NULL,
    product_id integer NOT NULL,
    total_price decimal NOT NULL,
    unit_price decimal NOT NULL,
    quantity integer default 1
);

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fk_supplier_id FOREIGN KEY (supplier_id) REFERENCES public.supplier(id) ON DELETE CASCADE;

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fk_supplier_id FOREIGN KEY (category_id) REFERENCES public.product_category(id) ON DELETE CASCADE;


ALTER TABLE ONLY public.order
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.user(id) ON DELETE CASCADE;

ALTER TABLE ONLY public.lineitem
    ADD CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES public.order(id) ON DELETE CASCADE;

ALTER TABLE ONLY public.lineitem
    ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES public.product(id) ON DELETE CASCADE;
