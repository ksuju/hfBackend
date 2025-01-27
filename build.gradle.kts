plugins {
	java
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.ll"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("com.mysql:mysql-connector-j")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	// WebSocket, STOMP
	implementation("org.springframework.boot:spring-boot-starter-websocket:3.4.1")

	// JWT & Spring Security
	testImplementation("org.springframework.security:spring-security-test")
	implementation ("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// ApiScheduler: Xml -> Json
	dependencies {
		implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.18.2")
		implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
		implementation("com.fasterxml.jackson.core:jackson-annotations:2.18.2")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
