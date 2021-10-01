CREATE TABLE IF NOT EXISTS "customer"
(
    id                BIGSERIAL PRIMARY KEY,
    full_name         VARCHAR(128) NOT NULL,
    email             VARCHAR(128) NOT NULL,
    telephone         VARCHAR(32)  NOT NULL,
    registration_code VARCHAR(64)  NOT NULL
);

CREATE TABLE IF NOT EXISTS "order"
(
    id              BIGSERIAL PRIMARY KEY,
    submission_date DATE   NOT NULL,
    customer_id     BIGINT NOT NULL,
    CONSTRAINT customer_FK
        FOREIGN KEY (customer_id)
            REFERENCES "order" (id)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "product"
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(128) NOT NULL,
    sku_code VARCHAR(12)  NOT NULL,
    unit_price DECIMAL NOT NULL
);

CREATE TABLE IF NOT EXISTS "order_line"
(
    id         BIGSERIAL PRIMARY KEY,
    order_id   BIGINT  NOT NULL,
    product_id BIGINT  NOT NULL,
    quantity   INTEGER NOT NULL,
    CONSTRAINT product_FK
        FOREIGN KEY (product_id)
            REFERENCES "product" (id)
            ON DELETE CASCADE,
    CONSTRAINT order_FK
        FOREIGN KEY (order_id)
            REFERENCES "order" (id)
            ON DELETE CASCADE
);