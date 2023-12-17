plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("io.javalin.community.openapi:javalin-openapi-plugin:5.6.3-2")
    implementation("io.javalin.community.openapi:javalin-swagger-plugin:5.6.3-2")
    implementation("org.apache.shiro:shiro-core:1.10.0")
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("io.etcd:jetcd-core:0.7.6")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
}

application {
    mainClass.set("org.example.Main")
}

tasks.test {
    useJUnitPlatform()
}