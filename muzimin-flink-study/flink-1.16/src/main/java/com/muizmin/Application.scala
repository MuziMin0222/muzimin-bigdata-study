package com.muizmin

import org.apache.flink.table.api.{EnvironmentSettings, TableEnvironment}

/**
 * @author: 李煌民
 * @date: 2024-04-12 10:26
 *        ${description}
 * */
object Application {
  def main(args: Array[String]): Unit = {
    val settings = EnvironmentSettings.inBatchMode()
    val tenv = TableEnvironment.create(settings)

    val ip = ""
    val password = ""

    tenv.executeSql(
      s"""
         |create table ods_org_admin_i
         |(
         |    `fid`                 STRING,
         |    `fname_l1`            STRING,
         |    `fname_l2`            STRING,
         |    `fname_l3`            STRING,
         |    `fnumber`             STRING,
         |    `fdescription_l1`     STRING,
         |    `fdescription_l2`     STRING,
         |    `fdescription_l3`     STRING,
         |    `fsimplename`         STRING,
         |    `fisgrouping`         INT,
         |    `feffectdate`         TIMESTAMP,
         |    `finvaliddate`        TIMESTAMP,
         |    `fisfreeze`           INT,
         |    `fiscompanyorgunit`   INT,
         |    `fisadminorgunit`     INT,
         |    `fissaleorgunit`      INT,
         |    `fispurchaseorgunit`  INT,
         |    `fisstorageorgunit`   INT,
         |    `fisprofitorgunit`    INT,
         |    `fiscostorgunit`      INT,
         |    `fiscu`               INT,
         |    `fisunion`            INT,
         |    `fishrorgunit`        INT,
         |    `fcreatorid`          STRING,
         |    `fcreatetime`         TIMESTAMP,
         |    `flastupdateuserid`   STRING,
         |    `flastupdatetime`     TIMESTAMP,
         |    `fcontrolunitid`      STRING,
         |    `fisleaf`             INT,
         |    `flevel`              INT,
         |    `flongnumber`         STRING,
         |    `fparentid`           STRING,
         |    `fisentity`           INT,
         |    `fisvirtual`          INT,
         |    `fphonenumber`        STRING,
         |    `flayerid`            STRING,
         |    `fresponpositionid`   STRING,
         |    `faddressid`          STRING,
         |    `fprincipalid`        STRING,
         |    `flayertypeid`        STRING,
         |    `fbaseduty_l1`        STRING,
         |    `fbaseduty_l2`        STRING,
         |    `fbaseduty_l3`        STRING,
         |    `findex`              INT,
         |    `fjobsystemid`        STRING,
         |    `fadminaddress_l1`    STRING,
         |    `fadminaddress_l2`    STRING,
         |    `fadminaddress_l3`    STRING,
         |    `fzipcode`            STRING,
         |    `ffax`                STRING,
         |    `fissealup`           INT,
         |    `fisstart`            INT,
         |    `fisousealup`         INT,
         |    `fdisplayname_l1`     STRING,
         |    `fdisplayname_l2`     STRING,
         |    `fdisplayname_l3`     STRING,
         |    `fpropertysealupdate` TIMESTAMP,
         |    `fversionnumber`      STRING,
         |    `fcode`               STRING,
         |    `fistransportorgunit` INT,
         |    `fisqualityorgunit`   INT,
         |    `fsortcode`           STRING,
         |    `findustryid`         STRING,
         |    `feconomictype`       INT,
         |    `fregisteredcapital`  decimal,
         |    `fregisteredcode`     STRING,
         |    `fsetupdate`          TIMESTAMP,
         |    `fendupdate`          TIMESTAMP,
         |    `ftaxnumber`          STRING,
         |    `fischurchyard`       INT,
         |    `fjuridicalpersonid`  STRING,
         |    `fisjuridicalcompany` INT,
         |    `fisindependence`     INT,
         |    `fterritory_l1`       STRING,
         |    `fterritory_l2`       STRING,
         |    `fterritory_l3`       STRING,
         |    `forgcode`            STRING,
         |    `fareaid`             STRING,
         |    `forgpropertyid`      STRING,
         |    `fregisttypeid`       STRING,
         |    `feffdt`              TIMESTAMP,
         |    `fleffdt`             TIMESTAMP,
         |    `fhistoryrelateid`    STRING,
         |    `fdelegatehrid`       STRING,
         |    `forgfunctionid`      STRING,
         |    `fcompanyid`          STRING,
         |    `fdepartmentid`       STRING,
         |    `fofficeid`           STRING,
         |    `flevelfourgroupid`   STRING,
         |    `flevelfivegroupid`   STRING,
         |    `flevelfivegroup`     STRING,
         |    `flevelsixgroupid`    STRING,
         |    `freservefieldfirst`  STRING,
         |    `freservefieldsecond` STRING,
         |    `freserveitemfirst`   STRING,
         |    `freserveitemsecond`  STRING,
         |    `fisstartshr`         INT,
         |    `forgtypestr`         STRING,
         |    `flevelonegroupid`    STRING,
         |    `fleveltwogroupid`    STRING
         |)
         |with (
         |    'connector'='jdbc',
         |    'url'='jdbc:oracle:thin:@${ip}:1521:hrtest',
         |    'table-name'='SHR0309.T_ORG_ADMIN',
         |    'username'='JTCS',
         |    'password'='${password}',
         |    'driver' = 'oracle.jdbc.driver.OracleDriver'
         |)
         |""".stripMargin)

    tenv.executeSql(
      s"""
         |select * from ods_org_admin_i
         |""".stripMargin)
      .print()
  }

}
