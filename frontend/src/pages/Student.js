import { Space, Table, Tag, Button, Modal } from "antd";
import axios from "axios";
import React, { useEffect, useState } from "react";
import ModalViewStudent from "../components/ModalViewStudent";

const Student = ({ keycloak }) => {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [currentStudent, setCurrentStudent] = useState({});

  const columns = [
    {
      title: "Major",
      dataIndex: "major",
      key: "major",
    },
    {
      title: "User Name",
      dataIndex: "username",
      key: "username",
    },
    {
      title: "Gpa",
      dataIndex: "gpa",
      key: "gpa",
    },
    {
      title: "State",
      dataIndex: "state",
      key: "state",
    },
    {
      title: "City",
      dataIndex: "city",
      key: "city",
    },
    {
      title: "Action",
      dataIndex: "action",
      key: "action",
      render: (_, student) => (
        <Space size="middle">
          <Button onClick={() => handleView(student)}>View</Button>
        </Space>
      ),
    },
  ];
  // const data = [
  //   {
  //     username: "username",
  //     address: '{"state":"iowa","city":"fairfield","zip":"52557"}',
  //     majorId: 1,
  //     gpa: 3.3,
  //     cv: "url1",
  //   },
  //   {
  //     username: "username2",
  //     address: '{"state":"iowa","city":"fairfield2","zip":"52557"}',
  //     majorId: 2,
  //     gpa: 3.4,
  //     cv: "url2",
  //   },
  //   {
  //     username: "username3",
  //     address: '{"state":"iowa3","city":"fairfield3","zip":"52557"}',
  //     majorId: 1,
  //     gpa: 3.5,
  //     cv: "url3",
  //   },
  // ];

  let majors = [
    {
      id: 1,
      name: "Compro",
    },
    {
      id: 2,
      name: "MSD",
    },
  ];

  const [studentData, setStudentData] = useState([]);

  async function fetchStudentData() {
    try {
      let res = await axios.get(
        `${process.env.REACT_APP_BACKEND_BASE_URL}/users/student`,
        {
          headers: {
            // Authorization: `Bearer ${keycloak.token}`
          },
        }
      );
      console.log({ data: res.data });
      if (res.data) {
        setStudentData(res.data);
      }
    } catch (err) {
      console.error(err);
    }
  }

  useEffect(() => {
    console.log("Loading");
    fetchStudentData();
  }, []);

  function majorById(id) {
    let major = majors.find((major) => major.id == id);
    return major ? major.name : "";
  }

  function getAddres(address) {
    if (!address) return { state: "", city: "" };
    return {
      state: JSON.parse(address).state,
      city: JSON.parse(address).city,
    };
  }

  let tableData = studentData.map((student) => {
    return {
      major: majorById(student.majorId),
      username: student.username,
      gpa: student.gpa,
      address: student.address,
      cv: student.cv,
      state: getAddres(student.address).state,
      city: getAddres(student.address).city,
    };
  });

  const showModal = () => {
    setIsModalVisible(true);
  };

  const handleOk = () => {
    setIsModalVisible(false);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  const handleView = (student) => {
    console.log(student);
    setCurrentStudent(student);
    showModal();
  };
  //
  return (
    <div>
      <Modal
        title="Basic Modal"
        visible={isModalVisible}
        onOk={handleOk}
        onCancel={handleCancel}
        width={1000}
      >
        <ModalViewStudent
          student={currentStudent}
          keycloak={keycloak}
        ></ModalViewStudent>
      </Modal>
      <Table columns={columns} dataSource={tableData} />
    </div>
  );
};

export default Student;
