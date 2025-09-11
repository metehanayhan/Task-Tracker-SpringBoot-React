# Axios ile PUT İsteği

### **Veriyi `PUT` İsteği ile Güncelleme**

### **Adım 1: Basit Bir Güncelleme Formu Oluşturalım**

Bu formda, hangi kullanıcıyı güncelleyeceğimizi belirtmek için bir "ID" alanı ve yeni bilgiler için "İsim" ve "Email" alanları olacak. Bir önceki dersteki gibi, input'lara sadece `name` özelliği vereceğiz.

JavaScript

```java
// src/components/EditUserFormSimple.jsx
import axios from 'axios';

function EditUserFormSimple() {

  const handleUpdate = (event) => {
    // Form gönderildiğinde bu fonksiyon çalışacak
  };

  return (
    <form onSubmit={handleUpdate}>
      <h2>Kullanıcı Güncelle (Basit)</h2>
      <div>
        <label>Güncellenecek Kullanıcı ID:</label>
        <input type="number" name="userId" required />
      </div>
      <div>
        <label>Yeni İsim:</label>
        <input type="text" name="userName" required />
      </div>
      <div>
        <label>Yeni Email:</label>
        <input type="email" name="userEmail" required />
      </div>
      <button type="submit">Güncelle</button>
    </form>
  );
}

export default EditUserFormSimple;
```

### **Adım 2: Form Gönderildiğinde Veriyi Alalım ve Gönderelim**

Şimdi `handleUpdate` fonksiyonunu yazalım. Bu fonksiyon, formdaki ID, isim ve e-posta değerlerini alıp `axios.put` ile doğru adrese gönderecek.

JavaScript

```java
const handleUpdate = (event) => {
  // 1. Sayfanın yenilenmesini engelle
  event.preventDefault();

  // 2. Formdaki verilere "name" attribute'ları üzerinden eriş
  const form = event.target;
  const id = form.elements.userId.value;
  const name = form.elements.userName.value;
  const email = form.elements.userEmail.value;

  // ID olmadan PUT isteği atamayız, bu yüzden bir kontrol ekleyelim.
  if (!id) {
    alert('Lütfen güncellenecek kullanıcı ID girin!');
    return;
  }

  // 3. Sunucuya göndereceğimiz güncel veri objesini oluşturalım.
  const updatedUser = {
    id: id, // JSONPlaceholder bazen ID'yi gövdede de bekler
    name: name,
    email: email,
  };
  
  // 4. Axios ile PUT isteği gönderelim. URL'nin sonuna ID'yi eklediğimize dikkat et!
  axios.put(`https://jsonplaceholder.typicode.com/users/${id}`, updatedUser)
    .then(response => {
      // BAŞARILI DURUMU
      console.log('Sunucudan gelen cevap:', response.data);
      alert(`ID: ${id} olan kullanıcı başarıyla güncellendi!`);
      form.reset();
    })
    .catch(error => {
      // HATA DURUMU
      console.error('Güncelleme sırasında hata!', error);
      alert('Güncelleme sırasında bir hata oluştu.');
    });
};
```

---

### **Tam ve Basit Kod (`EditUserFormSimple.jsx`)**

İşte her şeyin bir arada olduğu, en basit haliyle `PUT` isteği:

JavaScript

```java
import axios from 'axios';

function EditUserFormSimple() {

  const handleUpdate = (event) => {
    event.preventDefault();

    const form = event.target;
    const id = form.elements.userId.value;
    const name = form.elements.userName.value;
    const email = form.elements.userEmail.value;

    if (!id) {
      alert('Lütfen güncellenecek kullanıcı ID girin!');
      return;
    }

    const updatedUser = { id, name, email };

    // DİKKAT: URL'nin sonunda /${id} var. Bu, hangi kullanıcıyı güncelleyeceğimizi belirtir.
    const url = `https://jsonplaceholder.typicode.com/users/${id}`;

    axios.put(url, updatedUser)
      .then(response => {
        console.log('Güncelleme başarılı:', response.data);
        alert(`ID: ${response.data.id} olan kullanıcının yeni adı: ${response.data.name}`);
        form.reset();
      })
      .catch(error => {
        console.error('Güncelleme hatası:', error);
        alert('Bir hata oluştu, kullanıcı güncellenemedi.');
      });
  };

  return (
    <form onSubmit={handleUpdate}>
      <h2>Kullanıcı Güncelle (Basit)</h2>
      <div>
        <label>Güncellenecek Kullanıcı ID:</label>
        <input type="number" name="userId" placeholder="Örn: 1" required />
      </div>
      <div>
        <label>Yeni İsim:</label>
        <input type="text" name="userName" required />
      </div>
      <div>
        <label>Yeni Email:</label>
        <input type="email" name="userEmail" required />
      </div>
      <button type="submit">Güncelle</button>
    </form>
  );
}

export default EditUserFormSimple;
```

### **Özet ve `POST` ile Farkı**

Gördüğün gibi `PUT` isteği, `POST` isteğine çok benziyor. Aradaki **en kritik fark** şudur:

- **POST:** Yeni bir kaynak oluşturur. URL'de ID belirtmezsin (`/users`).
- **PUT:** Mevcut bir kaynağı günceller. Hangi kaynağı güncelleyeceğini belirtmek için URL'nin sonuna **mutlaka ID eklersin** (`/users/5`).

---