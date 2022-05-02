-- Comandos Curso Spring boot com Java (UDEMY)

--------------------------------
-- ## REFERNCIAS DOCUMENTACAO --
--------------------------------

-- Docker
https://hub.docker.com/
https://hub.docker.com/_/mysql

-- SpringBoot
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/

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

-----------------
-- ## ACTUATOR --
-----------------

-- Verificando se a aplicação está "de Pé" (local):
http://localhost:8080/actuator/health

-- Verificado as métricas do projeto:
http://localhost:8080/actuator/matrics

-------------------
-- ## PROMETHEUS --
-------------------

-- Documentação do projeto:
https://prometheus.io/docs/introduction/overview/

-- Obter métricas do projeto
https://micrometer.io/docs/registry/prometheus

-- Instalação Prometheus:
https://micrometer.io/docs/registry/prometheus#_installing

-- Endereço Local - Configurado no arquivo "prometheus.yml" 
-- do projeto apontando para o endereço IP da maquina local
http://localhost:9090/

----------------
-- ## GRAFANA --
----------------

-- Site Grafana
https://grafana.com/
https://grafana.com/grafana/

-- Imagem do grafana pelo docker hub
https://hub.docker.com/r/grafana/grafana/

-- A versão mais atual do grafana pode ser obtida em
https://hub.docker.com/r/grafana/grafana/tags

-- Endereço local grafana
http://localhost:3000/login
username: admin
password: admin

------------------------------------
-- ## FORMATAÇÃO DE CAMPOS - JAVA --
------------------------------------

-- regexr
https://regexr.com/

----------------------
-- ## CLONE PROJETO --
----------------------
git@github.com:williamgo0205/apirest_java_springboot_udemy.git

-----------------------------------------------------------
-- ## Gerar imagem do projeto através de uma imagem base --
-----------------------------------------------------------
Link: https://hub.docker.com/_/openjdk

comandos utilizados no arquivo Dockerfile

-- versão do java
FROM openjdk:11
-- porta da aplicação
EXPOSE 8080
-- Arquivo ao qual será gerado o jar 
-- caminho origem: /target/gestao-vendas-0.0.1-SNAPSHOT.jar
-- caminho destino: /app/gestao-vendas.jar
COPY /target/gestao-vendas-0.0.1-SNAPSHOT.jar /app/gestao-vendas.jar
-- Diretório de trabalho
WORKDIR /app
-- Comandos para a execução do arquivo gestao-vendas.jar
-- exemplo: java -jar gestao-vendas.jar
ENTRYPOINT [ "java", "-jar", "/app/gestao-vendas.jar"]

---------------------------------------------------
-- ## Controlando Start order com Docker-Compose --
---------------------------------------------------
link: https://docs.docker.com/compose/startup-order/

----------------
-- 1 - DOCKER --
----------------
-- Executando docker compose
docker-compose up

-- Executando docker compose Liberando Terminal
docker-compose up -d

-- Startando e Buildando no docker compose
docker-compose up --build

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
 