-- ----------------------------
-- Table structure for "C##FHBOOT"."GD_GONGDAN"
-- ----------------------------
-- DROP TABLE "C##FHBOOT"."GD_GONGDAN";
CREATE TABLE "C##FHBOOT"."GD_GONGDAN" (
	"TITLE" VARCHAR2(100 BYTE) NULL ,
	"CREATETIME" VARCHAR2(32 BYTE) NULL ,
	"GONGDAN_ID" VARCHAR2(100 BYTE) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;

COMMENT ON COLUMN "C##FHBOOT"."GD_GONGDAN"."TITLE" IS '标题';
COMMENT ON COLUMN "C##FHBOOT"."GD_GONGDAN"."CREATETIME" IS '创建时间';
COMMENT ON COLUMN "C##FHBOOT"."GD_GONGDAN"."GONGDAN_ID" IS 'ID';

-- ----------------------------
-- Indexes structure for table GD_GONGDAN
-- ----------------------------

-- ----------------------------
-- Checks structure for table "C##FHBOOT"."GD_GONGDAN"

-- ----------------------------

ALTER TABLE "C##FHBOOT"."GD_GONGDAN" ADD CHECK ("GONGDAN_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table "C##FHBOOT"."GD_GONGDAN"
-- ----------------------------
ALTER TABLE "C##FHBOOT"."GD_GONGDAN" ADD PRIMARY KEY ("GONGDAN_ID");
