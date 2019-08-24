DROP TABLE IF EXISTS users_has_pokemons;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS pokemon_has_weaknesses;
DROP TABLE IF EXISTS pokemon_has_types;
DROP TABLE IF EXISTS types;
DROP TABLE IF EXISTS stats;
DROP TABLE IF EXISTS pokemon_has_abilities;
DROP TABLE IF EXISTS abilities;
DROP TABLE IF EXISTS pokemon_has_genders;
DROP TABLE IF EXISTS genders;
DROP TABLE IF EXISTS pokemon;
DROP TABLE IF EXISTS categories;

CREATE TABLE categories
(
    id   BIGSERIAL    NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

INSERT INTO categories
VALUES (DEFAULT, 'Seed'),
       (DEFAULT, 'Lizard'),
       (DEFAULT, 'Flame');

CREATE TABLE pokemon
(
    id             BIGSERIAL     NOT NULL PRIMARY KEY,
    name           VARCHAR(100)  NOT NULL,
    description    TEXT          NOT NULL,
    height         NUMERIC(5, 2) NOT NULL,
    category_id    BIGINT        NOT NULL REFERENCES categories,
    weight         NUMERIC(5, 2) NOT NULL,
    predecessor_id BIGINT DEFAULT NULL REFERENCES pokemon,
    successor_id   BIGINT DEFAULT NULL REFERENCES pokemon
);

INSERT INTO pokemon
VALUES (DEFAULT, 'Bulbasaur', '', 0.7, 1, 6.9);
INSERT INTO pokemon
VALUES (DEFAULT, 'Ivysaur', '', 1.0, 1, 13.0, 1);
UPDATE pokemon
SET successor_id = 2
WHERE id = 1;
INSERT INTO pokemon
VALUES (DEFAULT, 'Venusaur', '', 2.0, 1, 100.0, 2);
UPDATE pokemon
SET successor_id = 3
WHERE id = 2;

INSERT INTO pokemon
VALUES (DEFAULT, 'Charmander', '', 0.6, 2, 8.5);
INSERT INTO pokemon
VALUES (DEFAULT, 'Charmeleon', '', 1.1, 3, 19.0, 4);
UPDATE pokemon
SET successor_id = 5
WHERE id = 4;
INSERT INTO pokemon
VALUES (DEFAULT, 'Charizard', '', 1.7, 2, 90.5, 5);
UPDATE pokemon
SET successor_id = 6
WHERE id = 5;

CREATE TABLE genders
(
    id     BIGSERIAL    NOT NULL PRIMARY KEY,
    name   VARCHAR(100) NOT NULL,
    symbol VARCHAR(20)  NOT NULL
);

CREATE TABLE pokemon_has_genders
(
    id_pokemon BIGINT NOT NULL REFERENCES pokemon,
    id_gender  BIGINT NOT NULL REFERENCES genders
);

CREATE TABLE abilities
(
    id     BIGSERIAL    NOT NULL PRIMARY KEY,
    name   VARCHAR(100) NOT NULL,
    symbol VARCHAR(20)  NOT NULL
);

CREATE TABLE pokemon_has_abilities
(
    id_pokemon   BIGINT NOT NULL REFERENCES pokemon,
    id_abilities BIGINT NOT NULL REFERENCES abilities
);

CREATE TABLE stats
(
    id              BIGSERIAL NOT NULL PRIMARY KEY,
    hp              INT       NOT NULL,
    attack          INT       NOT NULL,
    defense         INT       NOT NULL,
    special_attack  INT       NOT NULL,
    special_defense INT       NOT NULL,
    speed           INT       NOT NULL
);

CREATE TABLE types
(
    id    BIGSERIAL    NOT NULL PRIMARY KEY,
    name  VARCHAR(100) NOT NULL,
    color VARCHAR(20)  NOT NULL
);

INSERT INTO types
VALUES (DEFAULT, 'Normal', '#aaaa99'),
       (DEFAULT, 'Fire', '#ff4422'),
       (DEFAULT, 'Water', '#3399ff'),
       (DEFAULT, 'Electric', '#ffcc33'),
       (DEFAULT, 'Grass', '#77cc55'),
       (DEFAULT, 'Ice', '#66ccff'),
       (DEFAULT, 'Fighting', '#bb5544'),
       (DEFAULT, 'Poison', '#aa5599'),
       (DEFAULT, 'Ground', '#ddbb55'),
       (DEFAULT, 'Flying', '#8899ff'),
       (DEFAULT, 'Psychic', '#ff5599'),
       (DEFAULT, 'Bug', '#aabb22'),
       (DEFAULT, 'Rock', '#bbaa66'),
       (DEFAULT, 'Ghost', '#6666bb'),
       (DEFAULT, 'Dragon', '#7766ee'),
       (DEFAULT, 'Dark', '#775544'),
       (DEFAULT, 'Steel', '#aaaabb'),
       (DEFAULT, 'Fairy', '#ee99ee');

CREATE TABLE pokemon_has_types
(
    id_pokemon BIGINT NOT NULL REFERENCES pokemon,
    id_type    BIGINT NOT NULL REFERENCES types
);

CREATE TABLE pokemon_has_weaknesses
(
    id_pokemon BIGINT NOT NULL REFERENCES pokemon,
    id_type    BIGINT NOT NULL REFERENCES types
);

CREATE TABLE users
(
    id       BIGSERIAL    NOT NULL PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    email    VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE users_has_pokemons
(
    user_id    BIGINT NOT NULL REFERENCES users,
    pokemon_id BIGINT NOT NULL REFERENCES pokemon
);
