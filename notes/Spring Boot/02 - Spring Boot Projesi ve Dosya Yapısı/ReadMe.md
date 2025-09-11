# Spring Boot Projesi ve Dosya Yapısı

## 1. Spring Boot Projesi Açma Yöntemleri

Spring Boot projelerini birkaç farklı yolla açabilirsin:

### 🔹 1.1. Spring Initializr (En Kolay Yöntem)

- https://start.spring.io adresine git.
- Şu seçimleri yap:
    - **Project:** Maven Project
    - **Language:** Java
    - **Spring Boot Version:** En güncel kararlı sürüm (örn. 3.3.x)
    - **Dependencies (bağımlılıklar):**
        - Spring Web
        - Spring Data JPA
        - H2 Database (ya da MySQL)
        - Lombok (opsiyonel, kodu kısaltır)
- “Generate” tuşuna bas → `.zip` dosyası iner.
- Zip’i aç → IDE’de (IntelliJ, VS Code, Eclipse, STS) aç.

---

### 🔹 1.2. IDE Üzerinden Açma

- **IntelliJ IDEA** → New Project → Spring Initializr seç.
- **Eclipse / STS** → File → New → Spring Starter Project.
- Adımları doldur, bağımlılıkları seç, proje hazır olur.

---

### 🔹 1.3. Maven/Gradle ile Manuel

Terminalden:

```bash
mvn archetype:generate

```

ama genelde Spring Initializr daha çok tercih edilir.

---

## 2. Spring Boot Projesi Dosya Yapısı

Bir Spring Boot projesi açtığında klasör yapısı şöyle olur:

```
myproject/
 ├── src/
 │    ├── main/
 │    │    ├── java/
 │    │    │     └── com/example/myproject/
 │    │    │            ├── MyprojectApplication.java   # Ana sınıf
 │    │    │            ├── controller/                 # Controller katmanı
 │    │    │            ├── service/                    # Service katmanı
 │    │    │            ├── repository/                 # Repository katmanı
 │    │    │            └── model/                      # Entity sınıfları
 │    │    │
 │    │    └── resources/
 │    │            ├── application.properties           # Ayar dosyası
 │    │            ├── static/                          # Statik dosyalar (css, js, resimler)
 │    │            └── templates/                       # HTML sayfaları (Thymeleaf, FreeMarker)
 │    │
 │    └── test/
 │           └── java/
 │                 └── com/example/myproject/           # Test sınıfları
 │
 ├── .mvn/        # Maven ayar dosyaları
 ├── mvnw         # Maven Wrapper (Linux/Mac)
 ├── mvnw.cmd     # Maven Wrapper (Windows)
 ├── pom.xml      # Maven bağımlılık dosyası

```

---

## 3. Dosyaların ve Klasörlerin İçeriği

### 🔹 `MyprojectApplication.java`

Bu dosya her projede vardır.

```java
@SpringBootApplication
public class MyprojectApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyprojectApplication.class, args);
    }
}

```

- Uygulamanın giriş noktasıdır.
- İçinde `@SpringBootApplication` anotasyonu bulunur.

---

### 🔹 `controller/`

- HTTP isteklerini (GET, POST, PUT, DELETE) karşılayan sınıflar burada olur.

```java
@RestController
@RequestMapping("/api")
public class StudentController {
    @GetMapping("/hello")
    public String hello() {
        return "Merhaba Spring Boot!";
    }
}

```

---

### 🔹 `service/`

- İş mantığını içerir. Controller’dan gelen istekler burada işlenir.

```java
@Service
public class StudentService {
    public String getStudentName() {
        return "Ali Veli";
    }
}

```

---

### 🔹 `repository/`

- Veritabanı işlemleri için. Spring Data JPA kullanılır.

```java
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}

```

---

### 🔹 `model/`

- Veritabanındaki tabloları temsil eden sınıflar (Entity).

```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}

```

---

### 🔹 `resources/application.properties`

- Uygulama ayarları burada yapılır.
    
    Örnek:
    

```
server.port=8081
spring.datasource.url=jdbc:mysql://localhost:3306/okul
spring.datasource.username=root
spring.datasource.password=1234

```

---

### 🔹 `resources/static/`

- Statik dosyalar (CSS, JS, resim) buraya konur.

### 🔹 `resources/templates/`

- Thymeleaf gibi template engine kullanılıyorsa HTML dosyaları buraya konur.

---

### 🔹 `pom.xml`

- Maven bağımlılıklarının listelendiği dosya.
    
    Örnek:
    

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>

```

---

## 4. Uygulama Çalıştırma

Projeyi açtıktan sonra:

- IntelliJ/Eclipse → **Run** tuşuna bas.
- Ya da terminalden:

```bash
mvn spring-boot:run

```

- Tarayıcıda `http://localhost:8080` adresine git → uygulama çalışır.

---

✅ ÖZET:

- Spring Boot projesi **Spring Initializr** ile kolayca açılır.
- Standart katmanlı yapı: `controller`, `service`, `repository`, `model`.
- Konfigürasyon `application.properties` dosyasında yapılır.
- Gömülü Tomcat sayesinde ek sunucu kurulumu gerekmez.

---