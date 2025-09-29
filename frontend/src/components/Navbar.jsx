import React from "react";
import { useKeycloak } from "@react-keycloak/web";

const Navbar = () => {
  const { keycloak } = useKeycloak();

  return (
    <nav
      style={{
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        padding: "1rem",
        background: "#f0f0f0",
        borderBottom: "1px solid #ccc",
      }}
    >
      <h1>Task Tracker</h1>
      <div>
        {!keycloak.authenticated && (
          <button type="button" onClick={() => keycloak.login()}>
            Giriş Yap
          </button>
        )}

        {keycloak.authenticated && (
          <div>
            <span style={{ marginRight: "1rem" }}>
              Hoş geldin, {keycloak.tokenParsed?.preferred_username}
            </span>
            <button type="button" onClick={() => keycloak.logout()}>
              Çıkış Yap
            </button>
          </div>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
