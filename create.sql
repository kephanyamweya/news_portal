CREATE DATABASE news_portal;
\c news_portal;
CREATE TABLE departments (id serial PRIMARY KEY, name varchar, description varchar, noofemployees int);
CREATE TABLE news (id serial PRIMARY KEY, content varchar, author varchar, createdat BIGINT, departmentid int, type varchar);
CREATE TABLE users (id serial PRIMARY KEY, name varchar, role varchar, departmentid int);
CREATE DATABASE news_portal_test WITH TEMPLATE news_portal;