CREATE TABLE gift_certificate (
    id BIGSERIAL PRIMARY KEY,
    name varchar(255),
    description varchar(255),
    price integer,
    duration integer,
    create_date timestamp,
    last_update_date timestamp
);

CREATE TABLE tag (
    id BIGSERIAL PRIMARY KEY,
    name varchar(255)
);

CREATE TABLE gift_tags (
    gift_id bigint,
    tag_id bigint,
    PRIMARY KEY (gift_id, tag_id),
    FOREIGN KEY (gift_id) REFERENCES gift_certificate(id),
    FOREIGN KEY (tag_id) REFERENCES tag(id)
);







-- ALTER TABLE ONLY gift_tags
--     ADD CONSTRAINT fk_gift FOREIGN KEY (gift_id) REFERENCES gift_certificate(id) ON UPDATE CASCADE ON DELETE CASCADE;
--
-- ALTER TABLE ONLY gift_tags
--     ADD CONSTRAINT fk_tag FOREIGN KEY (tag_id) REFERENCES tag(id) ON UPDATE CASCADE ON DELETE CASCADE;


