CREATE TABLE signup_reward
(
    id                 uuid           DEFAULT uuid_generate_v4() primary key,
    reward_type        varchar(200) NOT NULL,
    config_name        varchar(200)   default 'NA',
    config_value       varchar(200)   default 'NA',
    start_range        numeric(12, 2) default 0,
    end_range          numeric(12, 2) default 0,
    discount_price     numeric(12, 2) default 0,
    status             varchar(15),
    created_date       timestamptz,
    last_modified_date timestamptz
);


-- static data
INSERT INTO public.setting (config_type, config_name, config_value, status, created_date, last_modified_date)
VALUES ('SIGNUP_PROMO', 'isStoreSignupDiscountPromoOn', 'true', 'ENABLE', 'now()', 'now()');
