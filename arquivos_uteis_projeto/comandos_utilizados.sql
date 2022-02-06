-- Comandos Curso Spring boot com Java (UDEMY)

-- Referencias Docuemnatação
https://hub.docker.com/
https://hub.docker.com/_/mysql

-- Clone do projeto
git@github.com:williamgo0205/apirest_java_springboot_udemy.git

----------------
-- 1 - DOCKER --
----------------
-- Executando docker compose
docker-compose up

-- Executando container mysql
docker exec -it mysql mysql -uvendas -pvendas


---------------
-- 2 - MYSQL --
---------------

-- Exibir Databases
 show databases;

-- Acessando um databases
 use <nome_database>
ex: use vendas_db

-- Verificando as tabelas
show tables;
 