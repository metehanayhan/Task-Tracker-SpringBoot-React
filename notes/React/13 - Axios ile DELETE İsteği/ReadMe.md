# Axios ile DELETE İsteği

### **Sunucudan Veri Silme - Axios ile DELETE İsteği Yapmak**

### **1. DELETE İsteği Nedir?**

`DELETE` isteği, `PUT` isteği gibi, belirli bir kaynağı hedefler. Bu yüzden istek yapılan URL, silinecek olan verinin ID'sini içermelidir.

**En Önemli Farkları:**

- `POST` ve `PUT`'un aksine, `DELETE` isteği genellikle sunucuya bir **gövde (body) göndermez.** Sadece hangi kaynağın silineceğini URL üzerinden bildirir.
- Başarılı bir `DELETE` işleminden sonra sunucu genellikle `200 OK` veya `204 No Content` (İçerik Yok) gibi bir durum kodu ile boş bir cevap döner.

**Akış:**

1. **React (Frontend):** Kullanıcı "Sil" butonuna tıklar.
2. **React (Frontend):** `axios.delete(URL)` metodunu kullanarak silinmek istenen verinin ID'sini içeren URL'ye istek atar.
3. **Spring Boot (Backend):** `DELETE /users/5` gibi bir istek alır, ID'si 5 olan kullanıcıyı veritabanında bulur ve siler.
4. **Spring Boot (Backend):** İşlemin başarılı olduğuna dair bir cevap döner.

### **2. Adım: Basit Bir Silme Arayüzü**

Yine en basit haliyle başlayalım. Sadece bir input alanı ve bir butondan oluşan, ID'sini girdiğimiz kullanıcıyı silecek bir component yapalım.

JavaScript

```java
// src/components/DeleteUserSimple.jsx
import axios from 'axios';

function DeleteUserSimple() {

  const handleDelete = (event) => {
    event.preventDefault();

    const form = event.target;
    const id = form.elements.userId.value;

    if (!id) {
      alert('Lütfen silinecek kullanıcı ID girin!');
      return;
    }

    // URL'nin sonuna silinecek ID'yi ekliyoruz.
    const url = `https://jsonplaceholder.typicode.com/users/${id}`;

    // axios.delete metodunu kullanıyoruz. Gövde (body) göndermediğimize dikkat et.
    axios.delete(url)
      .then(response => {
        console.log('Silme işlemi başarılı:', response);
        alert(`ID: ${id} olan kullanıcı başarıyla silindi!`);
        form.reset();
      })
      .catch(error => {
        console.error('Silme sırasında hata!', error);
        alert('Kullanıcı silinirken bir hata oluştu.');
      });
  };

  return (
    <form onSubmit={handleDelete}>
      <h2>Kullanıcı Sil (Basit)</h2>
      <div>
        <label>Silinecek Kullanıcı ID:</label>
        <input type="number" name="userId" placeholder="Örn: 1" required />
      </div>
      <button type="submit">Kullanıcıyı Sil</button>
    </form>
  );
}

export default DeleteUserSimple;
```

Bu kadar basit! Gördüğün gibi, `axios.delete(url)` fonksiyonu `axios.get(url)` gibi sadece URL alıyor, çünkü bir veri göndermiyoruz, sadece bir silme komutu veriyoruz.

---

### **3. Pratik Uygulama: Listeden Bir Öğeyi Silme**

Gerçek dünyada, genellikle bir listeden bir öğeyi silmek isteriz. Şimdi `GET` ve `DELETE` işlemlerini birleştiren daha gerçekçi bir örnek yapalım.

Bu component, önce tüm kullanıcıları listeleyecek ve her kullanıcının yanında bir "Sil" butonu olacak.

JavaScript

```java
// src/components/UserListWithDelete.jsx
import { useState, useEffect } from 'react';
import axios from 'axios';

function UserListWithDelete() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // 1. Başlangıçta tüm kullanıcıları çekiyoruz (GET isteği)
  useEffect(() => {
    axios.get('https://jsonplaceholder.typicode.com/users')
      .then(response => {
        setUsers(response.data);
        setLoading(false);
      })
      .catch(error => {
        setError(error.message);
        setLoading(false);
      });
  }, []);

  // 2. Silme işlemini yönetecek fonksiyon
  const handleDelete = (id) => {
    // API'ye silme isteği gönderiyoruz
    axios.delete(`https://jsonplaceholder.typicode.com/users/${id}`)
      .then(response => {
        console.log(`Kullanıcı ${id} silindi.`);
        
        // 3. Arayüzü Güncelleme: Silinen kullanıcıyı listeden çıkarıyoruz.
        // En iyi pratik, tüm listeyi yeniden çekmek yerine,
        // mevcut state'ten silinen elemanı çıkarmaktır.
        setUsers(prevUsers => prevUsers.filter(user => user.id !== id));
      })
      .catch(error => {
        console.error(`Kullanıcı ${id} silinirken hata oluştu!`, error);
        alert('Silme işlemi sırasında bir hata oluştu.');
      });
  };

  if (loading) return <div>Yükleniyor...</div>;
  if (error) return <div>Hata: {error}</div>;

  return (
    <div>
      <h2>Kullanıcı Listesi ve Silme İşlemi</h2>
      <ul>
        {users.map(user => (
          <li key={user.id}>
            {user.name}
            <button onClick={() => handleDelete(user.id)} style={{ marginLeft: '10px' }}>
              Sil
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default UserListWithDelete;
```

**Bu Örnekteki Kilit Nokta:**`handleDelete` fonksiyonunun içindeki `.then` bloğunda yer alan `setUsers` çağrısıdır.
`setUsers(prevUsers => prevUsers.filter(user => user.id !== id));`
Bu satır, React'in gücünü gösterir:

- `prevUsers`, `users` state'inin o anki halidir.
- `.filter()` metodu, bu dizi içinde bir döngü çalıştırır.
- `user.id !== id` koşulu, ID'si bizim sildiğimiz ID'ye eşit *olmayan* tüm kullanıcıları tutar.
- Sonuç olarak, silinen kullanıcı hariç herkesin olduğu **yeni bir dizi** oluşturulur ve state bu yeni dizi ile güncellenir. Bu, ekranın anında güncellenmesini sağlar.

---