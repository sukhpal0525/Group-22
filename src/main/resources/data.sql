-- Categories
SET @PROCESSOR = 0;
SET @MOTHERBOARD = 1;
SET @GPU = 2;
SET @MEMORY = 3;
SET @STORAGE = 4;

-- Configs
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE `webproduct`;
TRUNCATE TABLE `webuser`;
TRUNCATE TABLE `weborder`;
TRUNCATE TABLE `weborderitem`;
TRUNCATE TABLE `webbasket`;
TRUNCATE TABLE `webbasketitem`;

SET FOREIGN_KEY_CHECKS = 1;

-- Processor\
INSERT INTO `webproduct` (`Name`, `Amount`, `Description`,`Category`,`AmountAvailable`) VALUES
  ('Intel Core i9-11900K', 399.99, '11th Gen, 16-core, 32-thread processor', @PROCESSOR, 40),
  ('AMD Ryzen 9 5900X', 449.99, '12-core, 24-thread processor with 3.7 GHz base clock', @PROCESSOR, 50),
  ('AMD Ryzen 7 5800X', 299.99, '8-core, 16-thread processor with 3.8 GHz base clock', @PROCESSOR, 60),
  ('Intel Core i7-11700K', 309.99, '11th Gen, 8-core, 16-thread processor with 5.0 GHz boost', @PROCESSOR, 70),
  ('AMD Ryzen 5 5600X', 199.99, '6-core, 12-thread processor with 3.7 GHz base clock', @PROCESSOR, 80),
  ('Intel Core i5-11600K', 229.99, '11th Gen, 6-core, 12-thread processor with 4.9 GHz boost', @PROCESSOR, 90);

 UPDATE `webproduct`
 SET `url` = 'Intel Core i9-11900K.jpg'
 WHERE `Name` = 'Intel Core i9-11900K';
 UPDATE`webproduct`
 SET  `url` = 'AMD Ryzen 5 5600X.jpg'
 WHERE`Name`='AMD Ryzen 5 5600X';
 UPDATE`webproduct`
 SET `url` = 'AMD Ryzen 7 5800X.jpg'
  WHERE `Name` = 'AMD Ryzen 7 5800X';
  UPDATE`webproduct`
  SET `url` = 'AMD Ryzen 9 5900X.jpg'
   WHERE `Name` = 'AMD Ryzen 9 5900X';
   UPDATE`webproduct`
   SET `url` = 'Intel Core i5-11600K.jpg'
    WHERE `Name` = 'Intel Core i5-11600K';


-- Motherboard
INSERT INTO `webproduct` (`Name`, `Amount`, `Description`,`Category`,`AmountAvailable`) VALUES
  ('ASUS ROG Maximus XIII Hero', 329.99, 'Z590 chipset, WiFi 6, RGB lighting', @MOTHERBOARD, 20),
  ('Gigabyte AORUS X570 Master', 419.99, 'X570 chipset, WiFi 6, dual LAN, RGB lighting', @MOTHERBOARD, 30),
  ('MSI MPG Z590 Gaming Edge WiFi', 219.99, 'Z590 chipset, WiFi 6, dual LAN, Mystic Light RGB', @MOTHERBOARD, 40),
  ('ASRock B450 Steel Legend', 99.99, 'B450 chipset, RGB lighting', @MOTHERBOARD, 50),
  ('EVGA Z590 Dark', 399.99, 'Z590 chipset, WiFi 6, dual LAN, RGB lighting', @MOTHERBOARD, 60);

INSERT INTO `webproduct` (`Name`, `Amount`, `Description`,`Category`,`AmountAvailable`, `OnSale`, `Sale`) VALUES
  ('ASUS ROG Maximus XIII Hero (SALE)', 329.99, 'Z590 chipset, WiFi 6, RGB lighting', @MOTHERBOARD, 20, 1, 0.30);

UPDATE`webproduct`
SET `url` = 'ASRock B450 Steel Legend.jpg'
WHERE `Name` = 'ASRock B450 Steel Legend';
UPDATE`webproduct`
SET `url` = 'ASUS ROG Maximus XIII Hero.jpg'
WHERE `Name` = 'ASUS ROG Maximus XIII Hero (SALE)';
UPDATE`webproduct`
SET `url` = 'Gigabyte AORUS X570 Master.jpg'
WHERE `Name` = 'Gigabyte AORUS X570 Master';
UPDATE`webproduct`
SET `url` = 'MSI MPG Z590 Gaming Edge WiFi.jpg'
WHERE `Name` = 'MSI MPG Z590 Gaming Edge WiFi';
UPDATE`webproduct`
SET `url` = 'EVGA Z590 Dark.jpg'
WHERE `Name` = 'EVGA Z590 Dark';
--INSERT INTO `webproduct` (`Name`, `Amount`, `Description`,`Category`,`AmountAvailable`) VALUES
--(' ASUS TUF Gaming GeForce RTX 4090 OC Edition Gaming Graphics Card',   1899.99,'. ASUS TUF Gaming GeForce RTX 4090 OC Edition Gaming Graphics Card  '@GPU,20),
--('MSI NVIDIA GeForce RTX 4080',1339.98,'16GB GAMING X TRIO Ada Lovelace Graphics Card',@GPU,10);
--('ASUS GeForce RTX 3070 Ti',689.50,'Strix Edition OC 8GB Graphics Card',@GPU,50);
--{'MSI NVIDIA GeForce RTX 4070 Ti',953.99,'SUPRIM X 12GB Ada Lovelace Graphics Card ',@GPU,20};
--{'Gigabyte GeForce GTX 1650',179.99,'D6 OC 4GB Graphics Card GV-N1656OC-4GD V2',@GPU,40};
--{'Gigabyte Radeon RX 7900 XTX',1099.99,'GAMING OC 24GB GDDR6 Graphics Card',@GPU,10};

-- Admin Account (Password = 'admin')
INSERT INTO `webuser` (`Email`, `UserName`, `Password`, `IsAdmin`) VALUES
  ('admin@aston.com', 'Admin', '$2a$10$AOYMzvkn8MISJsQR3Q0kXO0ZmFMJXQO6mw7IH/p2PfpJO.zCfrLlK', 1),
-- Test Account (Password = 'admin')
  ('test@test.com', 'Test User', '$2a$10$AOYMzvkn8MISJsQR3Q0kXO0ZmFMJXQO6mw7IH/p2PfpJO.zCfrLlK', 0);

INSERT INTO `webbasket` (`UserID`) VALUES
  (1),
  (2);

INSERT INTO `webbasketitem` (`BasketID`, `ProductID`, `Amount`) VALUES
  (1, 4, 5),
  (1, 2, 1),
  (1, 5, 3);