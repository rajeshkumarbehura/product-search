drop table if exists user_profile_copy;

CREATE TABLE user_profile_copy
(
    id                 uuid DEFAULT uuid_generate_v4() primary key,
    user_id            uuid        NOT NULL,
    user_name          varchar(100),
    phone_no           int  default 0,
    user_type          varchar(15) not null,
    status             varchar(15) not null,
    created_date       timestamptz,
    last_modified_date timestamptz
);
CREATE UNIQUE INDEX idx_user_profile_copy_user_id ON user_profile_copy (user_id);