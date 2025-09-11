# Props

### **Component'ler Arası İletişim: Props**

Önceki derste component'lerin React'in yapı taşları olduğunu öğrendik. Peki bu yapı taşları birbiriyle nasıl konuşur? Bir component'in başka bir component'e veri göndermesi nasıl sağlanır? İşte bu noktada **Props** (Properties'in kısaltması) devreye girer.

### **1. Props Nedir? Temel Konsept**

Props, en basit tanımıyla, **üst (parent) component'ten alt (child) component'e veri aktarmak** için kullanılan bir mekanizmadır.

Bunu bir JavaScript fonksiyonuna argüman göndermek gibi düşünebilirsiniz:

JavaScript

```java
// Fonksiyon ve Argümanları
function selamVer(isim) {
  console.log(`Merhaba, ${isim}!`);
}
selamVer("Ahmet"); // "Ahmet" burada bir argümandır.

// React Component ve Props
<SelamVerComponent isim="Ahmet" /> // "Ahmet" burada bir prop'tur.
```

Component'ler birer fonksiyondur ve onlara gönderdiğimiz **props'lar da o fonksiyonların argümanlarıdır.** Bu sayede aynı component'i farklı verilerle defalarca kullanabiliriz.

**En Önemli Kural: Props Sadece Okunurdur (Read-Only)**
Bu, React'in en temel prensiplerinden biridir. Bir alt component, kendisine gönderilen props'ları **asla değiştiremez.** Props, sanki cam bir kutu içinde gelen bir hediye gibidir; içindekini görebilir ve kullanabilirsiniz ama onu değiştiremezsiniz. Bu tek yönlü veri akışı (`one-way data flow`), React uygulamalarını daha öngörülebilir ve daha kolay hata ayıklanabilir yapar.

### **2. Props Gönderme ve Alma**

Props'ları kullanmak iki adımlı bir süreçtir: Üst component'ten göndermek ve alt component'te almak.

**Adım 1: Props Göndermek (Parent Component)**
Props'lar, component'i çağırırken HTML nitelikleri (attributes) yazar gibi gönderilir.

Aşağıdaki `App.jsx` dosyasında, farklı verilerle üç adet `UserProfileCard` component'i oluşturalım:

JavaScript

```java
// src/App.jsx
import UserProfileCard from './UserProfileCard';

function App() {
  const hobbies = ['Kodlama', 'Müzik', 'Yürüyüş'];

  return (
    <div className="container">
      <h1>Kullanıcı Profilleri</h1>
      <UserProfileCard 
        name="Metehan" 
        age={25} 
        isPremiumUser={true} 
        hobbies={hobbies} 
      />
      <UserProfileCard 
        name="Ayşe" 
        age={30} 
        isPremiumUser={false}
        hobbies={['Kitap Okumak', 'Sinema']} 
      />
      <UserProfileCard 
        name="Fatma" 
        age={22}
      />
    </div>
  );
}

export default App;
```

**Dikkat Edilmesi Gerekenler:**

- **String Değerler:** Tırnak işaretleri `""` içinde gönderilir (`name="Metehan"`).
- **Diğer Veri Türleri:** String dışındaki her şey (sayı, boolean, dizi, obje) süslü parantez `{}` içinde gönderilmelidir (`age={25}`, `isPremiumUser={true}`).

**Adım 2: Props Almak ve Kullanmak (Child Component)**
Gönderilen bu props'lar, alt component'in fonksiyon parametresi olarak tek bir `props` objesi içinde toplanır.

Şimdi `UserProfileCard.jsx` dosyasını oluşturalım ve bu props'ları alalım.

**Yöntem 1: `props` objesini doğrudan kullanmak**

JavaScript

```java
// src/UserProfileCard.jsx

function UserProfileCard(props) {
  // props objesi şuna benzer: { name: "Metehan", age: 25, isPremiumUser: true, ... }
  console.log(props);

  return (
    <div className="card">
      <h2>{props.name} ({props.age})</h2>
      {props.isPremiumUser && <span>⭐ Premium Üye</span>}
      <ul>
        {props.hobbies.map(hobby => <li key={hobby}>{hobby}</li>)}
      </ul>
    </div>
  );
}

export default UserProfileCard;
```

**Yöntem 2: Obje Yapıbozumu (Object Destructuring) - (Modern ve Tavsiye Edilen Yöntem)**
Her seferinde `props.name`, `props.age` yazmak yerine, gelen `props` objesini doğrudan parametre kısmında parçalara ayırabiliriz. Bu, kodu çok daha temiz ve okunaklı hale getirir.

JavaScript

```java
// src/UserProfileCard.jsx

function UserProfileCard({ name, age, isPremiumUser, hobbies }) {
  return (
    <div className={isPremiumUser ? 'card premium' : 'card'}>
      <h2>{name} ({age})</h2>
      {isPremiumUser && <span>⭐ Premium Üye</span>}
      
      {/* hobbies prop'u gelmediyse hata vermemesi için kontrol */}
      {hobbies && (
        <>
          <h4>Hobiler:</h4>
          <ul>
            {hobbies.map(hobby => <li key={hobby}>{hobby}</li>)}
          </ul>
        </>
      )}
    </div>
  );
}

export default UserProfileCard;
```

### **3. Özel Props'lar ve İleri Seviye Kullanım**

### **`children` Prop'u**

`children`, React tarafından rezerve edilmiş özel bir prop'tur. Bir component'i çağırırken açılış ve kapanış etiketleri arasına yazdığınız her şey, o component'e `children` prop'u olarak gönderilir. Bu, genel kapsayıcı (wrapper) component'ler oluşturmak için çok güçlü bir yöntemdir.

**Örnek: Bir `Card` component'i yapalım.**

JavaScript

```java
// src/Card.jsx

// Gelen children'ı doğrudan div'in içine basıyoruz.
function Card({ children }) {
  return <div className="generic-card">{children}</div>;
}

export default Card;
```

**Kullanımı:**

JavaScript

```java
// src/App.jsx
import Card from './Card';

function App() {
  return (
    // Card etiketleri arasına yazdığımız her şey, Card component'ine children prop'u olarak gider.
    <Card>
      <h2>Bu bir Başlık</h2>
      <p>Bu da kartın içeriği. İstediğimiz kadar JSX elementi ekleyebiliriz.</p>
      <button>Tıkla</button>
    </Card>
  );
}
```

### **Varsayılan Prop Değerleri (Default Props)**

Bazen bir prop'un gönderilmesi zorunlu olmayabilir. Eğer bir prop gönderilmezse, değeri `undefined` olur ve bu hatalara yol açabilir. Bunu önlemek için varsayılan değerler atayabiliriz.

Modern JavaScript'te bunu yapmanın en kolay yolu, fonksiyonun parametrelerinde varsayılan değer atamaktır.

JavaScript

```java
// src/Button.jsx

function Button({ text = "Varsayılan Buton", color = "gray" }) {
  return <button style={{ backgroundColor: color }}>{text}</button>;
}

// Kullanımı
<Button text="Kaydet" color="blue" /> // Gönderilen değerleri kullanır.
<Button text="İptal" /> // color prop'u gönderilmediği için varsayılan "gray" kullanılır.
<Button /> // Hem text hem de color için varsayılan değerler kullanılır.
```

---

### **Özet ve Anahtar Kavramlar**

1. **Amaç:** Props, veriyi **tek yönlü** olarak üst (parent) component'ten alt (child) component'e aktarır.
2. **Salt Okunur (Read-Only):** Bir component, kendisine gelen props'ları **asla değiştiremez**.
3. **Veri Türleri:** String, sayı, boolean, dizi, obje, fonksiyon ve hatta başka React component'leri bile props olarak gönderilebilir.
4. **`{}` Kullanımı:** String dışındaki tüm dinamik veriler süslü parantez `{}` içinde gönderilir.
5. **Destructuring:** Props'ları alırken `({ name, age })` şeklinde yapıbozumu kullanmak, modern ve temiz bir yöntemdir.
6. **`children`:** Component etiketleri arasına yazılan her şeyi yakalayan özel bir prop'tur.