# ==========================================
# ESTÁGIO 1: BUILDER (Onde o código é compilado)
# ==========================================
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

# Define o diretório de trabalho dentro do container
WORKDIR /build

# DICA DE SÊNIOR: Copiamos o pom.xml ANTES do código fonte.
# Por que? O Docker faz cache em camadas. Se você mudar apenas uma linha no seu 
# código Java, o Docker não vai baixar a internet inteira (dependências do Maven) de novo!
COPY pom.xml .
RUN mvn dependency:go-offline

# Agora sim, copiamos o código fonte
COPY src ./src

# Compila o projeto e gera o arquivo .jar (ignorando testes para acelerar o build)
RUN mvn clean package -DskipTests

# ==========================================
# ESTÁGIO 2: RUNTIME (A imagem final, leve e segura)
# ==========================================
# Usamos apenas o JRE (Java Runtime Environment), que é bem mais leve que o JDK (que tem compilador)
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# DICA DE SECOPS (Security Operations): 
# Por padrão, o Docker roda tudo como 'root'. Isso é uma falha de segurança gravíssima.
# Aqui criamos um usuário comum chamado 'spring' e dizemos ao container para rodar com ele.
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiamos apenas o arquivo .jar gerado lá no Estágio 1 (Builder)
COPY --from=builder /build/target/*.jar app.jar

# Informa qual porta o container vai escutar
EXPOSE 8080

# O comando que será executado quando o container ligar
ENTRYPOINT ["java", "-jar", "app.jar"]