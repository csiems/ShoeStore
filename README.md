#Shoe Stores!

#### An app to track shoe stores and brands shoes.

#### By Christopher Siems

## Description

The Shoe Stores! app will allow you to track your favorite shoe stores and create a list of the shoe brands they carry.

## Setup/Installation Requirements

* Clone this repository.
* Make sure you have Gradle, Java and Postgres installed.
* In a terminal: Open `postgres`
* Open psql in a new tab
* Run the following command in psql to create the database
`CREATE DATABASE shoe_stores`
* Navigate to your project directory in your terminal and run the following command to populate your database
`psql shoe_stores < shoe_stores.sql`
* Alternately, you can build your own version of this database with the following commands in PSQL:
`CREATE DATABASE shoe_stores;`
`CREATE TABLE stores (id serial PRIMARY KEY, name varchar);`
`CREATE TABLE brands (id serial PRIMARY KEY, name varchar);`
`CREATE TABLE brands_stores (id serial PRIMARY KEY, brand_id int, store_id int)`
* In the top level of the cloned directory, run the following command in your terminal:
`gradle run`
* Open your web browser of choice to localhost:4567

## Technologies Used

Java, PostgreSQL, Spark, JUnit, Velocity, Bootstrap, FluentLenium

### License

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

Copyright (c) 2016 **_Christopher Siems_**
