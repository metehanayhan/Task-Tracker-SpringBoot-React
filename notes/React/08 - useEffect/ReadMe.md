# useEffect

### **Component'in Dış Dünya İle Etkileşimi: `useEffect` Hook'u**

Bir React component'inin temel görevi, `props` ve `state` alıp ekrana çizilecek JSX'i döndürmektir. Ancak bazen component'lerimizin bu temel görevin dışında işler yapması gerekir.

### **1. "Yan Etki" (Side Effect) Nedir?**

React'in kontrolü dışındaki herhangi bir işlem "yan etki" olarak kabul edilir. Bir component'in render süreci dışında yaptığı her şey bir yan etkidir. Başlıca yan etki türleri şunlardır:

- **Veri Çekme (Data Fetching):** Bir API'ye istek atıp sunucudan veri almak.
- **Abonelikler (Subscriptions):** Bir WebSocket bağlantısı kurmak veya `setInterval` gibi zamanlayıcılar başlatmak.
- **DOM'u Manuel Olarak Değiştirme:** `document.title`'ı (sayfa başlığını) değiştirmek veya `window` objesine event listener eklemek gibi React dışı DOM manipülasyonları.

**Problem:** Bu tür kodları nereye yazmalıyız? Direkt olarak component fonksiyonunun içine yazarsak, bu kod component **her render edildiğinde** (her state veya prop değişiminde) yeniden çalışır. Bu, gereksiz API istekleri, hafıza sızıntıları ve sonsuz döngüler gibi ciddi sorunlara yol açabilir.

**Çözüm:** React bize bu yan etkileri component'in yaşam döngüsüyle (lifecycle) senkronize bir şekilde çalıştırmak için `useEffect` hook'unu sunar.

### **2. `useEffect`'in Anatomisi**

`useEffect`, iki argüman alan bir fonksiyondur:

JavaScript

```java
import { useEffect } from 'react';

useEffect(() => {
  // 1. Yan etkiyi çalıştıran fonksiyon (Effect Function)
  // Bu kod, component render edildikten SONRA çalışır.

  return () => {
    // 3. Temizlik fonksiyonu (Cleanup Function) - Opsiyonel
  };
}, [/* 2. Bağımlılık dizisi (Dependency Array) */]);
```

1. **Yan Etki Fonksiyonu:** Yapmak istediğimiz yan etkiyi (veri çekme, timer başlatma vb.) içeren fonksiyondur.
2. **Bağımlılık Dizisi:** `useEffect`'in **ne zaman yeniden çalışacağını** kontrol eden bir dizidir. Burası `useEffect`'in en kritik ve en önemli kısmıdır.
3. **Temizlik Fonksiyonu (Cleanup):** Yan etki fonksiyonundan `return` edilen bu opsiyonel fonksiyon, bir sonraki etkiden önce veya component ekrandan kaldırıldığında (unmount) çalışarak geride çöp (hafıza sızıntısı vb.) bırakmamızı engeller.

### **3. `useEffect`'in Üç Temel Kullanım Senaryosu**

`useEffect`'in davranışını tamamen **bağımlılık dizisi** belirler.

### **Senaryo 1: Bağımlılık Dizisi Hiç Verilmezse (ÇOK NADİR KULLANILIR)**

JavaScript

```java
useEffect(() => {
  console.log('Component render edildi.');
  // Bu etki, ilk render'dan sonra VE her state/prop değişiminden sonra çalışır.
});
```

**Ne zaman çalışır?:** Her render'dan sonra.
**UYARI:** Bu kullanım genellikle istenmeyen bir durumdur. Eğer bu etki içinde state güncellerseniz, **sonsuz bir render döngüsüne** girersiniz. Bu yüzden neredeyse her zaman bir bağımlılık dizisi belirtmelisiniz.

### **Senaryo 2: Boş Bağımlılık Dizisi `[]` (EN YAYGIN KULLANIMLARDAN BİRİ)**

JavaScript

```java
useEffect(() => {
  // Sunucudan veri çekme işlemi
  fetch('https://api.example.com/data')
    .then(res => res.json())
    .then(data => setData(data));
}, []); // <-- Boş dizi
```

**Ne zaman çalışır?:** Yalnızca **bir kez**, component ilk kez ekrana basıldığında (mount olduğunda).
**Kullanım Alanları:**

- Component'in ihtiyaç duyduğu başlangıç verisini API'den çekmek.
- Sadece bir kereliğine kurulması gereken abonelikleri veya event listener'ları başlatmak.

### **Senaryo 3: Dolu Bağımlılık Dizisi `[prop, state]`**

JavaScript

