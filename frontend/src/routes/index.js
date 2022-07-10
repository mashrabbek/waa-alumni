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
  // const { keycloak, initialized } = useKeycloak();
  // if (!initialized) {
  //   return <h3>Loading ... !!!</h3>;
  // }
  return (
    <>
      {/* <Menu keycloak={keycloak} keycloakInitialized={initialized} /> */}
      <BrowserRouter>
        <Routes>
          <Route exact path="/" component={HomePage} />
          <Route path="/" component={Profile} />
          <Route path="/" component={Student} />
          <Route path="/" component={Advertisement} />
          <Route path="/" component={Employee} />
          <Route path="/" component={Chat} />
          {/* <PrivateRoute
            roles={["RealmAdmin"]}
            path="/protected"
            component={ProtectedPage}
          /> */}
        </Routes>
      </BrowserRouter>
    </>
  );
};

//export default withKeycloak(PageRoutes);
export default PageRoutes;
