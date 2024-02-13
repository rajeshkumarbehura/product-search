drop table if exists basket_sales;
drop table if exists customer_badge;

CREATE TABLE basket_sales
(
    id                    uuid           DEFAULT uuid_generate_v4() primary key,
    customer_id           uuid NOT NULL,
    order_date            date NOT NULL,
    basket_id             uuid NOT NULL,
    order_no              int  NOT NULL,
    total_price           numeric(12, 2) default 0,
    total_rely_bulk_price numeric(12, 2) default 0,
    discount_price        numeric(12, 2) default 0,
    shipping_price        numeric(12, 2) default 0,
    select_option         varchar(15),
    basket_status         varchar(15),
    status                varchar(15),
    created_date          timestamptz,
    last_modified_date    timestamptz
);
CREATE INDEX idx_basket_sales_customer_id_order_date ON basket_sales (customer_id, order_date);
CREATE UNIQUE INDEX idx_basket_sales_order_no ON basket_sales (order_no);


CREATE TABLE customer_badge
(
    id                    uuid           DEFAULT uuid_generate_v4() primary key,
    customer_id           uuid NOT NULL,
    year_month            int  NOT NULL,
    total_price           numeric(12, 2) default 0,
    total_rely_bulk_price numeric(12, 2) default 0,
    default_badge         varchar(25),
    monthly_badge         varchar(25),
    status                varchar(15),
    created_date          timestamptz,
    last_modified_date    timestamptz
);
CREATE UNIQUE INDEX idx_customer_badge_customer_id_year_month ON customer_badge (customer_id, year_month);

