CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE TABLE shipping_fee_promo
(
    id                 uuid           DEFAULT uuid_generate_v4() primary key,
    start_range        numeric(12, 2) default 0,
    end_range          numeric(12, 2) default 0,
    shipping_fee       numeric(12, 2) default 0,
    status             varchar(15),
    created_date       timestamptz,
    last_modified_date timestamptz
);

CREATE TABLE setting
(
    id                 uuid         DEFAULT uuid_generate_v4() primary key,
    config_type    varchar(350),
    config_name        varchar(350),
    config_value       varchar(350) default 'NOT_AVAILABLE',
    status             varchar(50),
    created_date       timestamptz,
    last_modified_date timestamptz,
    UNIQUE (config_type, config_name)
);

-- static data
INSERT INTO public.setting (config_type, config_name, config_value, status, created_date, last_modified_date)
VALUES ('SHIPPING_PROMO', 'isStoreShippingFeePromoOn', 'true', 'ENABLE', 'now()', 'now()');


INSERT INTO public.shipping_fee_promo (start_range, end_range, shipping_fee, status, created_date, last_modified_date)
VALUES (0, 500000.00, 30000, 'ENABLE', 'now()', 'now()');
