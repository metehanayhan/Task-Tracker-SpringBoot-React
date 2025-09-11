# State ve useState

### **Component'in Hafızası: State ve `useState` Hook'u**

Şimdiye kadar component'lerin `props` aracılığıyla dışarıdan veri alabildiğini gördük. Peki bir component'in kendi içindeki verileri, örneğin bir butona kaç kez tıklandığını veya bir form alanına ne yazıldığını, nasıl takip ederiz? İşte bu noktada **State (Durum)** devreye girer.

### **1. Neden State'e İhtiyaç Duyarız? Problem Ne?**

React'in nasıl çalıştığını anlamak için, state olmadan bir sayaç yapmayı deneyelim.

**Yanlış Yaklaşım (Normal Değişken ile):**

JavaScript

```java
// src/Counter.jsx

function Counter() {
  let count = 0; // Normal bir JavaScript değişkeni

  const handleIncrement = () => {
    count = count + 1;
    console.log(count); // Konsolda 1, 2, 3... diye artar
  };

  return (
    <div>
      <p>Sayı: {count}</p> {/* Ekranda her zaman 0 yazar */}
      <button onClick={handleIncrement}>Arttır</button>
    </div>
  );
}
```

Bu kodu çalıştırdığınızda, butona her bastığınızda konsolda `count` değişkeninin değerinin arttığını görürsünüz. Ancak ekrandaki yazı **her zaman "Sayı: 0" olarak kalır.**

**Neden?** Çünkü React'in bir component'i yeniden render etmesi (ekranı güncellemesi) için normal bir JavaScript değişkeninin değişmesi yeterli değildir. React'in, "Hey, bir veri değişti, git ve arayüzü bu yeni veriye göre güncelle!" komutunu alması gerekir. İşte bu komutu veren mekanizmaya **State** diyoruz.

### **2. State Nedir?**

**State**, bir component'in kendi içinde tuttuğu, zamanla değişebilen ve her değiştiğinde component'in yeniden render edilmesini tetikleyen bir "hafıza"dır.

- **Props vs. State:**
    - **Props:** Component'e **dışarıdan (parent'tan)** verilir. Component için **salt-okunurdur (read-only)**.
    - **State:** Component'in **kendi içinde** yönetilir. Component tarafından **değiştirilebilir**.

Bir component'in State'i her değiştiğinde, React o component'i ve onun alt component'lerini otomatik olarak yeniden render eder. Bu sayede arayüz, her zaman en güncel state'i yansıtır. Bu, React'in **"reaktif" (tepkisel)** olmasının temelidir.

### **3. Hooks Nedir?**

Peki fonksiyonel component'lere bu "state" hafızasını nasıl ekleriz? Eskiden bunun için Class Component'ler kullanmak zorundaydık. Ancak React 16.8 ile birlikte **Hooks (Kancalar)** tanıtıldı.

**Hooks**, fonksiyonel component'lerin içine "kanca atarak" React'in state ve diğer özelliklerine erişmemizi sağlayan özel fonksiyonlardır. Tüm hook'lar `use` kelimesiyle başlar (örn: `useState`, `useEffect`, `useContext`).

Bizim için en temel ve en önemli hook, **`useState`**'dir.

### **4. `useState` Hook'u Nasıl Kullanılır?**

`useState`, bir component'e state eklemenin yoludur. Kullanımı üç adımdan oluşur:

**Adım 1: Import Etmek**`useState`'i kullanmak için önce onu React'ten import etmeliyiz.

JavaScript

```java
import { useState } from 'react';
```

**Adım 2: State Değişkeni Oluşturmak**
Component fonksiyonunun en üst seviyesinde `useState`'i çağırırız.

JavaScript

```java
const [count, setCount] = useState(0);
```

Bu satır ilk başta kafa karıştırıcı görünebilir, şimdi onu parçalara ayıralım:

- `useState(0)`: `useState` fonksiyonunu **ilk değer (initial value)** olarak `0` ile çağırıyoruz. Bu, component ilk render edildiğinde state'imizin değerinin 0 olacağı anlamına gelir. `useState` bize içinde iki eleman olan bir dizi `[değer, güncellemeFonksiyonu]` döndürür.
- `const [count, setCount] = ...`: Burada JavaScript'in **dizi yapıbozumu (array destructuring)** özelliğini kullanıyoruz. `useState`'in döndürdüğü dizinin elemanlarına isimler veriyoruz:
    - `count`: State'imizin **o anki değerini** tutan değişkendir. Bunu JSX içinde `{count}` şeklinde kullanırız.
    - `setCount`: Bu state'i **güncellememizi sağlayan özel fonksiyondur.** State'i **asla** `count = 5` gibi doğrudan değiştirmemelisiniz. Her zaman bu setter fonksiyonunu kullanmalısınız.

