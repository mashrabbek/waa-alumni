import React, { useState } from "react";

import { Modal, Form, Input, Button, Select } from "antd";
// import DropdownMultiselect from "react-multiselect-dropdown-bootstrap";

const { Option } = Select;
const tag_array = [];

for (let i = 10; i < 36; i++) {
  tag_array.push(
    <Option key={i.toString(36) + i}>{i.toString(36) + i}</Option>
  );
}

const AddJob = () => {
  const [isModalVisible, setIsModalVisible] = useState(false);

  const showModal = () => {
    setIsModalVisible(true);
  };

  const handleOk = () => {
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  let historyInit = {
    companyName: "",
    reasonToLeave: "",
    tags: [],
  };

  const [size, setSize] = useState("middle");
  const [jobHistory, setJobHistory] = useState(historyInit);

  const jobOnChangeSelect = (e) => {
    setJobHistory({ ...jobHistory, tags: e });
  };

  return (
    <>
      <Button className="btn btn-primary add-btn" type="primary" onClick={showModal}>
        Add
      </Button>
      <Modal
        title="Add"
        visible={isModalVisible}
        onOk={handleOk}
        onCancel={handleCancel}
        width={1000}
      >
        <Form
          labelCol={{ span: 4 }}
          wrapperCol={{ span: 14 }}
          layout="horizontal"
        //   onValuesChange={onFormLayoutChange}
        >
          <div className="Auth-form-content">
            <div className="form-group mt-3 text-left">
              <label>Description:</label>
              <input
                type="text"
                required
                className="form-control mt-1"
                placeholder="please input your description"
              />
            </div>
            <div className="form-group mt-3 text-left">
              <label>Benifit:</label>
              <input
                type="text"
                required
                className="form-control mt-1"
                placeholder="please input your benifit"
              />
            </div>
            <div className="form-group mt-3 text-left">
              <label>Tags:</label>
              <Select
                mode="tags"
                name="tags"
                size={size}
                placeholder="Please select tags"
                defaultValue={["a10", "c12"]}
                onChange={jobOnChangeSelect}
                style={{
                  width: "100%",
                }}
              >
                {tag_array}
              </Select>
            </div>
            <div className="form-group mt-3 text-left">
              <label>file:</label>
              <input
                type="file"
                multiple
                className="form-control mt-1"
              />
            </div>
          </div>
        </Form>
      </Modal>
    </>
  );
};

export default AddJob;
