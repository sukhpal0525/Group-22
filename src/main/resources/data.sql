-- Categories
SET @PROCESSOR = 0;
SET @MOTHERBOARD = 1;
SET @GPU = 2;
SET @MEMORY = 3;
SET @STORAGE = 4;

-- Processor
INSERT INTO `webproduct` (`Name`, `Amount`, `Description`,`Category`,`AmountAvailable`) VALUES
  ('Intel Core i9-11900K', 399.99, '11th Gen, 16-core, 32-thread processor', @PROCESSOR, 40),
  ('AMD Ryzen 9 5900X', 449.99, '12-core, 24-thread processor with 3.7 GHz base clock', @PROCESSOR, 50),
  ('AMD Ryzen 7 5800X', 299.99, '8-core, 16-thread processor with 3.8 GHz base clock', @PROCESSOR, 60),
  ('Intel Core i7-11700K', 309.99, '11th Gen, 8-core, 16-thread processor with 5.0 GHz boost', @PROCESSOR, 70),
  ('AMD Ryzen 5 5600X', 199.99, '6-core, 12-thread processor with 3.7 GHz base clock', @PROCESSOR, 80),
  ('Intel Core i5-11600K', 229.99, '11th Gen, 6-core, 12-thread processor with 4.9 GHz boost', @PROCESSOR, 90);

-- Motherboard
INSERT INTO `webproduct` (`Name`, `Amount`, `Description`,`Category`,`AmountAvailable`) VALUES
  ('ASUS ROG Maximus XIII Hero', 329.99, 'Z590 chipset, WiFi 6, RGB lighting', @MOTHERBOARD, 20),
  ('Gigabyte AORUS X570 Master', 419.99, 'X570 chipset, WiFi 6, dual LAN, RGB lighting', @MOTHERBOARD, 30),
  ('MSI MPG Z590 Gaming Edge WiFi', 219.99, 'Z590 chipset, WiFi 6, dual LAN, Mystic Light RGB', @MOTHERBOARD, 40),
  ('ASRock B450 Steel Legend', 99.99, 'B450 chipset, RGB lighting', @MOTHERBOARD, 50),
  ('EVGA Z590 Dark', 399.99, 'Z590 chipset, WiFi 6, dual LAN, RGB lighting', @MOTHERBOARD, 60);

-- Admin Account (Password = 'admin')
--INSERT INTO `webuser` (`Email`, `FirstName`, `LastName`,`Password`) VALUES
--  ('admin@aston.com', 'Admin', 'Admin', '$2a$10$AOYMzvkn8MISJsQR3Q0kXO0ZmFMJXQO6mw7IH/p2PfpJO.zCfrLlK');