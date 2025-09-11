# Spring Context & Bean

# 📘 Spring Context & Bean

## 1) Bean nedir?

- **Bean**, Spring’in senin yerine oluşturup yönettiği nesnedir.
- Normalde Java’da nesne oluştururken `new` kullanırız:
    
    ```java
    UserService service = new UserService();
    ```
    
    Ama Spring’de `new` demene gerek yok.
    
    Çünkü Spring, bu nesneyi kendi **“kasesine”** koyar.
    
- Bu kaseye **Spring Context** (veya ApplicationContext) denir.

👉 **Benzetme:**

Bir mutfakta **fasulye taneleri (bean)** var, hepsi bir kasenin (context) içinde duruyor.

İhtiyacın olduğunda gidip “bana fasulye ver” diyorsun, Spring de veriyor. Sen fasulyeyi tek tek üretmiyorsun.

---

## 2) Spring Context nedir?

- Spring Context, **bean’leri saklayan ve yöneten bir kutudur.**
- Uygulaman başlarken Spring bu kutuyu oluşturur, içine gerekli bean’leri doldurur.
- Sen hangi sınıfa ihtiyaç duyarsan Spring o sınıfı kutudan çıkarıp sana verir.

---

## 3) Neden lazım?

Düşün ki bir sınıfı birden fazla yerde kullanıyorsun:

- `UserService` hem `UserController` hem `OrderController` içinde lazım.
- Normalde her seferinde `new UserService()` yazman gerekir → kod tekrar eder, yönetmek zorlaşır.

Spring Context bunu senin yerine yapar:

- Bir kere `UserService` oluşturur,
- Nerede ihtiyacın varsa aynı nesneyi verir.

👉 Böylece hem bellek tasarrufu olur hem de bağımlılıklar (dependencies) daha düzenli yönetilir.

---

## 4) Bean nasıl yapılır?

Bir sınıfı Spring’in “kasesine” eklemek için `@Component`, `@Service`, `@Repository`, `@Controller` veya `@Bean` kullanırsın.

### Örnek 1: @Component ile

```java
@Component
public class UserService {
    public String getUser() {
        return "Ali Veli";
    }
}
```

Spring açılırken bunu kasesine koyar.

### Örnek 2: @Bean ile

```java
@Configuration
public class AppConfig {
    @Bean
    public UserService userService() {
        return new UserService();
    }

```

Burada da elle tarif edip kasenin içine koyuyorsun.

---

## 5) Kullanım (Enjeksiyon)

Spring’e “bana fasulyeyi ver” demek için `@Autowired` kullanabilirsin.

```java
@RestController
public class UserController {

    @Autowired
    private UserService userService; // Spring kaseden alıp buraya koyar

    @GetMapping("/user")
    public String getUser() {
        return userService.getUser();
    }
}

```

Burada sen `new UserService()` demedin, Spring arka planda kendi kasasından verdi.

---

## 6) Kısacası

- **Spring Context = Kase**
- **Bean = Fasulye tanesi (yönetilen nesne)**
- Sen `new` demiyorsun, Spring oluşturuyor.
- Başka sınıflarda ihtiyaç olunca `@Autowired` veya constructor injection ile alıyorsun.