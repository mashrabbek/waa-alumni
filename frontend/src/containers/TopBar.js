import React, { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faAlignLeft } from "@fortawesome/free-solid-svg-icons";
import {
  Navbar,
  Button,
  NavbarToggler,
  Collapse,
  Nav,
  NavItem,
  NavLink,
} from "reactstrap";
import { Link } from "react-router-dom";
import { useKeycloak } from "@react-keycloak/web";

const TopBar = ({ toggleSidebar }) => {
  const [topbarIsOpen, setTopbarOpen] = useState(true);
  const toggleTopbar = () => setTopbarOpen(!topbarIsOpen);
  const { keycloak, initialized } = useKeycloak();

  return (
    <Navbar
      color="light"
      light
      className="navbar shadow-sm p-3 mb-5 bg-white rounded"
      expand="md"
    >
      <Button color="info" onClick={toggleSidebar}>
        <FontAwesomeIcon icon={faAlignLeft} />
      </Button>
      {/* <NavbarToggler onClick={toggleTopbar} /> */}
      {/* <Collapse isOpen={topbarIsOpen} navbar> */}
      <Nav className="ms-auto" navbar>
        <Button onClick={() => keycloak.logout()}>Logout</Button>
      </Nav>
      {/* </Collapse> */}
    </Navbar>
  );
};

export default TopBar;
