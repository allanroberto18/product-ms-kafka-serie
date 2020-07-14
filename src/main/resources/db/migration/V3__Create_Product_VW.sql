CREATE VIEW ProductView AS
SELECT `PD`.`Id` AS `Id`,
       `G`.`Id` AS `GroupId`,
       `G`.`Name` AS `GroupName`,
       `PD`.`Name` AS `ProductName`,
       `PD`.`Price` AS `Price`
FROM `ProductGroup` AS `G`
LEFT JOIN `Product` AS `PD` ON `PD`.`GroupId` = `G`.`Id`;
