CREATE TABLE tbl_vehicle (
    seq INT(11) NOT NULL AUTO_INCREMENT COMMENT '순번',
    carNo varchar(8) NOT NULL COMMENT '차량번호',
    result varchar(2) NOT NULL COMMENT '면제여부',
    author varchar(20) NULL COMMENT '작성자',
    regDate datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초작성일',
    modiDate datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (seq, carNo)
) ENGINE=InnoDB COMMENT='면제 차량 여부';

CREATE TABLE tbl_vehicle_hist (
    carNo varchar(8) NOT NULL COMMENT '차량번호',
    seq TINYINT NOT NULL COMMENT '순번',
    result varchar(2) NOT NULL COMMENT '면제여',
    sDate datetime NOT NULL DEFAULT CURRENT_DATE COMMENT '기준일자',
    regDate datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초작성일',
    modiDate datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (carNo, seq)
) ENGINE=InnoDB COMMENT='면제 차량 history';

CREATE TABLE tbl_code (
    code char(2) NOT NULL COMMENT '코드',
    codeName varchar(8) NOT NULL COMMENT '코드명',
    author varchar(20) NULL  COMMENT '작성자',
    regDate datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초작성일',
    modiDate datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (code)
) ENGINE=InnoDB COMMENT='코드';


insert into tbl_code values ('00', '면제아님', 'system',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into tbl_code values ('01', '조회불능', 'system',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into tbl_code values ('02', '다자녀차량', 'system',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into tbl_code values ('03', '장애인차량', 'system',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into tbl_code values ('04', '경차차량', 'system',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into tbl_code values ('05', '유공자차량', 'system',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into tbl_code values ('06', '환경친화적차량', 'system',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into tbl_code values ('07', '부산전기차량', 'system',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into tbl_code values ('08', '부산수소차량', 'system',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into tbl_code values ('09', '전기차량', 'system',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into tbl_code values ('10', '수소차량', 'system',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into tbl_code values ('99', '등록', 'system',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);