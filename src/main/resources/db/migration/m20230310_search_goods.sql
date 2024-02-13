CREATE TABLE goods_search
(
    id                   uuid DEFAULT uuid_generate_v4() primary key,
    track_date           int          not null,
    track_or_customer_id uuid         not null,
    keyword              varchar(100) not null,
    status               varchar(15)  not null,
    created_date         timestamptz
);

CREATE INDEX idx_goods_search_track_date_other ON goods_search (track_date, track_or_customer_id, keyword);
CREATE INDEX idx_goods_search_track_or_customer_id ON goods_search (track_or_customer_id);

CREATE TABLE goods_search_count
(
    id         uuid DEFAULT uuid_generate_v4() primary key,
    keyword    varchar(100) not null,
    used_count int,
    status     varchar(15)  not null
);
CREATE UNIQUE INDEX idx_keyword_count_keyword ON goods_search_count (keyword, status);



