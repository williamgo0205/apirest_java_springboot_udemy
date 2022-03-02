-- Comandos Curso Spring boot com Java (UDEMY)

-- Referencias Documentação
https://hub.docker.com/
https://hub.docker.com/_/mysql

-- Projeto Spring
https://start.spring.io/

-- Link Swagger local
http://localhost:8080/swagger-ui.html

-- Link Jaeger - Acessar Docs/Get Started (Controle da aplicação com Microserviços)
https://www.jaegertracing.io/

    -- Acesso local Jaeager
    http://localhost:16686/search

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
 