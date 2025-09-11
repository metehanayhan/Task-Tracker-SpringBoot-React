# React Proje Yapısı

### **React Proje Yapısını Anlamak**

Vite ile oluşturulan bir React projesi, standart ve modern bir dosya yapısıyla gelir. Bu yapıdaki her elemanın belirli bir görevi vardır.

### **Ana Klasörler**

### **1. `src` Klasörü (Source - Kaynak)**

Bu klasör, projenin kalbidir. Geliştirme sürecinin **%99'u bu klasörün içinde** geçecektir. React component'leri, stiller (CSS), resimler ve uygulamanın mantığına dair her şey burada yer alır.

- `main.jsx`: React uygulamasının **giriş noktasıdır**. Uygulamanın çalışmasını başlatan dosyadır. İçindeki `ReactDOM.createRoot().render()` fonksiyonu, yazdığımız tüm React kodunu (`<App />` component'ini) alıp `index.html` dosyasındaki `<div id="root"></div>` etiketinin içine yerleştirir. Genellikle bu dosyayı başlangıçta değiştirmenize gerek kalmaz.
- `App.jsx`: Ana **kök (root) component'tir**. Uygulamanın kendisini temsil eden ilk component budur. Diğer tüm component'ler genellikle bu `App.jsx`'in içinden çağrılır. Ekranda görünen ilk içeriği bu dosya belirler.
- `App.css`: `App.jsx` component'ine özel CSS stillerini barındıran dosyadır.
- `index.css`: Projenin tamamını etkileyen **global CSS** kurallarının yazıldığı yerdir. Örneğin `body` etiketi için arka plan rengi, varsayılan yazı tipi gibi genel stiller burada tanımlanır.
- `assets/`: Component'ler içinde `import` edilerek kullanılacak resimler, ikonlar gibi statik varlıkların tutulduğu klasördür.

### **2. `public` Klasörü**

Bu klasör, projenin yapılandırma (build) sürecinden geçmeden, doğrudan erişilebilen dosyaları barındırır. Tarayıcı bu dosyalara doğrudan `siteadi.com/dosya-adi` şeklinde erişebilir.

- `vite.svg`: Örnek bir resim dosyası.
- Genellikle `favicon.ico` (tarayıcı sekmesinde görünen ikon), `robots.txt` (arama motoru botları için kurallar) gibi dosyalar burada tutulur.

### **3. `node_modules` Klasörü**

`npm install` komutu çalıştırıldığında oluşturulur. Projenin çalışması için gereken tüm dış kütüphaneleri (React, Vite, ve diğer tüm bağımlılıkları) içerir.

- **ÖNEM:** Bu klasöre **asla manuel olarak dokunulmaz, içindeki dosyalar silinmez veya değiştirilmez.** Binlerce dosya içerir ve projenin beyni gibidir. Eğer bir sorun olursa bu klasörü silip tekrar `npm install` komutunu çalıştırmak, tüm paketlerin temiz bir şekilde yeniden yüklenmesini sağlar.

---

### **Kök Dizinindeki Dosyalar (Root Files)**

- `index.html`: **Tek Sayfa Uygulamalarının (Single Page Application - SPA) temelidir.** Tarayıcının açtığı tek HTML dosyası budur. İçi neredeyse boştur, en önemli kısmı React uygulamasının içine monte edileceği `<div id="root"></div>` etiketidir. Bizim yazdığımız tüm React kodu, `main.jsx` aracılığıyla bu `div`'in içine enjekte edilir.
- `package.json`: Projenin **kimlik kartıdır**. Projenin adını, versiyonunu ve en önemlisi çalışması için hangi paketlere ihtiyaç duyduğunu (`dependencies`) listeler. Ayrıca `npm run dev` gibi terminal komutlarını (`scripts`) tanımlar.
- `package-lock.json`: `package.json`'daki paketlerin **tam olarak hangi versiyonlarının** yüklendiğini kaydeder. Bu, projenin farklı bilgisayarlarda veya farklı zamanlarda her zaman aynı paket versiyonlarıyla kurulmasını garanti eder. **Bu dosya da manuel olarak düzenlenmemelidir.**
- `vite.config.js`: Vite'ın yapılandırma dosyasıdır. Projeye eklentiler (plugin) eklemek veya Vite'ın davranışını özelleştirmek için kullanılır. Başlangıçta bu dosyayı değiştirmenize gerek yoktur.
- `.gitignore`: Git versiyon kontrol sistemine hangi dosyaları ve klasörleri **görmezden gelmesi gerektiğini** söyler. Örneğin `node_modules` klasörü, boyutu çok büyük olduğu için asla versiyon kontrolüne dahil edilmez ve bu dosyada listelenir.
- `README.md`: Proje hakkında bilgi içeren standart bir Markdown dosyasıdır.

---

### **Özet ve Pratik Bilgiler**

### **Kodları Nereye Yazacağım?**

Tüm kodlarını **`src`** klasörünün içine yazacaksın. Yeni component'ler için yeni `.jsx` dosyaları oluşturacak, stiller için `.css` dosyaları kullanacak ve bunları `App.jsx` içinden çağıracaksın.

### **Hangilerine ASLA Dokunmamalıyım?**

- **`node_modules/`**
- **`package-lock.json`**

Bu iki yapıya manuel müdahale etmek projenin bozulmasına neden olabilir.

### **Hangilerini Güvenle Silebilir veya Temizleyebilirim?**

Projeye sıfırdan başlamak için "temizlik" yapmak istersen aşağıdaki dosyaları silebilir veya içeriklerini boşaltabilirsin:

- `public` klasörü içindeki `vite.svg` dosyasını silebilirsin.
- `src/assets` klasörünü tamamen silebilirsin.
- `src/App.css` dosyasının içeriğini tamamen temizleyebilirsin.
- `src/index.css` dosyasının içeriğini tamamen temizleyebilirsin.
- `src/App.jsx` dosyasının içeriğini temizleyip, kendi "Merhaba Dünya" kodunu yazmak için hazır hale getirebilirsin.

---