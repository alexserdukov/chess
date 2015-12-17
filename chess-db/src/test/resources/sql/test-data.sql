INSERT INTO USERACCESSGROUPS (ID, NAME, ISADMIN) VALUES
  (1, 'admin', TRUE),
  (2, 'user', FALSE),
  (3, 'groupForDelete', FALSE);

INSERT INTO USERS (ID, EMAIL, PASSWORD, NAME, BIRTHDATE, USERACCESSGROUP_ID) VALUES
  (1, 'serdyukov@javamonkeys.com', '12345', 'aleksandr serdyukov', null, 1),
  (2, 'sirosh@javamonkeys.com', '12345', 'sergey  sirosh', null, 1),
  (3, 'filippov@javamonkeys.com', '12345', 'aleksandr filippov', null, 2),
  (4, 'UpdateUser@javamonkeys.com', '12345', 'update user', null, 2),
  (5, 'DeleteUser@javamonkeys.com', '12345', 'delete user', null, 2);


INSERT INTO GAMES (ID, MATCHDATE, AUTHOR_ID, WHITE_ID, BLACK_ID, STARTTIME, GAMELENGTH, WHITETIME, BLACKTIME, RESULT, MOVETEXT, STATUS) VALUES
  (1, null, 1, 2, 1, null, 500, 500, 500, '0.5-0.5','1.e4 e5', 'IN_PROGRESS'),
  (2, null, 1, 1, 3, null, 500, 500, 500, '','1.e4 e5', 'IN_PROGRESS'),
  (3, null, 2, 2, 3, null, 500, 500, 500, '','1.e4 e5', 'ON_HOLD'),
  (4, null, 2, 2, NULL, null, 500, 500, 500, '','1.e4 e5', 'NEW');

INSERT INTO TURNS (ID, TURNDATE, USER_ID, GAME_ID, PIECE, STARTPOSITION, ENDPOSITION, FEN) VALUES
  (1, null, 1, 1, 'PAWN', 'e2', 'e4', '00000'),
  (2, null, 2, 1, 'PAWN', 'e7', 'e5', '00000'),
