import React, { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faHome,
  faBriefcase,
  faPaperPlane,
  faQuestion,
  faImage,
  faCopy,
} from "@fortawesome/free-solid-svg-icons";

import { NavItem, NavLink, Nav } from "reactstrap";
import classNames from "classnames";
import { Link } from "react-router-dom";
import routes from "../routes/routes";
import { useKeycloak } from "@react-keycloak/web";
// import { withKeycloak } from "@react-keycloak/web";
import jsonwebtoken from "jsonwebtoken";

export default function SideBar({ isOpen, toggle }) {
  //
  const { initialized, keycloak } = useKeycloak();

  let [filteredRoutes, setFilteredRoutes] = useState([]);
  let [profileName, setProfileName] = useState("");

  const isAuthorized = (elem) => {
    let roles = elem.roles;
    if (keycloak && roles) {
      return roles.some((r) => {
        const realm = keycloak.hasRealmRole(r);
        const resource = keycloak.hasResourceRole(r);
        return realm || resource;
      });
    }
    return false;
  };

  const filterMenus = (routes) => routes.filter((elem) => isAuthorized(elem));

  const username = () => {
    let decoded = jsonwebtoken.decode(keycloak.token);
    let name =
      !decoded.given_name && !decoded.family_name
        ? decoded.preferred_username
        : decoded.given_name + " " + decoded.family_name;

    //console.log({ name });
    return name;
  };

  useEffect(() => {
    setFilteredRoutes(filterMenus(routes));
    setProfileName(username());
  }, []);

  return (
    <div className={classNames("sidebar", { "is-open": isOpen })}>
      <div className="sidebar-header">
        <span color="info" onClick={toggle} style={{ color: "#fff" }}>
          &times;
        </span>
        <h3>Alumni Portal</h3>
      </div>
      <div className="side-menu">
        <Nav vertical className="list-unstyled pb-3">
          <p>{profileName}</p>
          {routes.map((elem) => {
            return isAuthorized(elem) ? (
              <NavItem key={elem.name}>
                <NavLink tag={Link} to={elem.url}>
                  {/* <FontAwesomeIcon icon={elem.icon} className="mr-2" /> */}
                  {elem.name}
                </NavLink>
              </NavItem>
            ) : null;
          })}
        </Nav>
      </div>
    </div>
  );
}
