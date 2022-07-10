import React from "react";
import classNames from "classnames";
import { Container } from "reactstrap";
import { Routes, Route } from "react-router-dom";

import TopBar from "./TopBar";
import PageRoutes from "../routes";

const Content = ({ sidebarIsOpen, toggleSidebar }) => (
  <Container
    fluid
    className={classNames("content", { "is-open": sidebarIsOpen })}
  >
    <TopBar toggleSidebar={toggleSidebar} />
    {/* Content */}
    <PageRoutes />
  </Container>
);

export default Content;
