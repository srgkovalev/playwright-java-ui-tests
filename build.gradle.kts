plugins {
    id("java")
    // lombok заменяет геттеры/сеттеры
    id("io.freefair.lombok") version "8.11"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Дефолтные тесты
    implementation(platform("org.junit:junit-bom:5.10.0"))
    implementation("org.junit.jupiter:junit-jupiter")
    // Использование браузеров, эмуляция устройств и сетевых условий, интеграция с CI
    implementation("com.microsoft.playwright:playwright:1.41.0")
    // Для удобного чтения конфигов
    implementation("org.aeonbits.owner:owner:1.0.12")
    // Для логирования
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.4.14")
}

tasks.test {
    useJUnitPlatform()
}

