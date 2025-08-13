CREATE TABLE IF NOT EXISTS tb_order_products (
                                   order_id INT NOT NULL,
                                   products INT NOT NULL,
                                   CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES tb_order (id) ON DELETE CASCADE
);

CREATE INDEX idx_order_products_order_id ON tb_order_products (order_id);
