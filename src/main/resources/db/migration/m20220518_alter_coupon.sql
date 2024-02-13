alter table discount_coupon
    add description text;

alter table discount_coupon
    add display_no smallint default 0;

alter table discount_coupon alter column coupon_name type varchar(350);