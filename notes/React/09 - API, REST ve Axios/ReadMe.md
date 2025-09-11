# API, REST ve Axios

### **Full-Stack Geliştirmeye Giriş: API, REST ve Axios**

### **1. Büyük Resim: Restoran Analojisi**

Full-Stack bir uygulamanın çalışma mantığını anlamanın en kolay yolu bir restoran analojisi kullanmaktır:

- **Siz (Müşteri) = Frontend (React Uygulaması):**
Restorana gelirsiniz ve ne yemek istediğinizi bilirsiniz. Önünüzde bir menü vardır. Ancak mutfağa gidip yemeğinizi kendiniz alamazsınız. İsteğinizi bir aracıya iletmeniz gerekir.
- **Mutfak = Backend (Spring Boot Sunucusu ve Veritabanı):**
Burası sihrin gerçekleştiği yerdir. Tüm malzemeler (veriler) buradadır, aşçılar (iş mantığı/business logic) yemeği hazırlar. Mutfak son derece karmaşık olabilir, ancak müşteri olarak sizin bu karmaşayı bilmenize gerek yoktur.
- **Garson = API (Application Programming Interface):**
Garson, sizinle mutfak arasındaki **iletişim katmanıdır**.
    1. Sizden siparişinizi alır (React'ten gelen **HTTP İsteği**).
    2. Bu siparişi mutfağın anlayacağı bir dilde mutfağa iletir.
    3. Mutfak yemeği hazırladığında, onu alır ve size servis eder (Spring Boot'tan gelen **HTTP Cevabı**).

Garson, sizin mutfağın nasıl çalıştığını bilmenize gerek kalmadan, mutfağın da sizin kim olduğunuzu bilmesine gerek kalmadan bu iletişimi sağlayan bir **sözleşme (contract)** ve **kurallar bütünüdür**. İşte **API** tam olarak budur.

### **2. Teknik Kavramlar: API, REST ve JSON**

**API (Application Programming Interface - Uygulama Programlama Arayüzü):**
Farklı yazılım uygulamalarının birbiriyle konuşmasını sağlayan kurallar ve protokoller bütünüdür. Bizim örneğimizde API, React (frontend) ve Spring Boot (backend) uygulamalarının nasıl iletişim kuracağını tanımlayan "garsonun menüsü ve sipariş alma kurallarıdır".

**REST (Representational State Transfer) ve RESTful API:**
REST, bir teknoloji veya dil değil, bu API'leri tasarlamak için kullanılan bir **mimari tarzdır**. Web servisleri oluşturmak için en popüler yaklaşımdır. RESTful bir API, belirli kurallara uyar:

- **Kaynaklar (Resources):** API'deki her şey bir kaynaktır. "Kullanıcılar", "ürünler", "makaleler" birer kaynaktır.
- **Endpoint'ler (Uç Noktalar):** Bu kaynaklara erişmek için kullanılan URL'lerdir.
    - `/api/users` -> Tüm kullanıcıları temsil eden endpoint.
    - `/api/users/123` -> ID'si 123 olan tek bir kullanıcıyı temsil eden endpoint.
- **Standart Metotlar (HTTP Verbs):** Kaynaklar üzerinde işlem yapmak için standart HTTP metotları kullanılır. En temel olanlar şunlardır:
    - **GET:** Veri **okumak/almak** için. (`/api/users` -> Tüm kullanıcıları getir.)
    - **POST:** Yeni bir veri **oluşturmak/eklemek** için. (`/api/users` -> Yeni bir kullanıcı ekle.)
    - **PUT / PATCH:** Mevcut bir veriyi **güncellemek** için. (`/api/users/123` -> 123 ID'li kullanıcıyı güncelle.)
    - **DELETE:** Bir veriyi **silmek** için. (`/api/users/123` -> 123 ID'li kullanıcıyı sil.)
    Bu operasyonlar **CRUD (Create, Read, Update, Delete)** operasyonlarına denk gelir.

**JSON (JavaScript Object Notation):**
Frontend ve backend birbirleriyle konuşurken veriyi hangi formatta alıp verecekler? En popüler format **JSON**'dur. JSON, hem insanlar tarafından kolayca okunabilen hem de makineler (özellikle JavaScript) tarafından kolayca ayrıştırılabilen (parse edilebilen) metin tabanlı bir formattır.

Örnek bir kullanıcı objesinin JSON formatı:

JSON

```java
{
  "id": 123,
  "username": "metehan",
  "email": "metehan@example.com",
  "isActive": true
}
```

### **3. Oyuncuların Rolleri: React, Spring Boot ve Axios**

**React'in Rolü (Frontend - Müşteri):**
React uygulamasının görevi, kullanıcıya bir arayüz sunmak ve sunucudan veri istemektir.

- Bir sayfaya girildiğinde (`useEffect` içinde) veya bir butona tıklandığında (event handler içinde) backend'e bir HTTP isteği gönderir.
- Bu istekleri yapmak için tarayıcının yerleşik `fetch` API'sini veya **Axios** gibi daha yetenekli kütüphaneleri kullanır.
- **Axios**, `fetch`'e göre daha kolay hata yönetimi, otomatik JSON dönüşümü gibi avantajlar sunduğu için çok popülerdir. **React'te API istekleri için genellikle Axios'u kullanacağız.**
- Backend'den JSON formatında gelen cevabı alır, bunu `useState` ile bir state'e atar ve ekranda gösterir.

**Spring Boot'un Rolü (Backend - Mutfak):**
Spring Boot sunucusunun görevi, gelen isteklere cevap vermektir.

- `/api/users` gibi endpoint'leri tanımlar (@RestController, @GetMapping, @PostMapping vb. anotasyonlarla).
- Bir `GET /api/users` isteği geldiğinde, veritabanına bağlanır, tüm kullanıcıları çeker.
- Veritabanından gelen bu veriyi **JSON formatına** dönüştürür.
- Bu JSON verisini bir HTTP Cevabı (Response) içinde React'e geri gönderir.

### **4. Full-Stack Akışı: Bir İsteğin Yaşam Döngüsü**

1. **React (Kullanıcı):** Kullanıcı "Kullanıcıları Listele" sayfasına girer.
2. **React (`useEffect`):** Component ilk render olduğunda, `useEffect` içindeki `axios.get('http://localhost:8080/api/users')` kodu çalışır.
3. **Ağ (Garson Siparişi Alır):** Tarayıcı, backend sunucusunun adresi olan `http://localhost:8080/api/users`'a bir GET isteği gönderir.
4. **Spring Boot (Garson Siparişi Mutfağa İletir):** Spring Boot sunucusu bu isteği alır. `/api/users` endpoint'ini dinleyen `@GetMapping`'li metodu bulur ve çalıştırır.
5. **Spring Boot (Mutfak Yemeği Hazırlar):** Bu metot, veritabanına gider, tüm kullanıcıları alır.
6. **Spring Boot (Garson Yemeği Servise Hazırlar):** Kullanıcı listesini JSON'a çevirir ve bir HTTP cevabı olarak paketler (örn: `200 OK` status kodu ile).
7. **Ağ (Garson Yemeği Getirir):** Bu cevap ağ üzerinden tarayıcıya geri döner.
8. **React (Axios):** Axios bu cevabı yakalar. `.then(response => ...)` bloğu çalışır. `response.data` içinde artık kullanıcıların listesi vardır.
9. **React (`useState`):** `setUsers(response.data)` fonksiyonu çağrılır ve kullanıcılar listesi component'in state'ine kaydedilir.
10. **React (Ekran Güncellenir):** State değiştiği için component yeniden render olur. `users.map(...)` ile kullanıcı listesi ekrana basılır.

İşte hepsi bu kadar! Farklı makinelerde çalışan iki ayrı uygulama (React ve Spring Boot), birbirleriyle evrensel bir dil olan **HTTP** ve ortak bir format olan **JSON** üzerinden, **API** adı verilen bir kurallar bütünüyle konuşarak tam bir uygulama oluşturur.

---