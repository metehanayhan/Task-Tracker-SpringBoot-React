# Component

### **React'in Yapı Taşı: Component'ler**

React ile geliştirdiğimiz kullanıcı arayüzleri, aslında bir araya getirilmiş yüzlerce küçük ve bağımsız parçadan oluşur. İşte bu parçaların her birine **Component (Bileşen)** denir.

### **1. Component Nedir?**

Bir Component, kullanıcı arayüzünün (UI) **bağımsız, yeniden kullanılabilir** bir parçasıdır. Kendi HTML yapısını (JSX ile), CSS stillerini ve JavaScript mantığını içinde barındırır.

Daha önceki LEGO analojimizi hatırlayalım: Eğer web sayfamız bir LEGO şatosu ise; şatonun kulesi, kapısı, penceresi, bayrağı gibi her bir parça ayrı bir component'tir. Bir "pencere" component'i yapıp, bunu şatonun her yerinde defalarca kullanabiliriz.

**Component kullanmanın temel avantajları:**

- **Yeniden Kullanılabilirlik (Reusability):** Bir `Button` veya `UserProfileCard` component'i yazıp, projenin farklı yerlerinde tekrar tekrar kullanabilirsiniz.
- **İzolasyon (Isolation):** Her component kendi dünyasında yaşar. Bir component'te yaptığınız değişiklik, diğer component'leri (doğrudan ilişkili değilse) etkilemez. Bu, hata ayıklamayı (debugging) ve kodu yönetmeyi inanılmaz kolaylaştırır.
- **Birleştirilebilirlik (Composability):** Küçük ve basit component'ler oluşturup, bunları bir araya getirerek büyük ve karmaşık component'ler veya sayfalar inşa edebilirsiniz.

### **2. Component Nasıl Oluşturulur ve Kullanılır?**

Modern React'te component oluşturmanın en yaygın ve tavsiye edilen yolu **Fonksiyonel Component'lerdir (Functional Components)**.

**Fonksiyonel Component:**
Bu, JSX döndüren basit bir JavaScript fonksiyonudur.

**Örnek: Bir `Header.jsx` dosyası oluşturalım.**

JavaScript

```java
// src/Header.jsx

// Bu basit bir JavaScript fonksiyonudur.
// Dışarıdan "props" alabilir ve geriye JSX döndürür.
function Header() {
  return (
    <header className="main-header">
      <h1>Benim React Uygulamam</h1>
      <nav>
        <a href="#">Ana Sayfa</a>
        <a href="#">Hakkında</a>
        <a href="#">İletişim</a>
      </nav>
    </header>
  );
}
```

Bu component henüz ekranda görünmez. Onu kullanmak için başka bir component'in içine, tıpkı bir HTML etiketi gibi, **çağırmamız (import edip render etmemiz)** gerekir.

**Örnek: `App.jsx` içinde `Header` component'ini kullanalım.**

JavaScript

```java
// src/App.jsx

// Header component'ini kullanabilmek için önce onu import etmeliyiz.
import Header from './Header'; 

function App() {
  return (
    <div>
      {/* İşte burada Header component'ini çağırıyoruz */}
      <Header /> 
      
      <main>
        <p>Uygulamanın ana içeriği buraya gelecek.</p>
      </main>
    </div>
  );
}

export default App;
```

Bu kadar! Artık `Header` component'i, `App` component'inin bir parçası olarak ekranda görünecektir.

### **3. Component'leri Dışa Aktarma: `export` ve `export default`**

Bir component'in başka dosyalarda kullanılabilmesi (`import` edilebilmesi) için, oluşturulduğu dosyadan **dışa aktarılması (export edilmesi)** gerekir. JavaScript modül sisteminde bunun iki ana yolu vardır: `export default` ve `export` (named export).

### **`export default` (Varsayılan Olarak Dışa Aktarma)**

- **Kural:** Bir dosyada yalnızca **bir tane** `export default` olabilir.
- **Amaç:** O dosyanın "ana" veya "varsayılan" olarak dışa aktardığı şeyi belirtir. Genellikle bir dosyanın asıl amacı olan tek bir component için kullanılır.
- **Kullanım:**
    
    ```java
    // src/UserProfile.jsx
    
    function UserProfile() {
      // ... component içeriği
    }
    
    export default UserProfile; // Fonksiyonu varsayılan olarak dışa aktar.
    ```
    
- **İçe Aktarma (`import`):** `export default` ile dışa aktarılan bir modülü `import` ederken, ona **istediğiniz herhangi bir ismi** verebilirsiniz. Çünkü o dosyanın varsayılanı bellidir.
    
    ```java
    // App.jsx
    
    import ProfileOfUser from './UserProfile'; // UserProfile yerine başka bir isim de verebilirdik.
    import HerhangiBirIsim from './UserProfile'; // Bu da geçerlidir.
    ```
    

### **`export` (İsimli Olarak Dışa Aktarma - Named Export)**

- **Kural:** Bir dosyada **birden fazla** `export` olabilir.
- **Amaç:** Bir dosyadan birden çok fonksiyon, değişken veya component'i dışa aktarmak için kullanılır. Genellikle yardımcı fonksiyonlar (utility functions) veya küçük, ilişkili component'ler için tercih edilir.
- **Kullanım:**JavaScript
    
    ```java
    // src/components/Buttons.jsx
    
    export function PrimaryButton() {
      return <button className="btn-primary">Tıkla</button>;
    }
    
    export function SecondaryButton() {
      return <button className="btn-secondary">İptal</lebutton>;
    }
    
    export const version = "1.0";
    ```
    
- **İçe Aktarma (`import`):** İsimli olarak dışa aktarılan modülleri `import` ederken, **süslü parantezler `{}` kullanmak zorunludur** ve isimler **tam olarak eşleşmelidir**.
    
    ```java
    // App.jsx
    
    // İsimler tam olarak eşleşmeli ve {} içinde olmalı.
    import { PrimaryButton, SecondaryButton } from './components/Buttons';
    ```
    

### **Özet ve En İyi Pratikler: Ne Zaman Hangisini Kullanmalı?**

| Özellik | `export default` | `export` (Named Export) |
| --- | --- | --- |
| **Dosya Başına Sayı** | Sadece 1 tane | Sınırsız |
| **`import` Sözdizimi** | `import IstediginIsim from './Dosya';` | `import { TamOlarakAyniIsim } from './Dosya';` |
| **`import` Esnekliği** | İsim değiştirilebilir | İsim sabit olmalı (veya `as` ile takma ad verilmeli) |
| **Yaygın Kullanım** | Bir dosyanın ana component'i | Yardımcı fonksiyonlar, sabitler, birden çok küçük component |

**Genel Kural:**

- Eğer bir dosya sadece tek bir component içeriyorsa ve o dosyanın varoluş amacı o component ise, **`export default`** kullanın. (Örn: `UserProfile.jsx`, `Header.jsx`)
- Eğer bir dosyadan birden fazla şey (yardımcı fonksiyonlar, sabitler, ikonlar vb.) dışa aktarmak istiyorsanız, **`export`** kullanın. (Örn: `utils/formatters.js`)

---