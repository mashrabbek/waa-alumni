import React, { useState, useEffect } from "react";
import {
  Form,
  FormGroup,
  Label,
  Input,
  Container,
  Row,
  Col,
  Button,
  FormText,
  ListGroupItem,
} from "reactstrap";

import jsonwebtoken from "jsonwebtoken";
import axios from "axios";

const StudendForm = ({ keycloak }) => {
  let token = jsonwebtoken.decode(keycloak.token);

  let majors = [
    { label: "Compro", id: 1 },
    { label: "MSD", id: 2 },
  ];

  let [extraDetails, setExtraDetails] = useState({
    majorId: 1,
    gpa: "",
  });

  let [addressDetails, setAddressDetails] = useState({
    state: "",
    city: "",
    zip: "",
  });

  const [selectedFile, setSelectedFile] = useState({
    url: "",
    file: null,
  });

  async function getExtraDetails() {
    let res = await axios.get(
      `${process.env.REACT_APP_BACKEND_BASE_URL}/users/student/${token.preferred_username}`,
      {
        headers: {
          //todo auth
        },
      }
    );
    console.log(res.data);
    if (res.data) {
      // console.log({ address: JSON.parse(res.data.address) });
      setExtraDetails(res.data);
      setAddressDetails(JSON.parse(res.data.address));
      setSelectedFile({ url: res.data.cv });
      // setSelectedFile(res.data.cv);
    }
  }

  useEffect(() => {
    getExtraDetails();
  }, []);

  const handleChange = (e) => {
    setExtraDetails({ ...extraDetails, [e.target.name]: e.target.value });
  };
  const handleAddressChange = (e) => {
    setAddressDetails({ ...addressDetails, [e.target.name]: e.target.value });
  };

  const handleFileChange = async (e) => {
    setSelectedFile({ file: e.target.files[0] });
  };

  const handleSaveStudentForm = async () => {
    const formData = new FormData();
    formData.append("username", token.preferred_username);
    formData.append("majorId", extraDetails.majorId);
    formData.append("gpa", extraDetails.gpa);
    if (selectedFile.file) formData.append("file", selectedFile.file);
    formData.append("address", JSON.stringify(addressDetails));

    //console.log(JSON.stringify(addressDetails));

    for (let [key, val] of formData.entries()) {
      console.log({ key, val });
    }

    try {
      let res = await axios.put(
        `${process.env.REACT_APP_BACKEND_BASE_URL}/users/student/${token.preferred_username}`,
        formData,
        {
          headers: {
            // "Access-Control-Allow-Origin": "*",
            "Content-Type": "multipart/form-data",
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
              name="majorId"
              value={extraDetails.majorId}
              onChange={handleChange}
            >
              {majors.map((major) => (
                <option key={major.id} value={major.id}>
                  {major.label}
                </option>
              ))}
            </Input>
          </FormGroup>
          <FormGroup>
            <Input
              id="gpa"
              name="gpa"
              placeholder="Gpa"
              type="number"
              value={extraDetails.gpa}
              onChange={handleChange}
            />
          </FormGroup>
          <FormGroup>
            <Input
              id="cvFile"
              name="file"
              type="file"
              onChange={handleFileChange}
            />
            <ListGroupItem action href={selectedFile.url} tag="a">
              {selectedFile.url}
            </ListGroupItem>
            <FormText>Upload Resume</FormText>
          </FormGroup>

          <Label>Adress</Label>
          <FormGroup>
            <Input
              id="state"
              name="state"
              placeholder="State"
              type="text"
              value={addressDetails&&addressDetails.state}
              onChange={handleAddressChange}
            />
            <Input
              id="city"
              name="city"
              placeholder="City"
              type="text"
              value={addressDetails&&addressDetails.city}
              onChange={handleAddressChange}
            />
            <Input
              id="zip"
              name="zip"
              placeholder="Zip"
              type="number"
              value={addressDetails&&addressDetails.zip}
              onChange={handleAddressChange}
            />
          </FormGroup>

          <Button onClick={handleSaveStudentForm}>Save</Button>
        </Form>
      </Container>
    </div>
  );
};

export default StudendForm;
