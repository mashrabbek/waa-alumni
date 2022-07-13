import React, { useState, useEffect } from "react";

import { Modal, Form, Input, List, Button, Select, Typography } from "antd";
import axios from "axios";
// import DropdownMultiselect from "react-multiselect-dropdown-bootstrap";
const ViewJobApps = ({ ad }) => {
  //   async function getTags() {
  //     let res = await axios.get(`${process.env.REACT_APP_BACKEND_BASE_URL}/tag`, {
  //       headers: {
  //         //todo auth
  //       },
  //     });

  //   }

  const [isModalVisible, setIsModalVisible] = useState(false);
  const [appData, setAppData] = useState([{ cv: "url", username: "kimdr" }]);

  const showModal = (e) => {
    console.log(e);
    setIsModalVisible(true);
  };

  const handleOk = () => {};

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  //const [size, setSize] = useState("middle");

  async function fetchApplicants() {
    try {
      let res = await axios.get(
        `${process.env.REACT_APP_BACKEND_BASE_URL}/jobAd/id/${ad.id}`
      );
      console.log(res.data);
      if (res.data) setAppData(res.data);
    } catch (err) {
      console.error(err);
    }
  }

  useEffect(() => {
    fetchApplicants();
  }, []);

  return (
    <>
      <Button
        className="btn btn-primary add-btn"
        type="primary"
        onClick={showModal}
      >
        View
      </Button>
      <Modal
        title="View"
        visible={isModalVisible}
        onOk={handleOk}
        onCancel={handleCancel}
        width={1000}
      >
        <List
          header={<div>Applied</div>}
          dataSource={appData}
          renderItem={(item) => (
            <List.Item>
              <Typography.Text mark>[{item.username}]</Typography.Text>{" "}
              <a href={item.cv}>{item.cv}</a>
            </List.Item>
          )}
        />
      </Modal>
    </>
  );
};

export default ViewJobApps;
