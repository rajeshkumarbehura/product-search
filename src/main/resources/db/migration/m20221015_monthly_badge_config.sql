drop table if exists monthly_badge_brand_config;
drop table if exists customer_badge;
drop table if exists monthly_badge;


CREATE TABLE monthly_badge
(
    id                 uuid           DEFAULT uuid_generate_v4() primary key,
    badge              varchar(50)    default 'NA' unique,
    badge_index        int NOT NULL   default 0 unique,
    badge_value        numeric(12, 2) default 0 unique,
    status             varchar(15),
    created_date       timestamptz,
    last_modified_date timestamptz
);

CREATE TABLE monthly_badge_brand_config
(
    id                   uuid           DEFAULT uuid_generate_v4() primary key,
    monthly_badge_id     uuid NOT NULL,
    goods_brand_id       uuid NOT NULL,
    goods_brand_name     varchar(150),
    badge_discount_rate  numeric(4, 2)  default 0,
    badge_discount_price numeric(12, 2) default 0,
    status               varchar(15),
    created_date         timestamptz,
    last_modified_date   timestamptz,

    CONSTRAINT fk_monthly_badge_brand_config_monthly_badge_id
        FOREIGN KEY (monthly_badge_id) REFERENCES monthly_badge (id)
);

CREATE UNIQUE INDEX idx_monthly_badge_config_monthly_badge_id_brand_id ON monthly_badge_brand_config (monthly_badge_id, goods_brand_id);


CREATE TABLE customer_badge
(
    id                    uuid           DEFAULT uuid_generate_v4() primary key,
    customer_id           uuid NOT NULL,
    year_month            int  NOT NULL,
    total_price           numeric(12, 2) default 0,
    total_rely_bulk_price numeric(12, 2) default 0,
    default_badge_id      uuid,
    default_badge         varchar(25),
    monthly_badge_id      uuid,
    monthly_badge         varchar(25),
    note                  varchar(50)    default 'NA',
    status                varchar(15),
    created_date          timestamptz,
    last_modified_date    timestamptz,

    CONSTRAINT fk_customer_badge_monthly_badge_id
        FOREIGN KEY (monthly_badge_id) REFERENCES monthly_badge (id),
    CONSTRAINT fk_customer_badge_default_badge_id
        FOREIGN KEY (default_badge_id) REFERENCES monthly_badge (id)
);
CREATE UNIQUE INDEX idx_customer_badge_customer_id_year_month ON customer_badge (customer_id, year_month);


-- default data for monthly badge
INSERT INTO monthly_badge (id, badge, badge_index, badge_value, status, created_date, last_modified_date)
VALUES ('744f6da6-0f12-4bac-9111-f6deee2a3ebb', 'MEMBER', 1, 2000000.00, 'ENABLE', now(), now()),
       ('4e30ecd6-d0f9-4115-bf1e-5d690d19fc83', 'BRONZE', 2, 5000000.00, 'ENABLE', now(), now()),
       ('31101c43-9e62-496b-b9a4-e1438ab92492', 'SILVER', 3, 10000000.00, 'ENABLE', now(), now()),
       ('57330a4f-6fad-495c-8304-3077cc3184e2', 'GOLD', 4, 20000000.00, 'ENABLE', now(), now()),
       ('fb3424f5-1b3e-4b76-b0cf-66e7d4fe6f28', 'PLATINUM', 5, 50000000.00, 'ENABLE', now(), now()),
       ('f6de2820-263c-4fea-87dd-cdc524df7a53', 'DIAMOND', 6, 100000000.00, 'ENABLE', now(), now()),
       ('243335a8-651f-40d8-b3f6-631e6b1c3f32', 'NA', 0, 0.00, 'ENABLE', now(), now());
