# MVP Anolojisi

Düşün ki bir restoranın var:

- **Müşteri (Dış Dünya):** Senin API'nı kullanacak olan frontend (React) uygulamasıdır.
- **Garson (`Controller`):** Müşteriden siparişi alır (`GET`, `POST` isteği). Siparişin detaylarını mutfağa iletir. Yemeğin nasıl yapıldığını bilmez, sadece siparişi alır ve hazır olan yemeği müşteriye sunar. **Asla yemek yapmaz.**
- **Aşçıbaşı (`Service`):** Garsonun getirdiği siparişi (isteği) alır. Yemeği yapmak için gerekli mantığı, tarifi, sırları bilir. Hangi malzemelerin ne kadar kullanılacağına, ne kadar pişeceğine o karar verir. Bu, senin **iş mantığındır (business logic)**. Aşçıbaşı, "bana 2 domates, 1 soğan getir" diye çırağına seslenir. Kendi gidip kilerden domates almaz.
- **Kiler Sorumlusu/Çırak (`Repository`):** Aşçının istediği malzemeleri kilerden (veritabanından) alıp aşçıya getirmekle sorumludur. Malzemenin nereden geldiğini, nasıl yetiştirildiğini bilmez. Sadece "ver" denileni verir, "kaydet" denileni kaydeder. **Asla yemek tarifini bilmez.**
- **Malzemeler (`Entity`):** Domates, soğan, et... Bunlar senin ham verilerindir (`Task.java`).

Şimdi düşün, garson aynı zamanda hem aşçı hem de kiler sorumlusu olsaydı ne olurdu? Restoran kaosa dönerdi. İşte katmanlı mimari bu kaosu önler. Herkes kendi işini yapar. Bu, kodunu **anlaşılır, sürdürülebilir, test edilebilir ve düzenli** yapar.

---

### Nereden Başlayacağım?

Projeye veya yeni bir özelliğe başlarken her zaman izleyebileceğin mantıksal bir akış var.

### İdeal Geliştirme Akışı

Genellikle geliştirme, **veriden başlayıp kullanıcı arayüzüne doğru** ilerler. Yani restorandaki akış gibi: önce malzemelerin (`Entity`) olmalı, sonra o malzemeleri getirecek biri (`Repository`), sonra o malzemelerle yemek yapacak biri (`Service`), en son da o yemeğin siparişini alacak biri (`Controller`).

**Adım 1: Veriyi Tanımla (Entity - "Ne?" sorusu)**

- Her şeye "Ben neyin verisini tutacağım?" diye sorarak başla. Cevap: "Task".
- O zaman ilk işin `Task.java` **Entity** sınıfını oluşturmaktır. İçinde hangi alanlar olacak (`id`, `title` vs.)?

**Adım 2: Veriye Nasıl Erişeceğini Tanımla (Repository - "Nasıl?" sorusu)**

- `Task` verisini oluşturdun. Peki bunu veritabanından nasıl okuyup yazacaksın?
- İkinci işin hemen `TaskRepository.java` **Repository** interface'ini oluşturmaktır.

**Adım 3: Veriyle Ne Yapılacağını Tanımla (Service - "Hangi kurallarla?" sorusu)**

- Artık veritabanından `Task` alıp kaydedebiliyorsun. Peki bu veriyle ne yapacaksın?
- Üçüncü işin `TaskService.java` **Service** sınıfını oluşturmaktır. "Yeni task eklenirken `title` boş olamaz" gibi iş kuralları, mantıklar burada yaşar. Bu katman, Repository'den aldığı veriyi işler.

**Adım 4: Dış Dünyaya Aç (Controller - "Kim erişecek?" sorusu)**

- Artık görevleri listeleyen, ekleyen, silen bir servis mantığın var. Peki kullanıcı buna nasıl erişecek?
- Son işin `TaskController.java` **Controller** sınıfını oluşturmaktır. Gelen istekleri (`/api/tasks`) uygun servise yönlendiren kapıları (`@GetMapping`, `@PostMapping`) burada tanımlarsın.

**ÖZET AKIŞ: `Entity` → `Repository` → `Service` → `Controller`**

---