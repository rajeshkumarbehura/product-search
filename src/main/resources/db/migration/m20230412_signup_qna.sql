CREATE TABLE signup_qna
(
    id                 uuid         DEFAULT uuid_generate_v4() primary key,
    phone_no           int         not null,
    query_no           smallint     default 0,
    query_en           text         default 'NA',
    query_vn           text         default 'NA',
    answer             varchar(200) default 'NA',
    status             varchar(15) not null,
    created_date       timestamptz,
    last_modified_date timestamptz
);

CREATE UNIQUE INDEX idx_customer_qna_phone_no_query_no ON signup_qna (phone_no, query_no);
