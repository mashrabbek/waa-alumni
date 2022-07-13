import React, { useState, useEffect } from "react";
import { Form, FormGroup, Label, Input, Container, Button } from "reactstrap";

import jsonwebtoken from "jsonwebtoken";
import axios from "axios";

const FacultyForm = ({ keycloak }) => {
  let token = jsonwebtoken.decode(keycloak.token);

  let majors = [
    { label: "Compro", id: 1 },
    { label: "MSD", id: 2 },
  ];

  let [facDetails, setFacDetails] = useState({
    departmentId: 1,
    state: "",
    city: "",
    zip: "",
  });

  async function fetchFacDetails() {
    let res = await axios.get(
      `${process.env.REACT_APP_BACKEND_BASE_URL}/users/faculty/${token.preferred_username}`,
      {
        headers: {
          //todo auth
        },
      }
    );
    console.log(res.data);
    if (res.data) {
      //console.log(res.data);
      // console.log({ address: JSON.parse(res.data.address) });
      let address = res.data.address ? res.data.address : {};
      setFacDetails({ ...res.data, ...address });
      // setSelectedFile(res.data.cv);
    }
  }

  useEffect(() => {
    fetchFacDetails();
  }, []);

  const handleChange = (e) => {
    setFacDetails({ ...facDetails, [e.target.name]: e.target.value });
  };

  const handleSaveFacForm = async () => {
    try {
      let facData = {
        username: token.preferred_username,
        departmentId: facDetails.departmentId,
        address: {
          city: facDetails.city,
          state: facDetails.state,
          zip: facDetails.zip,
        },
      };

      console.log(facData);

      let res = await axios.post(
        `${process.env.REACT_APP_BACKEND_BASE_URL}/users/faculty`,
        facData,
        {
          headers: {
            // "Access-Control-Allow-Origin": "*",
            // "Content-Type": "multipart/form-data",
            // Authorization: `Bearer  ${keycloak.token}`,
          },
        }
      );
      // console.log(res);
      // console.log(res.data);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div>
      <Container>
        <Form>
          {/* <Label>Extra Details</Label> */}

          <FormGroup>
            <Input
              bsSize="md"
              className="mb-3"
              type="select"
              name="departmentId"
              value={facDetails.departmentId}
              onChange={handleChange}
            >
              {majors.map((major) => (
                <option key={major.id} value={major.id}>
                  {major.label}
                </option>
              ))}
            </Input>
          </FormGroup>

          <Label>Adress</Label>
          <FormGroup>
            <Input
              id="state"
              name="state"
              placeholder="State"
              type="text"
              value={facDetails && facDetails.state}
              onChange={handleChange}
            />
            <Input
              id="city"
              name="city"
              placeholder="City"
              type="text"
              value={facDetails && facDetails.city}
              onChange={handleChange}
            />
            <Input
              id="zip"
              name="zip"
              placeholder="Zip"
              type="number"
              value={facDetails && facDetails.zip}
              onChange={handleChange}
            />
          </FormGroup>

          <Button onClick={handleSaveFacForm}>Save</Button>
        </Form>
      </Container>
    </div>
  );
};

export default FacultyForm;
