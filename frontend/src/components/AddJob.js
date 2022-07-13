import React, { useState, useEffect } from "react";
import {
  FormGroup,
  FormText,
} from "reactstrap";
import { Modal, Form, Input, Button, Select } from "antd";
import axios from "axios";
import jsonwebtoken from "jsonwebtoken";
// import DropdownMultiselect from "react-multiselect-dropdown-bootstrap";

const { Option } = Select;
const tag_array = [];

const AddJob = ({ keycloak }) => {
  let token = jsonwebtoken.decode(keycloak.token);
  async function getTags() {
    let res = await axios.get(`${process.env.REACT_APP_BACKEND_BASE_URL}/tag`, {
      headers: {
        //todo auth
      },
    });
    console.log(res.data);

    for (let ob of res.data) {
      tag_array.push(<Option key={ob.id}>{ob.name}</Option>);
    }
  }

  // const [id, setId] = useState(id.props.id);
  const [isModalVisible, setIsModalVisible] = useState(false);

  const showModal = (e) => {
    console.log(e);
    setIsModalVisible(true);
  };

  const [selectedFile, setSelectedFile] = useState([{
    url: "",
    file: null,
  }]);
  const handleFileChange = async (e) => {
    setSelectedFile({ file: e.target.files[0] });
  };
  const handleOk = async () => {
    const formData = new FormData();
    formData.append("ownerUsername", token.preferred_username);
    formData.append("description", adJob.description);
    formData.append("benefits", adJob.benefits);
    if (selectedFile.file) formData.append("files", [selectedFile.file]);
    formData.append("tagIds", adJob.tags);


    for (let [key, val] of formData.entries()) {
      console.log({ key, val });
    }

    try {
      let res = await axios.post(
        `${process.env.REACT_APP_BACKEND_BASE_URL}/jobAd`,
        formData,
        {
          headers: {
            // "Access-Control-Allow-Origin": "*",
            "Content-Type": "multipart/form-data",
            // Authorization: `Bearer  ${keycloak.token}`,
          },
        }
      );
    } catch (error) {
      console.error(error);
    }
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  let adInit = {
    description: "",
    benefits: "",
    tags: "",
  };

  const [size, setSize] = useState("middle");
  const [adJob, setAdJob] = useState(adInit);

  const adChange = (e) => {
    setAdJob({ ...adJob, [e.target.name]: e.target.value });
  };

  const adChangeSelect = (e) => {
    setAdJob({ ...adJob, tags: e });
  };

  useEffect(() => {
    getTags();
  }, []);

  return (
    <>
      <Button
        className="btn btn-primary add-btn"
        type="primary"
        onClick={showModal}
      >
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
        >
          <Form.Item label="Description">
            <Input
              value={adJob.description}
              name="description"
              onChange={adChange}
            />
          </Form.Item>
          <Form.Item label="Benefit">
            <Input value={adJob.benefits} name="benefits" onChange={adChange} />
          </Form.Item>

          <Form.Item label="Tags">
            <Select
              mode="tags"
              name="tags"
              size={size}
              placeholder="Please select"
              defaultValue={[]}
              onChange={adChangeSelect}
              style={{
                width: "100%",
              }}
            >
              {tag_array}
            </Select>
          </Form.Item>
          <FormGroup>
            <Input
              id="cvFile"
              name="file"
              type="file"
              onChange={handleFileChange}
            />
            <FormText>Upload Resume</FormText>
          </FormGroup>
        </Form>
      </Modal>
    </>
  );
};

export default AddJob;
