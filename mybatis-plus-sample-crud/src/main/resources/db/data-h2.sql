DELETE FROM user;

INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com'),
(6, 'Jone', 18, 'test1@baomidou.com'),
(7, 'Jack', 20, 'test2@baomidou.com'),
(8, 'Tom', 28, 'test3@baomidou.com'),
(9, 'Sandy', 21, 'test4@baomidou.com'),
(10, 'Billie', 24, 'test5@baomidou.com'),
(11, 'Jone', 18, 'test1@baomidou.com'),
(12, 'Jack', 20, 'test2@baomidou.com'),
(13, 'Tom', 28, 'test3@baomidou.com'),
(14, 'Sandy', 21, 'test4@baomidou.com'),
(15, 'Billie', 24, 'test5@baomidou.com'),
(16, 'Sandy', 21, 'test4@baomidou.com'),
(17, 'Billie', 24, 'test5@baomidou.com');

INSERT INTO score (id, user_id, avg_value) VALUES
(1, 1, 100),
(2, 2, 94),
(3, 3, 56),
(4, 4, 78),
(5, 5, 24),
(6, 6, 94),
(7, 7, 56),
(8, 8, 78),
(9, 9, 24),
(10, 10, 94),
(11, 11, 56),
(12, 12, 78),
(13, 13, 24),
(14, 14, 94),
(15, 15, 56),
(16, 16, 78),
(17, 17, 24);

INSERT INTO family (id, user_id, relation, name) VALUES
(1, 1, 'mother', 'lijie'),
(2, 1, 'father', 'wangshushu'),
(3, 2, 'mother', 'zhengjie');