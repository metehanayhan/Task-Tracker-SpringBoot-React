import Keycloak from "keycloak-js";

const keycloakConfig = {
  url: "http://localhost:8180", // Docker'da çalıştırdığımız Keycloak adresi
  realm: "task-tracker", // Oluşturduğumuz Realm adı
  clientId: "task-tracker-app", // Oluşturduğumuz Client ID
};

const keycloak = new Keycloak(keycloakConfig);

export default keycloak;
