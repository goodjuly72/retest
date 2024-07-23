-- 데이터 생성

-- 가상 데이터 삽입
BEGIN
    FOR i IN 1..5 LOOP
	    -- 1.면제여부 처리전 (최초 RPA 입력시)
        INSERT INTO TBL_VEHICLE (
            SEQ, CAR_NO, BASE_DATE, PROG_FLAG, EXEMPT_STATUS,
			IC_CODE, WORK_DATE, WORK_NO, HAND_SNO,
			VEHICLE_TYPE, PASS_TIME,VEHICLE_KIND,
			REG_ID, REG_DT, MOD_ID, MOD_DT
        ) VALUES (
            SEQ.NEXTVAL, 'CAR'||TO_CHAR(i+5, 'FM0000'), TO_CHAR(SYSDATE, 'YYYYMMDD'), '01', '99',   -- 면제여부, 진행 FLAG
			'001', TO_CHAR(SYSDATE, 'YYYYMMDD'), '0001', i+5,   --RPA 입력부분
			'01', '', '01',    --차량타입, 차량통과 시간, 차량종류
			'RPA', SYSDATE, 'RPA', SYSDATE
        );
		-- 2.면제여부 처리전 (API 처리중)
        INSERT INTO TBL_VEHICLE (
            SEQ, CAR_NO, BASE_DATE, PROG_FLAG, EXEMPT_STATUS,
			IC_CODE, WORK_DATE, WORK_NO, HAND_SNO,
			VEHICLE_TYPE, PASS_TIME,VEHICLE_KIND,
			REG_ID, REG_DT, MOD_ID, MOD_DT
        ) VALUES (
            SEQ.NEXTVAL, 'CAR'||TO_CHAR(i+10, 'FM0000'), TO_CHAR(SYSDATE, 'YYYYMMDD'), '02', '99',   -- 면제여부, 진행 FLAG
			'001', TO_CHAR(SYSDATE, 'YYYYMMDD'), '0001', i+10,   --RPA 입력부분
			'01', '', '01',    --차량타입, 차량통과 시간, 차량종류
			'RPA', SYSDATE, 'BATCH', SYSDATE
        );
		-- 3.면제여부 처리후 (API 처리완료; 비면제)
        INSERT INTO TBL_VEHICLE (
            SEQ, CAR_NO, BASE_DATE, PROG_FLAG, EXEMPT_STATUS,
			IC_CODE, WORK_DATE, WORK_NO, HAND_SNO,
			VEHICLE_TYPE, PASS_TIME,VEHICLE_KIND,
			REG_ID, REG_DT, MOD_ID, MOD_DT
        ) VALUES (
            SEQ.NEXTVAL, 'CAR'||TO_CHAR(i+15, 'FM0000'), TO_CHAR(SYSDATE, 'YYYYMMDD'), '03', '01',   -- 면제여부, 진행 FLAG
			'001', TO_CHAR(SYSDATE, 'YYYYMMDD'), '0001', i+15,   --RPA 입력부분
			'01', '', '01',    --차량타입, 차량통과 시간, 차량종류
			'RPA', SYSDATE, 'BATCH', SYSDATE
        );
		-- 4.면제여부 처리후 (API 처리완료; 조회불능)
        INSERT INTO TBL_VEHICLE (
            SEQ, CAR_NO, BASE_DATE, PROG_FLAG, EXEMPT_STATUS,
			IC_CODE, WORK_DATE, WORK_NO, HAND_SNO,
			VEHICLE_TYPE, PASS_TIME,VEHICLE_KIND,
			REG_ID, REG_DT, MOD_ID, MOD_DT
        ) VALUES (
            SEQ.NEXTVAL, 'CAR'||TO_CHAR(i+20, 'FM0000'), TO_CHAR(SYSDATE, 'YYYYMMDD'), '03', '02',   -- 면제여부, 진행 FLAG
			'001', TO_CHAR(SYSDATE, 'YYYYMMDD'), '0001', i+20,   --RPA 입력부분
			'01', '', '01',    --차량타입, 차량통과 시간, 차량종류
			'RPA', SYSDATE, 'BATCH', SYSDATE
        );
		-- 5.면제여부 처리후 (API 처리완료; 다자녀)
        INSERT INTO TBL_VEHICLE (
            SEQ, CAR_NO, BASE_DATE, PROG_FLAG, EXEMPT_STATUS,
			IC_CODE, WORK_DATE, WORK_NO, HAND_SNO,
			VEHICLE_TYPE, PASS_TIME,VEHICLE_KIND,
			REG_ID, REG_DT, MOD_ID, MOD_DT
        ) VALUES (
            SEQ.NEXTVAL, 'CAR'||TO_CHAR(i+25, 'FM0000'), TO_CHAR(SYSDATE, 'YYYYMMDD'), '03', '03',   -- 면제여부, 진행 FLAG
			'001', TO_CHAR(SYSDATE, 'YYYYMMDD'), '0001', i+25,   --RPA 입력부분
			'01', '', '01',    --차량타입, 차량통과 시간, 차량종류
			'RPA', SYSDATE, 'BATCH', SYSDATE
        );
		-- 6.면제여부 처리후 (API 처리완료; 유공자)
        INSERT INTO TBL_VEHICLE (
            SEQ, CAR_NO, BASE_DATE, PROG_FLAG, EXEMPT_STATUS,
			IC_CODE, WORK_DATE, WORK_NO, HAND_SNO,
			VEHICLE_TYPE, PASS_TIME,VEHICLE_KIND,
			REG_ID, REG_DT, MOD_ID, MOD_DT
        ) VALUES (
            SEQ.NEXTVAL, 'CAR'||TO_CHAR(i+30, 'FM0000'), TO_CHAR(SYSDATE, 'YYYYMMDD'), '03', '04',   -- 면제여부, 진행 FLAG
			'001', TO_CHAR(SYSDATE, 'YYYYMMDD'), '0001', i+30,   --RPA 입력부분
			'01', '', '01',    --차량타입, 차량통과 시간, 차량종류
			'RPA', SYSDATE, 'BATCH', SYSDATE
        );
		-- 7.면제여부 처리후 (API 처리완료; 장애인)
        INSERT INTO TBL_VEHICLE (
            SEQ, CAR_NO, BASE_DATE, PROG_FLAG, EXEMPT_STATUS,
			IC_CODE, WORK_DATE, WORK_NO, HAND_SNO,
			VEHICLE_TYPE, PASS_TIME,VEHICLE_KIND,
			REG_ID, REG_DT, MOD_ID, MOD_DT
        ) VALUES (
            SEQ.NEXTVAL, 'CAR'||TO_CHAR(i+35, 'FM0000'), TO_CHAR(SYSDATE, 'YYYYMMDD'), '03', '05',   -- 면제여부, 진행 FLAG
			'001', TO_CHAR(SYSDATE, 'YYYYMMDD'), '0001', i+35,   --RPA 입력부분
			'01', '', '01',    --차량타입, 차량통과 시간, 차량종류
			'RPA', SYSDATE, 'BATCH', SYSDATE
        );
		-- 8.면제여부 처리후 (API 처리완료; 전기(부산))
        INSERT INTO TBL_VEHICLE (
            SEQ, CAR_NO, BASE_DATE, PROG_FLAG, EXEMPT_STATUS,
			IC_CODE, WORK_DATE, WORK_NO, HAND_SNO,
			VEHICLE_TYPE, PASS_TIME,VEHICLE_KIND,
			REG_ID, REG_DT, MOD_ID, MOD_DT
        ) VALUES (
            SEQ.NEXTVAL, 'CAR'||TO_CHAR(i+40, 'FM0000'), TO_CHAR(SYSDATE, 'YYYYMMDD'), '03', '06',   -- 면제여부, 진행 FLAG
			'001', TO_CHAR(SYSDATE, 'YYYYMMDD'), '0001', i+40,   --RPA 입력부분
			'01', '', '01',    --차량타입, 차량통과 시간, 차량종류
			'RPA', SYSDATE, 'BATCH', SYSDATE
        );
		-- 9.면제여부 처리후 (API 처리완료; 공차(택시)
        INSERT INTO TBL_VEHICLE (
            SEQ, CAR_NO, BASE_DATE, PROG_FLAG, EXEMPT_STATUS,
			IC_CODE, WORK_DATE, WORK_NO, HAND_SNO,
			VEHICLE_TYPE, PASS_TIME,VEHICLE_KIND,
			REG_ID, REG_DT, MOD_ID, MOD_DT
        ) VALUES (
            SEQ.NEXTVAL, 'CAR'||TO_CHAR(i+45, 'FM0000'), TO_CHAR(SYSDATE, 'YYYYMMDD'), '03', '08',   -- 면제여부, 진행 FLAG
			'001', TO_CHAR(SYSDATE, 'YYYYMMDD'), '0001', i+45,   --RPA 입력부분
			'02', SYSDATE, '01',    --차량타입, 차량통과 시간, 차량종류
			'RPA', SYSDATE, 'BATCH', SYSDATE
        );
		-- 10.면제여부 처리후 (RPA 사용완료)
        INSERT INTO TBL_VEHICLE (
            SEQ, CAR_NO, BASE_DATE, PROG_FLAG, EXEMPT_STATUS,
			IC_CODE, WORK_DATE, WORK_NO, HAND_SNO,
			VEHICLE_TYPE, PASS_TIME,VEHICLE_KIND,
			REG_ID, REG_DT, MOD_ID, MOD_DT
        ) VALUES (
            SEQ.NEXTVAL, 'CAR'||TO_CHAR(i+50, 'FM0000'), TO_CHAR(SYSDATE, 'YYYYMMDD'), '04', '03',   -- 면제여부, 진행 FLAG
			'001', TO_CHAR(SYSDATE, 'YYYYMMDD'), '0001', i+50,   --RPA 입력부분
			'01', '', '01',    --차량타입, 차량통과 시간, 차량종류
			'RPA', SYSDATE, 'BATCH', SYSDATE
        );
    END LOOP;
END;
INSERT INTO TBL_VEHICLE_MST (
  CAR_NO, BASE_DATE, EXEMPT_STATUS, VEHICLE_KIND, REG_ID, REG_DT, MODI_ID, MODI_DT
)
SELECT
  CAR_NO,
  BASE_DATE,
  EXEMPT_STATUS,
  VEHICLE_KIND,
  'BATCH' AS REG_ID,
  SYSDATE AS REG_DT,
  'BATCH' AS MODI_ID,
  SYSDATE AS MODI_DT
FROM TBL_VEHICLE
WHERE EXEMPT_STATUS = '04';


-- TBL_CODE 데이터 생성
insert into tbl_code values ('00', '면제아님', 'system',CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP);
insert into tbl_code values ('01', '조회불능', 'system',CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP);
insert into tbl_code values ('02', '다자녀차량', 'system',CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP);
insert into tbl_code values ('03', '장애인차량', 'system',CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP);
insert into tbl_code values ('04', '경차차량', 'system',CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP);
insert into tbl_code values ('05', '유공자차량', 'system',CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP);
insert into tbl_code values ('06', '환경친화적차량', 'system',CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP);
insert into tbl_code values ('07', '부산전기차량', 'system',CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP);
insert into tbl_code values ('08', '부산수소차량', 'system',CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP);
insert into tbl_code values ('09', '전기차량', 'system',CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP);
insert into tbl_code values ('10', '수소차량', 'system',CURRENT_TIMESTAMP, 'system',  CURRENT_TIMESTAMP);
insert into tbl_code values ('99', '등록', 'system',CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP);