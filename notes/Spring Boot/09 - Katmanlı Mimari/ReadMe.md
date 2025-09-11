# Katmanlı Mimari

### Başlamadan Önce: Neden Katmanlı Mimari?

Bir yazılım projesini tek bir büyük dosya içinde yazmadığımız gibi, tüm mantığı da tek bir sınıf içinde toplamayız. Katmanlı mimari, bir uygulamanın farklı sorumluluklarını mantıksal gruplara (katmanlara) ayırma prensibidir. Bunun temel faydaları şunlardır:

- **Separation of Concerns (Sorumlulukların Ayrılması):** Her katman sadece kendi işini yapar. Veritabanı ile konuşan kod, kullanıcı arayüzü mantığını bilmez. Bu, kodu daha anlaşılır ve yönetilebilir kılar.
- **Maintainability (Sürdürülebilirlik):** Bir katmanda yapacağın değişiklik, diğer katmanları minimum düzeyde etkiler. Örneğin, veritabanını MySQL'den PostgreSQL'e geçirmek istediğinde sadece Veri Erişim Katmanı'nı değiştirmen yeterli olur.
- **Testability (Test Edilebilirlik):** Her katman, diğerlerinden bağımsız olarak test edilebilir. Servis katmanını, gerçek bir veritabanı olmadan sahte (mock) verilerle test edebilirsin.
- **Reusability (Yeniden Kullanılabilirlik):** Özellikle servis katmanındaki iş mantığı, farklı arayüzler (web, mobil, masaüstü) tarafından tekrar tekrar kullanılabilir.

---

### Temel Kavramlar: Büyünün Başladığı Yer (IoC, DI, Spring Context ve Bean)

Katmanların nasıl çalıştığını anlamadan önce, Spring'in bu katmanları nasıl yönettiğini ve birbirine bağladığını anlamamız gerekiyor.

### 1. Inversion of Control (IoC - Kontrolün Tersine Çevrilmesi)

Normalde, bir nesne kendi içinde ihtiyaç duyduğu başka bir nesneyi kendisi yaratır. Örneğin:

Java

```java
public class SiparisService {
    private UrunRepository urunRepository;

    public SiparisService() {
        // KONTROL BİZDE: UrunRepository nesnesini biz yaratıyoruz.
        this.urunRepository = new UrunRepository();
    }

    public void siparisOlustur() {
        //...
        urunRepository.stoktanDus();
        //...
    }
}
```

Bu yaklaşımda `SiparisService`, `UrunRepository` nesnesinin nasıl yaratılacağını bilmek zorundadır ve ona sıkı sıkıya bağlıdır (`tightly coupled`).

**IoC** bu durumu tersine çevirir. Nesnelerin yaratılması ve yönetilmesi kontrolünü bizden alıp bir çerçeveye (framework), yani **Spring'e** verir. Biz artık `new UrunRepository()` demeyiz. Sadece "Benim bir `UrunRepository` nesnesine ihtiyacım var" deriz, Spring de onu bizim için bulur ve hazır hale getirir. Kontrol bizden Spring'e geçer. İşte bu "Kontrolün Tersine Çevrilmesi"dir.

### 2. Dependency Injection (DI - Bağımlılık Enjeksiyonu)

IoC prensibinin pratikteki uygulama şekline **Dependency Injection (Bağımlılık Enjeksiyonu)** denir. Spring, kontrolü eline aldıktan sonra, bir nesnenin ihtiyaç duyduğu bağımlılıkları (diğer nesneleri) ona dışarıdan "enjekte eder" (verir).

Yukarıdaki örneğin DI ile yazılmış hali:

Java

```java
@Service // Bu sınıfın bir Spring bileşeni olduğunu belirtiyoruz
public class SiparisService {
    private final UrunRepository urunRepository;

    // KONTROL SPRING'DE: Spring, UrunRepository nesnesini constructor'a parametre olarak enjekte ediyor.
    @Autowired
    public SiparisService(UrunRepository urunRepository) {
        this.urunRepository = urunRepository;
    }

    public void siparisOlustur() {
        //...
        urunRepository.stoktanDus();
        //...
    }
}
```

