![logo](https://user-images.githubusercontent.com/116152412/228357949-eba1a23a-5988-4623-a6a7-28ff92e9af1a.png)

## PC Lab

Welcome to the PC parts store! An online store for the people who are looking forward to Upgrade their PC's using different Processors, Motherboards, GPUs, Memory, and Mouse for a better user experience. Here is everything you need to know to get started:

## Prerequisites

To run this project, the following prerequisites are required:

- Java Development Kit (JDK): This Spring boot Java application requires installation of Java 8 or later version. It is better to use the latest version.
- Maven or Gradle: You can use either Maven or Gradle build tools.
- IDE: Use IntelliJ IDEA as it is a very feature-rich IDE, it automatically installs all of the dependencies needed by the shared GitHub project, so if there are issues with using the shared GitHub project, it's conceivable that this is because IntelliJ isn't being used.

Simply search for Intellij on your browser and click on the Downlaod to install their free comunity edition.

![IDE](https://user-images.githubusercontent.com/116152412/228968451-9d584452-8f43-4f68-9e04-37abe6f25681.jpg)

![IDE (1)](https://user-images.githubusercontent.com/116152412/228969070-24dfdc77-8a61-4197-bb06-1867806277e8.png)

- Database: The database for the project needs to be installed and configured on your system so that this application could interact with the database.
- Web browser: You will need a web browser to test your web application if you are developing one.
- Operating system: You can use different Operating systems including Windows, Mac, and Linux.

## Installation

A few components on your computer must be installed before you can use our PC Lab website. The hosting and operation of our website locally require these elements:

- Install MySQL, an open source relational database management system. MySQL can be downloaded for free from the official MySQL website. Just search for mysql and click on the link marked in the image below:

![mySQL](https://user-images.githubusercontent.com/116152412/228969476-d9b586d6-ffab-49c3-a039-499356210af6.png)

Then press the download button according to the your Operating System.

![mySQL (1)](https://user-images.githubusercontent.com/116152412/228970983-b9a5f9fc-2d9b-4331-b8c1-3a512b765c9f.png)

Notably, when it comes to this page, simply click the blue link at the bottom to begin the download without creating an account.

![mySQL (2)](https://user-images.githubusercontent.com/116152412/229386185-563c6993-c512-4512-ac3d-25def592bf73.png)

Here comes the actual installation process where you have to choose from different setup types but it is important to select the "Custom" option as only 2 things need to be installed: MySQL and MySQL Workbench.

![mySQL setup](https://user-images.githubusercontent.com/116152412/229386393-5f9a8129-22f9-4276-bf2b-fd93460a2756.png)

Next, when choosing the features and products, choose the most recent versions of MySQL and MySQL Workbench by tapping on the green right-pointing arrow.

![mySQL setup (1)](https://user-images.githubusercontent.com/116152412/229386562-bd59acd4-7a45-42d2-8d0a-68f620ff8043.png)

![mySQL setup (2)](https://user-images.githubusercontent.com/116152412/229386587-47ccae3d-065f-4319-84c9-52ef79bce6ce.png)

The next button should be pressed by default to pick the default options from this screen onward. That is, however, before the person who has access to everything on the MySQL server, known as the root user, is prompted for a password on this screen. Enter 'root' in both of the text boxes to confirm your entry. This will make things simple.

![mySQL setup (3)](https://user-images.githubusercontent.com/116152412/229387085-40811add-5775-4537-abb4-95e4a256130d.png)

After this, it's just a simple process of selecting next, approving all the default options that are given.

- After installing MySQL, we recommend that you also install MySQL Workbench, a visual tool for managing MySQL databases. MySQL Workbench can also be downloaded for free from the official MySQL website so that a MySQL server can be created, hosting the database that Java backend will use.
You just need to search for MySQL Workbench download on your browser and click on the link marked red in the image below. 

![mySql Workbench](https://user-images.githubusercontent.com/116152412/228975216-2f7b35f2-2a32-4f10-b07b-2277817effcf.png)

Assuming this is the first time you have accessed MySQL Workbench, there should be no MySQL connections currently open. Hence, the localhost connection in the below image is crossed out.

![mySQL Workbench (1)](https://user-images.githubusercontent.com/116152412/229387502-b1161232-83b1-493b-a3d5-63e5ca3c30eb.png)

Therefore, you will have to click on plus icon arrowed to create one.

![mySQL Workbench (2)](https://user-images.githubusercontent.com/116152412/229387517-744a4db9-4d36-45b2-aa07-b715ef238f03.png)

Next, the values of the two text fields for "connection name" and "host name" should both be changed to "localhost."

![mySQL Workbench (3)](https://user-images.githubusercontent.com/116152412/229387804-10028e6b-cdff-46de-ac56-58326901dd6e.png)

![mySQL Workbench (4)](https://user-images.githubusercontent.com/116152412/229387814-93dc8bb5-48b7-4899-878e-7a680578a3ad.png)

After that, it is time to add a password, clicking on the "store in vault" button.

![mySQL Workbench (5)](https://user-images.githubusercontent.com/116152412/229387844-ac406cbf-3421-43be-ac67-2a9b4e58142a.png)

Type "root" in the empty password field, to make things easier for yourself as the username and the password will both have the same value.

![mySQL Workbench (6)](https://user-images.githubusercontent.com/116152412/229388133-80665216-33df-4069-b421-b54e947a62b2.png)

It is now time to establish this "localhost" server by selecting the "test connection" button at the bottom right of the window after closing the password window.

![mySQL Workbench (7)](https://user-images.githubusercontent.com/116152412/229388259-e6557ddd-680c-432a-bdeb-d41aa526594f.png)

If everything goes according to plan, this should result in a connection labelled "localhost" appearing on the main MySQL Workbench screen, comparable to the one that was initially crossed out.

It is now time to double-click on this to access it and build the database that Java backend needs.

![mySQL Workbench (8)](https://user-images.githubusercontent.com/116152412/229388520-2b5cee4a-7729-4e66-bd59-107390133b49.png)

This should result in a page that looks like the one below. Don't let it intimidate you. To build a new "Schema," which MySQL Workbench refers to as what is really a "Database," just click the option that has been highlighted.

![mySQL Workbench (9)](https://user-images.githubusercontent.com/116152412/229388710-841d547d-f165-40a4-b7c0-7b2b5eb729f9.png)

The newly generated "Schema" must be named "ecommerce" in its entirety because this is the name of the Database that Java backend is set up to work with. And now, in order to make this Schema, you should click the "apply" button.

![mySQL Workbench (10)](https://user-images.githubusercontent.com/116152412/229388805-31985d22-548f-4478-a53e-ff329e28f9ad.png)

- Creating the necessary ‘WEBUSER’ table.

Going to the main entry point of the application, "EcommerceApplication.java," the server is almost ready to be started via the standard method in IntelliJ, clicking on the green arrow to the left of the "main" method. This is assuming that you have cloned the GitHub repository and have run the "pull" command, pulling in the most up to date version if the local repository is not already reflective of this.

![Webuser](https://user-images.githubusercontent.com/116152412/229389469-434e4739-38de-4648-bae6-4f82304b0fb0.png)

The problem is that the application currently needs the creation of two tables in the database, one of which, "webproduct," is automatically created when the server starts and deleted when it stops, while "webuser," is not.
Hence, it is essential to establish this "webuser" table right away in order for the server to function without a hitch.
In order to do this, open MySQL Workbench again, double-click the newly created "ecommerce" Schema to pick it, and then select the option to add a new tab for running SQL queries. (pointed out with an arrow in the below image).

![mySQL Workbench (11)](https://user-images.githubusercontent.com/116152412/229389626-52bd9656-59d8-4b88-b81c-624066a158a7.png)

On the basis of that, the following query should have been typed into the text box that ought to have appeared, creating the "webuser" database in accordance with the structure that the application requires of it. After entering this, it's time to launch the application and select the lightning bolt icon to build the table.

![mySQL Workbench (12)](https://user-images.githubusercontent.com/116152412/229389863-c48e9952-eb72-4422-86a0-26f0d9d10f8b.png)

Beyond that, if all has gone well, then the ‘ecommerce’ database should now be retrofitted with some new options, proving that this table has been created.

![mySQL Workbench (13)](https://user-images.githubusercontent.com/116152412/229390087-d044b8e1-6e1e-49fc-8044-2d5f31569d01.png)

After all these installations and setup processes you should be able to run the server without any errors, following the image below.

![Run Server](https://user-images.githubusercontent.com/116152412/229390279-06e1d0bf-16f7-4586-acb8-b3966d7c30f4.png)

## Products

We sell a variety of PC parts such as CPUs, motherboards, GPUs, memory and storage. Below are some of the products we offer, along with their descriptions and prices.

Processors

- Intel Core i9-11900K: 11th Gen, 16-core, 32-thread processor. Price: $399.99.
- AMD Ryzen 9 5900X: 12-core, 24-thread processor with 3.7 GHz base clock. Price: $449.99.
- AMD Ryzen 7 5800X: 8-core, 16-thread processor with 3.8 GHz base clock. Price: $299.99.
- Intel Core i7-11700K: 11th Gen, 8-core, 16-thread processor with 5.0 GHz boost. Price: $309.99.
- AMD Ryzen 5 5600X: 6-core, 12-thread processor with 3.7 GHz base clock. Price: $199.99.
- Intel Core i5-11600K: 11th Gen, 6-core, 12-thread processor with 4.9 GHz boost. Price: $229.99.

Motherboards

- ASUS ROG Maximus XIII Hero: Z590 chipset, Wi-Fi 6, RGB lighting. Price: $329.99.
- Gigabyte AORUS X570 Master: X570 chipset, Wi-Fi 6, dual LAN, RGB lighting. Price: $419.99.
- MSI MPG Z590 Gaming Edge Wi-Fi: Z590 chipset, Wi-Fi 6, dual LAN, Mystic Light RGB. Price: $219.99.
- ASRock B450 Steel Legend: B450 chipset, RGB lighting. Price: $99.99.
- EVGA Z590 Dark: Z590 chipset, Wi-Fi 6, dual LAN, RGB lighting. Price: $399.99.
- ASUS ROG Maximus XIII Hero: Z590 chipset, Wi-Fi 6, RGB lighting. Price $329.99.

GPUs

- ASUS ROG Strix GeForce RTX 3080: 10GB GDDR6X, 2nd Gen Ray Tracing Cores. Price: $1499.99.
- NVIDIA GeForce RTX 3090: 24GB GDDR6X, 2nd Gen Ray Tracing Cores. Price: $2299.99.
- AMD Radeon RX 6900 XT: 16GB GDDR6, 128MB Infinity Cache. Price: $1499.99.
- EVGA GeForce RTX 3060 Ti: 8GB GDDR6, 2nd Gen Ray Tracing Cores. Price: $899.99.
- MSI GeForce RTX 3070: 8GB GDDR6, 2nd Gen Ray Tracing Cores. Price: $1199.99.

Memory:

- Corsair Vengeance RGB Pro 32GB: DDR4 3200MHz, CL16, RGB Lighting. Price: $169.99.
- G.Skill Ripjaws V 16GB: DDR4 3600MHz, CL16, Dual Channel Kit. Price: $89.99.
- Crucial Ballistix RGB 64GB: DDR4 3200MHz, CL16, RGB Lighting. Price: $309.99.
- Team T-Force Delta RGB 32GB: DDR4 3200MHz, CL16, RGB Lighting. Price: $149.99 (15% off).
- Kingston HyperX Fury 16GB: DDR4 3200MHz, CL16, Black Heat Spreader. Price: $79.99 (15% off).
- Patriot Viper Steel Series 32GB: DDR4 3200MHz, CL16, Low Profile Design. Price: $129.99 (25% off).

Mouse

- Logitech G Pro Wireless Gaming Mouse: Lightweight Design, HERO 25K Sensor, Ambidextrous. Price: $129.99.
- Razer DeathAdder V2 Gaming Mouse: Optical Sensor, RGB Lighting, Ergonomic Design. Price: $79.99.
- SteelSeries Rival 600 Gaming Mouse: Dual Optical Sensor, Weight System, RGB Lighting. Price: $89.99.
- HyperX Pulsefire FPS Pro Gaming Mouse: Optical Sensor, RGB Lighting, Ergonomic Design. Price: $59.99.

## Contact Us

Please don't hesitate to get in touch with us if you have any inquiries or feedback about our goods or services. We will try to respond as soon as possible and resolve the issue. 
