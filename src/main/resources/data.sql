-- Categories
SET @PROCESSOR = 0;
SET @MOTHERBOARD = 1;
SET @GPU = 2;
SET @MEMORY = 3;
SET @MOUSE = 4;

-- Configs
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE `WebProduct`;
TRUNCATE TABLE `WebUser`;
TRUNCATE TABLE `WebOrder`;
TRUNCATE TABLE `WebOrderItem`;
TRUNCATE TABLE `WebBasket`;
TRUNCATE TABLE `WebBasketItem`;

--SET GLOBAL time_zone = '+00:00';

------------------------------------------------------------------------------------------------------------------------

-- Processor
INSERT INTO `WebProduct` (`Name`, `Amount`, `Description`,`Category`,`AmountAvailable`) VALUES
  ('Intel Core i9-11900K', 399.99, '11th Gen, 16-core, 32-thread processor', @PROCESSOR, 40),
  ('AMD Ryzen 9 5900X', 449.99, '12-core, 24-thread processor with 3.7 GHz base clock', @PROCESSOR, 50),
  ('AMD Ryzen 7 5800X', 299.99, '8-core, 16-thread processor with 3.8 GHz base clock', @PROCESSOR, 60),
  ('Intel Core i7-11700K', 309.99, '11th Gen, 8-core, 16-thread processor with 5.0 GHz boost', @PROCESSOR, 70),
  ('AMD Ryzen 5 5600X', 199.99, '6-core, 12-thread processor with 3.7 GHz base clock', @PROCESSOR, 80),
  ('Intel Core i5-11600K', 229.99, '11th Gen, 6-core, 12-thread processor with 4.9 GHz boost', @PROCESSOR, 90);

 UPDATE `WebProduct`
 SET `url` = 'Intel Core i9-11900K.jpg'
 WHERE `Name` = 'Intel Core i9-11900K';
 UPDATE`WebProduct`
 SET  `url` = 'AMD Ryzen 5 5600X.jpg'
 WHERE`Name`='AMD Ryzen 5 5600X';
 UPDATE`WebProduct`
 SET `url` = 'AMD Ryzen 7 5800X.jpg'
 WHERE `Name` = 'AMD Ryzen 7 5800X';
 UPDATE`WebProduct`
 SET `url` = 'AMD Ryzen 9 5900X.jpg'
 WHERE `Name` = 'AMD Ryzen 9 5900X';
 UPDATE`WebProduct`
 SET `url` = 'Intel Core i5-11600K.jpg'
 WHERE `Name` = 'Intel Core i5-11600K';
 UPDATE`WebProduct`
 SET `url` = 'Intel Core i7-11700K.jpg'
 WHERE `Name` = 'Intel Core i7-11700K';

------------------------------------------------------------------------------------------------------------------------

-- Motherboard
INSERT INTO `WebProduct` (`Name`, `Amount`, `Description`,`Category`,`AmountAvailable`) VALUES
  ('ASUS ROG Maximus XIII Hero', 329.99, 'Z590 chipset, WiFi 6, RGB lighting', @MOTHERBOARD, 20),
  ('Gigabyte AORUS X570 Master', 419.99, 'X570 chipset, WiFi 6, dual LAN, RGB lighting', @MOTHERBOARD, 30),
  ('MSI MPG Z590 Gaming Edge WiFi', 219.99, 'Z590 chipset, WiFi 6, dual LAN, Mystic Light RGB', @MOTHERBOARD, 40),
  ('ASRock B450 Steel Legend', 99.99, 'B450 chipset, RGB lighting', @MOTHERBOARD, 50),
  ('EVGA Z590 Dark', 399.99, 'Z590 chipset, WiFi 6, dual LAN, RGB lighting', @MOTHERBOARD, 60);

-- Motherboard (SALE)
INSERT INTO `WebProduct` (`Name`, `Amount`, `Description`,`Category`,`AmountAvailable`, `OnSale`, `Sale`) VALUES
  ('ASUS ROG Maximus XIII Hero (SALE)', 329.99, 'Z590 chipset, WiFi 6, RGB lighting', @MOTHERBOARD, 20, 1, 0.30);

 UPDATE`WebProduct`
 SET `url` = 'ASRock B450 Steel Legend.jpg'
 WHERE `Name` = 'ASRock B450 Steel Legend';
 UPDATE`WebProduct`
 SET `url` = 'ASUS ROG Maximus XIII Hero.jpg'
 WHERE `Name` = 'ASUS ROG Maximus XIII Hero (SALE)';
 UPDATE`WebProduct`
 SET `url` = 'Gigabyte AORUS X570 Master.jpg'
 WHERE `Name` = 'Gigabyte AORUS X570 Master';
 UPDATE`WebProduct`
 SET `url` = 'MSI MPG Z590 Gaming Edge WiFi.jpg'
 WHERE `Name` = 'MSI MPG Z590 Gaming Edge WiFi';
 UPDATE`WebProduct`
 SET `url` = 'EVGA Z590 Dark.jpg'
 WHERE `Name` = 'EVGA Z590 Dark';
 UPDATE`WebProduct`
 SET `url` = 'ASUS ROG Maximus XIII Hero.jpg'
 WHERE `Name` = 'ASUS ROG Maximus XIII Hero';

