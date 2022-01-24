CREATE TABLE IF NOT EXISTS slides(
    id              INT,
    image_Url       VARCHAR(500)   NULL,
    organizationId  INT            NOT NULL,
    text            TEXT           NULL,
    order_number    INT            NULL,
    PRIMARY KEY (id),
    CONSTRAINT slide_fk FOREIGN KEY (organizationId ) REFERENCES organizations (idOrganization)
);
