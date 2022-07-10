import React from "react";
import { useKeycloak } from "@react-keycloak/web";
//import AuthrizedElement from "../components/AuthrizedElement";

const Advertisement = () => {
  const [keycloak, initialized] = useKeycloak();

  return (
    <div>
      <h1>Advertisement Page</h1>
    </div>
  );
};
export default Advertisement;