------------------------------------------------------------------------------------------------------------------------

-- GPU
INSERT INTO `WebProduct` (`Name`,`Amount`,`Description`,`Category`,`AmountAvailable`) VALUES
  ('ASUS ROG Strix GeForce RTX 3080', 1499.99, '10GB GDDR6X, 2nd Gen Ray Tracing Cores', @GPU, 10),
  ('NVIDIA GeForce RTX 3090', 2299.99, '24GB GDDR6X, 2nd Gen Ray Tracing Cores', @GPU, 5),
  ('AMD Radeon RX 6900 XT', 1499.99, '16GB GDDR6, 128MB Infinity Cache', @GPU, 15),
  ('EVGA GeForce RTX 3060 Ti', 899.99, '8GB GDDR6, 2nd Gen Ray Tracing Cores', @GPU, 25),
  ('MSI GeForce RTX 3070', 1199.99, '8GB GDDR6, 2nd Gen Ray Tracing Cores', @GPU, 12);

-- GPU (SALE)
INSERT INTO `WebProduct` (`Name`,`Amount`, `Description`,`Category`, `AmountAvailable`,`OnSale`,`Sale`) VALUES
  ('GIGABYTE AORUS GeForce RTX 3080', 1699.99, '10GB GDDR6X, 2nd Gen Ray Tracing Cores', @GPU, 8, 1, 0.15);

 UPDATE`WebProduct`
 SET `url` = 'ASUS ROG Strix GeForce RTX 3080.jpg'
 WHERE `Name` = 'ASUS ROG Strix GeForce RTX 3080';
 UPDATE`WebProduct`
 SET `url` = 'NVIDIA GeForce RTX 3090.jpg'
 WHERE `Name` = 'NVIDIA GeForce RTX 3090';
 UPDATE`WebProduct`
 SET `url` = 'AMD Radeon RX 6900 XT.jpg'
 WHERE `Name` = 'AMD Radeon RX 6900 XT';
 UPDATE`WebProduct`
 SET `url` = 'EVGA GeForce RTX 3060 Ti.jpg'
 WHERE `Name` = 'EVGA GeForce RTX 3060 Ti';
 UPDATE`WebProduct`
 SET `url` = 'MSI GeForce RTX 3070.jpg'
 WHERE `Name` = 'MSI GeForce RTX 3070';
 UPDATE`WebProduct`
 SET `url` = 'GIGABYTE AORUS GeForce RTX 3080.jpg'
 WHERE `Name` = 'GIGABYTE AORUS GeForce RTX 3080';

------------------------------------------------------------------------------------------------------------------------

-- Memory
INSERT INTO `WebProduct` (`Name`,`Amount`,`Description`,`Category`,`AmountAvailable`) VALUES
  ('Corsair Vengeance RGB Pro 32GB', 169.99, 'DDR4 3200MHz, CL16, RGB Lighting', @MEMORY, 25),
  ('G.Skill Ripjaws V 16GB', 89.99, 'DDR4 3600MHz, CL16, Dual Channel Kit', @MEMORY, 35),
  ('Crucial Ballistix RGB 64GB', 309.99, 'DDR4 3200MHz, CL16, RGB Lighting', @MEMORY, 15);

