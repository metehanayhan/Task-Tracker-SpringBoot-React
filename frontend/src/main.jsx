import React from "react"; 
import { createRoot } from "react-dom/client";
import App from "./App.jsx";
import { ReactKeycloakProvider } from "@react-keycloak/web"; 
import keycloak from "./keycloak"; 

createRoot(document.getElementById("root")).render(
 
  <ReactKeycloakProvider authClient={keycloak}>
    <React.StrictMode>
      <App />
    </React.StrictMode>
  </ReactKeycloakProvider>
 
);
