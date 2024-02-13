CREATE TABLE coupon_tracker
(
    id                 uuid DEFAULT uuid_generate_v4() primary key,
    customer_id        uuid NOT NULL,
    discount_coupon_id uuid NOT NULL,
    status             varchar(15),
    created_date       timestamptz,
    CONSTRAINT fk_coupon_tracker_discount_coupon_id FOREIGN KEY (discount_coupon_id) REFERENCES discount_coupon (id)
);

CREATE INDEX idx_coupon_tracker_customer_id_coupon_id
    ON coupon_tracker (customer_id, discount_coupon_id);
