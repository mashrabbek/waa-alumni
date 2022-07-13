import React, { useState, useEffect } from "react";
import Credential from "../components/Credential";
import StudendForm from "../components/StudentForm";
import { Container, Row, Col } from "reactstrap";
import { Space, Button, Typography } from "antd";
import AddJobHistory from "../components/AddJobHistory";
import FacultyForm from "../components/FacultyForm";
//import jsonwebtoken from "jsonwebtoken";

const { Title } = Typography;

const Profile = ({ keycloak }) => {
  // todo globolize keykloak and decoded token
  // let token = jsonwebtoken.decode(keycloak.token);

  function isStudent() {
    return keycloak.hasRealmRole("student");
  }

  if (isStudent()) {
    return (
      <Container>
        <Row>
          <Col className="bg-light border" xs="12" md="6">
            <Title level={2}>Details</Title>
            <Credential keycloak={keycloak} />
          </Col>
          <Col className="bg-light border" xs="12" md="6">
            <Title level={2}>Extra Details</Title>
            <StudendForm keycloak={keycloak} />
          </Col>
        </Row>
        <Row className="ver-space"></Row>
        <Row>
          <Col className="bg-light border jobHistoryTable">
            <Title level={2}>Job History</Title>
            <AddJobHistory keycloak={keycloak}></AddJobHistory>
          </Col>
        </Row>
      </Container>
    );
  } else {
    return (
      <Container>
        <Row>
          <Col className="bg-light border jobHistoryTable" xs="12" md="6">
            <Title level={2}>Faculty</Title>
            <FacultyForm keycloak={keycloak}></FacultyForm>
          </Col>
        </Row>
      </Container>
    );
  }
};

export default Profile;
