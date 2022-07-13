import React, { useState, useEffect } from "react";
import { useKeycloak } from "@react-keycloak/web";
//import AuthrizedElement from "../components/AuthrizedElement";
import { Link } from "react-router-dom";
import ReactECharts from "echarts-for-react";
// import jsonwebtoken from "jsonwebtoken";
import axios from "axios";

import { Form, FormGroup, Label, Input } from "reactstrap";
import "antd/dist/antd.css";
import { Button, Space, Table } from "antd";
import AddJob from "../components/AddJob";
import EditJob from "../components/EditJob";
import jsonwebtoken from "jsonwebtoken";
import ViewJobApps from "../components/ViewJobApps";

// const data = [
//   {
//     id: 1,
//     description: "George",
//     benefits: "Monkey",
//     creatorUsername: "qq",
//     tags: ["hello1", "hello2", "hello3"],
//     files: ["file1", "file2"],
//   },
//   {
//     id: 2,
//     description: "George",
//     benefits: "Monkey",
//     creatorUsername: "qq",
//     tags: ["hello1", "hello2", "hello3"],
//     files: ["file1", "file2"],
//   },
// ];

const Advertisement = ({ keycloak }) => {
  const [filteredInfo, setFilteredInfo] = useState({});
  const [sortedInfo, setSortedInfo] = useState({});
  const [adData, setAdData] = useState([]);
  const [selectAd, setSelectAd] = useState({});

  let token = jsonwebtoken.decode(keycloak.token);

  async function fetchAdData() {
    try {
      let res = await axios.get(
        `${process.env.REACT_APP_BACKEND_BASE_URL}/jobAd/${token.preferred_username}`,
        {
          headers: {
            // Authorization: `Bearer ${keycloak.token}`
          },
        }
      );
      // [{ username, cv }]
      console.log({ data: res.data });
      if (res.data) {
        setAdData(res.data);
      }
    } catch (err) {
      console.error(err);
    }
  }

  useEffect(() => {
    console.log("Loading");
    fetchAdData();
  }, []);

  const handleChange = (pagination, filters, sorter) => {
    console.log("Various parameters", pagination, filters, sorter);
    setFilteredInfo(filters);
    setSortedInfo(sorter);
  };
  const onJobApply = async (jobAd) => {
    console.log(jobAd);
    let data = {
      jobAdId: jobAd.id,
      studentUsername: token.preferred_username,
    };
    console.log(data);
    try {
      let res = await axios.post(
        `${process.env.REACT_APP_BACKEND_BASE_URL}/jobApplication`,
        data,
        {
          headers: {
            // Authorization: `Bearer ${keycloak.token}`
          },
        }
      );
      console.log({ data: res.data });
      // if (res.data) {
      //   // setAdData(res.data);
      // }
    } catch (err) {
      console.error(err);
    }
  };

  const columns = [
    {
      title: "Id:",
      dataIndex: "id",
      key: "id",
      filteredValue: filteredInfo.id || null,
      onFilter: (value, record) => record.id.includes(value),
      sorter: (a, b) => a.id - b.id,
      sortOrder: sortedInfo.columnKey === "id" ? sortedInfo.order : null,
      ellipsis: true,
    },
    {
      title: "Description:",
      dataIndex: "description",
      key: "description",
      filteredValue: filteredInfo.description || null,
      sorter: (a, b) =>
        a.description > b.description
          ? 1
          : a.description < b.description
          ? -1
          : 0,
      sortOrder:
        sortedInfo.columnKey === "description" ? sortedInfo.order : null,
      ellipsis: true,
    },
    {
      title: "Benifit:",
      dataIndex: "benefits",
      key: "benefits",
      sorter: (a, b) =>
        a.benefits > b.benefits ? 1 : a.benefits < b.benefits ? -1 : 0,
      sortOrder: sortedInfo.columnKey === "benifit" ? sortedInfo.order : null,
      ellipsis: true,
    },
    {
      title: "Tags:",
      dataIndex: "tags",
      key: "tags",
      render: (tags) => {
        return (
          <div>
            {tags.map((ind) => (
              <p>{ind}</p>
            ))}
          </div>
        );
      },
      filteredValue: filteredInfo.tags || null,
      ellipsis: true,
    },
    {
      title: "Creator:",
      dataIndex: "creatorUsername",
      key: "creatorUsername",
      filters: [
        {
          text: "fac",
          value: "fac",
        },
        {
          text: "stu",
          value: "stu",
        },
      ],
      filteredValue: filteredInfo.creatorUsername || null,
      onFilter: (value, record) => record.creatorUsername.includes(value),
      sorter: (a, b) =>
        a.creatorUsername > b.creatorUsername
          ? 1
          : a.creatorUsername < b.creatorUsername
          ? -1
          : 0,
      sortOrder:
        sortedInfo.columnKey === "creatorUsername" ? sortedInfo.order : null,
      ellipsis: true,
    },
    {
      title: "File:",
      dataIndex: "files",
      key: "files",
      render: (files) => {
        return (
          <div>
            {files.map((ind) => (
              <p>
                <a href={ind}>{ind}</a>
              </p>
            ))}
          </div>
        );
      },
      ellipsis: true,
    },
    {
      title: "Action:",
      render: (_, ad) => {
        return (
          <>
            <ViewJobApps ad={ad} />
            <EditJob ad={ad} />
          </>
        );
      },
    },
  ];

  const columns1 = [
    {
      title: "Id:",
      dataIndex: "id",
      key: "id",
      filteredValue: filteredInfo.id || null,
      onFilter: (value, record) => record.id.includes(value),
      sorter: (a, b) => a.id - b.id,
      sortOrder: sortedInfo.columnKey === "id" ? sortedInfo.order : null,
      ellipsis: true,
    },
    {
      title: "Description:",
      dataIndex: "description",
      key: "description",
      filteredValue: filteredInfo.description || null,
      sorter: (a, b) =>
        a.description > b.description
          ? 1
          : a.description < b.description
          ? -1
          : 0,
      sortOrder:
        sortedInfo.columnKey === "description" ? sortedInfo.order : null,
      ellipsis: true,
    },
    {
      title: "Benifit:",
      dataIndex: "benefits",
      key: "benefits",
      sorter: (a, b) =>
        a.benefits > b.benefits ? 1 : a.benefits < b.benefits ? -1 : 0,
      sortOrder: sortedInfo.columnKey === "benifit" ? sortedInfo.order : null,
      ellipsis: true,
    },
    {
      title: "Tags:",
      dataIndex: "tags",
      key: "tags",
      render: (tags) => {
        return (
          <div>
            {tags.map((ind) => (
              <p>{ind}</p>
            ))}
          </div>
        );
      },
      filteredValue: filteredInfo.tags || null,
      ellipsis: true,
    },
    {
      title: "Creator:",
      dataIndex: "creatorUsername",
      key: "creatorUsername",
      filters: [
        {
          text: "fac",
          value: "fac",
        },
        {
          text: "stu",
          value: "stu",
        },
      ],
      filteredValue: filteredInfo.creatorUsername || null,
      onFilter: (value, record) => record.creatorUsername.includes(value),
      sorter: (a, b) =>
        a.creatorUsername > b.creatorUsername
          ? 1
          : a.creatorUsername < b.creatorUsername
          ? -1
          : 0,
      sortOrder:
        sortedInfo.columnKey === "creatorUsername" ? sortedInfo.order : null,
      ellipsis: true,
    },
    {
      title: "File:",
      dataIndex: "files",
      key: "files",
      render: (files) => {
        return (
          <div>
            {files.map((ind) => (
              <p>
                <a href={ind} target="_blank">
                  {ind}
                </a>
              </p>
            ))}
          </div>
        );
      },
      ellipsis: true,
    },
    {
      title: "Action:",
      render: (jobAd) => {
        return (
          <Button
            className="btn btn-primary add-btn"
            onClick={() => onJobApply(jobAd)}
          >
            Apply
          </Button>
        );
      },
    },
  ];

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-12 text-right">
          <AddJob keycloak={keycloak}/>
        </div>
        <div className="col-md-12">
          <h5>User</h5>
          <Table
            columns={columns}
            dataSource={adData}
            onChange={handleChange}
          />
        </div>
        <div className="col-md-12">
          <h5>User</h5>
          <Table
            columns={columns1}
            dataSource={adData}
            onChange={handleChange}
          />
        </div>
      </div>
    </div>
  );
};

export default Advertisement;
