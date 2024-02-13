CREATE TABLE contactus
(
    id                 uuid DEFAULT uuid_generate_v4() primary key,
    phone_no           varchar(15) NOT NULL,
    customer_id        uuid,
    query              text,
    comment            text,
    status             varchar(25),
    created_date       timestamptz,
    last_modified_date timestamptz
);

CREATE INDEX idx_contactus_phone_no ON contactus (phone_no);
