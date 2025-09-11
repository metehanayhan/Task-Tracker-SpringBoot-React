# JSX Nedir?

### **JSX Nedir ve Neden Önemlidir?**

React'te component'lerin arayüzünü (UI) oluştururken, HTML'e çok benzeyen özel bir sözdizimi (syntax) kullanılır. Bu sözdizimine **JSX** adı verilir.

### **JSX Nedir?**

JSX, **JavaScript XML**'in kısaltmasıdır. Temelde, JavaScript kodunun içinde HTML benzeri yapılar yazmamıza olanak tanıyan bir sözdizimi uzantısıdır. JSX sayesinde, bir component'in hem görünümünü (markup) hem de o görünümün arkasındaki mantığı (logic) aynı dosyada, bir arada ve okunaklı bir şekilde tutabiliriz.

Önemli bir not: Tarayıcılar JSX kodunu doğrudan **anlayamazlar**. Yazdığımız JSX kodu, proje çalıştırılmadan veya derlenmeden önce **Babel** gibi bir araç tarafından standart JavaScript koduna çevrilir. JSX, aslında geliştiriciler için yazım kolaylığı sağlayan bir "sözdizimsel şekerdir" (syntactic sugar).

### **Neden JSX Kullanırız? Bir Örnekle Anlayalım**

JSX'in neden bu kadar güçlü olduğunu anlamanın en iyi yolu, onsuz bir React kodu yazmayı denemektir.

**JSX Olmadan:**
React'te bir `<h1>` etiketi oluşturmak için `React.createElement()` fonksiyonunu kullanmamız gerekir. Bu fonksiyon üç parametre alır: etiketin türü, özellikleri (props) ve içindeki çocuklar (children).

JavaScript

```java
// JSX olmadan bir başlık ve paragraf oluşturmak
React.createElement(
  "div",
  { className: "container" },
  React.createElement("h1", null, "Merhaba Dünya!"),
  React.createElement("p", null, "Bu, React'in temel halidir.")
);
```

**JSX İle:**
Şimdi aynı yapıyı JSX ile oluşturalım:

Gördüğünüz gibi, iç içe geçmiş birkaç element oluşturmak bile hızla karmaşık, okunması zor ve yazması zahmetli bir hale geliyor.

JavaScript

```java
// JSX ile aynı yapıyı oluşturmak
<div className="container">
  <h1>Merhaba Dünya!</h1><p>Bu, React'in temel halidir.</p>
</div>
```

Sonuç ortada. JSX, HTML yazar gibi UI oluşturmamızı sağlar. Bu da kodu çok daha **okunaklı, sezgisel ve yönetilebilir** kılar.

---

### **JSX'in Temel Kuralları**

JSX, HTML'e benzese de kendine ait katı kuralları vardır. Bu kurallara uymak zorunludur.

**1. Bütün Etiketler Kapatılmalıdır**
HTML'de `<br>` veya `<img>` gibi etiketleri kapatmadan kullanmak mümkündür. JSX'te ise içeriği olmayan etiketler de dahil olmak üzere **tüm etiketler** kapatılmalıdır.

JavaScript

```java
// Yanlış
<img src="/profil.jpg">
<br>

// Doğru
<img src="/profil.jpg" /><br />
```

**2. Tek Bir Kök (Root) Element Olmalıdır**
Bir component'ten döndürülen JSX ifadesi, her zaman tek bir ebeveyn (parent) element tarafından sarmalanmalıdır.

JavaScript

```java
// Yanlış: İki tane kök element var (h1 ve p)
return (
  <h1>Başlık</h1><p>Paragraf</p>
);

// Doğru: Bir div ile sarmalanmış
return (
  <div>
    <h1>Başlık</h1>
    <p>Paragraf</p>
  </div>
);
```

Eğer DOM'a fazladan bir `<div>` eklemek istemiyorsanız, **Fragment** (`<>...</>`) adı verilen özel yapıyı kullanabilirsiniz:

JavaScript

```java
// Doğru: Fragment kullanarak
return (
  <>
    <h1>Başlık</h1>
    <p>Paragraf</p>
  </>
);
```

