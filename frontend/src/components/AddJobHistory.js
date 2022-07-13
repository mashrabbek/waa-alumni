import React, { useState, useEffect } from "react";

import { Modal, Form, Input, Button, Select, DatePicker, Space, Table, Tag } from "antd";
import axios from "axios";
import jsonwebtoken from "jsonwebtoken";

const { Option } = Select;
const tag_array = [];


const { TextArea } = Input;







const AddJobHistory = ({ keycloak }) => {

  const tableColumns = [
    {
      title: "id",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "Company name",
      dataIndex: "companyName",
      key: "companyName",
    },
    {
      title: "Start date",
      dataIndex: "startDate",
      key: "startDate",
    },
    {
      title: "End date",
      dataIndex: "endDate",
      key: "endDate",
    },
    {
      title: "Reason to leave",
      dataIndex: "reasonToLeave",
      key: "reasonToLeave",
    },
    {
      title: "Tags",
      key: "tags",
      dataIndex: "tags",
      render: (_, { tags }) => (
        <>
          {tags.map((tag) => {
            let color = tag.length > 5 ? "geekblue" : "green";
  
            if (tag === "loser") {
              color = "volcano";
            }
  
            return (
              <Tag color={color} key={tag}>
                {tag.toUpperCase()}
              </Tag>
            );
          })}
        </>
      ),
    },
    {
      title: "Action",
      key: "id",
      dataIndex: "id",
      render: (_, { id }) => (
        <>
          <Button danger onClick={()=> deleteHistory(id)} >Delete</Button>
          
        </>
      )
    },
  ];


  let token = jsonwebtoken.decode(keycloak.token);

  const [isModalVisible, setIsModalVisible] = useState(false);
  const [jobHistoryArray, setJobHistoryArray] = useState([]);

  const showModal = () => {
    setIsModalVisible(true);
  };


  const setTableData = async () => {
    try {
      let res = await axios.get(
        `${process.env.REACT_APP_BACKEND_BASE_URL}/history/`,
        {
          headers: {
            "Content-Type": "application/json"
          },
        }
      );
      if(res.data){
        setJobHistoryArray(res.data);
      }
    } catch (error) {
      console.error(error);
    }
  }

  async function deleteHistory(id){
    let res = await axios.delete(
      `${process.env.REACT_APP_BACKEND_BASE_URL}/history/` + id,
      {
        headers: {
          //todo auth
        },
      }
    );
    setTableData();
  }

  async function getTags() {
    let res = await axios.get(
      `${process.env.REACT_APP_BACKEND_BASE_URL}/tag`,
      {
        headers: {
          //todo auth
        },
      }
    );
    console.log(res.data);

    for(let ob of res.data){
      tag_array.push(<Option key={ob.id}>{ob.name}</Option>);
    }
    
  }

  useEffect(() => {
    getTags();
    setTableData();
  }, []);

  

  const handleOk = async () => {
    let jsonForSending = {
      companyName: jobHistory.companyName,
      startDate: formatDate(historyDates.startDate),
      endDate: formatDate(historyDates.endDate),
      reasonToLeave: jobHistory.reasonToLeave,
      tagIds: jobHistory.tags,
    };
    try {
      let res = await axios.post(
        `${process.env.REACT_APP_BACKEND_BASE_URL}/history/${token.preferred_username}`,
        jsonForSending,
        {
          headers: {
            // "Access-Control-Allow-Origin": "*",
            "Content-Type": "application/json",
            // Authorization: `Bearer  ${keycloak.token}`,
          },
        }
      );
      setTableData();
      setIsModalVisible(false);
    } catch (error) {
      console.error(error);
    }
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
              defaultValue={[]}
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

      <Table columns={tableColumns} dataSource={jobHistoryArray} />
    </>
  );
};

export default AddJobHistory;
