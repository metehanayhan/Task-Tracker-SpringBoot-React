# React Nedir?

### **React'e Giriş ve Temel Kavramlar**

Bu derste React'in ne olduğunu, neden bu kadar popüler olduğunu ve en temel yapı taşları olan Component, Props ve State'in ne anlama geldiğini öğreneceğiz.

### **React Nedir?**

En basit tanımıyla React, **kullanıcı arayüzleri (User Interfaces - UI)** oluşturmak için kullanılan bir **JavaScript kütüphanesidir**.

- **Kütüphane mi, Framework mü?** Bu önemli bir ayrım. React bir *framework* (örn: Angular) gibi bütün kuralları kendi koyan katı bir yapı değildir. O, size sadece kullanıcı arayüzü inşa etmeniz için güçlü araçlar sunan bir *kütüphanedir*. Bu sayede projenizin diğer kısımlarında (veri yönetimi, yönlendirme vs.) istediğiniz başka kütüphaneleri kullanma esnekliğiniz olur.
- **Kim Geliştirdi?** Facebook (şimdiki adıyla Meta) tarafından geliştirildi ve halen aktif olarak destekleniyor. Instagram, WhatsApp Web, Facebook gibi devasa uygulamaların arayüzleri React ile yazılmıştır.
- **Temel Felsefesi Nedir?** React'in ana fikri, karmaşık kullanıcı arayüzlerini **"bileşen (component)"** adını verdiği küçük, yeniden kullanılabilir ve kendi içinde mantığı olan parçalara ayırmaktır. Bir web sayfasını kocaman bir lego seti gibi düşünün. Sayfadaki her bir düğme, her bir arama çubuğu, her bir profil kartı ayrı bir lego parçasıdır (yani component'tir).

### **React'in Temel Kavramları: Bir LEGO Analojisi**

React'in kalbini anlamak için basit bir LEGO analojisi kullanalım. Hayalimizde bir LEGO arabası yapıyoruz.

### **1. Component (Bileşen)**

- **Analoji:** LEGO setindeki **her bir parça** bir component'tir. Tekerlek, direksiyon, kapı, koltuk... Bunların hepsi kendi başına birer parçadır. Tekerlek parçasını alıp sadece bu arabada değil, bir uçakta veya bir trende de kullanabilirsiniz. Yani **yeniden kullanılabilirdir**.
- **React Dünyası:** React'te de bir component, kullanıcı arayüzünün (UI) bağımsız ve yeniden kullanılabilir bir parçasıdır. Örneğin, bir web sitesindeki "Giriş Yap" butonu bir component olabilir. Bu butonu hem ana sayfada, hem de profil sayfasında hiçbir değişiklik yapmadan kullanabilirsiniz. Her component kendi HTML, CSS ve JavaScript mantığını içinde barındırır.
    
    *Örnek Component'ler: `Button`, `SearchBar`, `UserProfileCard`, `Header`, `Footer`*
    

### **2. Props (Properties - Özellikler)**

- **Analoji:** Elimizde 4 tane aynı **tekerlek parçası (component)** var. Ama bu tekerleklerden ikisini "ön tekerlek", ikisini "arka tekerlek" olarak kullanmamız gerekiyor. Ya da diyelim ki farklı renklerde LEGO parçalarımız var. Parçanın şekli aynı, ama birine "sen kırmızı ol", diğerine "sen mavi ol" diyoruz. İşte bu "ön/arka olma" veya "kırmızı/mavi olma" bilgisi **props**'tur. Parçanın kendisini değiştirmeyiz, sadece ona dışarıdan özellikler veririz.
- **React Dünyası:** Props, bir üst component'ten (parent) bir alt component'e (child) veri aktarma yöntemidir. Props, component'in davranışını veya görünüşünü özelleştirmek için kullanılır ve **tek yönlüdür** (sadece yukarıdan aşağıya doğru akar). Alt component, kendisine gelen props'u **değiştiremez**, sadece okuyabilir.
    
    Örnek: Bir `Button` component'imiz olsun.
    

