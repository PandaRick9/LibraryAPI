CREATE TABLE book_info
(
    book_id           int PRIMARY KEY,
    status            bool,
    received_at       timestamp,
    time_to_give_back timestamp,
    deleted           bool
);