**Adım 3: State'i Okumak ve Güncellemek**
Artık sayaç örneğimizi `useState` ile doğru bir şekilde yazabiliriz.

JavaScript

```java
// src/Counter.jsx
import { useState } from 'react'; // Adım 1: Import et

function Counter() {
  // Adım 2: State değişkenini oluştur
  const [count, setCount] = useState(0); 

  const handleIncrement = () => {
    // Adım 3: State'i güncellemek için setter fonksiyonunu kullan
    setCount(count + 1); 
  };
  
  const handleDecrement = () => {
    setCount(count - 1);
  };

  return (
    <div>
      {/* Adım 3: State'i okumak */}
      <p>Sayı: {count}</p> 
      <button onClick={handleIncrement}>Arttır</button>
      <button onClick={handleDecrement}>Azalt</button>
    </div>
  );
}

export default Counter;
```

### **State Değiştiğinde Arka Planda Neler Oluyor?**

Kullanıcının "Arttır" butonuna bastığı anı adım adım inceleyelim:

1. **İlk Render:** `Counter` component'i ilk kez çalışır. `useState(0)` çağrılır. React, `count`'un ilk değerinin `0` olduğunu hafızasına yazar. Ekrana "Sayı: 0" yazılır.
2. **Tıklama Olayı:** Kullanıcı butona tıklar ve `handleIncrement` fonksiyonu çalışır.
3. **Güncelleme İsteği:** `setCount(count + 1)` (yani `setCount(1)`) fonksiyonu çağrılır. Bu fonksiyon, React'e bir "state güncelleme isteği" gönderir. **Önemli:** `count` değişkeni o anda hemen `1` olmaz.
4. **Yeniden Render Planlaması:** React bu isteği alır ve `Counter` component'ini yeniden render etmeyi planlar.
5. **Yeniden Render:** React, `Counter` fonksiyonunu baştan sona tekrar çalıştırır.
6. **Yeni Değeri Alma:** Bu yeni render sırasında, `useState(0)` satırına gelindiğinde, React hafızasındaki güncel değeri hatırlar ve bu sefer `count` değişkenine `1` değerini atar.
7. **DOM Güncellemesi:** React, yeni oluşturulan JSX (`<p>Sayı: 1</p>`) ile bir önceki JSX'i (`<p>Sayı: 0</p>`) karşılaştırır. Sadece `<p>` etiketinin içeriğinin değiştiğini fark eder ve **sadece o kısmı** gerçek DOM'da verimli bir şekilde günceller.

İşte React'in reaktifliğinin ve verimliliğinin sırrı budur. Siz sadece state'i güncellersiniz, React gerisini halleder.

---

**Ek Bilgi:** State olarak sadece sayı değil; string, boolean, dizi veya obje de tutabilirsiniz.

JavaScript

```java
const [text, setText] = useState(''); // Form inputu için
const [isLoggedIn, setIsLoggedIn] = useState(false); // Kullanıcı girişi için
const [user, setUser] = useState({ name: 'Ali', age: 30 }); // Obje için
```

---

### **State Hakkında Bilinmesi Gerekenler**

### **1. State Güncellemeleri Asenkrondur (Asynchronous)**

Bu, yeni başlayanların en sık karşılaştığı "tuzak"tır. Bir state'i güncelleyen setter fonksiyonunu (`setCount(1)` gibi) çağırdığınızda, state değişkeni **o anda hemen değişmez.**

React, performansı artırmak için state güncellemelerini toplu halde işleyebilir (batching). `setCount(1)` demek, React'e "Ben `count`'un 1 olmasını istiyorum, lütfen en kısa zamanda bir yeniden render planla" demektir.

**Pratik Sonucu:**

JavaScript

```java
const [count, setCount] = useState(0);

const handleClick = () => {
  setCount(count + 1); // React'e bir render planlamasını söyledik.
  console.log(count);  // BU SATIR HALA 0 YAZDIRACAKTIR!
};
```

Neden? Çünkü `handleClick` fonksiyonu, component'in o anki render'ına aittir ve o render başladığında `count`'un değeri 0'dı. `setCount` bir sonraki render'ı tetikler, ama mevcut render'ın değerlerini değiştirmez.

### **2. Bir Önceki Değere Göre Güncelleme: Fonksiyonel Güncelleme (Functional Update)**

Peki, state'in en güncel haline güvenerek bir güncelleme yapmak istersek ne olacak? Örneğin, bir butona çok hızlı iki kez basıldığında sayacın iki artmasını garanti etmek gibi.

**Yanlış Yaklaşım:**

JavaScript

```java
const handleTripleClick = () => {
  setCount(count + 1); // count hala 0
  setCount(count + 1); // count hala 0
  setCount(count + 1); // count hala 0
}
// Bu fonksiyon çalıştığında, sayaç sadece 1 artar. Çünkü hepsi aynı render'daki 0 değerini kullanır.
```

