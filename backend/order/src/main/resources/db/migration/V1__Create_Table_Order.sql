CREATE TABLE tb_order IF NOT EXISTS tb_order (
                          id SERIAL PRIMARY KEY,
                          created TIMESTAMP NOT NULL,
                          total_amount DOUBLE PRECISION NOT NULL
);