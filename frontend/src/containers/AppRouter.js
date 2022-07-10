import { useKeycloak } from "@react-keycloak/web";

import React, { useState } from "react";
import SideBar from "./SideBar";
import Content from "./Content";
import { BrowserRouter as Router } from "react-router-dom";

export const AppRouter = () => {
  const [sidebarIsOpen, setSidebarOpen] = useState();
  const { keycloak, initialized } = useKeycloak();
  if (!initialized) {
    return <h3>Loading ... !!!</h3>;
  }

  const toggleSidebar = () => setSidebarOpen(!sidebarIsOpen);

  return (
    <>
      <Router>
        <SideBar toggle={toggleSidebar} isOpen={sidebarIsOpen} />
        <Content toggleSidebar={toggleSidebar} sidebarIsOpen={sidebarIsOpen} />
      </Router>
    </>
  );
};
