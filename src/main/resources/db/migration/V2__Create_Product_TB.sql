CREATE TABLE `Product`
(
    `Id`      INT          NOT NULL AUTO_INCREMENT,
    `GroupId` INT          NOT NULL,
    `Name`    VARCHAR(255) NOT NULL,
    `Price`   DOUBLE       NOT NULL,
    PRIMARY KEY (`Id`),
    CONSTRAINT `FK_ProductGroup` FOREIGN KEY (`GroupId`)
        REFERENCES `ProductGroup` (`Id`)
) ENGINE = InnoDB;