-- Memory (SALE)
INSERT INTO `WebProduct` (`Name`,`Amount`, `Description`,`Category`, `AmountAvailable`,`OnSale`,`Sale`) VALUES
  ('Team T-Force Delta RGB 32GB', 149.99, 'DDR4 3200MHz, CL16, RGB Lighting', @MEMORY, 20, 1, 0.15),
  ('Kingston HyperX Fury 16GB', 79.99, 'DDR4 3200MHz, CL16, Black Heat Spreader', @MEMORY, 40, 1, 0.15),
  ('Patriot Viper Steel Series 32GB', 129.99, 'DDR4 3200MHz, CL16, Low Profile Design', @MEMORY, 30, 1, 0.25);

 UPDATE`WebProduct`
 SET `url` = 'Corsair Vengeance RGB Pro 32GB.jpg'
 WHERE `Name` = 'Corsair Vengeance RGB Pro 32GB';
 UPDATE`WebProduct`
 SET `url` = 'G.Skill Ripjaws V 16GB.jpg'
 WHERE `Name` = 'G.Skill Ripjaws V 16GB';
 UPDATE`WebProduct`
 SET `url` = 'Crucial Ballistix RGB 64GB.jpg'
 WHERE `Name` = 'Crucial Ballistix RGB 64GB';
 UPDATE`WebProduct`
 SET `url` = 'Team T-Force Delta RGB 32GB.jpg'
 WHERE `Name` = 'Team T-Force Delta RGB 32GB';
 UPDATE`WebProduct`
 SET `url` = 'Kingston HyperX Fury 16GB.jpg'
 WHERE `Name` = 'Kingston HyperX Fury 16GB';
 UPDATE`WebProduct`
 SET `url` = 'Patriot Viper Steel Series 32GB.jpg'
 WHERE `Name` = 'Patriot Viper Steel Series 32GB';

------------------------------------------------------------------------------------------------------------------------

INSERT INTO `WebProduct` (`Name`, `Amount`, `Description`, `Category`, `AmountAvailable`) VALUES
  ('Logitech G Pro Wireless Gaming Mouse', 129.99, 'Lightweight Design, HERO 25K Sensor, Ambidextrous', @MOUSE, 20),
  ('Razer DeathAdder V2 Gaming Mouse', 79.99, 'Optical Sensor, RGB Lighting, Ergonomic Design', @MOUSE, 30),
  ('SteelSeries Rival 600 Gaming Mouse', 89.99, 'Dual Optical Sensor, Weight System, RGB Lighting', @MOUSE, 25),
  ('HyperX Pulsefire FPS Pro Gaming Mouse', 59.99, 'Optical Sensor, RGB Lighting, Ergonomic Design', @MOUSE, 40);

 UPDATE`WebProduct`
 SET `url` = 'Logitech G Pro Wireless Gaming Mouse.jpg'
 WHERE `Name` = 'Logitech G Pro Wireless Gaming Mouse';
 UPDATE`WebProduct`
 SET `url` = 'Razer DeathAdder V2 Gaming Mouse.jpg'
 WHERE `Name` = 'Razer DeathAdder V2 Gaming Mouse';
 UPDATE`WebProduct`
 SET `url` = 'SteelSeries Rival 600 Gaming Mouse.jpg'
 WHERE `Name` = 'SteelSeries Rival 600 Gaming Mouse';
 UPDATE`WebProduct`
 SET `url` = 'HyperX Pulsefire FPS Pro Gaming Mouse.jpg'
 WHERE `Name` = 'HyperX Pulsefire FPS Pro Gaming Mouse';

------------------------------------------------------------------------------------------------------------------------

INSERT INTO `WebUser` (`UserID`, `Email`, `UserName`, `Password`, `IsAdmin`) VALUES
-- Admin Account (Password = 'admin')
  (1, 'admin@aston.com', 'Admin', '$2a$10$AOYMzvkn8MISJsQR3Q0kXO0ZmFMJXQO6mw7IH/p2PfpJO.zCfrLlK', 1),
-- Test Account  (Password = 'admin')
  (2, 'test@test.com', 'Test User', '$2a$10$AOYMzvkn8MISJsQR3Q0kXO0ZmFMJXQO6mw7IH/p2PfpJO.zCfrLlK', 0);

------------------------------------------------------------------------------------------------------------------------

INSERT INTO `WebBasket` (`UserID`) VALUES
  (1),
  (2);

------------------------------------------------------------------------------------------------------------------------

INSERT INTO `WebBasketItem` (`BasketID`, `ProductID`, `Amount`) VALUES
  (1, 4, 5),
  (1, 2, 1),
  (1, 5, 3);

----------------------------------------------------------------------------------------------------------------------

INSERT INTO `WebOrder` (`OrderID`, `TransactionNumber`, `UserID`, `OrderDate`, `CreationDate`, `Quantity`, `OrderAmount`, `Status`) VALUES
  (1, 1234, 2, '2023-03-26T08:45:00', '2023-03-26T08:45:00', 1, 19.99, 0),
  (2, 1235, 2, '2023-03-23T10:30:00', '2023-03-23T10:30:00', 2, 9.99, 1),
  (3, 1236, 2, '2023-03-22T14:15:00', '2023-03-22T14:15:00', 1, 29.97, 2),
  (4, 1237, 2, '2023-03-21T16:45:00', '2023-03-21T16:45:00', 3, 4.99, 3),
  (5, 1238, 2, '2023-03-20T19:00:00', '2023-03-20T19:00:00', 1, 24.99, 4);


