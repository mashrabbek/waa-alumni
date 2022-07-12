import { useKeycloak } from "@react-keycloak/web";

import React from "react";
import { BrowserRouter } from "react-router-dom";
import { Route, Routes } from "react-router";

import Menu from "../components/Menu";
import HomePage from "../pages/Home";
import Profile from "../pages/Profile";
import Student from "../pages/Student";
import Advertisement from "../pages/Advertisement";
import Chat from "../pages/Chat";
import Employee from "../pages/Employee";

//import { withKeycloak } from "@react-keycloak/web";

const PageRoutes = () => {
  const { keycloak, initialized } = useKeycloak();
  if (!initialized) {
    return <h3>Loading ... !!!</h3>;
  }
  return (
    <>
      {/* <Menu keycloak={keycloak} keycloakInitialized={initialized} /> */}
      <Routes>
        <Route path="/" element={<HomePage keycloak={keycloak}></HomePage>} />
        <Route path="/profile" element={<Profile keycloak={keycloak} />} />
        <Route path="/students" element={<Student keycloak={keycloak} />} />
        <Route path="/ads" element={<Advertisement keycloak={keycloak} />} />
        <Route path="/employees" element={<Employee keycloak={keycloak} />} />
        <Route path="/chat" element={<Chat keycloak={keycloak} />} />
        {/* <PrivateRoute
            roles={["RealmAdmin"]}
            path="/protected"
            component={ProtectedPage}
          /> */}
      </Routes>
    </>
  );
};

//export default withKeycloak(PageRoutes);
export default PageRoutes;