**3. JavaScript İfadeleri `{}` İçinde Yazılır**
JSX'in en güçlü yanı, JavaScript değişkenlerini ve ifadelerini doğrudan HTML benzeri yapının içinde kullanabilmektir. Bunu yapmak için süslü parantez `{}` kullanılır.

JavaScript

```java
const userName = "Metehan";
const year = 2025;

return (
  <div>
    <h1>Hoş geldin, {userName}!</h1>
    <p>Şu anki yıl: {year}</p>
    <p>5 + 5 = {5 + 5}</p>
  </div>
);
```

**4. HTML Nitelikleri (Attributes) `camelCase` Olarak Yazılır**
JSX, JavaScript olduğu için `class` ve `for` gibi bazı kelimeler JavaScript'te özel anlamlara sahiptir (reserved keywords). Bu çakışmayı önlemek için JSX'te bu nitelikler farklı yazılır.

- `class` yerine **`className`** kullanılır.
- `for` (label etiketlerinde) yerine **`htmlFor`** kullanılır.
- `onclick` yerine **`onClick`**, `onchange` yerine **`onChange`** gibi tüm event'ler `camelCase` formatında yazılır.

JavaScript

```java
// HTML
<div class="profile">
  <label for="username">Kullanıcı Adı</label>
</div>

// JSX
<div className="profile">
  <label htmlFor="username">Kullanıcı Adı</label>
</div>
```

**5. Inline Stil (Style) Bir Obje Olarak Verilir**
JSX'te bir elemente inline stil vermek için `style` niteliğine bir JavaScript objesi atanır. Bu objenin anahtarları (key) da `camelCase` formatında olmalıdır.

JavaScript

```java
// Yanlış: String olarak stil vermek
<h1 style="color: blue; background-color: yellow;">Başlık</h1>

// Doğru: Obje olarak stil vermek
<h1 style={ { color: 'blue', backgroundColor: 'yellow' } }>Başlık</h1>
```

### **Özet**

- **JSX**, JavaScript içinde UI oluşturmak için kullanılan HTML benzeri bir sözdizimidir.
- Kodun **okunabilirliğini** ve **yönetilebilirliğini** artırır.
- Tarayıcılar tarafından anlaşılmaz, **Babel** ile normal JavaScript'e çevrilir.
- Katı kuralları vardır: Tek kök element, tüm etiketleri kapatma, `className` kullanımı vb.
- `{}` süslü parantezleri ile JavaScript'in gücünü doğrudan arayüze taşımamızı sağlar.

---

### **JSX Sözdizimi - Pratik Kullanım Kılavuzu**

Bu kılavuz, JSX'in temel sözdizimi kurallarını ve en yaygın kullanım senaryolarını pratik örneklerle açıklamaktadır.

### **1. Temel Kurallar**

- **Etiketleri Kapatma:** Kendi kendine kapanan etiketler de dahil olmak üzere tüm JSX etiketleri kapatılmalıdır.JavaScript
    
    ```java
    <br />
    <hr /><img src="path/to/image.png" />
    ```
    
- **`class` Yerine `className`:** JavaScript'te `class` özel bir anahtar kelime olduğu için, HTML'deki `class` niteliği yerine `className` kullanılır.JavaScript
    
    ```java
    <div className="card-container">...</div>
    ```
    
- **Inline `style` Kullanımı:** Stil özellikleri, `camelCase` olarak yazılmış anahtarlara sahip bir JavaScript objesi olarak verilir.JavaScript
    
    ```java
    <p style={ { fontSize: '16px', backgroundColor: '#f0f0f0' } }>Styled Text</p>
    ```
    

### **2. JavaScript'i JSX İçinde Kullanma**

- **Yorum Satırları:** JSX içinde yorumlar `{/* ... */}` arasına yazılır. Standart HTML yorumları (``) burada çalışmaz.JavaScript
    
    ```java
    <div>
      {/* Bu bir başlık component'idir */}
      <h1>Merhaba!</h1>
    </div>
    ```
    
