import React from "react";
// import { useKeycloak } from "@react-keycloak/web";
//import AuthrizedElement from "../components/AuthrizedElement";
import { Form, FormGroup, Label, Input } from "reactstrap";
const HomePage = ({ keycloak }) => {
  // const [keycloak, initialized] = useKeycloak();

  return (
    <div>
      <h1>Home Page</h1>
      {/* <strong>Anyone can access this page</strong> */}
      <Input
        id="exampleText"
        name="text"
        type="textarea"
        value={keycloak.token}
      />
      {/* <textarea width="150">{keycloak.token}</textarea> */}
      {/* {initialized ? (
        keycloak.authenticated && (
          <pre>{JSON.stringify(keycloak, undefined, 2)}</pre>
        )
      ) : (
        <h2>keycloak initializing ....!!!!</h2>
      )} */}
    </div>
  );
};
export default HomePage;
