CREATE TABLE discount_coupon
(
    id                 uuid           DEFAULT uuid_generate_v4() primary key,
    coupon_name        varchar(50) NOT NULL unique,
    coupon_price       numeric(12, 2) default 0,
    min_buying_range numeric(12, 2) default 0,
    has_fixed_time     boolean        default false,
    customer_count     smallint,
    start_date         timestamptz,
    end_date           timestamptz,
    status             varchar(15),
    created_date       timestamptz,
    last_modified_date timestamptz
);


-- static data
INSERT INTO public.setting (config_type, config_name, config_value, status, created_date, last_modified_date)
VALUES ('DISCOUNT_COUPON_PROMO', 'isStoreDiscountCouponPromoOn', 'false', 'ENABLE', 'now()', 'now()');
