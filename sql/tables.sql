DROP TABLE IF EXISTS users_has_pokemon;
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
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE pokemon
(
    id             BIGSERIAL    NOT NULL PRIMARY KEY,
    name           VARCHAR(100) NOT NULL UNIQUE,
    description    TEXT         NOT NULL,
    height         FLOAT        NOT NULL,
    category_id    BIGINT       NOT NULL REFERENCES categories,
    weight         FLOAT        NOT NULL,
    predecessor_id BIGINT DEFAULT NULL REFERENCES pokemon,
    successor_id   BIGINT DEFAULT NULL REFERENCES pokemon
);

CREATE TABLE genders
(
    id   BIGSERIAL    NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE pokemon_has_genders
(
    pokemon_id BIGINT NOT NULL REFERENCES pokemon,
    gender_id  BIGINT NOT NULL REFERENCES genders
);

CREATE TABLE abilities
(
    id          BIGSERIAL    NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description TEXT         NOT NULL
);

CREATE TABLE pokemon_has_abilities
(
    pokemon_id BIGINT NOT NULL REFERENCES pokemon,
    ability_id BIGINT NOT NULL REFERENCES abilities
);

CREATE TABLE stats
(
    id              BIGSERIAL NOT NULL PRIMARY KEY,
    pokemon_id      BIGINT    NOT NULL REFERENCES pokemon UNIQUE,
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
    name  VARCHAR(100) NOT NULL UNIQUE,
    color VARCHAR(7)   NOT NULL
);

CREATE TABLE pokemon_has_types
(
    pokemon_id BIGINT NOT NULL REFERENCES pokemon,
    type_id    BIGINT NOT NULL REFERENCES types
);

CREATE TABLE pokemon_has_weaknesses
(
    pokemon_id    BIGINT NOT NULL REFERENCES pokemon,
    type_id       BIGINT NOT NULL REFERENCES types,
    effectiveness INT    NOT NULL DEFAULT 3
);

CREATE TABLE users
(
    id       BIGSERIAL    NOT NULL PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    email    VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE users_has_pokemon
(
    user_id    BIGINT NOT NULL REFERENCES users,
    pokemon_id BIGINT NOT NULL REFERENCES pokemon
);
