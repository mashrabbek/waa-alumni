import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import ReactECharts from "echarts-for-react";
// import jsonwebtoken from "jsonwebtoken";
import axios from "axios";

// import { useKeycloak } from "@react-keycloak/web";
//import AuthrizedElement from "../components/AuthrizedElement";
import { useSelector } from "react-redux";
import { Form, FormGroup, Label, Input } from "reactstrap";
import "antd/dist/antd.css";
import { Button, Space, Table } from "antd";

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

const options = {
  xAxis: {
    type: "category",
    data: [
      "job ad",
      "students per state",
      "students per city",
      "Tags",
      "Tags with location",
      "Average time",
      "Add at least 4",
    ],
  },
  yAxis: {
    type: "value",
  },
  series: [
    {
      data: [120, 200, 150, 80, 70, 110, 130],
      type: "bar",
    },
  ],
};

const HomePage = ({ keycloak }) => {
  // let token = jsonwebtoken.decode(keycloak.token);
  // const [keycloak, initialized] = useKeycloak();

  const [addJobs, setAddJobs] = useState([]);
  const [appJobs, setAppJobs] = useState([]);

  async function fetchTopJobs() {
    let res = await axios.get(
      `${process.env.REACT_APP_BACKEND_BASE_URL}/jobAd/top`,
      // let res = await axios.get(`http://localhost:8080/jobAd`,
      {
        headers: {
          //todo auth
        },
      }
    );
    console.log(res.data);
    if (res.data) setAddJobs(res.data);
  }

  async function fetchRecentJobs() {
    let res = await axios.get(
      `${process.env.REACT_APP_BACKEND_BASE_URL}/jobAd/recent`,
      // let res = await axios.get(`http://localhost:8080/jobAd`,
      {
        headers: {
          //todo auth
        },
      }
    );
    //console.log(res.data);
    if (res.data) setAppJobs(res.data);
  }

  useEffect(() => {
    fetchTopJobs();
    fetchRecentJobs();
  }, []);

  const [filteredInfo, setFilteredInfo] = useState({});
  const [sortedInfo, setSortedInfo] = useState({});

  const handleChange = (pagination, filters, sorter) => {
    console.log("Various parameters", pagination, filters, sorter);
    setFilteredInfo(filters);
    setSortedInfo(sorter);
  };

  const clearFilters = () => {
    setFilteredInfo({});
  };

  const clearAll = () => {
    setFilteredInfo({});
    setSortedInfo({});
  };

  const setAgeSort = () => {
    setSortedInfo({
      order: "descend",
      columnKey: "age",
    });
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
            {files.map((file) => (
              <p>
                <a href={file}>{file}</a>
              </p>
            ))}
          </div>
        );
      },
      ellipsis: true,
    },
  ];

  return (
    <div>
      <h1>Home Page</h1>

      <ReactECharts option={options} className="top" />

      <div className="row">
        <div className="col-sm12">
          <h5>Display the last 10 job advertisements</h5>
          <Table
            columns={columns}
            dataSource={addJobs}
            onChange={handleChange}
          />
        </div>
        <div className="col-12">
          <h5>Display 10 most recently applied job advertisements</h5>
          <Table
            columns={columns}
            dataSource={appJobs}
            onChange={handleChange}
          />
        </div>
      </div>
      {/* End Table */}
    </div>
  );
};
export default HomePage;
