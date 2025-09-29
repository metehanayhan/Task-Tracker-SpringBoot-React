import axios from "axios";
import keycloak from "../keycloak"; 

const API_URL = "http://localhost:8080/api/tasks";

// Axios istemcisi
const apiClient = axios.create({
  baseURL: API_URL,
});

// Axios'un "request interceptor" özelliğini ayarlıyoruz
// Bu kod, herhangi bir istek gönderilmeden HEMEN ÖNCE çalışır
apiClient.interceptors.request.use(
  (config) => {
    // Eğer Keycloak ile giriş yapılmışsa (authenticated ise)
    if (keycloak.authenticated) {
      // Her isteğin 'headers' kısmına Authorization bilgisini ekliyoruz..
      config.headers.Authorization = `Bearer ${keycloak.token}`;
    }
    return config;
  },
  (error) => {
    // Bir hata olursa promise'i reject et
    return Promise.reject(error);
  }
);

// Mevcut fonksiyonlarını, artık bizim apiClienti kullanacak şekilde güncelliyoruz
export const getAllTasks = () => {
  return apiClient.get("");
};

export const getTaskById = (id) => {
  return apiClient.get(`/${id}`);
};

export const createTask = (taskData) => {
  return apiClient.post("/", taskData);
};

export const updateTask = (id, taskDetails) => {
  return apiClient.put(`/${id}`, taskDetails);
};

export const deleteTask = (id) => {
  return apiClient.delete(`/${id}`);
};
