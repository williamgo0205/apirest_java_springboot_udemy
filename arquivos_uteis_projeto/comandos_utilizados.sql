-- Comandos Curso Spring boot com Java (UDEMY)

--------------------------------
-- ## REFERNCIAS DOCUMENTACAO --
--------------------------------
https://hub.docker.com/
https://hub.docker.com/_/mysql

-----------------------
-- ## PROJETO SPRING --
-----------------------
https://start.spring.io/

----------------
-- ## SWAGGER --
----------------
-- Link Swagger local
http://localhost:8080/swagger-ui.html

-----------------------
-- ## TRACING JAEGER --
-----------------------
-- Link Jaeger - Acessar Docs/Get Started (Controle da aplicação com Microserviços)
https://www.jaegertracing.io/

    -- Acesso local Jaeager
    http://localhost:16686/search

-- links GIT

-- java-spring-jaeger
https://github.com/opentracing-contrib/java-spring-jaeger

-- java-jdbc
https://github.com/opentracing-contrib/java-jdbc

----------------------
-- ## CLONE PROJETO --
----------------------
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
 