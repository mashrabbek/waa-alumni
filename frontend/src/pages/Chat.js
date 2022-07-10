import React from "react";
import { useKeycloak } from "@react-keycloak/web";
//import AuthrizedElement from "../components/AuthrizedElement";

const Chat = () => {
  const [keycloak, initialized] = useKeycloak();

  return (
    <div>
      <h1>Chat Page</h1>
    </div>
  );
};
export default Chat;
