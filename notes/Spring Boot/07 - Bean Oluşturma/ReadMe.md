# Bean Oluşturma

# 1) `@Bean` nedir?

- Bir **Java metodunu**, Spring’in yönettiği bir **bean üreticisi (factory method)** yapar.
- Metodun **döndürdüğü nesne**, **Spring Context** içine “bean” olarak kaydedilir.
- En yaygın kullanım yeri: **`@Configuration`** sınıfları.

```java
@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper(); // Context’e 'objectMapper' adıyla eklenir
    }
}

```

> Bean adı varsayılan olarak metot adıdır (objectMapper). İstersen isim verebilirsin: @Bean("jsonMapper").
> 

---

# 2) Ne zaman `@Bean` kullanılır?

- **Kendi yazmadığın** sınıfları (üçüncü parti kütüphane: `ObjectMapper`, `DataSource`, `RestTemplate` vb.) Spring’e tanıtmak istediğinde.
- Varsayılan yaratılış biçimini **özelleştirmen** gerektiğinde (constructor’a parametre, custom ayar).
- Otomatik tarama (`@Component`) ile gelmeyen nesneleri **elde** üretmek istediğinde.

---

# 3) `@Configuration` ile tam davranış (proxy) – neden önemli?

`@Bean` metodlarını genellikle **`@Configuration`** içine yaz:

```java
@Configuration // proxyBeanMethods=true (varsayılan) → tam fabrika davranışı
public class AppConfig {

    @Bean
    public A a() { return new A(b()); }

    @Bean
    public B b() { return new B(); }
}

```

- Bu sayede `a()` içinden `b()` çağrıldığında **yeni nesne üretilmez**; Spring proxy’si devreye girer ve **Context’teki tekil `B` bean’i** verilir.
- Eğer sınıf yalnızca `@Component` ise (veya `@Configuration(proxyBeanMethods=false)` ise) bu garanti **yoktur**; metodu doğrudan çağırıp **yeni** nesne üretebilirsin (istenmeyen durum).

> Kural: @Bean yazıyorsan tercihen @Configuration kullan ve (çoğu durumda) varsayılan proxyBeanMethods=true bırak.
> 

---

# 4) `@Bean` metodu parametre alabilir (otomatik enjeksiyon)

Spring, metot parametrelerini Context’ten **kendisi enjekte eder**:

```java
@Configuration
public class HttpConfig {

    @Bean
    public RestClient restClient(ObjectMapper mapper) {
        return RestClient.builder()
                         .messageConverter(new MappingJackson2HttpMessageConverter(mapper))
                         .build();
    }
}

```

- `ObjectMapper` bean’i varsa Spring onu otomatik geçer.
- Bu stil, **constructor injection**’ın metot versiyonu gibi düşünülebilir.

---

# 5) Yaşam döngüsü: init/destroy metotları

İhtiyaç olursa başlatma/kapatma kancaları verebilirsin:

```java
@Configuration
public class LifecycleConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public MyWorker worker() {
        return new MyWorker(); // MyWorker#start ve #stop çağrılır
    }
}

```

Alternatif olarak sınıfın içinde `@PostConstruct` / `@PreDestroy` da kullanabilirsin.

---

# 6) Scope, isim, nitelik (qualifier)

- Varsayılan **scope = singleton** (Context başına tek örnek).
- Değiştirmek için:

```java
@Bean
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public Task task() { return new Task(); }

```

- İsim vermek:

```java
@Bean(name = "fastMapper")
public ObjectMapper mapper() { return new ObjectMapper(); }

```

- Birden fazla aday için `@Qualifier`/`@Primary` kullan:

```java
@Bean @Primary
public Notifier emailNotifier() { return new EmailNotifier(); }

@Bean("sms")
public Notifier smsNotifier() { return new SmsNotifier(); }

```

---

# 7) Ortama göre (profile/conditional) `@Bean`

- Sadece belirli profilde:

```java
@Profile("dev")
@Bean
public DataSource h2() { /* ... */ }

```

- Şarta bağlı:

```java
@ConditionalOnMissingBean(ObjectMapper.class)
@Bean
public ObjectMapper defaultMapper() { return new ObjectMapper(); }

```

> Bu conditional anotasyonları çoğunlukla Spring Boot auto-config tarafında görürsün; kendi config’inde de kullanılabilir.
> 

---

# 8) En çok düşülen 6 tuzak (ve kısa çözümler)

1. **`@Configuration` yerine `@Component` kullanıp** `@Bean` yazmak → inter-method çağrıda yeni nesne oluşabilir.
    
    → Çözüm: `@Configuration` kullan (proxy’li).
    
2. `@Bean` metodu **`private`/`final`** yapmak → CGLIB proxy sorunları.
    
    → Çözüm: `public` (veya en az `package-private`) bırak, `final` yapma.
    
3. Aynı tipe **iki bean** ve **hangi biri enjekte edileceği belirsiz** → `NoUniqueBeanDefinitionException`.
    
    → Çözüm: `@Primary` veya `@Qualifier`.
    
4. **Dairesel bağımlılık** (A `@Bean` B’yi çağırıyor, B de A’yı) → başlatma hatası.
    
    → Çözüm: Tasarımı sadeleştir, bağımlılık yönünü kır.
    
5. `init/destroy` isimleri **yanlış** → kancalar çalışmaz.
    
    → Çözüm: Metot adlarını doğru ver, ya da `@PostConstruct/@PreDestroy` kullan.
    
6. `proxyBeanMethods=false` ile performans uğruna kapatıp **aynı konfig içinde `@Bean` metodunu çağırmak** → **yeni nesne** üretir.
    
    → Çözüm: Bu modu açma ya da inter-bean çağrı yapma (bağımlılığı parametre olarak iste).
    

---

# 9) Kısa, pratik bir demo

```java
@Configuration
public class AppConfig {

    // 1) Üçüncü parti nesne: tekil ObjectMapper
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().findAndRegisterModules();
    }

    // 2) Parametre enjeksiyonu: mapper otomatik gelir
    @Bean
    public JsonService jsonService(ObjectMapper mapper) {
        return new JsonService(mapper);
    }

    // 3) İsteğe bağlı: yaşam döngüsü kancaları
    @Bean(initMethod = "init", destroyMethod = "close")
    public CacheManager cacheManager() {
        return new CacheManager(256);
    }
}

```

Kullanım:

```java
@Service
public class ReportService {
    private final JsonService json;

    public ReportService(JsonService json) { // Context’ten gelir
        this.json = json;
    }
}

```

---

## Özet (tek sayfa reçete)

- `@Bean`: Metodun döndürdüğü nesneyi **Context’e bean** olarak ekler.
- En doğru yer: **`@Configuration`** sınıfı (proxy’li, tekillik garantili).
- Parametreler **otomatik enjekte edilir**.
- İsim = metot adı; `name=...` ile özelleştirilebilir.
- Scope, init/destroy, qualifier/primary ile ince ayar yapılır.
- Inter-`@Bean` çağrılarında **`@Configuration`** şart (aksi halde yeni örnek üretirsin).

---