create database EXODUS_STS;
create user 'testuser'@'%' identified by 'password';
grant all on EXODUS_STS.* to 'testuser'@'%;