Artık `SiparisService`'in `UrunRepository`'nin nasıl yaratıldığı hakkında hiçbir fikri yok. Sadece ona verilen hazır bir nesneyi kullanıyor. Bu, sınıflar arasındaki bağımlılığı gevşetir (`loosely coupled`).

### 3. Spring IoC Container (ApplicationContext)

Peki bu IoC ve DI işlerini yapan kim? İşte bu "sihirli kutu"nun adı **Spring IoC Container** veya daha yaygın adıyla **ApplicationContext**'tir.

Uygulama başladığında, Spring bu container'ı oluşturur. Görevi şunlardır:

1. Uygulamadaki belirli sınıfları tarar (scan eder).
2. Bu sınıflardan nesneler yaratır.
3. Bu nesnelerin yaşam döngüsünü yönetir (oluşturur, bağımlılıklarını enjekte eder, ve en sonunda yok eder).
4. Bu yönettiği nesneleri (Bean'leri) kendi içinde saklar ve ihtiyaç duyulduğunda başka nesnelere verir.

### 4. Bean

Spring IoC Container tarafından yönetilen (yaratılan, yapılandırılan ve bir araya getirilen) her nesneye **Bean** denir. Kısacası, `@Component`, `@Service`, `@Repository`, `@Controller` gibi anotasyonlarla işaretlediğin sınıflardan Spring'in yarattığı nesneler birer "Bean"dir.

---

### Spring Boot'ta Katmanlı Mimari ve Anotasyonları

Şimdi bu temel kavramları anladığımıza göre, klasik bir Spring Boot web uygulamasındaki katmanları ve görevlerini inceleyebiliriz.

### 1. Presentation Katmanı (Sunum Katmanı - Controller)

- **Görevi:** Dış dünyadan gelen istekleri (genellikle HTTP istekleri) karşılayan ilk katmandır. Gelen isteği analiz eder, verileri (JSON, XML vb.) alır, doğrular ve bu isteği işlemek üzere uygun olan Servis katmanındaki metoda paslar. Servis katmanından gelen sonucu da (başarı, hata, veri vb.) yine dış dünyaya uygun bir formatta (genellikle JSON formatında bir HTTP yanıtı olarak) geri döner. **Kesinlikle iş mantığı içermemelidir.**
- **Anotasyonlar:**
    - `@RestController`: Bu anotasyon, sınıfın bir web istek karşılayıcısı olduğunu belirtir. İçindeki metotlardan dönen değerlerin doğrudan HTTP yanıtının gövdesine (body) yazılmasını sağlar (genellikle JSON olarak). `@Controller` ve `@ResponseBody` anotasyonlarının birleşimidir.
    - `@Controller`: Eğer bir web sayfası (HTML) döndürecekseniz (örneğin Thymeleaf ile) kullanılır.
    - `@RequestMapping`, `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`: Hangi HTTP metodu ve hangi URL yolunun hangi metodu çalıştıracağını belirler.
    - `@PathVariable`, `@RequestParam`, `@RequestBody`: İstekle gelen verileri (URL'den, parametreden, gövdeden) metot parametrelerine bağlamak için kullanılır.

**Örnek:**

Java

```java
@RestController
@RequestMapping("/api/kullanicilar")
public class KullaniciController {

    private final KullaniciService kullaniciService;

    // Bağımlılık Constructor Injection ile enjekte ediliyor
    @Autowired
    public KullaniciController(KullaniciService kullaniciService) {
        this.kullaniciService = kullaniciService;
    }

    @GetMapping("/{id}")
    public Kullanici getKullaniciById(@PathVariable Long id) {
        // Gelen isteği doğrudan servis katmanına yönlendirir
        return kullaniciService.getKullaniciById(id);
    }

    @PostMapping
    public Kullanici createKullanici(@RequestBody Kullanici kullanici) {
        // Gelen veriyi (JSON body) doğrudan servis katmanına yönlendirir
        return kullaniciService.createKullanici(kullanici);
    }
}
```

### 2. Service Katmanı (İş Mantığı Katmanı)

- **Görevi:** Uygulamanın beynidir. Tüm iş mantığı, kurallar, hesaplamalar ve akış kontrolü bu katmanda yer alır. Bir işlemi gerçekleştirmek için birden fazla Repository'yi veya başka servisleri koordine edebilir (örneğin, sipariş oluştururken hem stok kontrolü yapıp hem de fatura oluşturmak gibi). **Transaction yönetimi genellikle bu katmanda başlar ve biter.**
- **Anotasyon:**
    - `@Service`: Bu sınıfın iş mantığı barındıran bir Spring bileşeni (Bean) olduğunu belirtir. Temelde `@Component` anotasyonunun bir özelleşmiş halidir ve kodun okunabilirliğini artırır. Spring bu sınıftan bir nesne yaratıp container'a koyar.

**Örnek:**

Java

```java
@Service
public class KullaniciService {

    private final KullaniciRepository kullaniciRepository;

    // Bağımlılık enjekte ediliyor
    @Autowired
    public KullaniciService(KullaniciRepository kullaniciRepository) {
        this.kullaniciRepository = kullaniciRepository;
    }

    public Kullanici getKullaniciById(Long id) {
        return kullaniciRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));
    }

    public Kullanici createKullanici(Kullanici kullanici) {
        // İŞ MANTIĞI: Örneğin yeni kullanıcı e-maili sistemde var mı diye kontrol et
        if (kullaniciRepository.existsByEmail(kullanici.getEmail())) {
            throw new RuntimeException("Bu email adresi zaten kullanılıyor!");
        }
        // İş mantığı kontrolleri sonrası veri tabanına kaydet
        return kullaniciRepository.save(kullanici);
    }
}
```

### 3. Data Access Katmanı (Veri Erişim Katmanı - Repository)

- **Görevi:** Veritabanı veya diğer veri kaynakları (API'lar, dosyalar vb.) ile iletişimi sağlayan katmandır. Sadece veri okuma (CRUD - Create, Read, Update, Delete) operasyonlarından sorumludur. **Kesinlikle iş mantığı içermemelidir.** Spring Data JPA sayesinde bu katmanı yazmak çok kolaylaşır.
- **Anotasyon:**
    - `@Repository`: Bu sınıfın bir veri erişim bileşeni (Bean) olduğunu belirtir. `@Service` gibi bu da `@Component`'in bir özelleşmiş halidir. Önemli bir artısı, veritabanı ile ilgili fırlatılabilecek özel exception'ları (istisnaları) Spring'in daha anlaşılır `DataAccessException`'larına çevirmesini sağlar.

**Örnek (Spring Data JPA ile):**

Java

```java
@Repository
// JpaRepository'yi extend ederek temel CRUD operasyonlarını hazır olarak alırız.
public interface KullaniciRepository extends JpaRepository<Kullanici, Long> {

    // Spring Data JPA, metot isminden sorguyu kendisi oluşturur!
    boolean existsByEmail(String email);

    // JPQL ile özel sorgu yazma imkanı
    @Query("SELECT k FROM Kullanici k WHERE k.aktif = true")
    List<Kullanici> findAllAktifKullanicilar();
}
```

### 4. Domain/Model Katmanı (Varlık Katmanı - Entity)

- **Görevi:** Bu katman, uygulamanın temel varlıklarını temsil eden sınıfları (POJO - Plain Old Java Object) içerir. Genellikle veritabanındaki tabloların bir yansımasıdırlar. Uygulama içinde veriyi bir katmandan diğerine taşımak için kullanılırlar.
- **Anotasyonlar (JPA kullanılıyorsa):**
    - `@Entity`: Bu sınıfın bir veritabanı tablosuna karşılık geldiğini belirtir.
    - `@Table`: Tablonun adını ve diğer özelliklerini belirtir.
    - `@Id`: Bu alanın tablonun birincil anahtarı (primary key) olduğunu belirtir.
    - `@GeneratedValue`: Primary key'in nasıl üretileceğini belirtir (örn. otomatik artan).
    - `@Column`: Sütunun adını, uzunluğunu vb. özelliklerini belirtir.

**Örnek:**

Java

```java
import javax.persistence.*;

@Entity
@Table(name = "kullanicilar")
public class Kullanici {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ad_soyad")
    private String adSoyad;

    @Column(unique = true)
    private String email;

    private boolean aktif;

    // Getter, Setter, Constructor...
}
```

---

### `@Autowired` ve Enjeksiyon Olayı Nasıl Oluyor?

`@Autowired` anotasyonu, Spring'e "Bu alana veya metoda, container içindeki uygun tipte bir Bean'i otomatik olarak enjekte et" talimatını verir.

**Enjeksiyon Süreci Adım Adım:**

1. **Uygulama Başlar:** `@SpringBootApplication` ile uygulama başlatılır.
2. **Component Scan:** Spring, ana sınıfın bulunduğu paketi ve alt paketleri taramaya başlar. `@Component` ve onun türevleri olan `@Service`, `@Repository`, `@RestController` gibi anotasyonlara sahip sınıfları bulur.
3. **Bean Yaratma:** Spring, bulduğu her sınıf için bir nesne (instance) oluşturur ve bunları `ApplicationContext` (container) içine koyar. Bu aşamada henüz bağımlılıklar enjekte edilmemiştir.
4. **Enjeksiyon:** Spring, container'daki her Bean'i tek tek ele alır. `@Autowired` ile işaretlenmiş constructor, metot veya alanları bulur.
5. **Bağımlılık Çözümleme:** Örneğin `KullaniciController`'ın constructor'ında `@Autowired` gördüğünde, parametre olarak `KullaniciService` tipinde bir nesneye ihtiyaç duyulduğunu anlar.
6. **Bean'i Bul ve Ver:** Container içinde `KullaniciService` tipindeki Bean'i arar, bulur ve `KullaniciController`'ın constructor'ını bu Bean ile çağırarak `KullaniciController` nesnesinin son halini oluşturur.

### Enjeksiyon Türleri ve Neden Constructor Injection?

- **Field Injection (Alan Enjeksiyonu):** `@Autowired` doğrudan alanın üzerine konur. **Tavsiye edilmez!** Çünkü test yazmayı zorlaştırır ve nesnenin bağımlılıklarını gizler.Java
    
    ```java
    @Autowired
    private KullaniciService kullaniciService;
    ```
    
- **Setter Injection (Setter Metodu Enjeksiyonu):** Bağımlılıklar opsiyonel ise kullanılabilir ama nadiren ihtiyaç duyulur.Java
    
    ```java
    private KullaniciService kullaniciService;
    
    @Autowired
    public void setKullaniciService(KullaniciService kullaniciService) {
        this.kullaniciService = kullaniciService;
    }
    ```
    
- **Constructor Injection (Yapıcı Metot Enjeksiyonu):** **En çok tavsiye edilen yöntemdir!**
    - **Zorunlu Bağımlılıklar:** Nesne, hayata başlamak için ihtiyaç duyduğu bağımlılıklar olmadan yaratılamaz. Bu, kodun daha güvenli olmasını sağlar.
    - **Test Edilebilirlik:** Testlerde, Spring'e ihtiyaç duymadan, `new KullaniciController(new MockKullaniciService())` diyerek sahte (mock) nesnelerle kolayca test edilebilir.
    - **Final Alanlar:** Bağımlılıkları `final` olarak işaretleyebilirsin, bu da nesnenin değişmezliğini (immutability) artırır.

Java

```java
// BEST PRACTICE!
private final KullaniciService kullaniciService;

// Sınıfta tek bir constructor varsa Spring 4.3 ve sonrası için @Autowired yazmak zorunlu değildir.
// Spring otomatik olarak anlar. Ama okunabilirlik için yazmak iyi bir alışkanlıktır.
@Autowired
public KullaniciController(KullaniciService kullaniciService) {
    this.kullaniciService = kullaniciService;
}
```

### Bilmen Gereken Ekstra Bir Şey: DTO (Data Transfer Object)

Katmanlı mimaride sıkça kullanılan bir desen de DTO'dur. Servis katmanının, veritabanı varlığı olan `@Entity`'yi doğrudan Controller'a ve dış dünyaya açması genellikle istenmez. Çünkü:

- Entity, veritabanı yapısını ifşa eder.
- Gereksiz veya hassas bilgileri (örn. şifre) dışarı sızdırabilirsin.
- API'nin ihtiyaç duyduğu veri yapısı ile veritabanı yapısı farklı olabilir.

**Çözüm:** Sadece sunum katmanının ihtiyaç duyduğu alanları içeren basit sınıflar (DTO'lar) oluşturulur. Servis katmanı, Entity'yi DTO'ya dönüştürerek Controller'a verir.

---