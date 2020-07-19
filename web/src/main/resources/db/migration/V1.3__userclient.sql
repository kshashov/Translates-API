ALTER TABLE public.users
    DROP CONSTRAINT users_unique_email;

ALTER TABLE public.users
    ADD COLUMN client varchar(255) NOT NULL DEFAULT '';
ALTER TABLE public.users
    ADD COLUMN sub varchar(255) NOT NULL DEFAULT '';

ALTER TABLE public.users
    ADD CONSTRAINT users_unique_client_sub UNIQUE (client, sub);


