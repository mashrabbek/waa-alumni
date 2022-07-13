import React, { useState, useEffect } from "react";
import { Button as Btn } from "antd";
import { useKeycloak } from "@react-keycloak/web";
import {
  Form,
  FormGroup,
  Label,
  Input,
  Container,
  Row,
  Col,
  Button,
} from "reactstrap";
import axios from "axios";

import jsonwebtoken from "jsonwebtoken";

const Credential = ({ keycloak }) => {
  let initialUserCredentials = {
    firstName: "",
    lastName: "",
  };
  let [userCredentials, setUserCredentials] = useState(initialUserCredentials);
  let token = jsonwebtoken.decode(keycloak.token);

  // todo get form before loading
  //console.log("outside");
  useEffect(() => {
    console.log(token);
    setUserCredentials({
      firstName: token.given_name,
      lastName: token.family_name,
    });
  }, []);

  function handleChange(e) {
    setUserCredentials({ ...userCredentials, [e.target.name]: e.target.value });
  }

  const handleSaveCredential = async () => {
    let EDIT_URL = `${process.env.REACT_APP_KEYCLOAK_API_URL}/${token.sub}`;
    try {
      let res = await axios.put(`${EDIT_URL}`, userCredentials, {
        headers: {
          // "Access-Control-Allow-Origin": "*",
          Authorization: `Bearer  ${keycloak.token}`,
        },
      });
      if (res.status != 204) {
        console.error(res.status + " : " + res.statusText);
      }
      //todo alert success
      console.log("SUCCESS");
    } catch (err) {
      console.error(err);
      // todo alert error
    }
  };


  const handleKeycloakCredential = ()=>{
     const url = `${process.env.REACT_APP_KEYCLOAK_PROFILE_URL}`;
      window.open(url, '_blank');
  }

  return (
    <div>
      <Container>
        <Form>
          {/* <Label>Details</Label> */}

          <FormGroup>
            {/* <Label for="firstName">Email</Label> */}
            <Input
              id="firstName"
              name="firstName"
              placeholder="First Name"
              type="text"
              value={userCredentials.firstName}
              onChange={handleChange}
            />
          </FormGroup>

          <FormGroup>
            {/* <Label for="firstName">Email</Label> */}
            <Input
              id="lastName"
              name="lastName"
              placeholder="Last Name"
              value={userCredentials.lastName}
              onChange={handleChange}
              type="text"
            />
          </FormGroup>

          <Button onClick={handleSaveCredential}>Save</Button>
        </Form>

        <Form>
          <br></br>
        <Btn type="primary" onClick={handleKeycloakCredential}>
           Change Password/Two-Factor Authentication
        </Btn>
        </Form>

      </Container>
    </div>
  );
};

export default Credential;
