plugins {
    java
    war
    id("org.springframework.boot") version "2.6.9"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "com.sportyfind"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation(kotlin("stdlib"))

    // Spring Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt:0.9.1")

    // Swagger
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.springfox:springfox-boot-starter:3.0.0")

    // Spring Data JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    // PostgreSQL JDBC Driver
    runtimeOnly("org.postgresql:postgresql")

    // Hibernate
    implementation("org.hibernate:hibernate-core")

    // Spring Boot Starter
    implementation("org.springframework.boot:spring-boot-starter-web")
//	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2021.0.6")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
