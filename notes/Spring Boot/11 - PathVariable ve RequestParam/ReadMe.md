# PathVariable ve RequestParam

### @PathVariable ve @RequestParam

Bir Spring Boot uygulamasında dış dünyadan (istemciden) veri almanın en yaygın yollarından ikisi `@PathVariable` ve `@RequestParam`'dir. Her ikisi de URL üzerinden gelen veriyi okumak için kullanılsa da, çalıştıkları yerler ve amaçları tamamen farklıdır.

---

### 1. `@PathVariable`: Yolun Bir Parçası Olan Değişken

`@PathVariable`, adından da anlaşılacağı gibi, URL **yolunun (path)** kendisinden bir değişken ayıklamak için kullanılır. URL'deki dinamik bir bölümü, metot parametresi olarak almamızı sağlar.

**Temel Mantığı:** Bir URL şablonu içindeki "placeholder" (yer tutucu) değeri yakalamaktır.

**Analoji:** Bir apartmanın adresi gibidir. Adres, kaynağa ulaşmak için izlenmesi gereken yolu tanımlar ve daire numarası o yolun vazgeçilmez bir parçasıdır.

- **Adres:** `Cumhuriyet Mahallesi / Atatürk Caddesi / No: 25 / Daire: **12**`
- **URL:** `/mahalleler/cumhuriyet/caddeler/ataturk/binalar/25/daireler/**12**`
- İşte buradaki **12** numarası, o daireye özeldir ve adresten ayrılamaz. Bu bir `@PathVariable`'dır.

### Ne Zaman Kullanılır?

Genellikle tek bir kaynağı **benzersiz olarak tanımlamak** veya hedeflemek için kullanılır. Genellikle CRUD (Create, Read, Update, Delete) işlemlerinin R, U ve D'si için vazgeçilmezdir.

- Bir görevi ID'sine göre getirmek: `GET /api/tasks/{id}`
- Bir kullanıcıyı kullanıcı adına göre bulmak: `GET /users/{username}`
- Bir ürünü ID'si ile güncellemek: `PUT /products/{productId}`
- Bir siparişi numarasıyla silmek: `DELETE /orders/{orderNumber}`

Bu örneklerde `{id}`, `{username}`, `{productId}` gibi süslü parantez içindeki kısımlar, `@PathVariable` ile yakalanan dinamik yer tutuculardır. Bu değer URL'de olmazsa, Spring o adresi tanıyamaz ve 404 Hatası verir.

### Kod Örneği:

Java

```java
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // URL'deki {taskId} değeri, metot içindeki "Long taskId" parametresine atanacak.
    @GetMapping("/{taskId}")
    public Task getTaskById(@PathVariable Long taskId) {
        return taskService.getTaskById(taskId);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }
}
```

---

### 2. `@RequestParam`: URL'in Sonundaki Sorgu Parametresi

`@RequestParam`, URL'in yolundan sonra `?` işareti ile başlayan **sorgu dizesinden (query string)** veri ayıklamak için kullanılır. Bu parametreler genellikle ana kaynağı **filtrelemek, sıralamak veya sayfalamak** gibi ek seçenekler sunmak için kullanılır.

**Temel Mantığı:** `ana-adres?anahtar1=deger1&anahtar2=deger2` formatındaki anahtar-değer çiftlerini yakalamaktır.

**Analoji:** Bir e-ticaret sitesinde "ayakkabıları" listelemek gibidir.

- **Ana Kaynak:** `site.com/ayakkabilar` (Tüm ayakkabıları listeler)
- **Filtreleme:** `site.com/ayakkabilar?renk=kirmizi&beden=42` (Sadece 42 numara kırmızı ayakkabıları listeler)
- **Sıralama:** `site.com/ayakkabilar?sirala=fiyata-gore-artan`
- Buradaki `renk`, `beden` ve `sirala` birer `@RequestParam`'dir. Bunlar, ana listeyi görüntüleme şeklinizi değiştiren **opsiyonel** bilgilerdir.

### Ne Zaman Kullanılır?

Bir kaynak listesini daraltmak veya özelleştirmek gerektiğinde kullanılır.

- Görevleri durumuna göre filtrelemek: `GET /api/tasks?status=completed`
- Ürünleri isme göre aratmak: `GET /products/search?name=laptop`
- Sonuçları sayfalama: `GET /articles?page=2&size=10` (2. sayfa, 10'ar adet)

`@RequestParam`'in `required` ve `defaultValue` gibi kullanışlı özellikleri vardır.

- `@RequestParam(required = false)`: Parametrenin gönderilmesini isteğe bağlı yapar.
- `@RequestParam(defaultValue = "1")`: Parametre gönderilmezse varsayılan bir değer atar.

### Kod Örneği:

Java

```java
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    // ...

    // status parametresi zorunlu, sortBy ise isteğe bağlı ve varsayılan değeri "id"
    @GetMapping("/search")
    public List<Task> searchTasks(
            @RequestParam String status,
            @RequestParam(required = false, defaultValue = "id") String sortBy
    ) {
        // ... gelen 'status' ve 'sortBy' değerlerine göre arama yapacak servis metodu çağrılır
        return taskService.searchByStatus(status, sortBy);
    }
}
```

Bu metoda `GET /api/tasks/search?status=completed&sortBy=title` isteği atılabilir.

---

### 3. Temel Farklar: Karşılaştırma Tablosu

| Özellik | `@PathVariable` | `@RequestParam` |
| --- | --- | --- |
| **Amacı** | Tek bir kaynağı **tanımlamak** (identifying). | Bir kaynak listesini **filtrelemek/özelleştirmek**. |
| **URL'deki Yeri** | Yolun bir parçasıdır: `/tasks/**1**` | `?` işaretinden sonradır: `/tasks**?status=done**` |
| **Zorunluluk** | Genellikle **zorunludur**. Olmazsa URL geçersiz olur. | Genellikle **isteğe bağlıdır**. (`required=false` ile) |
| **Tipik Kullanım** | `GET (tekil)`, `PUT`, `DELETE` işlemleri | `GET (çoğul)`, Arama, Filtreleme, Sıralama, Sayfalama |
| **Analoji** | Daire Numarası | Filtreleme Seçeneği (Örn: "Kırmızı Renk") |

### Özet

Özetle, bir kaynağın **adresi** ile ilgili bir bilgiye ihtiyacın varsa (`/tasks/5` gibi, 5 numaralı görevin kendisine ulaşmak için) **`@PathVariable`** kullanırsın.

O kaynağın bir listesini **özelleştirmek veya filtrelemek** için ek bilgilere ihtiyacın varsa (`/tasks?status=completed` gibi, tamamlanmış görevlerin bir listesini almak için) **`@RequestParam`** kullanırsın.

---