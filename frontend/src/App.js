import logo from "./logo.svg";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import React from "react";
import { AppRouter } from "./containers/AppRouter";
import { ReactKeycloakProvider } from "@react-keycloak/web";
import keycloak from "./Keycloak";

function App() {
  return (
    <ReactKeycloakProvider authClient={keycloak}>
      <div className="App wrapper">{/* <AppRouter /> */}</div>
    </ReactKeycloakProvider>
  );
}

export default App;