```java
const [userId, setUserId] = useState(1);
const [user, setUser] = useState(null);

useEffect(() => {
  // userId her değiştiğinde bu etki yeniden çalışır.
  fetch(`https://api.example.com/users/${userId}`)
    .then(res => res.json())
    .then(data => setUser(data));
}, [userId]); // <-- userId'ye bağımlı
```

**Ne zaman çalışır?:** Component ilk kez mount olduğunda **VE** bağımlılık dizisindeki değerlerden herhangi biri değiştiğinde.
**Kullanım Alanları:**

- Belirli bir `prop` veya `state` değiştiğinde yeniden veri çekmek (Örnek: Kullanıcı ID'si değişince yeni kullanıcının bilgilerini çekmek).
- Bir state değişimine tepki olarak `document.title`'ı güncellemek.

### **4. Temizlik Fonksiyonu (Cleanup Function)**

Bir component ekrandan kaldırıldığında (örneğin kullanıcı başka bir sayfaya geçtiğinde), başlattığımız yan etkilerin de temizlenmesi gerekir. Aksi takdirde **hafıza sızıntıları (memory leaks)** oluşur. Örneğin, başlattığınız bir `setInterval` component yok olduktan sonra bile çalışmaya devam eder.

Temizlik fonksiyonu, bu tür durumları yönetmek için vardır.

**Örnek: Bir Zamanlayıcı (Timer)**

JavaScript

```java
function Timer() {
  const [seconds, setSeconds] = useState(0);

  useEffect(() => {
    // Yan etki: Her saniye state'i güncelleyen bir interval başlat.
    const intervalId = setInterval(() => {
      setSeconds(prevSeconds => prevSeconds + 1);
    }, 1000);

    // Temizlik Fonksiyonu: Component ekrandan kaldırılmadan hemen önce çalışır.
    return () => {
      clearInterval(intervalId); // Interval'ı temizle.
      console.log('Timer temizlendi!');
    };
  }, []); // Sadece bir kez çalışmasını istediğimiz için boş dizi.

  return <div>Süre: {seconds} saniye</div>;
}
```

Bu örnekte, `Timer` component'i ekrandan kaldırıldığı anda `clearInterval` çalışır ve gereksiz yere çalışan zamanlayıcıyı durdurarak hafıza sızıntısını önler.

---

### **Özet ve Altın Kurallar**

1. **Amaç:** `useEffect`, React component'lerinin dış dünya ile etkileşime girmesini (yan etkileri yönetmesini) sağlar.
2. **Ne Zaman Çalışır?:** Her zaman component **render edildikten sonra** çalışır.
3. **Bağımlılık Dizisi Kraldır:** `useEffect`'in davranışını tamamen bu dizi kontrol eder.
    - **Boş Dizi `[]`:** Sadece component ilk render olduğunda (mount) bir kez çalışır.
    - **Dolu Dizi `[a, b]`:** İlk render'da ve `a` veya `b` her değiştiğinde çalışır.
    - **Dizi Yoksa:** Her render'da çalışır (Genellikle kaçının).
4. **Temizlik (Cleanup):** `useEffect` içinden `return` edilen fonksiyon, abonelikler ve zamanlayıcılar gibi yan etkileri temizleyerek hafıza sızıntılarını önler.
5. **Dürüst Olun:** Bağımlılık dizisine, etki fonksiyonu içinde kullandığınız tüm `prop` ve `state`'leri eklemelisiniz. Aksi takdirde kodunuz eski değerlerle çalışarak hatalara neden olabilir.

---

## **`useEffect` Hakkında Ekstra Önemli Bilgiler ve İpuçları**

### **1. Bağımlılık Dizisi ve Fonksiyonlar**

Eğer `useEffect`'in içinde component içinde tanımlanmış bir fonksiyonu çağırıyorsanız, o fonksiyonu bağımlılık dizisine eklemeniz gerekir. Ancak bu bir soruna yol açabilir:

JavaScript

```java
function MyComponent({ id }) {
  const [data, setData] = useState(null);

  // Bu fonksiyon, MyComponent her render olduğunda yeniden oluşturulur.
  const fetchData = () => {
    console.log("Fetching data...");
    // ... id'ye göre veri çekme mantığı
  }

  useEffect(() => {
    fetchData();
  }, [fetchData]); // <-- fetchData'yı bağımlılık olarak ekledik.

  // ...
}
```

**Problem:** `MyComponent` her render olduğunda (örneğin üst component'ten farklı bir prop aldığında), `fetchData` fonksiyonu bellekte yeniden yaratılır. `useEffect` de `fetchData`'nın değiştiğini (çünkü referansı değişti) görür ve **gereksiz yere tekrar çalışarak sonsuz döngüye neden olabilir.**

**Çözüm Yolları:**

- **En Basit Çözüm:** Eğer fonksiyon sadece o `useEffect` içinde kullanılıyorsa, onu doğrudan `useEffect`'in içine taşıyın.JavaScript
    
    ```java
    useEffect(() => {
      const fetchData = () => {
        console.log("Fetching data...");
      }
      fetchData();
    }, [id]); // Artık sadece id'ye bağımlı.
    ```
    
- **İleri Düzey Çözüm:** Eğer fonksiyon hem `useEffect` içinde hem de başka yerlerde (örneğin bir butona tıklandığında) kullanılacaksa, fonksiyonu **`useCallback`** hook'u ile sarmalayın. `useCallback`, içindeki fonksiyonu bağımlılıkları değişmediği sürece yeniden oluşturulmaktan korur. Bu konuyu ileride "Performans Optimizasyonu" derslerinde daha detaylı görebiliriz.

### **2. Veri Çekme ve "Race Condition" Sorunu**

Bu, özellikle hızlı kullanıcı etkileşimlerinde ortaya çıkan kritik bir sorundur.

**Senaryo:** Bir arama kutunuz var ve kullanıcı her harf girdiğinde `useEffect` tetiklenerek API'ye istek atıyor.

1. Kullanıcı "a" yazar. `fetch('/search?q=a')` isteği atılır.
2. Kullanıcı hızlıca "b" yazar. `fetch('/search?q=ab')` isteği atılır.

İnternet bağlantısı nedeniyle "ab" isteğinin cevabı, "a" isteğinin cevabından **daha önce** gelebilir. Sonra "a" isteğinin cevabı gelir ve ekrandaki doğru verinin ("ab" sonuçlarının) üzerine yazarak yanlış veriyi ("a" sonuçlarını) gösterir. Buna **Race Condition (Yarış Durumu)** denir.

**Çözüm: Temizlik Fonksiyonu ile İsteği İptal Etmek/Görmezden Gelmek**`useEffect`'in temizlik fonksiyonu, bu durumu yönetmek için mükemmeldir. Etki yeniden çalışmadan hemen önce, bir önceki etkinin "artık geçersiz olduğunu" belirtebiliriz.

JavaScript

```java
useEffect(() => {
  let isActive = true; // Bu etkinin "aktif" olduğunu belirten bir bayrak.

  fetch(`https://api.example.com/users/${userId}`)
    .then(res => res.json())
    .then(data => {
      // Sadece bu etki hala "aktif" ise state'i güncelle.
      if (isActive) {
        setUser(data);
      }
    });

  // Temizlik fonksiyonu
  return () => {
    // Component unmount olduğunda veya etki yeniden çalışmadan önce,
    // bir önceki etkinin bayrağını "pasif" yap.
    isActive = false;
  };
}, [userId]);
```

Bu desen sayesinde, hangi istek en son atıldıysa sadece onun sonucu state'e yazılır, öncekiler `isActive` kontrolüne takılıp görmezden gelinir.

### **3. `useEffect` ve Olay Yöneticileri (`Event Handlers`) Farkı**

Yeni başlayanlar bazen hangi mantığı `useEffect`'e, hangisini bir `onClick` gibi olay yöneticisine koyacaklarını karıştırabilirler.

- **Olay Yöneticileri (`onClick`, `onSubmit` vb.):** **Belirli bir kullanıcı etkileşiminin** doğrudan bir sonucu olarak çalışması gereken kodlar için kullanılır. Kullanıcı butona bastı -> "Formu gönder". Bu bir "olay"dır.
- **`useEffect`:** Kullanıcı etkileşiminden **bağımsız olarak**, component'in render edilmesi veya belirli `prop`/`state` değerlerinin değişmesiyle **senkronize olması gereken** kodlar için kullanılır. State'teki sepet güncellendi -> "Toplam tutarı yeniden hesapla". Bu bir "senkronizasyon"dur.

### **4. Hooks Kuralları (ESLint Eklentisi)**

React ekibi, `useEffect`'in bağımlılık dizisini doğru bir şekilde yönetmenin ne kadar kritik olduğunu bildiği için bir **ESLint eklentisi (`eslint-plugin-react-hooks`)** oluşturmuştur. Bu eklenti, Vite veya Create React App gibi modern araçlarla **varsayılan olarak gelir.**

Bu eklenti, editörünüzde sizi iki konuda uyarır:

1. Eğer `useEffect` içinde bir `prop` veya `state` kullanıp onu bağımlılık dizisine eklemeyi unutursanız, sizi uyarır ve otomatik düzeltme seçeneği sunar.
2. Eğer bağımlılık dizisine gereksiz bir değer eklerseniz, yine uyarır.

**Altın Kural:** **Bu ESLint kuralına her zaman uyun.** Bağımlılık dizisini manuel olarak yönetmeye çalışmak yerine, eklentinin uyarılarını dikkate alarak kodunuzu daha hatasız hale getirebilirsiniz.

---