import React, { useState } from "react";

import { Modal, Form, Input, Button, Select, DatePicker } from "antd";
import moment from "moment";

const { Option } = Select;
const tag_array = [];

for (let i = 10; i < 36; i++) {
  tag_array.push(
    <Option key={i.toString(36) + i}>{i.toString(36) + i}</Option>
  );
}

const { TextArea } = Input;

const AddJobHistory = () => {
  const [isModalVisible, setIsModalVisible] = useState(false);

  const showModal = () => {
    setIsModalVisible(true);
  };

  const handleOk = () => {
    // setIsModalVisible(false);
    let jsonForSending = {
      companyName: jobHistory.companyName,
      startDate: formatDate(historyDates.startDate),
      endDate: formatDate(historyDates.endDate),
      reasonToLeave: jobHistory.reasonToLeave,
      tags: jobHistory.tags,
    };
    console.log(jsonForSending);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  let historyInit = {
    companyName: "",
    reasonToLeave: "",
    tags: [],
  };

  function formatDate(date) {
    var d = new Date(date),
      month = "" + (d.getMonth() + 1),
      day = "" + d.getDate(),
      year = d.getFullYear();

    if (month.length < 2) month = "0" + month;
    if (day.length < 2) day = "0" + day;
    return [year, month, day].join("-");
  }

  const [jobHistory, setJobHistory] = useState(historyInit);
  const [historyDates, setHistoryDates] = useState({
    startDate: "",
    endDate: "",
  });
  const jobOnChange = (e) => {
    setJobHistory({ ...jobHistory, [e.target.name]: e.target.value });
  };

  const jobOnChangeSelect = (e) => {
    setJobHistory({ ...jobHistory, tags: e });
  };

  const OnChangeStartDate = (e) =>
    setHistoryDates({ ...historyDates, startDate: e });
  const OnChangeEndDate = (e) =>
    setHistoryDates({ ...historyDates, endDate: e });

  const [size, setSize] = useState("middle");

  const handleSizeChange = (e) => {
    setSize(e.target.value);
  };

  const dateFormat = "YYYY-MM-DD";

  return (
    <>
      <Button type="primary" onClick={showModal}>
        Add Job Experience
      </Button>
      <Modal
        title="Basic Modal"
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
          <Form.Item label="Company Name">
            <Input
              value={jobHistory.companyName}
              name="companyName"
              onChange={jobOnChange}
            />
          </Form.Item>
          <Form.Item label="Start Date">
            <DatePicker
              name="startDate"
              value={historyDates.startDate}
              onChange={OnChangeStartDate}
              format={dateFormat}
            />
          </Form.Item>
          <Form.Item label="End Date">
            <DatePicker
              name="endDate"
              value={historyDates.endDate}
              onChange={OnChangeEndDate}
              format={dateFormat}
            />
          </Form.Item>
          {/* reason */}
          <Form.Item label="Reason to leave">
            <TextArea
              rows={4}
              name="reasonToLeave"
              value={jobHistory.reasonToLeave}
              onChange={jobOnChange}
            />
          </Form.Item>

          <Form.Item label="Tags">
            <Select
              mode="tags"
              name="tags"
              size={size}
              placeholder="Please select"
              defaultValue={["a10", "c12"]}
              onChange={jobOnChangeSelect}
              style={{
                width: "100%",
              }}
            >
              {tag_array}
            </Select>
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default AddJobHistory;
