alter table basket_sales
    ADD pre_customer_badge  varchar(25) default 'NA',
    ADD post_customer_badge varchar(25) default 'NA';


alter table customer_badge
    ADD total_shipping_price numeric(12, 2) default 0,
    ADD total_discount_price numeric(12, 2) default 0;

