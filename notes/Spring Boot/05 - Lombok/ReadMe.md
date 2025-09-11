# Lombok Nedir?

# 📘 Lombok Nedir?

## 1) Tanım

- **Lombok**, Java projelerinde **tekrarlayan, sıkıcı kodları otomatik olarak oluşturan** bir kütüphanedir.
- “Annotation Processing” kullanır → yani sen derleme (compile) yaparken Lombok devreye girer ve gerekli kodları ekler.
- Özellikle **POJO sınıflarında (Plain Old Java Object)** faydalıdır.

---

## 2) Neden Lazım?

Java’da çok tekrar eden kod yazıyoruz:

- Getter / Setter
- Constructor (yapıcı metot)
- `toString()`, `equals()`, `hashCode()`
- Builder pattern
- Logger tanımları

👉 Bu kodları hep elle yazmak zorundayız. Lombok ise sadece anotasyon ekleyerek bunları **otomatik üretir.**

---

## 3) Lombok’un Sağladığı Avantajlar

- **Daha az kod, daha temiz sınıflar.**
- Kod tekrarını (boilerplate code) ortadan kaldırır.
- Okunabilirliği artırır.
- Hata yapma ihtimalini azaltır.
- Kod yazma süresini hızlandırır.

---

## 4) Kullanım Örneği

### Lombok olmadan:

```java
public class Student {
    private Long id;
    private String name;

    public Student() {}
    public Student(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name=" + name + "}";
    }
}

```

### Lombok ile:

```java
import lombok.Data;

@Data
public class Student {
    private Long id;
    private String name;
}

```

👉 `@Data` anotasyonu sayesinde:

- Getter & Setter
- `toString()`
- `equals()` & `hashCode()`
- `@RequiredArgsConstructor` (final alanlar için constructor)
    
    otomatik gelir.
    

---

## 5) En Çok Kullanılan Lombok Anotasyonları

- `@Getter` → sadece getter metodları üretir.
- `@Setter` → sadece setter metodları üretir.
- `@Data` → getter, setter, toString, equals, hashCode, constructor üretir.
- `@NoArgsConstructor` → parametresiz constructor üretir.
- `@AllArgsConstructor` → tüm alanları parametre alan constructor üretir.
- `@Builder` → Builder pattern desteği sağlar.
- `@Slf4j` → Logger otomatik ekler.

---

## 6) Nerelerde Kullanılır?

- **Entity sınıfları** (veritabanı tabloları)
- **DTO sınıfları** (data transfer objects)
- **Response / Request modelleri**
- **Service / Controller logger** tanımları

---

✅ **Özet:**

Lombok, Java’da kod tekrarını azaltan, sıkıcı metotları otomatik üreten bir kütüphanedir. Sen sadece anotasyon koyarsın, derleme sırasında Lombok bu metodları ekler.

---