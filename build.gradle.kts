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
}

dependencies {
	// Spring Data JPA
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.projectlombok:lombok:1.18.26")

	// PostgreSQL JDBC Driver
	runtimeOnly("org.postgresql:postgresql")

	// Hibernate
	implementation("org.hibernate:hibernate-core")

	// Spring Boot Starter
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Spring Boot Test
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
