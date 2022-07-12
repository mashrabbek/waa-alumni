import React, { useState, useEffect } from "react";
import Credential from "../components/Credential";
import StudendForm from "../components/StudentForm";
import { Container, Row, Col } from "reactstrap";
import JobHistory from "../components/JobHistory";
import { Space, Button, Typography } from "antd";
import AddJobHistory from "../components/AddJobHistory";

const { Title } = Typography;

const Profile = ({ keycloak }) => {
  // todo globolize keykloak and decoded token

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
          <AddJobHistory></AddJobHistory>
          <JobHistory />
        </Col>
      </Row>
    </Container>
  );
};

export default Profile;
