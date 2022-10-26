# DB 생성
DROP DATABASE IF EXISTS SB_AM;
CREATE DATABASE SB_AM;
USE SB_AM;

# 게시글 테이블 생성
CREATE TABLE article(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

# 멤버 테이블 생성
CREATE TABLE `member`(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(20) NOT NULL,
    loginPw CHAR(60) NOT NULL,
    `authLevel` SMALLINT(2) UNSIGNED DEFAULT 3 COMMENT '권한 레벨 (3=일반, 7=관리자)',
    `name` CHAR(20) NOT NULL,
    nickName CHAR(20) NOT NULL,
    cellphoneNum CHAR(200) NOT NULL,
    email CHAR(50) NOT NULL,
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴여부 (0=탈퇴 전, 1=탈퇴 후)',
    delDate DATETIME COMMENT '탈퇴날짜'
);

# 게시판 테이블 작성
CREATE TABLE board (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `code` CHAR(50) NOT NULL UNIQUE COMMENT 'notice(공지사항), free1(자유게시판1), free2(자유게시판2), ...',
    `name` CHAR(50) NOT NULL UNIQUE COMMENT '게시판 이름',
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '삭제여부 (0=삭제 전, 1=삭제 후)',
    delDate DATETIME COMMENT '삭제날짜'
)

# reactionPoint 테이블 작성
CREATE TABLE reactionPoint (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    relTypeCode CHAR(50) NOT NULL COMMENT '관련 데이터 타입 코드',
    relId INT(10) UNSIGNED NOT NULL COMMENT '관련 데이터 번호',
    `point` SMALLINT(2) NOT NULL
)

# reactionPoint 테스트 데이터 생성
# 1번 회원이 1번 article에 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 1,
`point` = -1;

# 1번 회원이 2번 article에 좋아요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 2,
`point` = 1;

# 2번 회원이 1번 article에 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 1,
`point` = -1;

# 2번 회원이 2번 article에 좋아요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 2,
`point` = 1;

# 3번 회원이 1번 article에 좋아요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
relTypeCode = 'article',
relId = 1,
`point` = 1;

# 게시물 테이블에 goodReactionPoint 칼럼 추가
ALTER TABLE article ADD COLUMN goodReactionPoint INT(10) UNSIGNED NOT NULL DEFAULT 0;

# 게시물 테이블에 badReactionPoint 칼럼 추가
ALTER TABLE article ADD COLUMN badReactionPoint INT(10) UNSIGNED NOT NULL DEFAULT 0;

# 기존 게시물의 goodReactionPoint,badReactionPoint 필드의 값 채워주기
UPDATE article AS A
INNER JOIN (
	SELECT RP.relTypeCode, RP.relId,
	SUM(IF(RP.point > 0, RP.point, 0)) AS goodReactionPoint,
	SUM(IF(RP.point < 0, RP.point * -1, 0)) AS badReactionPoint
	FROM reactionPoint AS RP
	GROUP BY RP.relTypeCode, RP.relId
) AS RP_SUM
ON A.id = RP_SUM.relId
SET A.goodReactionPoint = RP_SUM.goodReactionPoint,
A.badReactionPoint = RP_SUM.badReactionPoint;

# 기본 게시판 생성
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'notice',
`name` = '공지사항';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'free',
`name` = '자유';

# 게시물 테이블에 boardId 컬럼 추가
ALTER TABLE article ADD COLUMN boardId INT(10) UNSIGNED NOT NULL AFTER `memberId`;

# 1,2 번 게시물을 공지사항 게시물로 수정
UPDATE article
SET boardId = 1
WHERE id IN (1,2);

# 3,4 번 게시물을 자유게시판 게시물로 수정
UPDATE article
SET boardId = 2
WHERE id IN (3,4);

# 게시글 테스트 데이터 생성

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목4',
`body` = '내용4';

# 회원 테스트 데이터 생성(관리자)
INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'admin',
    loginPw = 'admin',
    `authLevel` = 7,
    `name` = '관리자',
    nickName = '관리자',
    cellphoneNum = '01012341234',
    email = 'choiaeyoung1001@gmail.com';

# 회원 테스트 데이터 생성(일반)
INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'test1',
    loginPw = 'test1',
    `name` = '사용자1',
    nickName = '사용자1',
    cellphoneNum = '01043214321',
    email = 'choiaeyoung1001@gmail.com';

# 회원 테스트 데이터 생성(일반)
INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'test2',
    loginPw = 'test2',
    `name` = '사용자2',
    nickName = '사용자2',
    cellphoneNum = '01056785678',
    email = 'choiaeyoung1001@gmail.com';
    
    
# 게시물 테이블에 회원번호 칼럼 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER `updateDate`;

UPDATE article
SET memberId = 2
WHERE memberId = 0;

# 게시물 테이블에 조회수 칼럼 추가
ALTER TABLE article ADD COLUMN hitCount INT(10) UNSIGNED NOT NULL DEFAULT 0;

UPDATE article
SET hit = hit + 1
WHERE id = 629;

SELECT * FROM `member`;
SELECT * FROM article ORDER BY id DESC;
SELECT * FROM board;
SELECT * FROM reactionPoint;

/*
# 방법1
SELECT A.*, 
IFNULL(sum(RP.point),0) as extra__sumReactionPoint,
ifnull(sum(if(RP.point > 0, RP.point, 0)), 0) as extra__goodReactionPoint,
ifnull(sum(if(RP.point < 0, RP.point, 0)), 0) as extra__badReactionPoint
from (
    SELECT A.*, M.name AS extra__writerName
    FROM article AS A
    INNER JOIN `member` AS M
    ON A.memberId = M.id
    ) as A
left join reactionPoint as RP
on RP.relTypeCode = 'article'
and A.id = RP.relId
group by A.id

# 방법2_이걸 스프링에서 쓸 때에 대해서는 다시 생각해보기
SELECT A.*, M.nickname AS extra__writerName, 
IFNULL(SUM(RP.point),0) AS extra__sumReactionPoint,
IFNULL(SUM(IF(RP.point > 0, RP.point, 0)), 0) AS extra__goodReactionPoint,
IFNULL(SUM(IF(RP.point < 0, RP.point, 0)), 0) AS extra__badReactionPoint
from article as A 
left join `member` as M
on A.memberId = M.id
left join reactionPoint as RP
on A.id = RP.relId and RP.relTypeCode = 'article'
group by A.id
*/

# 게시물 갯수 늘리기
INSERT INTO article
(
    regDate, updateDate, memberId, boardId, title, `body`
)
SELECT NOW(), NOW(), FLOOR(RAND() * 2) + 1, FLOOR(RAND() * 2) + 1, CONCAT('제목_', RAND()), CONCAT('내용_', RAND())
FROM article;