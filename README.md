API allows to insert, get, update, remove and manage products, stock and orders.
Products have:
ID
Type
Name
Description
Price

Stock have:
ID (linked with product ID)
Amount

Orders have:
ID
Products
State
Date
Total Price

User is authenticated via Github account to access Orders API.
Products, stock, order can be inserted, get, update, removed and managed with REST services.

Requirement to run (Java version): 11


Definition of Architecture:
API has been developed in REST-ful architecture to provide services with JSON output.
Spring Boot (2.3.4 release) framework has been used to benefit it's rich, lightweight features, Spring data, Oath2, REST and etc. 
Maven has been used to easily add and resolve dependencies, automatically build application. 
Product, Stock and Order models have been set to manage data in API.
H2 Database is used to store product, stock and orders.
spring-boot-starter-data-jpa (2.3.3 release) is used to benefit its CRUD operations and persistence layer to access and persist to DB.
All services and controllers have been tested using JUnit (4.13 version).
org.mockito.Mockito framework used to mock data in tests.
spring-boot's oauth2-client is used to authenticate user to access API.

REST-ful services in API:
URL: /api/
Input: none
Output: none
Type: Any
Use: To print API is working

URL: /api/product/get/{id}
Input: id (int)
Output: Optional<Product>
Type: GET
Use: Get product by ID

URL: /api/product/new
Input: Product
Output: Product
Type: PUT
Use: To add new product

URL: /api/product/remove/{id}
Input: id (int)
Output: boolean (success)
Type: DELETE
Use: Delete product

URL: /api/stock/get/{id}
Input: id (int)
Output: int
Type: GET
Use: Get stock amount by ID

URL: /api/stock/in/{id}/{amount}"
Input: id (int), amount (int)
Output: Optional<Stock>
Type: POST
Use: To add new products to stock

URL: /api/stock/out/{id}/{amount}"
Input: id (int), amount (int)
Output: Optional<Stock>
Type: POST
Use: To take products from stock

URL: /api/stock/update"
Input: Stock
Output: Optional<Stock>
Type: PUT
Use: To update stock

URL: /api/stock/remove/{id}
Input: id (int)
Output: boolean (success)
Type: DELETE
Use: Remove stock

URL: /api/order/get/{id}
Input: id (int)
Output: Optional<Order>
Type: GET
Use: Get order by ID

URL: /api/order/new"
Input: products (Map<Product, Integer>)
Output: Optional<Order>
Type: POST
Use: To make new order

URL: /api/order/cancel/{id}"
Input: id (int)
Output: Optional<Order>
Type: PUT
Use: To cancel order by id