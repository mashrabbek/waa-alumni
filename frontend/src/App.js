import logo from "./logo.svg";
import "bootstrap/dist/css/bootstrap.min.css";
import "antd/dist/antd.css";
import "./chat.css"

import "./App.css";
import React, { StrictMode } from "react";
import { AppRouter } from "./containers/AppRouter";
import { ReactKeycloakProvider } from "@react-keycloak/web";
import keycloak from "./Keycloak";
import HomePage from "./pages/Home";

function App() {
  return (
    <div>
      <ReactKeycloakProvider
        initOptions={{ onLoad: "login-required", promiseType: "native" }}
        authClient={keycloak}
      >
        <div className="App wrapper">
          <AppRouter></AppRouter>
        </div>
      </ReactKeycloakProvider>
    </div>
  );
}

export default App;
