# React Projesi Oluşturma

### **İlk React Projesini Oluşturma (Modern Yöntem: Vite)**

Bu derste, bir React projesini hayata geçirmek için gereken ilk adımı atacağız. Hangi araçları kullanacağımızı, nedenlerini ve adım adım proje oluşturma sürecini öğreneceğiz.

### **Proje Oluşturma Araçları: Vite mi, Create React App mi?**

Geçmişte React projeleri oluşturmak için Facebook'un resmi aracı olan **Create React App (CRA)** standart olarak kabul ediliyordu. Ancak teknoloji gelişti ve artık çok daha hızlı, modern alternatifler var.

**Vite (Fransızca'da "hızlı" anlamına gelir, "vit" diye okunur)**, günümüzde React topluluğunun ezici bir çoğunlukla tercih ettiği araçtır.

Peki neden Vite?

| Özellik | **Vite (Modern ve Önerilen)** | **Create React App (Eski Yöntem)** |
| --- | --- | --- |
| **Başlatma Hızı** | **Işık hızında.** Saniyeler içinde geliştirme sunucusu hazır olur. | **Yavaş.** Proje büyüdükçe başlatma süresi dakikaları bulabilir. |
| **Kod Değişikliği** | **Anlık.** Yaptığın bir değişiklik anında tarayıcıya yansır (Hot Module Replacement). | **Yavaş.** Her değişiklikte tüm paketi yeniden derlediği için gecikmeler yaşanır. |
| **Teknoloji** | Modern tarayıcı özelliklerini (Native ESM) kullanarak inanılmaz bir hız elde eder. | Arka planda Webpack kullanır, bu da onu daha hantal ve yavaş yapar. |
| **Esneklik** | Sadece React için değil, Vue ve Svelte gibi başka kütüphaneler için de kullanılır. | Sadece React için tasarlanmıştır. |

**Sonuç:** 2025 yılı itibarıyla yeni bir React projesine başlamak için **en doğru, en mantıklı ve en modern yöntem kesinlikle Vite'tır.** Sana hem geliştirme sürecinde zaman kazandırır hem de daha keyifli bir deneyim sunar.

---

### **Adım Adım Vite ile İlk React Projesini Oluşturma**

**1. Adım: Terminali Aç**

Projelerini saklamak istediğin bir klasöre git (Örneğin, Masaüstü veya `Belgeler\Projelerim` gibi). Bu klasörün içindeyken terminalini (Komut İstemi, PowerShell veya VS Code'un entegre terminali) aç.

**2. Adım: Sihirli Komutu Çalıştır**

Aşağıdaki komutu terminale yaz ve Enter'a bas. Bu komut, Vite'ın en son sürümünü kullanarak proje oluşturma sihirbazını başlatır.

Bash

```java
npm create vite@latest
```

**3. Adım: Projeyi Yapılandır**

Bu komutu çalıştırdıktan sonra Vite sana interaktif olarak bazı sorular soracak:

- **`Project name: …`**: Projenin adını istiyor. Küçük harflerle ve boşluk yerine tire () kullanarak bir isim verelim. Örneğin: `ilk-react-uygulamam` yazıp Enter'a bas.
- **`Select a framework:`**: Birçok seçenek göreceksin (Vue, React, Svelte vb.). Ok tuşlarını kullanarak **React**'i seç ve Enter'a bas.
- **`Select a variant:`**: Projeyi hangi dilde yazmak istediğini soruyor. Şimdilik öğrenme aşamasında olduğumuz için ok tuşlarıyla **JavaScript**'i seç ve Enter'a bas. (İleride TypeScript'i de öğrenebilirsin, o da harika bir seçenektir).

Bu adımları tamamladığında, Vite senin için belirlediğin isimde bir klasör oluşturacak ve temel React proje dosyalarını içine yerleştirecektir.

**4. Adım: Projeyi Başlat**

Kurulum bittiğinde, terminal ekranında Vite sana sırasıyla çalıştırman gereken komutları gösterecektir. Bunlar her zaman aynıdır:

i. **Proje klasörüne gir:**

Bash

```java
cd ilk-react-uygulamam
```

(Eğer farklı bir isim verdiysen `cd verdiğin-isim` şeklinde yazmalısın.)

ii. **Gerekli paketleri yükle:**

Bash

```java
npm install
```

Bu komut, projenin çalışması için gereken tüm kütüphaneleri (react, react-dom vb.) internetten indirip `node_modules` adında bir klasöre kurar. Bu işlem internet hızına bağlı olarak 1-2 dakika sürebilir.

iii. **Geliştirme sunucusunu başlat:**

Bash

```java
npm run dev
```

**5. Adım: Sonucu Gör!**

`npm run dev` komutunu çalıştırdığında terminalde şuna benzer bir çıktı göreceksin:

```java
  VITE v5.x.x  ready in xxx ms

  ➜  Local:   http://localhost:5173/
  ➜  Network: use --host to expose
  ➜  press h + enter to show help
```

İşte bu kadar! Artık `http://localhost:5173/` adresini kopyalayıp web tarayıcında açabilirsin. Karşında çalışan ilk React projen duruyor olacak!

---

### **Proje Klasörlerine Hızlı Bir Bakış**

Vite'ın oluşturduğu klasörde birçok dosya göreceksin. Şimdilik en önemlileri şunlar:

- **`node_modules/`**: `npm install` ile yüklenen tüm paketlerin bulunduğu klasör. Buraya asla dokunmuyoruz.
- **`src/`**: **Bizim kalemiz!** Bütün React kodlarımızı, component'lerimizi ve stillerimizi bu klasörün içine yazacağız.
    - `App.jsx`: Şu an ekranda gördüğün sayfanın ana component'i.
    - `main.jsx`: React uygulamasını başlatan, ana giriş noktası olan dosya.
- **`package.json`**: Projenin kimlik kartı. Projenin adını, versiyonunu ve hangi paketlere ihtiyaç duyduğunu (`dependencies`) belirten dosyadır.

Tebrikler! Şu anda en modern yöntemleri kullanarak bir React projesini başarıyla oluşturdun ve çalıştırdın.

---