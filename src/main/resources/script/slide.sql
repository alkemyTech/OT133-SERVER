CREATE TABLE IF NOT EXISTS slides(
    slide_id        INT,
    image_url       VARCHAR(500),
    text            TEXT ,
    order_number    INT,
    organization_id INT,
    PRIMARY KEY (slide_id),
    CONSTRAINT slide_fk FOREIGN KEY (organization_id) REFERENCES organizations (organizationId)
);
