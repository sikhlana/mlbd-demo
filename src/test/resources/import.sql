INSERT INTO `user` (`id`, `name`, `email`) VALUES (1, 'Saif', 'saif@example.com');
INSERT INTO `user` (`id`, `name`, `email`) VALUES (2, 'Tajwar', 'tajwar@example.com');
INSERT INTO `user` (`id`, `name`, `email`) VALUES (3, 'Talha', 'talha@example.com');
INSERT INTO `user` (`id`, `name`, `email`) VALUES (4, 'Amira', 'amira@example.com');
INSERT INTO `user` (`id`, `name`, `email`) VALUES (5, 'Momo', 'momo@example.com');
INSERT INTO `user` (`id`, `name`, `email`) VALUES (6, 'Azrin', 'azrin@example.com');


INSERT INTO `book` (`id`, `title`, `author`, `published_at`) VALUES (7, 'Harry Potter and the Philosopher''s Stone', 'J. K. Rowling', '1997-06-26');
INSERT INTO `book` (`id`, `title`, `author`, `published_at`) VALUES (8, 'The Hobbit', 'J. R. R. Tolkien', '1937-09-21');
INSERT INTO `book` (`id`, `title`, `author`, `published_at`) VALUES (9, 'And Then There Were None', 'Agatha Christie', '1939-11-06');
INSERT INTO `book` (`id`, `title`, `author`, `published_at`) VALUES (10, 'The Lion, the Witch and the Wardrobe', 'Pauline Baynes', '1950-10-16');
INSERT INTO `book` (`id`, `title`, `author`, `published_at`) VALUES (11, 'Harry Potter and the Chamber of Secrets', 'J. K. Rowling', '1198-07-02');
INSERT INTO `book` (`id`, `title`, `author`, `published_at`) VALUES (12, 'The Hunger Games', 'Suzanne Collins', '2008-09-14');
INSERT INTO `book` (`id`, `title`, `author`, `published_at`) VALUES (13, 'Me Before You', 'Jojo Moyes', '2012-01-05');

INSERT INTO `book_meta` (`book_id`, `count`) VALUES (7, 4);
INSERT INTO `book_meta` (`book_id`, `count`) VALUES (8, 2);
INSERT INTO `book_meta` (`book_id`, `count`) VALUES (9, 8);
INSERT INTO `book_meta` (`book_id`, `count`) VALUES (10, 6);
INSERT INTO `book_meta` (`book_id`, `count`) VALUES (11, 3);
INSERT INTO `book_meta` (`book_id`, `count`) VALUES (12, 10);
INSERT INTO `book_meta` (`book_id`, `count`) VALUES (13, 5);
