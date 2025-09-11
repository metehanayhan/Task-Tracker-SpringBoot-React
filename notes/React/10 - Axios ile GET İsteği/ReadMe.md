# Axios ile GET İsteği

### **React'te Veri Çekme - Axios ile GET İsteği Yapmak**

Bu derste, sahte bir online API'den kullanıcı listesi çekecek ve bu listeyi ekranda gösterecek bir component oluşturacağız.

### **1. Adım: Hazırlık ve Kurulum**

**Axios Kütüphanesini Yükleme:**
Projemizde API istekleri yapmak için Axios kullanacağız. Eğer henüz yüklemediyseniz, projenizin terminalinde aşağıdaki komutu çalıştırın:

Bash

```java
npm install axios
```

**Kullanacağımız Sahte API: JSONPlaceholder**
Gerçek bir backend yazmadan API isteklerini test etmek için JSONPlaceholder adlı ücretsiz servisi kullanacağız. Bize sahte kullanıcı verileri sağlayan şu endpoint'i hedefleyeceğiz: `https://jsonplaceholder.typicode.com/users`

### **2. Adım: Component ve Gerekli State'lerin Oluşturulması**

`UserList.jsx` adında yeni bir component dosyası oluşturalım. Bir API isteğinin üç olası durumu vardır:

1. **Yükleniyor (Loading):** İstek atıldı, henüz cevap gelmedi.
2. **Başarılı (Success):** Cevap başarıyla geldi ve veriye ulaştık.
3. **Hata (Error):** İstek başarısız oldu (internet yok, sunucu hatası vb.).

Bu üç durumu da yönetebilmek için üç farklı state oluşturacağız.

JavaScript

```java
// src/components/UserList.jsx

import { useState, useEffect } from 'react';
import axios from 'axios';

function UserList() {
  // 1. Veriyi saklamak için state (başlangıçta boş bir dizi)
  const [users, setUsers] = useState([]);
  
  // 2. Yüklenme durumunu takip etmek için state (başlangıçta true)
  const [loading, setLoading] = useState(true);
  
  // 3. Hata durumunu saklamak için state (başlangıçta null)
  const [error, setError] = useState(null);

  // JSX kısmı şimdilik boş olabilir, önce mantığı yazacağız.
  return (
    <div>
      <h1>Kullanıcı Listesi</h1>
      {/* Buraya listeyi render edeceğiz */}
    </div>
  );
}

export default UserList;
```

### **3. Adım: `useEffect` İçinde API İsteği Yapma**

Veri çekme işlemi bir "yan etki" (side effect) olduğu için, bu isteği `useEffect` hook'u içinde yapacağız. İsteğin sadece component ilk render olduğunda bir kez yapılmasını istediğimiz için, `useEffect`'in bağımlılık dizisini boş (`[]`) bırakacağız.

JavaScript

```java
// ... importlar ve state'ler

useEffect(() => {
  // Axios ile GET isteği atıyoruz
  axios.get('https://jsonplaceholder.typicode.com/users')
    .then(response => {
      // İstek başarılı olursa:
      setUsers(response.data); // Gelen veriyi users state'ine ata
      setError(null); // Olası bir önceki hatayı temizle
    })
    .catch(error => {
      // İstek başarısız olursa:
      setError(error.message); // Hata mesajını error state'ine ata
      setUsers([]); // Veri listesini temizle
    })
    .finally(() => {
      // Bu blok, istek başarılı da olsa başarısız da olsa her zaman çalışır.
      setLoading(false); // Yüklenme durumunu sonlandır
    });
}, []); // <-- Boş dizi, bu etkinin sadece bir kez çalışmasını sağlar
```

### **4. Adım: Koşullu Render ile Durumları Ekrana Yansıtma**

Artık elimizde `loading`, `error` ve `users` state'leri var. Bu state'lere göre ekrana farklı JSX'ler basabiliriz. Buna **Koşullu Render (Conditional Rendering)** denir.

JavaScript

```java
// ... state'ler ve useEffect

if (loading) {
  return <div>Veriler Yükleniyor...</div>;
}

if (error) {
  return <div>Hata oluştu: {error}</div>;
}

// Eğer yüklenme bittiyse ve hata yoksa, listeyi göster
return (
  <div>
    <h1>Kullanıcı Listesi</h1>
    <ul>
      {users.map(user => (
        <li key={user.id}>
          {user.name} ({user.email})
        </li>
      ))}
    </ul>
  </div>
);
```

### **5. Tam ve Çalışan Kod (`UserList.jsx`)**

JavaScript

```java
import { useState, useEffect } from 'react';
import axios from 'axios';

function UserList() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios.get('https://jsonplaceholder.typicode.com/users')
      .then(response => {
        setUsers(response.data);
        setError(null);
      })
      .catch(error => {
        setError(error.message);
        setUsers([]);
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  if (loading) {
    return <div>Veriler Yükleniyor...</div>;
  }

  if (error) {
    return <div>Hata oluştu: {error}</div>;
  }

  return (
    <div>
      <h1>Kullanıcı Listesi</h1>
      <ul>
        {users.map(user => (
          <li key={user.id}>
            {user.name} - <em>{user.email}</em>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default UserList;
```

Bu component'i `App.jsx` içine `<UserList />` olarak eklediğinizde, önce "Veriler Yükleniyor..." yazısını, ardından da kullanıcı listesini göreceksiniz.

---

### **Daha Modern Bir Yaklaşım: `async/await`**

`.then/.catch` zincirleri yerine, `async/await` sözdizimini kullanarak asenkron kodu daha okunaklı bir şekilde yazabiliriz.

**Not:** `useEffect`'in doğrudan kendisine `async` veremezsiniz. Bunun yerine, içinde `async` bir fonksiyon tanımlayıp onu çağırmalısınız.

JavaScript

```java
useEffect(() => {
  // async bir fonksiyonu useEffect içinde tanımla
  const fetchUsers = async () => {
    try {
      // Henüz istek başlamadı, yükleniyor durumunu burada da ayarlayabiliriz
      setLoading(true); 
      const response = await axios.get('https://jsonplaceholder.typicode.com/users');
      setUsers(response.data);
      setError(null);
    } catch (err) {
      setError(err.message);
      setUsers([]);
    } finally {
      setLoading(false);
    }
  };

  // ve sonra o fonksiyonu çağır
  fetchUsers();
}, []);
```

Her iki yöntem de aynı işi yapar. `async/await` genellikle daha modern ve okunabilir kabul edilir.

### **Özet**

1. **Axios'u Yükle:** `npm install axios`.
2. **State'leri Tanımla:** Veri (`data`), yüklenme (`loading`) ve hata (`error`) için üç state oluştur.
3. **`useEffect` Kullan:** Yan etki olduğu için isteği `useEffect` içinde yap. Sadece bir kez çalışması için `[]` kullan.
4. **İsteği At:** `axios.get(URL)` ile isteği yap.
5. **Cevapları Yönet:** `.then/.catch/.finally` veya `try/catch/finally` ile üç durumu da (başarı, hata, tamamlanma) yöneterek state'leri güncelle.
6. **Koşullu Render Yap:** JSX içinde `if` kontrolleriyle veya ternary operatörlerle `loading` ve `error` durumlarına göre farklı çıktılar göster.

---