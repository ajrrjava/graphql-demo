CREATE TABLE ${flyway:defaultSchema}.planets (
    id              BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    name            VARCHAR(500),
    description     VARCHAR(500),
    diameter        DECIMAL(10, 4),
    type            VARCHAR(25),
    CONSTRAINT pk_planets PRIMARY KEY (id)
);