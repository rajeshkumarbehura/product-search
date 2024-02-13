CREATE TABLE lucky_draw_contest
(
    id                 uuid DEFAULT uuid_generate_v4() primary key,
    phone_no           varchar(15) NOT NULL,
    gift_type          varchar(15),
    status             varchar(15),
    created_date       timestamptz,
    last_modified_date timestamptz
);

CREATE INDEX idx_lucky_draw_contest_phone_no ON lucky_draw_contest (phone_no);
CREATE INDEX idx_lucky_draw_contest_created_date ON lucky_draw_contest (created_date);
