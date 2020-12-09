DELETE FROM user;

INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');

INSERT INTO score (id, user_id, avg_value) VALUES
(1, 1, 100),
(2, 2, 94),
(3, 3, 56),
(4, 4, 78),
(5, 5, 24);