- **Değişkenleri Görüntüleme:** Değişkenler veya JavaScript ifadeleri, `{}` süslü parantezleri içine yazılarak doğrudan JSX'e gömülebilir.JavaScript
    
    ```java
    const userName = "Kullanıcı";
    return <p>Hoşgeldin, {userName}!</p>;
    ```
    
- **Fonksiyon Çağırma:** Fonksiyonlar da `{}` içinde çağrılabilir. Fonksiyonun geri döndürdüğü değer (string, number, veya başka bir JSX) ekranda gösterilir.
    
    ```java
    const getUserFullName = (user) => `${user.firstName} ${user.lastName}`;
    const user = { firstName: "Ahmet", lastName: "Yılmaz" };
    
    return <h1>{getUserFullName(user)}</h1>;
    ```
    

### **3. Dinamik Arayüzler Oluşturma**

- **Koşullu Render (Conditional Rendering):** JSX içinde doğrudan `if-else` blokları kullanılamaz. Bunun yerine **ternary operatörü (`? :`)** en yaygın yöntemdir.
    
    ```java
    const isLoggedIn = true;
    return (
      <div>
        {isLoggedIn ? <UserProfile /> : <LoginForm />}
      </div>
    );
    ```
    
    **Ek Bilgi:** Eğer sadece `if` koşulu varsa (else durumu yoksa), **mantıksal AND operatörü (`&&`)** daha pratik bir kısa yoldur.
    
    ```java
    const unreadMessagesCount = 5;
    return (
      <div>
        {unreadMessagesCount > 0 && <h2>{unreadMessagesCount} yeni mesajınız var!</h2>}
      </div>
    );
    // Açıklama: Koşul doğruysa, &&'den sonraki JSX render edilir. Yanlışsa, hiçbir şey render edilmez.
    ```
    
- **Listeleri Render Etme (`.map()` metodu):** Bir veri dizisindeki her eleman için bir component veya JSX elementi oluşturmak amacıyla `.map()` metodu kullanılır.JavaScript
    
    ```java
    const products = ["Laptop", "Mouse", "Keyboard"];
    
    return (
      <ul>
        {products.map((product, index) => (
          <li key={index}>{product}</li>
        ))}
      </ul>
    );
    ```
    
    **ÇOK ÖNEMLİ - `key` Prop'u:**
    Bir liste render ederken, `.map()` içindeki her bir elemana **benzersiz (unique)** bir `key` prop'u vermek zorunludur.
    
    - **Neden?** `key`, React'in hangi elemanın değiştiğini, eklendiğini veya silindiğini anlamasına yardımcı olur. Bu, performansı ciddi şekilde artırır ve olası hataları önler.
    - **Ne olmalı?** `key` değeri, liste içindeki diğer elemanlardan farklı olmalıdır. Genellikle verinin kendisinden gelen `product.id` gibi benzersiz bir kimlik (ID) kullanılır. Dizinin `index` değerini `key` olarak kullanmak, eğer liste hiçbir zaman değişmeyecekse (sıralama, silme, ekleme olmayacaksa) kabul edilebilir, ancak genel olarak **kötü bir pratik** olarak görülür. Mümkünse her zaman veriye ait `id` kullanılmalıdır.

### **4. Component Mimarisi**

- **Component Çağırma:** Oluşturulan bir React component'i, bir HTML etiketi gibi, adı büyük harfle başlayarak çağrılır.
    
    ```java
    import Header from './Header';
    
    function App() {
      return <Header />;
    }
    ```
    
- **Props Gönderme:** Üst component'ten alt component'e veri aktarmak için props (properties) kullanılır. Bu, HTML niteliklerine çok benzer.
    
    ```java
    <UserProfile userName="Ayşe" age={28} />
    ```
    
- **Props Alma:** Alt component, kendisine gönderilen props'ları fonksiyon parametresi olarak alır. Genellikle **destructuring** yöntemiyle doğrudan istenen prop'lar alınır.
    
    ```java
    // props'ları obje olarak alıp içinden kullanmak
    function UserProfile(props) {
      return <h1>{props.userName}</h1>;
    }
    
    // props'ları doğrudan destructuring ile almak (daha modern ve yaygın)
    function UserProfile({ userName, age }) {
      return <p>{userName} ({age})</p>;
    }
    ```