```java
// Bu butona "text" ve "color" adında iki props gönderiyoruz.
<Button text="Kaydet" color="blue" />
<Button text="İptal Et" color="red" />
```

Burada `Button` component'i aynı kod olmasına rağmen, ona gönderdiğimiz `text` ve `color` props'ları sayesinde farklı görünüp farklı metinler yazar.

### **3. State (Durum)**

- **Analoji:** LEGO arabamıza açılıp kapanabilen bir kapı taktığımızı düşünelim. Kapının "açık" veya "kapalı" olması, arabanın o anki **kendi iç durumudur (state)**. Dışarıdan kimse kapıya sürekli "sen açık ol", "sen kapalı ol" demez. Kapının kendisi, birisi ona dokunduğunda (bir olay gerçekleştiğinde) durumunu "açık"tan "kapalı"ya veya tam tersine çevirme yeteneğine sahiptir. Bu durum değişikliği arabanın o anki görünümünü etkiler.
- **React Dünyası:** State, bir component'in **kendi içinde tuttuğu ve zamanla değişebilen** veridir. Genellikle kullanıcı etkileşimleri (butona tıklama, forma yazı yazma vb.) sonucunda state değişir. Bir component'in state'i her değiştiğinde, React o component'i otomatik olarak yeniden render eder (ekranda günceller) ve böylece arayüz her zaman en güncel veriyi gösterir.
    
    Örnek: Bir sayaç düşünün.
    
    - Başlangıçta sayacın değeri (state'i) 0'dır. Ekranda "0" yazar.
    - Kullanıcı "Arttır" butonuna tıklar.
    - Buton, component'in state'ini güncelleyerek değeri 1 yapar.
    - React, state'in değiştiğini fark eder ve component'i yeniden render ederek ekranda "1" yazmasını sağlar.

**Özetle:**

- **Component:** Arayüzü oluşturan yeniden kullanılabilir yapı taşı (LEGO parçası).
- **Props:** Bir component'i özelleştirmek için dışarıdan (üst component'ten) gelen veri (LEGO parçasının rengi veya görevi).
- **State:** Bir component'in kendi içinde tuttuğu ve değiştirebildiği, zamanla değişen veri (LEGO kapısının açık/kapalı olması).

---

### **React İçin Bilinmesi Gereken JavaScript Özellikleri**

React modern JavaScript özelliklerini sıkça kullanır. React'e başlamadan önce aşağıdaki JS konularına aşina olman işini çok kolaylaştıracaktır:

1. **`let` ve `const`:** Değişken tanımlamada `var` yerine artık bu ikisi kullanılır. `const` ile tanımlanan bir değişkenin değeri sonradan değiştirilemez.
2. **Arrow Functions (Ok Fonksiyonları):** `function () {}` yerine `() => {}` şeklinde daha kısa ve pratik bir fonksiyon yazım şeklidir. React'te çok yaygındır.
3. **Array Metotları (`.map()`, `.filter()`, `.reduce()`):** Özellikle `.map()`, bir veri dizisindeki her bir eleman için bir component oluşturmak (örneğin bir ürün listesi basmak) için kullanılır. For döngüsü yerine neredeyse her zaman `.map()` kullanılır.
4. **Destructuring (Yapı Bozma):** Bir obje veya diziden değerleri kolayca ayrı değişkenlere atamanı sağlar. Props'ları alırken çok kullanılır.
`const { name, age } = user;` // `const name = user.name;` yerine.
5. **Modules (`import` / `export`):** Component'leri farklı dosyalarda tutup ihtiyacın olan yerde `import` ederek kullanmanı sağlar. Proje yapısının temelidir.
6. **Ternary Operator (`koşul ? doğruysa : yanlışsa`):** JSX içinde `if-else` yazamadığımız için koşullu render etme (örneğin, "kullanıcı giriş yapmışsa profil resmini göster, yapmamışsa giriş yap butonunu göster") için sıkça kullanılır.

---