**Doğru Yaklaşım: Fonksiyonel Güncelleme**
Setter fonksiyonuna doğrudan bir değer vermek yerine, bir **fonksiyon** da verebiliriz. Bu fonksiyon, parametre olarak state'in **en güncel (previous) halini** alır ve yeni state değerini döndürmelidir.

JavaScript

```java
const handleTripleClick = () => {
  // Bu yöntem, güncellemelerin sıraya alınmasını ve doğru şekilde uygulanmasını garanti eder.
  setCount(prevCount => prevCount + 1);
  setCount(prevCount => prevCount + 1);
  setCount(prevCount => prevCount + 1);
}
// Bu fonksiyon çalıştığında, sayaç beklendiği gibi 3 artacaktır.
```

**Kural:** Eğer yeni state'iniz bir önceki state değerine bağlıysa, **her zaman fonksiyonel güncelleme formunu kullanın.**

### **3. Objeler ve Dizilerle State Yönetimi (Immutability Kuralı)**

Bu, React'teki en önemli kurallardan biridir. State'teki bir objeyi veya diziyi güncellerken, **mevcut objeyi/diziyi asla doğrudan değiştirmemelisiniz (mutate etmemelisiniz).** Bunun yerine, her zaman **yeni bir kopya** oluşturmalısınız.

**Neden?** React, bir state'in değişip değişmediğini anlamak için yüzeysel bir karşılaştırma yapar. Eğer objenin veya dizinin bellekteki referansı (adresi) aynı kalırsa, React bir değişiklik olmadığını varsayar ve component'i yeniden render etmez.

**Obje Güncelleme Örneği:**

JavaScript

```java
const [user, setUser] = useState({ name: 'Ali', age: 30 });

const handleNameChange = () => {
  // YANLIŞ ❌ - Mevcut objeyi değiştirme
  // user.name = 'Veli';
  // setUser(user); // React referans aynı kaldığı için değişikliği algılamaz!

  // DOĞRU ✅ - Spread operatörü (...) ile yeni bir kopya oluşturma
  setUser({ ...user, name: 'Veli' }); 
  // Bu, user objesinin tüm özelliklerini yeni bir objeye kopyalar ve 'name' özelliğini üzerine yazar.
}
```

**Dizi Güncelleme Örneği:**

JavaScript

```java
const [todos, setTodos] = useState(['Ders çalış', 'Spor yap']);

const addTodo = () => {
  // YANLIŞ ❌ - push metodu mevcut diziyi değiştirir
  // todos.push('Alışveriş yap');
  // setTodos(todos); // Değişiklik algılanmaz.

  // DOĞRU ✅ - Spread operatörü ile yeni bir dizi oluşturma
  setTodos([...todos, 'Alışveriş yap']);
}
```

### **4. Birden Fazla State Değişkeni Kullanmak**

Bir component'te birden fazla, birbiriyle alakasız state verisi tutmanız gerekebilir. Bu durumda, her bir state için ayrı bir `useState` çağrısı yapmak en temiz yöntemdir.

JavaScript

```java
function RegisterForm() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  // ... form mantığı
}
```

Bu, tüm bu verileri tek bir büyük state objesinde tutmaktan daha yönetilebilir ve okunabilirdir.

### **5. State'i Yukarı Taşıma (Lifting State Up)**

Bazen iki farklı alt component'in aynı state verisine erişmesi veya onu değiştirmesi gerekir. Bu durumda state, o iki component'in **ortak en yakın üst (parent) component'ine** taşınmalıdır.

**Senaryo:** Bir sıcaklık birimini (`Celsius`) gösteren bir component ve bu sıcaklığı değiştiren bir input component'i düşünün.

- `TemperatureDisplay` component'i `temperature` state'ini okumalı.
- `TemperatureInput` component'i `temperature` state'ini değiştirmeli.

**Çözüm:** `temperature` state'i ikisinin de parent'ı olan `App` component'inde tutulur.

1. `App` component'i, `temperature` state'ini `TemperatureDisplay`'e **prop olarak** gönderir.
2. `App` component'i, `setTemperature` fonksiyonunu `TemperatureInput`'a **prop olarak** gönderir.

Bu sayede, `TemperatureInput` içindeki bir değişiklik, `App`'teki state'i günceller. `App`'teki state güncellenince, `App` yeniden render olur ve bu da `TemperatureDisplay`'in yeni `temperature` prop'unu alarak güncellenmesini sağlar. Bu, React'te component iletişimi için çok temel ve güçlü bir desendir.

Bu detaylar, `useState`'i sadece "çalışan" kod yazmak için değil, aynı zamanda **doğru, verimli ve ölçeklenebilir** kod yazmak için nasıl kullanacağınızı gösterir.

---