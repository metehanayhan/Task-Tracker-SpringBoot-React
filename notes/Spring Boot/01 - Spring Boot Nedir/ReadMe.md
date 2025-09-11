# Spring Boot Nedir?

## 1. Spring Boot Nedir?

- Java tabanlı **açık kaynak bir framework**.
- Spring Framework üzerine kuruludur.
- Amacı: **Konfigürasyonu azaltmak, uygulama geliştirmeyi hızlandırmak**.
- İçinde gömülü sunucu (Tomcat, Jetty vb.) vardır → ekstra kurulum gerektirmez.
- “Microservice” ve “REST API” geliştirmede çok yaygın kullanılır.

---

## 2. Spring Boot’un Temel Özellikleri

1. **Auto-Configuration (Otomatik Yapılandırma):**
    
    Eklediğin bağımlılıklara göre Spring Boot arka planda gerekli ayarları yapar.
    
2. **Starter Dependencies (Başlangıç Paketleri):**
    
    Tek tek bağımlılık eklemek yerine hazır “starter” paketleri vardır.
    
    - `spring-boot-starter-web` → Web uygulamaları
    - `spring-boot-starter-data-jpa` → Veritabanı işlemleri
    - `spring-boot-starter-security` → Güvenlik
    - `spring-boot-starter-test` → Test
3. **Embedded Server (Gömülü Sunucu):**
    - Varsayılan: Tomcat
    - Başka bir şey kurmana gerek yok.
    - `java -jar uygulama.jar` komutu ile uygulama direkt ayağa kalkar.
4. **Production Ready Özellikler:**
    - Monitoring (Actuator)
    - Health check
    - Metrics (performans ölçümleri)

---

## 3. Spring Boot’un Avantajları

- Hızlı geliştirme (minimum konfigürasyon).
- Kolay entegrasyon (JPA, Security, Messaging, Cloud).
- Mikroservis mimarisine uygun.
- Açık kaynak → devasa topluluk desteği.

---

## 4. Spring Boot Proje Yapısı

Spring Boot projesi açıldığında genelde şu yapı olur:

```
src/main/java
 └── com.example.demo
       ├── DemoApplication.java   (ana sınıf, @SpringBootApplication)
       ├── controller             (REST API controller’ları)
       ├── service                (iş mantığı katmanı)
       ├── repository             (veritabanı işlemleri)
       └── model                  (entity sınıfları)

src/main/resources
 ├── application.properties (veya application.yml)
 └── static / templates     (HTML, CSS, JS dosyaları)

```

---

## 5. Önemli Anotasyonlar

Spring Boot’ta anotasyonlar çok önemlidir.

- **`@SpringBootApplication`** → Ana sınıfa konur, uygulamayı başlatır.
- **`@RestController`** → REST API tanımlar.
- **`@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`** → HTTP isteklerini karşılar.
- **`@Service`** → İş mantığını içeren sınıfları belirtir.
- **`@Repository`** → Veritabanı işlemleri için.
- **`@Entity`** → Bir sınıfı veritabanı tablosu yapar.
- **`@Autowired`** → Bağımlılık enjeksiyonu için.

---

## 6. Konfigürasyon Dosyaları

- **application.properties** veya **application.yml** dosyası ile ayarlar yapılır.
- Örn:

```
server.port=8081
spring.datasource.url=jdbc:mysql://localhost:3306/okul
spring.datasource.username=root
spring.datasource.password=1234

```

---

## 7. Spring Boot ile Veritabanı

- **Spring Data JPA** kütüphanesi sayesinde çok az kodla veritabanı işlemleri yapılır.

Örnek Entity:

```java
@Entity
public class Student {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}

```

Repository:

```java
public interface StudentRepository extends JpaRepository<Student, Long> {
}

```

Controller:

```java
@RestController
public class StudentController {
    @Autowired
    private StudentRepository repo;

    @GetMapping("/students")
    public List<Student> getAll() {
        return repo.findAll();
    }
}

```

---

## 8. Spring Boot Actuator

- Uygulamanın durumunu izlemek için kullanılır.
- Örn: `http://localhost:8080/actuator/health` → Uygulamanın çalışıp çalışmadığını gösterir.

---

## 9. Spring Boot Test

- JUnit, Mockito gibi test araçlarıyla entegredir.
- `@SpringBootTest` anotasyonu ile test ortamı hazırlanır.

---

## 10. Gerçek Hayatta Kullanım Alanları

- REST API geliştirme
- Mikroservisler
- Web uygulamaları
- Cloud tabanlı sistemler
- Kurumsal iş uygulamaları

---

## 11. Öğrenirken Bilmen Gereken Önkoşullar

- Java temelleri (OOP, sınıflar, metodlar, interface, paketler).
- HTTP protokolü (GET, POST, PUT, DELETE).
- JSON veri formatı.
- Temel SQL bilgisi.

---

📌 **Özet:** Spring Boot, Spring Framework’ün karmaşıklığını ortadan kaldırarak Java ile modern, hızlı ve kolay bir şekilde web uygulamaları ve mikroservisler geliştirmemizi sağlayan bir framework’tür.

---