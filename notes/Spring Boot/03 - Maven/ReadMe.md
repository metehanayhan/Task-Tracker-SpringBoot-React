# Maven Nedir?

## 1. Maven’in Tanımı

- **Maven**, Java dünyasında en yaygın kullanılan **proje yönetim ve otomasyon aracıdır**.
- Açık kaynaklıdır ve **Apache** tarafından geliştirilmiştir.
- Temel amacı:
    - **Bağımlılık yönetimi** (dependency management)
    - **Proje inşa süreci (build process)**
    - **Standart proje yapısı sağlama**

---

## 2. Maven Ne İşe Yarar?

1. **Bağımlılık Yönetimi:**
    - Java projelerinde harici kütüphaneler kullanmak gerekir (ör. Spring Boot, Hibernate, MySQL Connector).
    - Normalde her kütüphaneyi indirip projeye eklemek gerekir → zahmetli.
    - Maven, bu işi **otomatik yapar**. `pom.xml` dosyasına yazdığın bağımlılıkları kendisi indirir.
2. **Proje Derleme ve Çalıştırma:**
    - `mvn compile` → Projeyi derler.
    - `mvn test` → Testleri çalıştırır.
    - `mvn package` → Jar/War dosyası oluşturur.
    - `mvn spring-boot:run` → Spring Boot projesini çalıştırır.
3. **Standart Dosya Yapısı:**
    
    Maven projelerinin klasör yapısı sabittir.
    

```
src/
 ├── main/
 │    ├── java/        → Java kodları
 │    └── resources/   → Konfigürasyon dosyaları
 └── test/
      └── java/        → Test kodları

```

1. **Plugin Desteği:**
    
    Maven eklentiler (plugin) ile ekstra işler yapabilir:
    
    - Test raporu oluşturma
    - Kod analizi
    - Docker image oluşturma

---

## 3. Maven’in Çalışma Mantığı

- Projede en önemli dosya: **`pom.xml` (Project Object Model)**
- `pom.xml` içinde:
    - Proje bilgileri (adı, versiyonu)
    - Kullanılacak bağımlılıklar (dependencies)
    - Kullanılacak pluginler
    - Build ayarları bulunur.

---

## 4. Örnek `pom.xml`

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <dependencies>
        <!-- Web uygulamaları için Spring Boot starter -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Veritabanı için JPA -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <!-- H2 veritabanı -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
    </dependencies>

</project>

```

👉 Bu dosyada yazılı olan bağımlılıkları Maven, **Maven Central Repository**’den indirir.

---

## 5. Maven Repository Türleri

- **Local Repository:** Bilgisayarındaki `.m2/repository` klasörü (indirilen jar dosyaları burada tutulur).
- **Central Repository:** Maven’in internetteki ana deposu.
- **Remote Repository:** Şirket içinde özel kullanılan repository (ör. Nexus, Artifactory).

---

## 6. Maven Temel Komutları

- `mvn clean` → Eski derleme dosyalarını siler.
- `mvn compile` → Kodları derler.
- `mvn test` → Testleri çalıştırır.
- `mvn package` → Jar/War oluşturur.
- `mvn install` → Jar dosyasını local repo’ya yükler.
- `mvn spring-boot:run` → Spring Boot uygulamasını çalıştırır.

---

## 7. Maven vs Gradle

- **Maven:** XML tabanlı (`pom.xml`), daha klasik.
- **Gradle:** Groovy/Kotlin tabanlı (`build.gradle`), daha modern ve hızlı.
- İkisi de aynı işlevi görür → Proje bağımlılıklarını ve build sürecini yönetir.

---

✅ **Özet:**

Maven, Java projelerinde bağımlılıkları indirip yöneten, projeyi derleyip çalıştıran ve standart bir yapı sağlayan bir **proje yönetim aracıdır**. Spring Boot projelerinin kalbinde **`pom.xml`** vardır.

---

[Pom.xml](Maven%20Nedir%20262e67fac61d80afac1ddc3334cc66dd/Pom%20xml%20262e67fac61d80a7946af4c64890bdfe.md)

---